package com.web.dao;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.entity.UserLockInfo;

@Repository

public class UserLockInfoDao {
	private static final Log log = LogFactory.getLog(EmployeeDao.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveUserLockInfo(UserLockInfo ulInfo){

		    Session session = null;
		    session = sessionFactory.getCurrentSession();
	        Transaction tr = session.beginTransaction();
	        session.save(ulInfo);
	        tr.commit();
	}	
	
	public UserLockInfo getUserLockInfoByEwis(String ewid){
	    StringBuffer sql = new StringBuffer();
	    sql.append("from UserLockInfo where ewid=:ewid");
	    Session session = sessionFactory.getCurrentSession();
	    Query query = session.createQuery(sql.toString());
	    query.setString("ewid", ewid);
	    List list = query.list();
	    if(list.size() > 0){
	        return (UserLockInfo) list.get(0);
	    }else
	        return null;
	}
}
