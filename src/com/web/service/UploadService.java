package com.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.web.dao.UploadDao;
import com.web.entity.Org_employee;
import com.web.entity.Upload;

@Service("uploadService")
@Transactional
public class UploadService {
	@Autowired
	public UploadDao uploadDao;
	
	@SuppressWarnings("unchecked")

	public List<Map> listAllupload(Map map){
		return uploadDao.listAllupload(map);
	}
	/**
	 * 查询表
	 * @param map
	 * @return
	 */
	public List<Map> searchupload(Map map){
		return uploadDao.searchupload(map);
	}
	/**
	 * 查询表记录数
	 * @param map
	 * @return
	 */
	public int countupload(Map map){
		return uploadDao.countupload(map);
	}
	/**
	 * 根据id查询文档实体
	 * 
	 * @param id
	 * @return
	 */
	public Upload getuploadInfoById(int id) {
		return uploadDao.getuploadInfoById(id);
	}
	/**
	 * 保存 
	 * 
	 * @param admin
	 */
	public void saveuploadInfoId(Upload saveuploadInfo) {
		uploadDao.saveuploadinfo(saveuploadInfo);
	}
	
	
	/**
	 * 保存用户信息
	 * @param employee
	 */
	public void  saveupload(Upload Upload) {
		uploadDao.saveupload(Upload);
	}
	
	public boolean  delupload(String id) {
		return	uploadDao.delupload(id);
	}
	
	public boolean  deluploadimg(String id) {
		return	uploadDao.deluploadimg(id);
	}
	
}