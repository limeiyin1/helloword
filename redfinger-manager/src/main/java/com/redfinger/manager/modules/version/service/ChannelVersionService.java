package com.redfinger.manager.modules.version.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfChannelVersion;
import com.redfinger.manager.common.domain.RfChannelVersionExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfChannelVersionMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="channelVersionId")
public class ChannelVersionService extends BaseService<RfChannelVersion, RfChannelVersionExample, RfChannelVersionMapper> {
	@Autowired
	RfChannelVersionMapper mapper;
	
	public void updateChannelVersion(RfChannelVersion channelVersion){
		mapper.updateByPrimaryKeySelective(channelVersion);
	}
	
	public void saveChannelVersion(RfChannelVersion channelVersion){
		mapper.insertSelective(channelVersion);
	}
	
	/**
	 * 根据渠道编码，渠道版本编号，版本类型
	 * @param channelCode
	 * @param channelVersionCode
	 * @param osType
	 * @return
	 */
	public List<RfChannelVersion> selectChannelVersion(String channelCode,String channelVersionCode,String osType){
		RfChannelVersionExample example = new RfChannelVersionExample();
		example.createCriteria().andChannelCodeEqualTo(channelCode)
		.andChannelVersionCodeEqualTo(channelVersionCode)
		.andChannelOsTypeEqualTo(osType).andEnableStatusEqualTo(YesOrNoStatus.YES)
		.andStatusEqualTo(YesOrNoStatus.YES);
		List<RfChannelVersion> list = mapper.selectByExample(example);
		return list;
	}

}
