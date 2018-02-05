package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CouponTypeUtils {
	private static final String DICT_CODE = "COUPON_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 优惠劵*/
	public static final String COUPON = "1";
	/** 折扣*/
	public static final String DISCOUNT = "2";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();

	static {
		DICT_MAP.put(COUPON, "优惠劵");
		DICT_MAP.put(DISCOUNT, "折扣");
	}
}
