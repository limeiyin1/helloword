package com.redfinger.manager.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.TreeDomain;
import com.redfinger.manager.common.domain.SysMenu;
import com.redfinger.manager.common.domain.SysRole;

import com.redfinger.manager.common.domain.SysRoleMenu;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.ListSortUtils;
import com.redfinger.manager.modules.sys.service.MenuService;
import com.redfinger.manager.modules.sys.service.RoleMenuService;
import com.redfinger.manager.modules.sys.service.RoleService;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

	@Autowired
	RoleService service;
	@Autowired
	MenuService menuService;
	@Autowired
	RoleMenuService roleMenuService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<SysRole> list(HttpServletRequest request,
			HttpServletResponse response, Model model, SysRole bean)
			throws Exception {
		List<SysRole> list = service.initQuery(bean)
				.andLike("roleName", bean.getRoleName())
				.andLike("roleCode", bean.getRoleCode())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(list);
		return pageInfo;

	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, SysRole bean)
			throws Exception {
		if (bean.getRoleCode() == null) {

		} else {
			bean = service.get(bean.getRoleCode());
			model.addAttribute("bean", bean);

		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, SysRole bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, SysRole bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, SysRole bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, SysRole bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, SysRole bean)
			throws Exception {
		service.delete(request, bean);
	}

	@RequestMapping(value = "permissionForm")
	public String permissionForm(HttpServletRequest request,
			HttpServletResponse response, Model model, SysRole bean) {

		bean = service.get(bean.getRoleCode());
		model.addAttribute("bean", bean);
		// 查询出所有的MENU
		List<SysMenu> menulist = menuService.initQuery()
				.addOrderClause("reorder", "asc")
				.addOrderClause("menuLayer", "asc").findStopTrue();
		// 查询中间表 menu和role的数据

		List<SysRoleMenu> roleMenuList = roleMenuService.initQuery()
				.andEqualTo("roleCode", bean.getRoleCode()).findAll();

		// 默认勾选

		for (SysMenu sysMenu : menulist) {
			for (SysRoleMenu roleMenu : roleMenuList) {
				if (sysMenu.getMenuCode().equals(roleMenu.getMenuCode())) {
					sysMenu.setChecked(true);
					break;
				}
			}
		}
		TreeDomain treeDomain = ListSortUtils.sortListToTree(menulist,
				"menuCode", "parentMenuCode", "menuName");
		String treeJson = JsonUtils.ObjectToString(treeDomain.getChildren());

		model.addAttribute("treeJson", treeJson);

		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "permission")
	public void permission(HttpServletRequest request,
			HttpServletResponse response, Model model, SysRole bean,
			String permissionIds) throws Exception {
		roleMenuService.insert(request, bean, permissionIds);
	}

}
