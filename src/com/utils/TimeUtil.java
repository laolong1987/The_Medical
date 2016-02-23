package com.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * File Desc:  日期工具类
 * Author:      高扬
 */
public class TimeUtil {

    /**
     * 获得当前时间
     *
     * @return 时间格式为yyyy-MM-dd
     */
    public static String getSysDate() {
        return getCurrentDate(null);
    }

    /**
     * 获得当前时间
     *
     * @return Date对象
     */
    public static Date getSysDateAsDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获得当前时间
     *
     * @return 时间格式为HH:mm:ss
     */
    public static String getSysTime() {
        return getCurrentDate(GlobsAttributes.TIME_FORMAT_SIX);
    }

    /**
     * 得到固定格式的时间字符串
     * 121212
     *
     * @return
     */
    public static String getSysTime2() {
        String parseStr = "HHmmss";
        return getCurrentDate(parseStr);
    }

    /**
     * 获得当前时间
     *
     * @return 时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getSysDateAll() {
        return getCurrentDate(GlobsAttributes.TIME_FORMAT_THIRTEEN);
    }

    /**
     * 根据格式，获取当前时间
     *
     * @param format 时间格式，默认为"yyyy-MM-dd"
     *               "2003-02-01"标示为"yyyy-MM-dd",
     *               24小时："23:12:21"-->"HH:mm:ss"
     *               12小时：设为"hh:mm:ss"
     * @return 当前时间字符串
     */
    public static String getCurrentDate(String format) {
        return formatDate(Calendar.getInstance().getTime(), format);
    }

