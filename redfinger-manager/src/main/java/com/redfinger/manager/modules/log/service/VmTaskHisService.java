package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfVmTaskHis;
import com.redfinger.manager.common.domain.RfVmTaskHisExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfVmTaskHisMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "vmTaskId")
public class VmTaskHisService extends BaseService<RfVmTaskHis, RfVmTaskHisExample, RfVmTaskHisMapper> {

}
