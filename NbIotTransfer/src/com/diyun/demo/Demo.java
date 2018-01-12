package com.diyun.demo;

import com.diyun.code.Server;

public class Demo {
	public static void main(String[] args) {
		try {
			Server s = new Server();
			s.openServer();
		} catch (Exception e) {
			System.out.println("server starts failed...");
			e.printStackTrace();
		}
	}
}
