package com.redfinger.manager.modules.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfSmsTemplate;
import com.redfinger.manager.common.domain.RfSmsTemplateExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSmsTemplateMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "smsTemplateId")
public class SmsTemplateService extends BaseService<RfSmsTemplate, RfSmsTemplateExample, RfSmsTemplateMapper> {

	
}
