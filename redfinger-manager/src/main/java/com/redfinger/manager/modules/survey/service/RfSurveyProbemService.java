package com.redfinger.manager.modules.survey.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfSurveyProbem;
import com.redfinger.manager.common.domain.RfSurveyProbemExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSurveyProbemMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class RfSurveyProbemService extends BaseService<RfSurveyProbem, RfSurveyProbemExample, RfSurveyProbemMapper> {

}
