package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class BroadcastType {

	private static final String DICT_CODE = "BROADCAST_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/**后台发送*/
	public static final String BACKGROUND= "1";
	/**程序发送 */
	public static final String SOFTWARE = "2";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(BACKGROUND, "后台发送");
		DICT_MAP.put(SOFTWARE, "程序发送");
   }
}
