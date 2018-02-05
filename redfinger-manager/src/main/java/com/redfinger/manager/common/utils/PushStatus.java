package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 推送状态
 * @author liulu
 *
 */
public class PushStatus {
	private static final String DICT_CODE = "PUSH_STATUS";//对应数据库表SYS_DICT的DICT_CODE值
	/** 未推送*/
	public static final String NO_PUSH = "1";
	/** 推送成功 */
	public static final String PUSH_SUCCESS = "2";
	/** 推送失败 */
	public static final String PUSH_FAIL = "3";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(NO_PUSH, "未推送");
		DICT_MAP.put(PUSH_SUCCESS, "推送成功");
		DICT_MAP.put(PUSH_FAIL, "推送失败");
   }
}
