package com.redfinger.manager.modules.fault.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfMsgTemplate;
import com.redfinger.manager.common.domain.RfMsgTemplateExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfMsgTemplateMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "templateId")
public class FaultMsgTemplateService extends BaseService<RfMsgTemplate, RfMsgTemplateExample, RfMsgTemplateMapper> {

}
