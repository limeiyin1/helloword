package com.redfinger.manager.modules.message.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfMessageNotify;
import com.redfinger.manager.common.domain.RfMessageNotifyExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfMessageNotifyMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "messageId")
public class MessageNotifyService extends BaseService<RfMessageNotify, RfMessageNotifyExample, RfMessageNotifyMapper> {
	@Autowired
	private RfMessageNotifyMapper mapper;
	
	public void saveMessage(Integer userId,String notifyType,String notityMessage,String notifyCode,String messageTitle){
		RfMessageNotify message = new RfMessageNotify();
		message.setUserId(userId);
		message.setIsLook(YesOrNoStatus.NO);
		message.setNotifyType(notifyType);
		message.setNotityMessage(notityMessage);
		message.setNotityCode(notifyCode);
		message.setCreater("super");
		message.setCreateTime(new Date());
		message.setEnableStatus(YesOrNoStatus.YES);
		message.setStatus(YesOrNoStatus.YES);
		message.setMessageTitle(messageTitle);
		mapper.insertSelective(message);
	}

}
