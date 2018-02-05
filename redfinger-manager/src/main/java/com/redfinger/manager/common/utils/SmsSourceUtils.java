package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmsSourceUtils {
	private static final String DICT_CODE = "SMS_SOURCE";//对应数据库表SYS_DICT的DICT_CODE值
	
	/** 普通设备申请 */
	public static final String normal_pad = "1";
	/** 体验设备申请*/
	public static final String taste_pad = "2";
	/** 注册 */
	public static final String register = "3";
	/** 解绑邮箱*/
	public static final String unbund_email = "4";
	/** 找回密码*/
	public static final String back_password = "5";
	/** 后台发送*/
	public static final String manager = "6";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(normal_pad, "普通设备申请");
		DICT_MAP.put(taste_pad, "体验设备申请");
		DICT_MAP.put(register, "注册");
   }
}
