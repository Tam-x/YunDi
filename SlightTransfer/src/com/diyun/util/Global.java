package com.diyun.util;

/**
 * Global filed here.
 * 
 * @author Tx.Loooper
 * @version 2018/01/02, v1.0
 * @since 1.8
 *
 */
public class Global {
	
	public static final String FAILED  = "Fail";
	public static final String SUCCESS = "Success";
	public static final String UNKNOW  = "Unknown";
	public static final String GPRS = "Gprs";
	public static final String ETHERNET = "Ethernet";
	
	public static final byte FRAME_HEAD_1 = (byte) 0xAA;
	public static final byte FRAME_HEAD_DOWN = (byte)0xEF;
	public static final byte FRAME_HEAD_UP = (byte) 0xFF;
	public static final byte PROTOCAL_TYPE = 0x01;
	public static final byte PROJECT_VERSION = 0x01;
	public static final byte FRAME_TAIL_1 = (byte) 0xDD;
	public static final byte FRAME_TAIL_2 = (byte) 0xEE;
	public static final String ZIP_HEAD ="0.1.";
	
}
