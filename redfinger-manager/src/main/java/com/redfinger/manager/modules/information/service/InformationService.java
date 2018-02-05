package com.redfinger.manager.modules.information.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfInformation;
import com.redfinger.manager.common.domain.RfInformationExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfInformationMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class InformationService  extends BaseService<RfInformation, RfInformationExample, RfInformationMapper> {

}
