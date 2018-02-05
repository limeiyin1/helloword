package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class FacilityConstants {
	/** 红手指*/
	public static final String REDFINGER = "redfinger";
	/** 百度*/
	public static final String BAIDU = "baidu";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(REDFINGER, "红手指");
		DICT_MAP.put(BAIDU, "百度");
   }
}
