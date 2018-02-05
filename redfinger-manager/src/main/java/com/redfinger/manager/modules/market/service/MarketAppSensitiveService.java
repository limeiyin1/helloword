package com.redfinger.manager.modules.market.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppSensitive;
import com.redfinger.manager.common.domain.AppSensitiveExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppSensitiveMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketAppSensitiveService extends BaseService<AppSensitive, AppSensitiveExample, AppSensitiveMapper> {

}
