package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ViewLogActivity;
import com.redfinger.manager.common.domain.ViewLogActivityExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ViewLogActivityMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="")
public class LogActivityService extends BaseService<ViewLogActivity, ViewLogActivityExample, ViewLogActivityMapper> {
	
	
}













