package com.redfinger.manager.modules.base.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ResPhone;
import com.redfinger.manager.common.domain.ResPhoneExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ResPhoneMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field = "phone")
public class ResPhonService extends BaseService<ResPhone, ResPhoneExample, ResPhoneMapper> {

}
