package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class GrantControlUtils {
	private static final String DICT_CODE = "GRANT_CONTROL";//对应数据库表SYS_DICT的DICT_CODE值
	/** 可控制*/
	public static final String IS_CONTROL = "1";
	/** 不可控制 */
	public static final String NO_CONTROL = "0";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(IS_CONTROL, "可控制");
		DICT_MAP.put(NO_CONTROL, "不可控");
   }
}
