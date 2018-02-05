package com.redfinger.manager.modules.game.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGameApp;
import com.redfinger.manager.common.domain.RfGameAppExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGameAppMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "gameAppId")
public class GameAppService extends BaseService<RfGameApp, RfGameAppExample, RfGameAppMapper> {
	
	@Autowired
	private RfGameAppMapper mapper;
	
	public List<RfGameApp> selectRfGameAppByName(String gameAppName){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("gameAppNameLike", gameAppName);
		params.put("enableStatus", YesOrNoStatus.YES);
		params.put("status", YesOrNoStatus.YES);
		return mapper.selectByMap(params);
	}
	
	
}
