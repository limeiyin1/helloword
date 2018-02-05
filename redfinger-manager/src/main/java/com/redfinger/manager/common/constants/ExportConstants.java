package com.redfinger.manager.common.constants;

public class ExportConstants {

	/** 设备中心导出任务*/
	public static final String TYPE_PAD = "1";
	/** 实时监控导出任务*/
	public static final String TYPE_GMONITOR = "2";
	/** 待处理故障导出任务*/
	public static final String TYPE_FAULT_POOL = "3";
	/** 我的故障工单任务*/
	public static final String TYPE_FAULT_SELF = "4";
	/** 故障查看导出任务*/
	public static final String TYPE_FAULT_LOOK = "5";
	/** 虚拟设备任务清单导出*/
	public static final String TYPE_VM_TASK_HIS = "6";

	/** 任务未执行*/
	public static final String EXPORT_STATUS_MAKE = "0";
	/** 任务执行中*/
	public static final String EXPORT_STATUS_RUNNING = "1";
	/** 任务执行成功*/
	public static final String EXPORT_STATUS_SUCCED = "2";
	/** 任务执行失败*/
	public static final String EXPORT_STATUS_FAIL = "-1";
}
