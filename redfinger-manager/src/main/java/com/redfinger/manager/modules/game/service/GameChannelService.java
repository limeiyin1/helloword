package com.redfinger.manager.modules.game.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGameChannel;
import com.redfinger.manager.common.domain.RfGameChannelExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGameChannelMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "channelId")
public class GameChannelService extends BaseService<RfGameChannel, RfGameChannelExample, RfGameChannelMapper> {

	@Autowired
	private RfGameChannelMapper mapper;
	
	public List<RfGameChannel> selectByChannelName(String channelName){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("channelNameLike", channelName);
		params.put("status", YesOrNoStatus.YES);
		params.put("enableStatus", YesOrNoStatus.YES);
		return mapper.selectByParams(params);
	}
	
}
