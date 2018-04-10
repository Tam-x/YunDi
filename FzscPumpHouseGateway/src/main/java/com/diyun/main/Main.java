package com.diyun.main;

import java.net.UnknownHostException;

import com.diyun.consumer.Config;
import com.diyun.consumer.Consumer;
import com.diyun.parser.DecodeFrame;
import com.diyun.util.Utility;

import redis.clients.jedis.Jedis;


public class Main {

	public static void main(String[] args) {	
		try {			
			Config.setHost("tcp://182.61.25.208:1883");
			Consumer consumer = new Consumer();
			consumer.setUniqueId("cd-maven-tx");
			consumer.open();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}			
	}

	public void testDecode(){
		Jedis jedis = new Jedis("localhost",6379);
		String test = "AAFE0200210102010010000118032803030311221F3204E810201F3204E9F6DDEE";
		for(int i = 0; i<100; i++){
			DecodeFrame.decodeRealTimeMsg(Utility.hexStringToBytes(test), jedis);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
