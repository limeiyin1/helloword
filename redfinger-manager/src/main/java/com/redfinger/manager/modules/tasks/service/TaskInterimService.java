package com.redfinger.manager.modules.tasks.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TaskUserInterim;
import com.redfinger.manager.common.domain.TaskUserInterimExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TaskUserInterimMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class TaskInterimService extends BaseService<TaskUserInterim, TaskUserInterimExample, TaskUserInterimMapper> {

}
