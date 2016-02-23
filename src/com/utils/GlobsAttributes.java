package com.utils;


/**
 * 公共信息
 *
 * @author 高扬
 */
public class GlobsAttributes
{
	
    /**
     * 系统的外网绝对路径的替换符号：&lt;APPSITE&gt;
     */
    public final static String APPSITE = "<APPSITE>";
    /**
     * 空字符串 ""
     */
    public final static String STR_EMPTY = "";
    /**
     * 时间格式 yyyyMMdd
     */
    public final static String TIME_FORMAT_EIGHT = "yyyyMMdd";
    /**
     * 英文标点符号 "-"
     */
    public final static String RAIL = "-";
    /**
     * 时间格式 yyyy-MM-dd
     */
    public final static String TIME_FORMAT_TEN = "yyyy-MM-dd";
	  /**
     * 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public final static String TIME_FORMAT_THIRTEEN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式 HH:mm:ss
     */
    public final static String TIME_FORMAT_SIX = "HH:mm:ss";

    /**
     * 微信接收的消息的类型数组 [0]-text, [1]-image ,[2]-location ,[3]-link ,[4]-event
     */
    public final static String[] ARRAY_RECEIVE_MESSAGE_TYPE = {"text", "image", "location", "link", "event"};

    /**
     * 微信发送的消息的类型数组 [0]-text, [1]-music ,[2]-news
     */
    public final static String[] ARRAY_SEND_MESSAGE_TYPE = {"text", "music", "news"};

    /**
     * (数据库中保存的)微信接收消息的值的最大允许长度 值1：400
     */
    public final static int MAXLENGTH_WX_MESSAGE_VALUE1 = 400;

    /**
     * (数据库中保存的)微信接收消息的值的最大允许长度 值2：300
     */
    public final static int MAXLENGTH_WX_MESSAGE_VALUE2 = 300;

    /**
     * (数据库中保存的)微信接收消息的值的最大允许长度 值3：300
     */
    public final static int MAXLENGTH_WX_MESSAGE_VALUE3 = 300;

}