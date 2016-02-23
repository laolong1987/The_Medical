package com.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utils.StringUtil;
import com.web.dao.AdminDao;
import com.web.dao.ExportDao;
import com.web.dao.UserLockInfoDao;
import com.web.entity.Admin;
import com.web.entity.UserLockInfo;

@Service("exportService")
@Transactional
public class ExportService {
	
	@Autowired
	private ExportDao exportDao;
	
	@Autowired
	private UserLockInfoDao ulInfoDao;
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public int searchPeopleInfoCount(Map<String,String> p){
		return exportDao.getTotalCount(p);
	}
	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> getAllEmpoyees(Map<String,String> map){		
		List<Map> list=exportDao.getAllEmployees(map);
//		for(Map m:list){
//			String idCard = StringUtil.safeToString(m.get("idcard"), "");
//			if(!"".equals(idCard)&& idCard != null){
//				idCard = idCard.substring(idCard.length()-3, idCard.length());
//			}else
//				idCard="154X";
//			//idCard = idCard.substring(idCard.length()-3, idCard.length());
//			m.put("ewid", "EW"+StringUtil.safeToString(m.get("dealercode"), "")+ idCard);
//		}
		return list;
	}
	
	/**
	 * 查询导出数据
	 * 
	 * @return
	 */
	public List<String[]> searchshsgm(Map map) {
		List<String[]> resultList = new ArrayList<String[]>();
		List<Map> list = exportDao.searchshsgm(map);
		String ewid = "";
		String idCard = "";
		for (Map m : list) {
			String[] ss = new String[13];
//			idCard = StringUtil.safeToString(m.get("idcard"), "");
//			if(!"".equals(idCard)&& idCard != null){
//				idCard = idCard.substring(idCard.length()-3, idCard.length());
//			}else
//				idCard="154X";
//			ewid = "EW"+StringUtil.safeToString(m.get("dealercode"), "");
			//ss[0] = ewid + idCard;
			ss[0] = StringUtil.safeToString(m.get("ewid"), "");
			ss[1] = StringUtil.safeToString(m.get("brand"), "");
			ss[2] = StringUtil.safeToString(m.get("dealercode"), "");
			ss[3] = StringUtil.safeToString(m.get("dealer"), "");
			ss[4] = StringUtil.safeToString(m.get("name"), "");
			ss[5] = StringUtil.safeToString(m.get("position"), "");
			ss[6] = StringUtil.safeToString(m.get("idcard"), "");
//			if ("".equals(ss[5])) {
//				ss[4] = StringUtil.safeToString(m.get("ew_sales"), "");
//			}
			ss[7] = StringUtil.safeToString(m.get("phone"), "");
			ss[8] = StringUtil.safeToString(m.get("bankname"), "");			
		    ss[9] = StringUtil.safeToString(m.get("openbank"), "");
			ss[10] = StringUtil.safeToString(m.get("accountbank"), "");
			ss[11] = StringUtil.safeToString(m.get("doss_id"), "");
			String isActived = StringUtil.safeToString(m.get("isactived"), "");
			if("0".equals(isActived)){
				ss[12] = "未激活";
			}else if("1".equals(isActived)){
				ss[12] = "已激活";
			}else if("2".equals(isActived)){
				ss[12] = "已锁定";
			}				
			resultList.add(ss);
		}
		return resultList;
	}

	public String setEWid(String id) {
		String result = "";
		if (id.length() == 1) {
			result = "000" + id;
		} else if (id.length() == 2) {
			result = "00" + id;
		} else if (id.length() == 3) {
			result = "0" + id;
		} else {
			result = id;
		}
		return result;
	}
	/**
	 * 查找所有的要被锁定的人员信息
	 * @param map
	 * @return
	 */
	public List getLockingUsers(Map map){
		return exportDao.getAllLockingUsers(map);
	}
	/**
	 * 查找所有的要被锁定的人员信息
	 * @return
	 */
	public List getLockingUsers(){
		return exportDao.getAllLockingUsers();
	}
	/**
	 * 查出要被锁定的人员信息的总记录
	 * @param map
	 * @return
	 */
	public int getLockingUsersCount(Map map){
		return exportDao.getLockingUsersCount(map);
	}
	
	public void saveUserLockInfo(UserLockInfo ulInfo){
		ulInfoDao.saveUserLockInfo(ulInfo);
	}
	

    public UserLockInfo getUserLockInfoByEwid(String ewid){
        return ulInfoDao.getUserLockInfoByEwis(ewid);
    }
}
