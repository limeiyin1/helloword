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
import com.redfinger.manager.common.domain.AppRecommend;
import com.redfinger.manager.common.domain.AppRecommendApk;
import com.redfinger.manager.modules.market.service.MarketAppApkService;
import com.redfinger.manager.modules.market.service.MarketAppRcommendApkService;
import com.redfinger.manager.modules.market.service.MarketAppRecommendService;

@Controller
@RequestMapping(value = "/market/recommend")
public class MarketAppRecommendController extends BaseController {

	@Autowired
	MarketAppRecommendService service;
	@Autowired
	MarketAppRcommendApkService appRecommendApkService;
	@Autowired
	MarketAppApkService appApkService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AppRecommend> list(HttpServletRequest request,
			HttpServletResponse response, Model model, AppRecommend bean)
			throws Exception {
		List<AppRecommend> list = service.initQuery(bean)
				.andLike("name", bean.getName())
				.addOrderClause("reorder", " asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<AppRecommend> pageInfo = new PageInfo<AppRecommend>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, AppRecommend bean)
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
			Model model, AppRecommend bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, AppRecommend bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, AppRecommend bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, AppRecommend bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, AppRecommend bean)
			throws Exception {
		service.delete(request, bean);
	}

	@RequestMapping(value = "gameForm")
	public String gameForm(HttpServletRequest request,
			HttpServletResponse response, Model model, AppRecommend bean) {
		bean = service.get(bean.getId());
		model.addAttribute("bean", bean);
		// 所有游戏
		List<AppApk> appApkList = appApkService.initQuery().findStopTrue();
		// 自己的游戏
		List<AppRecommendApk> appRecommendApkList = appRecommendApkService
				.initQuery().andEqualTo("recommendId", bean.getId()).addOrderForce("reorder", "asc").findAll();
		model.addAttribute("appApkList", appApkList);
		model.addAttribute("appRecommendApkList", appRecommendApkList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "apk" )
	public void permission(HttpServletRequest request,
			HttpServletResponse response, Model model, AppRecommend bean,
			AppRecommendApk bean1, String appApkId, String categoryId)
			throws Exception {
		appRecommendApkService.insert(request, bean, appApkId);

	}

}
