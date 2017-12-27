package com.diyun.beans;

/**
 * Device information bean, it comes from device.xml
 * 
 * @author Tx.loooper
 * @version 2017/12/27 V1.0
 * @since 1.6
 *
 */
public class DataDevice {
	
	private int dtuID;
	private String IMEI;
	
	public DataDevice() {
		super();
	}

	public DataDevice(int dtuID, String iMEI) {
		super();
		this.dtuID = dtuID;
		IMEI = iMEI;
	}

	public int getDtuID() {
		return dtuID;
	}

	public void setDtuID(int dtuID) {
		this.dtuID = dtuID;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
		
}
