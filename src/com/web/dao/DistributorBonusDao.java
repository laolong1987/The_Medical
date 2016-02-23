package com.web.dao;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.entity.DistributorBonus;

@Repository
public class DistributorBonusDao {
	
   @Autowired
   private SessionFactory sessionFactory;
   /**
    * Get distributor bonus by distributor code and bonus batch
    * @param dis_code
    * @param bonus_batch
    * @return
    */
   public DistributorBonus getDisBonusByBatch(String dis_code, String bonus_batch){
	   StringBuffer sql = new StringBuffer();
	   sql.append("from DistributorBonus where dis_code=:code and bonus_batch=:batch");
	   Session session = sessionFactory.openSession();
	   Query query = session.createQuery(sql.toString());
	   query.setString("code", dis_code);
	   query.setString("batch", bonus_batch);
	   List list = query.list();
	   DistributorBonus disBonus = null;
	   if(list.size() > 0){
		   disBonus  =(DistributorBonus)list.get(0);
	   }	   
	   if(session != null){
		   session.close();
	   }	   
	   return disBonus;	   
   }
   
   public void saveDisBonus(DistributorBonus disBonus){
	   Session session = sessionFactory.getCurrentSession();
	   
	   if(0 == disBonus.getId()){	
		   session.save(disBonus);
	   }else{		    
		    session.saveOrUpdate(disBonus);
	   }
   }
}
