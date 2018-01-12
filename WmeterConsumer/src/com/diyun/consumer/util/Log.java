package com.diyun.consumer.util;

/**
 * 
 * Control log printing.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class Log {
	private static boolean lowDebug = false;
	private static boolean highDebug = false;	
	
	public static void setLog(boolean low_log, boolean high_log){
		lowDebug = low_log;
		highDebug = high_log;
	}
	
	public static void logh(String Tag, String log){
		if(highDebug)
			System.out.println(Tag +":"+ log);
	}
	
	public static void logw(String Tag, String log){
		if(lowDebug){
			System.out.println(Tag + ":" + log);
		}
	}
}
