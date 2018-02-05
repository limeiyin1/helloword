package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActivationCodeOperateType {
	private static final String DICT_CODE = "ACTIVATION_CODE_OPERATE_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 批量新增*/
	public static final String BATCH_ADD = "1";
	/** 修改*/
	public static final String UPDATE = "2";
	/** 禁用*/
	public static final String ENABLE = "3";
	/** 启用*/
	public static final String START = "4";
	/** 激活*/
	public static final String ACTIVATED = "5";
	/** 删除*/
	public static final String DELETE = "6";
	/** 批量修改*/
	public static final String BATCH_UPDATE = "7";
	/** 绑定*/
	public static final String BIND = "8";
	/** 续费*/
	public static final String RENEW = "9";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(BATCH_ADD, "批量新增");
		DICT_MAP.put(UPDATE, "修改");
		DICT_MAP.put(ENABLE, "禁用");
		DICT_MAP.put(START, "启用");
		DICT_MAP.put(ACTIVATED, "激活");
		DICT_MAP.put(DELETE, "删除");
		DICT_MAP.put(BATCH_UPDATE, "批量修改");
		DICT_MAP.put(BIND, "绑定");
		DICT_MAP.put(RENEW, "续费");
   }
}
