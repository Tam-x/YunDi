package com.diyun.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.diyun.bean.LightInfo;
import com.diyun.bean.PmInfo;
import com.diyun.config.Config;
import com.diyun.listener.OnReceiveListener;
import com.diyun.util.Frame;
import com.diyun.util.Tool;
import json.JSONException;
import json.JSONObject;

/**
 * MQTT.
 * @author Tx.Loooper
 *
 */
public class Mqtt implements OnReceiveListener {

	private MqttClient client;
	private MqttConnectOptions options;
	private ScheduledExecutorService scheduler;
	private OnReceiveListener mqttListener;

	public Mqtt() {

	}

	public void setListener(OnReceiveListener listener) {
		mqttListener = listener;
	}

	public void open() {
		try {
			String clientID = Config.CLIENT_ID;
			client = new MqttClient(Config.MQTT_BROKER, clientID,
					new MemoryPersistence());
			options = new MqttConnectOptions();
			options.setUserName(Config.MQTT_USER);                
            options.setPassword(Config.MQTT_PWD.toCharArray());
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
			client.setCallback(new PushCallback());		
			client.connect(options);
			Tool.myLog("Mqtt.open()", "Mqtt has connected proxy '"+Config.MQTT_BROKER+"' and ID is'"+clientID+"', subcribe topic '"+Config.getMqttTopic()[0]+"'.");
			client.subscribe(Config.getMqttTopic(), Config.getMqttQos());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	private void reconnect() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				if (!client.isConnected()) {
					try {
						client.connect(options);
						client.subscribe(Config.getMqttTopic(),
								Config.getMqttQos());
					} catch (MqttSecurityException e) {
						e.printStackTrace();
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}
			}
		}, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
	}

	private class PushCallback implements MqttCallback {

		public void connectionLost(Throwable arg0) {
			reconnect();
		}

		public void deliveryComplete(IMqttDeliveryToken arg0) {

		}

		public void messageArrived(String topic, MqttMessage msg)
				throws Exception {
			if (msg == null)
				return;
			byte[] payload = msg.getPayload();
			parseCommands(payload);
		}

	}

	/**
	 * Interface method for cloud data entry.
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * @param msg message from cloud (JSON string).
	 * 
	 * <p>
	 * A listener is listening server commands all the time, 
	 * once commands coming will be here.
	 */
	public void onReceiveData(String msg) {	
		separateCloudMsg(msg);
	}
	
	/**
	 * Differentiating the descending order (lamp control and environment control).
	 * 
	 * @author TX.Loooper
	 * @version V1.0, 2017-12-15
	 * @since JDK8
	 * @param msg message from cloud (JSON string).
	 * 
	 * <p>
	 * The control command contains character 'EN' must be a environment command,
	 * the lamp control command may contain character 'LN'.
	 */
	public void separateCloudMsg(String msg){
		if(msg.contains("EN")){
			handleMsgEnv(msg);
		}else{
			handleMsgLamp(msg);
		}
	}
	
	/**
	 * Handle the lamp control commands from the cloud.
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * @param msg message(JSON string) from cloud.
	 * 
	 * <p>
	 * The control command contains character 'power' must be a lamp switch command
	 * and a lamp light command contains character 'bright'.
	 */
	private void handleMsgLamp(String msg){
		if(msg.contains("power")){
			handleLampPower(msg);
		}else if(msg.contains("bright")){
			handleLampLight(msg);
		}		
	}
	
