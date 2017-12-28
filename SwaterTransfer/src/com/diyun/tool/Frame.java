
package com.diyun.tool;

public class Frame {
	//Frame Header(19 Bytes):
	//Body:
	//CRC:
	public static boolean checkFrame(byte[] array){
		
		if(array == null)
			return false;
		
		//must > 20
		if(array.length <= 20){
			return false;
		}
		
		if(!ckeckCrc8(array)){
			return false;
		}
		
		//check Header
		if((array[0] & 0xff) != 0xAA && (array[1] & 0xff) != 0xFF ){
			return false;
		}
		
		//check version
		if(array[2] != (byte)0x01){
			return false;
		}
		
		return true;
	}
	
	private static boolean ckeckCrc8(byte[] array)
	{
		//TODO
		return true;
	}	
}
