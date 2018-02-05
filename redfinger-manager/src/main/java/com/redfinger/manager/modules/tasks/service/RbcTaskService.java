package com.redfinger.manager.modules.tasks.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfRbcTask;
import com.redfinger.manager.common.domain.RfRbcTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRbcTaskMapper;
/**
 * 
* @ClassName: RbcTaskService
* @Description: 红豆任务表的service
* @author tluo
* @date 2015年9月23日 下午4:58:39
*
 */
@Transactional
@Service
@PrimaryKeyAnnotation(field = "taskId")
public class RbcTaskService extends BaseService<RfRbcTask, RfRbcTaskExample, RfRbcTaskMapper> {

}