package com.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utils.StringUtil;
import com.web.dao.EmployeeDao;
import com.web.entity.Modify_name;
import com.web.entity.Msgvalidate;
import com.web.entity.Org_employee;
import com.web.entity.Secretquestion;
import com.web.entity.Shsgm_new;


@Service("EmployeeService")
@Transactional
public class EmployeeService {
	
	@Autowired
	public EmployeeDao employeeDao;
	
	/**
	 * 验证登陆
	 */
	public Org_employee checkLogin(Map map) {
		List<Org_employee> user = employeeDao.serchEmployee(map);
		if (user.size() > 0) {
			return user.get(0);
		} else {
			
			return null;
		}
	}
	/**
	 * 查询用户
	 */
	public Org_employee serchEmployee(Map map){
		List<Org_employee> user = employeeDao.serchEmployee(map);
		if (user.size() > 0) {
			return user.get(0);
		} else {
			
			return null;
		}
	}
	
	/**
	 * 查询用户
	 */
	public List<Org_employee> searchEmployee(Map map){
		List<Org_employee> user = employeeDao.serchEmployee(map);
		return user;
	}
	
	/**
	 * 验证用户名是否存在
	 */
	public boolean checkUsernameIsexit(String username) {
		Map map = new HashMap<String, String>();
		map.put("username", username);
		List<Org_employee> user = employeeDao.serchUserName(map);
		if (user.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 验证用户是否存在
	 */
	public Org_employee checkUserIsexit(Map map) {
		List<Org_employee> user = employeeDao.serchEmployee(map);
		if (user.size() > 0) {
			return user.get(0);
		} else {
			return null;
		}
	}
	
	public List<Map> search_registEmp(Map map) {
		List<Map> user = employeeDao.search_registEmp(map);
		if (user.size() > 0) {
			return user;
		} else {
			return null;
		}
	}
	
	/**
	 * 查询注册用户记录数
	 * @param map
	 * @return
	 */
	public int RegistCount(Map map){
		return employeeDao.searchRegistCount(map);
	}

	public Org_employee getEmployeeByUsername(String username) {
		Map map = new HashMap<String, String>();
		map.put("username", username);
		List<Org_employee> list = employeeDao.serchEmployee(map);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	public Org_employee getEmployeeByEwid(String ewid) {
		Map map = new HashMap<String, String>();
		if(ewid!="" && ewid!=null){
			map.put("ewid", ewid);
			List<Org_employee> list = employeeDao.serchEmployee(map);
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}else{
			return null;
		}
	}
	
	public Org_employee getEmployeeByDossid(String dossid) {
		Map map = new HashMap<String, String>();
		if(dossid!="" && dossid!=null){
			map.put("doss_id", dossid);
			List<Org_employee> list = employeeDao.serchEmployee(map);
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}else{
			return null;
		}
	}
	public Modify_name getMDFByEwid(String ewid){
		return employeeDao.getMDFByEwid(ewid);
	}
	
	public List<Org_employee> getEmpsByDealerCode(String dealercode){
		Map map = new HashMap();
		if(dealercode!="" && dealercode!=null){
			map.put("dealercode", dealercode);
			List<Org_employee> emp= employeeDao.serchEmployee(map);
			if(emp.size()>0){
				return emp;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 根据id查询employee人员实体
	 * 
	 * @param id
	 * @return
	 */
	public Org_employee getEmployeeById(int id) {
		return employeeDao.getEmployeeById(id);
		
	}
	
	/**
	 * 获取EWID
	 */
	public synchronized String getEWID(String dealercode) {
		int r = employeeDao.searchEmployeeByDealercode(dealercode) + 1;
		String result = "" + r;
		if (result.length() == 1) {
			result = "00" + result;
		} else if (result.length() == 2) {
			result = "0" + result;
		}
		return "EW" + dealercode + result;
	}

	
	/**
	 * 根据request获取username
	 * 
	 * @param id
	 * @return
	 */
	public String getEmpUsername(HttpServletRequest request) {
		String result=StringUtil.safeToString(request.getSession().getAttribute("username"), "");
		return result;
	}
	
	/**
	 * 根据request获取id
	 * 
	 * @param request
	 * @return
	 */
	public String getEmpid(HttpServletRequest request) {
		String result=StringUtil.safeToString(request.getSession().getAttribute("id"), "");
		return result;
	}
	
	/**
	 * 保存用户信息
	 * @param employee
	 */
	public void  saveEmployee(Org_employee employee) {
		employeeDao.saveEmployee(employee);
	}
	
	/**
	 * 保存修改申请信息
	 * @param applyinfo
	 */
	public void  saveApplyInfo(Modify_name applyinfo) {
		employeeDao.saveApplyInfo(applyinfo);
	}
	/**
	 * 保存修改申请信息
	 * @param applyinfo
	 */
	public void  saveSecretQandA(Secretquestion secretQ_A) {
		employeeDao.saveSecretQandA(secretQ_A);
	}
	/**
	 * 验证密保信息
	 * @param applyinfo
	 */
	public boolean checkSecretQandA(Map map){
		List<Secretquestion> checkInfo=employeeDao.serchSecretQandA(map);
		if(checkInfo.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取密保信息
	 * @param applyinfo
	 */
	public Secretquestion getSecretQandAByEwid(String ewid){
		if(ewid!="" && ewid!=null){
			return employeeDao.getSecretQandAByEwid(ewid);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 查询登陆
	 * 
	 */
	public List<Org_employee> serchEmployeeall(Map map){
		return employeeDao.serchEmployee(map);
	}
	
	/**
	 * 获取短信验证码
	 * 
	 */
	public Msgvalidate getVertifyCode(Map map){
		List<Msgvalidate> msg=employeeDao.getVertifyCode(map);
		if(msg.size()==1){
			return msg.get(0);
		}else{
			return null;
		}
	}
	
	public void saveVertifyCode(Msgvalidate code){
		employeeDao.saveVertifyCode(code);
	}
	
	/**
	 * 根据DOSSID获取对象
	 */
	public Org_employee getEmployeeByDossidChevy(String dossid) {
		Map map=new HashMap();
		map.put("doss_id", dossid);
		map.put("brand", "Chevy");
		List<Org_employee> list= employeeDao.serchEmployee(map);
		if(list.size()>0){
			return list.get(0);
		}else {
			return null;
		}
	}
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Shsgm_new> searchShsgm_new(Map map){
		return employeeDao.searchShsgm_new(map);
	}
	
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchShsgm_newToMap(Map map){
		return employeeDao.searchShsgm_newToMap(map);
	}
	
	public List<Map> searchShsgm_newToMap3(Map map){
		return employeeDao.searchShsgm_newToMap3(map);
	}
	
	
	public List<Map> searchdealer(Map map){
		return employeeDao.searchdealer(map);
	}
	
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchShsgm_newToMap2(Map map){
		return employeeDao.searchShsgm_newToMap2(map);
	}
	
	
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchMatchdata(Map map){
		return employeeDao.searchMatchdata(map);
	}
	
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchShsgm_newToMap4(Map map){
		return employeeDao.searchShsgm_newToMap4(map);
	}
	
	public List<Org_employee> getEMPWithoutEWID(){
		return employeeDao.getEMPWithoutEWID();
	}
	
	/**
	 * 根据修改后的信息查找人员对象
	 * @param map
	 * @return
	 */
	public Org_employee getEMPByNIDP(Map map){
	    return employeeDao.searchEMPByNIDP(map);
	}
	
	public void updateshemnew(String id, String dossid){
		employeeDao.updateshemnew(id, dossid);
	}
	
	public void updateshemnew2(String id, String idCard){
		employeeDao.updateshemnew2(id, idCard);
	}
}
  