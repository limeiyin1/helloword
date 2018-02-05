package com.redfinger.manager.modules.stat.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.StatSysLoginLogMapper;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.utils.StatUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "userId")
public class StatMemberLoginCountService {

	@Autowired
	StatSysLoginLogMapper mapper;

	@Transactional(readOnly = true)
	public Map<String, Object> statMemberLoginCount2Logintime(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}

		Map<String, List<StatDomain>> m_date = Maps.newHashMap();
		
		m_date.put("pc", null);
		m_date.put("android", null);
		m_date.put("", null);
		m_date.put("1", null);
		
		for(Entry<String, List<StatDomain>> entry : m_date.entrySet()){
			String client_type = entry.getKey();
			bean.setWhere("client_type='"+client_type+"'");
			if("".equals(client_type)){
				bean.setWhere("client_type is null or client_type=''");
			}
			if("1".equals(client_type)){
				bean.setWhere("1=1");
			}
			List<StatDomain> date_list = mapper.selectClientType2LoginTime(bean);
			entry.setValue(date_list);
		}
		Map<String, Object> map = StatUtils.autoStatDate(bean.getType(), bean.getBegin(),bean.getEnd(),
				m_date.get("pc"),m_date.get("android"),m_date.get(""),m_date.get("1"));
		int countAll = 0;//总数统计
		for(StatDomain ss :m_date.get("1")){
			countAll+=ss.getNumber();
		}
		map.put("numAll", countAll);
	return map;	
	}
}
