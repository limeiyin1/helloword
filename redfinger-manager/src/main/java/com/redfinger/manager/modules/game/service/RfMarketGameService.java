package com.redfinger.manager.modules.game.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGame;
import com.redfinger.manager.common.domain.RfGameApp;
import com.redfinger.manager.common.domain.RfMarketGame;
import com.redfinger.manager.common.domain.RfMarketGameApp;
import com.redfinger.manager.common.domain.RfMarketGameExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfMarketGameMapper;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "relationId")
public class RfMarketGameService extends BaseService<RfMarketGame, RfMarketGameExample, RfMarketGameMapper> {

	@Autowired
	private RfMarketGameMapper marketGameMapper;
	@Autowired
	GameService service;
	
	
	public void saveMarketGame(Integer gameId,String marketType,String creater){
		RfMarketGame marketGame = new RfMarketGame();
		marketGame.setGameId(gameId);
		marketGame.setMarketType(marketType);
		marketGame.setStatus(YesOrNoStatus.YES);
		marketGame.setEnableStatus(YesOrNoStatus.YES);
		marketGame.setCreater(creater);
		marketGame.setCreateTime(new Date());
		marketGameMapper.insertSelective(marketGame);
	}
	
	/**
	 * 关联多个试玩应用市场检验
	 * @param idArray
	 * @param request
	 */
	public void saveMarkGameAppForIds(String[] idArray,HttpServletRequest request){
		for (String id : idArray) {
			
			RfGame game = service.get(Integer.parseInt(id));
			
			if(null == game){
				throw new BusinessException("请选择游戏不存在");
			}
			if(StringUtils.equals(game.getEnableStatus(), YesOrNoStatus.NO)){
				throw new BusinessException(game.getGameName() + "已经禁用");
			}
			if(StringUtils.equals(game.getStatus(), YesOrNoStatus.NO)){
				throw new BusinessException(game.getGameName() + "已经删除");
			}
			
			List<RfMarketGame> list = this.initQuery().andEqualTo("gameId", Integer.parseInt(id)).andEqualTo("marketType", OsTypeUtils.demoMarket).findAll();
			if(null != list && list.size() > 0){
				throw new BusinessException(game.getGameName() + "已经关联试玩应用市场");
			}
			this.saveMarketGame(Integer.parseInt(id), OsTypeUtils.demoMarket, SessionUtils.getCurrentUserId(request));
		}
	}
}
