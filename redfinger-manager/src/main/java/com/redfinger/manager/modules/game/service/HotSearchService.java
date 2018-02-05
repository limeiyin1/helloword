package com.redfinger.manager.modules.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfHotSearch;
import com.redfinger.manager.common.domain.RfHotSearchExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfHotSearchMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class HotSearchService extends BaseService<RfHotSearch, RfHotSearchExample,RfHotSearchMapper> {

}
