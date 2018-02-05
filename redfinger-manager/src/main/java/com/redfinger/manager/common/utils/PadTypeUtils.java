package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PadTypeUtils {
	private static final String DICT_CODE = "PAD_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 普通*/
	public static final String NORMAL = "0";
	/** VIP*/
	public static final String VIP = "1";
	/**游戏设备*/
	public static final String GAME = "4";
	/**GVIP设备*/
	public static final String GVIP = "5";
	/**云控设备*/
	public static final String CLOUD_VIP = "6";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(NORMAL, "普通");
		DICT_MAP.put(VIP, "VIP");
		DICT_MAP.put(GAME, "游戏设备");
		DICT_MAP.put(GVIP, "高级VIP");
		DICT_MAP.put(CLOUD_VIP, "云控VIP");
   }
}
