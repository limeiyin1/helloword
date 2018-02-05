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
import com.redfinger.manager.common.domain.LogPad;
import com.redfinger.manager.common.domain.LogPadExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.LogPadMapper;
import com.redfinger.manager.common.mapper.StatLogPadMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.utils.StatUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class PadBindService extends BaseService<LogPad, LogPadExample, LogPadMapper> {

	@Autowired
	StatLogPadMapper mapper;

	@Transactional(readOnly = true)
	public Map<String, Object> stat(StatDomain bean) {
		this.validate(bean);
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		List<StatDomain> list = mapper.statNumber(bean);
		for (StatDomain domain : list) {
			number.add(domain.getNumber());
			label.add(domain.getLabel());
		}
		// 补全数据
		StatUtils.transferDateResultForNumber(bean, map,"label","number");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> alreadyBind(StatDomain bean) {
		this.validate(bean);
		bean.setWhere("category ='bind'");
		Map<String, Object> bindMap = this.stat(bean);
		bean.setWhere("category ='relieve'");
		Map<String, Object> relieveMap = this.stat(bean);
		List<Integer> bindNumber=(List<Integer>)bindMap.get("number");
		List<Integer> relieveNumber=(List<Integer>)relieveMap.get("number");
		Integer beforeBind = mapper.getBindNumberByEndDate(DateUtils.parseDate(bean.getBegin()));
		if(beforeBind==null){
			beforeBind=0;
		}
		for(int i=0;i<bindNumber.size();i++){
			beforeBind=beforeBind+bindNumber.get(i)-relieveNumber.get(i);
			bindNumber.remove(i);
			bindNumber.add(i, beforeBind);
		}
		return bindMap;
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
