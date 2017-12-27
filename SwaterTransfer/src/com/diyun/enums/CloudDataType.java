package com.diyun.enums;

/**
 * Data type defined on cloud by Fei.Ming(YarlungSoftware)
 * 
 * @author Tx.Loooper
 * @version 2017/12/27 V1.0
 * @since 1.6
 *
 */
public enum CloudDataType {
	
	TYPE_401_TEMPHUMI(401),
	TYPE_402_NOISE(402),
	TYPE_403_ENERGY(403),
	TYPE_404_WORK(404),
	TYPE_405_AMBIENT(405),	
	TYPE_406_INLET(406),
	TYPE_407_WATER_QUALITY(407),
	TYPE_408_WATER_LEVEL(408),
	TYPE_409_OUTLET(409);
	
	private int type;
    
    private CloudDataType(int type) {
        this.type = type;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
       
}
