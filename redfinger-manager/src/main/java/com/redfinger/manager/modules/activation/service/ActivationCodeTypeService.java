package com.redfinger.manager.modules.activation.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfActivationCode;
import com.redfinger.manager.common.domain.RfActivationCodeLog;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfActivationCodeTypeExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfActivationCodeLogMapper;
import com.redfinger.manager.common.mapper.RfActivationCodeMapper;
import com.redfinger.manager.common.mapper.RfActivationCodeTypeMapper;
import com.redfinger.manager.common.utils.ActivationCodeOperateType;
import com.redfinger.manager.common.utils.NumberUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="typeId")
public class ActivationCodeTypeService extends BaseService<RfActivationCodeType, RfActivationCodeTypeExample, RfActivationCodeTypeMapper> {

	@Autowired
	private RfActivationCodeMapper rfActivationCodeMapper;
	@Autowired
	private RfActivationCodeLogMapper rfActivationCodeLogMapper;
	
	
	public void updateActivation(HttpServletRequest request, RfActivationCodeType bean) throws Exception{
		RfActivationCode record = new RfActivationCode();
		record.setTypeId(bean.getTypeId());
		record.setPadType(bean.getPadType());
		record.setPadTime(NumberUtils.multiplyTime(bean.getPadTime()));
		record.setActivationStatus(YesOrNoStatus.NO);
		rfActivationCodeMapper.updateActivationTypePrimaryKey(record);
		
		String remark = "";
		RfActivationCodeLog log = new RfActivationCodeLog();
		log.setOpearteType(ActivationCodeOperateType.BATCH_UPDATE);
		log.setTypeId(bean.getTypeId());
		log.setStatus(bean.getStatus());
		log.setEnableStatus(bean.getEnableStatus());
		log.setPadType(bean.getPadType());
		log.setOperateIp(StringUtils.getRemoteAddr(request));
		log.setCreater(SessionUtils.getCurrentUserId(request));
		log.setCreateTime(new Date());
		remark = "{"+"operateType:"+ActivationCodeOperateType.BATCH_UPDATE+",typeId:"+bean.getTypeId()+
				",padType:"+bean.getPadType()+",padType:"+bean.getPadTime()+"}";
		log.setRemark(remark);
		rfActivationCodeLogMapper.insert(log);
	}
}
