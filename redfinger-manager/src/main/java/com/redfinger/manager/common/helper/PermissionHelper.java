package com.redfinger.manager.common.helper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.SysPermission;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.SysAdminRole;
import com.redfinger.manager.common.domain.SysMenu;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.sys.service.AdminRoleService;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.sys.service.MenuService;

public class PermissionHelper {
	// 是否有菜单和按钮权限
	public static boolean hasMenuAndButtonPermission(HttpServletRequest request, String path) {
		if (hasMenu(path)) {
			if (!hasMenu(SessionUtils.getCurrentPersmission(request), path)) {
				return false;
			}
		} else {
			path = path.substring(0, path.lastIndexOf("/"));
			if (!hasMenu(SessionUtils.getCurrentPersmission(request), path)) {
				return false;
			}
		}
		return true;
	}

	// 是否有某个特殊权限
	public static boolean hasSpecialPermission(HttpServletRequest request, String keey) {
		if (SessionUtils.getCurrentPersmission(request).containsKey(keey)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean hasMenu(String path) {
		for (String key : DataInitProcessor.permissionMap.keySet()) {
			if (DataInitProcessor.permissionMap.get(key).getUrl().equals(path)) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasMenu(Map<String, SysMenu> map, String path) {
		for (String key : map.keySet()) {
			if (map.get(key).getMenuUri().equals(path)) {
				return true;
			}
		}
		return false;
	}

	// menu_and_operate
	public static synchronized void initPermission() {
		DataInitProcessor.permissionList.clear();
		DataInitProcessor.permissionMap.clear();
		MenuService menuService = SpringContextHolder.getBean(MenuService.class);
		List<SysMenu> menuAll = menuService.initQuery().addOrderClause("menuLayer", "asc").addOrderClause("reorder", "asc").findStopTrue();
		for (SysMenu sysMenu : menuAll) {
			SysPermission sysPermission = new SysPermission();
			sysPermission.setGrade(sysMenu.getMenuLayer());
			sysPermission.setName(sysMenu.getMenuName());
			sysPermission.setCode(sysMenu.getMenuCode());
			sysPermission.setParentCode(sysMenu.getParentMenuCode());
			sysPermission.setUrl(sysMenu.getMenuUri());
			DataInitProcessor.permissionList.add(sysPermission);
			DataInitProcessor.permissionMap.put(sysMenu.getMenuCode(), sysPermission);
		}
		for (SysPermission permission : DataInitProcessor.permissionList) {
			if (permission.getGrade().equals(3)) {
				SysPermission parent = DataInitProcessor.permissionMap.get(permission.getParentCode());
				permission.setUrl(parent.getUrl() + "/" + permission.getUrl());
			}
		}
	}
	
	//根据角色获取拥有角色的管理员用户
	@SuppressWarnings("unchecked")
	public static List<SysAdmin> findAdminByRoleList(List<String> roleCodes){
		AdminRoleService adminRoleService = SpringContextHolder.getBean(AdminRoleService.class);
		AdminService adminService = SpringContextHolder.getBean(AdminService.class);
		List<SysAdminRole> adminRoleList=adminRoleService.initQuery().andIn("roleCode", roleCodes).findStopTrue();
		if(Collections3.isEmpty(adminRoleList)){
			return Lists.newArrayList();
		}
		List<String> adminCodes=(List<String>)Collections3.extractToList(adminRoleList, "adminCode");
		return adminService.initQuery().andIn("adminCode",adminCodes).findStopTrue();
	}

}
