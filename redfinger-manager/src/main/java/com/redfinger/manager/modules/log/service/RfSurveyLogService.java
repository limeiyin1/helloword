package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfSurveyLog;
import com.redfinger.manager.common.domain.RfSurveyLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSurveyLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "logId")
public class RfSurveyLogService extends BaseService<RfSurveyLog, RfSurveyLogExample, RfSurveyLogMapper> {

}
