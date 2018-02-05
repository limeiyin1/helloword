package com.redfinger.manager.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResultType {
	/** 返回 */
	public static final String CODE = "code";
	/** 成功 */
	public static final String SUCCESS = "200";
	/** 失败 */
	public static final String FAIL = "501";
	/** 信息 */
	public static final String MSG = "msg";
	/** 失败次数 */
	public static final String FAILT_COUNT = "fail_count";
	/** 需要修改密码 */
	public static final String needResetPwd = "999";
	
	public static final String USER_TEMP="user_temp";
	
	
	
	/** 键值对象 */
	public static Map<String, String> RESULT_MAP = new LinkedHashMap<String, String>();
   
	static {
		RESULT_MAP.put(SUCCESS, "成功");
		RESULT_MAP.put(FAIL, "失败");
    }
}
