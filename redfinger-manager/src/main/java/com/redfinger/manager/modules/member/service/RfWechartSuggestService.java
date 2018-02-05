package com.redfinger.manager.modules.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfWechartSuggest;
import com.redfinger.manager.common.domain.RfWechartSuggestExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfWechartSuggestMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class RfWechartSuggestService extends BaseService<RfWechartSuggest, RfWechartSuggestExample, RfWechartSuggestMapper> {

}
