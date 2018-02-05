package com.redfinger.manager.modules.market.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfChannelGrade;
import com.redfinger.manager.common.domain.RfChannelGradeExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfChannelGradeMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "gradeId")
public class MarketChannelGradeService extends BaseService<RfChannelGrade, RfChannelGradeExample, RfChannelGradeMapper> {
	public  static final String FIRST_GRADE = "1";
	public  static final String SECOND_GRADE = "2";
	
	
	public  static final String FIRST_GRADE_NAME = "一级";
	public  static final String SECOND_GRADE_NAME = "二级";
	
}
