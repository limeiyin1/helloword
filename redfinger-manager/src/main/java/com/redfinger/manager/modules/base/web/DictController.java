package com.redfinger.manager.modules.base.web;

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
import com.redfinger.manager.common.domain.SysDict;
import com.redfinger.manager.modules.base.service.DictService;

@Controller
@RequestMapping(value = "/base/dict")
public class DictController extends BaseController {

	@Autowired
	DictService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		List<String> categoryList = service.getDictCategory();
		model.addAttribute("categoryList", categoryList);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<SysDict> list(HttpServletRequest request, HttpServletResponse response, Model model, SysDict bean) throws Exception{
		List<SysDict> list = service.initQuery().andEqualTo("dictCategory", bean.getDictCategory()).andLike("remark", bean.getRemark()).andLike("dictCode", bean.getDictCode()).addOrderClause("dictCategory", "asc").addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<SysDict> pageInfo = new PageInfo<SysDict>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, SysDict bean) throws Exception{
		if (bean.getDictCode() == null) {
			
		} else {
			bean = service.get(bean.getDictCode());
			model.addAttribute("bean", bean);
		}
		List<String> categoryList = service.getDictCategory();
		model.addAttribute("categoryList", categoryList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, SysDict bean) throws Exception{
		service.save(request,bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, SysDict bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, SysDict bean) throws Exception {
		service.start(request,bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, SysDict bean) throws Exception{
		service.stop(request,bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, SysDict bean) throws Exception{
		service.delete(request,bean);
	}

}
