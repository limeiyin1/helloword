package com.redfinger.manager.modules.game.web;

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
import com.redfinger.manager.common.domain.AppCategory;
import com.redfinger.manager.common.domain.AppCategoryApk;
import com.redfinger.manager.common.mapper.AppApkMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.modules.game.service.GameApkService;
import com.redfinger.manager.modules.game.service.GameAppApkService;
import com.redfinger.manager.modules.game.service.GameCategoryApkService;
import com.redfinger.manager.modules.game.service.GameCategoryService;

@Controller
@RequestMapping(value="/game/category")
public class GameCategoryController extends BaseController{
	
	@Autowired
	AppApkMapper appApkMapper;
	@Autowired
	GameCategoryService service;
	@Autowired
	GameCategoryApkService gameCategoryApkService;
	@Autowired
	GameAppApkService gameAppApkService;
	@Autowired
	GameApkService apkService;
	
	
	@RequestMapping(value="")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model,AppCategory bean,AppCategoryApk bean1, String appApkId,String categoryId) throws Exception {
		
		
		return this.toPage(request, response, model);  
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AppCategory> list(HttpServletRequest request, HttpServletResponse response, Model model, AppCategory bean) throws Exception {
		List<AppCategory> list = service.initQuery(bean)
				.andLike("name", bean.getName())
			//	.andLike("creater", bean.getCreater())
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(),bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<AppCategory> pageInfo = new PageInfo<AppCategory>(list);
				return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, 
			HttpServletResponse response, Model model, AppCategory bean) throws Exception {
		if (bean.getId() == null) {
			
		} else {	
			bean = service.get(bean.getId());
			model.addAttribute("bean", bean);
		} 
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, AppCategory bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, AppCategory bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, AppCategory bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, AppCategory bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, AppCategory bean) throws Exception {
		service.delete(request, bean);
	}
	
	@RequestMapping(value = "gameForm")
	public String gameForm(HttpServletRequest request,
			HttpServletResponse response, Model model, AppCategory bean) {
		bean = service.get(bean.getId());
		model.addAttribute("bean", bean);
		// 所有游戏
		List<AppApk> appApkList = gameAppApkService.initQuery().findStopTrue();
		// 自己的游戏
		List<AppCategoryApk> appCategoryApkList = gameCategoryApkService
				.initQuery().andEqualTo("categoryId", bean.getId())
				.addOrderForce("reorder", "asc").findAll();
		model.addAttribute("appApkList", appApkList);
		model.addAttribute("appCategoryApkList", appCategoryApkList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "apk" )
	public void permission(HttpServletRequest request,
			HttpServletResponse response, Model model, AppCategory bean,
			AppCategoryApk bean1, String appApkId, String categoryId)
			throws Exception {
		gameCategoryApkService.insert(request, bean, appApkId);
	}
	
	
	@RequestMapping(value="look")
	public String look(HttpServletRequest request,HttpServletResponse response,
												Model model, AppCategory bean){
		bean = service.get(bean.getId());
		model.addAttribute("bean", bean);
	 return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "apklist")
	@SuppressWarnings("unchecked")
	public PageInfo<AppApk> apklist(HttpServletRequest request, HttpServletResponse response,
							Model model, AppCategory bean,AppApk apkbean,  String apkname) {
		List<AppCategoryApk> list = gameCategoryApkService.initQuery(bean)
				.andEqualTo("categoryId", bean.getId())
				.addOrderClause("reorder", " asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.findAll();
		List<Integer> Ids = Collections3.extractToList(list, "apkId");
		//当分类ids中没有数据，则添加0条数据
		if(Ids.size()==0){
			Ids.add(null);
		}
		List<AppApk> appApkList = gameAppApkService.initQuery()
				.andIn("id", Ids)
				.andLike("name", apkbean.getName())
				.addOrderClause("reorder", " asc")
				.addOrderForce(apkbean.getSort(), apkbean.getOrder())
				.pageAll(apkbean.getPage(), apkbean.getRows());
		
		PageInfo<AppApk> pageInfo = new PageInfo<AppApk>(appApkList);
		return pageInfo;

	}
			
	
	
}
















