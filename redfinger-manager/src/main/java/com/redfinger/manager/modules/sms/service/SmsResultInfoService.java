package com.redfinger.manager.modules.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfSmsResultInfo;
import com.redfinger.manager.common.domain.RfSmsResultInfoExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfSmsResultInfoMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "resultInfoId")
public class SmsResultInfoService extends BaseService<RfSmsResultInfo, RfSmsResultInfoExample, RfSmsResultInfoMapper> {
	@Autowired
	RfSmsResultInfoMapper mapper;
	
	public RfSmsResultInfo selectByCodeOrType(String resultCode,String smsResultType){
		RfSmsResultInfoExample example = new RfSmsResultInfoExample();
		example.createCriteria().andResultCodeEqualTo(resultCode).andSmsResultTypeEqualTo(smsResultType)
		.andStatusEqualTo(YesOrNoStatus.YES).andEnableStatusEqualTo(YesOrNoStatus.YES);
		return mapper.selectOneByExample(example);
	}

}
