package com.diyun.consumer.bean;

/**
 * 
 * Battery data.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class BatteryValue extends BaseValue{
	private int status;
	private String generateTime;
	private int type;
	private int alarm;
	private float value;
	private int reserve;
	
	public BatteryValue(String ip, String dataTime, int status, String generateTime, int type, int alarm,
			float value, int reserve) {
		super();
		this.deviceIp = ip;
		this.dataTime = dataTime;
		this.status = status;
		this.generateTime = generateTime;
		this.type = type;
		this.alarm = alarm;
		this.value = value;
		this.reserve = reserve;
	}
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getGenerateTime() {
		return generateTime;
	}


	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getAlarm() {
		return alarm;
	}


	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}


	public float getValue() {
		return value;
	}


	public void setValue(float value) {
		this.value = value;
	}


	public int getReserve() {
		return reserve;
	}


	public void setReserve(int reserve) {
		this.reserve = reserve;
	}


	@Override
	public String toString() {
		return "BatteryValue [ip = "+deviceIp+",dataTime="+dataTime+",status=" + status + ", generateTime=" + generateTime + ", type="
				+ type + ", alarm=" + alarm + ", value=" + value + "V, reserve="
				+ reserve + "]";
	}	
}
