package com.redfinger.manager.modules.treasure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfTreasureLottery;
import com.redfinger.manager.common.domain.RfTreasureLotteryExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfTreasureLotteryMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "lotteryId")
public class TreasureLotteryService extends BaseService<RfTreasureLottery, RfTreasureLotteryExample, RfTreasureLotteryMapper> {
	
	@Autowired
	private RfTreasureLotteryMapper mapper;
	
	public RfTreasureLottery selectTreasureLottery(Integer treasureId,Integer periodId){
		RfTreasureLotteryExample example = new RfTreasureLotteryExample();
		example.createCriteria().andTreasureIdEqualTo(treasureId).andPeriodIdEqualTo(periodId);
		return mapper.selectOneByExample(example);
	}

}
