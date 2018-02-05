package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CouponTypeLogUtils {
	private static final String DICT_CODE = "LOG_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 批量新增*/
	public static final String BATCH_ADD = "1";
	/** 修改*/
	public static final String UPDATE = "2";
	/** 禁用*/
	public static final String ENABLE = "3";
	/** 启用*/
	public static final String START = "4";
	/** 删除*/
	public static final String DELETE = "5";
	/** 批量修改*/
	public static final String BATCH_UPDATE = "6";
	/** 绑定*/
	public static final String BIND = "7";
	/** 使用*/
	public static final String USE = "8";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(BATCH_ADD, "批量新增");
		DICT_MAP.put(UPDATE, "修改");
		DICT_MAP.put(ENABLE, "禁用");
		DICT_MAP.put(START, "启用");
		DICT_MAP.put(DELETE, "删除");
		DICT_MAP.put(BATCH_UPDATE, "批量修改");
		DICT_MAP.put(BIND, "绑定");
		DICT_MAP.put(USE, "使用");
   }
}
