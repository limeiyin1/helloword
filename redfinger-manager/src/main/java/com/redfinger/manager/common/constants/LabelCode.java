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
public class LabelCode {
	/** 用户标签-活动 */
	public static final String USER_ACTIVITY = "activity";
	/** 设备标签-任务 */
	public static final String USER_TASK = "task";
	/** 设备标签-游戏礼包 */
	public static final String USER_GAMEPACKAGE = "gamePackage";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(USER_ACTIVITY, "活动");
		DICT_MAP.put(USER_TASK, "任务");
		DICT_MAP.put(USER_GAMEPACKAGE, "游戏礼包");
    }
}
