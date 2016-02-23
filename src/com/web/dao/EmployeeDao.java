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
import com.web.entity.Admin;
import com.web.entity.Modify_name;
import com.web.entity.Msgvalidate;
import com.web.entity.Org_employee;
import com.web.entity.Secretquestion;
import com.web.entity.Shsgm_new;

@Repository
public class EmployeeDao {

	private static final Log log = LogFactory.getLog(EmployeeDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 查询登陆
	 * 
	 */
	public List<Org_employee> serchEmployee(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from Org_employee where 1=1 ");
		if (map.containsKey("username")) {
			sql.append(" and (username= '").append(map.get("username")).append(
					"' or ewid='").append(map.get("username")).append(
					"' or doss_id='").append(map.get("username")).append("')");
		}
		if (map.containsKey("pwd")) {
			sql.append(" and pwd = '").append(map.get("pwd")).append("'");
		}

		if (map.containsKey("doss_id")) {
			sql.append(" and doss_id = '").append(map.get("doss_id")).append(
					"'");
		}
		if (map.containsKey("ewid")) {
			sql.append(" and ewid = '").append(map.get("ewid")).append("'");
		}
		if (map.containsKey("brand")) {
			sql.append(" and brand = '").append(map.get("brand")).append("'");
		}
		if (map.containsKey("name") && !"".equals(map.get("name"))) {
			sql.append(" and name = '").append(map.get("name")).append("'");
		}
		if (map.containsKey("dealercode")) {
			sql.append(" and dealercode = '").append(map.get("dealercode"))
					.append("'");
		}
		if (map.containsKey("position")) {
			sql.append(" and position = '").append(map.get("position"))
					.append("'");
		}
		if (map.containsKey("idcard") && !"".equals(map.get("idcard"))) {
			sql.append(" and idcard = '").append(map.get("idcard")).append(
					"'");
		}
		
		sql.append(" order by updatetime desc");
//System.out.println("sql == " + sql.toString());	
		log.info(sql);
		Query query = sessionFactory.getCurrentSession().createQuery(
				sql.toString());
		return query.list();
	}

	/**
	 * 查询注册途径的用户
	 * 
	 */
	public List<Map> search_registEmp(Map map) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select * from org_employee where (isregist=1 or doss_id='' or doss_id is null) and isactived=1 ");
		if (map.containsKey("ispass") && !"".equals(map.get("ispass"))) {
			sql.append(" and ispass=").append(map.get("ispass"));
		}
		if (map.containsKey("ewid") && !"".equals(map.get("ewid"))) {
			sql.append(" and ewid=").append(map.get("ewid"));
		}
		if (map.containsKey("dealercode") && !"".equals(map.get("dealercode"))) {
			sql.append(" and dealercode = '").append(map.get("dealercode"))
					.append("'");
		}
		if (map.containsKey("idcard") && !"".equals(map.get("idcard"))) {
			sql.append(" and idcard like '").append(map.get("idcard")).append(
					"'");
		}
		log.info(sql);
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
	 * 查询注册用户总记录数
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int searchRegistCount(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select  COUNT(*) from org_employee where(doss_id='' or doss_id is null or isregist=1) and isactived=1 ");
		if (map.containsKey("ispass") && !"".equals(map.get("ispass"))) {
			sql.append(" and ispass=").append(map.get("ispass"));
		}
		if (map.containsKey("ewid") && !"".equals(map.get("ewid"))) {
			sql.append(" and ewid=").append(map.get("ewid"));
		}
		if (map.containsKey("dealercode") && !"".equals(map.get("dealercode"))) {
			sql.append(" and dealercode = '").append(map.get("dealercode"))
					.append("'");
		}
		if (map.containsKey("idcard") && !"".equals(map.get("idcard"))) {
			sql.append(" and idcard like '").append(map.get("idcard")).append(
					"'");
		}
		log.info(sql);
		int result = 0;
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}

	/**
	 * 查询用户名
	 * 
	 */
	public List<Org_employee> serchUserName(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from Org_employee where ");
		if (map.containsKey("username")) {
			sql.append("username= '").append(map.get("username")).append(
					"' or ewid='").append(map.get("username")).append("'");
			Query query = sessionFactory.getCurrentSession().createQuery(
					sql.toString());
			log.info(sql);
			return query.list();
		} else {
			return null;
		}
	}

	/**
	 * 根据id查询用户实体
	 * 
	 * @param id
	 * @return
	 */
	public Org_employee getEmployeeById(int id) {
		return (Org_employee) sessionFactory.getCurrentSession().get(
				Org_employee.class, id);
	}


	/**
	 * 根据经销商CODE统计人数
	 */
	public synchronized int searchEmployeeByDealercode(String dealercode) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(*) from org_employee where dealercode='")
				.append(dealercode).append("'");
		sql.append(" and (ewid!='' or ewid is not null )");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		int result = 0;
		Object obj = query.uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param employee
	 */
	public void saveEmployee(Org_employee employee) {
		if (0 == employee.getId()) {
			sessionFactory.getCurrentSession().save(employee);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(employee);
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 通过EWID获取申请修改信息
	 * 
	 * @param employee
	 */
	public Modify_name getMDFByEwid(String ewid) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Modify_name where 1=1 and ewid=:ewid");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql.toString());
		query.setString("ewid", ewid);
		List list = query.list();
		Modify_name mdfn = null;
		if(list.size() > 0 ){
			mdfn = (Modify_name) list.get(0);
		}
		return mdfn;
	}

	/**
	 * 保存用户申请修改信息
	 * 
	 * @param employee
	 */
	public void saveApplyInfo(Modify_name applyinfo) {
		if (0 == applyinfo.getId()) {
			sessionFactory.getCurrentSession().save(applyinfo);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(applyinfo);
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 保存密保_答案信息
	 * 
	 * @param employee
	 */
	public void saveSecretQandA(Secretquestion secretQ_A) {
		if (0 == secretQ_A.getId()) {
			sessionFactory.getCurrentSession().save(secretQ_A);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(secretQ_A);
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 查询密保信息
	 * 
	 */
	public List<Secretquestion> serchSecretQandA(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from Secretquestion where ");
		if (map.containsKey("username") && map.containsKey("question")
				&& map.containsKey("answer")) {
			sql.append("(ewid= '").append(map.get("username")).append(
					"' or username='").append(map.get("username")).append("')");
			sql.append(" and secretquestion= '").append(map.get("question"))
					.append("'");
			sql.append(" and secretAnwser= '").append(map.get("answer"))
					.append("'");
			Query query = sessionFactory.getCurrentSession().createQuery(
					sql.toString());
			log.info(sql);
			return query.list();
		} else {
			return null;
		}
	}

	/**
	 * 通过ewid获取密保信息
	 * 
	 */
	public Secretquestion getSecretQandAByEwid(String ewid) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Secretquestion where ewid=:ewid");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql.toString());
		query.setString("ewid", ewid);
		Secretquestion info = (Secretquestion) query.uniqueResult();
		return info;
	}

	/**
	 * 查询用户名
	 * 
	 */
	public List<Msgvalidate> getVertifyCode(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from Msgvalidate where ");
		if (map.containsKey("id") && map.containsKey("time")) {
			sql.append(" id=").append(map.get("id"));
			sql.append(" and createtime >='").append(map.get("time")).append(
					"'");
			Query query = sessionFactory.getCurrentSession().createQuery(
					sql.toString());
			log.info(sql);
			return query.list();
		} else {
			return null;
		}
	}

	/**
	 * 保存手机验证码信息
	 * 
	 * @param employee
	 */
	public void saveVertifyCode(Msgvalidate code) {
		if (0 == code.getId()) {
			sessionFactory.getCurrentSession().save(code);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(code);
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 根据DOSSID获取对象
	 */
	public Org_employee getEmployeeByDossid(String dossid) {
		StringBuffer sql = new StringBuffer();
		sql.append("from Org_employee where doss_id='").append(dossid).append(
				"'");
		Query query = sessionFactory.getCurrentSession().createQuery(
				sql.toString());
		Org_employee emp = null;
		List list  = query.list();
		if(list.size() > 0){
			emp = (Org_employee)list.get(0);
		}
		return emp;
	}

	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Shsgm_new> searchShsgm_new(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from Shsgm_new where 1=1 and newbatch!='2014H2个人奖励第三批' and newbatch!='2014H2个人奖励第二批'  and newbatch!='2014H2个人奖励第四批' ");
		
		Query query = sessionFactory.getCurrentSession().createQuery(
				sql.toString());
		return query.list();

	}
	
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchShsgm_newToMap(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM shsgm_new WHERE 1=1 ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.list();
	}
	

	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchShsgm_newToMap3(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM shsgm_new WHERE  dossId ='未匹配' OR dossId ='' OR dossId='无' OR dossId IS NULL  ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.list();
	}
	
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchShsgm_newToMap2(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT dossId,pName,idCard,dealercode FROM shsgm_new WHERE dossId !='未匹配' AND dossId !=''  AND dossId !='无'  AND dossId IS NOT NULL ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.list();
	}
	
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchShsgm_newToMap4(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM shsgm_new c WHERE (c.idCard,c.pName) IN (SELECT b.idCard,b.pName FROM (SELECT DISTINCT idCard,pName,dealercode FROM shsgm_new) b  GROUP BY b.idCard,b.pName HAVING COUNT(b.dealercode) > 1) ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.list();
	}
	
	/**
	 * 查询临时表所有数据
	 * 
	 */
	public List<Map> searchMatchdata(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  DISTINCT dossId,pName,idCard,dealercode ,job, phone FROM matchdata2 WHERE " +
				" dossId!='' AND dossId IS NOT NULL AND idCard!='' AND idCard IS NOT NULL AND pName IS NOT NULL AND pName!='' ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.list();
	}
    
	/**
	 * 查询经销商数据
	 * 
	 */
	public List<Map> searchdealer(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM dealer ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.list();
	}
	
	public List<Org_employee> getEMPWithoutEWID(){
		StringBuffer sql = new StringBuffer();
		sql.append("from Org_employee where ewid is null");
		Session session = sessionFactory.getCurrentSession();
		List list = session.createQuery(sql.toString()).list();
		return list;		
	}
	
	public Org_employee getEMPByEWID(String ewid){
		StringBuffer sql = new StringBuffer();
		sql.append("from Org_employee where ewid =:ewid");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql.toString());
		query.setString("ewid", ewid);		
		return (Org_employee) query.uniqueResult();
	}
	public Org_employee searchEMPByNIDP(Map map){
	    StringBuffer sql = new StringBuffer();
	    sql.append("from Org_employee where 1 = 1");
	    if(map.containsKey("name") && map.get("name") != null){
	        sql.append(" and name = '" + map.get("name")).append("'");
	    }
	    
	    if(map.containsKey("idcard") && map.get("idcard") != null){
	        sql.append(" and idcard = '" + map.get("idcard")).append("'");
	    }
	    
	    if(map.containsKey("position") && map.get("position") != null){
	        sql.append(" and position = '" + map.get("position")).append("'");
	    }
	    
	    if(map.containsKey("doss_id") && map.get("doss_id") != null){
	        sql.append(" and doss_id = '" + map.get("doss_id")).append("'");
	    }
	    
	    if(map.containsKey("dealercode") && map.get("dealercode") != null){
            sql.append(" and dealercode = '" + map.get("dealercode")).append("'");
        }
//	    System.out.println(sql.toString());
	    Session session = sessionFactory.getCurrentSession();
	    List list = session.createQuery(sql.toString()).list();
	    
	    if(list.size() > 0){
	        return (Org_employee) list.get(0);
	    }else{
	        return null;
	    }    
	}
	
	public void updateshemnew(String id, String dossid){
		String sqlString="UPDATE shsgm_new SET dossId='"+dossid+"' WHERE id="+id;
    	Session session = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.createSQLQuery(sqlString).executeUpdate();
		session.getTransaction().commit();
	}
	
	public void updateshemnew2(String id, String idCard){
		String sqlString="UPDATE shsgm_new SET idCard='"+idCard+"' WHERE id="+id;
    	Session session = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.createSQLQuery(sqlString).executeUpdate();
		session.getTransaction().commit();
	}
}
