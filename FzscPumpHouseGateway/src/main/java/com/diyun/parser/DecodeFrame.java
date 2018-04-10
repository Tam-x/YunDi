package com.diyun.parser;

import redis.clients.jedis.Jedis;
import com.diyun.config.Config;
import com.diyun.data.DataBean;
import com.diyun.message.BaseFrame;
import com.diyun.message.FrameInstance;
import com.diyun.util.Logger;
import com.diyun.util.Utility;

/**
 * Decode the data frame.
 * @author t.x
 * <br>2018/03/28<br>
 *
 */
public class DecodeFrame {
	
	private static final String TAG = "DecodeFrame";
	
	public static void decodeRealTimeMsg(byte[] msg, Jedis jedis){
		if(CheckFrame.checkFrame(msg)){
			BaseFrame frame = FrameInstance.getFrameInstance(msg);
			if(null != frame){
				DataBean bean = new DataBean();				
				if(frame.getCmdCode() == FrameInstance.CMD_REAL_TIME){
					handleRealTimeData(frame, bean, jedis);
				}else{					
					handleHeartbeatData(frame, bean, jedis);
				}			
			}			
		}
	}
	
	/**
	 * handle heart beat(the tag is 02).
	 * 
	 * @param frame Frame data encapsulation object. 
	 * @param bean  Redis data encapsulation object. 
	 */
	private static void handleHeartbeatData(BaseFrame frame, DataBean bean, Jedis jedis){
		bean.setIp(frame.getDtuIp());
		bean.setReportTime(frame.getDate());
		bean.setDataTag("02");
		bean.setValue("heartbeat");
		bean.setDeviceTag(frame.getDtuIp()+"0000");
		bean.setFullTag(frame.getDtuIp()+"000002");
		saveData(bean, jedis);
	}
	
	private static void handleRealTimeData(BaseFrame frame, DataBean bean, Jedis jedis){
		bean.setIp(frame.getDtuIp());
		bean.setReportTime(frame.getDate());
		decode(frame.getData(),jedis, bean, frame.getDataLen());
	}
	
	/**
	 * Analyzing TLV format data frames by recursion.
	 * 
	 * @param data
	 * @param bean
	 * @param length
	 */
	private static void decode(byte[] data, Jedis jedis,DataBean bean, int length){		
		String adr = Utility.byteToHexStr(data[0]);
		String channel = Utility.byteToHexStr(data[1]);
		String tag = Utility.byteToHexStr(data[2]);
		
		bean.setDataTag(tag);
		bean.setDeviceTag(bean.getIp()+adr+channel);
		bean.setFullTag(bean.getIp()+adr+channel+tag);
		
		int valueLen = parseLenBit(data, jedis, bean, data[3]);		
		int nextLen = length - valueLen - 4;
		if (nextLen > 0){
			byte[] nextData = new byte[length-valueLen -4];
			System.arraycopy(data, valueLen + 4, nextData, 0, nextLen);			
			decode(nextData, jedis, bean, nextLen);
		} 
	}	
	
	/**
	 * Analyzing the 'L' of TLV format data frames.
	 * <br>High four-bit representative data type</br>
	 * <br>Low three-bit representative data digits</br>
	 * @param data
	 * @param bean
	 * @param bit
	 * @return the byte length of data.
	 */
	private static int parseLenBit(byte[] data, Jedis jedis, DataBean bean, byte bit){
		int fCode = Utility.byteHigh4Bit(bit);
		int pCode = Utility.byteLow3Bit(bit);		
		double p = countPercision(pCode);		
		return caculateValue(data, jedis, bean, fCode, p);
	}
	
	private static double countPercision(int pCode){
		switch (pCode) {
		case 1:
			return 0.1;
		case 2:
			return 0.01;
		case 3:
			return 0.001;
		case 4:
			return 0.0001;
		case 5:
			return 0.00001;
		case 6:
			return 0.000001;
		case 7:	
			return 0.0000001;		
		default:
			return 1;
		}
	}
	
	/**
	 * Caculate the data value.
	 * 
	 * @param data
	 * @param bean
	 * @param fCode
	 * @param percision
	 * @return the byte length of data.
	 */
	private static int caculateValue(byte[] data, Jedis jedis, DataBean bean, int fCode, double percision){
		switch (fCode) {	
		case 0:
			handleBooleanValue(data, jedis, bean, percision);			
			return 1;
		case 1:
			handleSignedCharValue(data, jedis, bean, percision);			
			return 1;
		case 2:
			handleUnsignedCharValue(data, jedis, bean, percision);		
			return 1;
		case 3:
			handleSignedShortValue(data, jedis, bean, percision);		
			return 2;
		case 4:
			handleUnsignedShortValue(data, jedis, bean, percision);			
			return 2;
		case 5:
			handleSignedIntValue(data, jedis, bean, percision);			
			return 4;
		case 6:
			handleUnsignedIntValue(data, jedis, bean, percision);			
			return 4;
		case 7:
			handleSignedLongValue(data, jedis, bean, percision);		
			return 8;
		case 8:
			handleUnsignedLongValue(data, jedis, bean, percision);			
			return 8;
		case 9:
			handleFloatValue(data, jedis, bean, percision);	
			return 4;
		case 10:
			handleDoubleValue(data, jedis, bean, percision);			
			return 8;
		case 11:
			handleDateArray(data, jedis, bean, percision);
			return 6;
		case 12:
			handleAsciiArray(data, jedis, bean, percision);			
			return data[4]&0xFF+1;
		case 13:
			handleByteArray(data, jedis, bean, percision);			
			return data[4]&0xFF+1;
		case 14:			
		case 15:						
			return 0;	
		}
		return 0;
	}
	
