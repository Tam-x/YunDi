package com.diyun.util;

import com.diyun.bean.LightInfo;
import com.diyun.bean.PmInfo;

/**
 * Parser class.
 * 
 * @author Tx.Loooper
 * @version 
 * @since 1.8
 *
 */
public class Frame {
	public static byte[] downCmd = {Global.FRAME_HEAD_1,Global.FRAME_HEAD_DOWN,
		Global.PROTOCAL_TYPE,0x0,0x0,0x0,0x0,
		Global.PROJECT_VERSION,0x0,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x08,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,Global.FRAME_TAIL_1,Global.FRAME_TAIL_2};
	
	public static byte[] getDownCmd(String ip, int type,  String zigbeeAdr, int channel, int status){
		String zigbee = "";
		if(Integer.valueOf(zigbeeAdr) > 255){
			zigbee = Global.ZIP_HEAD+String.valueOf(Integer.valueOf(zigbeeAdr)>> 8)+"."+
					String.valueOf(Integer.valueOf(zigbeeAdr) - ((Integer.valueOf(zigbeeAdr)>>8)<<8));
		}else{
			zigbee = Global.ZIP_HEAD+"0."+zigbeeAdr;
		}
		byte[] ips = Tool.ipStringTo4Byte(ip);
		byte[] adrs = Tool.ipStringTo4Byte(zigbee);
		System.arraycopy(ips, 0, downCmd, 3, ips.length);
		System.arraycopy(adrs, 0, downCmd, 19, adrs.length);
		downCmd[8] = (byte) type;
		downCmd[23] = (byte) channel;//0- Unknown; 1 - GPRS; 2 - Ethernet.
		downCmd[24] = (byte) status;//status£º1 -100 open, 0 - close
		byte[] crc = new byte[downCmd.length-3];
		System.arraycopy(downCmd, 0, crc, 0, crc.length);
		downCmd[downCmd.length-3] = CRC8.calcCrc8(crc);
		System.out.println("DownCMD:"+ Tool.BytesToHexStringEx(downCmd));
		return downCmd;
	}
	
	public static LightInfo parseLightInfo(byte[] data){
		byte[] zip = new byte[4];
		zip[0] = data[22];
		zip[1] = data[21];
		zip[2] = data[20];
		zip[3] = data[19];
		String zigbee = Tool.BytesToHexStringEx(zip);
		int chanle = data[23] & 0xFF;
		int status = data[24] & 0xFF;
		int press = data[25] & 0xFF | (data[26] & 0xFF)<< 8;
		int flow = data[27] & 0xFF | (data[28] & 0xFF)<< 8;
		short active = (short) (data[29] & 0xFF | (data[30] & 0xFF)<< 8);
		short reactive = (short) (data[31] & 0xFF | (data[32] & 0xFF)<< 8);
		int hz = data[33] & 0xFF | (data[34] & 0xFF)<< 8;
		if(hz > 6500){
			hz = 4998;//work around.
		}
		return new LightInfo(zigbee, chanle, status, (float) (press/100.0),(float) (flow/1000.0),(float) (active/100.0),(float) (reactive/100.0),(float) (hz/100.0));
	}
	
	public static PmInfo parsePMInfo(byte[] data){
		byte[] zip = new byte[4];
		zip[0] = data[22];
		zip[1] = data[21];
		zip[2] = data[20];
		zip[3] = data[19];
		String zigbee = Tool.BytesToHexStringEx(zip);
		int chanle = data[23] & 0xFF;
		int pm2_5 = data[24] & 0xFF | (data[25] & 0xFF) << 8;
		int pm10 = data[26] & 0xFF | (data[27] & 0xFF) << 8;
		return new PmInfo(zigbee, chanle, (float)(pm2_5/10.0), (float)(pm10/10.0));
	}
}
