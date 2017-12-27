package com.diyun.threads;

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
