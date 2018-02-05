package com.redfinger.manager.common.utils;

public class FaultFeedOperateType {
	private static final String DICT_CODE = "FAULTFEED_OPERATE_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 后台操作*/
	@Deprecated
	public static final String MANAGER = "1";
	/** app操作*/
	public static final String APP = "2";
	/**定时器操作*/
	public static final String QUARTZ = "3";
	/**新增工单*/
	public static final String NEW_ADD = "4";
	/**客户端更换操作*/
	public static final String RENEWAL = "5";
}
