package com.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.utils.JDBCUtils;

public class UserLockDao {
	private static final Log log = 	LogFactory.getLog(JDBCUtils.class);
	public void lockUsers(){
		StringBuffer sql = new StringBuffer();
		sql.append("select emp.id, emp.dealercode, emp.ewid, emp.name, emp.position, emp.idcard, emp.doss_id, emp.isactived, emp.dimission_time, dis.status dis_status,emp.inservice" + 
					" from org_employee emp" + 
					" inner join distributor dis" + 
					" on emp.dealercode = dis.code" + 
					" where (TO_DAYS(Now()) - TO_DAYS(emp.dimission_time) >30)" + 
					" or (dis.status in (0,2,3) and (TO_DAYS(Now()) - TO_DAYS(dis.unavailable_date)) > 30 )" + 
					" and emp.isactived in (0,1)");
//System.out.println(sql.toString());
		String updateSql = "update org_employee set isactived = 2 where id = ?";
		Connection conn = JDBCUtils.getConnection();
		//Statement stm = JDBCUtils.getStatement(conn);
		PreparedStatement pstm = JDBCUtils.getPreparedStatement(conn,sql.toString());
		PreparedStatement updatepstm = null;
		ResultSet rs = JDBCUtils.getResultSet(pstm);
		int id = 0;
		String ewid = "";
		int dis_status = 0;
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		String insertSql = "";
		String lockReason = "";
		String dimission_time = null;
		int lockType = 0;
		String service_status = "";
				
		try {
			conn.setAutoCommit(false);
			while(rs.next()){
				lockType = 0;
				id = rs.getInt("id");
				dis_status = rs.getInt("dis_status");
				ewid = rs.getString("ewid");
				dimission_time = rs.getString("dimission_time");
				service_status = rs.getString("inservice");
				
				updatepstm = JDBCUtils.getPreparedStatement(conn, updateSql);
				updatepstm.setInt(1, id);
				updatepstm.execute();
				
				
				if(dis_status == 0){
					lockReason = "此人所在的店为待上线，暂时锁定该人员信息";
    			}else if(dis_status == 2){
    				lockReason = "此人所在的店已停业，锁定该人员信息";
    			}else if(dis_status == 3){
    				lockReason = "此人所在的店已下线，锁定该人员信息";
    			}
    			if(!(null == dimission_time) && !(null == service_status)){
    				lockReason = "此人的就业状态为:"+service_status+"，锁定该人员信息";
    				//Lock emp login account
    				lockType = 1;
    			}
				insertSql = "insert into user_lock_info (ewid, lock_date, lock_reason, lock_type) values (?,?,?,?)";				
				updatepstm = JDBCUtils.getPreparedStatement(conn, insertSql);
				updatepstm.setString(1, ewid);
				updatepstm.setString(2, strDate);
				updatepstm.setString(3, lockReason);
				updatepstm.setInt(4, lockType);
				updatepstm.execute();
				
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();				
				log.info(e1.getMessage());
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info(e.getMessage());
		}finally{
			JDBCUtils.closeResultSet(rs);			
			JDBCUtils.closePreparedStatement(updatepstm);
			JDBCUtils.closePreparedStatement(pstm);
			JDBCUtils.closeConnection(conn);
		}
		    
	}

}
