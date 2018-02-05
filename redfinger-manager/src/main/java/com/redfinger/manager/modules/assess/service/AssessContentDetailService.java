package com.redfinger.manager.modules.assess.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AssessContentDetail;
import com.redfinger.manager.common.domain.AssessContentDetailExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AssessContentDetailMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")//这里的field要与对应类中的主键名相同
public class AssessContentDetailService extends BaseService<AssessContentDetail, AssessContentDetailExample, AssessContentDetailMapper> {

}
