package com.redfinger.manager.modules.game.web;

import java.util.ArrayList;
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
import com.redfinger.manager.common.domain.RfMarketGame;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.modules.game.service.GameService;
import com.redfinger.manager.modules.game.service.RfMarketGameService;

@Controller
@RequestMapping(value = "/game/marketGame")
public class MarketGameRecommandController extends BaseController {
	
	@Autowired
	private RfMarketGameService service;
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfMarketGame> marketGame = service.initQuery().andEqualTo("marketType", OsTypeUtils.demoMarket).findAll();
		List<Integer> gameIds = new ArrayList<Integer>();
		for (RfMarketGame rfMarketGame : marketGame) {
			gameIds.add(rfMarketGame.getGameId());
		}
		
		List<RfGame> games = gameService.initQuery().andIn("gameId", gameIds).findStopTrue();
		model.addAttribute("games", games);
		return this.toPage(request, response, model);  
	}
	

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfMarketGame> list(HttpServletRequest request, HttpServletResponse response, Model model, RfMarketGame bean) throws Exception {
		List<RfMarketGame> list = service.initQuery(bean)
				.andEqualTo("gameId", bean.getGameId())
				.addOrderClause("createTime", "desc")
				.addOrderClause("relationId", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())	
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size() > 0){
			for (RfMarketGame rfMarketGame : list) {
				if(null != rfMarketGame.getGameId()){
					RfGame game = gameService.get(rfMarketGame.getGameId());
					rfMarketGame.getMap().put("gameName", game.getGameName());
				}
			}
		}
		
		PageInfo<RfMarketGame> pageInfo = new PageInfo<RfMarketGame>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfMarketGame bean) throws Exception {
		service.remove(request, bean);
	}
}
