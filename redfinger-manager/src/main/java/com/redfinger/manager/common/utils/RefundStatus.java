/**
 *
 * com.toone.web.goldfinger.commons.define  2015-9-5
 *
 */
package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * @ClassName: TaskStatus 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson ylrainbow82@gmail.com
 * @date 2015-9-5 上午10:27:55 
 *  
 */
public class RefundStatus {
	private static final String DICT_CODE = "REFUND_STATUS";//对应数据库表SYS_DICT的DICT_CODE值
	/** 申请被退回 */
	public static final String BACK = "-1";
	/** 取消 */
	public static final String CANCEL = "0";
	/** 用户申请退款 */
	public static final String USER_APPLY = "1";
	/** 受理 */
	public static final String ACCEPT = "2";
	/** 审核 */
	public static final String VERIFY = "3";
	/** 退款 */
	public static final String REFUND = "4";
	/** 系统发起退款 */
	public static final String SYSTEM_APPLY = "5";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
    
	static {
		DICT_MAP.put(CANCEL, "取消退款");
		DICT_MAP.put(USER_APPLY, "用户申请退款");
		DICT_MAP.put(ACCEPT, "退款受理");
		DICT_MAP.put(VERIFY, "资料审核中");
		DICT_MAP.put(REFUND, "已退款");
		DICT_MAP.put(BACK, "申请被退回");
		DICT_MAP.put(SYSTEM_APPLY, "系统发起退款");
   }
}
