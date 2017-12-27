package com.diyun.beans;

import com.diyun.enums.CloudDataType;
import com.diyun.util.Global;

public class EnergyData extends BaseData{
	private int msgid        = 0;
	private int superType    = -1;
	private int ownType      = 0;
	private int houseid      = 0;
	private String dtuid     = null;
	private float costpower  = 0;
	private float avoltage    = 0;
	private float bvoltage    = 0;
	private float cvoltage    = 0;
	private float acurrent    = 0;
	private float bcurrent    = 0;
	private float ccurrent    = 0;
	private float power      = 0;
	private int order        = 0;
	private String timestamp = "0";
	
	public EnergyData() {
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
	public float getCostpower() {
		return costpower;
	}
	public void setCostpower(float costpower) {
		this.costpower = costpower;
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
	
	public float getAvoltage() {
		return avoltage;
	}
	public void setAvoltage(float avoltage) {
		this.avoltage = avoltage;
	}
	public float getBvoltage() {
		return bvoltage;
	}
	public void setBvoltage(float bvoltage) {
		this.bvoltage = bvoltage;
	}
	public float getCvoltage() {
		return cvoltage;
	}
	public void setCvoltage(float cvoltage) {
		this.cvoltage = cvoltage;
	}
	public float getAcurrent() {
		return acurrent;
	}
	public void setAcurrent(float acurrent) {
		this.acurrent = acurrent;
	}
	public float getBcurrent() {
		return bcurrent;
	}
	public void setBcurrent(float bcurrent) {
		this.bcurrent = bcurrent;
	}
	public float getCcurrent() {
		return ccurrent;
	}
	public void setCcurrent(float ccurrent) {
		this.ccurrent = ccurrent;
	}
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
	public String toString() {
		sb.append("{\"zdata\":{");
		sb.append("\"msgid\":" + msgid);
		sb.append(",\"type\":" + CloudDataType.TYPE_403_ENERGY.getType());
		sb.append(",\"order\":" + order);
		sb.append(",\"houseid\":" + houseid);
		sb.append(",\"dtuid\":\"" + dtuid + "\"");
		sb.append(",\"avoltage\":" + avoltage);
		sb.append(",\"bvoltage\":" + bvoltage);
		sb.append(",\"cvoltage\":" + cvoltage);
		sb.append(",\"acurrent\":" + acurrent);
		sb.append(",\"bcurrent\":" + bcurrent);
		sb.append(",\"ccurrent\":" + ccurrent);
		sb.append(",\"costpower\":" + costpower);
		sb.append(",\"timestamp\":\"" + (System.currentTimeMillis()+Global.timeDiff) + "\"");
		sb.append("}}");		
		
        return sb.toString();
    }
	public static void main(String[] args) {
		System.out.println(new EnergyData().toString());
	}
}
