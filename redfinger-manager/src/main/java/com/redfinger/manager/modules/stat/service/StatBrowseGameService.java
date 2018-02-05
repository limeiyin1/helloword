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
import com.redfinger.manager.common.domain.AppLogBrowse;
import com.redfinger.manager.common.domain.AppLogBrowseExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.StatBrowseGameMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class StatBrowseGameService extends BaseService<AppLogBrowse, AppLogBrowseExample, StatBrowseGameMapper> {

	@Autowired
	StatBrowseGameMapper mapper;

	@Transactional(readOnly = true)
	public Map<String, Object> stata(StatDomain bean) {
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
		List<StatDomain> list = mapper.statGame(bean);
		Map<String, Object> maps = Maps.newHashMap();
		List<Integer> numberAll = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		maps.put("number", numberAll);
		maps.put("label", label);
		int all = 0;// 总数统计
		Integer x=0;
		for(int i=0;i<list.size();i++){
			all+=list.get(i).getNumber();
			if(i<=14){
				label.add(list.get(i).getLabel());
				numberAll.add(list.get(i).getNumber());
			}else{
				x+=list.get(i).getNumber();
			}
		}
		if(list.size()>15){
		label.add("其他");
		numberAll.add(x);
		}
		maps.put("all", all);
		return maps;

	}
}
