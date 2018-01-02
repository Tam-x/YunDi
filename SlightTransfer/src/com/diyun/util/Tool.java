package com.diyun.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.diyun.config.Config;

public class Tool {
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}

	
	public static final long ip2Long(final String ip) {
		final String[] ipNums = ip.split("\\.");
		return (Long.parseLong(ipNums[0]) << 24)
				+ (Long.parseLong(ipNums[1]) << 16)
				+ (Long.parseLong(ipNums[2]) << 8)
				+ (Long.parseLong(ipNums[3]));
	}
	
	public static byte[] longTo4byte(long val){
		byte[] res = new byte[4];
		res[3] = (byte)((val >> 24) & 0xff);
		res[2] = (byte)((val >> 16) & 0xff);
		res[1] = (byte)((val >>  8) & 0xff);
		res[0] = (byte)((val >>  0) & 0xff);
		return res;
	}
	
	public static byte[] ipStringTo4Byte(String ip){
		return longTo4byte(ip2Long(ip));
	}
	
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
	
	public static int byte4ToInt(byte val1, byte val2, byte val3, byte val4){
		return (int)(((val4 & 0xff )<< 24) | 
					 ((val3 & 0xff )<< 16) |
					 ((val2 & 0xff )<<  8) | 
					  (val1 & 0xff ) 
					 );
	}
	
	 public static char ByteToChar(int b) {
	    	char ch = 0;
	    	switch(b) {
	    	case 0:
	    	case 1:
	    	case 2:
	    	case 3:
	    	case 4:
	    	case 5:
	    	case 6:
	    	case 7:
	    	case 8:
	    	case 9:
	    		ch = (char)(b + '0');
	    		break;
	    	case 10:
	    		ch = (char)'A';
	    		break;
	    	case 11:
	    		ch = (char)'B';
	    		break;
	    	case 12:
	    		ch = (char)'C';
	    		break;
	    	case 13:
	    		ch = (char)'D';
	    		break;
	    	case 14:
	    		ch = (char)'E';
	    		break;
	    	case 15:
	    		ch = (char)'F';
	    		break;
	    	default:
	    		break;
	    	}
	    	return ch;
	    }
	    
	    public static String BytesToHexStringEx(byte[] b, int len) {
	        String result = null;
	        int datalen = len;

	        char[] bc = new char[datalen * 2];
	        for (int i = 0; i < datalen; i++) {
	        	bc[i*2] = ByteToChar((b[i]&0xf0)>>4);
	        	bc[i*2 + 1] = ByteToChar(b[i]&0xf);
	        }
	        result = new String(bc);
	        return result;
	    }
	    
	    public static String BytesToHexStringEx(byte[] b, int start, int len) {
	        String result = null;
	        int datalen = len;
	        char[] bc = new char[datalen * 2];
	        for (int i = start; i < datalen; i++) {
	        	bc[i*2] = ByteToChar((b[i]&0xf0)>>4);
	        	bc[i*2 + 1] = ByteToChar(b[i]&0xf);
	        }
	        result = new String(bc);
	        return result;
	    }
	    
	    public static String BytesToHexStringEx(byte[] b) {
	    	return BytesToHexStringEx(b, b.length);
	    }

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
	    public static byte charToByte(char c) {  
	        return (byte) "0123456789ABCDEF".indexOf(c);  
	    }  
	    
	    public static void myLog(String tag, String log){
	    	if(Config.ALLOW_LOG){
	    		System.out.println("["+tag+"] "+log);
	    	}
	    }
}
