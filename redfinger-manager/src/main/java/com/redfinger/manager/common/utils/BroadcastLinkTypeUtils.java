package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class BroadcastLinkTypeUtils {

	private static final String DICT_CODE = "BROADCAST_LINK_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 网页链接*/
	public static final String  WEB_PAGE= "1";
	/** 界面链接*/
	public static final String APK_DETAIL = "2";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(WEB_PAGE, " 网页链接");
		DICT_MAP.put(APK_DETAIL, "界面链接");
   }
}
