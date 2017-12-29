package com.diyun.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.diyun.enums.LogLevel;
import com.diyun.util.Global;
import com.diyun.util.MessageQueue;
import com.diyun.util.Util;

public class RunnableCloud implements Runnable{
	
    private static final String TAG = "RunnableCloud";
    private Socket handle = null;
    public String imei = null;
    public String address = null;
    public int port = 0;
    private MessageQueue vqueue = null; 
    private volatile long lastSendTime = System.currentTimeMillis();
    private volatile long lastACKTime = System.currentTimeMillis();
    private int retryCount = 0;
    
    /**
     * 
     * @param imei
     * @param address
     * @param port
     */
    public RunnableCloud(String imei, String address, int port) {
        this.imei = imei;
        this.address = address;
        this.port = port;
    }

    private String gatewayNotify(String imei) {
        return "{\"zdata\":{\"imei\":\"" + imei + "\"}}";
    }

    public void sendHeartNotify() throws IOException {   	
        send(gatewayNotify(imei));
    }

    public void connect() {   	
    	Util.log(TAG, "Server connect" + ((retryCount > 0)? " retry " + retryCount + " times." : "..."), LogLevel.SYS);     
    	if (Global.ALLOW_RETRY_CONNECT) {
            if (retryCount >= Global.MAX_RETRY_NUM) {
                handle = null;
                Util.log(TAG, "Warning: reached to max network retry count " + retryCount, LogLevel.SYS);
                retryCount = 0;       
                reconnect();
                return;
            } else retryCount++;
        }
        if (handle == null) {
        	boolean isConnected = false;
            try {
                handle = new Socket(address, port);
                handle.setKeepAlive(true);
                handle.setReuseAddress(true);
                sendHeartNotify();
                retryCount = 0;
                isConnected = true;
                lastACKTime = System.currentTimeMillis();
            } catch (SocketException e) {      
                Util.log(TAG, "Server connect exception:" + e.toString(), LogLevel.SYS);
                isConnected = false;              
            } catch (UnknownHostException e) {               
                Util.log(TAG, "Server connect exception:" + e.toString(), LogLevel.ALL); 
                isConnected = false;  
            } catch (IOException e) {
                Util.log(TAG, "Server connect exception:" + e.toString(), LogLevel.ALL); 
                isConnected = false;  
            }
            if(!isConnected){
            	handle = null;
            	Util.log(TAG, "Server connect fail, connect again now.", LogLevel.SYS);
                try {
                    Thread.sleep(1000 * 30);
                    connect();
                } catch (InterruptedException e) {
                	 Util.log(TAG, "Server connect exception:" + e.toString(), LogLevel.ALL);
                }
            }
        }
    }

    public void reconnect() {
    	Util.log(TAG, "Server start to reconnect cloud...", LogLevel.ALL);
        if(handle != null && handle.isConnected())disconnect();
        if (handle != null) { 
            disconnect();
        }     
        connect();
    }

    public void disconnect() {
    	Util.log(TAG, "Server start to disconnect...", LogLevel.ALL);
        if (handle != null) {
            try {
                handle.close();
                handle = null;
            } catch (IOException e) {
            	Util.log(TAG, "Server disconnect "+e.toString(), LogLevel.ALL);
            }
        }
    }

    public boolean online() {
        return ((handle != null) && handle.isConnected());
    }

    public void run() {
        if (handle == null) {
            connect();
        }
        
        if (handle == null) {
        	Util.log(TAG, "Server failed to connect to cloud.", LogLevel.ALL);
            return;
        }

        if (!handle.isConnected()) {
            reconnect();
        }

        if (!handle.isConnected()) {
        	Util.log(TAG, "Server to reconnect to cloud cloud.", LogLevel.ALL);
            return;
        }
        receiveMessageFromCloud();
    }

    public boolean send(byte[] data) {
        return sendLater(data, false);
    }

    public void flush() {
    	if(handle != null){
	        try {
	            OutputStream os = handle.getOutputStream();
	            os.flush();
	        } catch (IOException e) {
	        	Util.log(TAG, "flush:" + e, LogLevel.SYS);
	        }
    	}

    }

