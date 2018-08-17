package com.cooksys.java_file_transfer_assessement;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ClientHandler implements Runnable {
	
	private Socket socket;
	private int number;
	private byte[] bytes;
	
	public ClientHandler (Socket socket, int number) {
		this.socket = socket;
		this.number = number;
	}
	
	public static Unmarshaller createUnmarshaller (JAXBContext context) {
		try {
			return context.createUnmarshaller();
		} catch (JAXBException e) {
			System.out.println("ERROR: Unmarshaller creation failed.");
		}
		return null;
	}

	@Override
	public void run() {
		InputStream bis;
		try {
			bis = new BufferedInputStream(new DataInputStream(socket.getInputStream()));
			byte[] bytes = Server.extractByteArray(bis);
			this.bytes = bytes;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		System.out.println("handler initialized.");
		
		String filepath = "server/file" + number + ".xml";
		
		Path path = Paths.get(filepath);
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			System.out.println("Write error.");
			e.printStackTrace();
		}
		
		JAXBContext context = null;
        try {
        	context = JAXBContext.newInstance(MessageSender.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR: JAXB Context creation failed.");
		}
        
		Unmarshaller um = createUnmarshaller(context);
		
		 try {
			MessageSender unmarshalledMessager = (MessageSender) um.unmarshal(new FileInputStream(filepath));
		//	new File(xmlPath).delete();
			
			String directory = "server/" + unmarshalledMessager.getPersonName() + "/" + unmarshalledMessager.getDate() + "/";
			 new File(directory).mkdirs();
				
			 Path path2 = Paths.get(directory, unmarshalledMessager.getFilename());
				
			 try {
				 Files.write(path2, unmarshalledMessager.getBytes());
				 System.out.println("File Written.");
			 } catch (IOException e) {
					// TODO Auto-generated catch block
				 e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (JAXBException e) {
			System.out.println("Unable to unmarshall file.");
			e.printStackTrace();
		}
		 
		 
	}
}
