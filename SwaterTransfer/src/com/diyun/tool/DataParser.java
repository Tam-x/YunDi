package com.diyun.tool;

import java.math.BigDecimal;
import com.diyun.beans.AmbientData;
import com.diyun.beans.DTUDataBean;
import com.diyun.beans.EnergyData;
import com.diyun.beans.InletWaterData;
import com.diyun.beans.NoiseData;
import com.diyun.beans.OutletWaterData;
import com.diyun.beans.TempHumiData;
import com.diyun.beans.WaterLevelData;
import com.diyun.beans.WaterQualityData;
import com.diyun.beans.WorkData;
import com.diyun.util.Global;

public class DataParser {
	
	private static final int  TEMPHUMI      = 0x01;
	private static final int  NOISE         = 0x02;
	private static final int  AMBIENT       = 0x03;
	private static final int  WATER_QUALITY = 0x04;
	private static final int  WATER_LEVEL   = 0x05;
	private static final int  ENERGY        = 0x06;
	private static final int  WORK          = 0x07;
	private static final int  INLET_VALVE   = 0x08;
	private static final int  OUTLET_VALVE  = 0x09;
	
	public static String parseData(DTUDataBean bean){
		String result ="";		
		byte[] bytes = bean.getMessage();
		int ip = bean.getDeviceIp();
		int type = bean.getType();
		switch (type) {
		case TEMPHUMI:
			result = parseTempHumi(bytes,ip,type);
			break;			
		case NOISE:
			result = parseNoise(bytes,ip,type);
			break;
		case AMBIENT:
			result = parseAmbient(bytes,ip,type);
			break;
		case WATER_QUALITY:
			result = parseWaterQuality(bytes,ip,type);
			break;
		case WATER_LEVEL:
			result = parseWaterLevel(bytes,ip,type);
			break;
		case ENERGY:
			result = parseEnergy(bytes,ip,type);
			break;
		case WORK:
			result = parseWork(bytes,ip,type);
			break;
		case INLET_VALVE:
			result = parseInletWater(bytes,ip,type);
			break;
		case OUTLET_VALVE:
			result = parseOutletWater(bytes,ip,type);
			break;
		default:
			break;
		}
		return result;
	}
	
	// parse temperature and humility data.
	public static String parseTempHumi(byte[] bytes,int ip,int type){
		TempHumiData data = new TempHumiData();		
		int temp = ((bytes[20] & 0xFF) | (bytes[21] & 0xFF)<< 8);	
		temp = ((temp & 0x8000) > 0)?(temp & 0x7FFFF)*(-1):temp;			
		int humi = ((bytes[22] & 0xFF) | (bytes[23] & 0xFF)<< 8);
		data.setOrder(bytes[19]& 0xFF);
		data.setHouseid(ip);
		data.setDtuid(Global.IMEIS.get(ip));
		data.setOwnType(type);
		data.setTemp((float)temp/100);
		data.setHumidity((float)humi/10);
		String result = data.toString();
		data.setStringBuffer();
		return result;
	}
	
	// parse noise data
	public static String parseNoise(byte[] bytes, int ip, int type){
		NoiseData data = new NoiseData();
		int noise = ((bytes[20] & 0xFF) |(bytes[21] & 0xFF)<< 8);
		data.setOrder(bytes[19]& 0xFF);
		data.setHouseid(ip);
		data.setDtuid(Global.IMEIS.get(ip));
		data.setOwnType(type);
		data.setNoise((float)noise/100);
		String result = data.toString();
		data.setStringBuffer();
		return result;
	}
	
