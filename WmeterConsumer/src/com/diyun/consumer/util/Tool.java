package com.diyun.consumer.util;

import java.util.Calendar;

/**
 * 
 * Tool class.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class Tool {	
	//little endiean
	public static int byte2ToInt(byte val1, byte val2){
		return (int)(((val2 & 0xff) << 8) | 
					  (val1 & 0xff)
					);
	}
	
	//little endiean
	public static int byte4ToInt(byte val1, byte val2, byte val3, byte val4){
		return (int)(((val4 & 0xff )<< 24) | 
					 ((val3 & 0xff )<< 16) |
					 ((val2 & 0xff )<<  8) | 
					  (val1 & 0xff ) 
					 );
	}
	
	public static String formatIp(byte val1, byte val2, byte val3, byte val4){
		return "" + (val4 & 0xff) + "." + (val3 & 0xff) + "." + (val2 & 0xff) + "." + (val1 & 0xff); 
	}
	
	public static String formatIp(int ip){
		return "" + ((ip >> 24) & 0xff) + "." + ((ip >> 16) & 0xff) + "." + ((ip >> 8) & 0xff) + "." + ((ip >> 0) & 0xff); 
	}
	
	//little endiean
	public static long byte8ToLong(byte val1, byte val2, byte val3, byte val4, byte val5, byte val6, byte val7, byte val8){
		return (long)(((val8 & (long)0xff )<< 56) | 
					  ((val7 & (long)0xff )<< 48) |
					  ((val6 & (long)0xff )<< 40) |
					  ((val5 & (long)0xff )<< 32) |
					  ((val4 & (long)0xff )<< 24) |
					  ((val3 & (long)0xff )<< 16) |
					  ((val2 & (long)0xff )<<  8) |
					   (val1 & (long)0xff ) 
					 );
	}
	//little endiean
		public static byte[] longTo4byte(long val){
			byte[] res = new byte[4];		
			res[3] = (byte)((val >> 24) & 0xff);
			res[2] = (byte)((val >> 16) & 0xff);
			res[1] = (byte)((val >>  8) & 0xff);
			res[0] = (byte)((val >>  0) & 0xff);
			return res;
		}
	//little endiean
	public static byte[] longTo8byte(long val){
		byte[] res = new byte[8];
		res[7] = (byte)((val >> 56) & 0xff);
		res[6] = (byte)((val >> 48) & 0xff);
		res[5] = (byte)((val >> 40) & 0xff);
		res[4] = (byte)((val >> 32) & 0xff);
		res[3] = (byte)((val >> 24) & 0xff);
		res[2] = (byte)((val >> 16) & 0xff);
		res[1] = (byte)((val >>  8) & 0xff);
		res[0] = (byte)((val >>  0) & 0xff);
		return res;
	}
	
	public static String formatDateString(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int mon = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		
		return String.format("%04d%02d%02d%02d%02d%02d", year, mon,day,hour,min,sec);      
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
	
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
	
    public static Long ipToLong(String ipString) {  
        Long[] ip = new Long[4];  
        int pos1= ipString.indexOf(".");  
        int pos2= ipString.indexOf(".",pos1+1);  
        int pos3= ipString.indexOf(".",pos2+1);  
        ip[0] = Long.parseLong(ipString.substring(0 , pos1));  
        ip[1] = Long.parseLong(ipString.substring(pos1+1 , pos2));  
        ip[2] = Long.parseLong(ipString.substring(pos2+1 , pos3));  
        ip[3] = Long.parseLong(ipString.substring(pos3+1));  
        return (ip[0]<<24)+(ip[1]<<16)+(ip[2]<<8)+ip[3];  
    }
	
    public static String ipStringToHexString(String ipString){
    	byte val[] = longTo4byte(ipToLong(ipString));
    	return BytesToHexStringEx(val, 0, val.length);
    }
}
