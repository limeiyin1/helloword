package com.redfinger.manager.modules.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfPayMode;
import com.redfinger.manager.common.domain.RfPayModeExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPayModeMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="payModeCode")
public class PayModeService extends BaseService<RfPayMode, RfPayModeExample, RfPayModeMapper>{

}
