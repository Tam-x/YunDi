package com.diyun.util;

import java.util.HashMap;

/**
 * 
 * @author Tx.loooper
 * @version 2017/12/27 V1.0
 * @since 1.6
 *
 */
public class Global {
	
	//Message queue max size.
	public static final int MESSAGE_QUEUE_SIZE = 128;
	//Connecting cloud's max number.
	public static final int MAX_RETRY_NUM = 8;
	// If message from server to cloud over this time, server will send message to cloud.
	public static final long MAX_ACK_INTERVAL_TIME = 1000 * 60 * 3;
	// If message from cloud to server over this time, server will reconnect cloud.
	public static final long MAX_RESET_TIME = 1000 * 60 * 6;
	//Allow connecting cloud many times.
	public static final boolean ALLOW_RETRY_CONNECT = true;
	//IMEI list.
	public static final HashMap<Integer, String> IMEIS = new HashMap<Integer, String>();
	//There is a time difference between the client and server.	
	public static long timeDiff = 0;
	
}
