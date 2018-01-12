package com.diyun.consumer.util;

import java.util.ArrayList;

import com.diyun.consumer.Config;

/**
 * MQTT topic.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class Topic {
	public static final String TAG = "Topic";
	public static final String MQTT_UP_TOPIC_HEAD = "nbiot/up/wmeter/";
	public static final String MQTT_TOPIC_DEVICE_TAIL = "/devices";
	public static final String MQTT_TOPIC_MAINTENANCE_TAIL = "/maintenance";
	public static final String MQTT_DOWN_TOPIC = "nbiot/down/wmeter/orders";
	private static ArrayList<String> mMqttTopics = new ArrayList<String>();
	private static boolean isSubscibeDevices = true;
	private static boolean isSubscibeMaintenance = true;
	
	public static void setSubscibeDevices(boolean status){
		isSubscibeDevices = status;
	}
	
	public static void setSubscibeMaintenance(boolean status){
		isSubscibeMaintenance = status;
	}
	
	public static String[] getSubscribeTopics(){
		try {
			if(Config.LOAD_IP){
				Load.loadIp("ips_template.txt");
			}else{
				Load.loadIp(Config.ips);
			}
		}catch (Exception e) {		
			Log.logh(TAG, "load ips fialed..."+e.toString());
		}
		ArrayList<String> ips = Load.getIps();
		mMqttTopics.clear();
		for(String iter:ips){
			if(isSubscibeDevices)mMqttTopics.add("nbiot/up/wmeter/" + iter + "/devices");			
			if(isSubscibeMaintenance)mMqttTopics.add("nbiot/up/wmeter/" + iter + "/maintenance");			
		}
		return (String[]) mMqttTopics.toArray(new String[mMqttTopics.size()]);
	}
}
