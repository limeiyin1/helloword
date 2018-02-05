package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.LogAdmin;
import com.redfinger.manager.common.domain.LogAdminExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.LogAdminMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class LogAdminService extends BaseService<LogAdmin, LogAdminExample, LogAdminMapper> {

}
