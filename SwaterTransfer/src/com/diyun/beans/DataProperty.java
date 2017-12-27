package com.diyun.beans;

/**
 * Configuration file encapsulation.
 * 
 * @author Tx.Loooper
 * @version 2017-12-26 V1.0
 * @since 1.6
 *
 */
public class DataProperty {
	
	private String mqttBroker;
	private String cloudAddress;
	private int cloudPort;
	
	public DataProperty() {
		super();
	}

	public DataProperty(String mqttBroker, String cloudAddress, int cloudPort) {
		super();
		this.mqttBroker = mqttBroker;
		this.cloudAddress = cloudAddress;
		this.cloudPort = cloudPort;
	}

	public String getMqttBroker() {
		return mqttBroker;
	}
	
	public void setMqttBroker(String mqttBroker) {
		this.mqttBroker = mqttBroker;
	}
	
	public String getcloudAddress() {
		return cloudAddress;
	}
	
	public void setcloudAddress(String cloudAddress) {
		this.cloudAddress = cloudAddress;
	}
	public int getCloudPort() {
		return cloudPort;
	}
	
	public void setCloudPort(int cloudPort) {
		this.cloudPort = cloudPort;
	}
	
}
