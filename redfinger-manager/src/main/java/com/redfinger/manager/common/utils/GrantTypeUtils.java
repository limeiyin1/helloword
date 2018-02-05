package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class GrantTypeUtils {
	@SuppressWarnings("unused")
	private static final String DICT_CODE = "RF_PAD.GRANT_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 管理员授权*/
	public static final String ADMIN = "0";
	/** 用户授权*/
	public static final String USER = "1";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(ADMIN, "管理员授权");
		DICT_MAP.put(USER, "用户授权");
   }
}
