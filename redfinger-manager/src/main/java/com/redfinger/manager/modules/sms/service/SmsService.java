package com.redfinger.manager.modules.sms.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;




import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaxin.interfacej.SmsHxClientSend;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfSms;
import com.redfinger.manager.common.domain.RfSmsBatch;
import com.redfinger.manager.common.domain.RfSmsExample;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSmsMapper;
import com.redfinger.manager.common.sms.SmsUtils;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.SmsSourceUtils;
import com.redfinger.manager.common.utils.XmlUtils;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sms.bean.Returnsms;
import com.redfinger.manager.modules.sms.bean.SendResult;
import com.ruanwei.interfacej.Sms5cClientSend;
import com.ruanwei.interfacej.SmsClientSend;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "smsId")
public class SmsService extends BaseService<RfSms, RfSmsExample, RfSmsMapper> {
	@Autowired
	UserPadService userPadService;
	@Autowired
	SmsBatchService batchService;
	@Autowired
	UserService userService;
	@Autowired
	PadService padService;
	@Autowired
	SmsUtils smsUtil;

	public SendResult sendSms(HttpServletRequest request,String ids, String smsContent , Integer templateId,String batchName) throws Exception {
		
		if (StringUtils.isBlank(ids)) {
			throw new BusinessException("没有可发送的记录！");
		}
		if (StringUtils.isEmpty(smsContent)) {
			throw new BusinessException("短信内容不能为空！");
		}
		if (smsContent.length() > 70) {
			throw new BusinessException("短信内容不能超过70个字！");
		}
		//保存批次
		RfSmsBatch batch= new RfSmsBatch();
		batch.setName(batchName);
		batchService.save(request, batch);
		
		String[] arrayId = ids.split(",");
		SendResult sendResult = new SendResult(arrayId.length);
		Date sendTime = new Date();
		for (String padId : arrayId) {
			if(StringUtils.isEmpty(padId)){
				sendResult.setTotal(sendResult.getTotal()-1);
				continue;
			}
			RfSms sms = new RfSms();
			sms.setSmsTemplateId(templateId);
			sms.setSmsContent(smsContent);
			sms.setSendTime(sendTime);
			sms.setPriorityLevel(1);
			
			//人工短信
			sms.setSmsStatus(ConstantsDb.rfSmsSmsStatusHuman());
			sms.setBatchId(batch.getId());
			//获取设备
			RfPad pad = padService.get(Integer.parseInt(padId));
			sms.setPadId(pad.getPadId());
			sms.setPadCode(pad.getPadCode());
			// 获取用户编号
			List<RfUserPad> userPadList=userPadService.initQuery().andEqualTo("padId", Integer.parseInt(padId)).singleAll();
			if(!Collections3.isEmpty(userPadList)){
				RfUserPad userPad = userPadList.get(0);
				//获取用户
				RfUser user = userService.get(userPad.getUserId());
				sms.setUserId(user.getUserId());
				sms.setUserName(user.getUserName());
				if(StringUtils.isNotEmpty(user.getUserMobilePhone())){
					sms.setSmsMobile(user.getUserMobilePhone());
				}
			}
			// 发送短信
			String result = "";
			if(StringUtils.equals("movek", smsUtil.getSmsType())){
				result = SmsClientSend.sendSms(smsUtil.getSmsMovekUrl(), smsUtil.getSmsMovekUserId(), 
						smsUtil.getSmsMovekAccount(), smsUtil.getSmsMovekPassword(),
						sms.getSmsMobile(), sms.getSmsContent());
				log.info("mobile="+sms.getSmsMobile()+" sms resultXml:"+result);
				
				Returnsms returnsms = XmlUtils.stringToObject(result, Returnsms.class);
				// 发送进度
				if (returnsms.getReturnstatus().equals("Success")) {
					sendResult.setSuccess(sendResult.getSuccess() + 1);
				} else {
					sendResult.setFault(sendResult.getFault() + 1);
				}
				
				// 持久化
				sms.setResultStatus(returnsms.getReturnstatus().toLowerCase());
				sms.setRemark(returnsms.getMessage());
				
			}else if(StringUtils.equals("5c", smsUtil.getSmsType())){
				result = Sms5cClientSend.send(smsUtil.getSms5cUrl(), smsUtil.getSms5cApikey(), 
						smsUtil.getSms5cUsername(), smsUtil.getSms5cPassword(),
						sms.getSmsMobile(), sms.getSmsContent());
				log.info("mobile="+sms.getSmsMobile()+" sms resultXml:"+result);
				
				if(StringUtils.startsWith(result, "success")){
					sendResult.setSuccess(sendResult.getSuccess() + 1);
					
					sms.setResultStatus("success");
					sms.setRemark(result);
					
					log.debug("send5cSMS success");
					
				}else{
					sendResult.setFault(sendResult.getFault() + 1);
					
					sms.setResultStatus("fail");
					sms.setRemark(result);
					
					log.debug("send5cSMS fail");
				}
				/** 采用北京创世华信发送短信 */
			}else if (StringUtils.equals("hx", smsUtil.getSmsType())) {
				
				/** 设置发送短信的类型 */
				sms.setSmsSendType("hx");
				result = SmsHxClientSend.send(smsUtil.getSmsHxUrl(), smsUtil.getSmsHxUserId(), smsUtil.getSmsHxAccount(), 
						smsUtil.getSmsHxPassword(),sms.getSmsMobile(), sms.getSmsContent());
				
				log.info("mobile="+sms.getSmsMobile()+" sms resultJson:"+result);
				
				if ("未发送,参数异常!".equals(result)) {
					throw new BusinessException(result);
				}
				
				JSONObject resultObject = JSONObject.fromObject(result);
				String status = resultObject.get("returnstatus").toString();
				/** 如果返回状态是Success,就表示是发送成功,并且正确的返回了状态值 */
				if ("Success".equals(status)) {
					sendResult.setSuccess(sendResult.getSuccess() + 1);
				} else {
					sendResult.setFault(sendResult.getFault() + 1);
				}
				
				// 持久化
				/** 如果发送失败,华信返回的是Faild与数据库中的字段不同,所以采用写死 */
				sms.setResultStatus(status.equals("Success")?"success":"fail");
				/** 设置任务id到resultModel中,方便后面进行回执状态更新的通过手机号码和任务id进行查找,然后更新回执状态*/
				sms.setResultModel(resultObject.get("taskID").toString());
				sms.setRemark(resultObject.toString());
			}
			this.save(request, sms);
		}
		return sendResult;
	}

