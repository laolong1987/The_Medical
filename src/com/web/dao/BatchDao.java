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
import com.web.entity.Batch;

@Repository
public class BatchDao {
	private static final Log log = 	LogFactory.getLog(BatchDao.class);
	@Autowired
	private SessionFactory sessionFactory;
	/**
	 * Get batch according to batch name;
	 * @param name
	 * @return
	 */
	public Batch getBatchByName(String name){
		StringBuffer sql = new StringBuffer();
		sql.append("from Batch where name =:name");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql.toString());		
		query.setString("name", name);
		Batch batch = (Batch) query.uniqueResult();
		return batch;
	}
	/**
	 * Get max batch code;
	 * @return
	 */
	public int getMaxCode(){
		StringBuffer sql = new StringBuffer();
		sql.append("select Max(code) from batch");
		int result = 0;
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}
	/**
	 * Get the code by batch name
	 * @param name
	 * @return
	 */
	public int getCodeByName(String name){
		StringBuffer sql = new StringBuffer();
		sql.append("select code from batch where name = '" + name).append("'");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());		
		//query.setString("name", name);
		Object obj = query.uniqueResult();
		int result = 0;
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}
	
	public void saveBatch(Batch batch){
		sessionFactory.getCurrentSession().save(batch);
	}
	
	
	public List<Map> searchBatchs(Map map){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from batch where 1=1 and code is not null");
sql.append(" order by id ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.list();
		
	}
	
	public String getBatchNamebyCode(int code){
		StringBuffer sql = new StringBuffer();
		sql.append("select name from batch where code = " + code);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		String name =  (String) query.uniqueResult();
		return name;
	}
	
	public Integer getMaxBatchCode(){
		StringBuffer sql = new StringBuffer("select max(code) from batch");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		Integer maxCode = (Integer) query.uniqueResult();
	    
		return maxCode;
	}
}
