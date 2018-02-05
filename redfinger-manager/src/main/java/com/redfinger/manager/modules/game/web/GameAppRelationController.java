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
import com.redfinger.manager.common.domain.RfGame;
import com.redfinger.manager.common.domain.RfGameApp;
import com.redfinger.manager.common.domain.RfGameAppRelation;
import com.redfinger.manager.modules.game.service.GameAppRelationService;
import com.redfinger.manager.modules.game.service.GameAppService;
import com.redfinger.manager.modules.game.service.GameService;

@Controller
@RequestMapping(value = "/game/relation")
public class GameAppRelationController extends BaseController {
	@Autowired
	private GameAppRelationService service;
	@Autowired
	private GameAppService gameAppService;
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfGameApp> gameApps = gameAppService.initQuery().findStopTrue();
		model.addAttribute("gameApps", gameApps);
		return this.toPage(request, response, model);  
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfGameAppRelation> list(HttpServletRequest request, HttpServletResponse response, Model model, RfGameAppRelation bean) throws Exception {
		List<RfGameAppRelation> list = service.initQuery(bean)
				.andEqualTo("gameAppId", bean.getGameAppId())
				.addOrderClause("createTime", "desc")
				.addOrderClause("relationId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())	
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size() > 0){
			for (RfGameAppRelation relation : list) {
				if(null != relation.getGameId()){
					RfGame game = gameService.get(relation.getGameId());
					relation.getMap().put("gameName", game.getGameName());
				}
				if(null != relation.getGameAppId()){
					RfGameApp gameApp = gameAppService.get(relation.getGameAppId());
					relation.getMap().put("gameAppName", gameApp.getGameAppName());
				}
			}
		}
		
		PageInfo<RfGameAppRelation> pageInfo = new PageInfo<RfGameAppRelation>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfGameAppRelation bean) throws Exception {
		service.remove(request, bean);
	}
}
