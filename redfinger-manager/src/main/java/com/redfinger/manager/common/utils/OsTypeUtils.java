package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class OsTypeUtils {
	
	private static final String DICT_CODE = "OS_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** win*/
	public static final String WIN = "win";
	/** android */
	public static final String ANDROID = "android";
	/** ios */
	public static final String IOS = "ios";
	/** win_st */
	public static final String WIN_ST = "win_st";
	/** starter */
	public static final String STARTER = "starter";
	/** startVM */
	public static final String STARTVM = "startVM";
	/** startVM */
	public static final String DESK = "vm_desk";
	
	/** marketApp */
	public static final String marketApp = "marketApp";
	/**试玩应用市场*/
	public static final String demoMarket = "demo_market";
	/** 补丁	 */
	public static final String MEND = "mend";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(WIN, "win");
		DICT_MAP.put(IOS, "ios");
		DICT_MAP.put(ANDROID, "android");
		DICT_MAP.put(WIN_ST, "win_st");
		DICT_MAP.put(STARTER, "starter");
		DICT_MAP.put(STARTVM, "startVM");
		DICT_MAP.put(marketApp, "marketApp");
		DICT_MAP.put(demoMarket, "demo_market");
		DICT_MAP.put(MEND, "mend");
		
   }

	public static Map<String, String> getOsType() {
		DICT_MAP.put(WIN, "win");
		DICT_MAP.put(ANDROID, "android");
		DICT_MAP.put(IOS, "ios");
		DICT_MAP.put(WIN_ST, "win_st");
		DICT_MAP.put(STARTER, "starter");
		DICT_MAP.put(STARTVM, "startVM");
		DICT_MAP.put(DESK, "vm_desk");
		DICT_MAP.put(marketApp, "marketApp");
		DICT_MAP.put(demoMarket, "demo_market");
		DICT_MAP.put(MEND, "mend");
		return DICT_MAP;
	}

}
