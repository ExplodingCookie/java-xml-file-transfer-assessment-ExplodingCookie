package com.cooksys.java_file_transfer_assessement;

public class ClientGenerator {
	
	public static void main( String[] args )
    {
			MessageSender sender = new MessageSender("Alex", 2018, 8, 17, "text1.txt", null);
	    	
			Client client = new Client(sender);
	        
	        client.initialize();
	        
	        MessageSender sender2 = new MessageSender("Rex", 2018, 8, 17, "text2.txt", null);
	    	
			Client client2 = new Client(sender2);
	        
	        client2.initialize();
	}
}
