package com.redfinger.manager.modules.group.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfUserPadGroup;
import com.redfinger.manager.common.domain.RfUserPadGroupExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfUserPadGroupMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "userPadGroupId")
public class UserPadGroupService  extends BaseService<RfUserPadGroup, RfUserPadGroupExample, RfUserPadGroupMapper> {
	
	@Autowired
	private RfUserPadGroupMapper userPadGroupMapper;
	
	public int deleteByUserPadId(Integer id){
		return userPadGroupMapper.deleteByUserPadId(id);
	}
	
	
	
	//deleteByGroupIds
	public int deleteByGroupIds(String groupIds) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] gro = groupIds.split(",");
		List<Integer> list = new ArrayList<>();
		for(int i=0;i<gro.length;i++){
			list.add(Integer.valueOf(gro[i]));
		}
		map.put("groupIdIn", list);
		return userPadGroupMapper.deleteByGroupIds(map);
	}
	
	
}
