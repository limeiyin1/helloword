package com.redfinger.manager.modules.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfPayTradeLog;
import com.redfinger.manager.common.domain.RfPayTradeLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPayTradeLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "tradeLogId")
public class PayTradeLogService extends BaseService<RfPayTradeLog, RfPayTradeLogExample, RfPayTradeLogMapper> {
	
	@Autowired
	private RfPayTradeLogMapper payTradeLogMapper;
	
	/**
	 * 
	 * 根据条件统计交易总金额
	 * @param example
	 * @return Integer 
	 */
	public Long tradeMoneySumByExample(RfPayTradeLogExample example){
		return payTradeLogMapper.tradeMoneySumByExample(example);
	}

}
