package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfVersionLog;
import com.redfinger.manager.common.domain.RfVersionLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfVersionLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="id")
public class LogVersionService  extends BaseService<RfVersionLog,RfVersionLogExample,RfVersionLogMapper>{

}
