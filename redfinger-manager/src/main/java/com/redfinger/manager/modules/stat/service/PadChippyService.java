package com.redfinger.manager.modules.stat.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfPadStatusLog;
import com.redfinger.manager.common.domain.RfPadStatusLogExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPadStatusLogMapper;
import com.redfinger.manager.common.mapper.StatRfPadStatusLogMapper;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.utils.StatUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "serialNo")
public class PadChippyService
		extends
		BaseService<RfPadStatusLog, RfPadStatusLogExample, RfPadStatusLogMapper> {

	@Autowired
	StatRfPadStatusLogMapper mapper;

	@Transactional(readOnly = true)
	public Map<String, Object> statChippyPad2Time(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType())
				|| StringUtils.isEmpty(bean.getWhere())
				|| StringUtils.isEmpty(bean.getBegin())
				|| StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		// 根据条件获取数据结果集
		List<StatDomain> date_list = mapper.statChippyPad(bean);
		int countAll = 0;// 总数统计
		for (StatDomain ss : date_list) {
			ss.setNumber(1);
			countAll++;
			
		}

		Map<String, Object> map = StatUtils.autoStatDate(bean.getType(),
				bean.getBegin(), bean.getEnd(), date_list);

		map.put("numAll", countAll);
		return map;
	}
}
