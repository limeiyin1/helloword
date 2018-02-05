package com.redfinger.manager.modules.ranking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfRanking;
import com.redfinger.manager.common.domain.RfRankingExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRankingMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="rankingId")
public class RankingService extends BaseService<RfRanking,RfRankingExample,RfRankingMapper> {

}
