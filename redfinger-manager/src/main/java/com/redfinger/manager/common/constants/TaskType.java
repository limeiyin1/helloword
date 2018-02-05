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
public class TaskType {
	private static final String DICT_CODE = "RF_TASK_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 每日任务 */
	public static final String EVERY_DAY = "everyday";
	/** 完善资料 */
	public static final String USER_DATA = "userdata";
	/** 问卷调查 */
	public static final String WJDC = "wjdc";
	/** 游戏下载 */
	public static final String GAME_DOWNLOAD = "gamedownload";
	/** 推广任务 */
	public static final String GANERALIZE = "ganeralize";
	/** 基本任务 */
	public static final String BASE = "base";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(EVERY_DAY, "每日任务");
		DICT_MAP.put(USER_DATA, "完善资料");
		DICT_MAP.put(WJDC, "问卷调查");
		DICT_MAP.put(GAME_DOWNLOAD, "游戏下载");
		DICT_MAP.put(GANERALIZE, "推广任务");
		DICT_MAP.put(BASE, "基本任务");
   }
}
