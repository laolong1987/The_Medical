package com.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 用于生成javabean
 * @author 高扬
 *
 */
public class POJOUtil {
	private Connection conn = null; // 保存链接路径
	private Statement stmt = null; // 建立连接
	private ResultSetMetaData meta = null; // 保存表属性信息
	private ResultSet rs = null; // 查询结果集
	private String str = null;
	private File f = null; // 建立的文件
	private OutputStreamWriter osw = null;
	private BufferedWriter bw = null;
	private FileOutputStream fos = null;
	private static StringBuffer coding = new StringBuffer(); // 字符串缓冲区
	private String packageName = null; // 数据库包名
	private String url = null; // 路径名
	private String table = null; // 表空间名
	private String password = null; // 密码
	private String tableName = null; // 表明
	private String filename="";
	
	public POJOUtil(String packageName, String url, String table,
			String password, String tableName,String filename) {
		this.packageName = packageName;
		this.url = url;
		this.table = table;
		this.password = password;
		this.tableName = tableName;
		this.filename=filename;
		f = new File(filename);
		if (!f.exists()) { // 如果文件不存在则建立文件
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getCoding(StringBuffer code) {
		return code.toString();
	}

	private StringBuffer generate1(String property,String type) {
		String prop = property.toLowerCase();
		String t=gettype(type);
		if("id".equals(prop)){
			coding.append(" @Id\n");
			coding.append(" @Basic(optional = false)\n");
			coding.append(" @Column(name = \"id\")\n");
			coding.append(" @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
		}else {
			coding.append(" @Column(name =\"").append(prop).append("\")\n");
		}
		coding.append(" private "+t+" " + prop + ";\n\r");
		return coding;

	}
	
	private String gettype(String type){
		String t="";
		System.out.println(type);
		if("INTEGER".equals(type) || "INT".equals(type)){
			t="int";
		}else if("VARCHAR".equals(type)){
			t="String";
		}else if("DATETIME".equals(type)){
			t="Date";
		}else if("LONGTEXT".equals(type)){
			t="String";
		}else if("DECIMAL".equals(type)){
			t="Double";
		}
		return t;
	}
	
	private StringBuffer generate2(String property,String type) {
		String prop = property.toLowerCase();
		String t=gettype(type);
		coding.append("\r public void set" + (char) (prop.charAt(0) - 32)
				+ prop.substring(1) + "("+t+" " + prop + "){\n");
		coding.append("   this." + prop + "=" + prop + ";\n");
		coding.append(" }\n");
		coding.append("\r public "+t+" get" + (char) (prop.charAt(0) - 32)
				+ prop.substring(1) + "(){\n");
		coding.append("   return this." + prop + ";\n");
		coding.append(" }\n\n");
		return coding;

	}
	

	private void destroy() {
		/*
		 * 关闭与数据库的所有链接
		 */
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}

			if (bw != null) {
				bw.close();
				bw = null;
			}
			if (fos != null) {
				fos.close();
				fos = null;
			}
			if (osw != null) {
				osw.close();
				osw = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void connect() {
		/*
		 * 数据库连接发生异常就关闭链接
		 */
		try {
			Class.forName(packageName);
			conn = DriverManager.getConnection(url, table, password);
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select  * from " + tableName); // 查询下确定结果集是那个表的
			meta = rs.getMetaData(); // 调用结果集的记录表信息的方法

			coding.append("public class " + (char) (tableName.charAt(0) - 32)
					+ tableName.substring(1) + "{\n");
			// System.out.println("a a a ");
			// rs.next();
			// System.out.println(rs.getString("name"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}

			} catch (SQLException e1) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		}

	}


	
	private List<Map<String, String>> getColumenName() {
		/*
		 * 得到表的所有列名以字符串数组的形式返回
		 */
		int count;
		List  s = new ArrayList<Map<String, String>>();
		try {
			count = meta.getColumnCount();
			for (int i = 1; i <= count; i++) {
			  Map map=new HashMap<String, String>();
			  map.put("type", meta.getColumnTypeName(i));
			  map.put("name", meta.getColumnName(i));
			  s.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	private void writeData(String message) {

		try {
			fos = new FileOutputStream(filename);
			osw = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osw);
			bw.write(message);
			bw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private StringBuffer getimp(){
		StringBuffer imp=new StringBuffer();
		imp.append("import java.util.Date;\n");
		imp.append("import javax.persistence.Basic;\n");
		imp.append("import javax.persistence.Column;\n");
		imp.append("import javax.persistence.Entity;\n");
		imp.append("import javax.persistence.GeneratedValue;\n");
		imp.append("import javax.persistence.GenerationType;\n");
		imp.append("import javax.persistence.Id;\n");
		imp.append("import javax.persistence.Table;\n\n");
		imp.append("@Entity\n");
		imp.append("@Table(name = \"").append(tableName).append("\")\n");
		return imp;
	}
	
	public static void main(String[] args) {
		String url="jdbc:mysql://127.0.0.1:3306/sgmew"; //数据库连接地址
		String username="root"; //数据库登录名
		String pwd="";//数据库密码
		String tablename="bo_bonusdetail"; //表名
		String file="C:\\data.java";  //输出文件
		
		
		POJOUtil ta = new POJOUtil("com.mysql.jdbc.Driver",url, username, pwd,tablename,file);
		ta.connect();
		List<Map<String, String>> s=new ArrayList<Map<String,String>>();
		s = ta.getColumenName();
		for(Map<String,String> map:s){
			ta.generate1(map.get("name"),map.get("type"));
		}
		for(Map<String,String> map:s){
			ta.generate2(map.get("name"),map.get("type"));
		}
		coding.append("}");
		ta.writeData(ta.getimp().toString()+coding);
		ta.destroy();
		System.out.println("--文件已生成----地址："+file);
	}

}
