package com.diyun.main;

import com.diyun.core.Mqtt;
import com.diyun.core.Server;

public class Main {
	public static void main(String[] args) {
		start();
	}
	
	public static void start(){
		Mqtt client = new Mqtt();
		Server server = new Server("350001000201712");
		server.setListener(client);
		client.setListener(server);
		client.open();
		server.startServer();
	}
}
