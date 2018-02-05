package com.redfinger.manager.modules.facility.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfIdcExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfIdcMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "idcId")
public class IdcService extends BaseService<RfIdc, RfIdcExample, RfIdcMapper> {

}
