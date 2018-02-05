package com.redfinger.manager.modules.activity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfActivityGoods;
import com.redfinger.manager.common.domain.RfActivityGoodsExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfActivityGoodsMapper;
import com.redfinger.manager.modules.activity.dto.RfActivityGoodsDto;

@Transactional
@Service
@PrimaryKeyAnnotation(field="actGoodsId")
public class ActivityGoodsService extends BaseService<RfActivityGoods, RfActivityGoodsExample, RfActivityGoodsMapper> {

	@Autowired
	private RfActivityGoodsMapper rfActivityGoodsMapper;
	
	public List<RfActivityGoodsDto> selectActivityGoods(Integer activityId){
		return rfActivityGoodsMapper.selectByActivityId(activityId);
	}
}
