package com.redfinger.manager.modules.grant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfPadGrantCode;
import com.redfinger.manager.common.domain.RfPadGrantCodeExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPadGrantCodeMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "grantCodeId")
public class PadGrantCodeService extends BaseService<RfPadGrantCode, RfPadGrantCodeExample, RfPadGrantCodeMapper> {
	
	public List<RfPadGrantCode> findByUserPadId(Integer userPadId) throws Exception{
		List<RfPadGrantCode> list =this.initQuery()
				.andEqualTo("userPadId", userPadId)
				.andEqualTo("status", YesOrNoStatus.YES)
				.andEqualTo("enableStatus", YesOrNoStatus.YES)
				.andEqualTo("grantCodeStatus", YesOrNoStatus.NO)
				.findStopTrue();
		return list;
	}
}
