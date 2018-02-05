package com.redfinger.manager.modules.refund.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfRefundAdmin;
import com.redfinger.manager.common.domain.RfRefundAdminExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRefundAdminMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="refundAdminId")
public class RefundAdminService  extends BaseService<RfRefundAdmin, RfRefundAdminExample, RfRefundAdminMapper> {

}
