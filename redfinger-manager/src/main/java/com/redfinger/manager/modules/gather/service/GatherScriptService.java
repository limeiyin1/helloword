package com.redfinger.manager.modules.gather.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGatherScript;
import com.redfinger.manager.common.domain.RfGatherScriptExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGatherScriptMapper;



@Transactional
@Service
@PrimaryKeyAnnotation(field = "scriptId")
public class GatherScriptService extends BaseService<RfGatherScript, RfGatherScriptExample, RfGatherScriptMapper>{

}
