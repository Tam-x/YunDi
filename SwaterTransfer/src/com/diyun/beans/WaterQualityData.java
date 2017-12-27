package com.diyun.beans;

import com.diyun.enums.CloudDataType;
import com.diyun.util.Global;

public class WaterQualityData extends BaseData{
	private int msgid          = 0;
	private int superType      = -1;
	private int ownType        = 0;
	private int houseid        = 0;
	private String dtuid       = null;
	private float wtemperature = 0;
	private float ph           = 0;
	private float terbidity    = 0; 
	private float chlorine     = 0;
	private int order          = 0;
	private String timestamp   = "0";
	
	public WaterQualityData() {
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

	public float getWtemperature() {
		return wtemperature;
	}

	public void setWtemperature(float wtemperature) {
		this.wtemperature = wtemperature;
	}

	public float getPh() {
		return ph;
	}

	public void setPh(float ph) {
		this.ph = ph;
	}

	public float getTerbidity() {
		return terbidity;
	}

	public void setTerbidity(float terbidity) {
		this.terbidity = terbidity;
	}

	public float getChlorine() {
		return chlorine;
	}

	public void setChlorine(float chlorine) {
		this.chlorine = chlorine;
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
		sb.append(",\"type\":" + CloudDataType.TYPE_407_WATER_QUALITY.getType());
		sb.append(",\"order\":" + order);
		sb.append(",\"houseid\":" + houseid);
		sb.append(",\"dtuid\":\"" + dtuid + "\"");
		sb.append(",\"wtemperature\":" + wtemperature);
		sb.append(",\"ph\":" + ph);
		sb.append(",\"terbidity\":" + terbidity);
		sb.append(",\"chlorine\":" + chlorine);
		sb.append(",\"timestamp\":\"" + (System.currentTimeMillis()+Global.timeDiff) + "\"");
		sb.append("}}");
        return sb.toString();
    }
	public static void main(String[] args) {
		System.out.println(new WaterQualityData().toString());
	}
}
