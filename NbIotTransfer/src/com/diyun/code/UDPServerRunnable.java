package com.diyun.code;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import com.diyun.config.Config;
import com.diyun.db.SqliteHelper;
import com.diyun.tool.CRC8;
import com.diyun.tool.Util;

public class UDPServerRunnable implements Runnable{
	private DatagramPacket data = null;
	private DatagramSocket socket = null;
	private Publisher publisher = null;
	private SqliteHelper db = null;
	static int count  = 0;
	public UDPServerRunnable(DatagramPacket data, DatagramSocket socket, Publisher publisher, SqliteHelper db){
		this.data = data;
		this.socket = socket;
		this.publisher = publisher;
		this.db = db;
	}
	
	public void run() {
		try {
			byte[] bt = new byte[data.getLength()];
			System.arraycopy(data.getData(), 0, bt, 0, data.getLength());
			String rev = Util.bytesToHexString(bt);
			Util.log("server recive num:" + (count++) + ", msg:" + rev);	
			if(bt.length > 18){
				String code = checkData(bt);	
				String sends = "ABEF"+rev.substring(4,18)+Util.getBCDTime()+"0200"+code+"00";
				String ip = Util.formatIp(bt[3], bt[4], bt[5], bt[6]);
				if(code == "00"){					
					int type = (bt[8] & 0xFF);
					publish(bt, type, ip);
				}
				String dbcmd = getDbCmd(ip);
				if(null == dbcmd){
					byte[] buf = Util.hexStringToBytes(sends);
					byte crc = CRC8.calcCrc8(buf);
					byte[] send = new byte[buf.length+3];
					System.arraycopy(buf, 0, send, 0, buf.length);
					send[buf.length] = crc;
					send[buf.length+1] = (byte)0xDD;
					send[buf.length+2] = (byte)0xEE;
					data.setData(send);
					socket.send(data);
					Util.log("normal server send:"+Util.bytesToHexString(send));
				}else{
					String cmd = dbcmd.split(",")[0];
					int id = Integer.valueOf(dbcmd.split(",")[1]);
					data.setData(Util.hexStringToBytes(cmd));
					socket.send(data);
					updateDbStatus(id);
					Util.log("cmd server send:"+cmd);
				}
				
			}else{
				Util.log("wrong data-->length less than 18.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void publish(byte[] data, int type, String ip){
		String topic = null;
		switch (type) {
		case 0x01:
		case 0x05:
			topic = Config.MQTT_UP_TOPIC_HEAD+ip+Config.MQTT_UP_TOPIC_TAIL_DEVICE;
			break;
		case 0x02:
		case 0x03:
		case 0x04:
			topic = Config.MQTT_UP_TOPIC_HEAD+ip+Config.MQTT_UP_TOPIC_TAIL_HEAT;
			break;
		default:
			topic = null;
			break;
		}
		if(topic != null){
			publisher.send(data, topic);
		}else{
			Util.log("unknow data!");
		}
	}
	private String checkData(byte[] data){
        int len = data.length;
        String code = "00";
        if(!checkLen(data)){
        	code = "04";
        	return code;
        }
        if(!checkVersion(data)){
        	code = "01";
        	return code;
        }
        if(!checkProject(data)){
        	code = "02";
        	return code;
        }
        if(!checkType(data)){
        	code = "03";
        	return code;
        }
        byte[] crc8Buf = new byte[len-3];
        System.arraycopy(data,0,crc8Buf,0,len-3);
        if(!Util.checkCRC8(crc8Buf, data[len-3])){ 
        	code = "06";
            return code;
        }
        Util.log("checking data, code is:"+code);
        return code;
    }
	
	private boolean checkLen(byte[] data){
		int len = data.length;
		boolean check = false;
		if(len < 22){
			return check;
		}
		switch (data[8]&0xFF) {
		case 0x01:
			if(len == 32){
				check = true;
			}
			break;
		case 0x02:
		case 0x03:
		case 0x04:
			if(len == 33){
				check = true;
			}
			break;
		case 0x05:
			if(len == 44){
				check = true;
			}
			break;
		default:
			break;
		}
		return check;
	}
	
	private boolean checkType(byte[] data){
		int type = (data[8]&0xFF);
		int[] types = Config.TYPES;
		for(int i = 0; i < types.length; i++){
			if(types[i] == type){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkProject(byte[] data){
		int project = (data[7]&0xFF);
		int[] projects = Config.PROJECTS;
		for(int i = 0; i < projects.length; i++){
			if(projects[i] == project){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkVersion(byte[] data){
		int version = (data[2]&0xFF);
		int[] versions = Config.VERSIONS;
		for(int i = 0; i < versions.length; i++){
			if(versions[i] == version){
				return true;
			}
		}
		return false;
	}
	
	private String getDbCmd(String ip){
		String sql = "SELECT * FROM "+Config.TABLE_NAME+" WHERE IP = '"+ip+"' AND STATUS = 0 ORDER BY ID DESC";
//		List<String> list = null;
//		try{
//			list = db.executeQuery(sql, new RowMapper<String>() {
//	        public String mapRow(ResultSet rs, int index)
//	            throws SQLException {
//	            	return rs.getString("CMD")+","+rs.getInt("ID");
//	            }
//			});
//		}catch (Exception e) {
//			System.out.println("getDbCmd():checkDB exception");
//		}
//		if(null == list || list.size()==0){
//			return null;
//		}		
		String res = null;
		try{
			res = db.executeQuery(sql);
		}catch (Exception e) {
			System.out.println("checking db contains cmd exception."+e.toString());
		}
		return res;	
	}
	
	private void updateDbStatus(int id){
		String sql = "UPDATE "+Config.TABLE_NAME+" SET STATUS  = "+1+" WHERE ID = "+id;
		try{
			db.executeUpdate(sql);
		}catch (Exception e) {
			System.out.println("update db failed");
		}
	}
	
}