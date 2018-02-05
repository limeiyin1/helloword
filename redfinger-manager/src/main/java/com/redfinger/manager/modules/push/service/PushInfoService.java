package com.redfinger.manager.modules.push.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.PushInfo;
import com.redfinger.manager.common.domain.PushInfoExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.PushInfoMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="id")
public class PushInfoService extends BaseService<PushInfo,PushInfoExample,PushInfoMapper> {

}
