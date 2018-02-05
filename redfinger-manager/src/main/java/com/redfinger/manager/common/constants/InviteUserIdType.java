/**
 *
 * com.toone.web.goldfinger.commons.define  2015-9-14
 *
 */
package com.redfinger.manager.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * @ClassName: PadClassify 
 * @Description: 邀请用户类型
 * @author Carson ylrainbow82@gmail.com
 * @date 2015-9-14 下午6:08:10 
 *  
 */

public class InviteUserIdType {
	/**通过任务*/
	public static final String invite_by_task = "1";
	/**通过七夕活动*/
	public static final String invite_by_activity = "2";
	
	/**通过教师节活动*/
	public static final String invite_by_teachersday = "4";
	
	/**通过国庆节活动*/
	public static final String invite_by_nationalday = "5";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(invite_by_teachersday, "邀请购买活动");
		DICT_MAP.put(invite_by_nationalday, "邀请登录活动");
   }
}