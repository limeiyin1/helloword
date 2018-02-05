package com.redfinger.manager.modules.assess.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysOrg;
import com.redfinger.manager.common.domain.SysOrgExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.SysOrgMapper;

/**
 * 部门信息Service
 * 
 * @ClassName: SysOrgService
 * @author tluo
 * @date 2016年3月24日 上午10:40:48
 */
@Transactional
@Service
@PrimaryKeyAnnotation(field = "orgCode")
public class SysOrgService extends BaseService<SysOrg, SysOrgExample, SysOrgMapper> {
}
