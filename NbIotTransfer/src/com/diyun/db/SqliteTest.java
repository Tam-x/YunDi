package com.diyun.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.diyun.config.Config;

public class SqliteTest {
    
    public static void testHelper() {
    	SqliteHelper h = null;
        try {
            h = new SqliteHelper(Config.DB_NAME);    
//            h.executeUpdate("drop table if exists CMDDB;");
            List<String> sqls = new ArrayList<String>();
            
            String sql = "CREATE TABLE CMDDB " +
            "(ID INTEGER PRIMARY KEY      AUTOINCREMENT," +
            " IP             TEXT     NOT NULL, " + 
            " CMD            TEXT     NOT NULL, " + 
            " STATUS         INT      NOT NULL, " + 
            " TIME           TEXT)"; 

			String sqls0 = "INSERT INTO CMDDB (IP,CMD,STATUS) " +
			"VALUES ('2.0.26.0', 'AAEEFFFFFFFFF', 0);"; 
			String sqls1 = "INSERT INTO CMDDB (IP,CMD,STATUS) " +
			"VALUES ('2.0.0.0', 'BBEEFFFFFFFFF', 0);"; 
			String sqls2 = "INSERT INTO CMDDB (IP,CMD,STATUS) " +
			"VALUES ('2.0.26.1', 'CCBBEEFFFFFFFFF', 0);"; 
			sqls.add(sql);
			sqls.add(sqls0);
			sqls.add(sqls1);
			sqls.add(sqls2);
            
//            h.executeUpdate(sqls);         
//            h.executeUpdate("create table test(name varchar(20));");
//            h.executeUpdate("insert into test values('SqliteHelper test0');");
//			String sqldd = "SELECT * FROM "+Config.TABLE_NAME+" WHERE IP = '120.57.233.110+' AND STATUS = 0 ORDER BY ID DESC";
			
//			sqldd = "SELECT * FROM CMDDB WHERE IP = '120.57.233.110' AND STATUS = 0 ORDER BY ID DESC";
			List<String> sList = h.executeQuery("select * from "+Config.TABLE_NAME+"", new RowMapper<String>() {
                public String mapRow(ResultSet rs, int index)
                        throws SQLException {
                    return rs.getString("cmd")+","+rs.getInt("status");
                }
            });
            for(int i = 0;i<sList.size();i++)
            	System.out.println(sList.get(i));
            
            System.out.println("=========================");
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(checkDB(h, "120.57.233.110"));
    }
    public static void main(String[] args) {
    	testHelper();
	}
    
	public static String checkDB(SqliteHelper h, String ip){
		String sql = "SELECT * FROM CMDDB WHERE IP = '"+ip+"' AND STATUS = 0 ORDER BY ID DESC";
		List<String> list = null;
		try{
			list = h.executeQuery(sql, new RowMapper<String>() {
	        public String mapRow(ResultSet rs, int index)
	            throws SQLException {
	            	return rs.getString("cmd");
	            }
			});
		}catch (Exception e) {
			System.out.println("checkDB exception");
		}
		if(null == list || list.size()==0){
			return null;
		}
		System.out.println("db lenth:"+list.size());
		return list.get(0);		
	}
}