package com.diyun.beans;

import com.diyun.enums.CloudDataType;
import com.diyun.util.Global;

public class AmbientData extends BaseData{	
	private String dtuid       = null;
	private String timestamp   = "0";
	private int msgid          = 0;
	private int houseid        = 0;
	private int superType      = -1;
	private int ownType        = 0;
	private int order          = 0;	
	private int pumpid         = 0;
	private float wtemperature = 0;
	private float amplitude    = 0; 	


	
	public AmbientData() {
		super();
	}
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
	public int getPumpid() {
		return pumpid;
	}
	public void setPumpid(int pumpid) {
		this.pumpid = pumpid;
	}
	public float getWtemperature() {
		return wtemperature;
	}
	public void setWtemperature(float wtemperature) {
		this.wtemperature = wtemperature;
	}
	public float getAmplitude() {
		return amplitude;
	}
	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
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
		sb.append(",\"type\":" + CloudDataType.TYPE_405_AMBIENT.getType());
		sb.append(",\"order\":" + order);
		sb.append(",\"houseid\":" + houseid);
		sb.append(",\"dtuid\":\"" + dtuid + "\"");
		sb.append(",\"pumpid\":" + pumpid);
		sb.append(",\"ptemperature\":" + wtemperature);
		sb.append(",\"amplitude\":" + amplitude);
		sb.append(",\"timestamp\":\"" + (System.currentTimeMillis()+Global.timeDiff) + "\"");
		sb.append("}}");	
        return sb.toString();
    }
	public static void main(String[] args) {
		System.out.println(new AmbientData().toString());
	}
}
