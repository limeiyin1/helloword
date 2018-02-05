package com.redfinger.manager.modules.gather.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGatherScript;
import com.redfinger.manager.common.domain.RfGatherScriptExample;
import com.redfinger.manager.common.gather.domain.RfGatherExpand;
import com.redfinger.manager.common.gather.domain.RfGatherExpandExample;
import com.redfinger.manager.common.gather.mapper.RfGatherExpandMapper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGatherScriptMapper;



@Transactional(value = "secondSource")
@Service
@PrimaryKeyAnnotation(field = "expandId")
public class GatherExpandService extends BaseService<RfGatherExpand, RfGatherExpandExample, RfGatherExpandMapper>{

}
