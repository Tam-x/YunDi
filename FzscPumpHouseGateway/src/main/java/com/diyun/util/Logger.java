package com.diyun.util;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author t.x
 * <br>2018/03/28<br>
 *
 */
public class Logger {
	private static final String TAG = "Logger";
	private static boolean allowLog = true;
	private static boolean allowLogFile = true;
	private static Map<String, FileWriter> fmap = new HashMap<String, FileWriter>();
	
	public static void setPrintLog(boolean allowLog){
		Logger.allowLog = allowLog;
	}
	
	public static void setLogFile(boolean allowLogFile){
		Logger.allowLogFile = allowLogFile;
	}
	
	public static void log(String tag, String info){
		if(Logger.allowLog){
			System.out.println("["+tag+"]"+info);
		}
	}
	
	public static void print(String tag, String info){
			System.out.println("["+tag+"]"+info);
	}
	
	public static void logf(String addr, String mes){
		if(!Logger.allowLogFile)
			return ;
		FileWriter fw = fmap.get(addr);
		if(fw == null){
			try{
				fw = new FileWriter("ip-" + addr +".txt", true);
				fw.write("\n --------------------"+Utility.getNowTimeString()+"--------------------\n");
				fmap.put(addr, fw);
			} catch(Exception e){
				Logger.print(TAG, e.toString());
			}			
		}
		if((fw = fmap.get(addr) ) != null){
			try{
				fw.write(mes+"\n");
				fw.flush();
			} catch(Exception e){
				Logger.print(TAG, e.toString());
			}
		}
	}
}