	private static void handleBooleanValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		bean.setValue(String.valueOf(data[4]&0xFF));
		saveData(bean, jedis);
	}
	
	private static void handleSignedCharValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		char signedChar = Utility.byteToSignedChar(data[4]);
		bean.setValue(String.valueOf(signedChar));
		saveData(bean, jedis);
	}
	
	private static void handleUnsignedCharValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		short unsignedChar = Utility.byteToUnsignedChar(data[4]);
		bean.setValue(Utility.handleDecimal(unsignedChar, percision));
		saveData(bean, jedis);
	}
	
	private static void handleSignedShortValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		int signedShort = Utility.byte2ToSignedShort(data[4], data[5]);
		bean.setValue(Utility.handleDecimal(signedShort, percision));
		saveData(bean, jedis);
	}
	
	private static void handleUnsignedShortValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		int unSignedShort = Utility.byte2ToUnsignedShort(data[4], data[5]);
		bean.setValue(Utility.handleDecimal(unSignedShort, percision));
		saveData(bean, jedis);
	}
	
	private static void handleSignedIntValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		int signedInt = Utility.byte4ToSignedInt(data[4], data[5], data[6], data[7]);
		bean.setValue(Utility.handleDecimal(signedInt, percision));
		saveData(bean, jedis);
	}
	
	private static void handleUnsignedIntValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		long unSignedInt = Utility.byte4ToUnsignedInt(data[4], data[5], data[6], data[7]);
		bean.setValue(Utility.handleDecimal(unSignedInt, percision));
		saveData(bean, jedis);
	}
	
	private static void handleSignedLongValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		long signedLong = Utility.byte8ToLong(data[4], data[5], data[6], data[7],
				data[8], data[9], data[10], data[11]);
		bean.setValue(Utility.handleDecimal(signedLong, percision));
		saveData(bean, jedis);
	}
	
	private static void handleUnsignedLongValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		long unSignedLong = Utility.byte8ToLong(data[4], data[5], data[6], data[7],
				data[8], data[9], data[10], data[11]);
		bean.setValue(Utility.handleDecimal(unSignedLong, percision));
		saveData(bean, jedis);
	}
	
	private static void handleFloatValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		float f = Float.intBitsToFloat(Utility.byte4ToSignedInt(data[4], data[5], data[6], data[7]));  
		bean.setValue(String.valueOf(f));
		saveData(bean, jedis);
	}
	
	private static void handleDoubleValue(byte[] data, Jedis jedis, DataBean bean,double percision){
		double d = Double.longBitsToDouble(Utility.byte8ToLong(data[4], data[5], data[6], data[7],
				data[8], data[9], data[10], data[11])); 
		bean.setValue(String.valueOf(d));
		saveData(bean, jedis);
	}
	
	private static void handleDateArray(byte[] data, Jedis jedis, DataBean bean,double percision){
		byte[] dateArray = new byte[6];
		System.arraycopy(data, 4, dateArray, 0, 6);
		bean.setValue(Utility.formateHexDate(dateArray));
		saveData(bean, jedis);
	}
	
	private static void handleAsciiArray(byte[] data, Jedis jedis, DataBean bean,double percision){
		int lenAscii = data[4]&0xFF;
		String value = null;
		if(lenAscii > 0){
			byte[] ascii = new byte[lenAscii];
			System.arraycopy(data, 5, ascii, 0, lenAscii);
			value = new String (ascii);
		}
		bean.setValue(value);
		saveData(bean, jedis);
	}
	
	private static void handleByteArray(byte[] data, Jedis jedis, DataBean bean, double percision){
		int lenArray = data[4]&0xFF;
		String valueArray = null;
		if(lenArray > 0){
			byte[] array = new byte[lenArray];
			System.arraycopy(data, 5, array, 0, lenArray);
			valueArray = Utility.parseBytesToHexStr(array);
		}
		bean.setValue(valueArray);
		saveData(bean, jedis);
	}
	
	/**
	 * Saving data to redis and file.
	 * 
	 * @param bean
	 * @param jedis
	 */
	private static void saveData(DataBean bean, Jedis jedis){
		Logger.log(TAG, bean.toJsonString());
		try{
			if(bean.getDataTag().equals("22") || bean.getDataTag().equals("23") || bean.getDataTag().equals ("24")){
				jedis.publish(Config.REDIS_KEY_DOOR, bean.toJsonString());
			}
			jedis.hset(Config.REDIS_KEY_DATA + bean.getDataTag(), bean.getDeviceTag(), bean.toJsonString());
			jedis.expire(Config.REDIS_KEY_IP + bean.getIp(), Config.REDIS_SAVE_DATA_TIME_OUT);
			jedis.hset(Config.REDIS_KEY_IP + bean.getIp(), bean.getFullTag(), bean.toJsonString());
			jedis.hset(Config.REDIS_KEY_MAP, bean.getIp(), bean.getIp());		
		}catch(Exception e){
			Logger.log(TAG, e.toString());
			try{
				if(jedis.isConnected()){
					jedis.disconnect();
				}
				jedis = new Jedis("localhost",6379);
			}catch (Exception e2) {
				Logger.log(TAG, e2.toString());
			}			
		}
		Logger.logf(bean.getIp(), bean.toJsonString());
	}	
}
