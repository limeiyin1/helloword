package com.redfinger.manager.modules.stat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfProblemAnswer;
import com.redfinger.manager.common.domain.RfSurveyLog;
import com.redfinger.manager.common.domain.RfSurveyLogExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSurveyLogMapper;
import com.redfinger.manager.common.mapper.StatRfSurveyLogMapper;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.survey.service.RfProblemAnswerService;
import com.redfinger.manager.modules.survey.service.RfProblemService;
import com.redfinger.manager.modules.survey.service.RfSurveyProbemService;
import com.redfinger.manager.modules.survey.service.RfSurveyService;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "logId")
public class StatRfSurveyLogService extends
		BaseService<RfSurveyLog, RfSurveyLogExample, RfSurveyLogMapper> {
	@Autowired
	StatRfSurveyLogMapper mapper;
	@Autowired
	RfSurveyService surveyService;
	@Autowired
	RfSurveyProbemService surveyProbemService;
	@Autowired
	RfProblemService problemService;
	@Autowired
	RfProblemAnswerService problemAnswerService;

	@Transactional(readOnly = true)
	public Map<String, Object> statSurveyLog2ProblemAnswer(RfSurveyLog bean) {
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> numberList = Lists.newArrayList();
		List<String> labelList = Lists.newArrayList();
		map.put("subTitle",
				problemService.initQuery()
						.andEqualTo("problemId", bean.getProblemId())
						.singleStopTrue().get(0).getProblemContent());
		map.put("number", numberList);
		map.put("label", labelList);
		if (bean.getProblemId() == null || bean.getSurveyId() == null) {
			return map;
		}
		List<RfProblemAnswer> PAList = problemAnswerService.initQuery()
				.andEqualTo("problemId", bean.getProblemId()).findStopTrue();
		if (PAList.size() == 0) {
			throw new BusinessException("问题不存在可选答案！");
		}
		// 根据答案统计问卷问题  float  b   =  (float)(Math.round(a*100))/100
		float all = 0;
		for (RfProblemAnswer pa : PAList) {
			bean.setAnswerId(pa.getAnswerId());
			StatDomain s = mapper.StatSurveyLog(bean);
			all+=s.getNumber();
		}
		for (RfProblemAnswer pa : PAList) {
			String label = pa.getAnswerContent();
			bean.setAnswerId(pa.getAnswerId());
			StatDomain s = mapper.StatSurveyLog(bean);
			int number = s.getNumber();
			numberList.add(number);
			labelList.add(label+" : "+number+"——"+(float)(Math.round((number/all)*10000))/100+"%");
		}
		return map;
	}
}
