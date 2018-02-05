package com.redfinger.manager.modules.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfAppWhiteList;
import com.redfinger.manager.common.domain.RfAppWhiteListExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfAppWhiteListMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "appWhiteId")
public class GameAppWhiteListService extends BaseService<RfAppWhiteList, RfAppWhiteListExample, RfAppWhiteListMapper> {

	
	
}
