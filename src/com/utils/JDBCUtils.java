package com.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.web.dao.AdminDao;


public class JDBCUtils {
	
	//private static final Log log = 	LogFactory.getLog(JDBCUtils.class);

	/**
	 * 获得数据库连接对象
	 * 
	 * @return
	 */
    public static Connection getConnection(){
    	
    	Connection conn = null;
    	try {
//    		Properties proper = new Properties();
//    		//src/com/utils/jdbc.properties;
//        	InputStream in = new BufferedInputStream(new FileInputStream("/com/utils/jdbc.properties"));
//        	proper.load(in);    	
        	String driver = SettingUtil.getSetting("Driver");
        	String url = SettingUtil.getSetting("url"); 
        	String user = SettingUtil.getSetting("username");
        	String pwd = SettingUtil.getSetting("pwd");
			Class.forName(driver);
		    conn = DriverManager.getConnection(url, user, pwd);		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return conn;
    }
    /**
     * 获取Statement对象
     * @param conn 数据库连接对象
     * @return
     */
    public static Statement getStatement(Connection conn){
    	Statement stm = null;
    	try {
    		stm =  conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stm;
    }
    /**
     * 获取PreparedStatement对象
     * @param conn
     * @param sql
     * @return
     */
    public static PreparedStatement getPreparedStatement(Connection conn, String sql){
    	PreparedStatement pstm = null;
    	try {
			pstm = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstm;
    }
    public static ResultSet getResultSet(PreparedStatement pstm){
    	ResultSet rs = null;
    	try {
			rs = pstm.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
    }
    /**
     * 
     * @param pstm
     * @return
     */
    public boolean updateData(PreparedStatement pstm){
    	boolean isupdate = false;
    		try {
				isupdate = pstm.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return isupdate;
    }
    /**
     * 关闭PreparedStatement
     * @param pstm
     */
    public static void closePreparedStatement(PreparedStatement pstm){
    	try {
			if(pstm != null){
				pstm.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 获取结果集对象
     * @param stm Statement对象
     * @param sql 查询语句
     * @return
     */
    public static ResultSet getResultSet(Statement stm, String sql){
    	ResultSet rs = null;
    	try {
			rs = stm.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rs;
    }
    /**
     * 关闭结果集对象
     * @param rs 结果集
     */
    public static void closeResultSet(ResultSet rs){
    	try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//log.info(e.getMessage());			
		}
    }
    
     /**
     * 关闭数据库Statement对象
     * @param stm
     */
    public static void closeStatement(Statement stm){
    	try {
			if(stm != null){
				stm.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * 关闭连接
     * @param conn
     */
    public static void closeConnection(Connection conn){
    	try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
//    public static void main(String[] args) {
//		try {
//			
//			//String filePath = "src/com/utils/jdbc.properties";
//				//getClass().getClassLoader().getResource("/").getPath() + "..\\" + fileName"jdbc.properties";
//			Connection conn = getConnection();
//			Statement stm = conn.createStatement();
//			ResultSet rs = getResultSet(stm, "select count(id) from distributor");
//			if(rs.next()){
//				System.out.println(rs.getInt(1));
//			}
//		    
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
    
}
