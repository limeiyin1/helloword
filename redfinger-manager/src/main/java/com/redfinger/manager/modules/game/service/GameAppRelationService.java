package com.redfinger.manager.modules.game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGameAppRelation;
import com.redfinger.manager.common.domain.RfGameAppRelationExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGameAppRelationMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "relationId")
public class GameAppRelationService extends BaseService<RfGameAppRelation, RfGameAppRelationExample, RfGameAppRelationMapper> {

	@Autowired
	private RfGameAppRelationMapper mapper;
	
	public int selectGameAppRelation(Integer gameAppId,Integer gameId){
		RfGameAppRelationExample example = new RfGameAppRelationExample();
		example.createCriteria().andGameAppIdEqualTo(gameAppId).andGameIdEqualTo(gameId)
		.andStatusEqualTo(YesOrNoStatus.YES).andEnableStatusEqualTo(YesOrNoStatus.YES);
		return mapper.countByExample(example);
	}
	
	public List<RfGameAppRelation> selectGameAppRelationByGameId(Integer gameAppId){
		RfGameAppRelationExample example = new RfGameAppRelationExample();
		example.createCriteria().andGameAppIdEqualTo(gameAppId)
		.andStatusEqualTo(YesOrNoStatus.YES).andEnableStatusEqualTo(YesOrNoStatus.YES);
		return mapper.selectByExample(example);
	}
	
	public void saveRelation(List<RfGameAppRelation> list){
		if(null != list && list.size() > 0){
			mapper.insertBatch(list);
		}
	}
	
	public int deleteByPrimaryKey(Integer relationId){
		if(relationId != null ){
			return mapper.deleteByPrimaryKey(relationId);
		}
		return 0;
	}
}
