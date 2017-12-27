package com.diyun.beans;

public class BaseData {
	StringBuffer sb = new StringBuffer();
	public void setStringBuffer(){
		if(sb != null){
			sb.setLength(0);
		}
	}
}
