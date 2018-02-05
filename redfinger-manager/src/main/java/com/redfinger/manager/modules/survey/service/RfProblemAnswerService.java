package com.redfinger.manager.modules.survey.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfProblemAnswer;
import com.redfinger.manager.common.domain.RfProblemAnswerExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfProblemAnswerMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "answerId")
public class RfProblemAnswerService extends BaseService<RfProblemAnswer, RfProblemAnswerExample, RfProblemAnswerMapper> {

}
