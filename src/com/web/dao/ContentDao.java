package com.web.dao;

import java.util.HashMap;
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
import com.web.entity.Bo_bonusdetail;
import com.web.entity.Doc_news;

import com.web.entity.Doc_relation;
import com.web.entity.Recordlog;
import com.web.entity.Upimg;
import com.web.entity.Viwepager;
import com.web.entity.Viwepagerdeta;

@Repository
public class ContentDao {
	private static final Log log = LogFactory.getLog(ContentDao.class);
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Map> listAlldocnews(Map map) {
		StringBuffer sql = new StringBuffer();
		Query query = null;

		sql.append("select    DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype    from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where  d.status=0 ");

		if (null != map && !map.isEmpty()) {

			if (map.containsKey("type")) {
				sql.append(" and d.type=").append(map.get("type"));
			}

			
			if (map.containsKey("brand") && null != map.get("brand")) {
				String[] paras = StringUtil.safeToString(map.get("brand"), "")
						.split(",");
				if (paras.length > 0) {
					sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
					int i = 0;
					for (String brand : paras) {
						i += 1;
						sql.append("   d2.name like '%" + brand).append("%'");
						if (i < paras.length) {
							sql.append(" or ");
						}
					}
					sql.append(")");
				}
			}

			if (map.containsKey("regionname") && null != map.get("regionname")) {
				String[] sons = StringUtil.safeToString(map.get("regionname"), "")
						.split(",");
				if (sons.length > 0) {
					sql.append(" and d.id in (  SELECT  did  FROM doc_relation d3 WHERE  ");
					int i = 0;
					for (String son :sons) {
						i += 1;
						sql.append("   d3.name like '%" + son+"%'");
						if (i < sons.length) {
							sql.append(" or ");
						}
					}
					sql.append(")");
				}
			}

			if (map.containsKey("regionname2") && null != map.get("regionname2")) {
				String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
						.split(",");
				if (son4s.length > 0) {
					sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
					int i = 0;
					for (String son4 : son4s) {
						i += 1;
						sql.append("       d4.name like  '" + son4).append(
								"%'");
						if (i < son4s.length) {
							sql.append(" or ");
						}
					}
					sql.append(")");
				}
			}
		
			sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE  1=1 and t.status=0  ");

			
			
			if (map.containsKey("regionname2")) {
				sql.append(" and a.name ='").append(map.get("regionname2")).append("'");
				//sql.append(" and  a.name=").append(map.get("regionname2"));
			}
			if (map.containsKey("type")) {
				sql.append(" and t.type=").append(map.get("type"));
			}


			
			sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=2 OR TYPE=1))");
			
			
sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE   1=1 and t.status=0 ");

			
			
			if (map.containsKey("regionname")) {
				sql.append("and  a.name ='").append(map.get("regionname")).append("'");
				//sql.append(" and  a.name=").append(map.get("regionname2"));
			}
			if (map.containsKey("type")) {
				sql.append(" and t.type=").append(map.get("type"));
			}


			
			sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=3 ))");
			
			
sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE   1=1 and t.status=0 ");

			
			
			if (map.containsKey("brand")) {
				sql.append("and  a.name ='").append(map.get("brand")).append("'");
				//sql.append(" and  a.name=").append(map.get("regionname2"));
			}
			if (map.containsKey("type")) {
				sql.append(" and t.type=").append(map.get("type"));
			}


			
			sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=2 or TYPE=3 ))");

			sql.append(" union ( select    DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype    from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where  d.status=0 ");

		

				if (map.containsKey("type")) {
					sql.append(" and d.type=").append(map.get("type"));
				}

				
				if (map.containsKey("brand") && null != map.get("brand")) {
					String[] paras = StringUtil.safeToString(map.get("brand"), "")
							.split(",");
					if (paras.length > 0) {
						sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
						int i = 0;
						for (String brand : paras) {
							i += 1;
							sql.append("   d2.name like '%" + brand).append("%'");
							if (i < paras.length) {
								sql.append(" or ");
							}
						}
						sql.append(")");
					}
				}

				

				if (map.containsKey("regionname2") && null != map.get("regionname2")) {
					String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
							.split(",");
					if (son4s.length > 0) {
						sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
						int i = 0;
						for (String son4 : son4s) {
							i += 1;
							sql.append("       d4.name like  '" + son4).append(
									"%'");
							if (i < son4s.length) {
								sql.append(" or ");
							}
						}
						sql.append(")");
					}
				}
			
				sql.append(")");
				sql.append(" union ( select    DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype    from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where  d.status=0 ");

				

				if (map.containsKey("type")) {
					sql.append(" and d.type=").append(map.get("type"));
				}

				
				if (map.containsKey("regionname") && null != map.get("regionname")) {
					String[] paras = StringUtil.safeToString(map.get("regionname"), "")
							.split(",");
					if (paras.length > 0) {
						sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
						int i = 0;
						for (String brand : paras) {
							i += 1;
							sql.append("   d2.name like '%" + brand).append("%'");
							if (i < paras.length) {
								sql.append(" or ");
							}
						}
						sql.append(")");
					}
				}

				

				if (map.containsKey("regionname2") && null != map.get("regionname2")) {
					String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
							.split(",");
					if (son4s.length > 0) {
						sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
						int i = 0;
						for (String son4 : son4s) {
							i += 1;
							sql.append("       d4.name like  '" + son4).append(
									"%'");
							if (i < son4s.length) {
								sql.append(" or ");
							}
						}
						sql.append(")");
					}
				}
			
				sql.append(")");
			
			sql.append(" order by createTime desc");
			query = sessionFactory.getCurrentSession().createSQLQuery(
					sql.toString());
			if (map.containsKey("start")) {
				query.setFirstResult(Integer.parseInt(StringUtil.safeToString(
						map.get("start"), "0")));
			}
			if (map.containsKey("maxResult")) {
				query.setMaxResults(Integer.parseInt(StringUtil.safeToString(
						map.get("maxResult"), "3")));
			} else {
				query.setMaxResults(3);
			}

		}
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++"+sql);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Map> listshuowdocnews(Map map) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select  DISTINCT  d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype  from doc_news d  LEFT JOIN      doc_relation d1       on d.id=d1.did     where   1=1   and d.status=0   ");

		if (map.containsKey("type") && !"".equals(map.get("type"))) {
			sql.append("AND d。type = ").append(map.get("type")).append("'");
			
			
		}
		
