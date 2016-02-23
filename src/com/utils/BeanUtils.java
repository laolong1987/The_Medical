package com.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import com.web.entity.Admin;

/**
 * javabean转换方法
 * @author 高扬
 *
 */
public class BeanUtils {

	/**
	 * bean转map
	 * @param bean
	 * @return
	 */
	 public static Map<String, Object> BeanToMap(Object obj) {
	        if(obj == null){
	            return null;
	        }        
	        Map<String, Object> map = new HashMap<String, Object>();
	        try {
	            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	            for (PropertyDescriptor property : propertyDescriptors) {
	                String key = property.getName();

	                // 过滤class属性
	                if (!key.equals("class")) {
	                    // 得到property对应的getter方法
	                    Method getter = property.getReadMethod();
	                    Object value = getter.invoke(obj);

	                    map.put(key, value);
	                }

	            }
	        } catch (Exception e) {
	            System.out.println("transBean2Map Error " + e);
	        }

	        return map;

	    }
	
	public static void main(String[] args) {
	Admin admin=new Admin();
	admin.setName("张三");
	     Map map= BeanUtils.BeanToMap(admin);
	     System.out.println(map.get("name"));
	}
}
