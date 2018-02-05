package com.redfinger.manager.modules.survey.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfSurvey;
import com.redfinger.manager.common.domain.RfSurveyExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSurveyMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "surveyId")
public class RfSurveyService extends BaseService<RfSurvey, RfSurveyExample, RfSurveyMapper> {

}
