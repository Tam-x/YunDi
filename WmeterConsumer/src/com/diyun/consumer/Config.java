package com.diyun.consumer;

/**
 * 
 * Configuration.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class Config {
	
	public final static String VERSION = "1.0";		
	private static int mode = Config.MODE_CUSTOMER_RELEASE;
	public static final int MODE_CUSTOMER_RELEASE = 0x00;		//release
	public static final int MODE_CUSTOMER_DEBUG   = 0x01;		//inner	
	public static final String BROKER = "tcp://182.61.25.208:1883";
	
	public static final String CLIENT_ID(){
		return (mode == MODE_CUSTOMER_RELEASE) ? "nbiot-wmeter-consumer-release-": "nbiot-wmeter-consumer-debug-";
	}
	public final static boolean LOAD_IP = true;//if true, load IP from ips_template.txt
	public static String[] ips = new String[]{"120.57.233.110","120.54.233.110","0.1.0.1","0.1.0.2"};
	
}