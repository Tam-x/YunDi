package com.diyun.code;

import java.sql.*;

public class SQLiteJDBC {
  public static void main( String args[] ) {
      Connection c = null;
      Statement stmt = null;
      
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:nbcmd.db");
         stmt = c.createStatement();
         String sql = "CREATE TABLE DOWNCMD " +
                        "(ID INTEGER PRIMARY KEY      AUTOINCREMENT," +
                        " IP             TEXT     NOT NULL, " + 
                        " CMD            TEXT     NOT NULL, " + 
                        " STATUS         INT      NOT NULL, " + 
                        " TIME           TEXT)"; 
         stmt.executeUpdate(sql);
         
         String sqls = "INSERT INTO DOWNCMD (IP,CMD,STATUS) " +
         "VALUES ('1.0.1.0', 'AAEEFFFFFFFFF', 0);"; 
         stmt.executeUpdate(sqls);
         String sqls1 = "INSERT INTO DOWNCMD (IP,CMD,STATUS) " +
         "VALUES ('1.0.2.0', 'BBEEFFFFFFFFF', 0);"; 
         stmt.executeUpdate(sqls1);
         String sqls2 = "INSERT INTO DOWNCMD (IP,CMD,STATUS) " +
         "VALUES ('1.0.1.0', 'CCBBEEFFFFFFFFF', 0);"; 
         stmt.executeUpdate(sqls2);
         
         ResultSet rs = stmt.executeQuery( "SELECT * FROM DOWNCMD;" );
         
         while ( rs.next() ) {
            int id = rs.getInt("id");
            String  name = rs.getString("ip");
            int age  = rs.getInt("status");
            String  address = rs.getString("cmd");
  
            
            System.out.println( "ID = " + id );
            System.out.println( "NAME = " + name );
            System.out.println( "AGE = " + age );
            System.out.println( "ADDRESS = " + address );
            System.out.println();
         }
         rs.close();      
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Opened database successfully");
   }
}