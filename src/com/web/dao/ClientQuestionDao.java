package com.web.dao;

import java.security.Policy.Parameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.utils.StringUtil;
import com.web.entity.ClientQuestion;
import com.web.entity.Replyclient;



//查询用户提出的所有问题和保存用户的提问信息
@Repository
public class ClientQuestionDao {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	//查询用户提问的信息
//	@SuppressWarnings("unchecked")
//	public List<Map> listAllQuestions(Map map ) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("select * from clientquestion where 1=1 ");
//		//sql.append("  and empid = "+empid);
//		return sessionFactory.getCurrentSession()
//		.createSQLQuery(sql.toString()).setResultTransformer(
//				Transformers.ALIAS_TO_ENTITY_MAP).list();
//	}
	@SuppressWarnings("unchecked")
	public List<Map> listAllQuestions(Map parameters){
		StringBuffer sql = new StringBuffer();
		Query query = null;
		sql.append("select c.empid, c.countent,c.status,c.type,c.title,c.id,DATE_FORMAT(c.updatetime,'%Y-%m-%d') updatetime,DATE_FORMAT(c.createtime,'%Y-%m-%d') createtime, rep.repcountent from clientquestion c left join replyclient rep on c.id = rep.hfid left join org_employee emp on c.empid = emp.id where 1=1 ");
		if(null != parameters && !parameters.isEmpty()){
            if(parameters.containsKey("id" ) && !"".equals(parameters.get("id"))){
				sql.append("and c.id = " + parameters.get("id"));
			}
			if(parameters.containsKey("empid") && !"".equals(parameters.get("empid"))){
				sql.append(" and c.empid =" + parameters.get("empid")).append(" ");
			}
			sql.append("order by c.id desc ");
			query = sessionFactory.getCurrentSession().createSQLQuery(
					sql.toString());
			
			if(parameters.containsKey("start")){
				query.setFirstResult(Integer.parseInt(StringUtil.safeToString(parameters
						.get("start"), "0")));
			}
			if(parameters.containsKey("maxResult")){
				query.setMaxResults(Integer.parseInt(StringUtil.safeToString(parameters
						.get("maxResult"), "3")));
			}else{
				query.setMaxResults(3);
			}
			//sql.append(" limit " + parameters.get("start")).append(", ").append(parameters.get("end"));
		}
		//System.out.println(sql);
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.list();
	}
	/**
	 * 查表中所有的记录
	 * @param parameters
	 * @return
	 */
	public Map getTotalCount(Map parameters){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(c.id) from clientquestion c left join replyclient rep on c.id = rep.hfid left join org_employee emp on c.empid = emp.id where 1=1 ");
		if(null != parameters && !parameters.isEmpty()){
            if(parameters.containsKey("id") && null != parameters.get("id")){
				sql.append("and c.id = " + parameters.get("id"));
			}
			if(parameters.containsKey("empid") && null != parameters.get("empid")){
				sql.append(" and c.empid ='" + parameters.get("empid")).append("'");
			}
			
		}
		List list = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).list();
		Map<String,String> totalRow = new HashMap<String,String>();
		//System.out.println(list.get(0).toString());
		totalRow.put("totalCount",list.get(0).toString());
		return totalRow;
	}
	public List<Map> listAllQuestionsByEmpid(int empid ){
		StringBuffer sqlbyid = new StringBuffer();
		sqlbyid.append("selecr * from clientquestion where 1=1 and empid = ");
		List list = sessionFactory.getCurrentSession().createSQLQuery("").setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();	
		return list;
		
	}
	
	//保存用户提问的信息
	public void saveQuetions(ClientQuestion clientquestion){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(clientquestion);
		session.getTransaction().commit();
		session.close();
	}
	
	
	
	//保存用户提问的信息
	public void saveHclient(Replyclient replyclient){
		
		if (0 == replyclient.getId()) {
			sessionFactory.getCurrentSession().save(replyclient);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(replyclient);
			session.getTransaction().commit();
			session.close();
		}
	}
	/**
	 * 查询明细表
	 * @param map
	 * @return
	 */
	public List<Map> searchclient(Map map){
		StringBuffer sql=new StringBuffer();
		sql.append("select c.id,r.id as rid,c.title,c.countent,emp.name empname,r.repcountent,DATE_FORMAT(c.createtime,'%Y-%m-%d') AS ctime,c.empid," +
				"c.status,c.type,DATE_FORMAT(c.updatetime, '%Y-%m-%d' ) AS ptime from  clientquestion c left join replyclient r on c.id=r.hfid " +
				   " left join org_employee emp on c.empid = emp.id where 1=1 ");
		if(map.containsKey("title") && !"".equals(map.get("title"))){
			sql.append("and c.title like '%" + map.get("title") ).append("%' ");
		}
		if(map.containsKey("type") && !"".equals(map.get("type"))){
			sql.append("and c.type = " + map.get("type"));
		}
		if(map.containsKey("state") && !"".equals(map.get("state"))){
			sql.append(" and c.status = " + map.get("state"));
		}
		if(map.containsKey("empname") && !"".equals(map.get("empname"))){
			sql.append(" and emp.name = '" + map.get("empname")).append("'");
		}
		sql.append(" order by c.id desc ");
		//System.out.println(sql.toString());
		
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		if (map.containsKey("firstrs")) {
			query.setFirstResult(Integer.parseInt(StringUtil.safeToString(map
					.get("firstrs"), "1")));
		} else {
			query.setFirstResult(0);
		}
//		if (map.containsKey("maxrs")) {
//			query.setMaxResults(Integer.parseInt(StringUtil.safeToString(map
//					.get("maxrs"), "10")));
//		} else {
//			query.setMaxResults(10);
//		}
		//System.out.println("====" + sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	/**
	 * 查询表记录数
	 * @param map
	 * @return
	 */
	public int countclient(Map map){
		StringBuffer sql=new StringBuffer();
		sql.append("select COUNT(*) from clientquestion where 1=1 ");
		int result = 0;
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}
	
	/**
	 * 根据id查询文档实体
	 * @param id
	 * @return
	 */
	public ClientQuestion getclientInfoById(int id) {		
	return (ClientQuestion) sessionFactory.getCurrentSession().get(
			ClientQuestion.class, id);
	}
	/**
	 * 根据id查询文档实体
	 * @param id
	 * @return
	 */
	public Replyclient getclientInfoByhfId(int id) {
		
		return (Replyclient) sessionFactory.getCurrentSession().get(
				Replyclient.class, id);
	}

	public void removeQCByID(String id){
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("delete from clientquestion where id =:id");
		Session session = sessionFactory.getCurrentSession();
		Transaction ts = session.beginTransaction();
		Query query = session.createSQLQuery(strbuf.toString());
		query.setParameter("id", id);
		query.executeUpdate();
		ts.commit();
		//session.close();		
	}
}
