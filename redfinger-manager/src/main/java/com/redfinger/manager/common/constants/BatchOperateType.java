package com.redfinger.manager.common.constants;


public class BatchOperateType {
	private static final String DICT_CODE = "BATCH_OPERATE_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	
	/** 开启设备维护 */
	public static final String START_MAINT = "1";
	
	/** 取消设备维护  */
	public static final String STOP_MAINT = "2";
	
	/** 更换设备  */
	public static final String RENEWAL_PAD = "3";
	
	/** 发送用户公告  */
	public static final String USER_NOTICE = "4";
	
	/** 发送设备公告 */
	public static final String PAD_NOTICE = "5";
	
	/** 按用户赠送红豆 */
	public static final String USER_RBC = "6";
	
	/** 按设备赠送红豆 */
	public static final String PAD_RBC = "7";
	
	/** 赠送设备时间 */
	public static final String PAD_TIME = "8";
	
	/** 虚拟设备启用 */
	public static final String VM_START = "9";
	
	/** 虚拟设备禁用 */
	public static final String VM_DISABLE = "10";
	
	/** 开启虚拟设备授权 */
	public static final String VM_GRANT_OPEN = "11";
	
	/** 取消虚拟设备授权 */
	public static final String VM_GRANT_CANCEL = "12";
	
	/** 虚拟设备重启 */
	public static final String VM_REBOOT = "13";
	
	/** 虚拟设备回收 */
	public static final String VM_RECOVERY = "14";
	
	/** 虚拟设备重置 */
	public static final String VM_RESET = "15";
	
	/** 虚拟设备启动 */
	public static final String VM_BOOT = "16";
	
	/** 虚拟设备关闭 */
	public static final String VM_SHUTDOWN = "17";
	
	/** 重启remotePlay */
	public static final String REBOOT_REMOTEPLAY = "18";
	
	/** 物理设备重启 */
	public static final String DEVICE_REBOOT = "19";
	
	/** 打用户标签 */
	public static final String USER_LABEL = "20";
	
	/** 打设备标签 */
	public static final String PAD_LABEL = "21";
	
	/** 批量绑定设备 */
	public static final String PAD_BIND = "22";
	
	/** 批量启用设备 */
	public static final String ENABLE_PAD_YES = "23";
	/** 批量禁用设备 */
	public static final String ENABLE_PAD_NO = "24";
	
	/**发送设备消息**/
	public static final String PAD_MSG = "25";
	
}