	// parse ambient data
	public static String parseAmbient(byte[] bytes, int ip, int type){
		AmbientData data = new AmbientData();
		int temp = ((bytes[20] & 0xFF) |(bytes[21] & 0xFF)<< 8);
		int amplitude = ((bytes[22] & 0xFF) |(bytes[23] & 0xFF)<< 8);
		data.setOrder(bytes[19] & 0xFF);
		data.setHouseid(ip);
		data.setDtuid(Global.IMEIS.get(ip));
		data.setOwnType(type);
		data.setWtemperature((float)temp/100);
		data.setAmplitude((float)amplitude/100);
		data.setPumpid(bytes[19] & 0xFF);
		String result = data.toString();
		data.setStringBuffer();
		return result;
	}
	
	// parse water quality data
	public static String parseWaterQuality(byte[] bytes, int ip, int type){
		WaterQualityData data = new WaterQualityData();
		int ph  = ((bytes[20] & 0xFF) |(bytes[21] & 0xFF)<< 8);
		int temp = ((bytes[22] & 0xFF) |(bytes[23] & 0xFF)<< 8);
		int terbidity = ((bytes[24] & 0xFF) |(bytes[25] & 0xFF)<< 8);
		int chlorine = ((bytes[26] & 0xFF) |(bytes[27] & 0xFF)<< 8);
		data.setOrder(bytes[19] & 0xFF);
		data.setHouseid(ip);
		data.setDtuid(Global.IMEIS.get(ip));
		data.setOwnType(type);
		data.setPh((float)ph/100);
		data.setWtemperature(temp);
		data.setTerbidity(terbidity);
		data.setChlorine(chlorine);
		String result = data.toString();
		data.setStringBuffer();
		return result;
	}
	
	// parse water level data
	public static String parseWaterLevel(byte[] bytes, int ip, int type){
		WaterLevelData data = new WaterLevelData();
		int waterlevel  = ((bytes[20] & 0xFF) |(bytes[21] & 0xFF)<< 8);
		int flowin = ((bytes[22] & 0xFF) |(bytes[23] & 0xFF)<< 8);
		int flowout = ((bytes[24] & 0xFF) |(bytes[25] & 0xFF)<< 8);
		int pressure = ((bytes[26] & 0xFF) |(bytes[27] & 0xFF)<< 8);
		
		data.setOrder(bytes[19] & 0xFF);
		data.setHouseid(ip);
		data.setDtuid(Global.IMEIS.get(ip));
		data.setOwnType(type);
		data.setWaterlevel((float)waterlevel/100);
		data.setFlowin(flowin);
		data.setFlowout(flowout);
		data.setPressure(pressure);
		String result = data.toString();
		data.setStringBuffer();
		return result;
	}
	
	// parse energy data
	public static String parseEnergy(byte[] bytes, int ip, int type){
		EnergyData data = new EnergyData();		
		int avoltage = ((bytes[24] & 0xFF) |(bytes[25] & 0xFF)<< 8);
		int bvoltage = ((bytes[26] & 0xFF) |(bytes[27] & 0xFF)<< 8);
		int cvoltage = ((bytes[28] & 0xFF) |(bytes[29] & 0xFF)<< 8);
		int costpower = Tool.byte4ToInt(bytes[20], bytes[21], bytes[22], bytes[23]);
		int acurrent  = Tool.byte4ToInt(bytes[30], bytes[31], bytes[32], bytes[33]);
		int bcurrent  = Tool.byte4ToInt(bytes[34], bytes[35], bytes[36], bytes[37]);
		int ccurrent  = Tool.byte4ToInt(bytes[38], bytes[39], bytes[40], bytes[41]);

		data.setOrder(bytes[19] & 0xFF);
		data.setHouseid(ip);
		data.setDtuid(Global.IMEIS.get(ip));
		data.setOwnType(type);
		data.setCostpower((float)((costpower == 0xffffffff) ? -1.0 : transBd(costpower*0.01, 2)));	
		data.setAvoltage((float)((avoltage == 0xffff) ? -1.0 : transBd(avoltage*0.1, 1)));
		data.setBvoltage((float)((bvoltage == 0xffff) ? -1.0 : transBd(bvoltage*0.1, 1)));
		data.setCvoltage((float)((cvoltage == 0xffff) ? -1.0 : transBd(cvoltage*0.1, 1)));	
		data.setAcurrent((float)((acurrent == 0xffffffff) ? -1.0 : transBd(acurrent*0.001, 3)));
		data.setBcurrent((float)((bcurrent == 0xffffffff) ? -1.0 : transBd(bcurrent*0.001, 3)));
		data.setCcurrent((float)((ccurrent == 0xffffffff) ? -1.0 : transBd(ccurrent*0.001, 3)));
		String result = data.toString();
		data.setStringBuffer();
		return result;
	}	
	
