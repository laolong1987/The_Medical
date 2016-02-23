package com.web.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.entity.Distributor;
import com.web.entity.Org_employee;

@Repository
public class DistributorDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveDistributor(Distributor dis){
        Session session = sessionFactory.getCurrentSession();
        session.save(dis);
	}
	
	public Distributor getDistributorByCode(String code){
		StringBuffer sql = new StringBuffer();
		sql.append("from Distributor where code=:code");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql.toString());
		query.setString("code", code);
		return (Distributor) query.uniqueResult();				
	}
	
	public void updateDistributor(Distributor dis){
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		tr.begin();
		session.saveOrUpdate(dis);
		tr.commit();
		session.close();	    
	}
	
	public List<Org_employee> getEmpsByDealerCode(String dealercode){
		StringBuffer sql = new StringBuffer();
		sql.append("from Org_employee where dealercode = '" + dealercode).append("'");
		return sessionFactory.getCurrentSession().createQuery(sql.toString()).list();
	}

}
