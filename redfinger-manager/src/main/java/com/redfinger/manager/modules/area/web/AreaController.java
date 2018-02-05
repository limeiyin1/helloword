package com.redfinger.manager.modules.area.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.Constants;
import com.redfinger.manager.common.domain.RfArea;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.modules.base.service.AreaService;
import com.redfinger.manager.modules.base.service.ConfigService;

@Controller
@RequestMapping(value = "/area/manage")
public class AreaController extends BaseController {
	@Autowired
	private AreaService service;
	@Autowired
	private ConfigService configService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public List<RfArea> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfArea bean,String areaName)
			throws Exception {
		
		List<RfArea> list = service.initQuery(bean)
				.andLike("name", areaName)
				.addOrderClause("id", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder()).findDelTrue();
				
		
		return list;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, RfArea bean)
			throws Exception {
		if (bean.getId() == null) {
			model.addAttribute("parent", service.get(bean.getParentId()));
		} else {
			bean = service.get(bean.getId());
			model.addAttribute("bean", bean);
			model.addAttribute("parent", service.get(bean.getParentId()));
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, RfArea bean) throws Exception {
		updateConfig(request);
		if(null == bean.getParentId()){
			bean.setParentId(0);
		}
		service.save(request, bean);
	}
	
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,String beginTimeStr,String endTimeStr,
			HttpServletResponse response, Model model, RfArea bean)
			throws Exception {
		updateConfig(request);
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfArea bean) throws Exception {
		updateConfig(request);
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfArea bean) throws Exception {
		updateConfig(request);
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfArea bean)
			throws Exception {
		updateConfig(request);
		service.delete(request, bean);
	}
	
	public void updateConfig(HttpServletRequest request) throws Exception{
		SysConfig config = configService.get(Constants.MD5_CITY);
		config.setConfigValue("MD"+generateStr());
		configService.updateConfig(request, config);
	}

}
