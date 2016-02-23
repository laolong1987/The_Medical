package com.web.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.utils.JDBCUtils;
import com.utils.StringUtil;
import com.web.dao.BonusDao;
import com.web.dao.UserLockDao;
import com.web.entity.Org_employee;
import com.web.entity.UserLockInfo;
import com.web.service.EmployeeService;
import com.web.service.ExportService;

@Component
public class ConfirmAdjustTask {
	
	private static final Log log = LogFactory.getLog(ConfirmAdjustTask.class);
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void confirAdjust(){
		log.info("task---------confirmAdjust-----start");
//		UserLockDao ulDao = new UserLockDao();
//		ulDao.lockUsers();
		Connection conn = JDBCUtils.getConnection();
		Statement stm = JDBCUtils.getStatement(conn);
		StringBuffer sql = new StringBuffer();
		sql.append("update bo_bonusdetail set is_visible = 1 where TIMESTAMPDIFF(DAY,CREATETIME,NOW()) = 257");
		try {
			stm.execute(sql.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    log.info("task---------confirmAdjust-------end");
	}
	
}
