package com.redfinger.manager.modules.broadcast.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfBroadcast;
import com.redfinger.manager.common.domain.RfBroadcastExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfBroadcastMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="broadcastId")
public class BroadcastService extends BaseService<RfBroadcast, RfBroadcastExample, RfBroadcastMapper> {

}