	private static double transBd(double d, int b){
		BigDecimal bd  = new BigDecimal(d);
		return bd.setScale(b, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	// parse work data
	private static String parseWork(byte[] bytes, int ip, int type){
		String result = "";		
		int i = 4;//the pump's number.
		StringBuffer sb = new StringBuffer();
		while(i-- > 0){
			if((bytes[22+i]& 0xFF) != 0xFF){
				WorkData data = new WorkData();
				boolean power = ((bytes[20] & 0xFF) == 1)? true : false;
				boolean haswater = ((bytes[21] & 0xFF) == 0)? true : false;
				boolean isrunning = ((bytes[22] & 0xFF)>> 4 == 1)?true : false;
				boolean fault = ((bytes[22] & 0xF) == 1)?true : false;
				
				data.setOrder(bytes[19] & 0xFF);
				data.setHouseid(ip);
				data.setDtuid(Global.IMEIS.get(ip));
				data.setOwnType(type);
				data.setPower(power);
				data.setHaswater(haswater);
				data.setIsrunning(isrunning);
				data.setFault(fault);
				data.setPumpid(bytes[19] & 0xFF << 8 | i);
				sb.append(data.toString()+"\\r\\n");
				data.setStringBuffer();
			}			
		}
		result = sb.toString();
		sb.setLength(0);
		return result;
	}
	private static String parseInletWater(byte[] bytes, int ip, int type){
		InletWaterData data = new InletWaterData();
		int pressure   = Tool.byte2ToInt(bytes[20], bytes[21]);
		int temperature= Tool.byte2ToInt(bytes[22], bytes[23]);
		int flowrate = Tool.byte4ToInt(bytes[24], bytes[25],bytes[26], bytes[27]);
		long totalflow = Tool.byte8ToLong( bytes[28],bytes[29],bytes[30],bytes[31],
							bytes[32],bytes[33],bytes[34],bytes[35]);
		data.setOrder(bytes[19] & 0xFF);
		data.setHouseid(ip);
		data.setDtuid(Global.IMEIS.get(ip));
		data.setOwnType(type);
		data.setPressure((float)pressure/100);
		data.setTemperature((float)temperature/100);
		data.setFlowrate((float)flowrate/1000);
		data.setTotalflow((float)totalflow/1000);
		String result = data.toString();
		data.setStringBuffer();
		return result;
	}
	private static String parseOutletWater(byte[] bytes, int ip, int type){
		OutletWaterData data = new OutletWaterData();
		int pressure   = Tool.byte2ToInt(bytes[20], bytes[21]);
		int temperature= Tool.byte2ToInt(bytes[22], bytes[23]);
		int flowrate = Tool.byte4ToInt(bytes[24], bytes[25],bytes[26], bytes[27]);
		long totalflow = Tool.byte8ToLong( bytes[28],bytes[29],bytes[30],bytes[31],
							bytes[32],bytes[33],bytes[34],bytes[35]);
		data.setOrder(bytes[19] & 0xFF);
		data.setHouseid(ip);
		data.setDtuid(Global.IMEIS.get(ip));
		data.setOwnType(type);
		data.setPressure((float)pressure/100);
		data.setTemperature((float)temperature/100);
		data.setFlowrate((float)flowrate/1000);
		data.setTotalflow((float)totalflow/1000);
		String result = data.toString();
		data.setStringBuffer();
		return result;
	}
}
