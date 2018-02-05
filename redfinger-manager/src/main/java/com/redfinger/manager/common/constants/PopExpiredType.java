/**
 *
 * com.toone.web.goldfinger.commons.define  2015-9-5
 *
 */
package com.redfinger.manager.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * @ClassName: TaskStatus 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson ylrainbow82@gmail.com
 * @date 2015-9-5 上午10:27:55 
 *  
 */
public class PopExpiredType {
	private static final String DICT_CODE = "rf_notice.pop_expired";//对应数据库表SYS_DICT的DICT_CODE值
	/** 无效 */
	public static final String INVALID = "0";
	/** 有效 */
	public static final String VALID = "1";
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(INVALID, "无效");
		DICT_MAP.put(VALID, "有效");
   }
}
