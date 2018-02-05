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
import com.redfinger.manager.common.domain.SysLoginLog;
import com.redfinger.manager.common.domain.SysLoginLogExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.StatSysLoginLogMapper;
import com.redfinger.manager.common.mapper.SysLoginLogMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.utils.StatUtils;
@Transactional
@Service
@PrimaryKeyAnnotation(field = "logId")
public class StatLoginUserLogService extends BaseService<SysLoginLog, SysLoginLogExample, SysLoginLogMapper> {
	@Autowired
	StatSysLoginLogMapper mapper;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> stat(StatDomain bean) {
		this.validate(bean);
		Integer count=0;
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		List<Integer> number1 = Lists.newArrayList();
		List<String> label1 = Lists.newArrayList();
		List<Integer> number2 = Lists.newArrayList();
		List<String> label2 = Lists.newArrayList();
		List<Integer> number3 = Lists.newArrayList();
		List<String> label3 = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		map.put("number1", number1);
		map.put("label1", label1);
		map.put("number2", number2);
		map.put("label2", label2);
		map.put("number3", number3);
		map.put("label3", label3);
		List<StatDomain> list = mapper.selectCountAndroid(bean);
		for (StatDomain domain : list) {
			number.add(domain.getNumber());
			label.add(domain.getLabel());
		}
		// 补全数据
		StatUtils.transferDateResultForNumber(bean, map, "label", "number");
		List<StatDomain> list1 = mapper.selectCountPc(bean);
		for (StatDomain domain : list1) {
			number1.add(domain.getNumber());
			label1.add(domain.getLabel());
		}
		StatUtils.transferDateResultForNumber(bean, map, "label1", "number1");
		List<StatDomain> list2 = mapper.selectCount(bean);
		for (StatDomain domain : list2) {
			count+=domain.getNumber();
			number2.add(domain.getNumber());
			label2.add(domain.getLabel());
		}
		StatUtils.transferDateResultForNumber(bean, map, "label2", "number2");
		List<StatDomain> list3 = mapper.selectCountNull(bean);
		for (StatDomain domain : list3) {
			number3.add(domain.getNumber());
			label3.add(domain.getLabel());
		}
		StatUtils.transferDateResultForNumber(bean, map, "label3", "number3");
		((List<Integer>) map.get("number")).addAll((List<Integer>) map.get("number1"));
		((List<Integer>) map.get("number")).addAll((List<Integer>) map.get("number3"));
		((List<Integer>) map.get("number")).addAll((List<Integer>) map.get("number2"));
		// 移除MAP里不需要的KEY
		map.remove("number1");
		map.remove("label1");
		map.remove("number2");
		map.remove("label2");
		map.remove("number3");
		map.remove("label3");
		map.put("count", count);
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
