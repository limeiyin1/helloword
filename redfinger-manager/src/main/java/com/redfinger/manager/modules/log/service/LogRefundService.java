package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfRefundLog;
import com.redfinger.manager.common.domain.RfRefundLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRefundLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "refundLogId")
public class LogRefundService extends BaseService<RfRefundLog,RfRefundLogExample,RfRefundLogMapper>{

}
