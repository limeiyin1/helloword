package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameConditionUtils {
	private static final String DICT_CODE = "GAME_CONDITION";//对应数据库表SYS_DICT的DICT_CODE值
	/** 等级*/
	public static final String GRADE = "1";
	/** 充值 */
	public static final String RECHARGE = "2";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(GRADE, "等级");
		DICT_MAP.put(RECHARGE, "充值");
   }
}
