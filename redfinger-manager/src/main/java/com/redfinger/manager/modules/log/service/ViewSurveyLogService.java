package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ViewSurveyLog;
import com.redfinger.manager.common.domain.ViewSurveyLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ViewSurveyLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "logId")
public class ViewSurveyLogService extends BaseService<ViewSurveyLog, ViewSurveyLogExample, ViewSurveyLogMapper> {

}
