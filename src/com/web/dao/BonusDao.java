package com.web.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.utils.StringUtil; 
import com.web.entity.Bo_bonusdetail;

@Repository
public class BonusDao {
	private static final Log log = 	LogFactory.getLog(BonusDao.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 查询奖金明细表
	 * @param map
	 * @return
	 */
	public List<Map> searchBonusdetail(Map map){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT t.ewid,DATE_FORMAT(t.updatetime, '%Y-%m-%d %k:%i:%s' ) as updatetime,t.id,t.type,t.batch,t.is_visible,t.status,  SUM(t.price) as price,a.name ," +
				"a.doss_id, a.idcard, t.position,t.taxprice,t.realizeprice,t.salesNum, t.reason, c.dealershortname AS dealer,b.name as bname  ");
		sql.append(" FROM bo_bonusdetail t LEFT JOIN org_employee a ON a.ewid=t.ewid left join batch b on b.code=t.batch LEFT JOIN dealer c  ON t.dealercode=c.dealercode  WHERE 1=1 ");
		if (map.containsKey("empid") && !"".equals(map.get("empid"))) {
			sql.append(" and t.empid=").append(map.get("empid"));
		}
		
		if (map.containsKey("ewid") && !"".equals(map.get("ewid"))) {
			sql.append(" and t.ewid='").append(map.get("ewid")).append("'");
		}
		
		if(map.containsKey("type") && !"".equals(map.get("type"))){
			sql.append(" and t.type =").append(map.get("type"));
		}
		if(map.containsKey("status") && !"".equals(map.get("status"))){
			sql.append(" and t.status =").append(map.get("status"));
		}
		if(map.containsKey("batch") && !"".equals(map.get("batch"))){
			sql.append(" and b.code =").append(map.get("batch"));
		}
		if(map.containsKey("dealercode") && !"".equals(map.get("dealercode"))){
			sql.append(" and t.dealercode ='").append(map.get("dealercode")).append("'");
		}
		
		if(map.containsKey("position") && !"".equals(map.get("position"))){
			sql.append(" and t.position ='").append(map.get("position")).append("'");
		}
		
		if(map.containsKey("dossid") && !"".equals(map.get("dossid"))){
			sql.append(" and a.doss_id ='").append(map.get("dossid")).append("'");
		}
		
		if(map.containsKey("idcard") && !"".equals(map.get("idcard"))){
			sql.append(" and a.idcard ='").append(map.get("idcard")).append("'");
		}
		
		if(map.containsKey("realposition") && !"".equals(map.get("realposition"))){
			sql.append(" and t.realposition ='").append(map.get("realposition")).append("'");
		}
		
		if(map.containsKey("adjust")){
			
			sql.append(" and t.status != 1");
		}
		
//		if(map.containsKey("id") && !"".equals(map.get("id"))){
//			sql.append(" and t.id =").append(map.get("id"));
//		}
		
		if(map.containsKey("name") && !"".equals(map.get("name"))){
			sql.append(" and a.name like '%").append(map.get("name")).append("%'");
		}
		
		if(map.containsKey("dealer") && !"".equals(map.get("dealer"))){
			sql.append(" and a.dealer like '%").append(map.get("dealer")).append("%'");
		}
		
		if(map.containsKey("begintime") && !"".equals(map.get("begintime"))){
			sql.append(" and t.updatetime >='").append(map.get("begintime")).append(" 00:00:00'");
		}
		
		if(map.containsKey("endtime") && !"".equals(map.get("endtime"))){
			sql.append(" and t.updatetime <='").append(map.get("endtime")).append(" 23:59:59'");
		}
		if(map.containsKey("type") && !"".equals(map.get("type"))){
			sql.append(" and t.type =").append(map.get("type"));
		}
		
		if(map.containsKey("loggedPosition") && !"".equals(map.get("loggedPosition"))){
			String loggedPostion = (String) map.get("loggedPosition");
			if(!"总经理".equals(loggedPostion)){
				sql.append(" and t.is_visible = 1");
			}
		}
	    sql.append("   GROUP BY t.batch,t.dealercode,t.position ");
		if(map.containsKey("orderby") && !"".equals(map.get("orderby"))){
			sql.append(" ORDER BY CASE t.dealercode WHEN '").append(map.get("orderby")).append("' THEN 1  ELSE 2 END ASC ,t.updatetime DESC ");
		}else{
			sql.append(" order by t.updatetime desc");
		}
		
		//sql.append(" order by batch ");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		if (map.containsKey("firstrs")) {
			query.setFirstResult(Integer.parseInt(StringUtil.safeToString(map
					.get("firstrs"), "0")));
		}
		if (map.containsKey("maxrs")) {
			query.setMaxResults(Integer.parseInt(StringUtil.safeToString(map
					.get("maxrs"), "10")));
		}
		//System.out.println(sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	/**
	 * 查询奖金明细表记录数
	 * @param map
	 * @return
	 */
	public int countBonusdetail(Map map){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ( select COUNT(*) FROM bo_bonusdetail t LEFT JOIN org_employee a ON a.ewid=t.ewid WHERE 1=1 ");
		
		if (map.containsKey("empid") && !"".equals(map.get("empid"))) {
			sql.append(" and t.empid=").append(map.get("empid"));
		}
		
		if (map.containsKey("ewid") && !"".equals(map.get("ewid"))) {
			sql.append(" and t.ewid='").append(map.get("ewid")).append("'");
		}
		
		if(map.containsKey("type") && !"".equals(map.get("type"))){
			sql.append(" and t.type =").append(map.get("type"));
		}
		if(map.containsKey("status") && !"".equals(map.get("status"))){
			sql.append(" and t.status =").append(map.get("status"));
		}
		if(map.containsKey("batch") && !"".equals(map.get("batch"))){
			sql.append(" and t.batch =").append(map.get("batch"));
		}
		if(map.containsKey("dealercode") && !"".equals(map.get("dealercode"))){
			sql.append(" and t.dealercode ='").append(map.get("dealercode")).append("'");
		}
		
		if(map.containsKey("position") && !"".equals(map.get("position"))){
			sql.append(" and t.position ='").append(map.get("position")).append("'");
		}
		
		if(map.containsKey("id") && !"".equals(map.get("id"))){
			sql.append(" and t.id =").append(map.get("id"));
		}
		
		if(map.containsKey("name") && !"".equals(map.get("name"))){
			sql.append(" and a.name like '%").append(map.get("name")).append("%'");
		}
		
		if(map.containsKey("loggedPosition") && !"".equals(map.get("loggedPosition"))){
			String loggedPostion = (String) map.get("loggedPosition");
			if(!"总经理".equals(loggedPostion)){
				sql.append(" and t.is_visible = 1");
			}
		}
		
		if(map.containsKey("dealer") && !"".equals(map.get("dealer"))){
			sql.append(" and a.dealer like '%").append(map.get("dealer")).append("%'");
		}
		
		if(map.containsKey("begintime") && !"".equals(map.get("begintime"))){
			sql.append(" and t.updatetime >='").append(map.get("begintime")).append("'");
		}
		
		if(map.containsKey("realposition") && !"".equals(map.get("realposition"))){
			sql.append(" and t.realposition ='").append(map.get("realposition")).append("'");
		}
		
		
		if(map.containsKey("endtime") && !"".equals(map.get("endtime"))){
			sql.append(" and t.updatetime <='").append(map.get("endtime")).append("'");
		}
		sql.append("   GROUP BY t.batch,t.dealercode,t.position) AS a  ");
		int result = 0;
//		System.out.println(sql);
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}
	
	public boolean delBonusById(String id) {
		
		String hql = "delete Bo_bonusdetail u where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);
		
		return (query.executeUpdate() > 0);
	}

	
	/**
	 * 根据id查询奖金
	 * @param id
	 * @return
	 */
	public Bo_bonusdetail getBonusdetailById(int id) {		
	return (Bo_bonusdetail) sessionFactory.getCurrentSession().get(
			Bo_bonusdetail.class, id);
	}
	
	/**
	 * 根据ewid, 奖金批次， 类型， 岗位查找奖金
	 * @param ewid
	 * @param bonus_batch
	 * @param type
	 * @param position
	 * @return
	 */
    public List<Bo_bonusdetail> getBo_BonusByEwidBatch(String ewid, int bonus_batch, int type, String position, String dis_code){
       StringBuffer sql = new StringBuffer();
 	   sql.append("from Bo_bonusdetail where ewid =:ewid and batch=:batch and type=:type and position =:pos and dealercode=:discode");
 	   Session session = sessionFactory.getCurrentSession();
 	   Query query = session.createQuery(sql.toString());
 	   query.setString("ewid", ewid);
 	   query.setInteger("batch", bonus_batch);
 	   query.setInteger("type", type);
 	   query.setString("pos", position);
 	   query.setString("discode", dis_code);
 	   //List list = query.list();
  	   return query.list();   	
    }
    
    public void saveOrUpdateBonus(Bo_bonusdetail bonus){
    	Session session = null;
    	try {
    		session = sessionFactory.openSession();
			session.beginTransaction();
			if(0 == bonus.getId()){
				session.save(bonus);
			}else{
				session.saveOrUpdate(bonus);
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
    }
    /**
     * 
     * @param map
     * @return
     */

    public List<Map> searchDealer(Map map){
       StringBuffer sql = new StringBuffer();
 	   sql.append("SELECT a.regionname,t.dealer FROM dealer t LEFT JOIN region a ON t.sgmregionid=a.id where 1=1 ");
		if(map.containsKey("dealercode") && !"".equals(map.get("dealercode"))){
			sql.append(" AND t.dealercode= '").append(map.get("dealercode")).append("'");
		}

 		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		//System.out.println(sql);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list(); 	
    }
    /**
     * 根据ewid查找奖金
     * @param ewid
     * @return
     */    
    public List<Bo_bonusdetail> getBonusbyEWID(String ewid){
    	StringBuffer sql = new StringBuffer();
    	sql.append(" from Bo_bonusdetail where ewid ='" + ewid).append("'");
    	Query query = sessionFactory.openSession().createQuery(sql.toString());
    	//query.setString("pewid", ewid);
    	log.info(sql);
    	List<Bo_bonusdetail> list = query.list();
    	return list;
    }   
	/**
	 * 
	 * @param map
	 * @return
	 */

	public List<Map> searchDealeryb(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.regionname,t.dealer FROM dealer t LEFT JOIN region a ON t.sfrregionid=a.id where 1=1 ");
		if (map.containsKey("dealercode") && !"".equals(map.get("dealercode"))) {
			sql.append(" AND t.dealercode= '").append(map.get("dealercode"))
					.append("'");
		}

		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		//System.out.println(sql);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	public List getBonusByBatch(int batch){
	    StringBuffer sql = new StringBuffer();
	    sql.append("from Bo_bonusdetail where batch =:batch ");
	    Session session = sessionFactory.getCurrentSession();
	    Query query = session.createQuery(sql.toString());
	    query.setInteger("batch", batch);
	    return query.list();
	}
    /**
     * 调整金额
     * @param para
     * @return 更新结果
     */
	public int adjustBonus(Map para){
		StringBuffer sql = new StringBuffer();
		int result = 0;
		if(para.containsKey("ids") && !"".equals(para.get("ids"))){
			if(para.containsKey("ewid") && !"".equals(para.get("ewid"))){
				String ewid = (String) para.get("ewid");
				sql.append("update bo_bonusdetail set ewid =:ewid where id in (" + para.get("ids")+ ")");
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
				query.setString("ewid", ewid);
			    //System.out.println(sql.toString());
			    result = query.executeUpdate();
			}
		}	    
		return result;
	}
	
	public int confirmBonus(Map map){
		int result = 0;
		StringBuffer sql = new StringBuffer();
//	    sql.append("update bo_bonusdetail set is_visible = 1 where batch =:batch and dealercode =:dealercode");
		
//	    Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
//	    if(map.containsKey("dealercode") && !"".equals(map.get("dealercode"))){
//	    	query.setString("dealercode", (String) map.get("dealercode"));
//	    }
//	    
//	    if(map.containsKey("batch") && !"".equals(map.get("batch"))){
//	    	query.setString("batch", (String) map.get("batch"));
//	    }
				
	    String ids = (String) map.get("ids");
	    sql.append("update bo_bonusdetail set is_visible = 1 where id in (" +ids + ")");
	    Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
	    result = query.executeUpdate();
	    return result;
	}
	/**
	 * Set is_visible as 0 to cancel confirmation
	 * @param map
	 * @return
	 */
	public int cancelConfirm(Map map){
		int result = 0;
		StringBuffer sql = new StringBuffer();
//	    sql.append("update bo_bonusdetail set is_visible = 0 where batch =:batch and dealercode =:dealercode");
		
//	    Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
//	    if(map.containsKey("dealercode") && !"".equals(map.get("dealercode"))){
//	    	query.setString("dealercode", (String) map.get("dealercode"));
//	    }
//	    
//	    if(map.containsKey("batch") && !"".equals(map.get("batch"))){
//	    	query.setString("batch", (String) map.get("batch"));
//	    }
				
	    String ids = (String) map.get("ids");
	    sql.append("update bo_bonusdetail set is_visible = 0 where id in (" +ids + ")");
	    Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
	    result = query.executeUpdate();
	    return result;
	}
}
