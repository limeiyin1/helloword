package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class YesOrNoStatus {
	@SuppressWarnings("unused")
	private static final String DICT_CODE = "YES_NO";//对应数据库表SYS_DICT的DICT_CODE值
	/** 否*/
	public static final String NO = "0";
	/** 是 */
	public static final String YES = "1";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(YES, "是");
		DICT_MAP.put(NO, "否");
   }
}
