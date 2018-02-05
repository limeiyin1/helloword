package com.redfinger.manager.modules.sys.web;

import java.util.List;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.SysOrg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.modules.sys.service.OrgService;

@Controller
@RequestMapping(value = "/sys/org")
public class OrgController extends BaseController {

	@Autowired
	OrgService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public List<SysOrg> list(HttpServletRequest request, HttpServletResponse response, Model model, SysOrg bean) throws Exception {
		List<SysOrg> list =service.initQuery(bean).andLike("orgName", bean.getOrgName()).andLike("orgCode", bean.getOrgCode()).addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for(SysOrg org :list){
			if(org.getParentOrgCode().equals("0")){
				org.setParentOrgCode("");
			}
		}
		return list;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, SysOrg bean) throws Exception {
		if (bean.getOrgCode() == null) {
			model.addAttribute("parent", service.get(bean.getParentOrgCode()));
		} else {
			bean = service.get(bean.getOrgCode());
			model.addAttribute("bean", bean);
			model.addAttribute("parent", service.get(bean.getParentOrgCode()));
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, SysOrg bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, SysOrg bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, SysOrg bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, SysOrg bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, SysOrg bean) throws Exception {
		service.delete(request, bean);
	}

}
