package com.redfinger.manager.modules.market.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.poifs.property.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfChannel;
import com.redfinger.manager.common.domain.RfChannelGrade;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.market.service.MarketChannelGradeService;

@Controller
@RequestMapping(value = "/channel/grade")
public class MarketChannelGradeController extends BaseController {

	@Autowired
	MarketChannelGradeService service;
	
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		List<RfChannelGrade> firstGradeIds = service.initQuery()
				.andEqualTo("channelGrade", MarketChannelGradeService.FIRST_GRADE)
				.findDelTrue();
		model.addAttribute("firstGradeIds", firstGradeIds);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfChannelGrade> list(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelGrade bean,Integer firstGradeId) throws Exception {
		List<RfChannelGrade> list = service.initQuery(bean)
				.andEqualTo("channelGrade", bean.getChannelGrade())
				.andEqualTo("parentId", firstGradeId)
				.addOrderClause("reorder", "asc")
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		for(RfChannelGrade channelGrade : list){
			RfChannelGrade parent =  service.get(channelGrade.getParentId());
			if(null!=parent){
				channelGrade.setParentName(parent.getGradeName());
			}
		}
		
		PageInfo<RfChannelGrade> pageInfo = new PageInfo<RfChannelGrade>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model,RfChannelGrade bean,Integer pk) throws Exception{
		boolean isHide = true;
		if(null==pk){
			
		}else{
			RfChannelGrade channelGrade = service.get(pk);
			if(channelGrade==null){
				
			}else{
				if(MarketChannelGradeService.FIRST_GRADE.equals(channelGrade.getChannelGrade())){
					isHide = false;
				}else{
					
				}
			}
		}
		
		if(null==bean.getGradeId()){//新增
			if(isHide){
				model.addAttribute("isHide", "hide");
				model.addAttribute("channelGrade", MarketChannelGradeService.FIRST_GRADE);
				model.addAttribute("channelGradeName", MarketChannelGradeService.FIRST_GRADE_NAME);
			}else{
				
				RfChannelGrade parent = service.get(pk);
				model.addAttribute("parentId", pk);
				model.addAttribute("parentName", parent.getGradeName());
				
				model.addAttribute("isHide", "");
				model.addAttribute("channelGrade", MarketChannelGradeService.SECOND_GRADE);
				model.addAttribute("channelGradeName", MarketChannelGradeService.SECOND_GRADE_NAME);
			}
			
		}else{//编辑
			bean=service.get(bean.getGradeId());
			model.addAttribute("bean", bean);
			
			if(bean.getChannelGrade()==null||MarketChannelGradeService.FIRST_GRADE.equals(bean.getChannelGrade())){
				model.addAttribute("channelGrade", MarketChannelGradeService.FIRST_GRADE);
				model.addAttribute("channelGradeName", MarketChannelGradeService.FIRST_GRADE_NAME);
				model.addAttribute("isHide", "hide");
			}else{
				RfChannelGrade parent = service.get(bean.getParentId());
				model.addAttribute("parentId", bean.getParentId());
				model.addAttribute("parentName", parent.getGradeName());
				
				model.addAttribute("channelGrade", MarketChannelGradeService.SECOND_GRADE);
				model.addAttribute("channelGradeName", MarketChannelGradeService.SECOND_GRADE_NAME);
				model.addAttribute("isHide", "");
			}
			
			
			
		}
		
		List<RfChannelGrade> list = service.initQuery()
				.andEqualTo("channelGrade", MarketChannelGradeService.FIRST_GRADE)
				.findDelTrue();
		model.addAttribute("list", list);
		
		return this.toPage(request, response, model);
	}
	
	
	
	
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelGrade bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelGrade bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelGrade bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelGrade bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfChannelGrade bean) throws Exception {
		String[] idArray=StringUtils.split(bean.getIds(),",");
		List<String> idsAll = new ArrayList<>();
		for(String idStr:idArray){
			bean = service.get(Integer.valueOf(idStr));
			idsAll.add(idStr);
			if(MarketChannelGradeService.FIRST_GRADE.equals(bean.getChannelGrade())){
				List<RfChannelGrade> channelGrades = service.initQuery().andEqualTo("parentId", bean.getGradeId()).findDelTrue();
				for(RfChannelGrade channelGrade : channelGrades){
					idsAll.add(channelGrade.getGradeId().toString());
				}
			}
		}
		String ids = org.apache.commons.lang3.StringUtils.join(idsAll, ",");
		bean.setIds(ids);
		
		service.delete(request, bean);
	}
	
	

	
}
