package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PictureLinkTypeUtils {

	private static final String DICT_CODE = "PICTURE_LINK_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** apk详情页面*/
	public static final String APK_DETAIL = "1";
	/** 网页链接地址*/
	public static final String WEB_PAGE = "2";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(APK_DETAIL, "apk详情页面");
		DICT_MAP.put(WEB_PAGE, "网页链接地址");
   }
}
