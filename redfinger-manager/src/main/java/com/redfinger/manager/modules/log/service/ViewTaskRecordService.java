package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ViewTaskRecord;
import com.redfinger.manager.common.domain.ViewTaskRecordExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ViewTaskRecordMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field = "recordId")
public class ViewTaskRecordService extends BaseService<ViewTaskRecord, ViewTaskRecordExample,ViewTaskRecordMapper> {

}