	public SendResult addmobile(HttpServletRequest request, RfSms bean,String batchName) throws Exception {
		if (StringUtils.isBlank(bean.getSmsMobile())) {
			throw new BusinessException("没有可发送的记录！");
		}
		if (StringUtils.isEmpty(bean.getSmsContent())) {
			throw new BusinessException("短信内容不能为空！");
		}
		if (bean.getSmsContent().length() > 70) {
			throw new BusinessException("短信内容不能超过70个字！");
		}
		if(bean.getSmsMobile().length()>15 && bean.getSmsMobile().indexOf("\r\n")==-1){
			throw new BusinessException("发送多个手机号码时请用换行隔开！");
		}
		
		//保存批次
		RfSmsBatch batch= new RfSmsBatch();
		batch.setName(batchName);
		batchService.save(request, batch);
		/** 这里是发送的所有的短信的手机号码  */
		String[] mobileArr = bean.getSmsMobile().split("\r\n");
		SendResult sendResult = new SendResult(mobileArr.length);
		Date current = new Date();
		/** 一条一条的发送 */
		for (String mobile : mobileArr) {
			if(StringUtils.isEmpty(mobile)){
				sendResult.setTotal(sendResult.getTotal()-1);
				continue;
			}
			RfSms sms = new RfSms();
			sms.setSmsMobile(mobile.trim());
			sms.setSmsContent(bean.getSmsContent());
			sms.setPriorityLevel(1);
			sms.setSendTime(current);
			sms.setBatchId(batch.getId());
			sms.setSmsType(com.redfinger.manager.common.sms.SmsType.SMS);
			sms.setSmsSource(SmsSourceUtils.manager);
			sms.setClientSource(ClientType.PC);
			// 发送短信
			String result = "";
			
			if(StringUtils.equals("movek", smsUtil.getSmsType())){
				result = SmsClientSend.sendSms(smsUtil.getSmsMovekUrl(), smsUtil.getSmsMovekUserId(), 
						smsUtil.getSmsMovekAccount(), smsUtil.getSmsMovekPassword(),
						sms.getSmsMobile(), sms.getSmsContent());
				
				log.info("mobile="+sms.getSmsMobile()+" sms resultXml:"+result);
				
				Returnsms returnsms = XmlUtils.stringToObject(result, Returnsms.class);
				// 发送进度
				if (returnsms.getReturnstatus().equals("Success")) {
					sendResult.setSuccess(sendResult.getSuccess() + 1);
				} else {
					sendResult.setFault(sendResult.getFault() + 1);
				}
				
				// 持久化
				sms.setResultStatus(returnsms.getReturnstatus().toLowerCase());
				sms.setRemark(returnsms.getMessage());
				
			}else if(StringUtils.equals("5c", smsUtil.getSmsType())){
				result = Sms5cClientSend.send(smsUtil.getSms5cUrl(), smsUtil.getSms5cApikey(), 
						smsUtil.getSms5cUsername(), smsUtil.getSms5cPassword(),
						sms.getSmsMobile(), sms.getSmsContent());
				
				log.info("mobile="+sms.getSmsMobile()+" sms resultXml:"+result);
				
				if(StringUtils.startsWith(result, "success")){
					sendResult.setSuccess(sendResult.getSuccess() + 1);
					
					sms.setResultStatus("success");
					sms.setRemark(result);
					
					log.debug("send5cSMS success");
					
				}else{
					sendResult.setFault(sendResult.getFault() + 1);
					
					sms.setResultStatus("fail");
					sms.setRemark(result);
					
					log.debug("send5cSMS fail");
				}
				
				/** 采用北京创世华信发送短信 */
			}else if (StringUtils.equals("hx", smsUtil.getSmsType())) {
				
				/** 设置发送短信类型 */
				sms.setSmsSendType("hx");
				result = SmsHxClientSend.send(smsUtil.getSmsHxUrl(), smsUtil.getSmsHxUserId(), smsUtil.getSmsHxAccount(), 
						smsUtil.getSmsHxPassword(),sms.getSmsMobile(), sms.getSmsContent());
				
				log.info("mobile="+sms.getSmsMobile()+" sms resultJson:"+result);
				
				if ("未发送,参数异常!".equals(result)) {
					throw new BusinessException(result);
				}
				
				JSONObject resultObject = JSONObject.fromObject(result);
				String status = resultObject.get("returnstatus").toString();
				/** 如果返回状态是Success,就表示是发送成功,并且正确的返回了状态值 */
				if ("Success".equals(status)) {
					log.debug("send5cSMS success");
					sendResult.setSuccess(sendResult.getSuccess() + 1);
				} else {
					log.debug("send5cSMS fail");
					sendResult.setFault(sendResult.getFault() + 1);
				}
				
				// 持久化
				/** 如果发送失败,华信返回的是faild与数据库中的字段不同,所以采用写死 */
				sms.setResultStatus(status.equals("Success")?"success":"fail");
				/** 设置任务id到resultModel中,方便后面进行回执状态更新的通过手机号码和任务id进行查找,然后更新回执状态*/
				sms.setResultModel(resultObject.get("taskID").toString());
				sms.setRemark(resultObject.toString());
			
			}
			
			this.save(request, sms);
		}
		return sendResult;
	}

	public static void main(String[] args) {
		 String result=SmsHxClientSend.send("http://dx.ipyy.net/smsJson.aspx",
		 null, "hszkj", "abc123", "13189053881", "短信测试");
		 System.out.println(result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		System.out.println(jsonObject.get("returnstatus"));
		System.out.println(jsonObject.get("message"));
		System.out.println(jsonObject.get("remainpoint"));
		System.out.println(jsonObject.get("haha"));
		
	}
}
