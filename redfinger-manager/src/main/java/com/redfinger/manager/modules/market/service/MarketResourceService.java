package com.redfinger.manager.modules.market.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.MarketResource;
import com.redfinger.manager.common.domain.MarketResourceExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.MarketResourceMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketResourceService extends BaseService<MarketResource, MarketResourceExample, MarketResourceMapper> {

}
