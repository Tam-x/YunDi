package com.diyun.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.diyun.beans.DataDevice;
import com.diyun.enums.LogLevel;

/**
 * Loading device's ID and IMEI from file device.xml.
 * 
 * @author Tx.Loooper
 * @version 2017/12/27, V1.0
 * @since 1.6
 *
 */
public class SwaterDevice {

	private static final String TAG = "SwaterDevice";

	public static List<DataDevice> loadDeviceInfo(String path) {
		String fileName = path;
		List<DataDevice> list = new ArrayList<>();
		try {	
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			NodeList employees = document.getChildNodes();
			for (int i = 0; i < employees.getLength(); i++) {
				Node root = employees.item(i);
				NodeList devices = root.getChildNodes();
				for (int j = 0; j < devices.getLength(); j++) {
					Node node = devices.item(j);
					NodeList employeeMeta = node.getChildNodes();
					if(node.getNodeName().equals("device")){
						DataDevice dev = new DataDevice();
						StringBuilder msg = new StringBuilder();
						for (int k = 0; k < employeeMeta.getLength(); k++) {
							String data = employeeMeta.item(k).getTextContent();
							String name = employeeMeta.item(k).getNodeName();
							if(name.equals("id")) dev.setDtuID(Integer.parseInt(data));
							else if(name.equals("imei")) dev.setIMEI(data);
							if(!name.contains("#"))msg.append(data+"  ");
						}
						Util.log(TAG, msg.toString(), LogLevel.SYS);
						list.add(dev);
					}				
				}
			}
		} catch (FileNotFoundException e) {			
			Util.log(TAG, "device.xml is not been found.", LogLevel.SYS);
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		} catch (SAXException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return list;
	}
}
