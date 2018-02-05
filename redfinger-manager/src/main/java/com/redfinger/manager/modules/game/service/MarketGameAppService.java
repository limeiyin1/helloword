package com.redfinger.manager.modules.game.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGameApp;
import com.redfinger.manager.common.domain.RfMarketGameApp;
import com.redfinger.manager.common.domain.RfMarketGameAppExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfMarketGameAppMapper;
import com.redfinger.manager.common.utils.OsTypeUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "relationId")
public class MarketGameAppService extends BaseService<RfMarketGameApp, RfMarketGameAppExample, RfMarketGameAppMapper> {

	@Autowired
	private RfMarketGameAppMapper mapper;
	@Autowired
	private GameAppService service;
	
	public void saveMarketGameApp(Integer gameAppId,String marketType,String creater){
		RfMarketGameApp marketGameApp = new RfMarketGameApp();
		marketGameApp.setGameAppId(gameAppId);
		marketGameApp.setMarketType(marketType);
		marketGameApp.setStatus(YesOrNoStatus.YES);
		marketGameApp.setEnableStatus(YesOrNoStatus.YES);
		marketGameApp.setCreater(creater);
		marketGameApp.setCreateTime(new Date());
		mapper.insertSelective(marketGameApp);
	}
	
	/**
	 * 关联多个试玩应用市场检验
	 * @param idArray
	 * @param request
	 */
	public void saveMarkGameAppForIds(String[] idArray,HttpServletRequest request){
		for (String id : idArray) {
			RfGameApp gameApp = service.get(Integer.parseInt(id));
			
			if(null == gameApp){
				throw new BusinessException("请选择游戏应用不存在");
			}
			if(StringUtils.equals(gameApp.getEnableStatus(), YesOrNoStatus.NO)){
				throw new BusinessException(gameApp.getGameAppName()+"已经禁用");
			}
			if(StringUtils.equals(gameApp.getStatus(), YesOrNoStatus.NO)){
				throw new BusinessException(gameApp.getGameAppName()+"已经删除");
			}
			
			List<RfMarketGameApp> list = this.initQuery().andEqualTo("gameAppId", Integer.parseInt(id)).andEqualTo("marketType", OsTypeUtils.demoMarket).findAll();
			if(null != list && list.size() > 0){
				throw new BusinessException(gameApp.getGameAppName()+"已经关联试玩应用市场");
			}
			this.saveMarketGameApp(Integer.parseInt(id), OsTypeUtils.demoMarket, SessionUtils.getCurrentUserId(request));
		
		}
	}
}
