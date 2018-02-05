package com.redfinger.manager.modules.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfSmsBatch;
import com.redfinger.manager.common.domain.RfSmsBatchExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSmsBatchMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class SmsBatchService extends BaseService<RfSmsBatch, RfSmsBatchExample, RfSmsBatchMapper> {

	
}
