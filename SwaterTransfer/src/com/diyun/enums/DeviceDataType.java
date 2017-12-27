package com.diyun.enums;

/**
 * Data type defined on device by Nix.Long(YarlungSoftware)
 * 
 * @author Tx.Loooper
 * @version 2017/12/27 V1.0
 * @since 1.6
 *
 */
public enum DeviceDataType {
	
	TYPE_01_HOUSE_TEMP_HUMI (0x01),
	TYPE_02_HOUSE_NOISE     (0x02),
	TYPE_03_PUMP_FACE 		(0x03),
	TYPE_04_WATER_QUALITY 	(0x04),
	TYPE_05_RESERVOIR 		(0x05),
	TYPE_06_ELEC_ENERGY 	(0x06),
	TYPE_07_PLC_RUN_STATUS 	(0x07),
	TYPE_08_WATER_INFLOW 	(0x08),
	TYPE_09_WATER_OUTFLOW 	(0x09),	
	TYPE_99_WARNING	 		(0x99);
	private int type;
    
    private DeviceDataType(int type) {
        this.type  = type;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
