package com.redfinger.manager.modules.log.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfPadTask;
import com.redfinger.manager.common.domain.RfPadTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;

import com.redfinger.manager.common.mapper.RfPadTaskMapper;



@Transactional
@Service
@PrimaryKeyAnnotation(field = "taskId")
public class PadTaskService extends BaseService<RfPadTask, RfPadTaskExample, RfPadTaskMapper> {

}
