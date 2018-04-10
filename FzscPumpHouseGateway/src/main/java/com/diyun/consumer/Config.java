package com.diyun.consumer;

import java.net.UnknownHostException;

public class Config {
	
	public final static String VERSION = "v1.1.0";	
	
	private static int mode = Config.MODE_CUSTOMER_RELEASE;
		
	public static final int MODE_CUSTOMER_RELEASE = 0x00;		//release
	public static final int MODE_INNER_DEBUG      = 0x01;		//inner	
	
	public static final String HOST_TEST = "tcp://182.61.25.208:1883";
	public static String HOST = HOST_TEST;  	
	
	public static void setHost(String host) throws UnknownHostException{
		if(host == null || host.isEmpty()){
			throw new UnknownHostException("host is empty/null");
		}		
		
		if(host.startsWith("tcp://")){
			HOST = host;
		} else {
			throw new UnknownHostException("host must be tcp://x.x.x.x:1883");
		}
	}
	
	public static final String CLIENT_ID(){
		return (mode == MODE_CUSTOMER_RELEASE) ? ("fzphouse-consumer-release-") : ("fzphouse-consumer-debug-");
	}
	
	public static String NBWMETER_TOPIC_PREFIX(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("dtu/up/phouse/wltx") 	: 	("debug/dtu/up/phouse/wltx");
	}
	
	public static String NBWMETER_TOPIC_MATCH_ALL(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("dtu/up/phouse/wltx/#") 	: 	("debug/dtu/up/phouse/wltx/#");
	}
	
	private static boolean mIsToSubscibeDevices = true;	
	private static boolean mIsToSubscibeMaintenance = true;
	
	public static void setToSubscibeDevices(boolean status){
		mIsToSubscibeDevices = status;
	}
	
	public static boolean getToSubscibeDevices(){
		return mIsToSubscibeDevices;
	}
	
	public static void setToSubscibeMaintenance(boolean status){
		mIsToSubscibeMaintenance = status;
	}
	
	public static boolean getToSubscibeMaintenance(){
		return mIsToSubscibeMaintenance;
	}

	public static String getSubscribeTopic(){		
		return Config.NBWMETER_TOPIC_MATCH_ALL();		
	}
	
	public static boolean isSupportTopic(String topic){
		if(topic == null || topic.isEmpty())
			return false;
		
		if(topic.startsWith(Config.NBWMETER_TOPIC_PREFIX())){
			return true;
		}
		return false;
	}
}