package com.redfinger.manager.modules.stat.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfFaultFeedback;
import com.redfinger.manager.common.domain.RfFaultFeedbackExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfFaultFeedbackMapper;
import com.redfinger.manager.common.mapper.StatFaultMapper;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.stat.base.StatDomain;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "faultFeedbackId")
public class StatFaultFeedbackService extends BaseService<RfFaultFeedback, RfFaultFeedbackExample, RfFaultFeedbackMapper> {

	@Autowired
	StatFaultMapper mapper;
	@Autowired
	ClassService classService;

	@Transactional(readOnly = true)
	public Map<String, Object> statFaultCategory(StatDomain bean) {
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		List<StatDomain> list = mapper.statFaultCategory(bean);
		for (StatDomain domain : list) {
			number.add(domain.getNumber());
			if(StringUtils.isNotEmpty(domain.getLabel())){
				label.add(classService.get(Integer.parseInt(domain.getLabel())).getClassName());
			}else{
				label.add("[未知]");
			}
			
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> statFaultCategoryFix(StatDomain bean) {
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		List<StatDomain> list = mapper.statFaultCategoryFix(bean);
		for (StatDomain domain : list) {
			number.add(domain.getNumber());
			if(StringUtils.isNotEmpty(domain.getLabel())){
				label.add(classService.get(Integer.parseInt(domain.getLabel())).getClassName());
			}else{
				label.add("[未知]");
			}
		}
		return map;
	}

	
}
