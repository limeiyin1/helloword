package com.redfinger.manager.modules.facility.web;

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
import com.redfinger.manager.common.domain.RfIdc;

import com.redfinger.manager.modules.facility.service.IdcService;

@Controller
@RequestMapping(value = "/facility/idc")
public class IdcController extends BaseController {
	@Autowired
	IdcService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfIdc> list(HttpServletRequest request, HttpServletResponse response, Model model, RfIdc bean) throws Exception {
		List<RfIdc> list = service.initQuery(bean).andLike("idcName", bean.getIdcName()).andLike("idcCode", bean.getIdcCode())
				.addOrderClause("reorder", "asc")
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfIdc> pageInfo = new PageInfo<RfIdc>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfIdc bean) throws Exception {
		if (bean.getIdcId() == null) {

		} else {
			bean = service.get(bean.getIdcId());
			model.addAttribute("bean", bean);

		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfIdc bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfIdc bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfIdc bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfIdc bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfIdc bean) throws Exception {
		service.remove(request, bean);
	}

}