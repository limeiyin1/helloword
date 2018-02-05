package com.redfinger.manager.modules.member.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfSms;
import com.redfinger.manager.common.domain.RfSmsBatch;
import com.redfinger.manager.common.domain.RfSmsExample;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSmsMapper;
import com.redfinger.manager.common.sms.SmsUtils;
import com.redfinger.manager.common.utils.XmlUtils;
import com.redfinger.manager.modules.sms.bean.Returnsms;
import com.redfinger.manager.modules.sms.bean.SendResult;
import com.redfinger.manager.modules.sms.service.SmsBatchService;
import com.ruanwei.interfacej.Sms5cClientSend;
import com.ruanwei.interfacej.SmsClientSend;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "smsId")
public class UserSmsService extends BaseService<RfSms, RfSmsExample, RfSmsMapper> {

	@Autowired
	SmsBatchService batchService;
	@Autowired
	UserService userService;
	@Autowired
	SmsUtils smsUtil;

	public SendResult sendSms(HttpServletRequest request, String ids, String smsContent, Integer templateId,String batchName) throws Exception {
		if (StringUtils.isBlank(ids)) {
			throw new BusinessException("没有可发送的记录！");
		}
		if (StringUtils.isEmpty(smsContent)) {
			throw new BusinessException("短信内容不能为空！");
		}
		if (smsContent.length() > 70) {
			throw new BusinessException("短信内容不能超过70个字！");
		}
		// 保存批次
		RfSmsBatch batch = new RfSmsBatch();
		batch.setName(batchName);
		batchService.save(request, batch);

		String[] arrayId = ids.split(",");
		SendResult sendResult = new SendResult(arrayId.length);
		Date sendTime = new Date();
		for (String userId : arrayId) {
			if (StringUtils.isEmpty(userId)) {
				sendResult.setTotal(sendResult.getTotal() - 1);
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
			// 获取用户编号
			RfUser user = userService.initQuery().andEqualTo("userId",Integer.parseInt( userId)).singleAll().get(0);
			sms.setUserId(user.getUserId());
			sms.setUserName(user.getUserName());
			sms.setSmsMobile(user.getUserMobilePhone());
			// 发送短信
			String result = "";
			
			if(StringUtils.equals("movek", smsUtil.getSmsType())){
				result = SmsClientSend.sendSms(smsUtil.getSmsMovekUrl(), smsUtil.getSmsMovekUserId(), 
						smsUtil.getSmsMovekAccount(), smsUtil.getSmsMovekPassword(),
						sms.getSmsMobile(), sms.getSmsContent());
				
				log.debug("resultXml:"+result);
				
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
				
				log.debug("resultXml:"+result);
				
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
				
			}
			this.save(request, sms);
		}
		return sendResult;
	}

	public SendResult addmobile(HttpServletRequest request, RfSms bean) throws Exception {
		if (StringUtils.isBlank(bean.getSmsMobile())) {
			throw new BusinessException("没有可发送的记录！");
		}
		if (StringUtils.isEmpty(bean.getSmsContent())) {
			throw new BusinessException("短信内容不能为空！");
		}
		if (bean.getSmsContent().length() > 70) {
			throw new BusinessException("短信内容不能超过70个字！");
		}

		String[] mobileArr = bean.getSmsMobile().split(",");
		SendResult sendResult = new SendResult(mobileArr.length);
		Date current = new Date();
		for (String mobile : mobileArr) {
			if (StringUtils.isEmpty(mobile)) {
				sendResult.setTotal(sendResult.getTotal() - 1);
				continue;
			}
			RfSms sms = new RfSms();
			sms.setSmsMobile(mobile);
			sms.setSmsContent(bean.getSmsContent());
			sms.setPriorityLevel(1);
			sms.setSendTime(current);
			// 发送短信
			/*String result = SmsClientSend.sendSms(smsUrl, smsUserid, smsAccount, smsPassword, sms.getSmsMobile(), sms.getSmsContent());
			Returnsms returnsms = XmlUtils.stringToObject(result, Returnsms.class);
			// 发送进度
			if (returnsms.getReturnstatus().equals("Success")) {
				sendResult.setSuccess(sendResult.getSuccess() + 1);
			} else {
				sendResult.setFault(sendResult.getFault() + 1);
			}
			// 持久化
			sms.setResultStatus(returnsms.getReturnstatus().toLowerCase());
			sms.setRemark(returnsms.getMessage());*/
			
String result = "";
			
			if(StringUtils.equals("movek", smsUtil.getSmsType())){
				result = SmsClientSend.sendSms(smsUtil.getSmsMovekUrl(), smsUtil.getSmsMovekUserId(), 
						smsUtil.getSmsMovekAccount(), smsUtil.getSmsMovekPassword(),
						sms.getSmsMobile(), sms.getSmsContent());
				
				log.debug("resultXml:"+result);
				
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
				
				log.debug("resultXml:"+result);
				
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
				
			}
			
			this.save(request, sms);
		}
		return sendResult;
	}

	public static void main(String[] args) {
		// String
		// result=SmsClientSend.sendSms("http://218.244.136.70:8888/sms.aspx",
		// "1603", "hszkj", "duanxin520", "13189053881", "短信测试");
		// System.out.println(result);
	}
}
