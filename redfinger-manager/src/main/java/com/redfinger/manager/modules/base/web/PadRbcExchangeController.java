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
import com.redfinger.manager.common.domain.RfPadRbcStandard;
import com.redfinger.manager.modules.base.service.PadRbcExchangeService;

@Controller
@RequestMapping(value = "/base/padRbcStandard")
public class PadRbcExchangeController extends BaseController{
	@Autowired
	private PadRbcExchangeService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public  PageInfo<RfPadRbcStandard> list(HttpServletRequest request, HttpServletResponse response,RfPadRbcStandard bean) throws Exception{
		List<RfPadRbcStandard> list = service.initQuery().addOrderClause("reorder", "asc").pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfPadRbcStandard> pageInfo = new PageInfo<RfPadRbcStandard>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPadRbcStandard bean) throws Exception{
		if(bean.getStandardId() != null){
			bean = service.get(bean.getStandardId());
			model.addAttribute("bean", bean);
		}
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,Model model, RfPadRbcStandard bean) throws Exception {
		//bean.setPadGrade("0");//目前只能是普通设备
		service.save(request,bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfPadRbcStandard bean) throws Exception{
		//bean.setPadGrade("0");
		service.update(request,bean);
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,Model model, RfPadRbcStandard bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,Model model, RfPadRbcStandard bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,HttpServletResponse response, Model model, RfPadRbcStandard bean)
			throws Exception {
		service.remove(request, bean);
	}
}
