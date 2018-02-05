package com.redfinger.manager.modules.version.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfChannelVersion;
import com.redfinger.manager.common.domain.RfChannelVersionExample;
import com.redfinger.manager.common.domain.RfParentVersion;
import com.redfinger.manager.common.domain.RfParentVersionExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfChannelVersionMapper;
import com.redfinger.manager.common.mapper.RfParentVersionMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="parentVersionId")
public class ParentVersionService extends BaseService<RfParentVersion, RfParentVersionExample, RfParentVersionMapper> {
	@Autowired
	RfParentVersionMapper mapper;
	@Autowired
	RfChannelVersionMapper versionMapper;
	
	
	public void saveParentVersion(HttpServletRequest request,RfParentVersion bean){
		bean.setCreater(SessionUtils.getCurrentUserId(request));
		bean.setCreateTime(new Date());
		bean.setStatus(YesOrNoStatus.YES);
		bean.setEnableStatus(YesOrNoStatus.NO);
		mapper.insertSelective(bean);
		
		RfChannelVersionExample example = new RfChannelVersionExample();
		example.createCriteria().andChannelOsTypeEqualTo(bean.getOsType())
		.andChannelCodeEqualTo(bean.getParentChannelCode())
		.andChannelVersionCodeEqualTo(bean.getParentVersionCode());
		List<RfChannelVersion> list = versionMapper.selectByExample(example);
		
		if(null == list && list.size()<=0){
			RfChannelVersion channelVersion = new RfChannelVersion();
			channelVersion.setChannelCode(bean.getParentChannelCode());
			channelVersion.setChannelVersionCode(bean.getParentVersionCode());
			channelVersion.setChannelVersionName(bean.getParentVersionName());
			channelVersion.setChannelOsType(bean.getOsType());
			channelVersion.setChannelVersionMust(bean.getVersionMust());
			channelVersion.setChannelVersionNew(bean.getVersionNew());
			channelVersion.setChannelUpdateUrl(bean.getParentVersionUpdateurl());
			channelVersion.setChannelDownloadUrl(bean.getParentVersionDownloadurl());
			channelVersion.setChannelVersionDesc(bean.getParentVersionDesc());
			channelVersion.setChannelVersionTime(bean.getParentVersionTime());
			channelVersion.setStatus(bean.getStatus());
			channelVersion.setEnableStatus(bean.getEnableStatus());
			channelVersion.setCreater(SessionUtils.getCurrentUserId(request));
			channelVersion.setCreateTime(new Date());
			versionMapper.insert(channelVersion);
		}
	}
}
