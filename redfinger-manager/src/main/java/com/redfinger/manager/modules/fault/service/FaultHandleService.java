package com.redfinger.manager.modules.fault.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfFaultHandle;
import com.redfinger.manager.common.domain.RfFaultHandleExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfFaultHandleMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "faultHandleId")
public class FaultHandleService extends BaseService<RfFaultHandle, RfFaultHandleExample, RfFaultHandleMapper> {

	@Transactional(readOnly = true)
	public List<RfFaultHandle> queryByFaultFeedbackId(Integer faultFeedbackId){
		List<RfFaultHandle> list=this.initQuery().andEqualTo("faultFeedbackId", faultFeedbackId).addOrderClause("createTime", "asc").findDelTrue();
		return list;
	}
	
}
