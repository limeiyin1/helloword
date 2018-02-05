package com.redfinger.manager.modules.market.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfChannel;
import com.redfinger.manager.common.domain.RfChannelExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfChannelMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "channelId")
public class MarketChannelService extends BaseService<RfChannel, RfChannelExample, RfChannelMapper> {

}
