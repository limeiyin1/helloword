package com.redfinger.manager.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class BatchOperateStatus {
	private static final String DICT_CODE = "BATCH_OPERATE_STATUS";//对应数据库表SYS_DICT的DICT_CODE值
	
	/** 待启用 */
	public static String WAIT_START = "1";
	/** 启用(发送jms) */
	public static String START = "2";
	/** 数据解析中 */
	public static String DATA_ANALYSING = "3";
	/** 数据解析失败 */
	public static String ANALYS_FAIL = "4";
	/** 待执行 */
	public static String WAIT_EXCEUTE = "5";
	/** 执行中 */
	public static String EXECUTING = "6";
	/** 执行成功 */
	public static String EXECUTE_SUCCESS = "7";
	/** 待执出错 */
	public static String EXECUTE_ERROR = "8";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(WAIT_START, "待启用");
		DICT_MAP.put(START, "启用");
		DICT_MAP.put(DATA_ANALYSING, "数据解析中");
		DICT_MAP.put(ANALYS_FAIL, "数据解析失败");
		DICT_MAP.put(WAIT_EXCEUTE, "待执行");
		DICT_MAP.put(EXECUTING, "正在执行");
		DICT_MAP.put(EXECUTE_SUCCESS, "执行成功");
		DICT_MAP.put(EXECUTE_ERROR, "执行出错");
   }
}
