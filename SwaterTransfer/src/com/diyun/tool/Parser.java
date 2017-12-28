package com.diyun.tool;


import java.util.Arrays;

public class Parser {
	public static boolean checkInvalid(byte[] msg){
		// can not be null or empty
		if(msg ==null || msg.length ==0){
			return false;
		}

		return true;
	}
	
	public static byte[] parseToByte(String msg){
		int length = msg.length()/2;
		byte[] Array = new byte[length];
		
		for(int i=0; i < length; i++){
			try{
				Array[i] = (byte) Integer.parseInt(msg.substring(2*i, 2*i+2), 16);
			} catch (Exception e){
				//parse fail
				return null;
			}
		}
		
		System.out.println(Arrays.toString(Array));
		return Array;
	}
	
	public static boolean checkSum(byte[] Input){
		return true;
	}
}
