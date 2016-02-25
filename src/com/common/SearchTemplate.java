/**
 * 
 */
package com.common;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaoyang
 *
 */
public class SearchTemplate {
	private List<Map<String, Object>> values = null;
	private int count = 0;
	private String sql = null;

	public SearchTemplate(String sql) {
		this.sql = sql;
	}

	public String getOrderSql(Map<String, String> param) {
		StringBuilder _sql = new StringBuilder(sql);
		_sql.insert(0, "select * from (");
		_sql.append(") t");
		if (param.get("sortName") != null && param.get("sortOrder") != null) {
			_sql.append("\n order by ").append(param.get("sortName")).append(" ").append(param.get("sortOrder"));
		}
		this.sql = _sql.toString();
		return _sql.toString();
	}

	public String getAdvancedSql(Map param) {
		Map<String,String> temp = new HashMap();
		temp.put("like", " like ");
		temp.put("equal", " = ");
		StringBuilder _sql = new StringBuilder(sql);
		_sql.insert(0, "select * from (");
		_sql.append(") t");
		_sql.append(" where 1=1");
		if (param.get("rules") != null) {
			List<Map> list = (List<Map>) (param.get("rules"));
			for (Map m : list) {
				if (!m.get("field").equals("undefined") && !m.get("value").equals("")) {
					String op = temp.get(m.get("op"));
					_sql.append(" and ");
					if(op.equals(" like ")){
						if(m.get("type").equals("date")){
							_sql.append("to_char("+m.get("field")+",'yyyy-mm-dd')");
							param.put("advanced"+m.get("field"),m.get("value"));
						}else{
							_sql.append("to_char("+ m.get("field") +")");
							param.put("advanced"+m.get("field"),"%" + m.get("value")+ "%");
						}
					}else{
						if(m.get("type").equals("date")){
							_sql.append("to_char("+m.get("field")+",'yyyy-mm-dd')");
							param.put("advanced"+m.get("field"),m.get("value"));
						}else{
						_sql.append(m.get("field"));
						param.put("advanced"+m.get("field"), m.get("value"));
						}
					}
					if(m.get("type").equals("date")){
						_sql.append(" = ");
					}else{
						_sql.append(temp.get(m.get("op")));
					}
					_sql.append(" :advanced"+ m.get("field") +"");

//					if (m.get("op").equals("like")) {
//						if (m.get("type").equals("string") || m.get("type").equals("int")) {
//							_sql.append(m.get("field"));
//							_sql.append(" like ");
//							_sql.append("'%" + m.get("value") + "%'");
//						} else if (m.get("type").equals("date")) {
//							_sql.append(" TO_DATE('" + m.get("field") + "','yyyy-MM-dd')");
//							_sql.append(" = ");
//							_sql.append(" TO_DATE('" + m.get("value") + "','yyyy-MM-dd')");
//						}
//					} else if (m.get("op").equals("equal")) {
//						if (m.get("type").equals("int")) {
//							_sql.append(m.get("field"));
//							_sql.append(" = ");
//							_sql.append(m.get("value"));
//						} else if (m.get("type").equals("string")) {
//							_sql.append(m.get("field"));
//							_sql.append(" = ");
//							_sql.append("'" + m.get("value") + "'");
//						} else if (m.get("type").equals("date")) {
//							_sql.append(" TO_DATE('" + m.get("field") + "','yyyy-MM-dd')");
//							_sql.append(" = ");
//							_sql.append(" TO_DATE('" + m.get("value") + "','yyyy-MM-dd')");
//						}
//					}
				}
			}
		}
		if (param.get("sortName") != null && param.get("sortOrder") != null) {
			_sql.append("\n order by ").append(param.get("sortName")).append(" ").append(param.get("sortOrder"));
		}
		this.sql = _sql.toString();
		return _sql.toString();
	}

	public String getCountSql() {
		StringBuilder _sql = new StringBuilder(this.sql);
		_sql.insert(0, "select count(*) from (");
		_sql.append(") t");
		return _sql.toString();
	}

	public void setValues(List<Map<String, Object>> values) {
		this.values = values;
	}

	public List<Map<String, Object>> getValues() {
		return this.values;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Map<String, Object> getResult() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", this.values);
		result.put("Total", this.count);
		return result;
	}
}