//		if (map.containsKey("regionname") && !"".equals(map.get("regionname"))) {
//			sql.append("AND regionname = ").append(map.get("regionname")).append("'");
//		}
		if (map.containsKey("brand") && null != map.get("brand")) {
			String[] paras = StringUtil.safeToString(map.get("brand"), "")
					.split(",");
			if (paras.length > 0) {
				sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
				int i = 0;
				for (String brand : paras) {
					i += 1;
					sql.append("   d2.name like '%" + brand).append("%'");
					if (i < paras.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("regionname") && null != map.get("regionname")) {
			String[] sons = StringUtil.safeToString(map.get("regionname"), "")
					.split(",");
			if (sons.length > 0) {
				sql.append(" and d.id in (  SELECT  did  FROM doc_relation d3 WHERE  ");
				int i = 0;
				for (String son :sons) {
					i += 1;
					sql.append("   d3.name like '%" + son+"%'");
					if (i < sons.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("regionname2") && null != map.get("regionname2")) {
			String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
					.split(",");
			if (son4s.length > 0) {
				sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
				int i = 0;
				for (String son4 : son4s) {
					i += 1;
					sql.append("       d4.name like  '" + son4).append(
							"%'");
					if (i < son4s.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		
	sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE  1=1 and t.status=0  ");

	if (map.containsKey("regionname2")) {
		sql.append(" and a.name ='").append(map.get("regionname2")).append("'");
		//sql.append(" and  a.name=").append(map.get("regionname2"));
	}if (map.containsKey("type")) {
		sql.append(" and t.type=").append(map.get("type"));
	}

		
		sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=2 OR TYPE=1))");
		
		
		
		sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE   1=1 and t.status=0 ");

		
		
		if (map.containsKey("regionname")) {
			sql.append(" and a.name ='").append(map.get("regionname")).append("'");
			//sql.append(" and  a.name=").append(map.get("regionname2"));
		}
		if (map.containsKey("type")) {
			sql.append(" and t.type=").append(map.get("type"));
		}


		
		sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=3 ))");
		
		
		sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE   1=1 and t.status=0 ");

		
		
		if (map.containsKey("brand")) {
			sql.append("and  a.name ='").append(map.get("brand")).append("'");
			//sql.append(" and  a.name=").append(map.get("regionname2"));
		}
		if (map.containsKey("type")) {
			sql.append(" and t.type=").append(map.get("type"));
		}


		
		sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=2 or TYPE=3 ))");

		sql.append(" union ( select    DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype    from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where  d.status=0 ");

		

		if (map.containsKey("type")) {
			sql.append(" and d.type=").append(map.get("type"));
		}

		
		if (map.containsKey("brand") && null != map.get("brand")) {
			String[] paras = StringUtil.safeToString(map.get("brand"), "")
					.split(",");
			if (paras.length > 0) {
				sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
				int i = 0;
				for (String brand : paras) {
					i += 1;
					sql.append("   d2.name like '%" + brand).append("%'");
					if (i < paras.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		

		if (map.containsKey("regionname2") && null != map.get("regionname2")) {
			String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
					.split(",");
			if (son4s.length > 0) {
				sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
				int i = 0;
				for (String son4 : son4s) {
					i += 1;
					sql.append("       d4.name like  '" + son4).append(
							"%'");
					if (i < son4s.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}
	
		sql.append(")");
		sql.append(" union ( select    DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype    from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where  d.status=0 ");

		

		if (map.containsKey("type")) {
			sql.append(" and d.type=").append(map.get("type"));
		}

		
		if (map.containsKey("regionname") && null != map.get("regionname")) {
			String[] paras = StringUtil.safeToString(map.get("regionname"), "")
					.split(",");
			if (paras.length > 0) {
				sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
				int i = 0;
				for (String brand : paras) {
					i += 1;
					sql.append("   d2.name like '%" + brand).append("%'");
					if (i < paras.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		

		if (map.containsKey("regionname2") && null != map.get("regionname2")) {
			String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
					.split(",");
			if (son4s.length > 0) {
				sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
				int i = 0;
				for (String son4 : son4s) {
					i += 1;
					sql.append("       d4.name like  '" + son4).append(
							"%'");
					if (i < son4s.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}
	
		sql.append(")");
		
		
		sql.append(" order by createTime desc");

		return sessionFactory.getCurrentSession()
				.createSQLQuery(sql.toString()).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 查表中所有的记录
	 * 
	 * @param parameters
	 * @return
	 */
	public Map getdocCount(Map map) {
		StringBuffer sql = new StringBuffer();


		sql.append(" SELECT COUNT( DISTINCT a.id )  FROM   ( (");

		sql.append("select    DISTINCT  d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype    from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where  d.status=0 ");

		
		if (map.containsKey("type") && !"".equals(map.get("type"))) {
			sql.append(" AND d.type = ").append(map.get("type")).append(" ");
		}
		
		if (map.containsKey("brand") && null != map.get("brand")) {
			String[] paras = StringUtil.safeToString(map.get("brand"), "")
					.split(",");
			if (paras.length > 0) {
				sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE   ");
				int i = 0;
				for (String brand : paras) {
					i += 1;
					sql.append("   d2.name like '%" + brand).append("%'");
					if (i < paras.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("regionname") && null != map.get("regionname")) {
			String[] sons = StringUtil.safeToString(map.get("regionname"), "")
					.split(",");
			if (sons.length > 0) {
				sql.append(" and d.id in (  SELECT  did  FROM doc_relation d3 WHERE  ");
				int i = 0;
				for (String son :sons) {
					i += 1;
					sql.append("  d3.name like '%" + son+"%'");
					if (i < sons.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("regionname2") && null != map.get("regionname2")) {
			String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
					.split(",");
			if (son4s.length > 0) {
				sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
				int i = 0;
				for (String son4 : son4s) {
					i += 1;
					sql.append("       d4.name like  '" + son4).append(
							"%'");
					if (i < son4s.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}
		
		sql.append(")");
		
		sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE  1=1 and t.status=0  ");

		if (map.containsKey("regionname2")) {
			sql.append(" and a.name ='").append(map.get("regionname2")).append("'");
			//sql.append(" and  a.name=").append(map.get("regionname2"));
		}
		if (map.containsKey("type")) {
			sql.append(" and t.type=").append(map.get("type"));
		}

			sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=2 OR TYPE=1))");
			
			
			
			sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE  1=1 and t.status=0  ");

			
			
			if (map.containsKey("regionname")) {
				sql.append(" and a.name ='").append(map.get("regionname")).append("'");
				//sql.append(" and  a.name=").append(map.get("regionname2"));
			}
			if (map.containsKey("type")) {
				sql.append(" and t.type=").append(map.get("type"));
			}


			
			sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=3 ))");
			
			
sql.append("union ( select DISTINCT t.id,t.name,t.type ,t.content,t.status,t.author,t.createTime,t.utype FROM doc_news t LEFT JOIN doc_relation a  ON t.id=a.did  WHERE   1=1 and t.status=0 ");

			
			
			if (map.containsKey("brand")) {
				sql.append("and  a.name ='").append(map.get("brand")).append("'");
				//sql.append(" and  a.name=").append(map.get("regionname2"));
			}
			if (map.containsKey("type")) {
				sql.append(" and t.type=").append(map.get("type"));
			}


			
			sql.append(" AND t.id NOT IN (SELECT did FROM doc_relation WHERE TYPE=2 or TYPE=3 ))");

			sql.append(" union ( select    DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype    from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where  d.status=0 ");

			

			if (map.containsKey("type")) {
				sql.append(" and d.type=").append(map.get("type"));
			}

			
			if (map.containsKey("brand") && null != map.get("brand")) {
				String[] paras = StringUtil.safeToString(map.get("brand"), "")
						.split(",");
				if (paras.length > 0) {
					sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
					int i = 0;
					for (String brand : paras) {
						i += 1;
						sql.append("   d2.name like '%" + brand).append("%'");
						if (i < paras.length) {
							sql.append(" or ");
						}
					}
					sql.append(")");
				}
			}

			

			if (map.containsKey("regionname2") && null != map.get("regionname2")) {
				String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
						.split(",");
				if (son4s.length > 0) {
					sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
					int i = 0;
					for (String son4 : son4s) {
						i += 1;
						sql.append("       d4.name like  '" + son4).append(
								"%'");
						if (i < son4s.length) {
							sql.append(" or ");
						}
					}
					sql.append(")");
				}
			}
		
			sql.append(")");
sql.append(" union ( select    DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype    from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where  d.status=0 ");

			

			if (map.containsKey("type")) {
				sql.append(" and d.type=").append(map.get("type"));
			}

			
			if (map.containsKey("regionname") && null != map.get("regionname")) {
				String[] paras = StringUtil.safeToString(map.get("regionname"), "")
						.split(",");
				if (paras.length > 0) {
					sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
					int i = 0;
					for (String brand : paras) {
						i += 1;
						sql.append("   d2.name like '%" + brand).append("%'");
						if (i < paras.length) {
							sql.append(" or ");
						}
					}
					sql.append(")");
				}
			}

			

			if (map.containsKey("regionname2") && null != map.get("regionname2")) {
				String[] son4s = StringUtil.safeToString(map.get("regionname2"), "")
						.split(",");
				if (son4s.length > 0) {
					sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
					int i = 0;
					for (String son4 : son4s) {
						i += 1;
						sql.append("       d4.name like  '" + son4).append(
								"%'");
						if (i < son4s.length) {
							sql.append(" or ");
						}
					}
					sql.append(")");
				}
			}
		
			sql.append(")");
			
			sql.append(")a");
		sql.append(" order by createTime desc");
		List list = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).list();
		Map<String, String> totalRow = new HashMap<String, String>();
		// System.out.println(list.get(0).toString());
		totalRow.put("totalCount", list.get(0).toString());
		return totalRow;
	}

	@SuppressWarnings("unchecked")
	public List<Map> listshowimg(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from  upimg where 1=1");
		// if (map.containsKey("vid")) {
		// sql.append(" and vid=").append(map.get("vid"));
		// }

		if (map.containsKey("brand") && !"".equals(map.get("brand"))) {
			sql.append(" and brand ='").append(map.get("brand")).append("'");
		}
		sql.append(" order by xhid ");

		return sessionFactory.getCurrentSession()
				.createSQLQuery(sql.toString()).setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 查询明细表
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> searchDocnews(Map map) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select  DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype ,DATE_FORMAT(createTime, '%Y-%m-%d %k:%i:%s' ) AS ptime from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where 1=1 ");

		if (map.containsKey("brand") && null != map.get("brand")) {
			String[] paras = StringUtil.safeToString(map.get("brand"), "")
					.split(",");
			if (paras.length > 0) {
				sql.append(" and d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
				int i = 0;
				for (String brand : paras) {
					i += 1;
					sql.append("   d2.name like '%" + brand).append("%'");
					if (i < paras.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("son") && null != map.get("son")) {
			String[] sons = StringUtil.safeToString(map.get("son"), "")
					.split(",");
			if (sons.length > 0) {
				sql.append(" and  d.id in (  SELECT  did  FROM doc_relation d3 WHERE  ");
				int i = 0;
				for (String son :sons) {
					i += 1;
					sql.append("     d3.name like '%" + son).append(
							"%'");
					if (i < sons.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("son4") && null != map.get("son4")) {
			String[] son4s = StringUtil.safeToString(map.get("son4"), "")
					.split(",");
			if (son4s.length > 0) {
				sql.append(" and  d.id in(  SELECT  did  FROM doc_relation d4 WHERE   ");
				int i = 0;
				for (String son4 : son4s) {
					i += 1;
					sql.append("       d4.name like  '" + son4).append(
							"%'");
					if (i < son4s.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}


		if (map.containsKey("name") && !"".equals(map.get("name"))) {
			sql.append("  and d.name like '%").append(map.get("name")).append(
					"%'");
		}

		sql.append(" order by createTime desc ");
		// sql.append(" GROUP BY   d.id desc ");
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
	 * 查询表记录数
	 * 
	 * @param map
	 * @return
	 */
	public int countDocnews(Map map) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select   COUNT(DISTINCT d.id)  from doc_news d  LEFT JOIN doc_relation d1 on d.id=d1.did  where 1=1 ");


		
		if (map.containsKey("brand") && null != map.get("brand")) {
			String[] paras = StringUtil.safeToString(map.get("brand"), "")
					.split(",");
			if (paras.length > 0) {
				sql.append(" and  d.id in (  SELECT did  FROM doc_relation d2 WHERE      ");
				int i = 0;
				for (String brand : paras) {
					i += 1;
					sql.append("   d2.name like '%" + brand).append("%'");
					if (i < paras.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("son") && null != map.get("son")) {
			String[] sons = StringUtil.safeToString(map.get("son"), "")
					.split(",");
			if (sons.length > 0) {
				sql.append(" and  d.id in ( SELECT  did  FROM doc_relation d3 WHERE   ");
				int i = 0;
				for (String son :sons) {
					i += 1;
					sql.append("     d3.name like '%" + son).append(
							"%'");
					if (i < sons.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("son4") && null != map.get("son4")) {
			String[] son4s = StringUtil.safeToString(map.get("son4"), "")
					.split(",");
			if (son4s.length > 0) {
				sql.append(" and  d.id in(   SELECT  did  FROM doc_relation d4 WHERE  ");
				int i = 0;
				for (String son4 : son4s) {
					i += 1;
					sql.append("       d4.name like  '" + son4).append(
							"%'");
					if (i < son4s.length) {
						sql.append(" or ");
					}
				}
				sql.append(")");
			}
		}

		if (map.containsKey("name") && !"".equals(map.get("name"))) {
			sql.append("  and d.name like '%").append(map.get("name")).append(
					"%'");
		}

		// sql.append(" GROUP BY   d.id desc ");
		int result = 0;
		Object obj = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString()).uniqueResult();
		if (null != obj) {
			result = Integer.parseInt(StringUtil.safeToString(obj, "0"));
		}
		return result;
	}

	/**
	 * 查询img表
	 * 
	 * @param map
	 * @return
	 */
	public List<Map> searchimg(Map map) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select *,DATE_FORMAT(createTime, '%Y-%m-%d %k:%i:%s' ) AS ptime from upimg  where 1=1 ");

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
	 * 查询表记录数
	 * 
	 * @param map
	 * @return
	 */
	public int countimg(Map map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select COUNT(*) from upimg where 1=1 ");
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
	 * 
	 * @param id
	 * @return
	 */
	public Doc_news getcontentInfoById(int id) {

//		 StringBuffer sql = new StringBuffer();
//		 sql.append("select  DISTINCT d.id, d.name,d.type,d.content,d.status,d.author,d.createTime,d.utype ,DATE_FORMAT(createTime, '%Y-%m-%d %k:%i:%s' ) AS ptime from Doc_news d LEFT JOIN doc_relation d1 on d.id=d1.did    where 1=1 ").append(" and d.id =  ").append(id);
//		 Query query =
//		 sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
//		 System.out.println(query.list().get(0).toString());
		
	//	 return (Doc_news) query.list().get(0);

		return (Doc_news) sessionFactory.getCurrentSession().get(
				Doc_news.class, id);
	}

	/**
	 * 根据id查询img实体
	 * 
	 * @param id
	 * @return
	 */
	public Upimg getimgInfoById(int id) {

		// StringBuffer sql = new StringBuffer();
		// sql.append("select * from Viwepagerdeta where 1=1 ").append(" and vid =  ").append(vid);
		// Query query =
		// sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		// System.out.println(query.list().get(0).toString());
		//	
		// return (Viwepagerdeta) query.list().get(0);
		//		
		return (Upimg) sessionFactory.getCurrentSession().get(Upimg.class, id);
	}
	
	
	/**
	 * 根据id查询img实体
	 * 
	 * @param id
	 * @return
	 */
	public Doc_relation getrelationInfoById(int id) {
//
//		// StringBuffer sql = new StringBuffer();
//		// sql.append("select * from Viwepagerdeta where 1=1 ").append(" and vid =  ").append(vid);
//		// Query query =
//		// sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
//		// System.out.println(query.list().get(0).toString());
//		//	
//		// return (Viwepagerdeta) query.list().get(0);
//		//		
		return (Doc_relation) sessionFactory.getCurrentSession().get(Doc_relation.class, id);
	}
	
	
	/**
	 * 根据xhid查询img实体
	 * 
	 * @param xhidid
	 * @return
	 */
	public List<Map> getrelationInfoById(Map map) {

		StringBuffer sql = new StringBuffer();
		Query query = null;
		sql.append("SELECT d1.name  FROM Doc_relation d1  where 1=1 ");
	//	sql.append(" SELECT  replace(concat('''',d1.name  ,''''),',',''',''')as names  FROM Doc_relation d1  where 1=1 ");
		if (map.containsKey("did") && !"".equals(map.get("did"))) {
			sql.append(" and d1.did ='").append(map.get("did")).append("'");
		}

		 query = sessionFactory.getCurrentSession().createQuery(
				sql.toString());
		return query.list();
//		query = sessionFactory.getCurrentSession().createSQLQuery(
//				sql.toString());
//		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//				.list();
//	
	}

	/**
	 * 根据xhid查询img实体
	 * 
	 * @param xhidid
	 * @return
	 */
	public List<Upimg> getimgxh(Map map) {

		StringBuffer sql = new StringBuffer();
		sql.append(" from Upimg where 1=1 ");
		if (map.containsKey("xhid") && !"".equals(map.get("xhid"))) {
			sql.append(" and xhid ='").append(map.get("xhid")).append("'");
		}

		if (map.containsKey("brand") && !"".equals(map.get("brand"))) {
			sql.append(" and brand ='").append(map.get("brand")).append("'");
		}

		Query query = sessionFactory.getCurrentSession().createQuery(
				sql.toString());
		return query.list();

	}

	/**
	 * 保存人员信息
	 * 
	 * @param peopleInfo
	 */
	public void saverecordlog(Recordlog recordlog) {
		if (0 == recordlog.getId()) {
			sessionFactory.getCurrentSession().save(recordlog);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(recordlog);
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 保存人员信息
	 * 
	 * @param peopleInfo
	 */
	public void savecontentinfo(Doc_news doc_news) {
		if (0 == doc_news.getId()) {
			sessionFactory.getCurrentSession().save(doc_news);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(doc_news);
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 保存区域关系信息
	 * 
	 * @param peopleInfo
	 */
	public void savecontentrela(Doc_relation doc_relation) {
		if (0 == doc_relation.getId()) {
			sessionFactory.getCurrentSession().save(doc_relation);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(doc_relation);
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 保存轮播图信息
	 * 
	 * @param peopleInfo
	 */
	public void saveuploadlistimg(Upimg upimg) {
		if (0 == upimg.getId()) {
			sessionFactory.getCurrentSession().save(upimg);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(upimg);
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 保存图片信息
	 * 
	 * @param peopleInfo
	 */
	public void saveuploadimg(Viwepagerdeta viwepagerdeta) {
		if (0 == viwepagerdeta.getId()) {
			sessionFactory.getCurrentSession().save(viwepagerdeta);
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(viwepagerdeta);
			session.getTransaction().commit();
			session.close();
		}
	}

	public boolean delcontent(String id) {

		String hql = "delete Doc_news u where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);

		return (query.executeUpdate() > 0);
	}

	@SuppressWarnings("unchecked")
	public List<Map> searchDoc_relation(Map map) {
		StringBuffer sql = new StringBuffer();
		Query query = null;
		sql.append("SELECT GROUP_CONCAT(NAME) as names ,did  FROM doc_relation WHERE 1=1 " );
		if (map.containsKey("type")) {
			sql.append(" and type=").append(map.get("type"));
		}

		sql.append(" GROUP BY did  ");
		query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public boolean delrelation(String id) {

		String hql = "delete Doc_relation u where u.did = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);

		return (query.executeUpdate() > 0);
	
}
}
