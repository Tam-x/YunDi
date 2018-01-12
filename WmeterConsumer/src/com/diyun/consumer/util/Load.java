package com.diyun.consumer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Load device's IPs.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */

public class Load {
	
	private static ArrayList<String> mIps = new ArrayList<String>();
	
	
	public static ArrayList<String> getIps(){
		return mIps;
	}
	
	private static boolean push(String ip){
		String newIp ;
		
		if(ip == null || ip.trim().isEmpty())
			return false;
		
		newIp = ip.trim();
		
		if(!isIp(newIp))
			return false;
		
		if(mIps.contains(newIp))
			return true;
		
		mIps.add(newIp);
		
		return true;
	}
	
	private static void logIps(){
		if(!mIps.isEmpty())
			System.out.println("Load IPs: " + mIps.toString());
		else
			System.out.println("Load IPs: nothing...");
	}
	
	public static boolean isIp(String IP){
		boolean b = false;
		IP = IP.trim();
		if(IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
			String s[] = IP.split("\\.");
			if(Integer.parseInt(s[0])<255)
				if(Integer.parseInt(s[1])<255)
					if(Integer.parseInt(s[2])<255)
						if(Integer.parseInt(s[3])<255)
							b = true;
		}
		return b;
	}

	public static void loadIp(String[] ips) throws Exception{
		if(ips == null || ips.length == 0)
			throw new Exception("No IPs ...");
		for(int i = 0; i < ips.length; i++){
			push(ips[i]);
		}
	}
	
	public static void loadIp(String filename) throws Exception{
		File file = new File(filename);
		System.out.println("ips file path:"+file.getAbsolutePath());
		BufferedReader reader = null;

		try{
			System.out.println("Reading Ip...");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;		
			while((tempString = reader.readLine()) != null){
				push(tempString);
			}		
			logIps();	
			reader.close();
			reader = null;
		} catch (IOException e){
			e.printStackTrace();
			throw new Exception("Load IP List Fail, Please Check...");
		}
	}
}
