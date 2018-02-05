package com.redfinger.manager.modules.label.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfPadLabel;
import com.redfinger.manager.common.domain.RfPadLabelExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPadLabelMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "padLabelId")
public class PadLabelService extends BaseService<RfPadLabel, RfPadLabelExample, RfPadLabelMapper> {

}