    public boolean sendLater(byte[] data, boolean later) {
        boolean result = true;
        if(handle != null){
	        try {    		        	
		        	if(handle != null && handle.isConnected()){
			            OutputStream os = handle.getOutputStream();
			            os.write(data);
			            if (data.length > 0) {
			                os.write("\\r\\n".getBytes());
			            }
			            os.flush();
			            lastSendTime = System.currentTimeMillis();
	        	}
	        } catch (IOException e) {
	        	Util.log(TAG, "Server send data ioexception:" + e.toString(),LogLevel.SYS);
	            result = false;
	        } catch (Exception e) {
	        	Util.log(TAG, "Server send data exception:" + e.toString(), LogLevel.SYS);
	            result = false;
	        }
        }else{
        	result = false;
        }
        return result;
    }

    public void sendHeartConnect() {
        long interval = System.currentTimeMillis() - lastSendTime;
        long acktime = System.currentTimeMillis() - lastACKTime;
        if(handle != null && !handle.isConnected())
        {
        	reconnect();    
        	return;
        }
        if (interval > Global.MAX_ACK_INTERVAL_TIME) {
            send("");
            Util.log(TAG,"Server send heart test with interval:" + interval, LogLevel.SYS);
        }
        if(acktime > Global.MAX_RESET_TIME){
        	Util.log(TAG,"Server has not receive 'ack' info from cloud, and server'll restart.(ack time:)"+acktime, LogLevel.SYS);   
        	lastACKTime = System.currentTimeMillis();
        	reconnect();       	
        }
    }

    public boolean sendLater(String data) {
    	Util.log(TAG,"Server is sending:" + data, LogLevel.SYS);
        return sendLater(data.getBytes(), true);
    }

    public boolean send(String data) {
        return send(data.getBytes());
    }

    public void receiveMessageFromCloud() {
        byte[] buf = new byte[512];
        try {
        	if(handle != null){
	            InputStream is = handle.getInputStream();
	            int buflen = 0;
	            {
	                buflen = is.read(buf);
	                if ((buflen > 0) ) {
	                    boolean hasOK = false;
	                    boolean hasACK = false;
	                    boolean hasUTC = false;
	                    String message = new String(buf);
	                    message = message.trim();
	                    if ((message == null) || (message.length() == 0)) {
	                    	Util.log(TAG, message, LogLevel.SYS);
	                        return;
	                    }
	                    hasOK = (message.indexOf("OK") < 0)? false : true;
	                    hasACK = (message.indexOf("ACK") < 0)? false : true;
	                    hasUTC = (message.indexOf("UTC") < 0)? false : true;
	                    
	                    lastACKTime = System.currentTimeMillis();
	                    if(hasUTC){
	                    	if(Global.timeDiff == 0){
	                    		Global.timeDiff = Long.parseLong(message.substring
		                      			(message.indexOf("UTC:") + 4,message.indexOf(";"))) - lastSendTime;
		                      	Util.log(TAG, "time difference:"+Global.timeDiff, LogLevel.SYS);
	                    	}
	                    } 
	                    if(hasOK && !hasACK) {                   	
	                        buf = null;
	                        Util.log(TAG,"Message from cloud:"+message, LogLevel.SYS);
	                        return;
	                    }                  
	                    send("{\"zresponse\":\"ALIVE\"}\r\n");
	                    if (hasACK) {	
	                    	Util.log(TAG,"Message from cloud:"+message, LogLevel.SYS);
	                        buf = null;
	                        return;
	                    } 
	                }
	            }
        	}
        } catch (IOException e) {
        	Util.log(TAG, "receiveMessageFromCloud:" + e.toString(), LogLevel.SYS);
//            reconnect();
        }
        buf = null;
    }

    public void sendMessageToCloud() {
        String message = null;
        while ((vqueue != null) && (vqueue.length() > 0)) {
            message = vqueue.pop();
            if (message != null) {
                if(!sendLater(message)) {
                    vqueue.push(message);
                    Util.log(TAG, "Server send message to cloud failed.", LogLevel.SYS);
                    try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
                }else{
                	lastACKTime = System.currentTimeMillis();
                }
            }
        }
        flush();
    }

    public void cleanup(){
        disconnect();
        vqueue = null;
    }

    public void setQueue(MessageQueue queue) {
        vqueue = queue;
    }
}
