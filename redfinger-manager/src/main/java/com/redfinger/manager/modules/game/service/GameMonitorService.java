package com.redfinger.manager.modules.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGameMonitorCfg;
import com.redfinger.manager.common.domain.RfGameMonitorCfgExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGameMonitorCfgMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "cfgId")
public class GameMonitorService extends BaseService<RfGameMonitorCfg, RfGameMonitorCfgExample, RfGameMonitorCfgMapper> {

}
