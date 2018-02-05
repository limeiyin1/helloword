package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameStatusUtils {
	private static final String DICT_CODE = "GAME_STATUS";//对应数据库表SYS_DICT的DICT_CODE值
	/** 正常*/
	public static final String NORMAL = "1";
	/** 维护 */
	public static final String MAINTENANCE = "2";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(NORMAL, "正常");
		DICT_MAP.put(MAINTENANCE, "维护");
   }
}
