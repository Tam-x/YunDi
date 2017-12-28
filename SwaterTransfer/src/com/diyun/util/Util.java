package com.diyun.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.diyun.enums.LogLevel;

/**
 * Providing utility methods that can be used for programming.
 * 
 * @author Tx.Loooper
 * @version 2017-12-26 V1.0
 * @since 1.6
 */
public class Util {
	
	private static int logLevel = 1;
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * Set log level
	 * @param level Log level.
	 */
	public static void setLogLevel(int level){
		logLevel = level;
	}
	
	/**
	 * Custom log.
	 * @param tag Class name.
	 * @param info Message.
	 * @param level Log level.
	 */
	public static void log(String tag, String info, LogLevel level){
		if(logLevel == 0)return;
		Date date = new Date(System.currentTimeMillis());
		if(logLevel >= level.getLevel())System.out.println("["+formatter.format(date)+"] " +"["+tag+"]"+info);
	}
	
}
