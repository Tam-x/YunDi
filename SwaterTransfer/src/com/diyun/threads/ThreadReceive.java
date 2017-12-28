package com.diyun.threads;

/**
 * Server receive data from cloud.
 * 
 * @author Tx.Loooper
 * @version 2017/12/28 V1.0
 * @since 1.6
 *
 */
public class ThreadReceive extends Thread{
	private boolean isLoop = true;
	private RunnableCloud cloud;
	public void run() {
		while(isLoop){
			if(cloud != null){				
				try {					
					cloud.receiveMessageFromCloud();
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
	public void stopLoop(){
		isLoop = false;
	}
}
