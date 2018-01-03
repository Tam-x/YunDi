package com.diyun.config;

public class Config {
	public static final String VERSION = "v11";
	
	public static final boolean isLog = true;	
	public static final boolean needFakeTime = true;
	
	public static final String UDP_SERVER = "42.121.18.62";	
	public static final int PORT = 10010;
	
	public static final String MQTT_BROKER = "tcp://182.61.25.208:1883";
	public static final String MQTT_UP_TOPIC_HEAD = "nbiot/up/wmeter/";
	public static final String MQTT_UP_TOPIC_TAIL_DEVICE = "/devices";
	public static final String MQTT_UP_TOPIC_TAIL_HEAT = "/maintenance";
	public static final String MQTT_DOWN_TOPIC = "nbiot/down/wmeter/orders";
	
	public static final String TABLE_NAME = "CMDDB";
	public static final String DB_NAME = "NBIOT.db";
	
	public static final int[] VERSIONS = {0x01};
	public static final int[] PROJECTS = {0x66};
	public static final int[] TYPES = {0x01, 0x02, 0x03, 0x04, 0x05};
	
}
