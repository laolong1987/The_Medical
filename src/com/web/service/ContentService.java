package com.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utils.StringUtil;
import com.web.dao.ContentDao;
import com.web.entity.ClientQuestion;
import com.web.entity.Doc_news;


import com.web.entity.Doc_relation;
import com.web.entity.Recordlog;
import com.web.entity.Upimg;
import com.web.entity.Upload;
import com.web.entity.Viwepager;
import com.web.entity.Viwepagerdeta;



@Service("contentService")
@Transactional
public class ContentService {
	@Autowired
	public ContentDao contentDao;
	
	@SuppressWarnings("unchecked")
	public List<Map> listAlldocnews(Map map){
		return contentDao.listAlldocnews(map);
	}
	public Map getdocCount(Map p){
		return contentDao.getdocCount(p);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> listshuowdocnews(Map map){
		return contentDao.listshuowdocnews(map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map> listshowimg(Map map){
		return contentDao.listshowimg(map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Upimg> getimgxh(Map map){
		return contentDao.getimgxh(map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getrelationInfoById(Map map){
		return contentDao.getrelationInfoById(map);
	}
	
	/**
	 * 查询表
	 * @param map
	 * @return
	 */
	public List<Map> searchDocnews(Map map){
		return contentDao.searchDocnews(map);
	}
	/**
	 * 查询表记录数
	 * @param map
	 * @return
	 */
	public int countDocnewsl(Map map){
		return contentDao.countDocnews(map);
	}
	
	
	
	/**
	 * 查询img表
	 * @param map
	 * @return
	 */
	public List<Map> searchimg(Map map){
		return contentDao.searchimg(map);
	}
	/**
	 * 查询img表记录数
	 * @param map
	 * @return
	 */
	public int countimgsl(Map map){
		return contentDao.countimg(map);
	}
	
	/**
	 * 根据id查询文档实体
	 * 
	 * @param id
	 * @return
	 */
	public Doc_news getcontentInfoById(int id) {
		return contentDao.getcontentInfoById(id);
		
		
		
		
	}
	
	
	/**
	 * 根据id查询文档实体
	 * 
	 * @param id
	 * @return
	 */
	public Doc_relation getrelationInfoById(int id) {
		return contentDao.getrelationInfoById(id);
		
		
		
		
	}
	
	/**
	 * 保存轮播图信息
	 * @param employee
	 */
	public void  saveuploadlistimg(Upimg upimg) {
		contentDao.saveuploadlistimg(upimg);
	}
	/**
	 * 保存img信息
	 * @param employee
	 */
	public void  saveuploadimg(Viwepagerdeta viwepagerdeta) {
		contentDao.saveuploadimg(viwepagerdeta);
	}
	
	
	/**
	 * 保存用户点击公告的信息数据插入数据库
	 * 
	 * @param id
	 * @return
	 */
	public void saverizhifo(Recordlog recordlog) {
	
		
		
		 contentDao.saverecordlog(recordlog);
		
	}
	
	
	/**
	 * 根据id查询img实体
	 * 
	 * @param id
	 * @return
	 */
	public Upimg getimgInfoById(int id) {
		return contentDao.getimgInfoById(id);
	}
	
	/**
	 * 保存 
	 * 
	 * @param admin
	 */
	public void savecontentInfoId(Doc_news doc_news) {
		contentDao.savecontentinfo(doc_news);
	}

	
	/**
	 * 保存 
	 * 
	 * @param admin
	 */
	public void savecontentrela(Doc_relation doc_relation) {
		contentDao.savecontentrela(doc_relation);
	}
	
	/**
	 * 前台显示查询表的信息
	 * @param map
	 * @return
	 */
	public List<Map> searchDocnewswed(Map map){
		return contentDao.searchDocnews(map);
	}
	/**
	 * 删除 
	 * 
	 * @param admin
	 */
	public boolean delcontent(String id) {
		return	contentDao.delcontent(id);
	}
	
	
	public Map<String,String> searchDoc_relation(Map map){
	  List<Map> list=contentDao.searchDoc_relation(map);
	  Map<String,String> resultMap=new HashMap<String,String>();
	  for(Map m:list){
		  resultMap.put(StringUtil.safeToString(m.get("did"), ""), StringUtil.safeToString(m.get("names"), ""));
	  }
	   return resultMap;
	}
	
	
	
	/**
	 * 查询img表
	 * @param map
	 * @return
	 */
	public boolean delrelation(String id){
		return contentDao.delrelation(id);
	}
	
	
}