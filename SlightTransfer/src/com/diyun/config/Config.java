package com.diyun.config;

import java.util.Random;

/**
 * Configuration.
 * 
 * @author Tx.Loooper
 * @version 2018/01/02, v1.0
 * @since 1.8
 *
 */
public class Config {
	//http://42.121.18.62:8080/smartLight/xsqpass.jsp
	public static final boolean ALLOW_LOG = true;
	public static final String MQTT_BROKER = "tcp://116.62.237.127:1883";
	public static final String MQTT_USER = "admin";
	public static final String MQTT_PWD = "password";
	public static final String URL = "http://121.196.214.192/smartInterface/sapi.do";
//	public static final String URL = "http://42.121.18.62:8080/smartLightInterface/sapi.do";
//	public static final String URL  = "http://182.61.25.208:8080/smartLightInterface/sapi.do"; 
	public static final String CLIENT_ID = "smart-light-transter-release-"+new Random().nextInt(1000);
	public static final String SERVER_ADDRESS = "121.196.214.192";
	public static final int SERVER_PORT = 9016;
	public static final String MQ_TOPIC_DOWN_HEAD = "dtu/down/global/";
	public static final String[] DTU_IPS = {"1.0.70.0"};
	
	public static String[] getMqttTopic(){
		String[] ips = new String[DTU_IPS.length];
		for(int i = 0; i < DTU_IPS.length; i++){
			ips[i] = "dtu/up/global/"+DTU_IPS[i]+"/devices";
		}
		return ips;
	}
	
	public static int[] getMqttQos(){
		int[] qos = new int[DTU_IPS.length];
		for(int i = 0; i < qos.length; i++){
			qos[i] = 0;
		}
		return qos;
	}
}
