package com.diyun.bean;

import java.io.Serializable;

/**
 * PM data.
 * 
 * @author Tx.Loooper
 * @version 2018/01/02, v1.0
 * @since 1.8
 * 
 */
public class PmInfo implements Serializable{

	private static final long serialVersionUID = 112585638552L;
	private String zigbeeAdr;
	private int channel;
	private float pm2_5;
	private float pm10;
	
	public PmInfo(String zigbeeAdr, int channel, float pm2_5, float pm10) {
		super();
		this.zigbeeAdr = zigbeeAdr;
		this.channel = channel;
		this.pm2_5 = pm2_5;
		this.pm10 = pm10;
	}
	public String getZigbeeAdr() {
		return zigbeeAdr;
	}
	public void setZigbeeAdr(String zigbeeAdr) {
		this.zigbeeAdr = zigbeeAdr;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public float getPm2_5() {
		return pm2_5;
	}
	public void setPm2_5(float pm2_5) {
		this.pm2_5 = pm2_5;
	}
	public float getPm10() {
		return pm10;
	}
	public void setPm10(float pm10) {
		this.pm10 = pm10;
	}
	
	@Override
	public String toString() {		
		  String result = "";
	      result += "{\"zdata\":{";
	      result += "\"id\":\"" + "EN"+zigbeeAdr+ "\"";
	      result += ",\"type\":\"" + "EN"+ "\"";
	      result += ",\"pm25\":\"" + pm2_5 + "\"";
	      result += ",\"pm10\":\"" + pm10 + "\"";	    
	      result += ",\"time\":\"" + System.currentTimeMillis() + "\"";
	      result += "}}";
	      return result.toString();
	}
	
}
