package com.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentEchoServer {

	private static final Logger logger = LoggerFactory.getLogger(ConcurrentEchoServer.class);
	
	
	static class ConnectionWorker implements Runnable {
		Socket cs;
		
		public ConnectionWorker(Socket cs) {
			this.cs = cs;
		}
		
		@Override
		public void run() {
			byte[] buf = new byte[256];
			byte[] strBuf = new byte[512]; //limit it to avoid attacks
			
			try {
				process(cs, buf, strBuf);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);				
			} finally {
				try {
					cs.close();
				} catch (IOException e) { /* ignore */ }
			}			
		}
		
		void process(Socket cs, byte[] buf, byte[] strBuf) throws IOException {
			logger.info("Client connection: " + cs);
						
			int num = 0;
			int off = 0;
			
			InputStream in = cs.getInputStream(); 
			OutputStream out = cs.getOutputStream();
			try  {													
				
				while ((num = in.read(buf)) != -1) {
					logger.debug("num={}", num);
					
					if (num > 0) {
						out.write(buf, 0, num);
						
						if (off < strBuf.length) {
							System.arraycopy(buf, 0, strBuf, off, Math.min(num, strBuf.length - off));
						}
						
						off += num;	
					}																									
				}
				
				if (off > strBuf.length) {			
					logger.warn("String was overflow. Expected: {}, was: {}", strBuf.length, off);
				}
														
			} catch (SocketException se) {
				//connection can be reset by client, so just log it
				logger.warn("", se);				
			}
			
			//try to create 
			String str = new String(strBuf, 0, Math.min(off, strBuf.length), "UTF-8");
			logger.info("Read string: " + str);
		}		
	}
	
	public void exec(int port) throws IOException {		
		ExecutorService executorService = new ThreadPoolExecutor(
				10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(100));
		
		try (ServerSocket ss = new ServerSocket(port)) {
			
			logger.info("Listen on address: " + ss.getLocalSocketAddress());
									
			while (true) {
				Socket cs = ss.accept();
				executorService.submit(new ConnectionWorker(cs));								 						
			}				
		} finally {
			executorService.shutdown();
		}				
	}		

	public static void main(String[] args) throws IOException {
		ConcurrentEchoServer server = new ConcurrentEchoServer();
		server.exec(3535);

	}

}
