package com.redfinger.manager.modules.game.web;

import java.util.Date;
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
import com.redfinger.manager.common.domain.AppDeveloper;
import com.redfinger.manager.common.domain.AppImage;
import com.redfinger.manager.common.domain.AppRecommend;
import com.redfinger.manager.common.domain.AppRecommendApk;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.PinYinUtils;
import com.redfinger.manager.modules.game.service.GameApkService;
import com.redfinger.manager.modules.game.service.GameAppCategoryApkService;
import com.redfinger.manager.modules.game.service.GameAppDeveloperService;
import com.redfinger.manager.modules.game.service.GameAppImageService;
import com.redfinger.manager.modules.game.service.GameAppcategoryService;
import com.redfinger.manager.modules.game.service.GameApprecommendService;
import com.redfinger.manager.modules.game.service.GameRecommendApkService;

@Controller
@RequestMapping(value ="/game/apkmanage")
public class GameApkController extends BaseController{
	
	@Autowired
	GameApkService service;
	@Autowired
	GameApprecommendService appservice;
	@Autowired
	GameRecommendApkService appRecommendApkService;
	@Autowired
	GameAppcategoryService categoryservice;
	@Autowired
	GameAppCategoryApkService categoryApkService;
	@Autowired
	GameAppDeveloperService developerService;
	@Autowired
	GameAppImageService imageService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);  
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<AppApk> list(HttpServletRequest request, HttpServletResponse response, Model model, AppApk bean) throws Exception {
		List<AppApk> list = service.initQuery(bean)
				
				.andLike("name", bean.getName())
				.addOrderClause("reorder", " asc")
				.addOrderForce(bean.getSort(), bean.getOrder())	
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		for (AppApk apk : list) {
			if (apk.getCountDownload() == null) {
				apk.setCountDownload(0);
			}
			if (apk.getCountDownloadBase() == null) {
				apk.setCountDownloadBase(0);
			}
			if (apk.getCountBrowse() == null) {
				apk.setCountBrowse(0);;
			}
			if (apk.getCountBrowseBase() == null) {
				apk.setCountBrowseBase(0);;
			}
			if (apk.getCountSearch() == null) {
				apk.setCountSearch(0);;
			}
			if (apk.getCountSearchBase() == null) {
				apk.setCountSearchBase(0);;
			}
			apk.setCountBrowse(apk.getCountBrowse()+apk.getCountBrowseBase());
			apk.setCountDownload(apk.getCountDownload()+apk.getCountDownloadBase());
			apk.setCountSearch(apk.getCountSearch()+apk.getCountSearchBase());
			apk.getMap().put("countBrowse",apk.getCountBrowse());
			apk.getMap().put("countDownload",apk.getCountDownload());
			apk.getMap().put("countSearch",apk.getCountSearch());
			
			//获取开发商名称
			apk.getMap().put("developer",apk.getDeveloper()!=null
					                     &&"1".equals(developerService.load(apk.getDeveloper()).getEnableStatus())
					                     &&"1".equals(developerService.load(apk.getDeveloper()).getStatus())?
					developerService.load(apk.getDeveloper()).getName():"--");
			
		}
		
		PageInfo<AppApk> apk = new PageInfo<AppApk>(list);
		return apk;
	}

	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, AppApk bean) throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
		}
		List<AppDeveloper> developerList = developerService.initQuery().findDelTrue();
		model.addAttribute("developerList",developerList);
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, AppApk bean) throws Exception {
		bean.setUpdateTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setCommentScore(0);
		bean.setCountComment(0);
		bean.setCountBrowse(0);
		bean.setCountDownload(0);
		bean.setCountSearch(0);
		bean.setVersionTime(new Date());
		bean.setRemark("");
		//根据名字自动生成拼音
		bean.setPinyin(PinYinUtils.LogogramToString(bean.getName()));
		bean.setPinyinFull(PinYinUtils.FullToString(bean.getName()));
		service.save(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, AppApk bean) throws Exception {
		//判断是否更改版本
		AppApk oldApk = service.get(bean.getId());
		String oldVersion = oldApk.getApkVersion()!=null ? oldApk.getApkVersion() : "";
		if(!oldVersion.trim().equals(bean.getApkVersion().trim())){
			bean.setVersionTime(new Date());
		}
		bean.setUpdateTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, AppApk bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, AppApk bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, AppApk bean) throws Exception {
		service.delete(request, bean);
	}

	
	@RequestMapping(value = "recommendForm")
	public String gameForm(HttpServletRequest request,
			HttpServletResponse response, Model model, AppApk bean) {
		bean = service.get(bean.getId());
		model.addAttribute("bean", bean);
		// 所有推荐
		List<AppRecommend> recommendList = appservice.initQuery().findStopTrue();
		
		// 已推荐
		List<AppRecommendApk> appRecommendApkList = appRecommendApkService
				.initQuery()
				.andEqualTo("apkId", bean.getId())
				.findAll();
		
		model.addAttribute("recommendList", recommendList);
		model.addAttribute("appRecommendApkList", appRecommendApkList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "recommend")
	public void permission(HttpServletRequest request,
			HttpServletResponse response, Model model, AppApk bean,
			AppRecommendApk bean1, String recommendApkId, String id)
			throws Exception {
		appRecommendApkService.insert(request, bean, recommendApkId);
	}
	
	@RequestMapping(value = "categoryForm")
	public String gameForm2(HttpServletRequest request,
			HttpServletResponse response, Model model, AppApk bean) {
		bean = service.get(bean.getId());
		model.addAttribute("bean", bean);
		// 所有分类
		List<AppCategory> categoryList = categoryservice.initQuery().findStopTrue();
		
		// 已分类
		List<AppCategoryApk> categoryApkList = categoryApkService
				.initQuery()
				.andEqualTo("apkId", bean.getId())
				.findAll();
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categoryApkList",categoryApkList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "category")
	public void permission2(HttpServletRequest request,
			HttpServletResponse response, Model model, AppApk bean,
		    String categoryApkId, String id)
			throws Exception {
		categoryApkService.insert(request, bean, categoryApkId);

	}
	
	@RequestMapping(value = "imageForm")
	public String imageForm(HttpServletRequest request,
			HttpServletResponse response, Model model, AppApk bean) {
		bean = service.get(bean.getId());
		model.addAttribute("bean", bean);
		List<AppImage> imageList = imageService
				.initQuery()
				.andEqualTo("apkId", bean.getId())
				.findAll();
		int count=0;
		
		while(imageList.size()<11){
			AppImage image=new AppImage();
			//判断image表中是否有数据,有数据及imageList.size()>0,则不执行下句
			if(!"icon".equals(image.getCategory())&&count==0&&imageList.size()==0){
				image.setCategory("icon");
			}
			image.setUrl("http://");
			imageList.add(image);
			count++;
		}
		
		model.addAttribute("imageList",imageList);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "image")
	public void permission3(HttpServletRequest request,HttpServletResponse response, Model model,AppImage bean1,String icon)
			throws Exception {
		imageService.insert1(request, bean1,icon);

	}
	
	@RequestMapping(value="recommendImageForm")
	public String recommendImageForm(HttpServletRequest request,
			HttpServletResponse response, Model model, AppApk bean) {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
		}
		List<AppImage> recommendImageList = imageService.initQuery().andEqualTo("apkId", bean.getId()).andEqualTo("category", "recommend").findStopTrue();
		String recommendUrl="";
		if(recommendImageList.size()!=0){
			recommendUrl=recommendImageList.get(0).getUrl();
		}
		model.addAttribute("recommendUrl",recommendUrl);
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "recommendImage")
	public void recommendImage(HttpServletRequest request,HttpServletResponse response, Model model,AppImage bean,String recommendImage)
			throws Exception {
		imageService.insertRecommendImage(request, bean, recommendImage);
	}
		
	
}
