package com.diyun.beans;

public class DTUDataBean {
	public byte[] message;
	public int type;
	public int deviceIp;
	
	public DTUDataBean() {
		super();
	}
	
	public DTUDataBean(byte[] message, int type, int ip) {
		super();
		this.message = message;
		this.type = type;
		this.deviceIp = ip;
	}

	public byte[] getMessage() {
		return message;
	}
	public void setMessage(byte[] message) {
		this.message = message;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(int ip) {
		this.deviceIp = ip;
	}
	
}
