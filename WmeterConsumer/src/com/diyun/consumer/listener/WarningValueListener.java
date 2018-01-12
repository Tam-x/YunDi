package com.diyun.consumer.listener;

/**
 * 
 * Listen warning data.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public interface WarningValueListener {
	
	public void deliveryBatteryData(String ip, String dataTime, int status, String generateTime, int type, int alarm,
			float value, int reserve);
	
	public void deliveryMagneticData(String ip, String dataTime, int status, String generateTime, int meterType, int alarmType,
			int reserve);
	
	public void deliveryOtherAlarmData(String ip, String dataTime, int status, String generateTime, int meterType, int alarmType,
			int reserve);

}