	/**
	 * Handle the environment control commands from the cloud.
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * @param msg message from cloud(JSON string).
	 * 
	 */
	private void handleMsgEnv(String msg){
		JSONObject jsonObj = new JSONObject(msg);
		JSONObject bodyObj = jsonObj.getJSONObject("zcmd");
		if(bodyObj.get("id") instanceof String){		
			Tool.myLog("handleMsgEnv", "query env data.");
			String id = (String) bodyObj.get("id");
			String z = id.substring(6);
			byte[] b = Tool.hexStringToBytes(z);
			int ip = b[0] & 0xFF << 8 | b[1] & 0xFF;
			byte[] cmd = Frame.getDownCmd(Config.DTU_IPS[0], 0xA3,
					String.valueOf(ip), 1, 0);
			Tool.myLog("downtopic",Config.MQ_TOPIC_DOWN_HEAD+ Config.DTU_IPS[0]);
			try {
				client.publish(Config.MQ_TOPIC_DOWN_HEAD
						+ Config.DTU_IPS[0], cmd, 0, false);
			} catch (MqttPersistenceException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Handle the lamp light commands from the cloud.
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * @param msg message(JSON string) from cloud.
	 * 
	 * <p>
	 * The message will be encapsulated as a communication protocol designed by nix.long 
	 * and will be published by MQTT.
	 */
	private void handleLampLight(String msg){
		try {
			JSONObject jsonObj = new JSONObject(msg);
			JSONObject bodyObj = jsonObj.getJSONObject("zcmd");
			int bright = (Integer) bodyObj.get("bright");
			if (msg.contains("id")) {
				String[] ids = null;
				if(bodyObj.get("id") instanceof String){
					ids = new String[]{(String) bodyObj.get("id")};
					Tool.myLog("handleLampLight", "Only handling one lamp.");
				}else{
					ids = (String[]) bodyObj.get("id");
					Tool.myLog("handleLampLight", "Handling many lamps.");
				}				
				for(String id: ids){
					String z = id.substring(6);
					byte[] b = Tool.hexStringToBytes(z);
					int ip = b[0] & 0xFF << 8 | b[1] & 0xFF;
					byte[] cmd = Frame.getDownCmd(Config.DTU_IPS[0], 0xA1,
							String.valueOf(ip), 2, bright);
					Tool.myLog("downtopic",Config.MQ_TOPIC_DOWN_HEAD+ Config.DTU_IPS[0]);
					try {
						client.publish(Config.MQ_TOPIC_DOWN_HEAD
								+ Config.DTU_IPS[0], cmd, 0, false);
					} catch (MqttPersistenceException e) {
						e.printStackTrace();
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}				
			} else {
				byte[] cmd = Frame.getDownCmd(Config.DTU_IPS[0], 0xA1, "65535", 2,
							bright);
				try {
					client.publish(Config.MQ_TOPIC_DOWN_HEAD
							+ Config.DTU_IPS[0], cmd, 0, false);
				} catch (MqttPersistenceException e) {
					e.printStackTrace();
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Handle the lamp switch commands from the cloud.
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * @param msg message(JSON string) from cloud.
	 * 
	 * <br></br>
	 * The message will be encapsulated as a communication protocol designed by nix.long 
	 * and will be published by MQTT.
	 */
	private void handleLampPower(String msg){
		try {
			JSONObject jsonObj = new JSONObject(msg);
			JSONObject bodyObj = jsonObj.getJSONObject("zcmd");
			boolean isOpen = bodyObj.getBoolean("power");
			int status = 0;
			if (isOpen) {
				status = 50;
			}
			if (msg.contains("id")) {
				String[] ids = null;
				if(bodyObj.get("id") instanceof String){
					ids = new String[]{(String) bodyObj.get("id")};
					Tool.myLog("handleLampPower", "Only handling one lamp.");
				}else{
					ids = (String[]) bodyObj.get("id");
					Tool.myLog("handleLampPower", "Handling many lamps.");
				}			
				for(String id: ids){
					String z = id.substring(6);
					byte[] b = Tool.hexStringToBytes(z);
					int ip = b[0] & 0xFF << 8 | b[1] & 0xFF;
					byte[] cmd = Frame.getDownCmd(Config.DTU_IPS[0], 0xA1,
							String.valueOf(ip), 2, status);
					Tool.myLog("downtopic",Config.MQ_TOPIC_DOWN_HEAD+ Config.DTU_IPS[0]);
					try {
						client.publish(Config.MQ_TOPIC_DOWN_HEAD
								+ Config.DTU_IPS[0], cmd, 0, false);
					} catch (MqttPersistenceException e) {
						e.printStackTrace();
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}				
			} else {
				byte[] cmd;
				if (isOpen) {
					Tool.myLog("Mqtt.handleLampPower()", "Open all lamps.");
					cmd = Frame.getDownCmd(Config.DTU_IPS[0], 0xA1, "65535", 2,
							50);
				} else {
					Tool.myLog("Mqtt.handleLampPower()", "Close all lamps.");
					cmd = Frame.getDownCmd(Config.DTU_IPS[0], 0xA1, "65535", 2,
							0);
				}
				try {
					client.publish(Config.MQ_TOPIC_DOWN_HEAD
							+ Config.DTU_IPS[0], cmd, 0, false);
					Tool.myLog("Mqtt.handleLampPower()", "Query all lamps's status.");
					client.publish(Config.MQ_TOPIC_DOWN_HEAD
							+ Config.DTU_IPS[0], Frame.getDownCmd(
							Config.DTU_IPS[0], 0xA2, "65535",
							1, 0), 0, false);
				} catch (MqttPersistenceException e) {
					e.printStackTrace();
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Convert byte data to JSON data. 
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * @param data a message from MQTT.
	 * 
	 * <p>
	 * Parse the data and make it be a JSON string,
	 * Then send it to interface method for MQTT data entry.
	 */
	private void parseCommands(byte[] payload){
		if(payload.length <22){
			return;
		}
		// TODO CRC8-check:
		int type = payload[8] & 0xFF;
		switch (type) {
			case 0xA2:
				LightInfo lightInfo = Frame.parseLightInfo(payload);
				mqttListener.onReceiveData(lightInfo.toString());
				break;
			case 0xA3:
				PmInfo pmInfo = Frame.parsePMInfo(payload);
				mqttListener.onReceiveData(pmInfo.toString());
				break;
			default:
				break;
		}
	}
}
