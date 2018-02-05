package com.redfinger.manager.modules.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfOrder;
import com.redfinger.manager.common.domain.RfOrderExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfOrderMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="orderId")
public class OrderService extends BaseService<RfOrder, RfOrderExample, RfOrderMapper> {

}
