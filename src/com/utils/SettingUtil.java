package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SettingUtil {
	private static String path = "/config/setting/setting.properties";


	public static String getSetting(String name) {
		Properties p = new Properties();
		String result="";
		try {
			InputStream in = SettingUtil.class.getResourceAsStream(path);
			 p.load(in);  
			 in.close();  
			 if(p.containsKey(name)){
				 result=String.valueOf(p.get(name));
			 }
		} catch (IOException ex) {
			
		}
		return result;  
	}
	
	public static void main(String[] args) {
		SettingUtil s=new SettingUtil();
		System.out.println(s.getSetting("appsecret"));
		 
	}
}