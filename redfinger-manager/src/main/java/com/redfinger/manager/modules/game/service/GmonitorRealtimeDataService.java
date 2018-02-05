package com.redfinger.manager.modules.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGmonitorRealtimeData;
import com.redfinger.manager.common.domain.RfGmonitorRealtimeDataExample;
import com.redfinger.manager.common.mapper.RfGmonitorRealtimeDataMapper;

@Transactional
@Service
public class GmonitorRealtimeDataService extends BaseService<RfGmonitorRealtimeData, RfGmonitorRealtimeDataExample, RfGmonitorRealtimeDataMapper> {

}
