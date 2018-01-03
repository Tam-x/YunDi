package com.diyun.code;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.diyun.config.Config;
import com.diyun.db.SqliteHelper;
import com.diyun.tool.Util;

public class Publisher {
	private MqttClient sampleClient = null;
	private MqttConnectOptions connOpts = null;
	private SqliteHelper db = null; 
	private static int count = 1;

	public Publisher(SqliteHelper db) {
		super();
		this.db = db;
		connect();
	}

	private void connect() {
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			sampleClient = new MqttClient(Config.MQTT_BROKER,
					"nbiot-wmeter-transfer-"+Config.VERSION+"-" + new Random().nextInt(1000),
					persistence);
			connOpts = new MqttConnectOptions();
			connOpts.setServerURIs(new String[] { Config.MQTT_BROKER });
			connOpts.setCleanSession(false);
			connOpts.setKeepAliveInterval(100);
			final String[] topicFilters=new String[]{Config.MQTT_DOWN_TOPIC};
            final int[]qos={1};						
			sampleClient.setCallback(new MqttCallback() {
				public void connectionLost(Throwable throwable) {
					System.out.println("mqtt connection lost");
					throwable.printStackTrace();
					while (!sampleClient.isConnected()) {
						try {
							Thread.sleep(1000 * 2);
							sampleClient.connect(connOpts);
							sampleClient.subscribe(topicFilters,qos);
						} catch (MqttException e) {
							e.printStackTrace();
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				public void messageArrived(String topic, MqttMessage mqttMessage)
						throws Exception {
					byte[] data = mqttMessage.getPayload();
					byte[] buf = new byte[data.length-3];
					int len = data.length;
					if(len > 18){
						System.arraycopy(data, 0, buf,0, buf.length);
						if(Util.checkCRC8(buf, data[data.length-3])){
							String ip = Util.formatIp(data[3], data[4], data[5], data[6]);
							String cmd = Util.bytesToHexString(data);
							String sql = "INSERT INTO "+Config.TABLE_NAME+" (IP,CMD,STATUS,TIME) " +
					         "VALUES ('"+ip+"', '"+cmd+"', 0,'"+Util.getTime()+"');"; 
							db.executeUpdate(sql);
							Util.log("cmd from server:"+cmd);
						}else{
							Util.log("publisher recive data's crc8 is wrong.");
						}				
					}else{
						Util.log("publisher recive data's length is wrong.");
					}					
				}

				public void deliveryComplete(
						IMqttDeliveryToken iMqttDeliveryToken) {
					System.out.println("deliveryComplete:"
							+ iMqttDeliveryToken.getMessageId());
				}
			});
			sampleClient.connect(connOpts);
			sampleClient.subscribe(topicFilters,qos);
			System.out.println("mqtt is running...");
		} catch (Exception me) {
			me.printStackTrace();
			System.out.println("mqtt starts failing...");
		}

	}

	public void send(byte[] data, String topic) {
		try {
			if (sampleClient.isConnected()) {
				sampleClient.publish(topic, data, 0, false);
				Util.log("publish success, topic:"+topic+",num:"+(count++));
			}
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
