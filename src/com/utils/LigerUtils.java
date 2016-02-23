package com.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Liger表格数据转换
 * @author 高扬
 *
 */
public class LigerUtils {
	public static JSONObject getJsonMap(List list){
		Map resultMap = new LinkedHashMap();
		resultMap.put("Rows", list);
		resultMap.put("Total", list.size());
		JSONObject object=JSONObject.fromObject(resultMap);
		return object;
	}
	
	public static JSONObject getJsonMap(List list,int zj){
		Map resultMap = new LinkedHashMap();
		resultMap.put("Rows", list);
		resultMap.put("Total", zj);
		JSONObject object=JSONObject.fromObject(resultMap);
		return object;
	}

}
