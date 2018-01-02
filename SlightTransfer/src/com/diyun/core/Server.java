package com.diyun.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.diyun.config.Config;
import com.diyun.listener.OnReceiveListener;
import com.diyun.util.Tool;

/**
 * server.
 * @author Tx.Loooper
 *
 */
public class Server implements OnReceiveListener{
	private String contify = "";
	private OnReceiveListener listener;
	private static String json = "{\"zdata\":{\"id\":\"LN00000001\",\"type\":\"LN\",\"power\":true,\"bright\":100,\"I\":0.5,\"V\":228.0,\"AW\":0.0,\"RW\":0.0,\"T\":28.9,\"F\":40.0,\"time\":\"1438146093002\"}}";
	
	public void setListener(OnReceiveListener listener){
		this.listener = listener;
	}
	
	/**
	 * Send data to cloud for confirming.
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * @param imei the sign of DTU.
	 * 
	 * <p>
	 * If this is not success, the cloud will refuse to connect.
	 */
	public Server(String imei){
		contify += "{\"cnotify\":{";
        contify += "\"imei\":\"" + imei+ "\"";	
        contify += ",\"online\":" + true;
        contify += ",\"time\":" + System.currentTimeMillis();
        contify += "}}";	
        Tool.myLog("Server()", "Send data to cloud for confirming.");
		HttpPostData(contify);
	}
	
	/**
	 * Interface method for MQTT data entry.
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * @param msg message from cloud (JSON string).
	 * 
	 * <p>
	 * A listener is listening MQTT data all the time, 
	 * once data coming will be here.
	 */
	public void onReceiveData(String msg) {
		Tool.myLog("Server.onReceiveData()","Server will post data:"+msg);
		HttpPostData(msg);
	}
	
	/**
	 * Start the server to transfer data.
	 * 
	 * @author TX.Loooper
	 * @version 2017-12-15
	 * 
	 * <p>
	 * On the one hand, parsing the MQTT data is reported to the cloud, 
	 * on the other hand, resolve the descending order and send it to MQTT.
	 */
	public void startServer(){
		try {
            @SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(Config.SERVER_PORT);
            Tool.myLog("Server.startServer()", "Server is starting...(port:"+server.getLocalPort()+").");
            while (true) {
                Socket socket = server.accept();
                Tool.myLog("Server.startServer()", "Client is accepted, from address:"+socket.getRemoteSocketAddress()+".");
                TcpThread socketThread = new TcpThread(socket, listener);
                socketThread.start();
            }           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/*
	 * test
	 */
	public static void PostData(){
		try{
			URL url = new URL(Config.URL);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        httpConn.setConnectTimeout(3000); 
	        httpConn.setReadTimeout(30000); 
	        httpConn.setUseCaches(false); 
	        httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
	        httpConn.setRequestMethod("POST");
	        httpConn.setRequestProperty("Connection", "Keep-Alive");
	        httpConn.setRequestProperty("User-Agent", "citylink");
	        httpConn.setRequestProperty("S3CTYPE", "AND");
	        httpConn.setRequestProperty("S3CPARAM", json);
	        httpConn.connect();
	        byte[] response = new byte[512];
			InputStream is = httpConn.getInputStream();
			while(is.read(response) > 0) {
				System.out.println(new String(response,"utf-8"));
			}
			httpConn.disconnect();
		}catch(Exception e){
			Tool.myLog("Server.PostData()", "Exception:"+e.toString());
		}
	}
		
	private void HttpPostData(String msg) {  
		try {  
			DefaultHttpClient httpclient = new DefaultHttpClient();  
		    String uri = Config.URL;  
		    HttpPost httppost = new HttpPost(uri);   
		    httppost.addHeader("User-Agent", "citylink");
		    httppost.addHeader("S3CTYPE", "AND");  
		    httppost.addHeader("S3CPARAM", msg);
		    httppost.addHeader("Connection", "Keep-Alive");
		    HttpResponse response;   
		    response = httpclient.execute(httppost);  
		    String rev =EntityUtils.toString(response.getEntity());
		    Tool.myLog("Server.HttpPostData()", "Cloud's back data:"+rev);
		} catch (IOException e) {
			e.printStackTrace();
			Tool.myLog("Server.HttpPostData()", "IoException:"+e.toString());
		} catch (Exception e) { 
			e.printStackTrace();
			Tool.myLog("Server.HttpPostData()", "Exception:"+e.toString());
		}  
	} 
	
	public static void main(String[] args) {
		Server server = new Server("");
		server.setListener(new Mqtt());
		server.startServer();
	}
}
