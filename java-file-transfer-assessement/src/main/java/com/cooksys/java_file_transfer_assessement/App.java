package com.cooksys.java_file_transfer_assessement;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Server server = new Server();
    	
    	server.startServer();
    	
    	//ClientGenerator gen = new ClientGenerator();
    	
    	//gen.generate(server);
    }
}
