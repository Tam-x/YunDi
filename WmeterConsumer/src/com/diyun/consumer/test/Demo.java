package com.diyun.consumer.test;

import com.diyun.consumer.Consumer;
import com.diyun.consumer.listener.ParameterValueListener;
import com.diyun.consumer.listener.RealTimeValueListener;
import com.diyun.consumer.listener.WarningValueListener;

/**
 * 
 * An example to show how consumer works.
 * 
 * @author Tx.Loooper
 * @version 2018/1/11, v1.0
 * @since 1.8
 *
 */
public class Demo {
	
	private static boolean isSendCmd = false;
	
	public static void main(String args[]){	
		Consumer consumer = new Consumer();
		consumer.setLog(true, true);
		consumer.setUniqueId("mytest");
		consumer.registerRealTimeValueListener(new Demo.ThisRealtimeDataListener());
		consumer.registerParameterValueListener(new Demo.ThisParameterValueListener());
		consumer.registerWarningValueListener(new Demo.ThisWarningValueListener());				
		consumer.open();
		if(isSendCmd){
			sendCMD(consumer);
		}
	}
	
	public static void sendCMD(Consumer consumer){
			int i=0, type = 1;
			while(true){
				try {
					if(type > 5){
						type = 1;
					}
					Thread.sleep(30*1000);
					consumer.pub("120.54.233.110", type++, 2017, 7, 7);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}catch (Exception e1) {
					e1.printStackTrace();
				}
				System.out.println("Consumer sleep " + (i++) + " S");
			}
	}
	
	public static class ThisRealtimeDataListener implements RealTimeValueListener{
		/*
		 *ip:�豸��ַ
		 *dataTime:��������ʱ��
		 *status: �ϱ�״̬
		 *generateTime:���ݲɼ�ʱ��
		 *type:�Ǳ�����
		 *value:�������
		 *
		 * */
		@Override
		public void deliveryRealtimeData(String ip, String dataTime,
				int status, String generateTime, int type, float value) {
			System.out.println("RealtimeData----ip:"+ip+",dataTime:"+dataTime+",generateTime:"+generateTime+",status:"+status+",value:"+value
					);
		}		
	}	
	
	public static class ThisParameterValueListener implements ParameterValueListener{
		/*
		 *ip:�豸��ַ
		 *dataTime:��������ʱ��
		 *type: �Ǳ�����
		 *freezeTime:������ʱ��
		 *wakeupTime:����ʱ��
		 *server:��������ַ
		 *port:�������˿�
		 *meterNumber:
		 *reserver:�����ֶ�
		 * */
		@Override
		public void deliveryParameterData(String ip, String dataTime, int type,
				String freezeTime, String wakeupTime, String server, int port,
				int meterNumber, int reserver) {
			System.out.println("ParameterValue----ip:"+ip+",dataTime:"+dataTime+",server:"+server+",port:"+port+",freezeTime:"+freezeTime+",wakeupTime:"+wakeupTime);
		}
		
	}
	
	public static class ThisWarningValueListener implements WarningValueListener{
		/*
		 *ip:�豸��ַ
		 *dataTime:��������ʱ��
		 *status: �ϱ�״̬
		 *generateTime:���ݲɼ�ʱ��
		 *type:�Ǳ�����
		 *alarm:��ص͵�ѹ����
		 *value:��ص�ѹֵ
		 *reserve:�����ֶ�
		 *
		 * */
		@Override
		public void deliveryBatteryData(String ip, String dataTime, int status,
				String generateTime, int type, int alarm, float value,
				int reserve) {
			System.out.println("BatteryData----ip:"+ip+",dataTime:"+dataTime+",generateTime:"+generateTime+",status:"+status+",value:"+value);
			
		}
		/*
		 *ip:�豸��ַ
		 *dataTime:��������ʱ��
		 *status: �ϱ�״̬
		 *generateTime:���ݲɼ�ʱ��
		 *meterType:�Ǳ�����
		 *alarmType:�Ÿ�������
		 *info:�����ֶ�(������ϢЯ��)
		 *
		 * */
		@Override
		public void deliveryMagneticData(String ip, String dataTime,
				int status, String generateTime, int meterType, int alarmType,
				int info) {
			System.out.println("Magnetic----ip:"+ip+",dataTime:"+dataTime+",generateTime:"+generateTime+",status:"+status+",alarmType:"+alarmType);
			
		}

		/*
		 *ip:�豸��ַ
		 *dataTime:��������ʱ��
		 *status: �ϱ�״̬
		 *generateTime:���ݲɼ�ʱ��
		 *meterType:�Ǳ�����
		 *alarmType:�Ÿ�������
		 *info:�����ֶ�(������ϢЯ��)
		 *
		 * */
		@Override
		public void deliveryOtherAlarmData(String ip, String dataTime,
				int status, String generateTime, int meterType, int alarmType,
				int info) {
			System.out.println("Other----ip:"+ip+",dataTime:"+dataTime+",generateTime:"+generateTime+",status:"+status+",alarmType:"+alarmType);
			
		}
		
	}
}
