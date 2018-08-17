package com.cooksys.java_file_transfer_assessement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ClientHandler implements Runnable {
	
	private byte[] bytes;
	private int number;
	
	public ClientHandler (byte[] bytes, int number) {
		this.bytes = bytes;
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
		System.out.println("handler initiated!");
		
		String filepath = "server/file" + number + ".xml";
		
		Path path = Paths.get(filepath);
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			System.out.println("Write error.");
			e.printStackTrace();
		}
		
		unmarshallAndWrite(filepath);
	}
	
	public void unmarshallAndWrite (String xmlPath) {
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
			MessageSender unmarshalledMessager = (MessageSender) um.unmarshal(new FileInputStream(xmlPath));
			writeTextFile(unmarshalledMessager);
		//	new File(xmlPath).delete();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (JAXBException e) {
			System.out.println("Unable to unmarshall file.");
			e.printStackTrace();
		}
	}
	
	public void writeTextFile(MessageSender messager) {
		String directory = "server/" + messager.getPersonName() + "/" + messager.getDate() + "/";
		new File(directory).mkdirs();
		
		Path path = Paths.get(directory, messager.getFilename());
		
		try {
			Files.write(path, messager.getBytes());
			System.out.println("File Written.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
