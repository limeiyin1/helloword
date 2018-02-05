package com.redfinger.manager.common.sms;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 短信类型
 * @author liulu
 *
 */
public class SmsType {
	private static final String DICT_CODE = "SMS_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 短信验证码 */
	public static final String SMS = "1";
	/** 语音验证码 */
	public static final String VOICE = "2";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(SMS, "短信验证码");
		DICT_MAP.put(VOICE, "语音验证码");
   }
}
