package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class TestPost {
/**
 * import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
 */
	
//	public String toPostSend() throws HttpException, IOException {
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost("http://www.sgmelite.com/sgm/login2.do");
//		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//		formParams.add(new BasicNameValuePair("username", "CH99990001"));
//		formParams.add(new BasicNameValuePair("password", "123"));
//		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams,
//				"UTF-8");
//		httpPost.setHeader("Content-Type", "text/plain; charset=UTF-8");
//		httpPost.setEntity(uefEntity);
//		System.out.println("executing request " + httpPost.getURI());
//		HttpResponse response = httpclient.execute(httpPost);
//		HttpEntity entity = response.getEntity();
//		String responseStr = null;
//		if (entity != null) {
//			 responseStr = EntityUtils.toString(entity,"UTF-8");
//			 System.out.println("---------------------------------------");
//			 System.out.println("Response content:" + responseStr);
//			 System.out.println("---------------------------------------");
//		}
//		return responseStr;
//	}
//
//	public static void main(String[] args) throws ClientProtocolException,
//			IOException {
//		HttpClient httpclient = new DefaultHttpClient();
////		HttpPost httpPost = new HttpPost("http://www.sgmelite.com/sgm/login2.do;jsessionid=");
//	    HttpPost httpPost = new HttpPost("http://www.renren.com/PLogin.do");
//		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
////		formParams.add(new BasicNameValuePair("username", "CH99990001"));
////		formParams.add(new BasicNameValuePair("password", "123"));
//		formParams.add(new BasicNameValuePair("email", "13795242060"));
//		formParams.add(new BasicNameValuePair("password", "1212121"));
//		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams,
//				"UTF-8");
//		httpPost.setHeader("Content-Type", "text/plain; charset=UTF-8");
//		httpPost.setEntity(uefEntity);
//		System.out.println("executing request " + httpPost.getURI());
//		HttpResponse response = httpclient.execute(httpPost);
//		HttpEntity entity = response.getEntity();
//		String responseStr = null;
//		if (entity != null) {
//			 responseStr = EntityUtils.toString(entity,"UTF-8");
//			 System.out.println("---------------------------------------");
//			 System.out.println("Response content:" + responseStr);
//			 System.out.println("---------------------------------------");
//		}
//
//	}
}
