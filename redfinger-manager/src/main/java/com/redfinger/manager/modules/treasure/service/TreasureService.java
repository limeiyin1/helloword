package com.redfinger.manager.modules.treasure.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfTreasure;
import com.redfinger.manager.common.domain.RfTreasureExample;
import com.redfinger.manager.common.domain.RfTreasurePeriod;
import com.redfinger.manager.common.domain.RfTreasurePeriodExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfTreasureMapper;
import com.redfinger.manager.common.mapper.RfTreasurePeriodMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "treasureId")
public class TreasureService extends BaseService<RfTreasure, RfTreasureExample, RfTreasureMapper> {

	@Autowired
	private RfTreasureMapper mapper;
	@Autowired
	private RfTreasurePeriodMapper periodMapper;
	
	public void updateTreasure(HttpServletRequest request,RfTreasure bean) throws Exception{
		this.update(request, bean);
		
		RfTreasurePeriodExample example = new RfTreasurePeriodExample();
		example.createCriteria().andTreasureIdEqualTo(bean.getTreasureId());
		List<RfTreasurePeriod> list = periodMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			for (RfTreasurePeriod rfTreasurePeriod : list) {
				rfTreasurePeriod.setNumberPrefix(bean.getNumberPrefix());
				rfTreasurePeriod.setNumberBase(bean.getNumberBase());
				rfTreasurePeriod.setTreasureMoney(bean.getTreasureMoney());
				rfTreasurePeriod.setRedCoin(bean.getRedCoin());
				rfTreasurePeriod.setTreasureFrequency(bean.getTreasureFrequency());
				rfTreasurePeriod.setActivationCodeType(bean.getActivationCodeType());
				periodMapper.updateByPrimaryKeySelective(rfTreasurePeriod);
			}
		}
	}
}
