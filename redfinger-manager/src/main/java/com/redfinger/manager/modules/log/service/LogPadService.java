package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.LogPad;
import com.redfinger.manager.common.domain.LogPadExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.LogPadMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class LogPadService extends BaseService<LogPad, LogPadExample, LogPadMapper> {

}
