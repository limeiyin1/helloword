package com.redfinger.manager.modules.tasks.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfTaskUser;
import com.redfinger.manager.common.domain.RfTaskUserExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfTaskUserMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "taskUserId")
public class RfTaskUserService extends BaseService<RfTaskUser, RfTaskUserExample, RfTaskUserMapper> {
	
}
