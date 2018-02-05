package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfRbcLog;
import com.redfinger.manager.common.domain.RfRbcLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRbcLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "logId")
public class LogTaskService extends BaseService<RfRbcLog, RfRbcLogExample, RfRbcLogMapper> {

}
