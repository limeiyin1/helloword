package com.redfinger.manager.modules.stat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppApk;
import com.redfinger.manager.common.domain.AppApkExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.StatGoodCommentMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.utils.StatUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class StatGoodCommentService extends
		BaseService<AppApk, StatGoodCommentMapper, AppApkExample> {

	@Autowired
	StatGoodCommentMapper mapper;

	@Transactional(readOnly = true)
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
		List<StatDomain> list = mapper.statGoodComment(bean);

		int countAll = 0;// 总数统计
		for (StatDomain ss : list) {
			countAll += ss.getNumber();
		}

		// 补全数据
		Map<String, Object> map = StatUtils.autoStatDate(bean.getType(),
				bean.getBegin(), bean.getEnd(), list);
		map.put("numAll", countAll);
		return map;

	}
}
