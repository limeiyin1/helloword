package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class AwardType {
	private static final String DICT_CODE = "AWARD_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 红豆*/
	public static final String RBC = "1";
	/** 积分 */
	public static final String SCORE = "2";
	/** 激活码 */
	public static final String ACTIVATION_CODE = "3";
	/** 优惠券 */
	public static final String COUPON = "4";
	/** 抽奖次数 */
	public static final String LOTTERY = "5";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(RBC, "红豆");
		DICT_MAP.put(SCORE, "积分");
		DICT_MAP.put(ACTIVATION_CODE, "激活码");
		DICT_MAP.put(COUPON, "优惠券");
		DICT_MAP.put(LOTTERY, "抽奖次数");
   }
}
