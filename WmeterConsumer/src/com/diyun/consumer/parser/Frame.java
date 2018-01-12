package com.diyun.consumer.parser;

import com.diyun.consumer.bean.BaseValue;
import com.diyun.consumer.bean.BatteryValue;
import com.diyun.consumer.bean.MagneticValue;
import com.diyun.consumer.bean.OtherAlarm;
import com.diyun.consumer.bean.ParameterValue;
import com.diyun.consumer.bean.RealtimeValue;
import com.diyun.consumer.util.CRC8;
import com.diyun.consumer.util.Log;
import com.diyun.consumer.util.Tool;

/**
 * 
 * Parse frame data which protocol definition by Nix.Long
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class Frame {
	private static final String TAG = "Frame"; 

	public static boolean checkFrame(byte[] array){
		
		if(array == null)
			return false;
		
		//must > 20
		if(array.length <= 20){
			return false;
		}
		
		if(!ckeckCrc8(array)){
			Log.logw(TAG, "invalid crc8 value");
			return false;
		}
		
		//check Header
		if((array[0] & 0xff) != 0xAB && (array[1] & 0xff) != 0xFF ){
			Log.logw(TAG, "invalid frame sync header, "+  array[0] + "," +array[1]);
			return false;
		}
		
		//check version
		if(array[2] != (byte)0x01){
			Log.logw(TAG, "invalid protocol version");
			return false;
		}
		
		return true;
	}
	
	private static boolean ckeckCrc8(byte[] array)
	{
		byte[] buf = new byte[array.length-3];
		System.arraycopy(array, 0, buf, 0, buf.length);
		if((CRC8.calcCrc8(buf)&0xFF) == (array[array.length-3]&0xFF)){
			return true;
		}
		Log.logw(TAG, "invalid crc8");
		return false;
	}
	
	public static BaseValue parseFrame(byte[] frame){
		String ip = Tool.formatIp(frame[3], frame[4], frame[5], frame[6]);
		String dataTime = Tool.BytesToHexStringEx(frame,0,frame.length).substring(20, 34);		
		switch(frame[8] & 0xff){
			case BaseValue.REAL_TIME_VALUE:				
				return parseRealTime(frame, ip, dataTime);
			case BaseValue.BATTERY_ALARM:
				return parseBatteryValue(frame, ip, dataTime);
			case BaseValue.MAGNETIC_VALUE:
				return parseMagneticValue(frame, ip, dataTime);
			case BaseValue.OTHER_ALARM:
				return parseOtherAlarm(frame, ip, dataTime);
			case BaseValue.PARAMETER_VALUE:
				return parseParameterValue(frame, ip, dataTime);
		}
		return null;
	}
	
	private static RealtimeValue parseRealTime(byte[] data, String ip, String dataTime){		
		int status = data[19]&0xFF;
		String generateTime = Tool.BytesToHexStringEx(data,0,data.length).substring(40, 48);
		int type = data[24]&0xFF;
		float value = (float) 0.0;
		value = (float) (Tool.byte4ToInt(data[25], data[26], data[27], data[28])/1000.0);		
		return new RealtimeValue(ip, dataTime, status, generateTime, type, value);
	}
	
	private static BatteryValue parseBatteryValue(byte[] data, String ip, String dataTime){
		int status = data[19]&0xFF;
		String generateTime = Tool.BytesToHexStringEx(data,0,data.length).substring(40, 48);
		int type = data[24]&0xFF;
		int alarm = data[25]&0xFF;
		float value = (float) ((data[26]&0xFF)/10.0);
		int reserve = (data[27]&0xFF) | (data[28]<<8)&0xFF00  | (data[29]<< 16)&0xFF0000 ;
		return new BatteryValue(ip, dataTime, status, generateTime, type, alarm, value, reserve);
	}
	
	private static MagneticValue parseMagneticValue(byte[] data, String ip, String dataTime){
		int status = data[19]&0xFF;
		String generateTime = Tool.BytesToHexStringEx(data,0,data.length).substring(40, 48);
		int meterType = data[24]&0xFF;
		int alarmType = data[25]&0xFF;
		int reserve = Tool.byte4ToInt(data[26], data[27], data[28], data[29]);	
		return new MagneticValue(ip, dataTime, status, generateTime, meterType, alarmType, reserve);
	}
	
	private static OtherAlarm parseOtherAlarm(byte[] data, String ip, String dataTime){
		int status = data[19]&0xFF;
		String generateTime = Tool.BytesToHexStringEx(data,0,data.length).substring(40, 48);
		int meterType = data[24]&0xFF;
		int alarmType = data[25]&0xFF;
		int reserve = Tool.byte4ToInt(data[26], data[27], data[28], data[29]);
		return new OtherAlarm(ip, dataTime, status, generateTime, meterType, alarmType, reserve);
	}
	
	private static ParameterValue parseParameterValue(byte[] data, String ip, String dataTime){
		int type = data[19]&0xFF;
		String freezeTime = Tool.BytesToHexStringEx(data,0,data.length).substring(40, 44);
		String wakeupTime = Tool.BytesToHexStringEx(data,0,data.length).substring(44, 50);
		String server = Tool.formatIp(data[25], data[26], data[27], data[28]);
		int port = data[29]&0xFF | (data[30]<< 8)& 0xFF00 ;
		int meterNumber = Tool.byte4ToInt(data[31], data[32], data[33], data[34]);
		int reserve = Tool.byte4ToInt(data[35], data[36], data[37], data[38]);
		return new ParameterValue(ip, dataTime, type, freezeTime, wakeupTime, server, port, meterNumber, reserve);
	}

}
