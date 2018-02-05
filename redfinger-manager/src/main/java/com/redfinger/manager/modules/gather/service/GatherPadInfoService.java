package com.redfinger.manager.modules.gather.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGatherScript;
import com.redfinger.manager.common.domain.RfGatherScriptExample;
import com.redfinger.manager.common.gather.domain.RfGatherDeviceInfo;
import com.redfinger.manager.common.gather.domain.RfGatherPadInfo;
import com.redfinger.manager.common.gather.domain.RfGatherPadInfoExample;
import com.redfinger.manager.common.gather.mapper.RfGatherDeviceInfoMapper;
import com.redfinger.manager.common.gather.mapper.RfGatherPadInfoMapper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGatherScriptMapper;



@Transactional(value = "secondSource")
@Service
@PrimaryKeyAnnotation(field = "padInfoId")
public class GatherPadInfoService extends BaseService<RfGatherPadInfo, RfGatherPadInfoExample, RfGatherPadInfoMapper>{
	
	@Autowired
	RfGatherPadInfoMapper rfGatherPadInfoMapper;
	
	public RfGatherPadInfo selectOneByMap(RfGatherPadInfo info){
		return rfGatherPadInfoMapper.selectOneByMap(info);
	}
	
	
	public List<RfGatherPadInfo> selectAllByIp(RfGatherPadInfo info) {
		return rfGatherPadInfoMapper.selectAllByIp(info);
	}
}
