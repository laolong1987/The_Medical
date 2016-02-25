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

import com.common.BaseDao;
import com.utils.StringUtil; 
import com.web.entity.Bo_bonusdetail;

@Repository
public class PatientDao extends BaseDao{
	private static final Log log = 	LogFactory.getLog(PatientDao.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
}
