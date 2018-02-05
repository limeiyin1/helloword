package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ViewPadTask;
import com.redfinger.manager.common.domain.ViewPadTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ViewPadTaskMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "taskId")
public class ViewPadTaskService extends BaseService<ViewPadTask, ViewPadTaskExample, ViewPadTaskMapper> {

}
