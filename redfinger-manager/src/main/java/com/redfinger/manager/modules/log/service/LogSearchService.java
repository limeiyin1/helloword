package com.redfinger.manager.modules.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;


import com.redfinger.manager.common.domain.AppLogSearch;
import com.redfinger.manager.common.domain.AppLogSearchExample;

import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;

import com.redfinger.manager.common.mapper.AppLogSearchMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class LogSearchService extends BaseService<AppLogSearch,AppLogSearchExample,AppLogSearchMapper>{

}
