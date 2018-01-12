package com.diyun.consumer.bean;

/**
 * 
 * Magnetic data.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class MagneticValue extends BaseValue{
	private int status;
	private String generateTime;
	private int meterType;
	private int alarmType;
	private int reserve;
	public MagneticValue(String ip, String dataTime, int status, String generateTime, int meterType, int alarmType,
			int reserve) {
		super();
		this.deviceIp = ip;
		this.dataTime = dataTime;
		this.status = status;
		this.generateTime = generateTime;
		this.meterType = meterType;
		this.alarmType = alarmType;
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

	public int getMeterType() {
		return meterType;
	}

	public void setMeterType(int meterType) {
		this.meterType = meterType;
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public int getReserve() {
		return reserve;
	}

	public void setReserve(int reserve) {
		this.reserve = reserve;
	}

	@Override
	public String toString() {
		return "MagneticValue [ip="+deviceIp+",dataTime= "+dataTime+",status=" + status + ", generateTime=" + generateTime
				+ ", meterType=" + meterType + ", alarmType=" + alarmType
				+ ", reserve=" + reserve + "]";
	}
	
}
