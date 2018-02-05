package com.redfinger.manager.modules.tasks.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TaskRecord;
import com.redfinger.manager.common.domain.TaskRecordExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TaskRecordMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="recordId")
public class TaskRecordService extends BaseService<TaskRecord, TaskRecordExample, TaskRecordMapper> {

}
