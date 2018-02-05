package com.redfinger.manager.modules.treasure.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfTreasure;
import com.redfinger.manager.common.domain.RfTreasurePeriod;
import com.redfinger.manager.common.domain.RfTreasurePeriodExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfTreasureMapper;
import com.redfinger.manager.common.mapper.RfTreasurePeriodMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "periodId")
public class TreasurePeriodService extends BaseService<RfTreasurePeriod, RfTreasurePeriodExample, RfTreasurePeriodMapper> {
	
	@Autowired
	private RfTreasureMapper treasureMapper;
	@Autowired
	private RfTreasurePeriodMapper mapper;
	
	public void saveTreasurePeriod(HttpServletRequest request,RfTreasurePeriod bean) throws Exception{
		RfTreasure treasure = treasureMapper.selectByPrimaryKey(bean.getTreasureId());
		treasure.setCurrentPeriod(bean.getCurrentPeriod());
		
		if(bean.getEndTime().getTime() > treasure.getEndTime().getTime()){
			throw new BusinessException("分期的结束时间不能大于总起的结束时间");
		}
		
		if(bean.getCurrentPeriod() > treasure.getTreasurePeriod()){//当前期数不能大于总期数
			throw new BusinessException("当前期数不能大于总期数");
		}
		
		
		bean.setNumberPrefix(treasure.getNumberPrefix());
		bean.setNumberBase(treasure.getNumberBase());
		bean.setTreasureMoney(treasure.getTreasureMoney());
		bean.setRedCoin(treasure.getRedCoin());
		bean.setTreasureFrequency(treasure.getTreasureFrequency());
		bean.setRemainderFrequency(treasure.getTreasureFrequency());
		bean.setActivationCodeType(treasure.getActivationCodeType());
		this.save(request, bean);
		treasureMapper.updateByPrimaryKey(treasure);
	}

	
	public void updateTreasurePeriod(HttpServletRequest request,RfTreasurePeriod bean) throws Exception{
		RfTreasure treasure = treasureMapper.selectByPrimaryKey(bean.getTreasureId());
		treasure.setCurrentPeriod(bean.getCurrentPeriod());
		
		if(bean.getEndTime().getTime() > treasure.getEndTime().getTime()){
			throw new BusinessException("分期的结束时间不能大于总起的结束时间");
		}
		
		if(bean.getCurrentPeriod() > treasure.getTreasurePeriod()){//当前期数不能大于总期数
			throw new BusinessException("当前期数不能大于总期数");
		}
		
		Integer treasureFrequency = 0;
		treasureFrequency = treasureFrequency + bean.getTreasureFrequency();
		RfTreasurePeriodExample example = new RfTreasurePeriodExample();
		example.createCriteria().andTreasureIdEqualTo(bean.getTreasureId())
		.andPeriodIdNotEqualTo(bean.getPeriodId())
		.andStatusEqualTo(YesOrNoStatus.YES).andEnableStatusEqualTo(YesOrNoStatus.YES);
		List<RfTreasurePeriod> list = mapper.selectByExample(example);
		if(null != list && list.size() > 0){
			for (RfTreasurePeriod rfTreasurePeriod : list) {
				treasureFrequency = treasureFrequency + rfTreasurePeriod.getTreasureFrequency();
			}
		}
		treasure.setTreasureFrequency(treasureFrequency);
		
		
		this.save(request, bean);
		treasureMapper.updateByPrimaryKey(treasure);
		
	}
	
	
	public int selectCountByTreasureId(Integer treasureId){
		RfTreasurePeriodExample example = new RfTreasurePeriodExample();
		example.createCriteria().andTreasureIdEqualTo(treasureId);
		return mapper.countByExample(example);
	}
	
	public void savePeriod(RfTreasurePeriod period,RfTreasure treasure ){
		treasure.setCurrentPeriod(period.getCurrentPeriod());
		
		treasureMapper.updateByPrimaryKey(treasure);
		mapper.insert(period);
	}

}
