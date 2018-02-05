package com.redfinger.manager.modules.sms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.SmsTypeKeyConstants;
import com.redfinger.manager.common.redis.RedisService;

@Controller
@RequestMapping(value = "/sms/sendType")
public class SmsSendTypeController extends BaseController{
	
	@Autowired
	private RedisService redisService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		/** 获取redis中文字短信发送方*/
		String smsTextSendType = redisService.get(SmsTypeKeyConstants.SMS_TEXT_KEY);
		/** 获取redis中语音短信发送方*/
		String smsVoiceSendType = redisService.get(SmsTypeKeyConstants.SMS_VOICE_KEY);
		/** 回显数据*/
		model.addAttribute("smsTextSendType", smsTextSendType);
		model.addAttribute("smsVoiceSendType", smsVoiceSendType);
		
		return this.toPage(request, response, model);  
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, String smsTextSendType, String smsVoiceSendType) throws Exception {
		
		/** 修改文字短信发送方*/
		if(StringUtils.isNotBlank(smsTextSendType)){
			redisService.set(SmsTypeKeyConstants.SMS_TEXT_KEY, smsTextSendType);
		}
		/** 修改语音短信发送方*/
		if(StringUtils.isNotBlank(smsVoiceSendType)){
			redisService.set(SmsTypeKeyConstants.SMS_VOICE_KEY, smsVoiceSendType);
		}
	}

}
