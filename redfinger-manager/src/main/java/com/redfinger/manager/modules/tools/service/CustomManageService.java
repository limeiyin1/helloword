package com.redfinger.manager.modules.tools.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfCustom;
import com.redfinger.manager.common.domain.RfCustomExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfCustomMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="customId")
public class CustomManageService extends BaseService<RfCustom, RfCustomExample, RfCustomMapper> {
}
