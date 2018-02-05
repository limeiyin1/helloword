package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.LogAwardRecord;
import com.redfinger.manager.common.domain.LogAwardRecordExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.LogAwardRecordMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class LogAwardRecordService extends BaseService<LogAwardRecord, LogAwardRecordExample, LogAwardRecordMapper> {

}
