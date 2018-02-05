package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class RfCustomGroup {
	public static final String one = "1";
	public static final String two = "2";
	public static final String three = "3";
	public static final String four = "4";
	public static final String five = "5";
	public static final String six = "6";
	public static final String seven = "7";
	public static final String eight = "8";
	/** 键值对象 */
	public static Map<String,String> CUSTOM_GROUP_MAP = new LinkedHashMap<String, String>();
	static {
		CUSTOM_GROUP_MAP.put(one,"甲组(早中晚班)");
		CUSTOM_GROUP_MAP.put(two,"乙组(早中晚班)");
		CUSTOM_GROUP_MAP.put(three,"丙组(早中晚班)");
		CUSTOM_GROUP_MAP.put(four,"丁组(早中晚班)");
		CUSTOM_GROUP_MAP.put(five,"甲组(夜班)");
		CUSTOM_GROUP_MAP.put(six,"乙组(夜班)");
		CUSTOM_GROUP_MAP.put(seven,"丙组(夜班)");
		CUSTOM_GROUP_MAP.put(eight,"丁组(夜班)");
   }
}
