package com.diyun.bean;

import java.io.Serializable;

/**
 * Lamp data.
 * 
 * @author Tx.Loooper
 * @version 2018/01/02, v1.0
 * @since 1.8
 * 
 */
public class LightInfo implements Serializable{
	
	private static final long serialVersionUID = 142542054357L;
	private String zigbeeAdr;
	private int channel;
	private int status;
	private float press;
	private float flow;
	private float active;
	private float reactive;
	private float hz;
	
	public LightInfo(String zigbeeAdr, int channel, int status, float press,
			float flow, float active, float reactive, float hz) {
		super();
		this.zigbeeAdr = zigbeeAdr;
		this.channel = channel;
		this.status = status;
		this.press = press;
		this.flow = flow;
		this.active = active;
		this.reactive = reactive;
		this.hz = hz;
	}
	public String getZigbeeAdr() {
		return zigbeeAdr;
	}
	public void setZigbeeAdr(String zigbeeAdr) {
		this.zigbeeAdr = zigbeeAdr;
	}
	public int getchannel() {
		return channel;
	}
	public void setchannel(int channel) {
		this.channel = channel;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getPress() {
		return press;
	}
	public void setPress(float press) {
		this.press = press;
	}
	public float getFlow() {
		return flow;
	}
	public void setFlow(float flow) {
		this.flow = flow;
	}
	public float getActive() {
		return active;
	}
	public void setActive(float active) {
		this.active = active;
	}
	public float getReactive() {
		return reactive;
	}
	public void setReactive(float reactive) {
		this.reactive = reactive;
	}
	public float getHz() {
		return hz;
	}
	public void setHz(float hz) {
		this.hz = hz;
	}
	
	private boolean getStatus(int status){
		return status == 0 ? false : true;
	}
	
	@Override
	public String toString() {
      String result = "";
	      result += "{\"zdata\":{";
	      result += "\"id\":\"" + "LN"+zigbeeAdr+ "\"";
	      result += ",\"type\":\"" + "LN"+ "\"";
	      result += ",\"power\":" + getStatus(status);
	      result += ",\"bright\":" + status;
	      result += ",\"I\":" + flow;
	      result += ",\"V\":" + press;
	      result += ",\"AW\":" + active;
	      result += ",\"RW\":" + reactive;
	      result += ",\"T\":" + 0;
	      result += ",\"F\":" + hz;
	      result += ",\"envbright\":" + 0;
	      result += ",\"time\":\"" + System.currentTimeMillis() + "\"";
	      result += "}}";
      return result;
	}
	
}