    /**
     * 根据格式，获取上月第一天
     *
     * @param format 时间格式，默认为"yyyy-MM-dd"
     * @return 上月第一天时间字符串
     */
    public static String getPreviousMonthFirst(String format) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);//减一个月，变为上月的1号
        return formatDate(lastDate.getTime(), format);
    }

    /**
     * 根据格式，获取上月第一天
     *
     * @return 上月第一天时间
     */
    public static Date getLastMonthFirstDay() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);//减一个月，变为上月的1号
        return lastDate.getTime();
    }

    /**
     * 根据格式，获取当月第一天
     *
     * @param format 时间格式，默认为"yyyy-MM-dd"
     * @return 当月第一天时间字符串
     */
    public static String getFirstDayOfMonth(String format) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        return formatDate(lastDate.getTime(), format);
    }

    /**
     * 根据格式，获取下月第一天
     *
     * @param format 时间格式，默认为"yyyy-MM-dd"
     * @return 下月第一天时间字符串
     */
    public static String getNextMonthFirst(String format) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号
        return formatDate(lastDate.getTime(), format);
    }

    /**
     * 格式化时间
     *
     * @param date   时间对象
     * @param format 时间格式，默认为"yyyy-MM-dd"
     * @return 格式化后的时间
     */
    public static String formatDate(Date date, String format) {
        try {
            if (StringUtil.isStrEmpty(format))
                format = GlobsAttributes.TIME_FORMAT_TEN;
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String formatDateToString(String date, String format) {
        if (StringUtil.isStrEmpty(format))
            format = GlobsAttributes.TIME_FORMAT_TEN;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar _cal = Calendar.getInstance();
        try {
            return dateFormat.format(dateFormat.parse(date));
        } catch (Exception ex) {
         
            return "";
        }
    }

    /**
     * 根据格式，获取上月最后一天
     *
     * @param format 时间格式，默认为"yyyy-MM-dd"
     * @return 上月最后一天时间字符串
     */
    public static String getPreviousMonthLast(String format) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天
        return formatDate(lastDate.getTime(), format);
    }

    /**
     * 获取上个月的最后一天
     *
     * @param endDate 格式为"yyyy-MM-dd"
     * @param format
     * @return
     */
    public static String getPrevioutMonthLast(String endDate, String format) {
        int year = Integer.valueOf(endDate.substring(0, 4));
        int month = Integer.valueOf(endDate.substring(5, 7));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        calendar.add(Calendar.DATE, -1);//减去一天，变为当月最后一天
        return formatDate(calendar.getTime(), format);
    }

    /**
     * 根据格式，获取当月最后一天
     *
     * @param format 时间格式，默认为"yyyy-MM-dd"
     * @return 当月最后一天时间字符串
     */
    public static String getLastDayOfMonth(String format) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天
        return formatDate(lastDate.getTime(), format);
    }

    /**
     * 根据格式，获取下月最后一天
     *
     * @param format 时间格式，默认为"yyyy-MM-dd"
     * @return 下月最后一天时间字符串
     */
    public static String getNextMonthLast(String format) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, 2);//加2个月，变为下下月的1号
        lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天
        return formatDate(lastDate.getTime(), format);
    }

    /**
     * 将20090321型日期转换成"yyyy-MM-dd"
     *
     * @param date 8位数字型日期
     * @return "yyyy-MM-dd"日期
     */
    public static String getSysDateInString(Long date) {
        String sdate = String.valueOf(date);
        StringBuffer buffer = new StringBuffer();
        buffer.append(sdate.substring(0, 4)).append(GlobsAttributes.RAIL).append(sdate.substring(4, 6)).append(GlobsAttributes.RAIL).append(sdate.substring(6));
        return buffer.toString();
    }

    /**
     * 得到某天的前n天（n小于0），或后n天(n大于0)
     *
     * @param someDay
     * @param n       时间间隔天数 前n天（n小于0），或后n天(n大于0)
     * @return
     */
    public static String getPreOrNextDay(String someDay, int n) {
        Date someDate = null;
        if (StringUtil.isStrEmpty(someDay)) {
            someDate = Calendar.getInstance().getTime();
        } else {
            someDate = formatDate(someDay, null);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(someDate);
        calendar.add(Calendar.DAY_OF_YEAR, n);
        return getSysDate(calendar.getTime());
    }

    /**
     * 得到某天的前n天（n小于0），或后n天(n大于0)
     *
     * @param date
     * @param n    时间间隔天数 前n天（n小于0），或后n天(n大于0)
     * @return
     */
    public static Date getPreOrNextDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, n);
        return calendar.getTime();
    }

    /**
     * 得到某天的前n天（n小于0），或后n天(n大于0)
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayOfWeek) {
            return "周日";
        } else if (2 == dayOfWeek) {
            return "周一";
        } else if (3 == dayOfWeek) {
            return "周二";
        } else if (4 == dayOfWeek) {
            return "周三";
        } else if (5 == dayOfWeek) {
            return "周四";
        } else if (6 == dayOfWeek) {
            return "周五";
        } else if (7 == dayOfWeek) {
            return "周六";
        } else
            return "";
    }

    /**
     * 日期的字符串形式转成Date
     *
     * @param date   String 默认格式为yyyy-MM-dd
     * @param format 格式，null则表示默认
     * @return Date 日期
     */
    public static Date formatDate(String date, String format) {
        if (format == null)
            format = GlobsAttributes.TIME_FORMAT_TEN;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date sysdate = null;
        if (date == null || "".equals(date)) {
            return null;
        }
        try {
            sysdate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sysdate;
    }

    /**
     * 得到当前的周数
     *
     * @param endDate
     * @return
     */
    public static int getCurrentWeek(String endDate) {
        Date someDate = null;
        if (StringUtil.isStrEmpty(endDate)) {
            someDate = Calendar.getInstance().getTime();
        } else {
            someDate = formatDate(endDate, null);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(someDate);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.get(Calendar.WEEK_OF_YEAR) + 1;
    }



    /**
     * 给某个时间增加（减少）小时
     *
     * @param date
     * @param hour +或-
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }


    /**
     * 根据传入的格式化参数，返回日期字符串
     *
     * @param date 需要格式化的日期
     * @return dateStr 返回格式化的字符串日期,形式为"yyyy-MM-dd"
     */
    public static String getSysDate(Date date) {
        if (date == null) {
            return null;
        }
        return formatDate(date, null);
    }

    /**
     * 得到八位数字型的当前日期
     *
     * @return (Long) yyyyMMdd型当前日期
     */
    public static Long getSysDateInLong() {
        return Long.valueOf(getCurrentDate(GlobsAttributes.TIME_FORMAT_EIGHT));
    }

    /**
     * 格式化时间
     *
     * @param strDate "yyyy-MM-dd"格式的字符串
     * @return 格式为"yyyyMMdd"的Long型
     */
    public static Long getFormatDate(String strDate) {
        if (strDate == null || strDate.equals(GlobsAttributes.STR_EMPTY))
            return Long.valueOf(0);
        StringBuffer buffer = new StringBuffer();
        buffer.append(strDate.substring(0, 4)).append(strDate.substring(5, 7)).append(strDate.substring(8, 10));
        return Long.valueOf(buffer.toString());
    }

    /**
     * 格式化yyyy-MM-dd HH:mm:ss的时间为yyyyMMdd日期
     *
     * @param strDate
     * @return
     */
    public static String getLongFormatDate(String strDate) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(strDate.substring(0, 4)).append(strDate.substring(5, 7)).append(strDate.substring(8, 10));
        return buffer.toString();
    }

    /**
     * 得到某天天之后N天日期
     *
     * @param someDay yyyyMMdd数字型日期
     * @param days    N天
     * @return yyyyMMdd数字型日期
     */
    @SuppressWarnings("deprecation")
    public static Long getSomeDayInLongAfterDays(Long someDay, int days) {
        Date d = formatDate(String.valueOf(someDay), GlobsAttributes.TIME_FORMAT_EIGHT);
        d.setDate(d.getDate() + days);
        return Long.valueOf(formatDate(d, GlobsAttributes.TIME_FORMAT_EIGHT));
    }

    /**
     * 得到某天之前N天日期
     *
     * @param someDay yyyyMMdd数字型日期
     * @param days    N天
     * @return yyyyMMdd数字型日期
     */
    @SuppressWarnings("deprecation")
    public static Long getSomeDayInLongBeforeDays(Long someDay, int days) {
        Date d = formatDate(String.valueOf(someDay), GlobsAttributes.TIME_FORMAT_EIGHT);
        d.setDate(d.getDate() - days);
        return Long.valueOf(formatDate(d, GlobsAttributes.TIME_FORMAT_EIGHT));
    }

    /**
     * 得到今天之前30天日期
     *
     * @return 日期
     */
    @SuppressWarnings("deprecation")
    public static String getTheDayBeforeOneMonth() {
        Long theDay = getSomeDayInLongBeforeDays(getSysDateInLong(), 30);
        String date = String.valueOf(theDay);
        date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        return date;
    }

    /**
     * 得到当前天之后N天日期
     *
     * @param days N天
     * @return yyyyMMdd数字型日期
     */
    @SuppressWarnings("deprecation")
    public static Long getDayInLongAfterDays(int days) {
        Date d = Calendar.getInstance().getTime();
        d.setDate(d.getDate() + days);
        return Long.valueOf(formatDate(d, GlobsAttributes.TIME_FORMAT_EIGHT));
    }

    /**
     * 得到某天天之后N天日期和时间
     *
     * @param someDay yyyyMMdd数字型日期
     * @param days    N天
     * @return 格式为yyyy-MM-dd HH:mm:ss的时间
     */
    @SuppressWarnings("deprecation")
    public static String getSomeDayTimeInLongAfterDays(Long someDay, int days) {
        Date d = formatDate(String.valueOf(someDay), GlobsAttributes.TIME_FORMAT_EIGHT);
        d.setDate(d.getDate() + days);
        return formatDate(d, GlobsAttributes.TIME_FORMAT_THIRTEEN);
    }

    /**
     * 得到某天之后N月日期
     *
     * @param someDay yyyyMMdd数字型日期
     * @param monNum  N月
     * @return yyyyMMdd数字型日期
     */
    @SuppressWarnings("deprecation")
    public static Long getSomeMonthDayInLong(Long someDay, int monNum) {
        Date d = formatDate(String.valueOf(someDay), GlobsAttributes.TIME_FORMAT_EIGHT);
        d.setMonth(d.getMonth() + monNum);
        return Long.valueOf(formatDate(d, GlobsAttributes.TIME_FORMAT_EIGHT));
    }



    /**
     * 计算两个日期的天数差值
     *
     * @param beginDate
     * @param endDate
     * @return 天数
     */
    @SuppressWarnings("deprecation")
    public static long getBetweenDays(Date beginDate, Date endDate) {
        endDate.setHours(beginDate.getHours());
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        double betweenDays = (((endTime - beginTime) * 1.0) / (1000 * 60 * 60 * 24)) + 0.5;
        return (long) (Math.floor(betweenDays));
    }

    /**
     * 根据传入的格式化参数，返回日期字符串
     *
     * @param parseFormat 要格式化成的样子 例如"2003-02-01"标示为"yyyy-MM-dd", 如果要格式为
     *                    24小时："23:12:21"-->"HH:mm:ss" 12小时：设为"hh:mm:ss"
     * @return dateStr 返回格式化的字符串日期
     */
    public static String getSysDate(String parseFormat) {
        String sysdate = "";
        try {
            String parseStr = parseFormat;
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(parseStr);
            sysdate = dateFormat.format(date);
        } catch (IllegalArgumentException illex) {
            illex.printStackTrace();
            sysdate = "";
        } catch (NullPointerException nullex) {
            nullex.printStackTrace();
            sysdate = "";
        }

        return sysdate;

    }

    /**
     * 得到报告期开始日期
     *
     * @param reportPeriod  报告期类型
     * @param reportEndDate 报告截止日期 (格式为yyyyMMdd)
     * @return
     */
    public static String getReportBeginDate(String reportPeriod, String reportEndDate) {
        String result = "";
        String year = reportEndDate.substring(0, 4);
        int month = Integer.valueOf(reportEndDate.substring(5, 7));
        // 月报
        if ("1".equals(reportPeriod)) {
            result = year + "-01-01";
            // 季报
        } else if ("2".equals(reportPeriod)) {
            result = year + "-" + String.format("%02d", month - 2) + "-01";
            // 半年报
        } else if ("3".equals(reportPeriod)) {
            if (month == 6) {
                result = year + "-01-01";
            } else {
                result = year + "-07-01";
            }
            // 年报
        } else {
            result = year + "-01-01";
        }
        return result;
    }

    /**
     * 得到报告期名称
     *
     * @param reportPeriod  报告期类型
     * @param reportEndDate 报告截止日期 (格式为yyyyMMdd)
     * @return
     */
    public static String getReportDateName(String reportPeriod, String reportEndDate) {
        String result = "";
        String year = reportEndDate.substring(0, 4);
        int month = Integer.valueOf(reportEndDate.substring(5, 7));
        // 月报
        if ("1".equals(reportPeriod)) {
            result = year + "年" + month + "月";
            // 季报
        } else if ("2".equals(reportPeriod)) {
            result = year + "年" + (month / 3) + "季度";
            // 半年报
        } else if ("3".equals(reportPeriod)) {
            if (month == 6) {
                result = year + "上半年";
            } else {
                result = year + "下半年";
            }
            // 年报
        } else {
            result = year + "年";
        }
        return result;
    }

    /**
     * 得到当前季度的开始结束时间
     *
     * @param flag
     * @return
     */
    public static String[] getReportDateList(String flag) {
        if (flag == null || flag.length() < 4) {
            return new String[2];
        }
        String[] result = new String[2];
        String year;
        int season;
        if (flag.length() == 4) {
            result[0] = flag + "-01-01";
            result[1] = flag + "-12-31";
        } else {
            year = flag.substring(0, 4);
            season = Integer.parseInt(flag.substring(4));
            if (season == 1) {
                result[0] = year + "-01-01";
                result[1] = year + "-03-31";
            }
            if (season == 2) {
                result[0] = year + "-04-01";
                result[1] = year + "-06-30";
            }
            if (season == 3) {
                result[0] = year + "-07-01";
                result[1] = year + "-09-30";
            }
            if (season == 4) {
                result[0] = year + "-10-01";
                result[1] = year + "-12-31";
            }
        }
        return result;
    }

    public static int getSysCurrYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 取得日期, 整型数值yyyymmdd
     */
    public static Date getDateFromYYYYMMDDInt(int d) {
        GregorianCalendar cl = new GregorianCalendar();
        int year = d / 10000;
        int month = (d % 10000) / 100 - 1;
        int day = d % 100;
        cl.set(year, month, day, 0, 0, 0);
        return cl.getTime();
    }

    /**
     * 安全的转换为日期格式
     *
     * @param date
     * @param fmt
     * @return
     */
    public static Date safeToDate(Object date, String fmt, Date dv) {
        Date result = dv;
        if (date != null) {
            try {
                result = new SimpleDateFormat(fmt).parse(date.toString().trim());
            } catch (Exception ex) {
            }
        }
        return result;
    }
    
    /**
     * 根据date，获取这个月的最后一天
     *@param  date 时间，格式:yyyy-MM-dd"
     * @param format 时间格式，默认为"yyyy-MM-dd"
     * @return 当月最后一天时间字符串
     */
    public static String getLastDayOfMonth(String date, String format) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(formatDate(date, format));   
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天
        return formatDate(lastDate.getTime(), format);
    }
    
    
    public static void main(String[] args) {
        System.out.println(getLastDayOfMonth("2011-11-01","yyyy-MM-dd"));
    } 
}
