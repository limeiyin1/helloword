package com.redfinger.manager.modules.treasure.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfTreasureNumber;
import com.redfinger.manager.common.domain.RfTreasureNumberExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfTreasureNumberMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "numberId")
public class TreasureNumberService extends BaseService<RfTreasureNumber, RfTreasureNumberExample, RfTreasureNumberMapper> {

}
