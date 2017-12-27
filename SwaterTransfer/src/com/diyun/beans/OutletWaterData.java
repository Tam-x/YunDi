package com.diyun.beans;

import com.diyun.enums.CloudDataType;
import com.diyun.util.Global;

public class OutletWaterData extends BaseData{

	private String dtuid     = null;//IMEI
	private int msgid        = 0;
	private int houseid      = 0;
	private int ownType      = 0;
	private int order        = 0;	
	private float pressure   = 0;
	private float temperature= 0;
	private float flowrate   = 0;
	private float totalflow  = 0;
	private boolean isflowin = false;
	public OutletWaterData() {
		super();
	}
	public String getDtuid() {
		return dtuid;
	}
	public void setDtuid(String dtuid) {
		this.dtuid = dtuid;
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
		sb.append(",\"type\":" + CloudDataType.TYPE_409_OUTLET.getType());
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
