package com.redfinger.manager.modules.sys.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysMenu;
import com.redfinger.manager.common.domain.SysRole;
import com.redfinger.manager.common.domain.SysRoleMenu;
import com.redfinger.manager.common.domain.SysRoleMenuExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.SysRoleMenuMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "roleAuthCode")
//角色——目录
public class RoleMenuService extends BaseService<SysRoleMenu,SysRoleMenuExample, SysRoleMenuMapper> {
	@Autowired
	MenuService menuService;
	
	public void insert(HttpServletRequest request,SysRole bean,String permissionIds) throws Exception {
		//先删除
		
		List<SysRoleMenu> removeList = this.initQuery().andEqualTo("roleCode", bean.getRoleCode()).findAll();
		
		for(SysRoleMenu sysRoleMenu : removeList){
			this.remove(request, sysRoleMenu.getId());
		}
		//再新增
		Set<String> result=Sets.newHashSet();
		List<String> permissions =Lists.newArrayList(StringUtils.split(permissionIds, ","));
		for(String id:permissions){
			result.add(id);
			this.getPermissionParentKeey(id, result);
		}
		for(String id:result){
			SysRoleMenu rolePermission = new SysRoleMenu();
			rolePermission.setMenuCode(id);
			rolePermission.setRoleCode(bean.getRoleCode());
			this.save(request, rolePermission);
		}
	}
	
	/**** custom ****/
	
	@Transactional(readOnly=true)
	private void getPermissionParentKeey(String id,Set<String> list){
		SysMenu per = menuService.get(id);
		if(per!=null&& per.getMenuLayer()!=1 ){
			SysMenu parent = menuService.get(per.getParentMenuCode());
			list.add(parent.getMenuCode());
			this.getPermissionParentKeey(parent.getMenuCode(), list);
		}
	}
}
