package com.redfinger.manager.modules.stat.service;

import java.util.Date;
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
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfFaultFeedbackMapper;
import com.redfinger.manager.common.mapper.StatGameDownloadMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.stat.base.StatDomain;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class StatGameDownLoadService extends BaseService<RfFaultFeedback, RfFaultFeedbackExample, RfFaultFeedbackMapper>{
	
	@Autowired
	StatGameDownloadMapper mapper;
	@Autowired
	ClassService classService;
	
	@Transactional(readOnly = true)
	public Map<String, Object> statGameDown(StatDomain bean) {
		this.validate(bean);
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		
		Integer all = 0;
		Integer x=0;
		List<StatDomain> list = mapper.selectGameCount(bean);
		for(int i=0;i<list.size();i++){
			all+=list.get(i).getNumber();
			if(i<=14){
				label.add(list.get(i).getLabel());
				number.add(list.get(i).getNumber());
			}else{
				x+=list.get(i).getNumber();
			}
		}
		if(list.size()>15){
		label.add("其他");
		number.add(x);
		}
		
		/*for (StatDomain domain : list) {
			all+=domain.getNumber();
			if(domain.getNumber()>10)
			{
				label.add(domain.getLabel());
				number.add(domain.getNumber());
			}else{
				x+=domain.getNumber();
			}
			
		}*/
		map.put("all", all);
		return map;
	}
	
	
	public void validate(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
	}
	
}
