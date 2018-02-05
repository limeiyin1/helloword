package com.redfinger.manager.modules.market.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppRecommend;
import com.redfinger.manager.common.domain.AppRecommendExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppRecommendMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketAppRecommendService extends BaseService<AppRecommend, AppRecommendExample,AppRecommendMapper>{

	
}
