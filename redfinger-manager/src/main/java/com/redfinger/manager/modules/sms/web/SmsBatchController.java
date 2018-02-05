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
import com.redfinger.manager.common.domain.RfSms;
import com.redfinger.manager.common.domain.RfSmsBatch;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.sms.service.SmsBatchService;
import com.redfinger.manager.modules.sms.service.SmsService;

@Controller
@RequestMapping(value = "/sms/batch")
public class SmsBatchController extends BaseController {

	@Autowired
	SmsBatchService service;
	@Autowired
	SmsService smsService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfSmsBatch> list(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsBatch bean) throws Exception {
		List<RfSmsBatch> list = service.initQuery(bean)
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.andLike("name", bean.getName())
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfSmsBatch> pageInfo = new PageInfo<RfSmsBatch>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "detailForm")
	public String detailForm(HttpServletRequest request, HttpServletResponse response, Model model,RfSmsBatch bean) throws Exception {
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "detailList")
	public PageInfo<RfSms> detailList(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsBatch bean) throws Exception {
		List<RfSms> list = smsService.initQuery(bean).andEqualTo("batchId", bean.getId()).addOrderClause("sendTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfSms> pageInfo = new PageInfo<RfSms>(list);
		return pageInfo;
	}

}
