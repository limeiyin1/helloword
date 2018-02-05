package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.LogWechartOperate;
import com.redfinger.manager.common.domain.LogWechartOperateExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.LogWechartOperateMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class LogWechartOperateService extends
		BaseService<LogWechartOperate, LogWechartOperateExample, LogWechartOperateMapper> {

}
