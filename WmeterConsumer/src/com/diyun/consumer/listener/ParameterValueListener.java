package com.diyun.consumer.listener;

/**
 * 
 * Listen parameter data.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public interface ParameterValueListener {
	/**
	 * Handle parameter data here.
	 * 
	 * @param ip
	 * @param dataTime Data generation time. 
	 * @param type Device type.
	 * @param freezeTime
	 * @param wakeupTime Wake up time.
	 * @param server
	 * @param port
	 * @param meterNumber
	 * @param reserver
	 */
	public void deliveryParameterData(String ip, String dataTime, int type, String freezeTime, String wakeupTime,
			String server, int port, int meterNumber, int reserver);
}
