package com.diyun.beans;

import java.util.Date;

public class DataSwater {
	
	private int sIpAddr;
	private long sTimeStamp;
	private int mtype;
	
	public void setIpAddr(int addr){
		sIpAddr = addr;
	}
	
	public int getIpAddr(){
		return sIpAddr;
	}
	
	public void setTimeStamp(long stamp){
		if(stamp ==0){
			Date d = new Date(System.currentTimeMillis());
			sTimeStamp = d.getTime();
		} else{		
			sTimeStamp = stamp;
		}
	}
	
	public long getTimeStamp(){
		return sTimeStamp;
	}
	
	public void setType(int type){
		mtype = type;
	}
	
	public int getType(){
		return mtype;
	}
	
	public void logme(){};
	
	public String formatIp(){		
		return "" + new Integer(((sIpAddr >> 24) & 0xff)) + "."
				  + new Integer(((sIpAddr >> 16) & 0xff)) + "."
				  + new Integer(((sIpAddr >>  8) & 0xff)) + "."
				  + new Integer(((sIpAddr >>  0) & 0xff));		
	}
	
	public String formatTimestamp(){
		return new Date(sTimeStamp).toString();
	}
}
