package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class GrantModeUtils {
	private static final String DICT_CODE = "GRANT_MODE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 指定账号*/
	public static final String SPECIFY_ACCOUNT = "1";
	/** 授权码 */
	public static final String AUTHORIZATION_CODE = "2";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(SPECIFY_ACCOUNT, "指定账号");
		DICT_MAP.put(AUTHORIZATION_CODE, "授权码");
   }
}
