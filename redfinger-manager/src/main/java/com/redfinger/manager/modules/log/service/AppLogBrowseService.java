package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppLogBrowse;
import com.redfinger.manager.common.domain.AppLogBrowseExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppLogBrowseMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="id")
public class AppLogBrowseService extends BaseService<AppLogBrowse,AppLogBrowseExample,AppLogBrowseMapper>{
	
}
