package com.diyun.threads;

public class ThreadWatch extends Thread{
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
