package com.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utils.StringUtil;
import com.web.dao.AdminDao;
import com.web.entity.Admin;

@Service("adminService")
@Transactional
public class AdminService {
	
	@Autowired
	public AdminDao adminDao;
	
	/**
	 * 验证登陆
	 */
	public Admin checkLogin(Map map) {
		List<Admin> user = adminDao.serchAdmin(map);
		if (user.size() > 0) {
			return user.get(0);
		} else {
			return null;
		}
	}

	public Admin getAdminByUsername(String username) {
		Map map = new HashMap<String, String>();
		map.put("username", username);
		List<Admin> list = adminDao.serchAdmin(map);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 根据request获取username
	 * 
	 * @param id
	 * @return
	 */
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
	public String getAdminId(HttpServletRequest request) {
		String result=StringUtil.safeToString(request.getSession().getAttribute("username"), "");
		return result;
	}
	
	/**
	 * 保存用户信息
	 * @param employee
	 */
	public void  saveAdmin(Admin admin) {
		adminDao.saveAdmin(admin);
	}
	
	/**
	 * 查询Emp信息
	 * @param employee
	 */
	public List<Map> searchEmpInfo(Map<String, String> map) {
		List<Map> list=adminDao.searchEmpInfo(map);
		return list;
	}
	/**
	 * 查询Emp记录总数
	 * @param employee
	 */
	public int searchEmpInfoCount(Map<String, String> map) {
		return adminDao.searchEmpInfoCount(map);
	}	
}
