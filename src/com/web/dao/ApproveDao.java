package com.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.utils.StringUtil;
import com.web.entity.Admin;
import com.web.entity.Apply_role;
import com.web.entity.Modify_name;
import com.web.entity.Org_employee;

@Repository
public class ApproveDao {
	private static final Log log = 	LogFactory.getLog(ApproveDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List<Map> getAllApprovalist(Map<String,String> map){
		StringBuffer sql = new StringBuffer();
		sql.append("select mdf.id, mdf.ewId,after_ewId,mdf.dealercode,mdf.before_position,mdf.after_position, mdf.before_name,mdf.before_idcard,mdf.before_dossId,mdf.after_name," +
				"mdf.after_idcard,mdf.after_dossId,isapprove, DATE_FORMAT(mdf.apply_date,'%Y-%m-%d') AS applyDate, mdf.prove_img, mdf.info_type, mdf.exch_bonus_phone from modify_name mdf where 1=1 ");
		if(map.containsKey("isApproved") && !"".equals(map.get("isApproved")) && null != map.get("isApproved")){
			sql.append("and isapprove = " + map.get("isApproved"));
		}
		if(map.containsKey("dossid") && !"".equals(map.get("dossid")) && null != map.get("dossid")){
			sql.append(" and before_dossId = '" + map.get("dossid")).append("'");
		}
		if(map.containsKey("orgname") && !"".equals(map.get("orgname")) && null != map.get("orgname")){
			sql.append(" and before_name = '" + map.get("orgname")).append("'");
		}
		
		if(map.containsKey("begintime") && !"".equals(map.get("begintime"))){
			sql.append(" and updatetime >='").append(map.get("begintime")).append("'");
		}
		
		if(map.containsKey("endtime") && !"".equals(map.get("endtime"))){
			sql.append(" and updatetime <='").append(map.get("endtime")).append("'");
		}
		
		sql.append(" order by applyDate desc");
		
		
		return sessionFactory.getCurrentSession()
		.createSQLQuery(sql.toString()).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public int searchApprovalCount(Map<String,String> map){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) from modify_name where 1=1 ");
		if(map.containsKey("isApproved") && !"".equals(map.get("isApproved")) && null != map.get("isApproved")){
			sql.append("and isapprove = " + map.get("isApproved"));
		}
		if(map.containsKey("dossid") && !"".equals(map.get("dossid")) && null != map.get("dossid")){
			sql.append(" and before_dossId = '" + map.get("dossid")).append("'");
		}
		if(map.containsKey("orgname") && !"".equals(map.get("orgname")) && null != map.get("orgname")){
			sql.append(" and before_name = '" + map.get("orgname")).append("'");
		}
		
		if(map.containsKey("begintime") && !"".equals(map.get("begintime"))){
			sql.append(" and updatetime >='").append(map.get("begintime")).append("'");
		}
		
		if(map.containsKey("endtime") && !"".equals(map.get("endtime"))){
			sql.append(" and updatetime <='").append(map.get("endtime")).append("'");
		}
		int result = 0;
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}
	
	public void updateEMP(Org_employee emp){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(emp);
		session.flush();
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Approve modification application
	 * @param emp
	 */
	
	public void approveEmp(Org_employee newEMP){
		//StringBuffer sql = new StringBuffer();
		Session session = sessionFactory.openSession();
		//Transaction ts = session.beginTransaction();
		session.save(newEMP);
		//session.update(oldEMP);		
		//ts.commit();	
		session.close();
	}
    
	public void updateMDF(Modify_name modify){
		Session session = sessionFactory.openSession();
		Transaction tsm = session.beginTransaction();
		session.saveOrUpdate(modify);
		tsm.commit();
		session.close();
	}
	
//	public Org_employee getEmployeeByEwid(String ewid){
//		StringBuffer sql = new StringBuffer();
//		sql.append("from Org_employee where 1=1 and ewid=:eid");
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery(sql.toString());
//		query.setString("eid",ewid);
//		Org_employee emp = (Org_employee) query.uniqueResult();
//		return emp;
//	}
	
	public Modify_name getMDFByID(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("from Modify_name where 1=1 and id=:id");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql.toString());
		query.setString("id",id);
		Modify_name mdfn = (Modify_name) query.uniqueResult();
		return mdfn;
	}
	
	public List<Map> getApplyRoleList(Map<String,String> map){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from apply_role where 1=1");
		if(map.containsKey("isApproved") && !"".equals(map.get("isApproved")) && null != map.get("isApproved")){
			sql.append(" and isapprove ='").append(map.get("isApproved")).append("'");
			sql.append(" order by createTime desc");
		}
		log.info(sql);
		return sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public int  getApplyRoleListCount(Map<String,String> map){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from apply_role where 1=1 ");
		if(map.containsKey("isApproved") && !"".equals(map.get("isApproved")) && null != map.get("isApproved")){
			sql.append(" and isapprove ='").append(map.get("isApproved")).append("'");
		}
		
		
		int result = 0;
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}
	
	/**
	 * 查询申请权限实体
	 * @param employee
	 */
	public List<Apply_role> serchApply_role(Map map){
		StringBuffer sql =new StringBuffer();
		sql.append(" from Apply_role where 1=1");
		if(map.containsKey("id")){
			sql.append(" and id=").append(map.get("id"));
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());
		log.info(sql);
		return query.list();
	}
	
	/**
	 * 保存用户信息
	 * @param employee
	 */
	public void saveApplyRole(Apply_role apply) {
		if (0 == apply.getId()) {
			sessionFactory.getCurrentSession().save(apply);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(apply);
			session.getTransaction().commit();
			session.close();
		}
	}

}
