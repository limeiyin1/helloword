package com.redfinger.manager.modules.base.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ResIp;
import com.redfinger.manager.common.domain.ResIpExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ResIpMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field = "ip")
public class ResIpService extends BaseService<ResIp, ResIpExample,ResIpMapper> {

}
