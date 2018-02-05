package com.redfinger.manager.common.constants;

public class CacheRedisConstant {
	
	/** 任务栏和发现栏开关模块*/
	public final static String DISCOVER_TASK_MODULE = "discover_task_module";
	/** 发现栏,发现栏开关在redis中的存活时间*/
	public final static int TASK_DISCOVER_REDIS_EXPIRE = 1800;
	
	/** 用户公告模块*/
	public final static String USERNOTICEPAGE_MODULE = "usernoticepage";
	/** 用户公告在redis中的存活时间*/
	public final static int USERNOTICEPAGE_REDIS_EXPIRE = 1800;

}
