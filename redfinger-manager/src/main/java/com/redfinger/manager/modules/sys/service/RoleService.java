package com.redfinger.manager.modules.sys.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;

import com.redfinger.manager.common.domain.SysRole;
import com.redfinger.manager.common.domain.SysRoleExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.SysRoleMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field = "roleCode")
//角色
public class RoleService extends BaseService<SysRole,SysRoleExample,SysRoleMapper> {
	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, SysRole bean) {
		SysRole sysRole=this.initQuery().get(bean.getRoleCode());
		if(sysRole!=null){
			return "该角色编号已存在";
		}
		return null;
		
	}

}
