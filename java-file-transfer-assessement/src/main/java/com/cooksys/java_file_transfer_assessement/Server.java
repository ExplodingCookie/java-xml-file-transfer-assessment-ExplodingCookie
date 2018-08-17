package com.cooksys.java_file_transfer_assessement;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
	int handledInts = 0;
	
	public void startServer () {
		System.out.println("Server started");
		
		
		try (
			ServerSocket ss = new ServerSocket(8080);	
			Socket server = ss.accept();
			InputStream bis = new BufferedInputStream(new DataInputStream(server.getInputStream()));
		){
			byte[] bytes = extractByteArray(bis);
			ClientHandler ch = new ClientHandler(bytes, handledInts);
			Thread t = new Thread(ch);
			t.start();
		} catch (IOException e) {
			System.out.println("Server was unable to connect to the client: 8080");
			e.printStackTrace();
			//break;
		}
	}
	
	public byte[] extractByteArray (InputStream inputStream) throws IOException {
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
