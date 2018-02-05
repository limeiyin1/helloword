package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * @ClassName: TaskSource 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson yuchanghong@tydic.com 
 * @date 2015-5-10 下午10:48:39 
 *  
 */
public class TaskSource {
	private static final String DICT_CODE = "PAD_TASK_SOURCE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 管理系统截图来源*/
	public static final String SCREENCAP = "manage.screencap";
	/** 管理系统查看属性来源*/
	public static final String DEVICEINFO = "manage.device.info";
	/** 管理系统查看设备游戏来源*/
	public static final String GAMEINFO = "manage.game.info";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(SCREENCAP, "管理系统截图来源");
   }
}
