package com.diyun.data;

/**
 * The format of redis data.
 * @author t.x
 * <br>2018/03/28<br>
 *
 */
public class DataBean {
	
	private String ip; //dtu ip
	private String dataTag; //data type tag
	private String deviceTag; // ip+adr+channel
	private String fullTag; //ip+adr+channel+dataTag
	private String value;
	private long reportTime;	
	
	public DataBean() {
		super();
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDataTag() {
		return dataTag;
	}
	public void setDataTag(String dataTag) {
		this.dataTag = dataTag;
	}
	public String getDeviceTag() {
		return deviceTag;
	}
	public void setDeviceTag(String deviceTag) {
		this.deviceTag = deviceTag;
	}
	public String getFullTag() {
		return fullTag;
	}
	public void setFullTag(String fullTag) {
		this.fullTag = fullTag;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getReportTime() {
		return reportTime;
	}
	public void setReportTime(long reportTime) {
		this.reportTime = reportTime;
	}
	
	public String toJsonString(){
		return "{\"ip\":\"" + ip+"\""+
				",\"dataTag\":\"" + dataTag +"\""+
				",\"deviceTag\":\"" + deviceTag +"\""+
				",\"fullTag\":\"" + fullTag +"\""+
				",\"value\":\"" + value +"\""+
				",\"reportTime\":" + reportTime+
				"}";	
	}
		
}
