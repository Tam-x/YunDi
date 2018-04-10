package com.diyun.message;

/**
 * The data which is coming from pump house's real time data frame.
 * @author t.x
 * <br>2018/03/28<br>
 *
 * This frame according to protocol of fzsc-mqtt-protocol was written by Nix.long.
 */
public class BaseFrame {
	
	public static final byte HEADER_FLAG_1 = (byte) 0xAA;
	public static final byte HEADER_FLAG_2 = (byte) 0xFE;
	public static final byte TAIL_FLAG_1 = (byte) 0xDD;
	public static final byte TAIL_FLAG_2 = (byte) 0xEE;
	public static final byte PROTOCOL_VERSION = 0x02;
	public static final int FRAME_DATA_INDEX = 18;
	public final static int FRAME_MIN_LEN = 21;
	
	protected int frameLen = 0;
	protected int projectCategory = 0;
	protected int supplierCode = 0;
	protected String dtuIp = "";
	protected int cmdCode = 0;
	protected long date;
	protected byte[] data;
	protected int dataLen = 0;
	
	public byte[] getData(){
		return this.data;
	}
	
	public int getDataLen(){
		return this.dataLen;
	}
	
	public String getDtuIp() {
		return this.dtuIp;
	}

	public long getDate() {
		return this.date;
	}

	public int getCmdCode(){
		return this.cmdCode;
	}
	
	@Override
	public String toString(){
		return "";
	}
	
}
