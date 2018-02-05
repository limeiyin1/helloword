package com.redfinger.manager.modules.mend.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfMend;
import com.redfinger.manager.common.domain.RfMendExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfMendMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="mendId")
public class MendService extends BaseService<RfMend, RfMendExample, RfMendMapper> {
	@Autowired
	RfMendMapper mapper;
	
	
	public void saveParentVersion(HttpServletRequest request,RfMend bean){
		
		bean.setCreater(SessionUtils.getCurrentUserId(request));
		bean.setCreateTime(new Date());
		bean.setStatus(YesOrNoStatus.YES);
		bean.setEnableStatus(YesOrNoStatus.NO);
		mapper.insertSelective(bean);
		
	}

	public List<RfMend> selectMend(String parentChannelCode,
			String parentVersionCode, String osType) {
		RfMendExample example = new RfMendExample();
		example.createCriteria().andMendChannelCodeEqualTo(parentChannelCode)
		.andMendVersionCodeEqualTo(parentVersionCode)
		.andMendOsTypeEqualTo(osType).andEnableStatusEqualTo(YesOrNoStatus.YES)
		.andStatusEqualTo(YesOrNoStatus.YES);
		List<RfMend> list = mapper.selectByExample(example);
		return list;
	}
	
	public void updateMend(RfMend bean){
		mapper.updateByPrimaryKeySelective(bean);
	}
	
	public void saveMend(RfMend bean){
		mapper.insertSelective(bean);
	}
}
