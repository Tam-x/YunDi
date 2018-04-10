package com.diyun.parser;

import com.diyun.message.BaseFrame;
import com.diyun.util.Logger;
import com.diyun.util.Utility;

/**
 * Check the data frame is legitimate or not.
 * @author t.x
 * <br>2018/03/28<br>
 *
 */
public class CheckFrame {
	
	private static final String TAG = "CheckFrame";
	
	public static boolean checkFrame(byte[] payload){	
		int length = 0;
		Logger.print(TAG,"Origin frame:"+Utility.parseBytesToHexStr(payload));		
		if(payload == null){
			Logger.print(TAG, "Pyaload is null.");
			return false;
		}		
		length = payload.length;
		if(length < BaseFrame.FRAME_MIN_LEN){
			Logger.print(TAG, "Payload's length is incorrect.");
			return false;
		}
		
		if( ((payload[0]&0xFF) != (BaseFrame.HEADER_FLAG_1&0xFF)) || 
			((payload[1]&0xFF) != (BaseFrame.HEADER_FLAG_2&0xFF))  ||
			((payload[length-1]&0xFF) != (BaseFrame.TAIL_FLAG_2&0xFF))||
			((payload[length-2]&0xFF) != (BaseFrame.TAIL_FLAG_1&0xFF))
		){
			Logger.print(TAG, "Payload's hearder or tail  is incorrect.");
			return false;
		}
		if(payload[2] != BaseFrame.PROTOCOL_VERSION){
			Logger.print(TAG, "Payload's protocol version is incorrect.");
			return false;
		}
		if(Utility.byte2ToInt(payload[3], payload[4])-BaseFrame.FRAME_MIN_LEN < 0 ||
				Utility.byte2ToInt(payload[3], payload[4]) != payload.length){
			Logger.print(TAG, "Payload's length is incorrect.");
			return false;
		}
		
		if(!Utility.checkCRC8(payload)){
			Logger.print(TAG, "Payload checks CRC8 error.");
			return false;
		}		
		return true;	
	}
	
}
