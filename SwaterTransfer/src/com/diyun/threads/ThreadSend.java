package com.diyun.threads;

/**
 * Server send data to cloud.
 * 
 * @author Tx.Loooper
 * @version 2017/12/28 V1.0
 * @since 1.6
 *
 */
public class ThreadSend extends Thread{
	private boolean isLoop = true;
	private RunnableCloud cloud;
	public void run() {
		while(isLoop){
			if(cloud != null){				
				try {					
					cloud.sendMessageToCloud();
					cloud.sendHeartConnect();					
					Thread.sleep(1);										
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
		}
	}
	public void setCloud(RunnableCloud cloud){
		this.cloud = cloud;
	}
}
