package com.redfinger.manager.modules.base.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfPadRbcStandard;
import com.redfinger.manager.common.domain.RfPadRbcStandardExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPadRbcStandardMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field="standardId")
public class PadRbcExchangeService extends BaseService<RfPadRbcStandard, RfPadRbcStandardExample, RfPadRbcStandardMapper> {

}
