package com.diyun.consumer.listener;

/**
 * 
 * Listen real time data.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public interface RealTimeValueListener {
	/**
	 * 
	 * Handle real time data here.
	 * 
	 * @param ip
	 * @param dataTime
	 * @param status 0-normal ,1-Reissue the data correctly, 2-Reissue the data failed.
	 * @param generateTime
	 * @param type
	 * @param value Water meter value.
	 */
	public void deliveryRealtimeData(String ip,String dataTime, int status,
			String generateTime, int type, float value);
}
