package com.cooksys.java_file_transfer_assessement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
	int handledInts = 0;
	
	public void startServer () {
		System.out.println("Server started");
		
		while(true) {
			try (
				ServerSocket ss = new ServerSocket(8080);	
			){
				Socket server = ss.accept();
				Runnable ch = new ClientHandler(server, handledInts);
				new Thread(ch).start();
			} catch (IOException e) {
				System.out.println("Server was unable to connect to the client: 8080");
				e.printStackTrace();
				//break;
			}
		}
	}
	
	public static byte[] extractByteArray (InputStream inputStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
			baos.write(buffer, 0, read);
		}		
		baos.flush();
		return Arrays.copyOfRange(baos.toByteArray(), 1, baos.toByteArray().length);
	}
}
