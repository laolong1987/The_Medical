/**
 * 
 */
package com.common;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.entity.Bo_bonusdetail;

/**
 * @author gaoyang
 *
 */
@Repository("BaseDao")
public class BaseDao {
    @Autowired
    SessionFactory sessionFactory;
    
    /**
	 * 保存或者更新实体
	 * @param sql
	 * @param entry
	 */
	public void save(Object entry){
		sessionFactory.getCurrentSession().saveOrUpdate(entry);
	}
	
	/**
	 * 根据sql返回单个查询结果
	 * @param sql
	 * @return
	 */
	public Object getUniqueResult(String sql){
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return query.uniqueResult();
	}

	/**
	 * 根据sql和param返回单个查询结果
	 * @param sql
	 * @param param
	 * @return
	 */
	public Object getUniqueResult(String sql, Map param){
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setProperties(param);
		return query.uniqueResult();
	}
	
	/**
	 * 查询对象集合
	 * @param sql
	 * @param entry
	 * @return
	 */
	public List findObjects(String sql, Object entry) {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setProperties(entry);
		return query.list();
	}
	
	/**
	 * 删除方法（参数必须是ids）
	 * @param sql
	 * @param ids
	 */
	public void removeByIds(String sql, List<Integer> ids) {		
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
	
	/**
	 * 删除对象
	 * @param entry
	 */
	public void remove(Object entry) {		
		sessionFactory.getCurrentSession().delete(entry);
	}
	
	/**
	 * 根据id查询对象
	 * @param id
	 * @param entry
	 * @return
	 */
	public Object getObjectById(Integer id, Object entry) {
		String className = entry.toString().substring(entry.toString().lastIndexOf('.') + 1);
		String sql = "from " + className + " t where id = :id";
		if (id != null && id > 0) {
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			query.setInteger("id", id);
			List<Object> list = query.list();
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}
	
	public Object getObjectById(Integer id,  Serializable arg1) {
	    return sessionFactory.getCurrentSession().get((Class) arg1, id);
	    
	}
	
	/**
	 * 查询方法（页面表格用）
	 * @param sql sql
	 * @param param 参数
	 * @return
	 */
	public SearchTemplate search(String sql, Map param) {
		SearchTemplate template = new SearchTemplate(sql);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(template.getOrderSql(param));
		if(param.get("page") != null && param.get("pageSize") != null){
			int page = Integer.parseInt(param.get("page").toString());
			int pageSize = Integer.parseInt(param.get("pageSize").toString());
			query.setFirstResult(page == 1 ? 0 : (page - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		query.setProperties(param);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		template.setValues(query.list());
		// 总条数
		query = sessionFactory.getCurrentSession().createSQLQuery(template.getCountSql());
		query.setProperties(param);
		template.setCount(((BigInteger) query.uniqueResult()).intValue());
		return template;
	}
	
	/**
	 * 高级查询方法（页面表格用）
	 * @param sql sql
	 * @param param 参数 
	 * @return
	 */
	public SearchTemplate searchadvanced(String sql, Map param) {
		SearchTemplate template = new SearchTemplate(sql);
		Query query = sessionFactory.getCurrentSession().createSQLQuery(template.getAdvancedSql(param));
		if(param.get("page") != null && param.get("pageSize") != null){
			int page = Integer.parseInt(param.get("page").toString());
			int pageSize = Integer.parseInt(param.get("pageSize").toString());
			query.setFirstResult(page == 1 ? 0 : (page - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		query.setProperties(param);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		template.setValues(query.list());
		// 总条数
		query = sessionFactory.getCurrentSession().createSQLQuery(template.getCountSql());
		query.setProperties(param);
		template.setCount(((BigInteger) query.uniqueResult()).intValue());
		return template;
	}
	
	/**
	 * 根据sql查询返回List<Map>
	 * @param sql sql
	 * @param param 参数
	 * @return
	 */
	public List findResult(String sql, Map param) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setProperties(param);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	/**
	 * 根据sql查询返回List<Entity>
	 * @param sql
	 * @param param
	 * @param entry
	 * @return
	 */
	public List findObjects(String sql, Map param, Class entry){		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(entry);
		query.setProperties(param);
		return query.list();
	}

	/**
	 * 执行sql操作语句
	 * @param sql
	 * @param params
	 */
	public void executeSql(String sql,Map params) {		
		try {
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.setProperties(params);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
