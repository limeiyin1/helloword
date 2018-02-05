package com.redfinger.manager.common.constants;


public class BatchDataCode {
	
	/** 开启设备维护和停止设备维护excel文件路径的datacode*/
	public static final String PAD_MAINT_FILE_PATH = "PAD_MAINT_FILE_PATH";
	
	
	/** 发送用户公告excle文件路径的datacode **/
	public static final String USER_NOTICE_FILE_PATH = "USER_NOTICE_FILE_PATH";
	/** 发送用户公告,公告内容的datacode **/
	public static final String USER_NOTICE_TITLE  = "USER_NOTICE_TITLE";
	/** 发送用户公告,公告内容的datacode **/
	public static final String USER_NOTICE_CONTENT = "USER_NOTICE_CONTENT";
	/** 发送用户公告,是否强制弹出的datacode **/
	public static final String USER_NOTICE_POPSTATUS = "USER_NOTICE_POPSTATUS";
	/** 发送用户公告,公告内容的datacode **/
	public static final String USER_NOTICE_POPEXPIRED  = "USER_NOTICE_POPEXPIRED";
	
	
	/** 发送设备公告excle文件路径的datacode **/
	public static final String PAD_NOTICE_FILE_PATH = "PAD_NOTICE_FILE_PATH";
	/** 发送设备发送方式**/
	public static final String PAD_NOTICE_SEND_TYPE = "PAD_NOTICE_SEND_TYPE";
	/** 发送设备公告机房id**/
	public static final String PAD_NOTICE_IDC_ID = "PAD_NOTICE_IDC_ID";
	/** 发送设备设备编码大于**/
	public static final String PAD_NOTICE_PADCODEGT = "PAD_NOTICE_PADCODEGT";
	/** 发送设备设备编码小于**/
	public static final String PAD_NOTICE_PADCODELT = "PAD_NOTICE_PADCODELT";
	/** 发送设备公告,公告内容的datacode **/
	public static final String PAD_NOTICE_TITLE  = "PAD_NOTICE_TITLE";
	/** 发送设备公告,公告内容的datacode **/
	public static final String PAD_NOTICE_CONTENT = "PAD_NOTICE_CONTENT";
	/** 发送设备公告,是否强制弹出的datacode **/
	public static final String PAD_NOTICE_POPSTATUS = "PAD_NOTICE_POPSTATUS";
	public static final String ONE_NOTICE_STATUS = "ONE_NOTICE_STATUS";
	
	
	/** 更换设备excle文件路径的datacode **/
	public static final String RENEWAL_PAD_FILE_PATH = "RENEWAL_PAD_FILE_PATH";
	/** 更换设备新设备前缀datacode **/
	public static final String RENEWAL_PAD_RENEWPADIDCID = "RENEWAL_PAD_RENEWPADIDCID";
	/** 更换设备新设备控制节点 **/
	public static final String RENEWAL_PAD_PADCONTROLID = "RENEWAL_PAD_PADCONTROLID";
	
