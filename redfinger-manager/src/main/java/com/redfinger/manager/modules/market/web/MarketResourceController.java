package com.redfinger.manager.modules.market.web;

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
import com.redfinger.manager.common.domain.MarketResource;
import com.redfinger.manager.modules.market.service.MarketResourceService;

@Controller
@RequestMapping(value = "/market/resource")
public class MarketResourceController extends BaseController {
	@Autowired
	MarketResourceService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<MarketResource> list(HttpServletRequest request, HttpServletResponse response, Model model, MarketResource bean) throws Exception {
		List<MarketResource> list = service.initQuery(bean).andLike("name", bean.getName()).addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<MarketResource> pageInfo = new PageInfo<MarketResource>(list);
		return pageInfo;

	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, MarketResource bean) throws Exception {

		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
		}
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, MarketResource bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, MarketResource bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, MarketResource bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, MarketResource bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, MarketResource bean) throws Exception {
		service.delete(request, bean);
	}

}
