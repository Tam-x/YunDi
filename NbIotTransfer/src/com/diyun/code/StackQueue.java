package com.diyun.code;

import java.util.Enumeration;
import java.util.Stack;
import com.diyun.listener.OnFrameListener;
import com.diyun.tool.Util;

public class StackQueue implements OnFrameListener{
	
	private static Stack<String> stack = new Stack<String>();
	
	public StackQueue(final Publisher pub) {
		new Thread(new Runnable() {		
			public void run() {
				printStack(stack, pub);
			}
		}).start();
	}
	
    private  void printStack(Stack<String> stack, Publisher pub ){
    	while(true){
	        if (!stack.empty()){
	             Enumeration<String> items = stack.elements(); 
	             while (items.hasMoreElements()){
	            	 String element = items.nextElement().toString();
	            	 String[] data = element.split(",");
	            	 pub.send(Util.hexStringToBytes(data[0]), data[1]);
	            	 stack.removeElement(element);
	             } 
	        }
	       try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	}
       
    }
 
	public void onRecive(byte[] data, String topic) {
		stack.push(Util.bytesToHexString(data)+","+topic);
	}
}