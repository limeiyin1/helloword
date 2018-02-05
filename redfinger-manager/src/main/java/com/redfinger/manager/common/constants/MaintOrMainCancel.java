package com.redfinger.manager.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class MaintOrMainCancel {
	@SuppressWarnings("unused")
	private static final String DICT_CODE = "rf_channel_version.maint_status";//对应数据库表SYS_DICT的DICT_CODE值
	
	
	/** 否*/
	public static final String MAINT_CANCEL = "0";
	/** 是 */
	public static final String MAINT = "1";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(MAINT, "维护中");
		DICT_MAP.put(MAINT_CANCEL, "取消维护");
   }
}
