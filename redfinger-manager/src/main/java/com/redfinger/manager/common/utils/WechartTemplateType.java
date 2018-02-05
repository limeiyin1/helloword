package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class WechartTemplateType {

	/**游戏运行异常*/
	public static String GAME_ABNORMAL = "1";
	/**设备更换*/
	public static String REPLACE_PAD = "2";
	/**设备到期*/
	public static String PAD_EXPIRATION = "3";
	/**设备到期*/
	public static String PAD_WARM= "4";
	/**游戏运行掉线*/
	public static String OFFLINE_NOTICE = "5";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
	
	static {
		DICT_MAP.put(GAME_ABNORMAL, "游戏运行异常");
		DICT_MAP.put(REPLACE_PAD, "设备更换");
		DICT_MAP.put(PAD_EXPIRATION, "设备到期");
		DICT_MAP.put(PAD_WARM, "预警通知");
		DICT_MAP.put(OFFLINE_NOTICE, "游戏运行掉线");
    }
}
