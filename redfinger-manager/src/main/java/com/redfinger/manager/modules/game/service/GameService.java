package com.redfinger.manager.modules.game.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfGame;
import com.redfinger.manager.common.domain.RfGameExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGameMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "gameId")
public class GameService extends BaseService<RfGame, RfGameExample,RfGameMapper> {
	@Autowired
	private RfGameMapper gameMapper;

	public RfGame getGameBySoftType(String softType) throws Exception{
		RfGameExample example = new RfGameExample();
		example.createCriteria().andSoftTypeEqualTo(softType).andDisclaimerIsNotNull()
		.andStatusEqualTo(YesOrNoStatus.YES).andEnableStatusEqualTo(YesOrNoStatus.YES);
		RfGame game = gameMapper.selectOneByExample(example);
		return game;
	}
	
	public void stopGame(HttpServletRequest request,String ids) throws Exception{
		String gameIds[] = ids.split(",");
		for (String gameId : gameIds) {
			RfGame game = gameMapper.selectByPrimaryKey(Integer.parseInt(gameId));
			game.setModifier(SessionUtils.getCurrentUserId(request));
			game.setEnableStatus(YesOrNoStatus.NO);
			game.setModifyTime(new Date());
			gameMapper.updateByPrimaryKey(game);
		}
	}
	
	public void startGame(HttpServletRequest request,String ids) throws Exception{
		String gameIds[] = ids.split(",");
		for (String gameId : gameIds) {
			RfGame game = gameMapper.selectByPrimaryKey(Integer.parseInt(gameId));
			game.setModifier(SessionUtils.getCurrentUserId(request));
			game.setEnableStatus(YesOrNoStatus.YES);
			game.setModifyTime(new Date());
			gameMapper.updateByPrimaryKey(game);
		}
	}
}
