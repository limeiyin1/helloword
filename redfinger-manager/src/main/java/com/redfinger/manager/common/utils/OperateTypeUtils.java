package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class OperateTypeUtils {
	private static final String DICT_CODE = "OPERATE_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 用户授权 */
	public static final String USER_GAVE = "1";
	/** 用户取消授权 */
	public static final String USER_EABLE_GAVE = "2";
	/** 系统自动取消授权 */
	public static final String SYSTEM_ENABLE_GAVE = "3";
	/** 管理员进行授权 */
	public static final String ADMIN_GAVE = "4";
	/** 管理员取消授权 */
	public static final String ADMIN_ENABLE_GAVE = "5";
	/** 更换设备取消授权*/
	public static final String RENEWAL_ENABLE_GAVE = "6";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(USER_GAVE, "用户授权");
		DICT_MAP.put(USER_EABLE_GAVE, "用户取消授权");
		DICT_MAP.put(SYSTEM_ENABLE_GAVE, "系统自动取消授权 ");
		DICT_MAP.put(ADMIN_GAVE, "管理员进行授权");
		DICT_MAP.put(ADMIN_ENABLE_GAVE, "管理员取消授权");
		DICT_MAP.put(RENEWAL_ENABLE_GAVE, "更换设备取消授权");
   }
}
