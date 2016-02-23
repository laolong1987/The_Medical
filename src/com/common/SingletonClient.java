package com.common;


import com.utils.SettingUtil;




public class SingletonClient {
	private static Client client=null;
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		if(client==null){
			try {
				client=new Client(SettingUtil.getSetting("softwareSerialNo"),SettingUtil.getSetting("key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	
}
