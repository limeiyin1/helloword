package com.redfinger.manager.modules.facility.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfControlExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfControlMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "controlId")
public class ControlService extends BaseService<RfControl, RfControlExample, RfControlMapper> {

}
