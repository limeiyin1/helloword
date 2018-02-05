package com.redfinger.manager.modules.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfClass;
import com.redfinger.manager.common.helper.DataInitProcessor;
import com.redfinger.manager.modules.base.service.ClassService;

@Controller
@RequestMapping(value = "/base/category")
public class CategoryController extends BaseController {

	@Autowired
	ClassService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		model.addAttribute("classList", DataInitProcessor.dictCategoryMap.get("rf_class.class_type"));
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public List<RfClass> list(HttpServletRequest request, HttpServletResponse response, Model model, RfClass bean) throws Exception{
		List<RfClass> list = service.initQuery().andLike("className", bean.getClassName()).andEqualTo("classType", bean.getClassType()).addOrderClause("classType", "asc").addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder()).findDelTrue();
		return list;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfClass bean) throws Exception{
		if (bean.getClassId() == null) {
			model.addAttribute("parent", service.get(bean.getClassPid()));
			model.addAttribute("classList", DataInitProcessor.dictCategoryMap.get("rf_class.class_type"));
		} else {
			bean = service.get(bean.getClassId());
			model.addAttribute("bean", bean);
			model.addAttribute("parent", service.get(bean.getClassPid()));
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfClass bean) throws Exception{
		service.save(request,bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfClass bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfClass bean) throws Exception {
		service.start(request,bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfClass bean) throws Exception{
		service.stop(request,bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfClass bean) throws Exception{
		service.delete(request,bean);
	}

}
