package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ViewLogQixiActivity;
import com.redfinger.manager.common.domain.ViewLogQixiActivityExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ViewLogQixiActivityMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="")
public class LogQiXiActivityService extends BaseService<ViewLogQixiActivity, ViewLogQixiActivityExample, ViewLogQixiActivityMapper> {
	
	
}













