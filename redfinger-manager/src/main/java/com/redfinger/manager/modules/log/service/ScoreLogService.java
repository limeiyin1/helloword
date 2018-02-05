package com.redfinger.manager.modules.log.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfScoreLog;
import com.redfinger.manager.common.domain.RfScoreLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfScoreLogMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "logId")
public class ScoreLogService extends BaseService<RfScoreLog, RfScoreLogExample, RfScoreLogMapper> {
	
	
}
