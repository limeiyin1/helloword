package com.redfinger.manager.modules.fault.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.gather.domain.RfClientNetinfo;
import com.redfinger.manager.common.gather.domain.RfClientNetinfoExample;
import com.redfinger.manager.common.gather.mapper.RfClientNetinfoMapper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;

@Transactional(value = "secondSource")
@Service
@PrimaryKeyAnnotation(field="netinfoId")
public class ClientNetInfoService extends BaseService<RfClientNetinfo, RfClientNetinfoExample, RfClientNetinfoMapper> {

}
