package com.redfinger.manager.modules.facility.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.common.domain.RfFacilityExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfFacilityMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "facilityId")
public class FacilityService extends BaseService<RfFacility, RfFacilityExample, RfFacilityMapper> {

	public void stopFacility(HttpServletRequest request, RfFacility facility) throws Exception{
		super.stop(request, facility);
		
		int count = this.initQuery().countStopTrue();
		if(count == 0){
			throw new BusinessException("处理失败，请至少保留一个设备来源为可用状态");
		}
	}
	
	public void deleteFacility(HttpServletRequest request, RfFacility facility) throws Exception{
		super.delete(request, facility);
		
		int count = this.initQuery().countStopTrue();
		if(count == 0){
			throw new BusinessException("处理失败，请至少保留一个设备来源为可用状态");
		}
	}
}
