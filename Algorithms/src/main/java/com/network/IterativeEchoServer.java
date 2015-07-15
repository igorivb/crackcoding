package com.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IterativeEchoServer {

	public void exec(int port) throws IOException {
		try (ServerSocket ss = new ServerSocket(port)) {
			
			System.out.println("Listen on address: " + ss.getLocalSocketAddress());
			
			byte[] buf = new byte[256];
			byte[] strBuf = new byte[512]; //limit it to avoid attacks
			
			while (true) {
				try (Socket cs = ss.accept()) {
					process(cs, buf, strBuf);					
				} 						
			}				
		}				
	}
	
	void process(Socket cs, byte[] buf, byte[] strBuf) throws IOException {
		System.out.println("Client connection: " + cs.getRemoteSocketAddress());
		
		try (InputStream in = cs.getInputStream(); OutputStream out = cs.getOutputStream();) {			
			int off = 0;			
			int num;
			
			while ((num = in.read(buf)) != -1) {
				System.out.printf("DEBUG: num=%d%n", num);
				
				if (num > 0) {
					out.write(buf, 0, num);
					
					if (off < strBuf.length) {
						System.arraycopy(buf, 0, strBuf, off, Math.min(num, strBuf.length - off));
					}
					
					off += num;	
				}																									
			}
			
			if (off > strBuf.length) {			
				System.out.printf("WARN: String was overflow. Expected: %d, was: %d%n", strBuf.length, off);
			}
			
			//try to create 
			String str = new String(strBuf, 0, Math.min(off, strBuf.length), "UTF-8");
			System.out.println("Read string: " + str);
		}						
	}

	public static void main(String[] args) throws IOException {
		IterativeEchoServer server = new IterativeEchoServer();
		server.exec(3535);

	}

}
