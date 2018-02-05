package com.redfinger.manager.modules.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppApk;
import com.redfinger.manager.common.domain.AppApkExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppApkMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GameApkService extends BaseService<AppApk, AppApkExample,AppApkMapper> {

	
}
