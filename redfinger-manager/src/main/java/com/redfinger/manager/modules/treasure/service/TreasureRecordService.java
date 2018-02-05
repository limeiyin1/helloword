package com.redfinger.manager.modules.treasure.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfTreasureRecord;
import com.redfinger.manager.common.domain.RfTreasureRecordExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfTreasureRecordMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "recordId")
public class TreasureRecordService extends BaseService<RfTreasureRecord, RfTreasureRecordExample, RfTreasureRecordMapper> {

}
