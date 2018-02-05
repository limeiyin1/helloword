package com.redfinger.manager.common.constants;

import com.redfinger.manager.common.helper.DictHelper;

public class ConstantsDb {
	/**
*
*/
	public static String agentGameIsPetYes() {
		return DictHelper.getDictValueByKey("agent_game.is_pet@yes");
	}

	/**
*
*/
	public static String agentGameIsPetNo() {
		return DictHelper.getDictValueByKey("agent.game_is_pet@no");
	}

	/**
*
*/
	public static String agentNoticeCheckStatusOk() {
		return DictHelper.getDictValueByKey("agent_notice.check_status@ok");
	}

	/**
*
*/
	public static String agentNoticeCheckStatusNo() {
		return DictHelper.getDictValueByKey("agent_notice.check_status@no");
	}

	/**
	 * 终端类型，android
	 */
	public static String globalClientCategoryAndroid() {
		return DictHelper.getDictValueByKey("global.client_category@android");
	}

	/**
	 * 终端类型，ios
	 */
	public static String globalClientCategoryIos() {
		return DictHelper.getDictValueByKey("global.client_category@ios");
	}

	/**
	 * 终端类型，PC
	 */
	public static String globalClientCategoryPc() {
		return DictHelper.getDictValueByKey("global.client_category@pc");
	}

	/**
	 * 所有正常状态
	 */
	public static String globalEnableStatusNomarl() {
		return DictHelper.getDictValueByKey("global.enable_status@nomarl");
	}

	/**
	 * 所有停用状态
	 */
	public static String globalEnableStatusStop() {
		return DictHelper.getDictValueByKey("global.enable_status@stop");
	}

	/**
	 * 性别,男
	 */
	public static String globalGenderMen() {
		return DictHelper.getDictValueByKey("global.gender@men");
	}

	/**
	 * 性别，女
	 */
	public static String globalGenderWomen() {
		return DictHelper.getDictValueByKey("global.gender@women");
	}

	/**
	 * 手机号所属运营商，移动
	 */
	public static String globalMobileCarriersMobile() {
		return DictHelper.getDictValueByKey("global.mobile_carriers@mobile");
	}

	/**
	 * 手机号码所属运营商，联通
	 */
	public static String globalMobileCarriersUnicom() {
		return DictHelper.getDictValueByKey("global.mobile_carriers@unicom");
	}

	/**
	 * 手机号码所属运营商，电信
	 */
	public static String globalMobileCarriersTelecom() {
		return DictHelper.getDictValueByKey("global.mobile_carriers@telecom");
	}

	/**
	 * 所有未删除数据
	 */
	public static String globalStatusNomarl() {
		return DictHelper.getDictValueByKey("global.status@nomarl");
	}

	/**
	 * 所有逻辑删除数据
	 */
	public static String globalStatusDelete() {
		return DictHelper.getDictValueByKey("global.status@delete");
	}

	/**
	 * 管理员日志，结果状态：成功
	 */
	public static String logAdminResultStatusSuccess() {
		return DictHelper.getDictValueByKey("log_admin.result_status@success");
	}

	/**
	 * 管理员日志，结果状态，失败
	 */
	public static String logAdminResultStatusDefeated() {
		return DictHelper.getDictValueByKey("log_admin.result_status@defeated");
	}

	/**
	 * 设备日志，操作类型，解绑记录
	 */
	public static String logPadCategoryRelieve() {
		return DictHelper.getDictValueByKey("log_pad.category@relieve");
	}

	/**
	 * 设备日志，操作类型，绑定记录
	 */
	public static String logPadCategoryBind() {
		return DictHelper.getDictValueByKey("log_pad.category@bind");
	}

	/**
	 * 设备日志，操作类型，更换记录（移除）
	 */
	public static String logPadCategoryRelievechange() {
		return DictHelper.getDictValueByKey("log_pad.category@relievechange");
	}

	/**
	 * 设备日志，操作类型，更换记录（新增）
	 */
	public static String logPadCategoryBindchange() {
		return DictHelper.getDictValueByKey("log_pad.category@bindchange");
	}

	/**
	 * 设备日志，操作类型，新增设备记录
	 */
	public static String logPadCategoryAdd() {
		return DictHelper.getDictValueByKey("log_pad.category@add");
	}

	/**
	 * 设备日志，操作类型，设备停用记录
	 */
	public static String logPadCategoryStop() {
		return DictHelper.getDictValueByKey("log_pad.category@stop");
	}

	/**
	 * 设备日志，操作类型，设备停用记录
	 */
	public static String logPadCategoryStart() {
		return DictHelper.getDictValueByKey("log_pad.category@start");
	}

	/**
	 * 设备日志，操作类型，设备删除记录（物理删除）
	 */
	public static String logPadCategoryRemove() {
		return DictHelper.getDictValueByKey("log_pad.category@remove");
	}

	/**
	 * 设备日志，操作类型，设备换新记录
	 */
	public static String logPadCategoryChange() {
		return DictHelper.getDictValueByKey("log_pad.category@change");
	}

	/**
	 * 设备日志，操作类型，管理员绑定
	 */
	public static String logPadCategoryAdminbinding() {
		return DictHelper.getDictValueByKey("log_pad.category@adminbinding");
	}

	/**
	 * 设备日志，操作类型，管理员解绑
	 */
	public static String logPadCategoryAdminrelieve() {
		return DictHelper.getDictValueByKey("log_pad.category@adminrelieve");
	}

	/**
	 * 设备日志，操作类型，VIP设备绑定
	 */
	public static String logPadCategoryVipbind() {
		return DictHelper.getDictValueByKey("log_pad.category@VIPbind");
	}

	/**
	 * 设备日志，操作类型，VIP设备解绑
	 */
	public static String logPadCategoryViprelieve() {
		return DictHelper.getDictValueByKey("log_pad.category@VIPrelieve");
	}
	
	/**
	 * 设备日志，操作类型，SVIP设备解绑
	 */
	public static String logPadCategorySviprelieve() {
		return DictHelper.getDictValueByKey("log_pad.category@SVIPrelieve");
	}
	
	/**
	 * 设备日志，操作类型，GVIP设备绑定
	 */
	public static String logPadCategoryGVipbind() {
		return DictHelper.getDictValueByKey("log_pad.category@GVIPbind");
	}

	/**
	 * 设备日志，操作类型，GVIP设备解绑
	 */
	public static String logPadCategoryGViprelieve() {
		return DictHelper.getDictValueByKey("log_pad.category@GVIPrelieve");
	}

	/**
	 * 设备日志，操作类型，VIP升级
	 */
	public static String logPadCategoryUpvip() {
		return DictHelper.getDictValueByKey("log_pad.category@upvip");
	}

	/**
	 * 设备日志，操作类型，设备到期后，帐号剩余普通设备控制天数自动转至一台普通设备上绑定给用户
	 */
	public static String logPadCategoryDegrade() {
		return DictHelper.getDictValueByKey("log_pad.category@degrade");
	}

