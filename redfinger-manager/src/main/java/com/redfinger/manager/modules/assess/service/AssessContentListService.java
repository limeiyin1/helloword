package com.redfinger.manager.modules.assess.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AssessContentList;
import com.redfinger.manager.common.domain.AssessContentListExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AssessContentListMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class AssessContentListService extends BaseService<AssessContentList, AssessContentListExample, AssessContentListMapper>{

}
