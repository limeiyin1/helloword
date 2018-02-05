package com.redfinger.manager.modules.base.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfArea;
import com.redfinger.manager.common.domain.RfAreaExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfAreaMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="id")
public class AreaService extends BaseService<RfArea, RfAreaExample, RfAreaMapper> {

}
