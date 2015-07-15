package com.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoClient {

	private static final Logger logger = LoggerFactory.getLogger(EchoClient.class);
	
	static String host = "localhost";
	static int port = 3535;
	static String encoding = "UTF-8";
	
	static class ClientWorker implements Runnable {
		int id;
		
		public ClientWorker(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			logger.info("Creating client: {}", id);
			
			try (Socket s = new Socket(host, port);) {												
				InputStream in = s.getInputStream(); 
				OutputStream out = s.getOutputStream();
				
				byte[] buf = ("hello_" + id).getBytes(encoding); 
					
				out.write(buf);
				
				int num = in.read(buf);
							
				logger.info("{} Echo: '{}'", id, new String(buf, 0, num, encoding));								
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}			
		}
		
	}
	
	public void process() throws IOException {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		try {
			for (int i = 0; i < 1000; i ++) {
				executorService.submit(new ClientWorker(i));
			}	
		} finally {
			executorService.shutdown();
		}		
	}
	
	public static void main(String[] args) throws Exception {
		EchoClient client = new EchoClient();
		client.process();
	}
}
