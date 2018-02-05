package com.redfinger.manager.modules.wechart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfWechartUser;
import com.redfinger.manager.common.domain.RfWechartUserExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfWechartUserMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="id")
public class WechartUserService extends BaseService<RfWechartUser, RfWechartUserExample, RfWechartUserMapper> {

	@Autowired
	RfWechartUserMapper mapper;
	
	public int getCountByUserId(Integer userId){
		RfWechartUserExample example = new RfWechartUserExample();
		example.createCriteria().andStatusEqualTo(YesOrNoStatus.YES)
		.andEnableStatusEqualTo(YesOrNoStatus.YES).andUserIdEqualTo(userId);
		return mapper.countByExample(example);
	}
	
}
