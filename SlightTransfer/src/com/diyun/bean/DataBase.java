package com.diyun.bean;

import com.diyun.util.Global;

/**
 * The supper class of all kinds of smart light data.
 * 
 * @author Tx.Loooper
 * @version 2018/01/02, v1.0
 * @since 1.8
 *
 */
public class DataBase {
	
	public String getChannel(int channel){
		return channel == 0 ? Global.UNKNOW :(channel == 1 ? Global.GPRS:Global.ETHERNET);
	}
}
