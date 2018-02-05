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
import com.redfinger.manager.common.domain.RfGameApp;
import com.redfinger.manager.common.domain.RfMarketGameApp;
import com.redfinger.manager.modules.game.service.GameAppService;
import com.redfinger.manager.modules.game.service.MarketGameAppService;

@Controller
@RequestMapping(value = "/game/marketGameApp")
public class MarketGameAppController extends BaseController {
	
	@Autowired
	private MarketGameAppService service;
	@Autowired
	private GameAppService gameAppService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfGameApp> gameApps = gameAppService.initQuery().findStopTrue();
		model.addAttribute("gameApps", gameApps);
		return this.toPage(request, response, model);  
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfMarketGameApp> list(HttpServletRequest request, HttpServletResponse response, Model model, RfMarketGameApp bean) throws Exception {
		List<RfMarketGameApp> list = service.initQuery(bean)
				.andEqualTo("gameAppId", bean.getGameAppId())
				.addOrderClause("createTime", "desc")
				.addOrderClause("relationId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())	
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size() > 0){
			for (RfMarketGameApp rfMarketGameApp : list) {
				if(null != rfMarketGameApp.getGameAppId()){
					RfGameApp gameApp = gameAppService.get(rfMarketGameApp.getGameAppId());
					rfMarketGameApp.getMap().put("gameAppName", gameApp.getGameAppName());
				}
			}
		}
		
		PageInfo<RfMarketGameApp> pageInfo = new PageInfo<RfMarketGameApp>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfMarketGameApp bean) throws Exception {
		service.remove(request, bean);
	}
}
