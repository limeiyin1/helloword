package com.redfinger.manager.modules.gather.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGatherScript;
import com.redfinger.manager.common.domain.RfGatherScriptExample;
import com.redfinger.manager.common.gather.domain.RfGatherFieldDefine;
import com.redfinger.manager.common.gather.domain.RfGatherFieldDefineExample;
import com.redfinger.manager.common.gather.mapper.RfGatherFieldDefineMapper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGatherScriptMapper;



@Transactional(value = "secondSource")
@Service
@PrimaryKeyAnnotation(field = "fieldCode")
public class GatherFieldDefineService extends BaseService<RfGatherFieldDefine, RfGatherFieldDefineExample, RfGatherFieldDefineMapper>{

}
