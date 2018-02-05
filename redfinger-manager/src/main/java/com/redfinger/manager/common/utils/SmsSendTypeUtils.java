package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmsSendTypeUtils {

	private static final String DICT_CODE = "SMS_SEND_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 5c*/
	public static final String FIVE_C = "5c";
	/** 阿里大于 */
	public static final String ALIDAYU = "alidayu";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(FIVE_C, "美联软通");
		DICT_MAP.put(ALIDAYU, "阿里大于");
   }
}
