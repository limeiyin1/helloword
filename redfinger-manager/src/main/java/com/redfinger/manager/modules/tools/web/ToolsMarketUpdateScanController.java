package com.redfinger.manager.modules.tools.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.ToolsMarketUpdate;
import com.redfinger.manager.modules.tools.service.ToolsMarketUpdateScanService;

@Controller
@RequestMapping(value="/tools/scan")
public class ToolsMarketUpdateScanController extends BaseController{

	@Autowired
	ToolsMarketUpdateScanService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ToolsMarketUpdate> list(HttpServletRequest request,
			HttpServletResponse response, Model model, ToolsMarketUpdate bean)
			throws Exception {
		List<ToolsMarketUpdate> list = service.initQuery(bean)
				.andLike("name", bean.getName())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andEqualTo("result", bean.getResult())
				.andEqualTo("marketName", bean.getMarketName())
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		PageInfo<ToolsMarketUpdate> pageInfo = new PageInfo<ToolsMarketUpdate>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, ToolsMarketUpdate bean)
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
			Model model, ToolsMarketUpdate bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, ToolsMarketUpdate bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, ToolsMarketUpdate bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, ToolsMarketUpdate bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, ToolsMarketUpdate bean)
			throws Exception {
		service.remove(request, bean);
	}
	
	@RequestMapping(value = "scan")
	public void scan(HttpServletRequest request,
			HttpServletResponse response, Model model, ToolsMarketUpdate bean)
			throws Exception {
		String[] arr=bean.getIds().split(",");
		if(arr!=null && arr.length>0){
			List<String> ids=Lists.newArrayList();
			for(String id:arr){
				ids.add(id);
			}
			service.scan(ids);
		}
		
	}

}
