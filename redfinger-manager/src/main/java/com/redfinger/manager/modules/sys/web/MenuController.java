package com.redfinger.manager.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.SysMenu;
import com.redfinger.manager.modules.sys.service.MenuService;

@Controller
@RequestMapping(value = "/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	MenuService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public List<SysMenu> list(HttpServletRequest request, HttpServletResponse response, Model model, SysMenu bean) throws Exception {
		List<SysMenu> list = service.initQuery(bean).andLike("menuName", bean.getMenuName()).andLike("menuCode", bean.getMenuCode()).addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder())
				.findDelTrue();
		for(SysMenu menu :list){
			if(menu.getParentMenuCode().equals("0")){
				menu.setParentMenuCode("");
			}
		}
		return list;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, SysMenu bean) throws Exception {
		if (bean.getMenuCode() == null) {
			model.addAttribute("parent", service.get(bean.getParentMenuCode()));
		} else {
			bean = service.get(bean.getMenuCode());
			model.addAttribute("bean", bean);
			model.addAttribute("parent", service.get(bean.getParentMenuCode()));
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, SysMenu bean) throws Exception {
		if(bean.getMenuUri()==null)
			bean.setMenuUri(bean.getMenuCode());
		service.save(request, bean);

	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, SysMenu bean) throws Exception {
		service.start(request, bean);

	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, SysMenu bean) throws Exception {
		service.delete(request, bean);

	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, SysMenu bean) throws Exception {
		service.stop(request, bean);

	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, SysMenu bean) throws Exception {
		service.update(request, bean);

	}

}
