package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class InformationTypeUtils {
	private static final String DICT_CODE = "INFORMATION_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 资讯*/
	public static final String INFORMATION = "1";
	/** 系统 */
	public static final String SYSTEM = "2";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(INFORMATION, "资讯");
		DICT_MAP.put(SYSTEM, "系统");
   }
}
