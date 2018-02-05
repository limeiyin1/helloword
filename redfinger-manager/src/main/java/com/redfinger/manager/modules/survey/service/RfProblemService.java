package com.redfinger.manager.modules.survey.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfProblem;
import com.redfinger.manager.common.domain.RfProblemExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfProblemMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "problemId")
public class RfProblemService extends BaseService<RfProblem, RfProblemExample, RfProblemMapper> {

}
