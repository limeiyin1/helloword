package com.redfinger.manager.modules.assess.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AssessProjectOption;
import com.redfinger.manager.common.domain.AssessProjectOptionExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AssessProjectOptionMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class AssessProjectOptionService extends BaseService<AssessProjectOption, AssessProjectOptionExample, AssessProjectOptionMapper>{

}
