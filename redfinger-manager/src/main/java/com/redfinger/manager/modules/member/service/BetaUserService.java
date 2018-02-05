package com.redfinger.manager.modules.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfBetaUser;
import com.redfinger.manager.common.domain.RfBetaUserExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfBetaUserMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "betaUserId")
public class BetaUserService  extends BaseService<RfBetaUser, RfBetaUserExample, RfBetaUserMapper> {

}
