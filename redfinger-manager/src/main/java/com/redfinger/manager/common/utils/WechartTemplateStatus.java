package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class WechartTemplateStatus {
	
	/**未发送*/
	public static String NO_SEND = "0";
	/**发送成功*/
	public static String SEND_SUCCESS = "1";
	/**发送失败*/
	public static String SEND_FAIL = "2";
	/**取消发送*/
	public static String SEND_CONCEL = "3";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
	
	static {
		DICT_MAP.put(NO_SEND, "未发送");
		DICT_MAP.put(SEND_SUCCESS, "发送成功");
		DICT_MAP.put(SEND_FAIL, "发送失败");
		DICT_MAP.put(SEND_CONCEL, "取消发送");
    }
}
