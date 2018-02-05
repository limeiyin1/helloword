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
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.StatBleedUserMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;

/**
 * 流失用户统计
 * 在选定时间之内没有登录的为流失用户
 * @author admin
 *
 */
@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class StatBleedUserService {
	
	@Autowired
	StatBleedUserMapper mapper;

	@Transactional(readOnly=true)
	public Map<String, Object> stat(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType())
				|| StringUtils.isEmpty(bean.getBegin())
				|| StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("number", number);
		map.put("label", label);
		
		
		
		//流失用户，获取选定时间之前登录的用户
		List<StatDomain> list=mapper.statBleedUser(bean);
		Integer bleedCount=list.get(0).getNumber();
		//用户总数
		List<StatDomain> countList=mapper.countAll(bean);
		Integer count=countList.get(0).getNumber();
		
		label.add("流失用户");
		number.add(bleedCount);
		label.add("其他用户");
		number.add(count-bleedCount);
		map.put("all", count);
		return map;
	}

}
