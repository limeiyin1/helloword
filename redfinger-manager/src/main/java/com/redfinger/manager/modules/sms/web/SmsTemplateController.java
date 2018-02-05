package com.redfinger.manager.modules.sms.web;

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
import com.redfinger.manager.common.domain.RfSmsTemplate;
import com.redfinger.manager.modules.sms.service.SmsTemplateService;

@Controller
@RequestMapping(value = "/sms/template")
public class SmsTemplateController extends BaseController {

	@Autowired
	SmsTemplateService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);  
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfSmsTemplate> list(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsTemplate bean) throws Exception {
		List<RfSmsTemplate> list = service.initQuery(bean).andLike("smsTemplateName", bean.getSmsTemplateName()).addOrderClause("reorder", "asc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfSmsTemplate> pageInfo = new PageInfo<RfSmsTemplate>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsTemplate bean) throws Exception {
		if (bean.getSmsTemplateId() == null) {
		} else {
			bean = service.get(bean.getSmsTemplateId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsTemplate bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsTemplate bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsTemplate bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsTemplate bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsTemplate bean) throws Exception {
		service.delete(request, bean);
	}

}
