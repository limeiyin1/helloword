package com.redfinger.manager.modules.facility.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfVideo;
import com.redfinger.manager.common.domain.RfVideoExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfVideoMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "videoId")
public class VideoService extends BaseService<RfVideo, RfVideoExample, RfVideoMapper> {

}
