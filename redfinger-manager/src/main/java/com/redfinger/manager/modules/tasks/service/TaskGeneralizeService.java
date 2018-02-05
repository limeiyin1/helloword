package com.redfinger.manager.modules.tasks.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TaskGeneralize;
import com.redfinger.manager.common.domain.TaskGeneralizeExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TaskGeneralizeMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="generalizeId")
public class TaskGeneralizeService extends BaseService<TaskGeneralize, TaskGeneralizeExample, TaskGeneralizeMapper> {

}
