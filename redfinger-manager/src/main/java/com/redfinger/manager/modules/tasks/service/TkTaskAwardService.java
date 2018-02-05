package com.redfinger.manager.modules.tasks.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TkTaskAward;
import com.redfinger.manager.common.domain.TkTaskAwardExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TkTaskAwardMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "awardId")
public class TkTaskAwardService extends BaseService<TkTaskAward, TkTaskAwardExample, TkTaskAwardMapper> {
	
}
