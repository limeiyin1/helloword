package com.redfinger.manager.modules.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppDeveloper;
import com.redfinger.manager.common.domain.AppDeveloperExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppDeveloperMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GameAppDeveloperService extends BaseService<AppDeveloper, AppDeveloperExample, AppDeveloperMapper>{

}
