package com.redfinger.manager.modules.gather.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGatherScript;
import com.redfinger.manager.common.domain.RfGatherScriptExample;
import com.redfinger.manager.common.gather.domain.RfGatherDeviceInfo;
import com.redfinger.manager.common.gather.domain.RfGatherDeviceInfoExample;
import com.redfinger.manager.common.gather.mapper.RfGatherDeviceInfoMapper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfGatherScriptMapper;



@Transactional(value = "secondSource")
@Service
@PrimaryKeyAnnotation(field = "deviceInfoId")
public class GatherDeviceInfoService extends BaseService<RfGatherDeviceInfo, RfGatherDeviceInfoExample, RfGatherDeviceInfoMapper>{

	
	@Autowired
	RfGatherDeviceInfoMapper rfGatherDeviceInfoMapper;
	
	public RfGatherDeviceInfo selectOneByMap(RfGatherDeviceInfo info){
		return rfGatherDeviceInfoMapper.selectOneByMap(info);
	}
	
	
	public List<RfGatherDeviceInfo> selectAllByIp(RfGatherDeviceInfo info) {
		return rfGatherDeviceInfoMapper.selectAllByIp(info);
	}
}
