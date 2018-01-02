package com.diyun.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.diyun.listener.OnReceiveListener;
import com.diyun.util.Tool;

public class TcpThread extends Thread {
    private Socket socket;
    private OnReceiveListener listener;

    public TcpThread(Socket socket, OnReceiveListener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    public void run() {	
    	try{
    		InputStream is = socket.getInputStream();            
             String info=null;
             byte[] buf = new byte[512];
             is =socket.getInputStream();
             int buflen = is.read(buf);
             if ((buflen > 0)) {
	             String message = new String(buf);	           
	             if ((message == null) || (message.length() == 0)) {
                      return;
                 }
	             info = message.trim();
	             Tool.myLog("TcpThread.run()", "cloud sends message:"+info.toString()+".");
		         if(listener != null){
		              listener.onReceiveData(info);
		         }
	             OutputStream os = socket.getOutputStream();
	             os.write("OK".getBytes());        
	             os.flush();
	             os.close(); 
	             is.close();
	             Tool.myLog("TcpThread.run()", "GatewayTransfer send 'OK' to cloud.");                  
	             socket = null;            
             }
    	}catch (Exception e) {
    		e.printStackTrace();
    		Tool.myLog("TcpThread.run()", "Exception:"+e.toString());
		}  	
    }
}