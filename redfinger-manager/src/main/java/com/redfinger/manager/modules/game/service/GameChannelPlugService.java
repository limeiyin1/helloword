package com.redfinger.manager.modules.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGameChannelPlug;
import com.redfinger.manager.common.domain.RfGameChannelPlugExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGameChannelPlugMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "channelPlugId")
public class GameChannelPlugService extends BaseService<RfGameChannelPlug, RfGameChannelPlugExample, RfGameChannelPlugMapper> {

}
