package com.diyun.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * sqlite�����ֱ࣬�Ӵ�������ʾ������������Ӧ�Ľ�ڼ��ɶ�sqlite���ݿ���в���
 * 
 * ������� sqlite jdbc v56
 * 
 * @author haoqipeng
 */
public class SqliteHelper { 
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String dbFilePath;
    
    /**
     * ���캯��
     * @param dbFilePath sqlite db �ļ�·��
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public SqliteHelper(String dbFilePath) throws ClassNotFoundException, SQLException {
        this.dbFilePath = dbFilePath;
        connection = getConnection(dbFilePath);
    }
    
    /**
     * ��ȡ���ݿ�����
     * @param dbFilePath db�ļ�·��
     * @return ���ݿ�����
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection(String dbFilePath) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");       
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
        return conn;
    }
    
    /**
     * ִ��sql��ѯ
     * @param sql sql select ���
     * @param rse ��������������
     * @return ��ѯ���
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public <T> T executeQuery(String sql, ResultSetExtractor<T> rse) throws SQLException, ClassNotFoundException {
        try {
            resultSet = getStatement().executeQuery(sql);
            T rs = rse.extractData(resultSet);
            return rs;
        } finally {
            destroyed();
        }
    }
    
    /**
     * ִ��select��ѯ�����ؽ���б�
     * 
     * @param sql sql select ���
     * @param rm ������������ݴ��������
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public <T> List<T> executeQuery(String sql, RowMapper<T> rm) throws SQLException, ClassNotFoundException {
        List<T> rsList = new ArrayList<T>();
        try {
            resultSet = getStatement().executeQuery(sql);
            while (resultSet.next()) {
                rsList.add(rm.mapRow(resultSet, resultSet.getRow()));
            }
        } finally {
            destroyed();
        }
        return rsList;
    }
    public String executeQuery(String sql) throws SQLException, ClassNotFoundException {
        List<String> rsList = new ArrayList<String>();
        try {
            resultSet = getStatement().executeQuery(sql);
            while (resultSet.next()) {
                rsList.add(resultSet.getString("CMD")+","+resultSet.getInt("ID"));
            }
        } finally {
            destroyed();
        }
        if(rsList.size()==0){
        	return null;
        }
        return rsList.get(0);
    }
    
    
    
    /**
     * ִ�����ݿ����sql���
     * @param sql
     * @return ��������
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        try {
            int c = getStatement().executeUpdate(sql);
            return c;
        } finally {
            destroyed();
        }       
    }

    /**
     * ִ�ж��sql�������
     * @param sqls
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void executeUpdate(String...sqls) throws SQLException, ClassNotFoundException {
        try {
            for (String sql : sqls) {
                getStatement().executeUpdate(sql);
            }
        } finally {
            destroyed();
        }
    }
    
    /**
     * ִ�����ݿ���� sql List
     * @param sqls sql�б�
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void executeUpdate(List<String> sqls) throws SQLException, ClassNotFoundException {
        try {
            for (String sql : sqls) {
                getStatement().executeUpdate(sql);
            }
        } finally {
            destroyed();
        }
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        if (null == connection){        	
        	connection = getConnection(dbFilePath);
        }
        return connection;
    }
    
    private Statement getStatement() throws SQLException, ClassNotFoundException {
        if (null == statement){ 
        	statement = getConnection().createStatement();
        }
        return statement;
    }
    
    /**
     * ���ݿ���Դ�رպ��ͷ�
     */
    public void destroyed() {
        try {
        	if (null != statement) {     
                statement.close();          
                statement = null;
            }
            if (null != connection) {
                connection.close();
                connection = null;
            }               
            if (null != resultSet) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {
            System.out.println("sqlite close exception:"+ e);
        }
    }
}
