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
import com.redfinger.manager.common.domain.AppApk;
import com.redfinger.manager.common.domain.AppDeveloper;

import com.redfinger.manager.common.mapper.AppApkMapper;
import com.redfinger.manager.common.mapper.AppDeveloperMapper;
import com.redfinger.manager.modules.market.service.MarketAppApkService;
import com.redfinger.manager.modules.market.service.MarketAppDeveloperService;

@Controller
@RequestMapping(value = "/market/developer")
public class MarketAppDeveloperController extends BaseController {
	@Autowired
	MarketAppDeveloperService appDeveloperService;
	@Autowired
	AppDeveloperMapper appDeveloperMapper;
	@Autowired
	AppApkMapper appApkMapper;
	@Autowired
	MarketAppApkService appApkService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AppDeveloper> list(HttpServletRequest request,
			HttpServletResponse response, Model model, AppDeveloper bean)
			throws Exception {
		List<AppDeveloper> list = appDeveloperService.initQuery(bean)
				.andLike("name", bean.getName())
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<AppDeveloper> pageInfo = new PageInfo<AppDeveloper>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, AppDeveloper bean)
			throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = appDeveloperService.get(bean.getId());
			model.addAttribute("bean", bean);
		}

		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, AppDeveloper bean) throws Exception {
		appDeveloperService.isExist(bean.getName());//判断发行商是否已经存在
		appDeveloperService.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, AppDeveloper bean)
			throws Exception {
		appDeveloperService.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, AppDeveloper bean) throws Exception {
		appDeveloperService.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, AppDeveloper bean) throws Exception {
		appDeveloperService.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, AppDeveloper bean)
			throws Exception {
		appDeveloperService.delete(request, bean);
	}

	@RequestMapping(value = "look")
	public String look(HttpServletRequest request,
			HttpServletResponse response, Model model, AppDeveloper bean)
			throws Exception {
		bean = appDeveloperService.get(bean.getId());
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "applist")
	public PageInfo<AppApk> tasklist(HttpServletRequest request,
			HttpServletResponse response, Model model,AppDeveloper bean,String apkname)
			throws Exception {
			List<AppApk> apkList = appApkService.initQuery()
				.andLike("name", bean.getName())
				.andEqualTo("developer", bean.getId())
				.andEqualTo("status", "1")
				.andEqualTo("enableStatus", "1")
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
			
		PageInfo<AppApk> pageInfo = new PageInfo<AppApk>(apkList);
		return pageInfo;	
	}

}
