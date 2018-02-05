package com.redfinger.manager.modules.market.service;

import org.springframework.stereotype.Service;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppFeedback;
import com.redfinger.manager.common.domain.AppFeedbackExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppFeedbackMapper;


@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketAppFeedBackService extends BaseService<AppFeedback,AppFeedbackExample, AppFeedbackMapper >{

}
