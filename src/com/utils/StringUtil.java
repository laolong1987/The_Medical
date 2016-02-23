package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static boolean isStrEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	public static boolean isStrTrimEmpty(String str) {
		return (str == null) || (str.trim().equals(""));
	}

	/**
	 * 将传入的对象转换为字符串，当传入的对象为null时返回默认值
	 * 
	 * @param o
	 * @param dv
	 * @return
	 */
	public static String safeToString(Object o, String dv) {
		String r = dv;
		if (o != null) {
			r = String.valueOf(o);
			if("null".equals(r)){
				r = dv;
			}
			
		}

		return r;
	}

	/**
	 * 大小写互转
	 * @param str
	 * @return
	 */
	public static String convertString(String str) {
		String upStr = str.toUpperCase();
		String lowStr = str.toLowerCase();
		StringBuffer buf = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == upStr.charAt(i)) {
				buf.append(lowStr.charAt(i));
			} else {
				buf.append(upStr.charAt(i));
			}
		}
		return buf.toString();
	}
	
	/**
	 * 小写转大写
	 * @param str
	 * @return
	 */
	public static String convertStringUpper(String str) {
		String upStr = str.toUpperCase();
		StringBuffer buf = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); i++) {
			buf.append(upStr.charAt(i));
		}
		return buf.toString();
	}
	
	/**
	 * 大写转小写
	 * @param str
	 * @return
	 */
	public static String convertStringLower(String str) {
		String lowStr = str.toLowerCase();
		StringBuffer buf = new StringBuffer(str.length());
		for (int i = 0; i < str.length(); i++) {
				buf.append(lowStr.charAt(i));
		}
		return buf.toString();
	}
	/**
	 * 对人名进行校验
	 * @param name
	 * @return
	 */
	public static boolean validateEmpName(String name){
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]{2,4}");
		Matcher matcher = pattern.matcher(name);
	    return  matcher.matches();
	}
	/**
	 * 对中文字符串进行处理
	 * @param name
	 * @return
	 */
	public static boolean validateCheseString(String name){
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]{2,}");
		Matcher matcher = pattern.matcher(name);
	    return  matcher.matches();
	}
	/**
	 * 校验岗位
	 * @param position
	 * @return
	 */
	public static boolean validatePosition(String position){
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]{3,5}");
		Matcher matcher = pattern.matcher(position);
	    return  matcher.matches();
	}
	/**
	 * 校验品牌
	 * @param brand
	 * @return
	 */
	public static boolean validateBrand(String brand){
		Pattern pattern = Pattern.compile("[A-Z]{1}[a-z]{4}$");
		Matcher matcher = pattern.matcher(brand);
	    return  matcher.matches();
	}
	/**
	 * 校验手机号码	
	 * @param phone
	 * @return
	 */
	public static boolean validatePhone(String phone){
		Pattern pattern = Pattern.compile("\\d{11}");
		Matcher matcher = pattern.matcher(phone);
	    return  matcher.matches();
	}
	/**
	 * 校验经销商代码
	 * @param dealercode
	 * @return
	 */
	public static boolean validateDealerCode(String dealercode){
		Pattern pattern = Pattern.compile("[A-Z]{2}[0-9]{4}");
		Matcher matcher = pattern.matcher(dealercode);
	    return  matcher.matches();
	}
	/**
	 * 校验身份证		
	 * @param idCard
	 * @return
	 */
	public static boolean validateIdCard(String idCard){
		Pattern pattern = Pattern.compile("([0-9]{17}([0-9]|X){1})|([0-9]{15,18})");
		Matcher matcher = pattern.matcher(idCard);
	    return  matcher.matches();
	}
	/**
	 * 校验邮箱地址
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email){
		Pattern pattern = Pattern.compile("\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)");
		Matcher matcher = pattern.matcher(email);
	    return  matcher.matches();
	}
	/**
	 * 校验银行账号	
	 * @param account
	 * @return
	 */
	public static boolean validateAccount(String account){
		Pattern pattern = Pattern.compile("\\d{16,19}");
		Matcher matcher = pattern.matcher(account);
	    return  matcher.matches();
	}
	/**
	 * 校验日期
	 * @param date
	 * @return
	 */
	public static boolean validateDate(String date){
		Pattern pattern = Pattern.compile("^\\d{4}-[0-1]\\d-[0-3]\\d [0-2][0-4]:[0-6]\\d:[0-6]\\d$");
		Matcher matcher = pattern.matcher(date);
	    return  matcher.matches();
	}
	/**
	 * 校验DOSS id
	 * @param dossid
	 * @return
	 */
	public static boolean validateDossID(String dossid){
		Pattern pattern = Pattern.compile("([A-Z]{6})|([0-9A-Z]{6})");
		Matcher matcher = pattern.matcher(dossid);
	    return  matcher.matches();
	} 
	
	/**
	 * 验证ewid
	 * @param ewid
	 * @return
	 */
	public static boolean validateEWID(String ewid){
		Pattern pattern = Pattern.compile("[A-Z]{4}[0-9]{7}");
		Matcher matcher = pattern.matcher(ewid);
	    return  matcher.matches();
	}
	/**
	 * 校验奖金
	 * @param bonus
	 * @return
	 */
	public static boolean validateBonus(String bonus){
		Pattern pattern = Pattern.compile("^([1-9][0-9]*)+(.[0-9]{1,2})?$");
		Matcher matcher = pattern.matcher(bonus);
	    return  matcher.matches();
	}
	
	public static boolean validateBatch(String batch){
		Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5A-Za-z0-9_]+$");
		Matcher matcher = pattern.matcher(batch);
	    return  matcher.matches();
	}
	
	public static void main(String[] args) {
		System.out.println(validateIdCard("412723199006065975"));
	}
}
