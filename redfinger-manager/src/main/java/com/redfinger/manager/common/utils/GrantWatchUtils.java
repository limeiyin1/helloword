package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class GrantWatchUtils {
	private static final String DICT_CODE = "GRANT_WATCH";//对应数据库表SYS_DICT的DICT_CODE值
	/** 可观看*/
	public static final String IS_WATCH = "1";
	/** 不可观看 */
	public static final String NO_WATCH = "0";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(IS_WATCH, "可观看");
		DICT_MAP.put(NO_WATCH, "不可观看");
   }
}
