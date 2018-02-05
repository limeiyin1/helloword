package com.redfinger.manager.modules.ranking.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfPicture;
import com.redfinger.manager.common.domain.RfPictureModule;
import com.redfinger.manager.common.domain.RfRanking;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.PictureModuleCodeUtils;
import com.redfinger.manager.modules.member.service.UserDataService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.ranking.service.RankingService;

@Controller
@RequestMapping(value ="/ranking/ranking")
public class RankingController extends BaseController {

	@Autowired
	private RankingService service;
	@Autowired
	private UserService userService;
	@Autowired
	private UserDataService userDataServie;
	
	@RequestMapping(value = "")
	public String toApple(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfRanking> list(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfRanking bean,Integer begin,Integer end,String statTimeStr) throws Exception {
		Date stat = null;
		if(StringUtils.isNotBlank(statTimeStr)){
			stat = DateUtils.parseDate(statTimeStr);
		}
		List<RfRanking> list = service.initQuery(bean)
				.andGreaterThanOrEqualTo("ranking", begin)
				.andLessThanOrEqualTo("ranking", end)
				.andLessThanOrEqualTo("startTime", stat)
				.andGreaterThanOrEqualTo("endTime", stat)
				.addOrderClause("ranking", "asc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null!=list && list.size()>0){
			for (RfRanking ranking : list) {
				ranking.getMap().put("nickName", userDataServie.get(ranking.getUserId()).getNickName());
				ranking.getMap().put("userMobilePhone", userService.get(ranking.getUserId()).getUserMobilePhone());
			}
		}
		
		PageInfo<RfRanking> pageInfo = new PageInfo<RfRanking>(list);
		return pageInfo;
	}
}
