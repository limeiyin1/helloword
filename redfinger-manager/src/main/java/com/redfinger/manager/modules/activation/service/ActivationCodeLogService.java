package com.redfinger.manager.modules.activation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfActivationCodeLog;
import com.redfinger.manager.common.domain.RfActivationCodeLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfActivationCodeLogMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="logId")
public class ActivationCodeLogService extends BaseService<RfActivationCodeLog, RfActivationCodeLogExample, RfActivationCodeLogMapper> {

}
