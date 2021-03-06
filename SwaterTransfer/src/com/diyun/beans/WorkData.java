package com.diyun.beans;

import com.diyun.enums.CloudDataType;
import com.diyun.util.Global;

public class WorkData extends BaseData{
	private int msgid         = 0;
	private int superType     = -1;
	private int ownType       = 0;
	private int houseid       = 0;
	private String dtuid      = null;
	private int pumpid        = 0;
	private boolean power     = false;
	private boolean haswater  = false; 
	private boolean isrunning = false;
	private boolean fault     = false;
	private int order         = 0;
	private String timestamp  = "0";
	
	public WorkData() {
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
	public boolean isPower() {
		return power;
	}
	public void setPower(boolean power) {
		this.power = power;
	}
	public boolean isHaswater() {
		return haswater;
	}
	public void setHaswater(boolean haswater) {
		this.haswater = haswater;
	}
	public boolean isIsrunning() {
		return isrunning;
	}
	public void setIsrunning(boolean isrunning) {
		this.isrunning = isrunning;
	}
	public boolean isFault() {
		return fault;
	}
	public void setFault(boolean fault) {
		this.fault = fault;
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
		sb.append(",\"type\":" + CloudDataType.TYPE_404_WORK.getType());
		sb.append(",\"order\":" + order);
		sb.append(",\"houseid\":" + houseid);
		sb.append(",\"dtuid\":\"" + dtuid + "\"");
		sb.append(",\"pumpid\":" + pumpid);
		sb.append(",\"power\":" + power);
		sb.append(",\"haswater\":" + haswater);
		sb.append(",\"isrunning\":" + isrunning);
		sb.append(",\"fault\":" + fault);
		sb.append(",\"timestamp\":\"" + (System.currentTimeMillis()+Global.timeDiff) + "\"");
		sb.append("}}");
        return sb.toString();
    }
}
