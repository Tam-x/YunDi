package com.diyun.beans;

import com.diyun.enums.CloudDataType;
import com.diyun.util.Global;

public class InletWaterData extends BaseData{
	private String dtuid     = null;
	private String timestamp = "0";
	private int msgid        = 0;
	private int houseid      = 0;
	private int superType    = -1;
	private int ownType      = 0;
	private int order        = 0;	
	private float pressure   = 0;
	private float temperature= 0;
	private float flowrate   = 0;
	private float totalflow  = 0;
	private boolean isflowin = true;
	public InletWaterData() {
		super();
	}
	public String getDtuid() {
		return dtuid;
	}
	public void setDtuid(String dtuid) {
		this.dtuid = dtuid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public int getHouseid() {
		return houseid;
	}
	public void setHouseid(int houseid) {
		this.houseid = houseid;
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
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public float getPressure() {
		return pressure;
	}
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	public float getFlowrate() {
		return flowrate;
	}
	public void setFlowrate(float flowrate) {
		this.flowrate = flowrate;
	}
	public float getTotalflow() {
		return totalflow;
	}
	public void setTotalflow(float totalflow) {
		this.totalflow = totalflow;
	} 		
	public boolean isIsflowin() {
		return isflowin;
	}
	public void setIsflowin(boolean isflowin) {
		this.isflowin = isflowin;
	}
	public String toString() {
		sb.append("{\"zdata\":{");
		sb.append("\"msgid\":" + msgid);
		sb.append(",\"type\":" + CloudDataType.TYPE_406_INLET.getType());
		sb.append(",\"order\":" + order);
		sb.append(",\"houseid\":" + houseid);
		sb.append(",\"dtuid\":\"" + dtuid + "\"");
		sb.append(",\"pressure\":" + pressure);
		sb.append(",\"wtemperature\":" + temperature);
		sb.append(",\"flowrate\":" + flowrate);
		sb.append(",\"totalflow\":" + totalflow);
		sb.append(",\"isflowin\":" + isflowin);
		sb.append(",\"timestamp\":\"" + (System.currentTimeMillis()+Global.timeDiff) + "\"");
		sb.append("}}");	
        return sb.toString();
    }
}
