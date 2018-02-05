package com.redfinger.manager.modules.stat.service;

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
import com.redfinger.manager.common.mapper.StatLoginOneMapper;
import com.redfinger.manager.modules.stat.base.StatDomain;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class StatLoginOneService {
	
	@Autowired
	StatLoginOneMapper mapper;

	@Transactional(readOnly=true)
	public Map<String, Object> stat(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getEnd()) ) {
			throw new BusinessException("查询条件不能为空！");
		}
		
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		map.put("number", number);
		map.put("label", label);
		
		List<StatDomain> list=mapper.statLoginOne(bean);
		List<StatDomain> count=mapper.countAll(bean);
		label.add("登陆一次用户");
		number.add(list.get(0).getNumber());
		label.add("其他用户");
		number.add(count.get(0).getNumber()-list.get(0).getNumber());
		map.put("all", count.get(0).getNumber());
		return map;
	}

}
