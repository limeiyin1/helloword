package com.redfinger.manager.common.constants;
import com.redfinger.manager.common.helper.ConfigHelper;
public class ConfigConstantsDb {
/**
*红手指抽奖池基数，可以理解奖池中有多少个纸团
*/
public static String configAwardBase(){
return ConfigHelper.getValueByCode("config_award_base");
}

/**
*活动的开始时间
*/
public static String configBeginTime(){
return ConfigHelper.getValueByCode("config_begin_time");
}

/**
*渠道普通设备本时间段剩余可申请台数
*/
public static String configChannelBindPadCount(){
return ConfigHelper.getValueByCode("config_channel_bind_pad_count");
}

/**
*渠道普通设备申请可控天数
*/
public static String configChannelCommon(){
return ConfigHelper.getValueByCode("config_channel_common");
}

/**
*渠道普通设备每时间段可申请台数
*/
public static String configChannelMaxPadCount(){
return ConfigHelper.getValueByCode("config_channel_max_pad_count");
}

/**
*渠道设备到期赎回天数
*/
public static String configChannelRansom(){
return ConfigHelper.getValueByCode("config_channel_ransom");
}

/**
*物理机自动生成虚拟设备数量
*/
public static String configDeviceVm(){
return ConfigHelper.getValueByCode("config_device_vm");
}

/**
*物理机最大添加虚拟设备数
*/
public static String configDeviceVmMax(){
return ConfigHelper.getValueByCode("config_device_vm_max");
}

/**
*下载文件存放的本地地址
*/
public static String configDownloadLocal(){
return ConfigHelper.getValueByCode("config_download_local");
}

/**
*活动的结束时间
*/
public static String configEndTime(){
return ConfigHelper.getValueByCode("config_end_time");
}

/**
*null
*/
public static String configExpireBefore(){
return ConfigHelper.getValueByCode("config_expire_before");
}

/**
*设备到期提醒消息推送的开关
*/
public static String configExpireBeforeOnOff(){
return ConfigHelper.getValueByCode("config_expire_before_on_off");
}

/**
*null
*/
public static String configExpireDesc(){
return ConfigHelper.getValueByCode("config_expire_desc");
}

/**
*null
*/
public static String configExpireTime(){
return ConfigHelper.getValueByCode("config_expire_time");
}

/**
*null
*/
public static String configExpireTitle(){
return ConfigHelper.getValueByCode("config_expire_title");
}

/**
*广告播放时间xx/秒
*/
public static String configFacilityAdvertisementtime(){
return ConfigHelper.getValueByCode("config_facility_advertisementtime");
}

/**
*播放广告的存储路径
*/
public static String configFacilityAdvertisementurl(){
return ConfigHelper.getValueByCode("config_facility_advertisementurl");
}

/**
*普通设备可控天数
*/
public static String configFacilityCommon(){
return ConfigHelper.getValueByCode("config_facility_common");
}

/**
*普通设备每天可使用控制时间xX秒/每天
*/
public static String configFacilityCommoncontrol(){
return ConfigHelper.getValueByCode("config_facility_commoncontrol");
}

/**
*设备到期后用户赎回天数
*/
public static String configFacilityRansom(){
return ConfigHelper.getValueByCode("config_facility_ransom");
}

/**
*再次申请设备赠送天数
*/
public static String configFacilityReapply(){
return ConfigHelper.getValueByCode("config_facility_reapply");
}

/**
*设备每天刷新时间（24小时制）整点刷新
*/
public static String configFacilityRefresh(){
return ConfigHelper.getValueByCode("config_facility_refresh");
}

/**
*VIP设备每天可使用控制时间xx秒/每天
*/
public static String configFacilityVipcontrol(){
return ConfigHelper.getValueByCode("config_facility_vipcontrol");
}

/**
*null
*/
public static String configFaultClientCreater(){
return ConfigHelper.getValueByCode("config_fault_client_creater");
}

/**
*系统剩余可以绑定普通设备数
*/
public static String configLeftBindPadCount(){
return ConfigHelper.getValueByCode("config_left_bind_pad_count");
}

/**
*微信抽奖每天可抽次数
*/
public static String configLotteryCount(){
return ConfigHelper.getValueByCode("config_lottery_count");
}

/**
*系统最多可以绑定普通设备数 
*/
public static String configMaxBindPadCount(){
return ConfigHelper.getValueByCode("config_max_bind_pad_count");
}

/**
*会员可绑定普通设备数量
*/
public static String configMemberFacility(){
return ConfigHelper.getValueByCode("config_member_facility");
}

/**
*普通设备所需红豆设定数xx豆/每天
*/
public static String configMemberOrmosia(){
return ConfigHelper.getValueByCode("config_member_ormosia");
}

/**
*会员可绑定VIP设备数量
*/
public static String configMemberVipfacility(){
return ConfigHelper.getValueByCode("config_member_vipfacility");
}

/**
*VIP设备所需红豆设定xx豆/每天
*/
public static String configMemberVipormosia(){
return ConfigHelper.getValueByCode("config_member_vipormosia");
}

/**
*设备掉线重连开关，1 全部推送，2 只向VIP设备推送，0 关闭推送功能
*/
public static String configPadReconnect(){
return ConfigHelper.getValueByCode("config_pad_reconnect");
}

/**
*截图命令图片路径的前缀（长沙）
*/
public static String configPicturedownloadlocal(){
return ConfigHelper.getValueByCode("config_pictureDownloadLocal");
}

/**
*截图命令图片路径的前缀（深圳）
*/
public static String configPicturedownloadlocalSz(){
return ConfigHelper.getValueByCode("config_pictureDownloadLocal_sz");
}

/**
*体验设备可控时间，单位（分钟）
*/
public static String configProbationControlTime(){
return ConfigHelper.getValueByCode("config_probation_control_time");
}

/**
*体验设备池可申请体验设备总数
*/
public static String configProbationPad(){
return ConfigHelper.getValueByCode("config_probation_pad");
}

/**
*体验设备控制时间，单位（分钟）
*/
public static String configProbationPadTime(){
return ConfigHelper.getValueByCode("config_probation_pad_time");
}

/**
*设备离线提醒推送内容
*/
public static String configPushDetails(){
return ConfigHelper.getValueByCode("config_push_details");
}

/**
*离线提醒推送内容（有包名）
*/
public static String configPushDetailsPackage(){
return ConfigHelper.getValueByCode("config_push_details_package");
}

/**
*设备离线提醒推送标题
*/
public static String configPushHeadline(){
return ConfigHelper.getValueByCode("config_push_headline");
}

/**
*设备离线
*/
public static String configPushHeadlinePackage(){
return ConfigHelper.getValueByCode("config_push_headline_package");
}

/**
*设备离线提醒推送间隔时间
*/
public static String configPushInterval(){
return ConfigHelper.getValueByCode("config_push_interval");
}

/**
*全局的推送功能开关
*/
public static String configPushOnOff(){
return ConfigHelper.getValueByCode("config_push_on_off");
}

/**
*null
*/
public static String configPushPadDetails(){
return ConfigHelper.getValueByCode("config_push_pad_details");
}

/**
*null
*/
public static String configPushPadHeadline(){
return ConfigHelper.getValueByCode("config_push_pad_headline");
}

/**
*null
*/
public static String configPushPadOnOff(){
return ConfigHelper.getValueByCode("config_push_pad_on_off");
}

/**
*定时任务调度时间表达式
*/
public static String configQuartzCron(){
return ConfigHelper.getValueByCode("config_quartz_cron");
}

/**
*付费赠送红豆活动结束时间
*/
public static String configRbcEndTime(){
return ConfigHelper.getValueByCode("config_rbc_end_time");
}

/**
*付费赠送红豆活动开始时间
*/
public static String configRbcStartTime(){
return ConfigHelper.getValueByCode("config_rbc_start_time");
}

/**
*null
*/
public static String configRoleCeshi(){
return ConfigHelper.getValueByCode("config_role_ceshi");
}

/**
*null
*/
public static String configRoleKefu(){
return ConfigHelper.getValueByCode("config_role_kefu");
}

/**
*null
*/
public static String configRoleYunwei(){
return ConfigHelper.getValueByCode("config_role_yunwei");
}

/**
*null
*/
public static String configRoleZixun(){
return ConfigHelper.getValueByCode("config_role_zixun");
}

/**
*每天发送短信的数量限制
*/
public static String configSmsDay(){
return ConfigHelper.getValueByCode("config_sms_day");
}

/**
*每小时短信发送数量限制
*/
public static String configSmsHour(){
return ConfigHelper.getValueByCode("config_sms_hour");
}

/**
*每分钟短信发送数量限制
*/
public static String configSmsMinute(){
return ConfigHelper.getValueByCode("config_sms_minute");
}

/**
*启动器公告内容
*/
public static String configStarterDetails(){
return ConfigHelper.getValueByCode("config_starter_details");
}

/**
*启动器公告标题
*/
public static String configStarterTitle(){
return ConfigHelper.getValueByCode("config_starter_title");
}

/**
*同一台设备执行命令的间隔时间
*/
public static String configTaskTimeout(){
return ConfigHelper.getValueByCode("config_task_timeout");
}

/**
*体验设备可申请数
*/
public static String configUsableProbationPad(){
return ConfigHelper.getValueByCode("config_usable_probation_pad");
}

/**
*官网公告内容
*/
public static String configWebsiteDetails(){
return ConfigHelper.getValueByCode("config_website_details");
}

/**
*控制官网的公告功能开启
*/
public static String configWebsiteOnOff(){
return ConfigHelper.getValueByCode("config_website_on_off");
}

/**
*官网公告标题
*/
public static String configWebsiteTitle(){
return ConfigHelper.getValueByCode("config_website_title");
}

/**
*微信网页授权回传参数state的值
*/
public static String configWechartState(){
return ConfigHelper.getValueByCode("config_wechart_state");
}

/**
*微信客服自动回复
*/
public static String weixinFaultTips(){
return ConfigHelper.getValueByCode("weixin_fault_tips");
}

/**
*微信客服自动回复
*/
public static String weixinFaultTipsKey(){
return ConfigHelper.getValueByCode("weixin_fault_tips_key");
}

/**
*后台新增广播内容的字符长度限制
*/
public static String broadcastContentLength(){
return ConfigHelper.getValueByCode("config_broadcast_content_length");
}

}
