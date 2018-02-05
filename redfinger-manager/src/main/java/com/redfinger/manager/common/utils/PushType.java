package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PushType {
	private static final String DICT_CODE = "PUSH_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 设备过期推送*/
	public static final String EXPIRE_PAD_PUSH = "1";
	/** 选中推送公告 */
	public static final String SELECT_NOTICE_PUSH = "2";
	/** 按条件推送公告 */
	public static final String PART_NOTICE_PUSH = "3";
	/** 向所有用户推送公告 */
	public static final String ALL_NOTICE_PUSH = "4";
	/** 按设备条件推送公告 */
	public static final String PAD_NOTICE_PUSH = "5";
	/** 按组推送公告 */
	public static final String GROUP_CAST_PUSH = "6";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(EXPIRE_PAD_PUSH, "设备过期推送");
		DICT_MAP.put(SELECT_NOTICE_PUSH, "选中推送公告");
		DICT_MAP.put(PART_NOTICE_PUSH, "按条件推送公告");
		DICT_MAP.put(ALL_NOTICE_PUSH, "向所有用户推送公告");
		DICT_MAP.put(PAD_NOTICE_PUSH, "按设备条件推送公告");
		DICT_MAP.put(GROUP_CAST_PUSH, "按用户组推送公告");
   }
}
