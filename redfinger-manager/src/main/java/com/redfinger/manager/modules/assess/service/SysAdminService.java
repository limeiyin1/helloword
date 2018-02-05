package com.redfinger.manager.modules.assess.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.SysAdminExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.SysAdminMapper;

/**
 * 后台管理员信息Service
 * 
 * @ClassName: CopyOfSysOrgService
 * @author tluo
 * @date 2016年3月24日 上午11:06:45
 */
@Transactional
@Service
@PrimaryKeyAnnotation(field = "orgCode")
public class SysAdminService extends BaseService<SysAdmin, SysAdminExample, SysAdminMapper> {
}
