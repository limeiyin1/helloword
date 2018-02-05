package com.redfinger.manager.modules.tasks.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TaskAttendance;
import com.redfinger.manager.common.domain.TaskAttendanceExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TaskAttendanceMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="attendanceId")
public class TaskAttendanceService extends BaseService<TaskAttendance, TaskAttendanceExample, TaskAttendanceMapper> {

}