	public static final String RENEWAL_PAD_PADCODEGT = "RENEWAL_PAD_PADCODEGT";
	public static final String RENEWAL_PAD_PADCODELT = "RENEWAL_PAD_PADCODELT";
	public static final String RENEWAL_PAD_DEVICEIPGT = "RENEWAL_PAD_DEVICEIPGT";
	public static final String RENEWAL_PAD_DEVICEIPLT = "RENEWAL_PAD_DEVICEIPLT";
	/** 更换设备是否发送消息datacode  **/
	public static final String RENEWAL_PAD_IS_MESSAGE = "RENEWAL_PAD_IS_MESSAGE";
	/** 更换设备发送消息的模板datacode **/
	public static final String RENEWAL_PAD_MESSAGE_TEMPLATE = "RENEWAL_PAD_MESSAGE_TEMPLATE";
	/** 更换设备是否发送微信datacode **/
	public static final String RENEWAL_PAD_IS_WECHART = "RENEWAL_PAD_IS_WECHART";
	/** 更换设备发送微信的模板datacode **/
	public static final String RENEWAL_PAD_WECHART_TEMPLATE = "RENEWAL_PAD_WECHART_TEMPLATE";
	
	
	/** 增加设备时间excle文件路径的datacode **/
	public static final String PAD_TIME_FILE_PATH = "PAD_TIME_FILE_PATH";
	/** 增加设备时间,时间单位的datacode **/
	public static final String PAD_TIME_TYPE  = "PAD_TIME_TYPE";
	/**增加时间数量**/
	public static final String PAD_TIME_AMOUNT  = "PAD_TIME_AMOUNT";
	
	
	/** 按用户修改红豆excle文件路径的datacode **/
	public static final String RBGET_FILE_PATH = "RBGET_FILE_PATH";
	public static final String RBGET_TYPE = "RBGET_TYPE";
	/** 修改红豆数量的datacode **/
	public static final String RBGET_AMOUNT  = "RBGET_AMOUNT";
	/**修改备注**/
	public static final String RBGET_REMARK  = "RBGET_REMARK";
	
	
	/** 按设备修改红豆excle文件路径的datacode **/
	public static final String RBC_BY_PAD_FILE_PATH = "RBC_BY_PAD_FILE_PATH";
	/**赠送方式**/
	public static final String RBC_BY_PAD_GIVETYPE = "RBC_BY_PAD_GIVETYPE";
	/**机房id**/
	public static final String RBC_BY_PAD_IDCID = "RBC_BY_PAD_IDCID";
	/**设备编码段大于**/
	public static final String RBC_BY_PAD_PADECODEGT = "RBC_BY_PAD_PADECODEGT";
	/**设备编码段小于**/
	public static final String RBC_BY_PAD_PADECODELT = "RBC_BY_PAD_PADECODELT";
	/**普通用户赠送数量**/
	public static final String RBC_BY_PAD_COMMOMRBC = "RBC_BY_PAD_COMMOMRBC";
	/***vip赠送数量**/
	public static final String RBC_BY_PAD_VIPRBC = "RBC_BY_PAD_VIPRBC";
	/**svip赠送数量***/
	public static final String RBC_BY_PAD_SVIPRBC = "RBC_BY_PAD_SVIPRBC";
	/**gvip赠送数量***/
	public static final String RBC_BY_PAD_GVIPRBC = "RBC_BY_PAD_GVIPRBC";
	
	
	/***批量绑定设备的excel文件**/
	public static final String BIND_PAD_FILE_PATH = "BIND_PAD_FILE_PATH";
	/**绑定的设备类型***/
	public static final String BIND_PAD_PADCLASSIFY = "BIND_PAD_PADCLASSIFY";
	/**绑定的时长***/
	public static final String BIND_GAMEPAD_TIME = "BIND_GAMEPAD_TIME";
	/**绑定的套餐***/
	public static final String BIND_PAD_GOODS = "BIND_PAD_GOODS";
	/**绑定的套餐***/
	public static final String BIND_PAD_GVIPGOODS = "BIND_PAD_GVIPGOODS";
	/**绑定的时长***/
	public static final String BIND_CLOUDPAD_TIME = "BIND_CLOUDPAD_TIME";
	
	
	
	
	/** 虚拟设备重启excel文件路径的datacode*/
	public static final String VM_PAD_RESTART_FILE_PATH = "VM_PAD_RESTART_FILE_PATH";
	
	/** 物理设备重启excel文件路径的datacode*/
	public static final String DEVICE_REBOOT_FILE_PATH = "DEVICE_REBOOT_FILE_PATH";
	
	/** 批处理——虚拟设备授权开放与取消excel文件路径的datacode*/
	public static final String VM_GRANT_FILE_PATH = "VM_GRANT_FILE_PATH";
	
	/** 启用或禁用excel文件路径的datacode*/
	public static final String ENABLE_PAD_FILE_PATH = "ENABLE_PAD_FILE_PATH";
	public static final String ENABLE_PAD_TYPE = "ENABLE_PAD_TYPE";
	public static final String ENABLE_PAD_IDC_ID = "ENABLE_PAD_IDC_ID";
	public static final String ENABLE_PAD_CONTROLL_ID = "ENABLE_PAD_CONTROLL_ID";
	public static final String ENABLE_PAD_STATUS = "ENABLE_PAD_STATUS";
	public static final String ENABLE_PAD_BIND_STATUS = "ENABLE_PAD_BIND_STATUS";
	public static final String ENABLE_PAD_FAULT_STATUS = "ENABLE_PAD_FAULT_STATUS";
	public static final String ENABLE_PAD_PADCODEGT = "ENABLE_PAD_PADCODEGT";
	public static final String ENABLE_PAD_PADCODELT = "ENABLE_PAD_PADCODELT";
	public static final String ENABLE_PAD_DEVICEIPGT = "ENABLE_PAD_DEVICEIPGT";
	public static final String ENABLE_PAD_DEVICEIPLT = "ENABLE_PAD_DEVICEIPLT";
	
	
	
	
	
	
	
	
	
	/** 发送设备消息excle文件路径的datacode **/
	public static final String PAD_MSG_FILE_PATH = "PAD_MSG_FILE_PATH";
	/** 发送设备消息发送方式**/
	public static final String PAD_MSG_SEND_TYPE = "PAD_MSG_SEND_TYPE";
	/** 发送设备消息机房id**/
	public static final String PAD_MSG_IDC_ID = "PAD_MSG_IDC_ID";
	/** 发送设备消息设备编码大于**/
	public static final String PAD_MSG_PADCODEGT = "PAD_MSG_PADCODEGT";
	/** 发送设备消息设备编码小于**/
	public static final String PAD_MSG_PADCODELT = "PAD_MSG_PADCODELT";
	/** 发送设备消息,公告内容的datacode **/
	public static final String PAD_MSG_TITLE  = "PAD_MSG_TITLE";
	/** 发送设备消息,公告内容的datacode **/
	public static final String PAD_MSG_CONTENT = "PAD_MSG_CONTENT";
	public static final String PAD_WEIXIN_CONTENT = "PAD_WEIXIN_CONTENT";
	public static final String ONE_MSG_STATUS = "ONE_MSG_STATUS";
	
}

