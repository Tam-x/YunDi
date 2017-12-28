package com.diyun.tool;

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

	 public static String BytesToHexStringEx(byte[] b,int type) {
	        String result = null;
	        if(b == null){
	        	return null;
	        }
	        int datalen = b.length;
	        char[] bc = new char[datalen * 2];
	        for (int i = 0; i < datalen; i++) {
	        	bc[i*2] = ByteToChar((b[i]&0xf0)>>4);
	        	bc[i*2 + 1] = ByteToChar(b[i]&0xf);
	        }
	        result = new String(bc);
	        if(type == 0){
	        	 return result;
	        }else{
	        	 return result.substring(datalen * 2-2,datalen * 2);	
	        }
	              
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
	    		ch = 'A';
	    		break;
	    	case 11:
	    		ch = 'B';
	    		break;
	    	case 12:
	    		ch = 'C';
	    		break;
	    	case 13:
	    		ch = 'D';
	    		break;
	    	case 14:
	    		ch = 'E';
	    		break;
	    	case 15:
	    		ch = 'F';
	    		break;
	    	default:
	    		break;
	    	}
	    	return ch;
	    } 
	  
	    public static int getDeviceAddress(byte address1,byte address2,byte address3,byte address4){
			return byte4ToInt(address1, address2,address3,address4);
		}
}
