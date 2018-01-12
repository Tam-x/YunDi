package com.diyun.consumer.bean;

/**
 * 
 * Real time data.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class RealtimeValue extends BaseValue{
	private int status;
	private int type;
	private float value;
	private String generateTime;
	
	public RealtimeValue(String ip,String dataTime, int status, String generateTime, int type, float value) {
		super();
		this.deviceIp = ip;
		this.dataTime = dataTime;
		this.status = status;
		this.generateTime = generateTime;
		this.type = type;
		this.value = value;
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}

	@Override
	public String toString() {
		return "RealtimeValue [deviceip="+deviceIp +"dataTime:"+dataTime+",status=" + status + ", generateTime=" + generateTime + ", type="
				+ type + ", value=" + value + "¡æ]";
	}
}
