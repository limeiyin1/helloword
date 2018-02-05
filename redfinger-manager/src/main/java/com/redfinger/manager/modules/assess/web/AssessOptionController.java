package com.redfinger.manager.modules.assess.web;

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
import com.redfinger.manager.common.domain.AssessOption;
import com.redfinger.manager.common.domain.AssessProjectOption;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.modules.assess.service.AssessOptionService;
import com.redfinger.manager.modules.assess.service.AssessProjectOptionService;

@Controller
@RequestMapping(value="/assess/option")
public class AssessOptionController extends BaseController{

	@Autowired
	AssessOptionService service;
	@Autowired
	AssessProjectOptionService projectOptionService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AssessOption> list(HttpServletRequest request,
			HttpServletResponse response, Model model, AssessOption bean)
			throws Exception {
		List<AssessOption> list = service.initQuery()
				.andLike("name", bean.getName())
				.andLike("keyword", bean.getKeyword())
				.addOrderClause("reorder", " asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<AssessOption> pageInfo = new PageInfo<AssessOption>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, AssessOption bean)
			throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
			model.addAttribute("bean", bean);

		}

		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, AssessOption bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, AssessOption bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, AssessOption bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, AssessOption bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, AssessOption bean)
			throws Exception {
		String[] ids=bean.getIds().split(",");
		for(String i :ids){
			int id=Integer.parseInt(i);
			List<AssessProjectOption> list=projectOptionService.initQuery().andEqualTo("optionId", id).findAll();
			if(list.size()!=0){
				throw new BusinessException("题目已配置,无法删除！");
			}
		}
		service.delete(request, bean);
	}
}
