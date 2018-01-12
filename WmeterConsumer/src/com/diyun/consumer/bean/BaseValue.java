package com.diyun.consumer.bean;

/**
 * 
 * The base class of water meter data.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class BaseValue {	
	public static final int REAL_TIME_VALUE = 0x01;
	public static final int BATTERY_ALARM = 0x02;
	public static final int MAGNETIC_VALUE = 0x03;
	public static final int OTHER_ALARM = 0x04;
	public static final int PARAMETER_VALUE = 0x05;
	
	public String deviceIp;
	public String dataTime;
	
	public String getDeviceIp() {
		return deviceIp;
	}
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}	
}
