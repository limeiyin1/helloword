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
import com.redfinger.manager.common.domain.RfSmsResultInfo;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.modules.sms.service.SmsResultInfoService;

@Controller
@RequestMapping(value = "/sms/result")
public class SmsResultInfoController extends BaseController {

	@Autowired
	private SmsResultInfoService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfSmsResultInfo> list(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsResultInfo bean) throws Exception {
		List<RfSmsResultInfo> list = service.initQuery(bean)
				.andLike("resultCode", bean.getResultCode())
				.andEqualTo("smsResultType", bean.getSmsResultType())
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		
		PageInfo<RfSmsResultInfo> pageInfo = new PageInfo<RfSmsResultInfo>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String addmobileForm(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsResultInfo bean) throws Exception {
		if(null != bean.getResultInfoId()){
			bean = service.get(bean.getResultInfoId());
			model.addAttribute("bean", bean);
		}
		
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsResultInfo bean) throws Exception {
		List<RfSmsResultInfo> list = service.initQuery(bean).andEqualTo("smsResultType", bean.getSmsResultType())
		.andEqualTo("resultCode", bean.getResultCode()).findAll();
		if(null != list && list.size()>0){
			throw new BusinessException(bean.getResultCode()+"此编码在你选择的发送方类型中已经存在！");
		}
		service.save(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsResultInfo bean) throws Exception {
		List<RfSmsResultInfo> list = service.initQuery(bean).andEqualTo("resultCode", bean.getResultCode()).andEqualTo("smsResultType", bean.getSmsResultType())
				.andNotEqualTo("resultInfoId", bean.getResultInfoId()).findAll();
		if(null != list && list.size()>0){
			throw new BusinessException(bean.getResultCode()+"此编码在你选择的发送方类型中已经存在！");
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsResultInfo bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsResultInfo bean) throws Exception {
		service.stop(request, bean);
	}
	
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfSmsResultInfo bean) throws Exception {
		service.delete(request, bean);
	}
}
