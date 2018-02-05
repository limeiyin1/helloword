package com.redfinger.manager.modules.stat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.StatLogSearchCountMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.utils.StatUtils;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class StatLogSearchCountService {

	@Autowired
	StatLogSearchCountMapper mapper;
	
	@Transactional(readOnly = true)
	public Map<String, Object> statAppLogSearchConut(StatDomain bean) {
	    	this.validate(bean);
		List<StatDomain> list = mapper.selectCount(bean);
		Map<String, Object> map =  StatUtils.autoStatDate(bean.getType(),bean.getBegin(),bean.getEnd(), list);
		map.put("all",list.size());
		return map;	
	}
	
	public void validate(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
		if (bean.getType().equals("month")) {
			if (DateUtils.addYears(beginDate, 1).before(endDate)) {
				throw new BusinessException("月统计，时段不能超过1年！");
			}
		} else if (bean.getType().equals("week")) {
			if (DateUtils.addMonths(beginDate, 6).before(endDate)) {
				throw new BusinessException("周统计，时段不能超过半年！");
			}
		} else if (bean.getType().equals("day")) {
			if (DateUtils.addMonths(beginDate, 1).before(endDate)) {
				throw new BusinessException("天统计，时段不能超过1个月！");
			}
		}
	}
}
