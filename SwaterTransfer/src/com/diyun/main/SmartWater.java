package com.diyun.main;

import java.util.List;

import com.diyun.beans.DataDevice;
import com.diyun.beans.DataProperty;
import com.diyun.threads.RunnableCloud;
import com.diyun.util.SwaterDevice;
import com.diyun.util.SwaterProperty;

/**
 * Program main entrance.
 * 
 * @author Tx.loooper
 * @version 2017/12/27 V1.0
 * @since 1.6
 *
 */
public class SmartWater {
	
	public static void main(String[] args) {
		init();
	}
	
	/**
	 * Load device info, load property info.
	 */
	private static void init(){
		List<DataDevice> devices = SwaterDevice.loadDeviceInfo();
		DataProperty property = SwaterProperty.loadProperties();
		if(property != null){
			property.getcloudAddress();
			property.getCloudPort();
			property.getMqttBroker();
			for(DataDevice device: devices)
				new Thread(new RunnableCloud(device.getIMEI(), property.getcloudAddress(), property.getCloudPort())).start();
		}
	}
}
