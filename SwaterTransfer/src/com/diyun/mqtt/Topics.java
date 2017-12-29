package com.diyun.mqtt;

public class Topics {
	
	public static final int MODE_CUSTOMER_RELEASE = 0x00;		//release
	public static final int MODE_CUSTOMER_DEBUG   = 0x01;		//shang mai
	public static final int MODE_INNER_DEBUG      = 0x02;		//inner
	
	private static int mode = MODE_CUSTOMER_RELEASE;
	
	public static final String CLIENT_ID(){
		return (mode == MODE_CUSTOMER_RELEASE) ? ("swater-transfer-jiangsu-release-") : ( (mode == MODE_CUSTOMER_DEBUG) ? "sm-swater-consumer-debug-" : "sm-swater-consumer-inner-debug-");
	}
	
	public static String SWATER_TOPIC_PREFIX(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater") 	: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater" 	: 	"InnerDebug/Swater");
	}
	
	public static String SWATER_TOPIC_MATCH_ALL(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/#") 	: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/#" 	: 	"InnerDebug/Swater/#");
	}
	
	public static String SWATER_TOPIC_01_HOUSE_TEMP_HUMI(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/house/temp_humi") 	: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/house/temp_humi" 	: 	"InnerDebug/Swater/house/temp_humi");
	}
	public static String SWATER_TOPIC_02_HOUSE_NOISE(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/house/noise") 		: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/house/noise" 		: 	"InnerDebug/Swater/house/noise");
	}
	public static String SWATER_TOPIC_03_PUMP_FACE(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/device/pump_face") 	: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/device/pump_face" 	: 	"InnerDebug/Swater/device/pump_face");
	}
	public static String SWATER_TOPIC_04_WATER_QUALITY(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/house/water_quality"): 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/house/water_quality": 	"InnerDebug/Swater/house/water_quality");
	}
	public static String SWATER_TOPIC_05_RESERVOIR(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/device/reservoir") 	: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/device/reservoir" 	: 	"InnerDebug/Swater/device/reservoir");
	}
	public static String SWATER_TOPIC_06_ELEC_ENERGY(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/house/elec") 		: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/house/elc" 			: 	"InnerDebug/Swater/house/elec");
	}
	public static String SWATER_TOPIC_07_RUN_STATUS(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/device/run_status") 	: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/device/run_status" 	: 	"InnerDebug/Swater/device/run_status");
	}
	public static String SWATER_TOPIC_08_WATER_INFLOW(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/device/water_inflow"): 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/device/water_inflow" : 	"InnerDebug/Swater/device/water_inflow");
	}
	public static String SWATER_TOPIC_09_WATER_OUTFLOW(){ 
		return (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/device/water_outflow"): 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/device/water_outflow" : "InnerDebug/Swater/device/water_outflow");
	}
	public static String SWATER_TOPIC_99_WARNING(){ 
		return  (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/system/warning") 	: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/system/warning" 	: 	"InnerDebug/Swater/system/warning");
	}
	public static String SWATER_TOPIC_UNHANDLED(){ 
		return  (mode == MODE_CUSTOMER_RELEASE) ? ("Release/Swater/system/unhandled") 	: 	( (mode == MODE_CUSTOMER_DEBUG) ? "Debug/Swater/system/unhandled" 	:	"InnerDebug/Swater/system/unhandled");
	}
	
	public static void setWorkMode(int mode){
		if(mode > MODE_INNER_DEBUG)
			return;
		Topics.mode = mode;
	}	
}
