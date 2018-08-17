package com.cooksys.java_file_transfer_assessement;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class Client {
	MessageSender sender;
	
	public Client (MessageSender sender) {
		this.sender = sender;
	}
	
	public static Marshaller createMarshaller (JAXBContext context) {
		try {
			return context.createMarshaller();
		} catch (JAXBException e) {
			System.out.println("ERROR: Marshaller creation failed.");
		}
		return null;
	}
	
	public void getFileInput () {
		try(
			InputStream fis = new FileInputStream(sender.getFilename());
		) {
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			//System.out.print(Arrays.toString(bytes));
			sender.setBytes(bytes);
			System.out.println("text file read successfully.");
		} catch (IOException e){
			System.out.println("Failed to read file.");
			e.printStackTrace();
		}
	}
	
	public void initialize () {
		System.out.println("Client initialized");
		getFileInput();
		
		JAXBContext context = null;
        try {
        	context = JAXBContext.newInstance(MessageSender.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR: JAXB Context creation failed.");
		}
        
        Marshaller m = createMarshaller(context);
        
        try {
        	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        	m.marshal(sender, new FileOutputStream(sender.getPersonName() + "-" + sender.getDate() + ".xml"));
        	System.out.println("file marshalled to an xml successfully.");
        } catch (JAXBException e) {
        	System.out.println("ERROR: Failed to marshall the file.");
        } catch (FileNotFoundException e) {
        	System.out.println("ERROR: File not found.");
        }
        
        sendFile(sender.getPersonName() + "-" + sender.getDate() + ".xml");
	}
	
	public void sendFile (String xmlFilename) {
		try(
			Socket client = new Socket("localhost", 8080);
			OutputStream bos = new BufferedOutputStream(new DataOutputStream(client.getOutputStream()));
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(xmlFilename));
		) {
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			System.out.println(Arrays.toString(bytes));
			bos.write(bytes.length);
			bos.write(bytes);
			bos.flush();
			System.out.println("File sent to socket.");
		} catch (IOException e){
			System.out.println("Failed to connect to localhost on port 8080");
			e.printStackTrace();
		}
	}
}
