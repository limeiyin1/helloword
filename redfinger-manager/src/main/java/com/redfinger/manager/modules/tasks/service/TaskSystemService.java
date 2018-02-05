package com.redfinger.manager.modules.tasks.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TaskSystem;
import com.redfinger.manager.common.domain.TaskSystemExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TaskSystemMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class TaskSystemService extends BaseService<TaskSystem, TaskSystemExample, TaskSystemMapper> {
	
}
