package com.redfinger.manager.modules.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGroup;
import com.redfinger.manager.common.domain.RfGroupExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGroupMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "groupId")
public class GroupService  extends BaseService<RfGroup, RfGroupExample, RfGroupMapper> {

}
