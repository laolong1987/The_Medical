package com.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.utils.StringUtil;

import com.web.entity.Org_employee;
import com.web.entity.Upload;
@Repository
public class UploadDao {
	private static final Log log = 	LogFactory.getLog(ContentDao.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@SuppressWarnings("unchecked")
	public List<Map> listAllupload(Map map){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from upload where 1=1 ");
//		if (map.containsKey("type")) {
//			sql.append(" and type=").append(map.get("type"));
//		} 
		if(map.containsKey("brand") && !"".equals(map.get("brand"))){
			sql.append(" and brand ='").append(map.get("brand")).append("'");
		}
		sql.append(" order by releasetime desc");
		return sessionFactory.getCurrentSession()
		.createSQLQuery(sql.toString()).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/**
	 * 查询明细表
	 * @param map
	 * @return
	 */
	public List<Map> searchupload(Map map){
		StringBuffer sql=new StringBuffer();
		sql.append("select *,DATE_FORMAT(Releasetime, '%Y-%m-%d %k:%i:%s' ) AS ptime from upload  where 1=1 ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		if (map.containsKey("firstrs")) {
			query.setFirstResult(Integer.parseInt(StringUtil.safeToString(map
					.get("firstrs"), "0")));
		} else {
			query.setFirstResult(0);
		}
		if (map.containsKey("maxrs")) {
			query.setMaxResults(Integer.parseInt(StringUtil.safeToString(map
					.get("maxrs"), "10")));
		} else {
			query.setMaxResults(10);
		}
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	/**
	 * 查询表记录数
	 * @param map
	 * @return
	 */
	public int countupload(Map map){
		StringBuffer sql=new StringBuffer();
		sql.append("select COUNT(*) from upload where 1=1 ");
		int result = 0;
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}
	/**
	 * 根据id查询文档路径实体
	 * @param id
	 * @return
	 */
	public Upload getuploadInfoById(int id) {
		
//		StringBuffer sql = new StringBuffer();
//		sql.append("select * from shsgm_new where 1=1 ").append(" and id =  ").append(id);
//		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
//		System.out.println(query.list().get(0).toString());
//
//		return (Shsgm_new) query.list().get(0);
		
		return (Upload) sessionFactory.getCurrentSession().get(
				Upload.class, id);
	}
	
	/**
	 * 保存路径信息
	 * @param peopleInfo
	 */
	public void saveuploadinfo(Upload contentinfo) {
		if (0 == contentinfo.getId()) {
			sessionFactory.getCurrentSession().save(contentinfo);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(contentinfo);
			session.getTransaction().commit();
			session.close();
		}
	}
	/**
	 * 保存信息
	 * @param employee
	 */
	public void saveupload(Upload upload) {
		if (0 == upload.getId()) {
			sessionFactory.getCurrentSession().save(upload);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(upload);
			session.getTransaction().commit();
			session.close();
		}
	}
public boolean delupload(String id) {
		
		String hql = "delete Upload u where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		
		return (query.executeUpdate() > 0);
	}

public boolean deluploadimg(String id) {
	
	String hql = "delete Upimg u where u.id = ?";
	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	query.setString(0, id);
	
	return (query.executeUpdate() > 0);
}
}
