package com.redfinger.manager.modules.coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfCouponGoods;
import com.redfinger.manager.common.domain.RfCouponGoodsExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfCouponGoodsMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="couponGoodsId")
public class CouponGoodsService extends BaseService<RfCouponGoods, RfCouponGoodsExample, RfCouponGoodsMapper>{

}
