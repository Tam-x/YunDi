package com.diyun.consumer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import redis.clients.jedis.Jedis;
import com.diyun.parser.DecodeFrame;
import com.diyun.util.Logger;
import com.diyun.util.Utility;

public class Consumer {
    private static final String TAG = "Consumer";    
    private static String mUniqueId = "";
	private String clientId = "";	
	private MqttClient client;  
    private MqttConnectOptions options;
    private ScheduledExecutorService scheduler; 
    private Jedis jedis;    
     
	public Consumer(){
		
	}	
	
	public void setUniqueId(String id){
    	mUniqueId = id;
    }
	
    public void open() {   	
        try {
        	
        	Config.HOST = Utility.getPropertyString("default_mqtt_server");
        	String jedisHost =Utility.getPropertyString("default_jedis_server_name");
        	Logger.print(TAG, Config.HOST);
        	int jedisPort = Utility.getPropertyInt("default_jedis_server_port");
        	  	
        	jedis = new Jedis(jedisHost, jedisPort);
        	clientId = Config.CLIENT_ID() + "[" + Config.VERSION + "@" + mUniqueId +"]" ;        	
        	Logger.log(TAG,"Connect to:" + Config.HOST + ", clientid:" + clientId);
            client = new MqttClient(Config.HOST, clientId, new MemoryPersistence());    
            options = new MqttConnectOptions();    
            options.setCleanSession(true);                           
//            options.setUserName("admin");                
//            options.setPassword("password".toCharArray());            
            options.setConnectionTimeout(10);    
            options.setKeepAliveInterval(60);  
            client.setCallback(new PushCallback());               
            client.connect(options);            
            client.subscribe(Config.getSubscribeTopic());
                   
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
                        client.subscribe(Config.getSubscribeTopic()); 
                    } catch (MqttSecurityException e) {  
                        e.printStackTrace();  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }, 5 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);  
    }  
     
    private class PushCallback implements MqttCallback {

		public void connectionLost(Throwable arg0) {
			arg0.printStackTrace();
			reconnect();			
		}

		public void deliveryComplete(IMqttDeliveryToken arg0) {
			
		}

		public void messageArrived(String topic, MqttMessage msg) throws Exception {
			if(msg == null)
				return;
			byte[] payload = msg.getPayload();
			Logger.print(TAG, topic);
			if(Config.isSupportTopic(topic))
			{			
				new DecodeThread(payload, jedis).start();
			}
		}        		
    }  
    
    private class DecodeThread extends Thread{
    	private Jedis jedis;
    	private byte[] msg;
    	public DecodeThread(byte[] msg, Jedis jedis){
    		this.msg = msg;
    		this.jedis = jedis;
    	}
    	public void run(){
    		DecodeFrame.decodeRealTimeMsg(msg, jedis);
    	}
    }
}
