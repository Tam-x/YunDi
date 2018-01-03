package com.diyun.tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.diyun.config.Config;

public class Util {
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString().toUpperCase();
	}

	/**
	 * Convert byte to hex string
	 * 
	 * @param src
	 * @return
	 */
	public static String byteToHexString(byte src) {
		StringBuilder stringBuilder = new StringBuilder("");
		int v = src & 0xFF;
		String hv = Integer.toHexString(v);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);

		return stringBuilder.toString().toUpperCase();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	public static void log(String info){
		if(Config.isLog){
			System.out.println("["+getTime()+"]"+info);
		}
	}
	
	public static String formatIp(byte b1, byte b2, byte b3, byte b4){
		 return new StringBuffer().append(b4 & 0xFF).append('.').append(
	        		b3 & 0xFF).append('.').append(b2 & 0xFF)
	                .append('.').append(b1 & 0xFF).toString();
	}
	
	public static boolean checkCRC8(byte[] buf,byte crc8){		
		return ((CRC8.calcCrc8(buf)&0xFF)== (crc8 & 0xFF));		
	}
	
	public static String getTime(){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		return time;
	}
	
	public static String getBCDTime(){	
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("00yyyyMMddHHmmss");
		String time=format.format(date);
		return time;
	}
	
	public static void main(String[] args) {
		System.out.print(getBCDTime());
	}
}
