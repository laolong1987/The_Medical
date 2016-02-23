package com.web.task;

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

import com.utils.StringUtil;
import com.web.dao.UserLockDao;
import com.web.entity.Org_employee;
import com.web.entity.UserLockInfo;
import com.web.service.EmployeeService;
import com.web.service.ExportService;

@Component("userLockTask")
public class UserLockTask {

	@Autowired 
	private ExportService exportService;
	
	@Autowired
	private EmployeeService empService;
	
	private static final Log log = LogFactory.getLog(UserLockTask.class);
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void lockUsers(){
		log.info("task---------updatCconstellation-----start");
		//Map map = new HashMap();
//		List<Map> list  = exportService.getLockingUsers();
//		Calendar cal = Calendar.getInstance();
//		Date date = cal.getTime();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    for(Map empmap:list){
//	    	String ewid = StringUtil.safeToString(empmap.get("ewid"), "");
//	    	String dis_status = StringUtil.safeToString(empmap.get("dis_status"), "");
//	    	String dismission_time = StringUtil.safeToString(empmap.get("dismission_time"), "");
//	    	String service_status = StringUtil.safeToString(empmap.get("inservice"), "");
//    		Org_employee emp = empService.getEmployeeByEwid(ewid);
//    		if(emp != null){	    		
//	    		//Save locked users info as record
//	    		UserLockInfo ulInfo = new UserLockInfo();
//	    		ulInfo.setEwid(ewid);
//	    		ulInfo.setLockDate(sdf.format(date));
//	    		if(!"".equals(dis_status)){
//	    			if("0".equals(dis_status)){
//	    				ulInfo.setLockReason("此人所在的店为待上线，暂时锁定该人员信息");
//	    			}else if("2".equals(dis_status)){
//	    				ulInfo.setLockReason("此人所在的店已停业，锁定该人员信息");
//	    			}else if("3".equals(dis_status)){
//	    				ulInfo.setLockReason("此人所在的店已下线，锁定该人员信息");
//	    			}	    			
//	    			ulInfo.setLockType(0);
//	    			//Lock emp login account
//		    		emp.setIsactived(2);
//		    		emp.setUpdatetime(date);
//		    		empService.saveEmployee(emp);
//	    			
//	    		}else{
//	    			if(!"".equals(dismission_time)){
//	    				ulInfo.setLockReason("此人的就业状态为:"+service_status+"，锁定该人员信息");
//	    				//Lock emp login account
//	    				ulInfo.setLockType(1);
//			    		emp.setIsactived(2);
//			    		emp.setUpdatetime(date);
//			    		empService.saveEmployee(emp);
//	    			}
//	    		}
//	    		exportService.saveUserLockInfo(ulInfo);
//    		}	    	
//	    }
		UserLockDao ulDao = new UserLockDao();
		ulDao.lockUsers();
	    log.info("task---------updatCconstellation-------end");
	}
}
