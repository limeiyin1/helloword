package com.redfinger.manager.modules.batch.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.BatchDataCode;
import com.redfinger.manager.common.constants.EnablePadType;
import com.redfinger.manager.common.constants.GiveRbcType;
import com.redfinger.manager.common.constants.SendPadMsgType;
import com.redfinger.manager.common.constants.SendPadNoticeType;
import com.redfinger.manager.common.domain.TkBatchData;
import com.redfinger.manager.common.domain.TkBatchTask;
import com.redfinger.manager.common.domain.TkBatchTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TkBatchTaskMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="batchId")
public class BatchTaskService extends BaseService<TkBatchTask, TkBatchTaskExample, TkBatchTaskMapper> {

	@Autowired
	private BatchDataService dataService;
	
	/**
	 * 批处理——虚拟设备授权开放与取消
	 * @throws Exception 
	 */
	public void saveVmGrantBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		saveBatchData(request,batchData,"虚拟设备授权开放与取消-设备编码的文件路径",dataFilePath,BatchDataCode.VM_GRANT_FILE_PATH);
	}
	
	
	/**
	 * 批处理物理设备重启
	 * @throws Exception 
	 */
	public void saveDeviceRebootBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		saveBatchData(request,batchData,"物理设备重启-物理设备编码的文件路径",dataFilePath,BatchDataCode.DEVICE_REBOOT_FILE_PATH);
	}
	
	/**
	 * 批处理虚拟设备重启
	 * @throws Exception 
	 */
	public void saveVmPadRestartBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		saveBatchData(request,batchData,"虚拟设备重启-虚拟设备编码的文件路径",dataFilePath,BatchDataCode.VM_PAD_RESTART_FILE_PATH);
	}
	
	
	/**
	 * 保存维护设备、取消维护设备批处理任务的batchData数据
	 * @throws Exception 
	 */
	public void saveMiantBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		saveBatchData(request,batchData,"设备维护或取消维护-设备编码的文件路径",dataFilePath,BatchDataCode.PAD_MAINT_FILE_PATH);
	}
	
	/**
	 * 保存更换设备批处理任务的batchData数据
	 * @param request
	 * @param batch
	 * @param dataFilePath
	 * @param suffix
	 * @param isSendMessage
	 * @param isSendWechart
	 * @param sendMessageTemplate
	 * @param sendWechartTemplate
	 * @throws Exception
	 */
	public void saveReNewalPadBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath,
			String reNewPadIdcId,String padControlId,String padCodeGt,String padCodeLt,String deviceIpGt,String deviceIpLt,
			String isSendMessage,String isSendWechart, String sendMessageTemplate, 
			String sendWechartTemplate) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		//保存公告文件路径
		saveBatchData(request,batchData,"更换设备编码的文件路径",dataFilePath,BatchDataCode.RENEWAL_PAD_FILE_PATH);
		//保存新设备前缀
		saveBatchData(request,batchData,"更换设备机房id",reNewPadIdcId,BatchDataCode.RENEWAL_PAD_RENEWPADIDCID);
		saveBatchData(request,batchData,"更换设备控制节点",padControlId,BatchDataCode.RENEWAL_PAD_PADCONTROLID);
		saveBatchData(request,batchData,"更换设备设备编码段大于",padCodeGt,BatchDataCode.RENEWAL_PAD_PADCODEGT);
		saveBatchData(request,batchData,"更换设备设备编码段小于",padCodeLt,BatchDataCode.RENEWAL_PAD_PADCODELT);
		saveBatchData(request,batchData,"更换设备物理IP段大于",deviceIpGt,BatchDataCode.RENEWAL_PAD_DEVICEIPGT);
		saveBatchData(request,batchData,"更换设备物理IP段小于",deviceIpLt,BatchDataCode.RENEWAL_PAD_DEVICEIPLT);
		saveBatchData(request,batchData,"是否发送消息",isSendMessage,BatchDataCode.RENEWAL_PAD_IS_MESSAGE);
		saveBatchData(request,batchData,"是否发送微信",isSendWechart,BatchDataCode.RENEWAL_PAD_IS_WECHART);
		saveBatchData(request,batchData,"消息模板",sendMessageTemplate,BatchDataCode.RENEWAL_PAD_MESSAGE_TEMPLATE);
		saveBatchData(request,batchData,"微信模板",sendWechartTemplate,BatchDataCode.RENEWAL_PAD_WECHART_TEMPLATE);
	}
	
	
	/**
	 * 保存发送用户公告批处理任务的batchData数据
	 * @throws Exception 
	 */
	public void saveNoticeBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath,String userNoticeTitle,
			String userNoticeContent,String popStatus,Date popExpiredTime) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		//保存公告文件路径
		saveBatchData(request,batchData,"用户手机号码列表文件路径",dataFilePath,BatchDataCode.USER_NOTICE_FILE_PATH);
		//保存公告标题
		saveBatchData(request,batchData,"用户公告标题",userNoticeTitle,BatchDataCode.USER_NOTICE_TITLE);
		//保存公告内容
		saveBatchData(request,batchData,"用户公告内容",userNoticeContent,BatchDataCode.USER_NOTICE_CONTENT);
		//保存是否强制弹出信息
		saveBatchData(request,batchData,"公告内容是否强制弹出",popStatus,BatchDataCode.USER_NOTICE_POPSTATUS);
		//保存弹出的有效时间
		if(YesOrNoStatus.YES.equals(popStatus)){
		
			String popExpired = DateUtils.formatDateTime(popExpiredTime);
			saveBatchData(request,batchData,"弹出有效时间",popExpired,BatchDataCode.USER_NOTICE_POPEXPIRED);
		}
	}
	
	/**
	 *  保存发送设备公告
	 * @param request
	 * @param batch
	 * @param dataFilePath
	 * @param isOne  同一用户是否只发送一个公告
	 * @throws Exception
	 */
	
	public void savePadNoticeBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath,String padNoticeTitle,String padNoticeContent,String popStatus,
			String sendPasNoticeType,String padNoticeIdcId,String padNoticePadCodeGt,String padNoticePadCodeLt,String oneNoticeStatus) throws Exception{
		if(null != batch.getBatchId()){

			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		if(sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_IDC)){
			saveBatchData(request,batchData,"设备公告机房id",padNoticeIdcId,BatchDataCode.PAD_NOTICE_IDC_ID);
		}else if (sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE)) {
			//保存公告文件路径
			saveBatchData(request,batchData,"设备编码的文件路径",dataFilePath,BatchDataCode.PAD_NOTICE_FILE_PATH);
		}else if (sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE_BETWEEN)){
			saveBatchData(request,batchData,"设备公告设备编码大于",padNoticePadCodeGt,BatchDataCode.PAD_NOTICE_PADCODEGT);
			saveBatchData(request,batchData,"设备公告设备编码小于",padNoticePadCodeLt,BatchDataCode.PAD_NOTICE_PADCODELT);
		}
		//保存发送方式
		saveBatchData(request,batchData,"设备公告发送方式",sendPasNoticeType,BatchDataCode.PAD_NOTICE_SEND_TYPE);
		//保存公告标题
		saveBatchData(request,batchData,"设备公告标题",padNoticeTitle,BatchDataCode.PAD_NOTICE_TITLE);
		//保存公告内容
		saveBatchData(request,batchData,"设备公告内容",padNoticeContent,BatchDataCode.PAD_NOTICE_CONTENT);
		//保存是否强制弹出信息
		saveBatchData(request,batchData,"公告内容是否强制弹出",popStatus,BatchDataCode.PAD_NOTICE_POPSTATUS);
		saveBatchData(request,batchData,"一个用户是否只发送一条",oneNoticeStatus,BatchDataCode.ONE_NOTICE_STATUS);
	}
	

	/**
	 *  保存赠送设备时间
	 * @param request
	 * @param batch
	 * @param dataFilePath
	 * @param isOne  同一用户是否只发送一个公告
	 * @throws Exception
	 */
	
	public void savePadTimeBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath,String timeType,Integer controltime) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		//保存文件路径
		saveBatchData(request,batchData,"设备编码的文件路径",dataFilePath,BatchDataCode.PAD_TIME_FILE_PATH);
		//保存单位
		saveBatchData(request,batchData,"增加设备时间单位",timeType,BatchDataCode.PAD_TIME_TYPE);
		//保存数量
		saveBatchData(request,batchData,"增加时间数量",controltime+"",BatchDataCode.PAD_TIME_AMOUNT);
	}
	
	/**
	 *  按用户修改红豆
	 * @param request
	 * @param batch
	 * @param dataFilePath
	 * @param isOne  同一用户是否只发送一个公告
	 * @throws Exception
	 */
	
	public void saveRbcGetBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath,String userRbcType,Integer rbcGet) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		//保存赠送方式
		saveBatchData(request,batchData,"赠送方式",userRbcType,BatchDataCode.RBGET_TYPE);
		//保存文件路径
		saveBatchData(request,batchData,"用户手机列表文件路径",dataFilePath,BatchDataCode.RBGET_FILE_PATH);
		//修改数量
		saveBatchData(request,batchData,"修改红豆数量",rbcGet.toString(),BatchDataCode.RBGET_AMOUNT);
		//备注
		saveBatchData(request,batchData,"修改备注",batch.getRemark(),BatchDataCode.RBGET_REMARK);
	}
	
	/**
	 *  按设备修改红豆
	 * @param request
	 * @param batch
	 * @param dataFilePath
	 * @param isOne  同一用户是否只发送一个公告
	 * @throws Exception
	 */
	
	public void saveRbcByPadBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath,
			String giveRbcType,String idcId,String padRbcPadCodeGt,String padRbcPadCodeLt,Integer commonRbc,Integer vipRbc,Integer svipRbc,Integer gvipRbc) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		if(GiveRbcType.GIVE_RBC_BY_IDC.equals(giveRbcType)){
			saveBatchData(request,batchData,"机房id",idcId,BatchDataCode.RBC_BY_PAD_IDCID);
		}else if(GiveRbcType.GIVE_RBC_BY_PADECODE.equals(giveRbcType)){
			saveBatchData(request,batchData,"按设备赠送设备编码文件",dataFilePath,BatchDataCode.RBC_BY_PAD_FILE_PATH);
		} else if(GiveRbcType.GIVE_RBC_BY_PADECODE_BETWEEN.equals(giveRbcType)){
			saveBatchData(request,batchData,"按设备赠送设备编码段大于",padRbcPadCodeGt,BatchDataCode.RBC_BY_PAD_PADECODEGT);
			saveBatchData(request,batchData,"按设备赠送设备编码段小于",padRbcPadCodeLt,BatchDataCode.RBC_BY_PAD_PADECODELT);
		}
		saveBatchData(request,batchData,"赠送方式",giveRbcType,BatchDataCode.RBC_BY_PAD_GIVETYPE);
		saveBatchData(request,batchData,"普通用户赠送数量",commonRbc.toString(),BatchDataCode.RBC_BY_PAD_COMMOMRBC);
		saveBatchData(request,batchData,"vip赠送数量",vipRbc.toString(),BatchDataCode.RBC_BY_PAD_VIPRBC);
		saveBatchData(request,batchData,"svip赠送数量",svipRbc.toString(),BatchDataCode.RBC_BY_PAD_SVIPRBC);
		saveBatchData(request,batchData,"gvip赠送数量",gvipRbc.toString(),BatchDataCode.RBC_BY_PAD_GVIPRBC);
	}
	
	
	/**
	 *  保存批量绑定设备的数据
	 * @param request
	 * @param batch
	 * @param dataFilePath
	 * @param isOne  同一用户是否只发送一个公告
	 * @throws Exception
	 */
	
	public void saveBindPadTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath,String padClassify,String gamePadTime,
			Integer goodsId,Integer gvipGoodsId,String cloudPadTime) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		saveBatchData(request,batchData,"批量绑定设备的文件",dataFilePath,BatchDataCode.BIND_PAD_FILE_PATH);
		saveBatchData(request,batchData,"绑定的设备类型",padClassify,BatchDataCode.BIND_PAD_PADCLASSIFY);
		saveBatchData(request,batchData,"绑定游戏设备时长",gamePadTime,BatchDataCode.BIND_GAMEPAD_TIME);
		saveBatchData(request,batchData,"绑定vip套餐",goodsId.toString(),BatchDataCode.BIND_PAD_GOODS);
		saveBatchData(request,batchData,"绑定gvip套餐",gvipGoodsId.toString(),BatchDataCode.BIND_PAD_GVIPGOODS);
		saveBatchData(request,batchData,"绑定云控设备时长",cloudPadTime,BatchDataCode.BIND_CLOUDPAD_TIME);
	}
	
	/**
	 * 保存发送设备消息
	 * @param request
	 * @param batch
	 * @param dataFilePath
	 * @param notice
	 * @param sendPasNoticeType
	 * @param padNoticeIdcId
	 * @param padNoticePadCodeGt
	 * @param padNoticePadCodeLt
	 * @param oneNoticeStatus
	 * @throws Exception
	 */
	public void savePadMsgBatchTask(HttpServletRequest request,TkBatchTask batch,String dataFilePath,String padMsgTitle,String padMsgContent,String oneMsgStatus,
			String sendPasMsgType,String padMsgIdcId,String padMsgPadCodeGt,String padMsgPadCodeLt,String padWeixinContent) throws Exception{
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		if(sendPasMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_IDC)){
			saveBatchData(request,batchData,"设备消息机房id",padMsgIdcId,BatchDataCode.PAD_MSG_IDC_ID);
		}else if (sendPasMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE)) {
			//保存公告文件路径
			saveBatchData(request,batchData,"设备编码的文件路径",dataFilePath,BatchDataCode.PAD_MSG_FILE_PATH);
		}else if (sendPasMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE_BETWEEN)){
			saveBatchData(request,batchData,"设备消息设备编码大于",padMsgPadCodeGt,BatchDataCode.PAD_MSG_PADCODEGT);
			saveBatchData(request,batchData,"设备消息设备编码小于",padMsgPadCodeLt,BatchDataCode.PAD_MSG_PADCODELT);
		}
		//保存发送方式
		saveBatchData(request,batchData,"设备消息发送方式",sendPasMsgType,BatchDataCode.PAD_MSG_SEND_TYPE);
		//保存消息标题
		saveBatchData(request,batchData,"设备消息标题",padMsgTitle,BatchDataCode.PAD_MSG_TITLE);
		//保存消息内容
		saveBatchData(request,batchData,"设备消息内容",padMsgContent,BatchDataCode.PAD_MSG_CONTENT);
		saveBatchData(request,batchData,"微信内容",padWeixinContent,BatchDataCode.PAD_WEIXIN_CONTENT);
		saveBatchData(request,batchData,"一个用户是否只发送一条",oneMsgStatus,BatchDataCode.ONE_MSG_STATUS);
	}
	
	
	/**
	 * 保存批处理数据信息
	 * @param request
	 * @param batchData
	 * @param dataName
	 * @param dataValue
	 * @param dataCode
	 * @throws Exception
	 */
	public void saveBatchData(HttpServletRequest request,TkBatchData batchData,String dataName,String dataValue,String dataCode) throws Exception{
		batchData.setDataName(dataName);
		batchData.setDataValue(dataValue);
		batchData.setDataCode(dataCode);
		dataService.save(request, batchData);
	}

	/**
	 * 保存设备启用或禁用
	 * @param request
	 * @param bean
	 * @param dataFilePath
	 * @param enablePadType
	 * @param enablePadIdcId
	 * @param enablePadCodeGt
	 * @param enablePadCodeLt
	 * @param enablePadDeviceIpGt
	 * @param enablePadDeviceIpLt
	 * @throws Exception 
	 */
	public void saveEnablePadTask(HttpServletRequest request, TkBatchTask batch, String dataFilePath,
			String enablePadType,String enablePadIdcId,String enablePadControlId,String enablePadStatus,String enablePadBindStatus,
			String enablePadFaultStatus, String enablePadCodeGt, String enablePadCodeLt,
			String enablePadDeviceIpGt, String enablePadDeviceIpLt) throws Exception {
		if(null != batch.getBatchId()){
			//修改
			this.update(request, batch);
			dataService.deleteByBatchId(request, batch.getBatchId());//删除旧记录
		}else{
			//新增
			this.save(request, batch);
		}
		
		TkBatchData batchData = new TkBatchData();
		batchData.setBatchId(batch.getBatchId());
		saveBatchData(request,batchData,"启用或禁用方式",enablePadType,BatchDataCode.ENABLE_PAD_TYPE);
		if (enablePadType.equals(EnablePadType.ENABLE_PAD_BY_FILE)) {
			saveBatchData(request,batchData,"设备编码的文件路径",dataFilePath,BatchDataCode.ENABLE_PAD_FILE_PATH);
		} else if (enablePadType.equals(EnablePadType.ENABLE_PAD_BY_ALL)){
			
		} else {
			saveBatchData(request,batchData,"机房id",enablePadIdcId,BatchDataCode.ENABLE_PAD_IDC_ID);
			saveBatchData(request,batchData,"控制节点id",enablePadControlId,BatchDataCode.ENABLE_PAD_CONTROLL_ID);
			saveBatchData(request,batchData,"设备启用",enablePadStatus,BatchDataCode.ENABLE_PAD_STATUS);
			saveBatchData(request,batchData,"设备绑定状态",enablePadBindStatus,BatchDataCode.ENABLE_PAD_BIND_STATUS);
			saveBatchData(request,batchData,"故障状态",enablePadFaultStatus,BatchDataCode.ENABLE_PAD_FAULT_STATUS);
			saveBatchData(request,batchData,"设备编码大于",enablePadCodeGt,BatchDataCode.ENABLE_PAD_PADCODEGT);
			saveBatchData(request,batchData,"设备编码小于",enablePadCodeLt,BatchDataCode.ENABLE_PAD_PADCODELT);
			saveBatchData(request,batchData,"物理ip大于",enablePadDeviceIpGt,BatchDataCode.ENABLE_PAD_DEVICEIPGT);
			saveBatchData(request,batchData,"物理ip小于",enablePadDeviceIpLt,BatchDataCode.ENABLE_PAD_DEVICEIPLT);
		}
	}
}













