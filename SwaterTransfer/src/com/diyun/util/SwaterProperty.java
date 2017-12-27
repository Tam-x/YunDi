package com.diyun.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.diyun.beans.DataProperty;
import com.diyun.enums.LogLevel;

/**
 * Loading smart water's property from swater.properties file.
 * 
 * @author Tx.Loooper
 * @version 2017-12-26 V1.0
 * @since 1.6
 *
 */
public class SwaterProperty {
	
	private static final String TAG = "SwaterProperty";
	
	/**
	 * load the property.
	 */
	public static DataProperty loadProperties(){
		DataProperty bean = new DataProperty();
		try {
			Properties property = new Properties(); 
			InputStream inputStream = SwaterProperty.class.getClassLoader().getResourceAsStream("swater.properties");
			property.load(inputStream);			  
			String mqttBroker = property.getProperty("mqttBroker");
			String cloudAddress = property.getProperty("cloudAddress");
			String cloudPort = property.getProperty("cloudPort");
			String logLevel = property.getProperty("logLevel");
			int port = Integer.parseInt(cloudPort);
			int level = Integer.parseInt(logLevel);
			Util.log(TAG, "Loading properties... \nLogLv->"+level+";Mqtt->"+mqttBroker+";Cloud->"+cloudAddress +":"+cloudPort+".", LogLevel.SYS);
			bean.setcloudAddress(cloudAddress);		
			bean.setCloudPort(port);
			bean.setMqttBroker(mqttBroker);
			Util.setLogLevel(level);
		} catch (FileNotFoundException e) {
			bean = null;
			e.printStackTrace();
		} catch (IOException e) {
			bean = null;
			e.printStackTrace();
		} 
		return bean;
	}
	
}
