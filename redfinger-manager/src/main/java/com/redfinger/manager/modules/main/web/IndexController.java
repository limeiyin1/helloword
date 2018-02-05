package com.redfinger.manager.modules.main.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.SysPermission;
import com.redfinger.manager.common.domain.SysMenu;
import com.redfinger.manager.common.helper.DataInitProcessor;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.sys.service.RoleService;

@Controller
public class IndexController {

	@Autowired
	AdminService service;
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 获取用户导航
		List<SysPermission> navList = Lists.newArrayList();
		List<SysPermission> all = DataInitProcessor.permissionList;
		Map<String, SysMenu> userPermission = SessionUtils.getCurrentPersmission(request);
		if (userPermission != null && userPermission.size() > 0) {
			for (SysPermission permission : all) {
				if (permission.getGrade().equals(1)) {
					if (userPermission.get(permission.getCode()) != null) {
						navList.add(permission);
					}
				}
			}
		}
		model.addAttribute("navList", navList);
		return "main/layout";
	}

	
	@RequestMapping(value = "/menu")
	public String menu(HttpServletRequest request, HttpServletResponse response, Model model, String id) {
		// 获取用户菜单
		List<SysPermission> menuList = Lists.newArrayList();
		List<SysPermission> all = DataInitProcessor.permissionList;
		Map<String, SysMenu> userPermission = SessionUtils.getCurrentPersmission(request);
		if (userPermission != null && userPermission.size() > 0) {
			for (SysPermission permission : all) {
				if (permission.getGrade().equals(2) && permission.getParentCode().equals(id)) {
					if (userPermission.get(permission.getCode()) != null) {
						menuList.add(permission);
					}
				}
			}
		}
		model.addAttribute("menuList", menuList);
		return "main/menu";
	}

	@ResponseBody
	@RequestMapping(value = "/keepSession")
	public void keepSession() {

	}

}
