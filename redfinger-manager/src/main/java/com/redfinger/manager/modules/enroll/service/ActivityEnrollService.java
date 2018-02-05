package com.redfinger.manager.modules.enroll.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfActivityEnroll;
import com.redfinger.manager.common.domain.RfActivityEnrollExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfActivityEnrollMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "enrollId")
public class ActivityEnrollService extends BaseService<RfActivityEnroll, RfActivityEnrollExample, RfActivityEnrollMapper> {

}
