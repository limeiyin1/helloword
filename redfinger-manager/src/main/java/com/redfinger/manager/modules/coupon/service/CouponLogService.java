package com.redfinger.manager.modules.coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfCouponLog;
import com.redfinger.manager.common.domain.RfCouponLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfCouponLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="logId")
public class CouponLogService  extends BaseService<RfCouponLog, RfCouponLogExample, RfCouponLogMapper>{

}
