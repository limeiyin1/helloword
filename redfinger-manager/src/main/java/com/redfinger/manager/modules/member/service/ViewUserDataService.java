package com.redfinger.manager.modules.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.ViewUserData;
import com.redfinger.manager.common.domain.ViewUserDataExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.ViewUserDataMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="id")
public class ViewUserDataService extends BaseService<ViewUserData, ViewUserDataExample, ViewUserDataMapper> {

}
