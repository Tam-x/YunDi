package com.diyun.consumer.bean;

/**
 * 
 * Parameter data.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class ParameterValue extends BaseValue{
	private int type;
	private String freezeTime;
	private String wakeupTime;
	private String server;
	private int port;
	private int meterNumber;
	private int reserver;	
	
	public ParameterValue(String ip, String dataTime, int type, String freezeTime, String wakeupTime,
			String server, int port, int meterNumber, int reserver) {
		super();
		this.deviceIp = ip;
		this.dataTime = dataTime;
		this.type = type;
		this.freezeTime = freezeTime;
		this.wakeupTime = wakeupTime;
		this.server = server;
		this.port = port;
		this.meterNumber = meterNumber;
		this.reserver = reserver;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(String freezeTime) {
		this.freezeTime = freezeTime;
	}

	public String getWakeupTime() {
		return wakeupTime;
	}

	public void setWakeupTime(String wakeupTime) {
		this.wakeupTime = wakeupTime;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(int meterNumber) {
		this.meterNumber = meterNumber;
	}

	public int getReserver() {
		return reserver;
	}

	public void setReserver(int reserver) {
		this.reserver = reserver;
	}

	@Override
	public String toString() {
		return "ParameterValue [ip="+deviceIp+",dataTime ="+dataTime+",type=" + type + ", freezeTime=" + freezeTime
				+ ", wakeupTime=" + wakeupTime + ", server=" + server + ", port="
				+ port + ", meterNumber=" + meterNumber + ", reserver="
				+ reserver + "]";
	}
	
}
