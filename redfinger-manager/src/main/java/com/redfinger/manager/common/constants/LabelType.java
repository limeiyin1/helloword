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
public class LabelType {
	private static final String DICT_CODE = "LABEL_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 用户标签 */
	public static final String USER_LABEL = "1";
	/** 设备标签 */
	public static final String PAD_LAEBL = "2";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(USER_LABEL, "用户标签");
		DICT_MAP.put(PAD_LAEBL, "设备标签");
    }
}
