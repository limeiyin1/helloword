package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class GrantCodeStatusUtils {
	private static final String DICT_CODE = "PAD_CODE_STATUS";//对应数据库表SYS_DICT的DICT_CODE值
	/** 未使用*/
	public static final String NOT_USED = "0";
	/** 已使用*/
	public static final String USED = "1";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(NOT_USED, "未使用");
		DICT_MAP.put(USED, "已使用");
   }
}
