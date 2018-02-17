package com.ttu.bank.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ttu.bank.client.Client;
import com.ttu.bank.connector.Connector;
import com.ttu.bank.server.Server;

public class App {

	public static void main(String[] args) {
		//ExecutorsService is a class for managing asynchronous tasks
		ExecutorService executor = Executors.newFixedThreadPool(2);

		Connector conector = new Connector();
		
		Server server = new Server(conector);
		Client client = new Client(conector);
		
		//Submitting classes to executorService object
		executor.submit(client);
		executor.submit(server);
		
		//terminating tasks
		executor.shutdown();
	}

}
