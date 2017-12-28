package com.diyun.main;

import java.util.List;
import com.diyun.beans.DataDevice;
import com.diyun.beans.DataProperty;
import com.diyun.enums.LogLevel;
import com.diyun.listener.HouseIDListener;
import com.diyun.mqtt.Consumer;
import com.diyun.mqtt.Topics;
import com.diyun.threads.RunnableCloud;
import com.diyun.threads.ThreadSend;
import com.diyun.threads.ThreadReceive;
import com.diyun.util.SwaterDevice;
import com.diyun.util.SwaterProperty;
import com.diyun.util.Util;
import com.diyun.util.Global;
import com.diyun.util.MessageQueue;

/**
 * Program main entrance.
 * 
 * @author Tx.loooper
 * @version 2017/12/27 V1.0
 * @since 1.6
 *
 */
public class SmartWater  implements HouseIDListener{
	
	private static final String TAG = "SmartWater";
	private static Consumer consumer;
	private static List<DataDevice> devices;
	private static DataProperty property;
	
	public static void main(String[] args) {
		init();
		start();
	}
	
	/**
	 * Load device info, load property info.
	 */
	private static void init(){
		devices = SwaterDevice.loadDeviceInfo();
		property = SwaterProperty.loadProperties();
		for(DataDevice device: devices){
			Global.IMEIS.put(device.getDtuID(), device.getIMEI());
		}
		consumer = new Consumer(property.getMqttBroker());	
		
	}
	
	public static void start(){
		consumer.setWorkMode(Topics.MODE_CUSTOMER_RELEASE);		
		consumer.setHouseListener(new SmartWater());
		consumer.open();
	}	
	
	private synchronized void connectLoop(int key){
		if((property == null)){
			Util.log(TAG, "There is nothing in swater.properts file", LogLevel.SYS);
			return;
		}
		String imei = Global.IMEIS.get(key);
		
		if(null == imei){
			Util.log(TAG, "Device's imei is not exist.", LogLevel.SYS);
			return;
		}	
		ThreadSend send= new ThreadSend();
		ThreadReceive watch = new ThreadReceive();	
		RunnableCloud cloud = new RunnableCloud(imei, property.getcloudAddress(), property.getCloudPort());
		MessageQueue queue = new MessageQueue();
		cloud.setQueue(queue);
		Consumer.mQueues.put(key,queue);
		send.setCloud(cloud);
		watch.setCloud(cloud);
		cloud.connect();
		startwork(send,watch);
	}		
		
	private static void startwork(ThreadSend send, ThreadReceive watch ){	
		if(send != null){
			send.start();
		}
		if(watch != null){
			watch.start();
		}		
	}

	@Override
	public void startLoop(int key) {
		connectLoop(key);
	}
}
