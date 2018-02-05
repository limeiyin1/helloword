package com.redfinger.manager.modules.refund.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfRefundStandard;
import com.redfinger.manager.common.domain.RfRefundStandardExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRefundStandardMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="standardId")
public class RefundStandardService  extends BaseService<RfRefundStandard, RfRefundStandardExample, RfRefundStandardMapper> {

}
