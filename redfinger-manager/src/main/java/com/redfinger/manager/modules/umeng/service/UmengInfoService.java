package com.redfinger.manager.modules.umeng.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.PushUmengInfoLog;
import com.redfinger.manager.common.domain.PushUmengInfoLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.PushUmengInfoLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class UmengInfoService  extends BaseService<PushUmengInfoLog, PushUmengInfoLogExample, PushUmengInfoLogMapper> {

	
}
