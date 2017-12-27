package com.diyun.beans;

import com.diyun.enums.CloudDataType;
import com.diyun.util.Global;

public class WaterLevelData extends BaseData{
	private int msgid        = 0;
	private int superType    = -1;
	private int ownType      = 0;
	private int houseid      = 0;
	private String dtuid     = null;
	private float waterlevel   = 0;
	private float flowin     = 0;
	private float flowout    = 0; 
	private float pressure   = 0;
	private int order        = 0;
	private String timestamp = "0";
		
	public WaterLevelData() {
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
	public float getWaterlevel() {
		return waterlevel;
	}
	public void setWaterlevel(float waterlevel) {
		this.waterlevel = waterlevel;
	}
	public float getFlowin() {
		return flowin;
	}
	public void setFlowin(float flowin) {
		this.flowin = flowin;
	}
	public float getFlowout() {
		return flowout;
	}
	public void setFlowout(float flowout) {
		this.flowout = flowout;
	}
	public float getPressure() {
		return pressure;
	}
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String toString() {
		sb.append("{\"zdata\":{");
		sb.append("\"msgid\":" + msgid);
		sb.append(",\"type\":" + CloudDataType.TYPE_408_WATER_LEVEL.getType());
		sb.append(",\"order\":" + order);
		sb.append(",\"houseid\":" + houseid);
		sb.append(",\"dtuid\":\"" + dtuid + "\"");
		sb.append(",\"waterlevel\":" + waterlevel);
		sb.append(",\"flowin\":" + flowin);
		sb.append(",\"flowout\":" + flowout);
		sb.append(",\"pressure\":" + pressure);
		sb.append(",\"timestamp\":\"" + (System.currentTimeMillis()+Global.timeDiff) + "\"");
		sb.append("}}");	
        return sb.toString();
    }
	public static void main(String[] args) {
		System.out.println(new WaterLevelData().toString());
	}
}
