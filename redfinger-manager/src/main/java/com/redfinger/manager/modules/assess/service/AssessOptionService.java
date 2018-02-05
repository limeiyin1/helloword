package com.redfinger.manager.modules.assess.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AssessOption;
import com.redfinger.manager.common.domain.AssessOptionExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AssessOptionMapper;

@Service
@Transactional
@PrimaryKeyAnnotation(field="id")
public class AssessOptionService extends BaseService<AssessOption, AssessOptionExample, AssessOptionMapper>{

}
