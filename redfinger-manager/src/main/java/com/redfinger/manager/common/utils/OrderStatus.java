/*
 * com.toone.web.goldfinger.commons.enums  2015-5-10
 *
 * Copyright 2010 Shenzhen TYDIC Information Technology Co.,Ltd.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * @ClassName: GoodsType 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson yuchanghong@tydic.com 
 * @date 2015-5-10 下午10:48:39 
 *  
 */
public class OrderStatus {
	private static final String DICT_CODE = "ORDER_STATUS";//对应数据库表SYS_DICT的DICT_CODE值
	/** 等待付款 */
	public static final String WAIT_FOR_PAY = "0";
	/** 付款完成 */
	public static final String PAY_FINISH = "1";
	/** 订单关取消*/
	public static final String CANCLED = "2";
	/** 设备分配成功*/
	public static final String ASSIGN_SUCCESS = "3";
	/** 设备分配失败*/
	public static final String ASSIGN_FAIL = "4";
	/** 设备处理成功*/
	public static final String PAD_HANDL_SUCCESS = "5";
	/** 设备处理失败*/
	public static final String PAD_HANDL_FAIL = "6";
	/** 支付成功减免失效*/
	public static final String PAY_FINISH_DEDUCTION_FAILE = "7";
	/** 已退款*/
	public static final String ORDER_REFUNDS = "8";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(WAIT_FOR_PAY, "等待付款");
		DICT_MAP.put(PAY_FINISH, "付款完成");
		DICT_MAP.put(CANCLED, "订单关取消");
		DICT_MAP.put(ASSIGN_SUCCESS, "设备分配完成");
		DICT_MAP.put(ASSIGN_FAIL, "设备分配未分配");
		DICT_MAP.put(PAD_HANDL_SUCCESS, "设备分配完成");
		DICT_MAP.put(PAD_HANDL_FAIL, "设备分配未分配");
		DICT_MAP.put(PAY_FINISH_DEDUCTION_FAILE, "支付成功减免失效");
		DICT_MAP.put(ORDER_REFUNDS, "已退款");
   }
}