	/**
	 * 虚拟机应用白名单，未审核
	 */
	public static String rfAppWhiteListAuditStatusNo() {
		return DictHelper
				.getDictValueByKey("rf_app_white_list.audit_status@no");
	}

	/**
	 * 虚拟机应用白名单，审核通过
	 */
	public static String rfAppWhiteListAuditStatusYes() {
		return DictHelper
				.getDictValueByKey("rf_app_white_list.audit_status@yes");
	}

	/**
	 * 虚拟机应用白名单，审核不通过
	 */
	public static String rfAppWhiteListAuditStatusNopass() {
		return DictHelper
				.getDictValueByKey("rf_app_white_list.audit_status@nopass");
	}

	/**
	 * 红手指抽奖，奖品类型：VIP年卡设备
	 */
	public static String rfAwardBatchAwardCategoryYeardevice() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_category@yearDevice");
	}

	/**
	 * 红手指抽奖，奖品类型：VIP季卡设备
	 */
	public static String rfAwardBatchAwardCategorySeasondevice() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_category@seasonDevice");
	}

	/**
	 * 红手指抽奖，奖品类型：VIP月卡设备
	 */
	public static String rfAwardBatchAwardCategoryMonthdevice() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_category@monthDevice");
	}

	/**
	 * 红手指抽奖，奖品类型：实物
	 */
	public static String rfAwardBatchAwardCategoryCommodity() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_category@commodity");
	}

	/**
	 * 红手指抽奖，奖品类型：红豆
	 */
	public static String rfAwardBatchAwardCategoryRbc() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_category@rbc");
	}

	/**
	 * 红手指抽奖，奖品发放状态：未抽中
	 */
	public static String rfAwardBatchAwardGiveStatusExist() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_give_status@exist");
	}

	/**
	 * 红手指抽奖，奖品发放状态：已抽走
	 */
	public static String rfAwardBatchAwardGiveStatusSent() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_give_status@sent");
	}

	/**
	 * 红手指抽奖，奖品级别：安慰奖
	 */
	public static String rfAwardBatchAwardGradeConsolationprize() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_grade@consolationPrize");
	}

	/**
	 * 红手指抽奖，奖品级别：一等奖
	 */
	public static String rfAwardBatchAwardGradeFirstprize() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_grade@firstPrize");
	}

	/**
	 * 红手指抽奖，奖品级别：二等奖
	 */
	public static String rfAwardBatchAwardGradeSecondprize() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_grade@secondPrize");
	}

	/**
	 * 红手指抽奖，奖品级别：三等奖
	 */
	public static String rfAwardBatchAwardGradeThirdprize() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_grade@thirdPrize");
	}

	/**
	 * 红手指抽奖，奖品级别：四等级
	 */
	public static String rfAwardBatchAwardGradeFourthprize() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_grade@fourthPrize");
	}

	/**
	 * 红手指抽奖，奖品级别：五等奖
	 */
	public static String rfAwardBatchAwardGradeFifthprize() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_grade@fifthPrize");
	}

	/**
	 * 红手指抽奖，奖品级别：六等奖
	 */
	public static String rfAwardBatchAwardGradeSixthprize() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_grade@sixthPrize");
	}

	/**
	 * 红手指抽奖，奖品级别：七等奖
	 */
	public static String rfAwardBatchAwardGradeSeventhprize() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_grade@seventhPrize");
	}

	/**
	 * 红手指抽奖，奖品领取状态：未领取
	 */
	public static String rfAwardBatchAwardReceiveStatusUncollected() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_receive_status@uncollected");
	}

	/**
	 * 红手指抽奖，奖品领取状态：已领取
	 */
	public static String rfAwardBatchAwardReceiveStatusReceive() {
		return DictHelper
				.getDictValueByKey("rf_award_batch.award_receive_status@receive");
	}

	/**
	 * 类型，故障类型
	 */
	public static String rfClassClassTypeFault() {
		return DictHelper.getDictValueByKey("rf_class.class_type@fault");
	}

	/**
	 * 类型，故障修复类型
	 */
	public static String rfClassClassTypeFaultfix() {
		return DictHelper.getDictValueByKey("rf_class.class_type@faultfix");
	}

	/**
	 * 类型，游戏类型
	 */
	public static String rfClassClassTypeGame() {
		return DictHelper.getDictValueByKey("rf_class.class_type@game");
	}

	/**
	 * 类型，游戏名分类
	 */
	public static String rfClassClassTypeGamename() {
		return DictHelper.getDictValueByKey("rf_class.class_type@gamename");
	}

	/**
	 * 类型，工具分类（启动器中的工具分类）
	 */
	public static String rfClassClassTypeTools() {
		return DictHelper.getDictValueByKey("rf_class.class_type@tools");
	}

	/**
	 * 类型，APP故障申请中的故障类型
	 */
	public static String rfClassClassTypeIssue() {
		return DictHelper.getDictValueByKey("rf_class.class_type@issue");
	}

	/**
	 * 控制节点，节点类型，用户链路
	 */
	public static String rfControlControlTypeUser() {
		return DictHelper.getDictValueByKey("rf_control.control_type@user");
	}

	/**
	 * 控制节点，节点类型，设备控制节点
	 */
	public static String rfControlControlTypeDevice() {
		return DictHelper.getDictValueByKey("rf_control.control_type@device");
	}

	/**
	 * 控制节点，节点类型，设备管理控制节点
	 */
	public static String rfControlControlTypeManage() {
		return DictHelper.getDictValueByKey("rf_control.control_type@manage");
	}

	/**
	 * 物理设备状态，在线
	 */
	public static String rfDeviceDeviceStatusOnline() {
		return DictHelper.getDictValueByKey("rf_device.device_status@online");
	}

	/**
	 * 物理设备状态，离线
	 */
	public static String rfDeviceDeviceStatusOffline() {
		return DictHelper.getDictValueByKey("rf_device.device_status@offline");
	}

	/**
	 * 故障反馈，故障来源，PC
	 */
	public static String rfFaultFeedbackFeedbackSourcePc() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_source@pc");
	}

	/**
	 * 故障反馈，故障来源，android
	 */
	public static String rfFaultFeedbackFeedbackSourceAndroid() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_source@android");
	}

	/**
	 * 故障反馈，故障来源，QQ
	 */
	public static String rfFaultFeedbackFeedbackSourceQq() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_source@qq");
	}

	/**
	 * 故障反馈，故障来源，电话
	 */
	public static String rfFaultFeedbackFeedbackSourcePhone() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_source@phone");
	}

	/**
	 * null
	 */
	public static String rfFaultFeedbackFeedbackSourceSystem() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_source@system");
	}

	/**
	 * 故障反馈，故障处理状态，新工单
	 */
	public static String rfFaultFeedbackFeedbackStatusNew() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_status@new");
	}

	/**
	 * 故障反馈，故障处理状态，处理中
	 */
	public static String rfFaultFeedbackFeedbackStatusProcessing() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_status@processing");
	}

	/**
	 * 故障反馈，故障处理状态，移交客服
	 */
	public static String rfFaultFeedbackFeedbackStatusMovekefu() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_status@movekefu");
	}

	/**
	 * 故障反馈，故障处理状态，移交测试
	 */
	public static String rfFaultFeedbackFeedbackStatusMoveceshi() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_status@moveceshi");
	}

	/**
	 * 故障反馈，故障处理状态，移交运维
	 */
	public static String rfFaultFeedbackFeedbackStatusMoveyunwei() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_status@moveyunwei");
	}

	/**
	 * 故障反馈，故障处理状态，已处理
	 */
	public static String rfFaultFeedbackFeedbackStatusHandle() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_status@handle");
	}

	/**
	 * 故障反馈，故障处理状态，完结
	 */
	public static String rfFaultFeedbackFeedbackStatusEnd() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.feedback_status@end");
	}

	/**
	 * 故障反馈处理，是否解决，解决
	 */
	public static String rfFaultFeedbackIsSolveSolve() {
		return DictHelper.getDictValueByKey("rf_fault_feedback.is_solve@solve");
	}

	/**
	 * 故障反馈处理，是否解决，未解决
	 */
	public static String rfFaultFeedbackIsSolveNosolve() {
		return DictHelper
				.getDictValueByKey("rf_fault_feedback.is_solve@nosolve");
	}

	/**
	 * 游戏监控实时数据，游戏状态，离线
	 */
	public static String rfGmonitorGameGameStatusOffline() {
		return DictHelper
				.getDictValueByKey("rf_gmonitor_game.game_status@offline");
	}

	/**
	 * 游戏监控实时数据，游戏状态，在线
	 */
	public static String rfGmonitorGameGameStatusOnline() {
		return DictHelper
				.getDictValueByKey("rf_gmonitor_game.game_status@online");
	}

	/**
	 * 公告，弹出状态，自动弹出
	 */
	public static String rfNoticePopStatusForced() {
		return DictHelper.getDictValueByKey("rf_notice.pop_status@forced");
	}

	/**
	 * 公告，弹出状态，不自动弹出
	 */
	public static String rfNoticePopStatusNo() {
		return DictHelper.getDictValueByKey("rf_notice.pop_status@no");
	}

	/**
	 * 订单信息，订单业务类型，购买
	 */
	public static String rfOrderOrderBizTypePurchase() {
		return DictHelper.getDictValueByKey("rf_order.order_biz_type@purchase");
	}

	/**
	 * 订单信息，订单业务类型，充值
	 */
	public static String rfOrderOrderBizTypeCharge() {
		return DictHelper.getDictValueByKey("rf_order.order_biz_type@charge");
	}

	/**
	 * 订单信息，订单状态，等待买家支付
	 */
	public static String rfOrderOrderStatusNopayment() {
		return DictHelper.getDictValueByKey("rf_order.order_status@nopayment");
	}

	/**
	 * 订单信息，订单状态，支付完成
	 */
	public static String rfOrderOrderStatusPayment() {
		return DictHelper.getDictValueByKey("rf_order.order_status@payment");
	}

	/**
	 * 订单信息，订单状态，订单取消
	 */
	public static String rfOrderOrderStatusAbolish() {
		return DictHelper.getDictValueByKey("rf_order.order_status@abolish");
	}

	/**
	 * 订单信息，订单状态，购买分配成功
	 */
	public static String rfOrderOrderStatusAssignyes() {
		return DictHelper.getDictValueByKey("rf_order.order_status@assignyes");
	}

	/**
	 * 订单信息，订单状态，购买分配失败
	 */
	public static String rfOrderOrderStatusAssignno() {
		return DictHelper.getDictValueByKey("rf_order.order_status@assignno");
	}

	/**
	 * 订单类型，订单状态，充值处理成功
	 */
	public static String rfOrderOrderStatusHandleyes() {
		return DictHelper.getDictValueByKey("rf_order.order_status@handleyes");
	}

	/**
	 * 订单类型，订单状态，充值处理失败
	 */
	public static String rfOrderOrderStatusHandleno() {
		return DictHelper.getDictValueByKey("rf_order.order_status@handleno");
	}

	/**
	 * 订单信息，订单类型，付款
	 */
	public static String rfOrderOrderTypePayment() {
		return DictHelper.getDictValueByKey("rf_order.order_type@payment");
	}

	/**
	 * 订单信息，订单类型，退款
	 */
	public static String rfOrderOrderTypeReimburse() {
		return DictHelper.getDictValueByKey("rf_order.order_type@reimburse");
	}

	/**
	 * 订单信息，支付方式，支付宝
	 */
	public static String rfOrderPayModeCodeAlipay() {
		return DictHelper.getDictValueByKey("rf_order.pay_mode_code@alipay");
	}

	/**
	 * 订单信息，支付方式，快钱
	 */
	public static String rfOrderPayModeCode99bill() {
		return DictHelper.getDictValueByKey("rf_order.pay_mode_code@99bill");
	}

	/**
	 * 设备状态，设备冻结状态，冻结
	 */
	public static String rfPadActiveStatusFreeze() {
		return DictHelper.getDictValueByKey("rf_pad.active_status@freeze");
	}

	/**
	 * 设备状态，设备冻结状态，活跃的
	 */
	public static String rfPadActiveStatusActive() {
		return DictHelper.getDictValueByKey("rf_pad.active_status@active");
	}

	/**
	 * 设备，绑定状态，绑定
	 */
	public static String rfPadBindStatusBind() {
		return DictHelper.getDictValueByKey("rf_pad.bind_status@bind");
	}

	/**
	 * 设备，绑定状态，未绑定
	 */
	public static String rfPadBindStatusNobind() {
		return DictHelper.getDictValueByKey("rf_pad.bind_status@nobind");
	}

	/**
	 * 设备启用状态，启用状态类型，人工禁用
	 */
	public static String rfPadEnableStatusForbidden() {
		return DictHelper.getDictValueByKey("rf_pad.enable_status@forbidden");
	}

	/**
	 * 设备启用状态，启用状态，启用
	 */
	public static String rfPadEnableStatusStart() {
		return DictHelper.getDictValueByKey("rf_pad.enable_status@start");
	}

	/**
	 * 设备启用状态，启用状态类型，解绑禁用
	 */
	public static String rfPadEnableStatusRelieve() {
		return DictHelper.getDictValueByKey("rf_pad.enable_status@relieve");
	}

	/**
	 * 设备启用状态，启用状态类型，更换禁用
	 */
	public static String rfPadEnableStatusRenewal() {
		return DictHelper.getDictValueByKey("rf_pad.enable_status@renewal");
	}

	/**
	 * 设备启用状态，启用状态类型，体验设备解绑禁用
	 */
	public static String rfPadEnableStatusTaste() {
		return DictHelper.getDictValueByKey("rf_pad.enable_status@taste");
	}

	/**
	 * 设备，故障状态，无故障，正常
	 */
	public static String rfPadFaultStatusNomarl() {
		return DictHelper.getDictValueByKey("rf_pad.fault_status@nomarl");
	}

	/**
	 * 设备，故障状态，故障
	 */
	public static String rfPadFaultStatusFault() {
		return DictHelper.getDictValueByKey("rf_pad.fault_status@fault");
	}

	/**
	 * 虚拟设备，授权启动状态，授权（可开放给用户申请使用）
	 */
	public static String rfPadGrantOpenStatusOn() {
		return DictHelper.getDictValueByKey("rf_pad.grant_open_status@on");
	}

	/**
	 * 虚拟设备，授权启动状态，取消授权（不能开放给用户申请）
	 */
	public static String rfPadGrantOpenStatusOff() {
		return DictHelper.getDictValueByKey("rf_pad.grant_open_status@off");
	}

	/**
	 * 设备维护状态，正常
	 */
	public static String rfPadMaintStatusOff() {
		return DictHelper.getDictValueByKey("rf_pad.maint_status@off");
	}

	/**
	 * 设备状态，用户设备状态，维护
	 */
	public static String rfPadMaintStatusOn() {
		return DictHelper.getDictValueByKey("rf_pad.maint_status@on");
	}

	/**
	 * 设备，设备状态，在线
	 */
	public static String rfPadPadStatusOnline() {
		return DictHelper.getDictValueByKey("rf_pad.pad_status@online");
	}

	/**
	 * 设备，设备状态，离线
	 */
	public static String rfPadPadStatusOffline() {
		return DictHelper.getDictValueByKey("rf_pad.pad_status@offline");
	}

	/**
	 * 设备，设备状态，受控
	 */
	public static String rfPadPadStatusControl() {
		return DictHelper.getDictValueByKey("rf_pad.pad_status@control");
	}

	/**
	 * 设备状态，用户设备状态，在线
	 */
	public static String rfPadUserPadStatusOnline() {
		return DictHelper.getDictValueByKey("rf_pad.user_pad_status@online");
	}

	/**
	 * 设备，用户设备状态，离线
	 */
	public static String rfPadUserPadStatusOffline() {
		return DictHelper.getDictValueByKey("rf_pad.user_pad_status@offline");
	}

	/**
	 * 设备虚拟状态，虚拟设备在线
	 */
	public static String rfPadVmStatusOnline() {
		return DictHelper.getDictValueByKey("rf_pad.vm_status@online");
	}

	/**
	 * 设备虚拟状态，虚拟设备离线
	 */
	public static String rfPadVmStatusOffline() {
		return DictHelper.getDictValueByKey("rf_pad.vm_status@offline");
	}
	
	/**
	 * 设备类型，主营设备
	 */
	public static String rfPadPadClassifyMajor() {
		return DictHelper.getDictValueByKey("rf_pad.pad_classify@major");
	}
	
	/**
	 * 设备类型，主营设备
	 */
	public static String rfPadPadClassifyGame() {
		return DictHelper.getDictValueByKey("rf_pad.pad_classify@game");
	}
	
	/**
	 * 设备类型，主营设备
	 */
	public static String rfPadPadClassifyGvip() {
		return DictHelper.getDictValueByKey("rf_pad.pad_classify@gvip");
	}
	
	/**
	 * 设备类型，主营设备
	 */
	public static String rfPadPadClassifySvip() {
		return DictHelper.getDictValueByKey("rf_pad.pad_classify@svip");
	}

	/**
	 * 任务状态，开始
	 */
	public static String rfPadTaskTaskStatusStart() {
		return DictHelper.getDictValueByKey("rf_pad_task.task_status@start");
	}

	/**
	 * 任务状态，进行
	 */
	public static String rfPadTaskTaskStatusUnderway() {
		return DictHelper.getDictValueByKey("rf_pad_task.task_status@underway");
	}

	/**
	 * 任务状态，结束
	 */
	public static String rfPadTaskTaskStatusEnd() {
		return DictHelper.getDictValueByKey("rf_pad_task.task_status@end");
	}

	/**
	 * 设备命令操作批次，检查APP安装
	 */
	public static String rfPadtaskBatchCheckappinstall() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@checkappinstall");
	}

	/**
	 * 设备命令操作批次，检查APP运行
	 */
	public static String rfPadtaskBatchCheckapprun() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@checkapprun");
	}

	/**
	 * 设备命令操作批次，检查APP版本号
	 */
	public static String rfPadtaskBatchCheckappversion() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@checkappversion");
	}

	/**
	 * 设备命令操作批次，安装APP
	 */
	public static String rfPadtaskBatchInstall() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@install");
	}

	/**
	 * 设备命令操作批次，卸载APP
	 */
	public static String rfPadtaskBatchUninstall() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@uninstall");
	}

	/**
	 * 设备命令操作批次，重启设备
	 */
	public static String rfPadtaskBatchReboot() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@reboot");
	}

	/**
	 * 设备命令操作批次，截屏
	 */
	public static String rfPadtaskBatchScreencap() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@screencap");
	}

	/**
	 * 设备命令操作批次，设置时间
	 */
	public static String rfPadtaskBatchSettime() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@settime");
	}

	/**
	 * 设备命令操作批次，设备重置
	 */
	public static String rfPadtaskBatchReset() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@reset");
	}

	/**
	 * 设备命令操作批次，PING命令
	 */
	public static String rfPadtaskBatchPadping() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@padping");
	}

	/**
	 * 设备命令操作批次新版重启设备
	 */
	public static String rfPadtaskBatchRestart() {
		return DictHelper.getDictValueByKey("rf_padtask.batch@restart");
	}

	/**
	 * 支付交易日志，支付方式，快钱
	 */
	public static String rfPayTradeLogPayModeCode99bill() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.pay_mode_code@99bill");
	}

	/**
	 * 支付交易日志，支付方式 ：支付宝 — android端
	 */
	public static String rfPayTradeLogPayModeCodeAlipay() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.pay_mode_code@ALIPAY");
	}

	/**
	 * 支付交易日志，支付方式 ：支付宝 — PC 端
	 */
	public static String rfPayTradeLogPayModeCodeAlipayPc() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.pay_mode_code@ALIPAY_PC");
	}

	/**
	 * 支付交易日志，支付方式 ： 微信支付 — android端
	 */
	public static String rfPayTradeLogPayModeCodeTenpay() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.pay_mode_code@TENPAY");
	}

	/**
	 * 支付交易日志，支付方式 ：微信支付 — pc端
	 */
	public static String rfPayTradeLogPayModeCodeTenpayPc() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.pay_mode_code@TENPAY_PC");
	}

	/**
	 * 支付交易日志，支付方式 ：银联支付 — android端
	 */
	public static String rfPayTradeLogPayModeCodeUnionpayPay() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.pay_mode_code@UNIONPAY_PAY");
	}

	/**
	 * 支付交易日志，支付方式 ：银联支付 — pc端
	 */
	public static String rfPayTradeLogPayModeCodeUnionpayPayPc() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.pay_mode_code@UNIONPAY_PAY_PC");
	}

	/**
	 * 支付交易日志，交易状态，成功
	 */
	public static String rfPayTradeLogTradeStatusSuccess() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.trade_status@success");
	}

	/**
	 * 支付交易日志，交易状态，失败
	 */
	public static String rfPayTradeLogTradeStatusFaild() {
		return DictHelper
				.getDictValueByKey("rf_pay_trade_log.trade_status@faild");
	}

	/**
	 * 红豆日志，操作类型，登录设备
	 */
	public static String rfRbcLogLogTypeLoginpad() {
		return DictHelper.getDictValueByKey("rf_rbc_log.log_type@loginpad");
	}

	/**
	 * 红豆日志，操作类型，转化时间
	 */
	public static String rfRbcLogLogTypeTime() {
		return DictHelper.getDictValueByKey("rf_rbc_log.log_type@time");
	}

	/**
	 * 红豆日志，操作类型，购买设备赠送红豆
	 */
	public static String rfRbcLogLogTypeBuy() {
		return DictHelper.getDictValueByKey("rf_rbc_log.log_type@buy");
	}

	/**
	 * 红豆日志，操作类型，充值设备赠送红豆
	 */
	public static String rfRbcLogLogTypeCharge() {
		return DictHelper.getDictValueByKey("rf_rbc_log.log_type@charge");
	}

	/**
	 * 红豆日志，操作类型，后台赠送
	 */
	public static String rfRbcLogLogTypeHandwork() {
		return DictHelper.getDictValueByKey("rf_rbc_log.log_type@handwork");
	}

	/**
	 * 红豆日志，操作类型，商场消费
	 */
	public static String rfRbcLogLogTypeShop() {
		return DictHelper.getDictValueByKey("rf_rbc_log.log_type@shop");
	}

	/**
	 * 红豆日志，操作类型，抽奖赠送
	 */
	public static String rfRbcLogLogTypeAward() {
		return DictHelper.getDictValueByKey("rf_rbc_log.log_type@award");
	}

	/**
	 * 充值记录，充值类型，快钱
	 */
	public static String rfRechargeRechargeType99bill() {
		return DictHelper.getDictValueByKey("rf_recharge.recharge_type@99bill");
	}

	/**
	 * 充值记录，充值类型，支付宝
	 */
	public static String rfRechargeRechargeTypeAlipay() {
		return DictHelper.getDictValueByKey("rf_recharge.recharge_type@alipay");
	}

	/**
	 * 充值记录，交易状态，成功
	 */
	public static String rfRechargeTradeStatusSuccess() {
		return DictHelper.getDictValueByKey("rf_recharge.trade_status@success");
	}

	/**
	 * 充值记录，交易状态，失败
	 */
	public static String rfRechargeTradeStatusFaild() {
		return DictHelper.getDictValueByKey("rf_recharge.trade_status@faild");
	}

	/**
	 * 短信，发送状态，成功
	 */
	public static String rfSmsResultStatusSuccess() {
		return DictHelper.getDictValueByKey("rf_sms.result_status@success");
	}

	/**
	 * 短信，发送状态，失败
	 */
	public static String rfSmsResultStatusDefeated() {
		return DictHelper.getDictValueByKey("rf_sms.result_status@defeated");
	}

	/**
	 * 短信发送，发送方式，人工
	 */
	public static String rfSmsSmsStatusHuman() {
		return DictHelper.getDictValueByKey("rf_sms.sms_status@human");
	}

	/**
	 * 短信发送，发送方式，系统
	 */
	public static String rfSmsSmsStatusSystem() {
		return DictHelper.getDictValueByKey("rf_sms.sms_status@system");
	}

	/**
	 * 用户表，是否首次申请普通设备，再次申请普通设备
	 */
	public static String rfUserFirstApplyStatusNo() {
		return DictHelper.getDictValueByKey("rf_user.first_apply_status@no");
	}

	/**
	 * 用户表，是否首次申请普通设备，首次申请普通设备
	 */
	public static String rfUserFirstApplyStatusYes() {
		return DictHelper.getDictValueByKey("rf_user.first_apply_status@yes");
	}

	/**
	 * 公告记录，用户公告状态，已读
	 */
	public static String rfUserNoticeUserNoticeStatusRead() {
		return DictHelper
				.getDictValueByKey("rf_user_notice.user_notice_status@read");
	}

	/**
	 * 公告信息记录，用户公告状态，未读
	 */
	public static String rfUserNoticeUserNoticeStatusUnread() {
		return DictHelper
				.getDictValueByKey("rf_user_notice.user_notice_status@unread");
	}

	/**
	 * 会员设备，设备等级，普通设备
	 */
	public static String rfUserPadPadGradeGeneral() {
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@general");
	}

	/**
	 * 会员设备，设备等级，VIP（1级）
	 */
	public static String rfUserPadPadGradeVip() {
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@vip");
	}
	
	/**
	 * 会员设备，设备等级，VIP（3级）
	 */
	public static String rfUserPadPadGradesVip() {
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@svip");
	}

	/**
	 * 设备等级，体验设备
	 */
	public static String rfUserPadPadGradeTaste() {
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@taste");
	}
	
	/**
	 * 设备等级，游戏设备(4)
	 */
	public static String rfUserPadPadGradeGame() {
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@game");
	}
	
	/**
	 * 设备等级，GVIP设备(5)
	 */
	public static String rfUserPadPadGradeGvip() {
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@gvip");
	}

	/**
	 * 视频，视频类型，用户链路
	 */
	public static String rfVideoVideoTypeUser() {
		return DictHelper.getDictValueByKey("rf_video.video_type@user");
	}

	/**
	 * 视频，视频类型，设备链路
	 */
	public static String rfVideoVideoTypeDevice() {
		return DictHelper.getDictValueByKey("rf_video.video_type@device");
	}

	/**
	 * 虚拟任务，任务结果状态
	 */
	public static String rfVmTaskTaskResultStatusSucceed() {
		return DictHelper
				.getDictValueByKey("rf_vm_task.task_result_status@succeed");
	}

	/**
	 * 虚拟任务结果状态，jms发送失败
	 */
	public static String rfVmTaskTaskResultStatusDefeated() {
		return DictHelper
				.getDictValueByKey("rf_vm_task.task_result_status@defeated");
	}

	/**
	 * 虚拟任务结果状态，失败
	 */
	public static String rfVmTaskTaskResultStatusDefeat() {
		return DictHelper
				.getDictValueByKey("rf_vm_task.task_result_status@defeat");
	}

	/**
	 * 任务来源，后台管理系统
	 */
	public static String rfVmTaskTaskSourceSystem() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_source@system");
	}

	/**
	 * 虚拟任务状态，开始
	 */
	public static String rfVmTaskTaskStatusStart() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_status@start");
	}

	/**
	 * 虚拟任务状态，进行中
	 */
	public static String rfVmTaskTaskStatusOngoing() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_status@ongoing");
	}

	/**
	 * 虚拟任务状态，结束
	 */
	public static String rfVmTaskTaskStatusOver() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_status@over");
	}

	/**
	 * 虚拟任务状态，发送jms消息失败
	 */
	public static String rfVmTaskTaskStatusEnd() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_status@end");
	}

	/**
	 * 虚拟管理任务，任务类型，启动
	 */
	public static String rfVmTaskTaskTypeStart() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_type@start");
	}

	/**
	 * 虚拟管理任务，任务类型，停止
	 */
	public static String rfVmTaskTaskTypeStop() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_type@stop");
	}

	/**
	 * 虚拟管理任务，任务类型，重启
	 */
	public static String rfVmTaskTaskTypeRestart() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_type@restart");
	}

	/**
	 * 虚拟管理任务，任务类型，回收
	 */
	public static String rfVmTaskTaskTypeRecovery() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_type@recovery");
	}

	/**
	 * 虚拟任务管理，任务类型，重置
	 */
	public static String rfVmTaskTaskTypeResetvm() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_type@resetVM");
	}

	/**
	 * 虚拟管理任务，任务类型，重启remoteplay
	 */
	public static String rfVmTaskTaskTypeRestartremoteplay() {
		return DictHelper
				.getDictValueByKey("rf_vm_task.task_type@restartRemotePlay");
	}

	/**
	 * 虚拟设备任务管理，任务类型，所有物理设备下的已授权虚拟设备启用申请
	 */
	public static String rfVmTaskTaskTypeOpen() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_type@open");
	}

	/**
	 * 虚拟管理任务，任务类型，根据虚拟设备SN号启用物理设备中的虚拟设备
	 */
	public static String rfVmTaskTaskTypeOpensn() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_type@openSn");
	}

	/**
	 * 针对物理设备的重启
	 */
	public static String rfVmTaskTaskTypeRebootdevice() {
		return DictHelper
				.getDictValueByKey("rf_vm_task.task_type@rebootDevice");
	}

	/**
	 * 虚拟管理任务，任务类型，更新（针对物理设备）
	 */
	public static String rfVmTaskTaskTypeUpdate() {
		return DictHelper.getDictValueByKey("rf_vm_task.task_type@update");
	}

	/**
	 * 礼包类型，优惠折扣礼包
	 */
	public static String shopPackageCategoryDiscount() {
		return DictHelper.getDictValueByKey("shop_package.category@discount");
	}

	/**
	 * 礼包类型，游戏礼包
	 */
	public static String shopPackageCategoryGame() {
		return DictHelper.getDictValueByKey("shop_package.category@game");
	}

	/**
	 * 礼包类型，红手指礼包
	 */
	public static String shopPackageCategoryRedfinger() {
		return DictHelper.getDictValueByKey("shop_package.category@redfinger");
	}

	/**
	 * 礼包类型，限量礼包
	 */
	public static String shopPackageCategoryLimitededition() {
		return DictHelper
				.getDictValueByKey("shop_package.category@limitedEdition");
	}

	/**
	 * 礼包码的购买情况，已被购买
	 */
	public static String shopPackageCodeBuyFlagYes() {
		return DictHelper.getDictValueByKey("shop_package_code.buy_flag@yes");
	}

	/**
	 * 礼包码的购买情况，未被购买
	 */
	public static String shopPackageCodeBuyFlagNo() {
		return DictHelper.getDictValueByKey("shop_package_code.buy_flag@no");
	}

	/**
	 * 礼包码的使用情况，已被使用
	 */
	public static String shopPackageCodeUseFlagYes() {
		return DictHelper.getDictValueByKey("shop_package_code.use_flag@yes");
	}

	/**
	 * 礼包码的使用情况，未被使用
	 */
	public static String shopPackageCodeUseFlagNo() {
		return DictHelper.getDictValueByKey("shop_package_code.use_flag@no");
	}

	/**
	 * 游戏下载任务审核状态：失败
	 */
	public static String taskGamedownloadCheckCheckStatusFail() {
		return DictHelper
				.getDictValueByKey("task_gamedownload_check.check_status@fail");
	}

	/**
	 * 游戏下载任务审核状态：未审核
	 */
	public static String taskGamedownloadCheckCheckStatusIng() {
		return DictHelper
				.getDictValueByKey("task_gamedownload_check.check_status@ing");
	}

	/**
	 * 游戏下载任务审核状态：已通过
	 */
	public static String taskGamedownloadCheckCheckStatusPass() {
		return DictHelper
				.getDictValueByKey("task_gamedownload_check.check_status@pass");
	}

	/**
	 * 用户任务的状态，未接取（可接取）
	 */
	public static String taskRecordAwardStatusMissed() {
		return DictHelper.getDictValueByKey("task_record.award_status@missed");
	}

	/**
	 * 用户任务的状态，已接取-未完成
	 */
	public static String taskRecordAwardStatusUndone() {
		return DictHelper.getDictValueByKey("task_record.award_status@undone");
	}

	/**
	 * 用户任务的状态，已完成-未领取（奖励）
	 */
	public static String taskRecordAwardStatusWaitgain() {
		return DictHelper
				.getDictValueByKey("task_record.award_status@waitgain");
	}

	/**
	 * 用户任务的状态，已领取（奖励）
	 */
	public static String taskRecordAwardStatusSucceed() {
		return DictHelper.getDictValueByKey("task_record.award_status@succeed");
	}

	/**
	 * 红手指任务类型，每日任务
	 */
	public static String taskSystemCategoryEveryday() {
		return DictHelper.getDictValueByKey("task_system.category@everyday");
	}

	/**
	 * 红手指任务类型，临时任务
	 */
	public static String taskSystemCategoryTemporary() {
		return DictHelper.getDictValueByKey("task_system.category@temporary");
	}

	/**
	 * 红手指任务类型，推广任务
	 */
	public static String taskSystemCategoryGaneralize() {
		return DictHelper.getDictValueByKey("task_system.category@ganeralize");
	}

	/**
	 * 红手指任务类型，完善资料
	 */
	public static String taskSystemCategoryUserdata() {
		return DictHelper.getDictValueByKey("task_system.category@userdata");
	}

	/**
	 * 红手指任务类型，问卷调查
	 */
	public static String taskSystemCategoryWjdc() {
		return DictHelper.getDictValueByKey("task_system.category@wjdc");
	}

	/**
	 * 红手指任务类型，游戏下载
	 */
	public static String taskSystemCategoryGamedownload() {
		return DictHelper
				.getDictValueByKey("task_system.category@gamedownload");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName应用宝() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@应用宝");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName360应用市场() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@360应用市场");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketNameTt() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@tt");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName百度() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@百度");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName小米() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@小米");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName九游() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@九游");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketNameOppo() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@oppo");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName豌豆荚() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@豌豆荚");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName安智() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@安智");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName拇指玩() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@拇指玩");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName应用汇() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@应用汇");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName华为() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@华为");
	}

	/**
*
*/
	public static String toolsMarketUpdateMarketName联想() {
		return DictHelper
				.getDictValueByKey("tools_market_update.market_name@联想");
	}

	/**
*
*/
	public static String toolsMarketUpdateResultYes() {
		return DictHelper.getDictValueByKey("tools_market_update.result@yes");
	}

	/**
*
*/
	public static String toolsMarketUpdateResultNo() {
		return DictHelper.getDictValueByKey("tools_market_update.result@no");
	}

	/**
*
*/
	public static String toolsMarketUpdateResultError() {
		return DictHelper.getDictValueByKey("tools_market_update.result@error");
	}

	/**
	 * 用户操作按钮统计：红豆商城
	 */
	public static String uiButtonStatRbcmall() {
		return DictHelper.getDictValueByKey("ui.button_stat@rbcMall");
	}

	/**
	 * 用户操作按钮统计：任务
	 */
	public static String uiButtonStatTasks() {
		return DictHelper.getDictValueByKey("ui.button_stat@tasks");
	}

	/**
	 * 用户操作按钮统计：兑换
	 */
	public static String uiButtonStatExchange() {
		return DictHelper.getDictValueByKey("ui.button_stat@exchange");
	}

	/**
	 * 用户操作按钮统计：充值
	 */
	public static String uiButtonStatRecharge() {
		return DictHelper.getDictValueByKey("ui.button_stat@recharge");
	}

	/**
	 * 用户操作按钮统计：客服
	 */
	public static String uiButtonStatCustomer() {
		return DictHelper.getDictValueByKey("ui.button_stat@customer");
	}

	/**
	 * 用户操作按钮统计：设置画质
	 */
	public static String uiButtonStatSetquality() {
		return DictHelper.getDictValueByKey("ui.button_stat@setQuality");
	}

	/**
	 * 用户操作按钮统计：查看设备
	 */
	public static String uiButtonStatLookdevice() {
		return DictHelper.getDictValueByKey("ui.button_stat@lookDevice");
	}

	/**
	 * 用户操作按钮统计：查看订单
	 */
	public static String uiButtonStatLookorder() {
		return DictHelper.getDictValueByKey("ui.button_stat@lookOrder");
	}

	/**
	 * 用户操作按钮统计：故障申请
	 */
	public static String uiButtonStatFault() {
		return DictHelper.getDictValueByKey("ui.button_stat@fault");
	}

	/**
	 * 用户操作按钮统计：应用市场进入次数
	 */
	public static String uiButtonStatUse() {
		return DictHelper.getDictValueByKey("ui.button_stat@use");
	}

	/**
	 * 用户操作按钮统计：回主界面启动次数
	 */
	public static String uiButtonStatReturnmain() {
		return DictHelper.getDictValueByKey("ui.button_stat@returnMain");
	}

	/**
	 * 用户操作按钮统计：重启、截图（后台有命令次数统计亦可）
	 */
	public static String uiButtonStatCommand() {
		return DictHelper.getDictValueByKey("ui.button_stat@command");
	}

	/**
	 * 微信操作类型，红手指帐号绑定
	 */
	public static String wechartOperateTypeBinding() {
		return DictHelper.getDictValueByKey("wechart.operate_type@binding");
	}

	/**
	 * 微信操作类型，红手指帐号解绑
	 */
	public static String wechartOperateTypeUnbundling() {
		return DictHelper.getDictValueByKey("wechart.operate_type@unbundling");
	}

	/**
	 * 微信操作类型，设备截图
	 */
	public static String wechartOperateTypeScreenshot() {
		return DictHelper.getDictValueByKey("wechart.operate_type@screenshot");
	}

	/**
	 * 微信操作类型，查询设备状态
	 */
	public static String wechartOperateTypeInquire() {
		return DictHelper.getDictValueByKey("wechart.operate_type@inquire");
	}

	/**
	 * 活动类型，红豆活动
	 */
	public static String activityRbc() {
		return DictHelper.getDictValueByKey("rf_activity.activity@rbc");
	}
	/**
	 * 活动类型，套餐活动
	 */
	public static String activityGoods() {
		return DictHelper.getDictValueByKey("rf_activity.activity@goods");
	}
	/**
	 * 活动类型，微信抽奖活动
	 */
	public static String activityWechartLottery() {
		return DictHelper.getDictValueByKey("rf_activity.activity@wechartLottery");
	}
	
	/**
	 * 活动类型，优惠劵活动
	 */
	public static String activityCoupon() {
		return DictHelper.getDictValueByKey("rf_activity.activity@coupon");
	}
	
	/**
	 * 活动类型，超级VIP
	 */
	public static String activitySvip() {
		return DictHelper.getDictValueByKey("rf_activity.activity@svip");
	}
	
	/**
	 * 活动类型,设备时长活动
	 * @return
	 */
	public static String activityTime() {
		return DictHelper.getDictValueByKey("rf_activity.activity@time");
	}
	
	/**
	 * 活动类型,钱包充值活动
	 * @return
	 */
	public static String activityWalleRecharge() {
		return DictHelper.getDictValueByKey("rf_activity.activity@wallet_recharge");
	}
	
	/**
	 * 活动类型,邀请购买设备
	 * @return
	 */
	public static String activityInviteTime() {
		return DictHelper.getDictValueByKey("rf_activity.activity@invite_time");
	}
	
	/**
	 * 活动类型,邀请登录
	 * @return
	 */
	public static String activityInviteTimeCoupon() {
		return DictHelper.getDictValueByKey("rf_activity.activity@invite_time_coupon");
	}
	
	/**
	 * 活动类型,邀请登录送平安礼盒
	 * @return
	 */
	public static String activityInviteSignupBox() {
		return DictHelper.getDictValueByKey("rf_activity.activity@invite_signup_box");
	}
	
	/**
	 * 活动类型,邀请登录送平安礼盒
	 * @return
	 */
	public static String activityInviteBuyBox() {
		return DictHelper.getDictValueByKey("rf_activity.activity@invite_buy_box");
	}
	
	/**
	 * 活动类型,邀请注册抽奖
	 * @return
	 */
	public static String activityInviteLottery() {
		return DictHelper.getDictValueByKey("rf_activity.activity@invite_lottery");
	}
	
	/**
	 * 活动类型,邀请购买设备抽奖
	 * @return
	 */
	public static String activityInviteBuyLottery() {
		return DictHelper.getDictValueByKey("rf_activity.activity@invite_buy_lottery");
	}
	
	/**
	 * 活动类型,累计登录赠送抽奖
	 * @return
	 */
	public static String activityCumulativeLogin() {
		return DictHelper.getDictValueByKey("rf_activity.activity@cumulative_login");
	}
	
	/**
	 * 活动类型,每日登录抽奖
	 * @return
	 */
	public static String activityDailyLogin() {
		return DictHelper.getDictValueByKey("rf_activity.activity@daily_login");
	}
	
	/**
	 * 活动类型,每日签到抽奖
	 * @return
	 */
	public static String activityDailySignIn() {
		return DictHelper.getDictValueByKey("rf_activity.activity@daily_sign_in");
	}
	
	/**
	 * 活动类型,每日充值钱包抽奖
	 * @return
	 */
	public static String activityDailyWalletRecharge() {
		return DictHelper.getDictValueByKey("rf_activity.activity@daily_walletRecharge");
	}
	
	/**
	 * 活动类型,新手折扣券
	 * @return
	 */
	public static String activityNoviceCoupon() {
		return DictHelper.getDictValueByKey("rf_activity.activity@novice_coupon");
	}
	
	/**
	 * 活动类型,大转盘活动
	 * @return
	 */
	public static String activityBigLottery() {
		return DictHelper.getDictValueByKey("rf_activity.activity@big_lottery");
	}
	
	/**
	 * 活动类型,小转盘活动
	 * @return
	 */
	public static String activityLittleLottery() {
		return DictHelper.getDictValueByKey("rf_activity.activity@little_lottery");
	}
	
	
	/**
	 * 产品类型：超级vip
	 */
	public static String goodsSvip() {
		return DictHelper.getDictValueByKey("rf_goods.goods_type@svip");
	}
	
	/**
	 * 产品类型：钱包充值套餐
	 */
	public static String goodsWalleRechare() {
		return DictHelper.getDictValueByKey("rf_goods.goods_type@wallet_recharge");
	}
	
	/**
	 * 产品类型：vip
	 */
	public static String goodsVip() {
		return DictHelper.getDictValueByKey("rf_goods.goods_type@vip");
	}
	
	/**
	 * 产品类型：gvip
	 */
	public static String goodsGvip() {
		return DictHelper.getDictValueByKey("rf_goods.goods_type@gvip");
	}
	
	/**
	 * 任务奖励：红豆
	 */
	public static String taskAwardRbc() {
		return DictHelper.getDictValueByKey("tk_task_award.award@rbc");
	}
	
	/**
	 * 任务奖励：积分
	 */
	public static String taskAwardScore() {
		return DictHelper.getDictValueByKey("tk_task_award.award@score");
	}
	
	/**
	 * 任务奖励：激活码
	 */
	public static String taskAwardActivationCode() {
		return DictHelper.getDictValueByKey("tk_task_award.award@activationCode");
	}
	
	/**
	 * 任务奖励：优惠券
	 */
	public static String taskAwardCoupon() {
		return DictHelper.getDictValueByKey("tk_task_award.award@coupon");
	}
	
	/**
	 * 任务奖励：抽奖次数
	 */
	public static String taskAwardLottery() {
		return DictHelper.getDictValueByKey("tk_task_award.award@lottery");
	}
	
	/**
	 * 广播类型：后台添加
	 */
	public static String background() {
		return DictHelper.getDictValueByKey("rf_broadcast.broadcast_type@background");
	}
	
	/**
	 * 时间类型：天
	 * @return
	 */
	public static String timeTypeDay(){
		return DictHelper.getDictValueByKey("time.time_type@day");
	}
	
	/**
	 * 时间类型：小时
	 * @return
	 */
	public static String timeTypeHour(){
		return DictHelper.getDictValueByKey("time.time_type@hour");
	}
	

	/**
	 * 时间类型：小时
	 * @return
	 */
	public static String expireTimeTypeHour(){
		return DictHelper.getDictValueByKey("expire_time.time_type@hour");
	}
	
	/**
	 * 时间类型：天
	 * @return
	 */
	public static String expireTimeTypeDay(){
		return DictHelper.getDictValueByKey("expire_time.time_type@day");
	}
	
	/**
	 * 时间类型：天
	 * @return
	 */
	public static String expireTimeTypeMinute(){
		return DictHelper.getDictValueByKey("expire_time.time_type@minute");
	}
	
	/**
	 * 设备类型：普通
	 * @return
	 */
	public static String normalPadGrade(){
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@general");
	}
	
	/**
	 * 设备类型：vip
	 * @return
	 */
	public static String vipnormalPadGrade(){
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@vip");
	}
	
	/**
	 * 设备类型：体验
	 * @return
	 */
	public static String tastePadGrade(){
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@taste");
	}
	
	/**
	 * 设备类型：超级vip
	 * @return
	 */
	public static String svipPadGrade(){
		return DictHelper.getDictValueByKey("rf_user_pad.pad_grade@svip");
	}
	
	/**
	 * 应用类型：工具
	 * @return
	 */
	public static String toolSoftType(){
		return DictHelper.getDictValueByKey("rf_game.soft_type@tool");
	}
	
	/**
	 * 应用类型：软件
	 * @return
	 */
	public static String softSoftType(){
		return DictHelper.getDictValueByKey("rf_game.soft_type@soft");
	}
	
	
	
	/**
	 * 短信发送方类型：阿里大于
	 * @return
	 */
	public static String smsSendTypeAlidayu(){
		return DictHelper.getDictValueByKey("sms.sms_send_type@alidayu");
	}
	
	/**
	 * 短信发送方类型：美联软通
	 * @return
	 */
	public static String smsSendType5c(){
		return DictHelper.getDictValueByKey("sms.sms_send_type@5c");
	}
	
	/**
	 * 标签类型类型：用户
	 * @return
	 */
	public static String labelTypeUser(){
		return DictHelper.getDictValueByKey("rf_label.label_type@user");
	}
	
	/**
	 * 标签类型类型：设备
	 * @return
	 */
	public static String labelTypePad(){
		return DictHelper.getDictValueByKey("rf_label.label_type@pad");
	}
	
	/**
	 * 用户标签编码：活动
	 * @return
	 */
	public static String labelCodeActivity(){
		return DictHelper.getDictValueByKey("system.user_label@activity");
	}
	
	/**
	 * 用户标签编码：商品
	 * @return
	 */
	public static String labelCodeGoods(){
		return DictHelper.getDictValueByKey("system.user_label@goods");
	}
	
	/**
	 * 消息类型：设备更换
	 * @return
	 */
	public static String padExchange(){
		return DictHelper.getDictValueByKey("message_notity.notity_type@PAD_EXCHANGE");
	}
	
	/**
	 * 开奖状态：待开奖
	 * @return
	 */
	public static String periodStatusWaiting(){
		return DictHelper.getDictValueByKey("treasure_period.period_status@waitinglottery");
	}
	
	/**
	 * 哪个项目使用了物理设备：主营
	 * @return
	 */
	public static String deviceUseMain(){
		return DictHelper.getDictValueByKey("rf_device.device_use@main");
	}
	
	/**
	 * 哪个项目使用了物理设备：其他项目
	 * @return
	 */
	public static String deviceUseOther(){
		return DictHelper.getDictValueByKey("rf_device.device_use@other");
	}
}
