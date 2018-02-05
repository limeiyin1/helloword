package com.redfinger.manager.modules.fault.web;

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
import com.redfinger.manager.common.domain.RfMsgTemplate;
import com.redfinger.manager.modules.fault.service.FaultMsgTemplateService;

@Controller
@RequestMapping(value = "/fault/msgtemplate")
public class FaultMsgTemplateController extends BaseController {

	@Autowired
	FaultMsgTemplateService service;
	
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfMsgTemplate> list(HttpServletRequest request, HttpServletResponse response, Model model, RfMsgTemplate bean) throws Exception {
		List<RfMsgTemplate> list = service.initQuery(bean)
				.andEqualTo("templateType", bean.getTemplateType())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfMsgTemplate> pageInfo = new PageInfo<RfMsgTemplate>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model,RfMsgTemplate bean) throws Exception{
		if(null!=bean.getTemplateId()){
			bean=service.get(bean.getTemplateId());
			model.addAttribute("bean", bean);
		}
		
		return this.toPage(request, response, model);
	}
	
	
	
	
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfMsgTemplate bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfMsgTemplate bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfMsgTemplate bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfMsgTemplate bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfMsgTemplate bean) throws Exception {
		service.remove(request, bean);
	}
	
	

	
}
