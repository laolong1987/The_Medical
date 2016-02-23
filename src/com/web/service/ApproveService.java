package com.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.dao.ApproveDao;
import com.web.entity.Apply_role;
import com.web.entity.Modify_name;
import com.web.entity.Org_employee;

@Service("approveService")
@Transactional
public class ApproveService {
	
	@Autowired
	public ApproveDao approveDao; 
	/**
	 * Get the total count of pending approval list.
	 * @param map
	 * @return
	 */
	public int searchApprovalCount(Map<String,String> map){
		return approveDao.searchApprovalCount(map);
	}
	
	
	
	/**
	 * Get the pending approval list.
	 * @param map
	 * 
	 */
	public List<Map> getAllApprovalist(Map<String,String> map){
		return approveDao.getAllApprovalist(map);
	}

	/**
	 * Save one new org_employee obj and update modify_name obj
	 * @param emp
	 * @param modify
	 */
//	public void approveEmp(Org_employee oldEMP, Org_employee newEMP, Org_employee existemp, Modify_name modify){
//		approveDao.updateEMP(oldEMP);
//		if(existemp != null){
//		   approveDao.updateEMP(existemp);
//		}
//		approveDao.updateMDF(modify);
//		approveDao.approveEmp(newEMP);
//		
//	}
	/**
	 * 修改申请信息
	 * @param modify
	 */
	public void modifyMDF(Modify_name modify){
		approveDao.updateMDF(modify);
	}
	
	/**
	 * 
	 * @param emp
	 */
	public void updateEMP(Org_employee emp){
		approveDao.updateEMP(emp);
	}
	/**
	 * Get org employee by ewid
	 * @param ewid
	 * @return
	 */
//	public Org_employee getEmployeeByEwid(String ewid){
//		return approveDao.getEmployeeByEwid(ewid);
//	}
	/**
	 * 根据id查找modify_name
	 */
	public Modify_name getMDFByID(String id){
		return approveDao.getMDFByID(id);
	}
	
	/**
	 * 拒绝申请
	 * @param modify
	 */
	public void rejectMDF(Modify_name modify){
		approveDao.updateMDF(modify);		
	}
	
	/**
	 * Get the pending approval list.
	 * @param map
	 * 
	 */
	public List<Map> getApplyRoleList(Map<String,String> map){
		return approveDao.getApplyRoleList(map);
	}
	
	public int getApplyRoleListCount(Map<String,String> map){
		return approveDao.getApplyRoleListCount(map);
	}
	
	public Apply_role getApplyRoleById(String id){
		Map map=new HashMap<String,String>();
		map.put("id", id);
		List<Apply_role> applyroleList=approveDao.serchApply_role(map);
		if(applyroleList.size()>0){
			return applyroleList.get(0);
		}else{
			return null;
		}
	}
	
	public void saveApplyRole(Apply_role applyrole){
		approveDao.saveApplyRole(applyrole);
	}
}
