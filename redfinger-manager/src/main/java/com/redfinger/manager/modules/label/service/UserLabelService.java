package com.redfinger.manager.modules.label.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfUserLabel;
import com.redfinger.manager.common.domain.RfUserLabelExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfUserLabelMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "userLabelId")
public class UserLabelService extends BaseService<RfUserLabel, RfUserLabelExample, RfUserLabelMapper> {

}
