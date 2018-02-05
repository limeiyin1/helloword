package com.redfinger.manager.modules.stat.service;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.StatRfUserMapper;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.utils.StatUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "userId")
public class StatMemberCreatimeService {

	@Autowired
	StatRfUserMapper mapper;

	@Transactional(readOnly = true)
	public Map<String, Object> statMemberCreatime(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType())
				|| StringUtils.isEmpty(bean.getBegin())
				|| StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		List<StatDomain> date_list = mapper.statUserLogin2CreateTime(bean);
		Map<String, Object> map = StatUtils.autoStatDate(bean.getType(),
				bean.getBegin(), bean.getEnd(), date_list);
		int countAll = 0;// 总数统计
		for (StatDomain ss : date_list) {
			countAll += ss.getNumber();
		}
		map.put("numAll", countAll);
		return map;
	}
}
