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
public class RefundHandleStatus {
	private static final String DICT_CODE = "REFUND_LOG_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 退回 */
	public static final String BACK = "-1";
	/** 取消 */
	public static final String CANCEL = "0";
	/** 申请退款 */
	public static final String USER_APPLY = "1";
	/** 退款受理 */
	public static final String APPLE_ACCEPT = "2";
	/** 审核待受理 */
	public static final String VERIFY = "3";
	/** 审核受理 */
	public static final String VERIFY_ACCEPT = "4";
	/** 退款 */
	public static final String REFUND = "5";
	/** 系统发起退款 */
	public static final String SYSTEM_APPLY = "6";
	/** 归档 */
	public static final String FINISH = "7";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
    
	static {
		DICT_MAP.put(CANCEL, "取消退款");
		DICT_MAP.put(USER_APPLY, "用户申请退款");
		DICT_MAP.put(APPLE_ACCEPT, "退款申请受理");
		DICT_MAP.put(VERIFY, "资料审核待受理");
		DICT_MAP.put(VERIFY_ACCEPT, "资料审核受理");
		DICT_MAP.put(REFUND, "已退款");
		DICT_MAP.put(BACK, "申请被退回");
		DICT_MAP.put(SYSTEM_APPLY, "系统发起退款");
		DICT_MAP.put(FINISH, "归档");
   }
}
