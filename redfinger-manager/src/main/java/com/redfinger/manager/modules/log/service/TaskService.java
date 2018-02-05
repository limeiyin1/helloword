package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfRbcTask;
import com.redfinger.manager.common.domain.RfRbcTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRbcTaskMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "taskId")
public class TaskService extends BaseService<RfRbcTask, RfRbcTaskExample, RfRbcTaskMapper> {

}
