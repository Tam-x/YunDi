package com.diyun.code;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.diyun.config.Config;
import com.diyun.db.SqliteHelper;

public class Server {

	public static void openServer() throws Exception {
		InetAddress adr = InetAddress.getLocalHost();
//		InetAddress adr = InetAddress.getByName(Config.UDP_SERVER);
		DatagramSocket socket = new DatagramSocket(Config.PORT, adr);
		
		SqliteHelper db = null;
		try{
			db = new SqliteHelper(Config.DB_NAME);        
			String sql = "CREATE TABLE "+Config.TABLE_NAME+" " +
            "(ID INTEGER PRIMARY KEY  AUTOINCREMENT," +
            " IP             TEXT     NOT NULL, " + 
            " CMD            TEXT     NOT NULL, " + 
            " STATUS         INT      NOT NULL, " + 
            " TIME           TEXT)"; 
			db.executeUpdate(sql);
		}catch (Exception e) {
			System.out.println("db is exist..."+db);
		}
		Publisher publisher = new Publisher(db);
		ExecutorService service = Executors.newFixedThreadPool(1);
		System.out.println("server is starting...");
		while (true) {
			byte[] bytes = new byte[1024];
			DatagramPacket data = new DatagramPacket(bytes, bytes.length);
			socket.receive(data);
			service.execute(new UDPServerRunnable(data, socket, publisher, db));
			bytes = null;
		}
	}

	public static void main(String[] args) {
		try {
			openServer();
		} catch (Exception e) {
			System.out.println("server starts failed...");
			e.printStackTrace();
		}
	}
}
