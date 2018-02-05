package com.redfinger.manager.modules.market.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.MarketGame;
import com.redfinger.manager.common.domain.MarketGameExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.MarketGameMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketGameService extends BaseService<MarketGame, MarketGameExample, MarketGameMapper> {

}
