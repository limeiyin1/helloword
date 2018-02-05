package com.redfinger.manager.modules.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.Constants;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.SysAdminExample;
import com.redfinger.manager.common.domain.SysAdminRole;
import com.redfinger.manager.common.domain.SysMenu;
import com.redfinger.manager.common.domain.SysRole;
import com.redfinger.manager.common.domain.SysRoleMenu;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.SysAdminMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.Md5Utils;
import com.redfinger.manager.common.utils.StringUtils;
@Transactional
@Service
@PrimaryKeyAnnotation(field="adminCode")
public class AdminService extends BaseService<SysAdmin, SysAdminExample, SysAdminMapper> {
	@Autowired
	RoleService roleService;
	@Autowired
	RoleMenuService roleMenuService;
	@Autowired
	AdminRoleService adminRoleService;
	@Autowired
	MenuService menuService;
	
	
	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, SysAdmin bean) {
		SysAdmin admin=this.initQuery().get(bean.getAdminCode());
		if (admin != null) {
			return "编码重复，请重新输入！";
		}
		//MD5(帐号名+加密字符+密码)
		bean.setAdminPwd(Md5Utils.MD5( bean.getAdminCode()+ Constants.USER_PASSWORD_SALT+ Constants.USER_DEFAULT_PASSWORD));
		bean.setLoginCount(0);
		return null;
	}
	
	public SysAdmin login(HttpServletRequest request , HttpServletResponse response,String login,SysAdmin user) throws Exception {
		if(user.getLoginCount()==null){
			user.setLoginCount(1);
		}else{
			user.setLoginCount(user.getLoginCount()+1);
		}
		user.setLoginIp(StringUtils.getRemoteAddr(request));
		log.info("login user {} ip {}", login, user.getLoginIp());
		user.setLoginTime(new Date());
		this.update(request, user);
		return user;
	}
	/**
	 * 获取登录用户的菜单权限
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Map<String, SysMenu> getPermissionByUser(SysAdmin user) {
		Map<String, SysMenu> menuMap = Maps.newHashMap();
		// 获取用户拥有的角色
		List<SysAdminRole> adminRoleList = adminRoleService.initQuery().andEqualTo("adminCode", user.getAdminCode()).findStopTrue();
		if (CollectionUtils.isEmpty(adminRoleList)) {
			return menuMap;
		}
		// 获取角色编号
		List<String> roleIdList = (List<String>) Collections3.extractToList(adminRoleList, "roleCode");
		if (CollectionUtils.isEmpty(roleIdList)) {
			return menuMap;
		}
		// 获取真正角色
		List<SysRole> roleList = roleService.initQuery().andIn("roleCode", roleIdList).findStopTrue();
		if (CollectionUtils.isEmpty(roleList)) {
			return menuMap;
		}
		roleIdList = (List<String>) Collections3.extractToList(roleList, "roleCode");
		// 获取拥有菜单
		List<SysRoleMenu> roleMenuList = roleMenuService.initQuery().andIn("roleCode", roleIdList).findStopTrue();
		if (CollectionUtils.isEmpty(roleMenuList)) {
			return menuMap;
		}
		// 获取菜单编号
		List<String> menuIdList = (List<String>) Collections3.extractToList(roleMenuList, "menuCode");
		if (CollectionUtils.isEmpty(menuIdList)) {
			return menuMap;
		}
		// 获取菜单实体
		List<SysMenu> menuList = menuService.initQuery().andIn("menuCode", menuIdList).findStopTrue();
		if (CollectionUtils.isEmpty(menuList)) {
			return menuMap;
		}

		for (SysMenu menu : menuList) {
			menuMap.put(menu.getMenuCode(), menu);
		}
		return menuMap;

	}


}
