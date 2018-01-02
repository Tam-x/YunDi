package com.diyun.bean;

import com.diyun.util.Global;

/**
 * Lamp command data.
 * 
 * @author Tx.Loooper
 * @version 2018/01/02, v1.0
 * @since 1.8
 * 
 */
public class LightCMDInfo extends DataBase{
	
	private String zigbeeAdr;
	private int channel;
	private int status;
	
	public LightCMDInfo(String zigbeeAdr, int channel, int status) {
		super();
		this.zigbeeAdr = zigbeeAdr;
		this.channel = channel;
		this.status = status;
	}
	
	private String getStatus(int status){
		return status == 0 ? Global.FAILED : (status == 1 ? Global.SUCCESS : Global.UNKNOW+"("+status+")");
	}
		
	@Override
	public String toString() {
		return "¡¾Receive-lamp¡¿zigbeeAdr:"+zigbeeAdr+", result:"+getStatus(status)+"channel:"+getChannel(channel)+"\n";
	}	
}
