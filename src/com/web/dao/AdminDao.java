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

@Repository
public class AdminDao {
	
	private static final Log log = 	LogFactory.getLog(AdminDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	/**
	 * 登陆查询
	 * 
	 */
	public List<Admin> serchAdmin(Map map){
		StringBuffer sql =new StringBuffer();
		sql.append(" from Admin where 1=1");
		if(map.containsKey("username")){
			sql.append(" and username= '").append(map.get("username")).append("'");
		}
		if(map.containsKey("pwd")){
			sql.append(" and pwd = '").append(map.get("pwd")).append("'");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());
		log.info(sql);
		return query.list();
	}
	
	/**
	 * 保存用户信息
	 * @param employee
	 */
	public void saveAdmin(Admin admin) {
		if (0 == admin.getId()) {
			sessionFactory.getCurrentSession().save(admin);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(admin);
			session.getTransaction().commit();
			session.close();
		}
	}
	
	
	/**
	 * 查询人员信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> searchEmpInfo(Map<String,String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from org_employee where 1=1 ");

		if(map.containsKey("dealercode") && !"".equals(map.get("dealercode"))){
			sql.append(" and dealercode ='").append(map.get("dealercode")).append("'");
		}
		
		if(map.containsKey("dealer") && !"".equals(map.get("dealer"))){
			sql.append(" and dealer like '%").append(map.get("dealer")).append("%'");
		}
		
		
		if(map.containsKey("name")&& !"".equals(map.get("name"))){//查询姓名
			sql.append(" and name like '%").append(map.get("name")).append("%'");
		}
		
		if(map.containsKey("position")&& !"".equals(map.get("position"))){//查询职位
			sql.append(" and position='").append(map.get("position")).append("'");
		}
		
		if(map.containsKey("idcard")&& !"".equals(map.get("idcard"))){//查询身份证
			sql.append(" and idcard like '%").append(map.get("idcard")).append("%'");
		}
		
		if(map.containsKey("phone")&& !"".equals(map.get("phone"))){////查询电话
			sql.append(" and phone ='").append(map.get("phone")).append("'");
		}
		
		if(map.containsKey("bankname")&& !"".equals(map.get("bankname"))){//查询银行名称
			sql.append(" and bankname like '%").append(map.get("bankname")).append("%'");
		}
		
		if(map.containsKey("openbank")&& !"".equals(map.get("openbank"))){//查询开户行
			sql.append(" and openbank like '%").append(map.get("openbank")).append("%'");
		}
		
		if(map.containsKey("accountbank")&& !"".equals(map.get("accountbank"))){//查询银行账号
			sql.append(" and accountbank like '%").append(map.get("accountbank")).append("%'");
		}
		
		if(map.containsKey("doss_id")&& !"".equals(map.get("doss_id"))){////查询Doss ID
			sql.append(" and doss_id = '").append(map.get("doss_id")).append("'");
		}
		
		
		if(map.containsKey("member_id")&& !"".equals(map.get("member_id"))){////销量
			sql.append(" and member_id = '").append(map.get("member_id")).append("'");
		}
		
		
		if(map.containsKey("address") && !"".equals(map.get("address"))){//查询是否已发放
			sql.append(" and address like '%").append(map.get("address")).append("%'");
		}
		
		if(map.containsKey("region") && !"".equals(map.get("region"))){//查询是否已发放
			sql.append(" and region like '%").append(map.get("region")).append("%'");
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
	 * 查询人员信息总记录数
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int searchEmpInfoCount(Map<String,String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  COUNT(*) from shsgm_new where 1=1 ");
		if(map.containsKey("dealercode") && !"".equals(map.get("dealercode"))){
			sql.append(" and dealercode ='").append(map.get("dealercode")).append("'");
		}
		
		if(map.containsKey("dealer") && !"".equals(map.get("dealer"))){
			sql.append(" and dealer like '%").append(map.get("dealer")).append("%'");
		}
		
		
		if(map.containsKey("name")&& !"".equals(map.get("name"))){//查询姓名
			sql.append(" and name like '%").append(map.get("name")).append("%'");
		}
		
		if(map.containsKey("position")&& !"".equals(map.get("position"))){//查询职位
			sql.append(" and position='").append(map.get("position")).append("'");
		}
		
		if(map.containsKey("idcard")&& !"".equals(map.get("idcard"))){//查询身份证
			sql.append(" and idcard like '%").append(map.get("idcard")).append("%'");
		}
		
		if(map.containsKey("phone")&& !"".equals(map.get("phone"))){////查询电话
			sql.append(" and phone ='").append(map.get("phone")).append("'");
		}
		
		if(map.containsKey("bankname")&& !"".equals(map.get("bankname"))){//查询银行名称
			sql.append(" and bankname like '%").append(map.get("bankname")).append("%'");
		}
		
		if(map.containsKey("openbank")&& !"".equals(map.get("openbank"))){//查询开户行
			sql.append(" and openbank like '%").append(map.get("openbank")).append("%'");
		}
		
		if(map.containsKey("accountbank")&& !"".equals(map.get("accountbank"))){//查询银行账号
			sql.append(" and accountbank like '%").append(map.get("accountbank")).append("%'");
		}
		
		if(map.containsKey("doss_id")&& !"".equals(map.get("doss_id"))){////查询Doss ID
			sql.append(" and doss_id = '").append(map.get("doss_id")).append("'");
		}
		
		
		if(map.containsKey("member_id")&& !"".equals(map.get("member_id"))){////销量
			sql.append(" and member_id = '").append(map.get("member_id")).append("'");
		}
		
		
		if(map.containsKey("address") && !"".equals(map.get("address"))){//查询是否已发放
			sql.append(" and address like '%").append(map.get("address")).append("%'");
		}
		
		if(map.containsKey("region") && !"".equals(map.get("region"))){//查询是否已发放
			sql.append(" and region like '%").append(map.get("region")).append("%'");
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
	
}
