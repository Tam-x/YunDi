package com.diyun.beans;

import com.diyun.enums.CloudDataType;
import com.diyun.util.Global;

public class TempHumiData extends BaseData{
	private int msgid        = 0;
	private int superType    = -1;
	private int ownType      = 0;
	private int houseid      = 0;
	private String dtuid     = null;
	private float temp       = 0;
	private float humidity   = 0;
	private int order        = 0;
	private String timestamp = "0";
	
	 public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	public int getSuperType() {
		return superType;
	}

	public void setSuperType(int superType) {
		this.superType = superType;
	}

	public int getOwnType() {
		return ownType;
	}

	public void setOwnType(int ownType) {
		this.ownType = ownType;
	}

	public int getHouseid() {
		return houseid;
	}

	public void setHouseid(int houseid) {
		this.houseid = houseid;
	}

	public String getDtuid() {
		return dtuid;
	}

	public void setDtuid(String dtuid) {
		this.dtuid = dtuid;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String toString() {
		sb.append("{\"zdata\":{");
		sb.append("\"msgid\":" + msgid);
		sb.append(",\"type\":" + CloudDataType.TYPE_401_TEMPHUMI.getType());
		sb.append(",\"order\":" + order);
		sb.append(",\"houseid\":" + houseid);
		sb.append(",\"dtuid\":\"" + dtuid + "\"");
		sb.append(",\"temperature\":" + temp);
		sb.append(",\"humidity\":" + humidity);
		sb.append(",\"timestamp\":\"" + (System.currentTimeMillis()+Global.timeDiff) + "\"");
		sb.append("}}");	
	    return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new TempHumiData().toString());
	}
}
