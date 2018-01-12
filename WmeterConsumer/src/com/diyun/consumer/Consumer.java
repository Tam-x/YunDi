package com.diyun.consumer;

import java.util.Random;
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
import com.diyun.consumer.bean.BaseValue;
import com.diyun.consumer.bean.BatteryValue;
import com.diyun.consumer.bean.MagneticValue;
import com.diyun.consumer.bean.OtherAlarm;
import com.diyun.consumer.bean.ParameterValue;
import com.diyun.consumer.bean.RealtimeValue;
import com.diyun.consumer.listener.ParameterValueListener;
import com.diyun.consumer.listener.RealTimeValueListener;
import com.diyun.consumer.listener.WarningValueListener;
import com.diyun.consumer.parser.Frame;
import com.diyun.consumer.util.CRC8;
import com.diyun.consumer.util.Log;
import com.diyun.consumer.util.Tool;
import com.diyun.consumer.util.Topic;

/**
 * 
 * MQTT consumer.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class Consumer {
	private static final String TAG = "Consumer";
	private MqttClient client;
	private MqttConnectOptions options;
	private ScheduledExecutorService scheduler;
	private ParameterValueListener parameterValueListener;
	private RealTimeValueListener realTimeValueListener;
	private WarningValueListener warningValueListener;

	private static String mUniqueId = "nbiot@wmeter";
	private String user = "";

	public Consumer() {
		
	}

	/**
	 * Set your own user name here.
	 * @param id User name
	 */
	public void setUniqueId(String id) {
		mUniqueId = id;
	}
	
	/**
	 * Set project's log.
	 * @param low If true, low level log will show.
	 * @param high If true, high level log will show.
	 */
	public void setLog(boolean low, boolean high) {
		Log.setLog(low, high);
	}

	public void registerParameterValueListener(ParameterValueListener callback) {
		parameterValueListener = callback;
	}

	public void registerRealTimeValueListener(RealTimeValueListener callback) {
		realTimeValueListener = callback;
	}

	public void registerWarningValueListener(WarningValueListener callback) {
		warningValueListener = callback;
	}

	/**
	 * Open connect.
	 * 
	 */
	public void open() {
		try {
			user = Config.CLIENT_ID() + "[" + Config.VERSION + "/" + "]-" + mUniqueId + "-"
					+ Tool.formatDateString() + "/" + new Random().nextInt(1000);
			client = new MqttClient(Config.BROKER, user, new MemoryPersistence());
			options = new MqttConnectOptions();
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(60);
			client.setCallback(new PushCallback());
			client.connect(options);
			toSubscribe(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Subscribe data from MQTT.
	 */
	private void toSubscribe(MqttClient client) throws Exception {
		String[] topics = Topic.getSubscribeTopics();
		if (topics.length == 0)
			throw new Exception("YOU MUST SPECIFY TOPICS TO GET REMOTE MESSAGES!!! FORGOT TO LOAD IPs?");
		Log.logh(TAG, "subscribe below topic:");
		for (int i = 0; i < topics.length; i++) {
			System.out.println(topics[i]);
		}
		int[] Qos = new int[topics.length];
		for (int i = 0; i < Qos.length; i++) {
			Qos[i] = 0;
		}
		client.subscribe(topics, Qos);
		Log.logh(TAG, "client subscribe topics success, waiting data...");
	}	
	
	/**
	 * 
	 * Publish order to request a kind of type data.
	 * 
	 * @param ipString
	 * @param requestType
	 * @param year
	 * @param month
	 * @param day
	 * @throws Exception
	 */
	public void pub(String ipString, int requestType, int year, int month, int day) throws Exception {
		if (year < 0 || String.valueOf(year).length() != 4) {
			throw new Exception("Input wrong year!");
		}
		if (month < 1 || month > 12) {
			throw new Exception("Input wrong month!");
		}
		if (day < 1 || day > 32) {
			throw new Exception("Input wrong day!");
		}
		if (requestType < 1 || requestType > 6) {
			throw new Exception("Input wrong requestType!");
		}

		String m = String.valueOf(month);
		String d = String.valueOf(day);
		String date = String.valueOf(year) + (m.length() == 1 ? "0" + m : m) + (d.length() == 1 ? "0" + d : d);
		byte[] payload = getDownCmd(ipString, date, requestType);
		try {
			client.publish(Topic.MQTT_DOWN_TOPIC, payload, 0, false);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Get down command bytes.
	 * 
	 * @param ipString
	 * @param date
	 * @param cmdType
	 * @return order
	 */
	private byte[] getDownCmd(String ipString, String date, int cmdType) {
		String data = "ABEF01" + Tool.ipStringToHexString(ipString) + "668000" + Tool.formatDateString() + "05000"
				+ cmdType + date;
		Log.logh(TAG, "down data:"+data);
		byte[] cmd = Tool.hexStringToByteArray(data);
		byte crc = CRC8.calcCrc8(cmd);
		byte[] send = new byte[cmd.length + 3];
		System.arraycopy(cmd, 0, send, 0, cmd.length);
		send[send.length - 3] = crc;
		send[send.length - 2] = (byte) 0xDD;
		send[send.length - 1] = (byte) 0xEE;
		return send;
	}

	/**
	 * Close MQTT.
	 */
	public void close() {
		try {
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reconnect MQTT.
	 */
	private void reconnect() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				if (!client.isConnected()) {
					try {
						client.connect(options);
						toSubscribe(client);
					} catch (MqttSecurityException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 5 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
	}

	/**
	 * A thread to parse frame from MQTT.
	 *
	 */
	private class ParseThread extends Thread {
		private byte[] data;
		private PushCallback mConsumer;

		public ParseThread(PushCallback con, byte[] payload) {
			this.mConsumer = con;
			this.data = payload;
		}

		public void run() {
			BaseValue elec = Frame.parseFrame(data);
			mConsumer.handleWmeterMessage(elec, data);
		}
	}

	/**
	 * MQTT call back.
	 *
	 */
	private class PushCallback implements MqttCallback {

		@Override
		public void connectionLost(Throwable throwable) {
			throwable.printStackTrace();
			reconnect();
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0) {

		}

		@Override
		public void messageArrived(String topic, MqttMessage msg) throws Exception {
			if (msg == null)
				return;
			byte[] payload = msg.getPayload();
			if (Frame.checkFrame(payload)) {
				Log.logh(TAG, "check passed[" + Tool.formatIp(payload[3], payload[4], payload[5], payload[6])
						+ "]- topic: " + topic + ", " + Tool.BytesToHexStringEx(payload, 0, payload.length));
				new ParseThread(this, payload).start();
			}

		}

		/**
		 * 
		 * Handle water meter data.
		 * 
		 * @param data
		 * @param Frame
		 */
		protected void handleWmeterMessage(BaseValue data, byte[] Frame) {
			if (data == null)
				return;
			switch (Frame[8] & 0xFF) {
			case BaseValue.REAL_TIME_VALUE:
				RealtimeValue realtime = (RealtimeValue) data;
				if (realTimeValueListener != null) {
					realTimeValueListener.deliveryRealtimeData(realtime.getDeviceIp(), realtime.getDataTime(),
							realtime.getStatus(), realtime.getGenerateTime(), realtime.getType(), realtime.getValue());
				}
				break;
			case BaseValue.BATTERY_ALARM:
				if (warningValueListener != null) {
					BatteryValue battey = (BatteryValue) data;
					warningValueListener.deliveryBatteryData(battey.getDeviceIp(), battey.getDataTime(),
							battey.getStatus(), battey.getGenerateTime(), battey.getType(), battey.getAlarm(),
							battey.getValue(), battey.getReserve());
				}
				break;
			case BaseValue.OTHER_ALARM:
				if (warningValueListener != null) {
					OtherAlarm alarm = (OtherAlarm) data;
					warningValueListener.deliveryOtherAlarmData(alarm.getDeviceIp(), alarm.getDataTime(),
							alarm.getStatus(), alarm.getGenerateTime(), alarm.getMeterType(), alarm.getAlarmType(),
							alarm.getReserve());
				}
				break;
			case BaseValue.MAGNETIC_VALUE:
				if (warningValueListener != null) {
					MagneticValue magnetic = (MagneticValue) data;
					warningValueListener.deliveryMagneticData(magnetic.getDeviceIp(), magnetic.getDataTime(),
							magnetic.getStatus(), magnetic.getGenerateTime(), magnetic.getMeterType(),
							magnetic.getAlarmType(), magnetic.getReserve());
				}
				break;
			case BaseValue.PARAMETER_VALUE:
				if (parameterValueListener != null) {
					ParameterValue parameter = (ParameterValue) data;
					parameterValueListener.deliveryParameterData(parameter.getDeviceIp(), parameter.getDataTime(),
							parameter.getType(), parameter.getFreezeTime(), parameter.getWakeupTime(),
							parameter.getServer(), parameter.getPort(), parameter.getMeterNumber(),
							parameter.getReserver());
				}
				break;
			}
		}
	}
}
