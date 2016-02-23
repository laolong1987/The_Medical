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
import com.web.entity.Org_employee;

@Repository
public class ExportDao {
	
	private static final Log log = 	LogFactory.getLog(ExportDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	/**
	 * Get all employees
	 * @param p
	 * @return
	 */
    public List<Map> getAllEmployees(Map<String,String> map){
    	
    	StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(updatetime, '%Y-%m-%d %k:%i:%s' ) as updatetime,id, ewid, brand,dealercode,dealer, doss_id,name,position,idcard,openbank,accountbank,phone,bankname,isactived from org_employee where 1=1 ");
        //Get brands
		
		if(map.containsKey("brands") && null != map.get("brands") && !"".equals(map.get("brands"))){
			String[] paras = StringUtil.safeToString(map.get("brands"), "").split(",");
			String brands = "";
			if( paras.length > 0){
				for(String brand:paras){
					if(brands.length() > 0){
						brands+=",";
					}
					brands+= "'" + brand + "'";
				}
			}
			sql.append(" and brand in (" + brands).append(")");
			
		}
		if (map.containsKey("dealercode") && !"".equals(map.get("dealercode"))) {
			sql.append(" and dealercode ='").append(map.get("dealercode"))
					.append("'");
		}
		if (map.containsKey("position") && !"".equals(map.get("position"))) {// 查询职位
			sql.append(" and position='").append(map.get("position")).append(
					"'");
		}

		if (map.containsKey("idCard") && !"".equals(map.get("idCard"))) {// 查询身份证
			sql.append(" and idCard like '%").append(map.get("idCard")).append(
					"%'");
		}
		
		if (map.containsKey("isActived") && !"".equals(map.get("isActived"))) {// 查询是否已发放
			sql.append(" and isactived = ").append(map.get("isActived")).append(" ");
		}
		
		if (map.containsKey("dossId") && !"".equals(map.get("dossId"))) {
			//查询Doss ID
			sql.append(" and doss_id = '").append(map.get("dossId")).append("'");
		}
		
		if (map.containsKey("empname") && !"".equals(map.get("empname"))) {
			//查询Doss ID
			sql.append(" and name like '%").append(map.get("empname")).append("%'");
		}
		
		if(map.containsKey("begintime") && !"".equals(map.get("begintime"))){
			sql.append(" and updatetime >='").append(map.get("begintime")).append(" 00:00:00'");
		}
		
		if(map.containsKey("endtime") && !"".equals(map.get("endtime"))){
			sql.append(" and updatetime <='").append(map.get("endtime")).append(" 23:59:59'");
		}
		
        sql.append(" order by updatetime  desc");
		log.info(sql);

		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		if (map.containsKey("firstrs")) {
			query.setFirstResult(Integer.parseInt(StringUtil.safeToString(map
					.get("firstrs"), "1")));
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
    
    public int getTotalCount(Map<String,String> map){

//    	StringBuffer strbuf = new StringBuffer();
//    	strbuf.append("select count(id) from shsgm_new where 1=1  ");
//    	Session session = sessionFactory.getCurrentSession();
//    	return Integer.valueOf(session.createSQLQuery(strbuf.toString()).list().get(0).toString());
    	
    	StringBuffer sql = new StringBuffer();
		sql.append("select  COUNT(id) from org_employee where 1=1 ");
//		if (map.containsKey("name") && !"".equals(map.get("name"))) {
//			sql.append(" and name like '%").append(map.get("name"))
//					.append("%'");
//		}
		//Query results according to brand
		if(map.containsKey("brands") && null != map.get("brands") && !"".equals(map.get("brands"))){
            String[] paras = StringUtil.safeToString(map.get("brands"), "").split(",");
            String brands = "";
            if( paras.length > 0){
                for(String brand:paras){
                    if(brands.length() > 0){
                        brands+=",";
                    }
                    brands+= "'" + brand + "'";
                }
            }
            sql.append(" and brand in (" + brands).append(")");
            
        }
        if (map.containsKey("dealercode") && !"".equals(map.get("dealercode"))) {
            sql.append(" and dealercode ='").append(map.get("dealercode"))
                    .append("'");
        }
        if (map.containsKey("position") && !"".equals(map.get("position"))) {// 查询职位
            sql.append(" and position='").append(map.get("position")).append(
                    "'");
        }

        if (map.containsKey("idCard") && !"".equals(map.get("idCard"))) {// 查询身份证
            sql.append(" and idCard like '%").append(map.get("idCard")).append(
                    "%'");
        }
        
        if (map.containsKey("isActived") && !"".equals(map.get("isActived"))) {// 查询是否已发放
            sql.append(" and isactived = ").append(map.get("isActived")).append(" ");
        }
        
        if (map.containsKey("dossId") && !"".equals(map.get("dossId"))) {
            //查询Doss ID
            sql.append(" and doss_id = '").append(map.get("dossId")).append("'");
        }
        
        if (map.containsKey("empname") && !"".equals(map.get("empname"))) {
            //查询Doss ID
            sql.append(" and name like '%").append(map.get("empname")).append("%'");
        }
        
        if(map.containsKey("begintime") && !"".equals(map.get("begintime"))){
            sql.append(" and updatetime >='").append(map.get("begintime")).append(" 00:00:00'");
        }
        
        if(map.containsKey("endtime") && !"".equals(map.get("endtime"))){
            sql.append(" and updatetime <='").append(map.get("endtime")).append(" 23:59:59'");
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
	 * 查询导出数据
	 * 
	 * @return
	 */
	public List<Map> searchshsgm(Map map) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select * from org_employee ");
		sql.append(" where 1=1 ");
		
	    //sql.append(" where a.TYPE="+map.get("type")+" ");
		if (map.containsKey("name") && !"".equals(map.get("name"))) {
			sql.append(" and name like '%").append(map.get("name"))
					.append("%'");
		}
		
		if(map.containsKey("brand") && !"".equals(map.get("brand"))){
			sql.append(" and brand in (" + map.get("brand")).append(")");
		}
		
		
		if (map.containsKey("dealercode") && !"".equals(map.get("dealercode"))) {
			sql.append(" and dealercode ='").append(map.get("dealercode"))
					.append("'");
		}

		if (map.containsKey("pName") && !"".equals(map.get("pName"))) {// 查询姓名
			sql.append(" and pName like '%").append(map.get("pName")).append(
					"%'");
		}

		if (map.containsKey("position") && !"".equals(map.get("position"))) {// 查询职位
			sql.append(" and position='").append(map.get("position")).append(
					"'");
		}

		if (map.containsKey("idCard") && !"".equals(map.get("idCard"))) {// 查询身份证
			sql.append(" and idCard ='").append(map.get("idCard")).append("'");
		}

		if (map.containsKey("phoneNum") && !"".equals(map.get("phoneNum"))) {// //查询电话
			sql.append(" and phoneNum ='").append(map.get("phoneNum")).append(
					"'");
		}

		if (map.containsKey("bankName") && !"".equals(map.get("bankName"))) {// 查询银行名称
			sql.append(" and bankName like '%").append(map.get("bankName"))
					.append("%'");
		}

		if (map.containsKey("openBank") && !"".equals(map.get("openBank"))) {// 查询开户行
			sql.append(" and openBank like '%").append(map.get("openBank"))
					.append("%'");
		}

		if (map.containsKey("accountBank")
				&& !"".equals(map.get("accountBank"))) {// 查询银行账号
			sql.append(" and accountBank = '").append(map.get("accountBank"))
					.append("'");
		}

		if (map.containsKey("dossId") && !"".equals(map.get("dossId"))) {// //查询Doss
																			// ID
			sql.append(" and doss_id = '").append(map.get("dossId")).append("'");
		}
		
		if (map.containsKey("isActived") && !"".equals(map.get("isActived"))) {// //查询Doss
			// ID
            sql.append(" and isactived = ").append(map.get("isActived"));
        }

//		if (map.containsKey("state") && !"".equals(map.get("state"))) {// 查询是否已发放
//			sql.append(" and state = '").append(map.get("state")).append("'");
//		}
//		
//		if (map.containsKey("type") && !"".equals(map.get("type"))) {// 查询是否已发放
//			sql.append(" and type = '").append(map.get("type")).append("'");
//		}
//		
//		if (map.containsKey("newbatch") && !"".equals(map.get("newbatch"))) {
//			sql.append(" and a.newbatch = '").append(map.get("newbatch")).append("'");
//		}
		
	    System.out.println("====" + sql.toString());
		return sessionFactory.getCurrentSession()
				.createSQLQuery(sql.toString()).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public List<Map> getAllLockingUsers(Map map){
		StringBuffer sql = new StringBuffer();
		sql.append("select emp.id, emp.dealercode, emp.ewid, emp.name, emp.position, emp.idcard, emp.doss_id, emp.isactived, emp.dimission_time, dis.status dis_status,emp.inservice" + 
				" from org_employee emp" + 
				" inner join distributor dis" + 
				" on emp.dealercode = dis.code" + 
				" where (TO_DAYS(Now()) - TO_DAYS(emp.dimission_time) >30)" + 
				" or (dis.status in (0,2,3) and (TO_DAYS(Now()) - TO_DAYS(dis.unavailable_date)) > 30 )");
		
		if (map.containsKey("isactived") && !"".equals(map.get("isactived"))) {// //查询Doss
			// ID
            sql.append(" and emp.isactived in (").append(map.get("isactived")).append(")");
        }
		
		if (map.containsKey("doss_id") && !"".equals(map.get("doss_id"))) {// //查询Doss
			// ID
			sql.append(" and emp.doss_id = '").append(map.get("doss_id")).append("'");
		}
		
		if (map.containsKey("name") && !"".equals(map.get("name"))) {
			sql.append(" and emp.name like '%").append(map.get("name")).append("%'");
		}
		
		if (map.containsKey("dealercode") && !"".equals(map.get("dealercode"))) {
            sql.append(" and emp.dealercode = '").append(map.get("dealercode")).append("'");
        }
		
		return sessionFactory.getCurrentSession()
		.createSQLQuery(sql.toString()).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		
	    
	}
	
	public List<Map> getAllLockingUsers(){
		StringBuffer sql = new StringBuffer();
		sql.append("select emp.id, emp.dealercode, emp.ewid, emp.name, emp.position, emp.idcard, emp.doss_id, emp.isactived, emp.dimission_time, dis.status dis_status,emp.inservice" + 
				" from org_employee emp" + 
				" inner join distributor dis" + 
				" on emp.dealercode = dis.code" + 
				" where (TO_DAYS(Now()) - TO_DAYS(emp.dimission_time) >30)" + 
				" or (dis.status in (0,1,2,3) and (TO_DAYS(Now()) - TO_DAYS(dis.unavailable_date)) > 30 )");
		
//		if (map.containsKey("isactived") && !"".equals(map.get("isactived"))) {// //查询Doss
//			// ID
//            sql.append(" and emp.isactived in (").append(map.get("isactived")).append(")");
//        }
//		
//		if (map.containsKey("doss_id") && !"".equals(map.get("doss_id"))) {// //查询Doss
//			// ID
//			sql.append(" and emp.doss_id = '").append(map.get("doss_id")).append("'");
//		}
//		
//		if (map.containsKey("name") && !"".equals(map.get("name"))) {
//			sql.append(" and emp.name = '").append(map.get("name")).append("'");
//		}
		
		return sessionFactory.getCurrentSession()
		.createSQLQuery(sql.toString()).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		
	    
	}
	
	public int getLockingUsersCount(Map map){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*)" + 
                " from org_employee emp" + 
                " inner join distributor dis" + 
                " on emp.dealercode = dis.code" + 
                " where (TO_DAYS(Now()) - TO_DAYS(emp.dimission_time) >30)" + 
                " or (dis.status in (0,2,3) and (TO_DAYS(Now()) - TO_DAYS(dis.unavailable_date)) > 30 )");
		
		if (map.containsKey("isactived") && !"".equals(map.get("isactived"))) {// //查询Doss
			// ID
            sql.append(" and emp.isactived in (").append(map.get("isactived")).append(")");
        }
		
		if (map.containsKey("doss_id") && !"".equals(map.get("doss_id"))) {// //查询Doss
			// ID
			sql.append(" and emp.doss_id = '").append(map.get("doss_id")).append("'");
		}
		
		if (map.containsKey("name") && !"".equals(map.get("name"))) {
			sql.append(" and emp.name = '%").append(map.get("name")).append("%'");
		}
		
		if (map.containsKey("dealercode") && !"".equals(map.get("dealercode"))) {
            sql.append(" and emp.dealercode = '").append(map.get("dealercode")).append("'");
        }
		
		int result = 0;
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}
}
