package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class NoticeTypeUtils {
	private static final String DICT_CODE = "NOTICE_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 发送少量用户*/
	public static final String PART = "0";
	/** 发送全部用户*/
	public static final String TOTAL = "1";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(PART, "部分用户");
		DICT_MAP.put(TOTAL, "全部用户");
   }
}
