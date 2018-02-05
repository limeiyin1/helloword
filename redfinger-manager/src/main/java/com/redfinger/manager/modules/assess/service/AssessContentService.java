package com.redfinger.manager.modules.assess.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AssessContent;
import com.redfinger.manager.common.domain.AssessContentExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AssessContentMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class AssessContentService extends BaseService<AssessContent, AssessContentExample, AssessContentMapper> {
}
