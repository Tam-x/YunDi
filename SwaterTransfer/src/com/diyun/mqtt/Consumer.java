package com.diyun.mqtt;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.diyun.beans.DTUDataBean;
import com.diyun.enums.LogLevel;
import com.diyun.listener.HouseIDListener;
import com.diyun.tool.DataParser;
import com.diyun.tool.Frame;
import com.diyun.tool.Parser;
import com.diyun.tool.Tool;
import com.diyun.util.MessageQueue;
import com.diyun.util.Util;

/**
 * 
 * Subscribe data by MQTT(V3.1).
 * 
 * @author Tx.Loooper
 * @version 2017/12/28
 * @since 1.6
 *
 */
public class Consumer {
 
	private static final String TAG = "Consumer";
    private MqttClient client;  
    private MqttConnectOptions options;
    private String host;
    public static HashMap<Integer, MessageQueue> mQueues = new HashMap<Integer, MessageQueue>();
    private HouseIDListener mListener;
    private HashSet<Integer> set = new HashSet<Integer>();
    private int key = 0;
	
	public Consumer(String host){
		this.host = host;
	}
	
	public void setWorkMode(int mode){
		Topics.setWorkMode(mode);
	}

    public void open() {  
        try {  
        	int[] Qos  = {1};  
            String[] topic1 = {Topics.SWATER_TOPIC_MATCH_ALL()}; 
            System.out.println("Server is connecting mqtt and watting message ...");
            client = new MqttClient(host, Topics.CLIENT_ID() + new Random().nextInt(1000), new MemoryPersistence());    
            options = new MqttConnectOptions();    
            options.setCleanSession(true);                             
            options.setConnectionTimeout(10);    
            options.setKeepAliveInterval(20);  
            client.setCallback(new MqttCallback(){

				@Override
				public void connectionLost(Throwable arg0) {
					while(!client.isConnected()){
						try {
							client.connect(options);
							client.subscribe(topic1, Qos);  
						} catch (MqttSecurityException e) {
							e.printStackTrace();
						} catch (MqttException e) {
							e.printStackTrace();
						}
						try {
							Thread.sleep(1000 * 30);
						} catch (InterruptedException e) {					
							e.printStackTrace();
						}
					}
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken arg0) {
					
				}

				@Override
				public void messageArrived(String topic, MqttMessage msg) throws Exception {
					if(msg ==null)
						return;
					byte[] payload = msg.getPayload();			
					if(topic.startsWith(Topics.SWATER_TOPIC_PREFIX()))			   
					{							
						if(Parser.checkInvalid(payload)){
							Util.log(TAG, "Mqtt message is coming, topic["+topic+"], payload["+Tool.BytesToHexStringEx(payload, 0)+"]", LogLevel.SYS);
							if(Frame.checkFrame(payload)){
								int dtuip = Tool.getDeviceAddress(payload[3],payload[4],payload[5],payload[6]);
								set.add(dtuip);		
								if(key < set.size()){					
									if(mListener != null){	
										Util.log(TAG, "#### A new dtu is coming ####, dtuip:"+dtuip+",dtu total nums:"+set.size(), LogLevel.SYS);
										mListener.startLoop(dtuip);
										key++;
									}
								}										
								if(mQueues.get(dtuip) != null){
									String str = DataParser.parseData(new DTUDataBean(payload,(0xFF & payload[8]),dtuip));						
									if(!str.trim().isEmpty())mQueues.get(dtuip).push(str);
								}
							}
						}
					} 		
				}           	
            });              
            client.connect(options);             
            client.subscribe(topic1, Qos);  
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
    
    public void setHouseListener(HouseIDListener listener){
    	this.mListener = listener;
    }
}
