package com.diyun.message;

import com.diyun.util.Utility;

/**
 * Filling data to the fixed part of a real time data frame.
 * 
 * @author t.x
 * <br>2018/03/28<br>
 *
 * This frame according to protocol of fzsc-2-mqtt-protocol which was written by Nix.long.
 */
public class FrameInstance {

	public static final int CMD_REAL_TIME = 0x01;
	public static final int CMD_HEART_BEAT = 0x04;

	public static BaseFrame getFrameInstance(byte[] payload) {
		if (payload[11] != CMD_REAL_TIME && payload[11] != CMD_HEART_BEAT) {
			return null;
		}
		BaseFrame msg = new BaseFrame();
		msg.frameLen = Utility.byte2ToInt(payload[3], payload[4]);
		msg.dataLen = msg.frameLen - BaseFrame.FRAME_MIN_LEN;
		msg.projectCategory = payload[5] & 0xFF;
		msg.supplierCode = payload[6] & 0xFF;
		msg.dtuIp = Utility.formatIp(Utility.formatIp(payload[7], payload[8],
				payload[9], payload[10]));
		msg.cmdCode = payload[11] & 0xFF;
		byte[] bDate = new byte[6];
		System.arraycopy(payload, 12, bDate, 0, 6);
		msg.date = Utility.getFormatDate((Utility.formateHexDate(bDate)));
		msg.data = new byte[msg.dataLen];
		if (msg.dataLen > 0) {
			System.arraycopy(payload, BaseFrame.FRAME_DATA_INDEX, msg.data, 0,
					msg.dataLen);
		}
		return msg;
	}

}
