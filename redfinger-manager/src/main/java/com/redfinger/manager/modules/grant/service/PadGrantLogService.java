package com.redfinger.manager.modules.grant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfPadGrantLog;
import com.redfinger.manager.common.domain.RfPadGrantLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPadGrantLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "grantCodeId")
public class PadGrantLogService  extends BaseService<RfPadGrantLog, RfPadGrantLogExample, RfPadGrantLogMapper> {

}
