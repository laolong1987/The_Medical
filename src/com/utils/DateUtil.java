package com.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期格式工具
 * 
 * @author ninglei
 */
public class DateUtil {

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String字符串形态
	 */
	public static String getCurrentTime() {
		java.util.Date date = new java.util.Date();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String datastr = simpleDateFormat.format(date);
		return datastr;
	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String字符串形态
	 */
	public static String getCurrentTime(String pattern) {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String datastr = simpleDateFormat.format(date);
		return datastr;
	}

	public static final DateFormat getFormat() {
		return new SimpleDateFormat("yyyyMMdd HH:mm");
	}

	public static final DateFormat getMysqlFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public static final String SimpleDateFormat(Date date) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDate = time.format(date);
		return formatDate;
	}

	public static final String SimpleDateUserFormat(Date date, String formater) {
		SimpleDateFormat time = new SimpleDateFormat(formater);
		String formatDate = time.format(date);
		return formatDate;
	}

	public static final String FormatUIDate(Date date) {
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
		String formatDate = time.format(date);
		return formatDate;
	}

	public static final String FormatDate(String dateString) {
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date date = null;
		try {
			date = time.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String formatDate = time.format(date);
		return formatDate;
	}

	/**
	 * 比较当前时间是否在两个时间段内(小时:分钟)
	 * 
	 * @return
	 */

	@SuppressWarnings("deprecation")
	public static final boolean checkTimes(String time1, String time2) {
		Date date = new Date();
		Date date1 = new Date();
		Date date2 = new Date();
		String[] time = time1.split(":");
		date1.setHours(Integer.valueOf(time[0]));
		date1.setMinutes(Integer.valueOf(time[1]));
		time = time2.split(":");
		date2.setHours(Integer.valueOf(time[0]));
		date2.setMinutes(Integer.valueOf(time[1]));
		if (date.after(date1) && date.before(date2))
			return false;
		return true;
	}

	/**
	 * <p>
	 * Title: 周计算类，星期一为一周的开始，星期日为一周的结束
	 * </p>
	 * <p>
	 * Description: 在两年的交接地带还有疑问。
	 * </p>
	 * <p>
	 * 比如2006-12-29到2009-01-04，属于2008年的最后一周，
	 * </p>
	 * <p>
	 * 2009-01-05位2009年第一周的开始。
	 * </p>
	 * <p>
	 * db2种的week_iso也是这样计算的
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2006
	 * </p>
	 * <p>
	 * DateTime: 2006-4-11 23:36:39
	 * </p>
	 * 
	 */
	/**
	 * 取得当前日期是多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 得到某一年周的总数
	 * 
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 取得今天是本周第几天 以周日为第一天
	 * 
	 * @date 查询时间
	 * @int
	 */
	public static int getNumofWeek(Date date) {
		// 取得当前系统时间
		Calendar c = Calendar.getInstance();
		if (date != null)
			c.setTime(date);
		// // -2天
		// c.add(Calendar.DAY_OF_MONTH, -2);
		// 取得当前是这周的第几天
		int temp = c.get(Calendar.DAY_OF_WEEK);
		// 由于java计算是以周日为第一天
		return temp;
		// if (temp == 1) {
		// // 当是第一天时返回周日
		// return 7;
		// } else {
		// // 其他情况直接返回数字-1
		// return temp;
		// }
	}

	/**
	 * 取得今天是本周第几天 以周日为第一天 方法重载，默认为系统当前时间
	 * 
	 * @int
	 */
	public static int getNumofWeek() {
		return getNumofWeek(null);
	}

	/**
	 * 以字符串形式得到今日为周几
	 * 
	 * @param date
	 * @return
	 */
	public static String getNumOfWeekStr(Date date) {
		int num = getNumofWeek(date);
		String weekNum = "";
		switch (num) {
		case 1:
			weekNum = "日";
			break;
		case 2:
			weekNum = "一";
			break;
		case 3:
			weekNum = "二";
			break;
		case 4:
			weekNum = "三";
			break;
		case 5:
			weekNum = "四";
			break;
		case 6:
			weekNum = "五";
			break;
		case 7:
			weekNum = "六";
			break;
		}
		return weekNum;
	}

	/**
	 * 以字符串形式得到今日为周几 默认系统时间，方法重载
	 * 
	 * @return
	 */
	public static String getNumOfWeekStr() {
		return getNumOfWeekStr(null);
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 得到某年1月1日
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfThisYear() {
		Calendar c = new GregorianCalendar();
		Calendar now = Calendar.getInstance();
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 取得当前月份的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(int Month) {
		Calendar c = new GregorianCalendar();
		Calendar now = Calendar.getInstance();
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		c.set(Calendar.MONDAY, Month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 取得当前月份的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLatDayOfMonth(int Month) {
		Calendar c = new GregorianCalendar();
		Calendar now = Calendar.getInstance();
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		c.set(Calendar.MONTH, Month);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return getDateBefore(c.getTime(), 1);
	}

	/**
	 * 取得当前月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getNowMonth() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		return now.get(Calendar.MONTH);
	}
	public static int getMonth(Date date){
		Calendar canCalendar = Calendar.getInstance();
		canCalendar.setTime(date);
		return canCalendar.get(Calendar.MONTH);
	}
	/**
	 * 取得当前月份的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonth() {
		Calendar c = new GregorianCalendar();
		Calendar now = Calendar.getInstance();
		c.set(Calendar.YEAR, now.get(Calendar.YEAR));
		c.set(Calendar.MONTH, now.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	public static Date getMonth(int i) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, i - 1);
		return c.getTime();
	}

	public static boolean CompareDate(String date1, String date2)
			throws ParseException {
		Date Time1 = getMysqlFormat().parse(date1);
		Date Time2 = getMysqlFormat().parse(date2);
		if (Time1.before(Time2))
			return true;
		return false;

	}

	public static final String getTwresulttime() {
		Date date = new Date();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDate = time.format(date);
		return formatDate;
	}

	public static String getcurrentDatetime(String style) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		Date currentTime = new Date();
		String currentDatetime = formatter.format(currentTime);
		return currentDatetime;
	}

	public static Date String2Date(String dateString, String style) {
		Date date = new Date();
		SimpleDateFormat strToDate = new SimpleDateFormat(style);
		// parse format String to date
		try {
			date = strToDate.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static Timestamp String2Timestamp(String dateString, String style) {
		SimpleDateFormat df = new SimpleDateFormat(style);
		String time = df.format(String2Date(dateString, style));
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	public static String DateToCronExpression(Date date) {
		SimpleDateFormat time = new SimpleDateFormat("ss mm HH dd MM ?");
		String formatDate = time.format(date);
		return formatDate;
	}

	public static void main(String[] args) {
		System.out.println(getDay());
		System.out.println(getNumOfWeekStr(null));
	}
	/**
	 * 获取当天属本月的天数
	 * @return
	 */
	public static int getDay() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_MONTH);
	}
	
}
