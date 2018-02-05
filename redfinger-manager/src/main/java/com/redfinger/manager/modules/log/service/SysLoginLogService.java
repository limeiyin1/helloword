package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysLoginLog;
import com.redfinger.manager.common.domain.SysLoginLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.SysLoginLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "logId")
public class SysLoginLogService extends BaseService<SysLoginLog, SysLoginLogExample, SysLoginLogMapper> {

}
