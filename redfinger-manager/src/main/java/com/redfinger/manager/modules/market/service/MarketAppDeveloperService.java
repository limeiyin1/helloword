package com.redfinger.manager.modules.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppDeveloper;
import com.redfinger.manager.common.domain.AppDeveloperExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppDeveloperMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketAppDeveloperService extends
		BaseService<AppDeveloper, AppDeveloperExample, AppDeveloperMapper> {
	@Autowired
	AppDeveloperMapper mapper;

	public boolean isExist(String name) { // 判断发行商是否已经存在
		AppDeveloperExample example = new AppDeveloperExample();
		AppDeveloperExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		AppDeveloper developer = mapper.selectOneByExample(example);
		if (developer == null) {
			return true;
		} else {
			throw new BusinessException("此发行商已经存在！");
		}
	}
}
