package com.redfinger.manager.modules.activation.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfActivationCode;
import com.redfinger.manager.common.domain.RfActivationCodeExample;
import com.redfinger.manager.common.domain.RfActivationCodeLog;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfActivationCodeLogMapper;
import com.redfinger.manager.common.mapper.RfActivationCodeMapper;
import com.redfinger.manager.common.utils.ActivationCodeOperateType;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="codeId")
public class ActivationCodeService extends BaseService<RfActivationCode, RfActivationCodeExample, RfActivationCodeMapper> {

	@Autowired
	private RfActivationCodeMapper rfActivationCodeMapper;
	@Autowired
	private RfActivationCodeLogMapper rfActivationCodeLogMapper;
	
	public void saveCodeList(HttpServletRequest request,List<RfActivationCode> list, String activationCodePrefix,
			int activationNumber) throws Exception{
		String remark = "";
		rfActivationCodeMapper.insertBatch(list);
		RfActivationCode code = list.get(0);
		RfActivationCodeLog log = new RfActivationCodeLog();
		log.setCodeId(code.getCodeId());
		log.setActivationCode(code.getActivationCode());
		log.setOpearteType(ActivationCodeOperateType.BATCH_ADD);
		log.setTypeId(code.getTypeId());
		log.setAddNumber(activationNumber);
		log.setActivationStatus(code.getActivationStatus());
		log.setStartTime(code.getStartTime());
		log.setEndTime(code.getEndTime());
		log.setStatus(code.getStatus());
		log.setEnableStatus(code.getEnableStatus());
		log.setPadType(code.getPadType());
		log.setOperateIp(StringUtils.getRemoteAddr(request));
		log.setCreater(SessionUtils.getCurrentUserId(request));
		log.setCreateTime(new Date());
		remark = "{"+"operateType:"+ActivationCodeOperateType.BATCH_ADD+",addNumber:"+activationNumber+
				",activationCodePrefix:"+activationCodePrefix+"}";
		log.setRemark(remark);
		rfActivationCodeLogMapper.insert(log);
	}
	
	public void updateCode(HttpServletRequest request,RfActivationCode code) throws Exception{
		this.update(request, code);;
		RfActivationCodeLog log = new RfActivationCodeLog();
		log.setOpearteType(ActivationCodeOperateType.UPDATE);
		log.setActivationCode(code.getActivationCode());
		log.setCodeId(code.getCodeId());
		log.setTypeId(code.getTypeId());
		log.setActivationStatus(code.getActivationStatus());
		log.setStartTime(code.getStartTime());
		log.setEndTime(code.getEndTime());
		log.setStatus(code.getStatus());
		log.setPadType(code.getPadType());
		log.setEnableStatus(code.getEnableStatus());
		log.setOperateIp(StringUtils.getRemoteAddr(request));
		log.setCreater(SessionUtils.getCurrentUserId(request));
		log.setCreateTime(new Date());
		log.setRemark("{"+"operateType:"+ActivationCodeOperateType.UPDATE+",codeId:"+code.getCodeId()+"}");
		rfActivationCodeLogMapper.insert(log);
	}
	
	public void startCode(HttpServletRequest request,RfActivationCode code) throws Exception{
		this.start(request, code);;
		RfActivationCodeLog log = new RfActivationCodeLog();
		log.setOpearteType(ActivationCodeOperateType.START);
		log.setCodeId(code.getCodeId());
		log.setActivationCode(code.getActivationCode());
		log.setTypeId(code.getTypeId());
		log.setActivationStatus(code.getActivationStatus());
		log.setStartTime(code.getStartTime());
		log.setEndTime(code.getEndTime());
		log.setStatus(code.getStatus());
		log.setPadType(code.getPadType());
		log.setEnableStatus(code.getEnableStatus());
		log.setOperateIp(StringUtils.getRemoteAddr(request));
		log.setCreater(SessionUtils.getCurrentUserId(request));
		log.setCreateTime(new Date());
		log.setRemark("{"+"operateType:"+ActivationCodeOperateType.START+",codeId:"+code.getCodeId()+"}");
		rfActivationCodeLogMapper.insert(log);
	}
	
	public void stopCode(HttpServletRequest request,RfActivationCode code) throws Exception{
		this.stop(request, code);;
		RfActivationCodeLog log = new RfActivationCodeLog();
		log.setOpearteType(ActivationCodeOperateType.ENABLE);
		log.setCodeId(code.getCodeId());
		log.setActivationCode(code.getActivationCode());
		log.setTypeId(code.getTypeId());
		log.setActivationStatus(code.getActivationStatus());
		log.setStartTime(code.getStartTime());
		log.setEndTime(code.getEndTime());
		log.setStatus(code.getStatus());
		log.setPadType(code.getPadType());
		log.setEnableStatus(code.getEnableStatus());
		log.setOperateIp(StringUtils.getRemoteAddr(request));
		log.setCreater(SessionUtils.getCurrentUserId(request));
		log.setCreateTime(new Date());
		log.setRemark("{"+"operateType:"+ActivationCodeOperateType.ENABLE+",codeId:"+code.getCodeId()+"}");
		rfActivationCodeLogMapper.insert(log);
	}
	
	public void removeCode(HttpServletRequest request,RfActivationCode code) throws Exception{
		String[] ids = code.getIds().split(",");
		for (String id : ids) {
			RfActivationCode activationCode = this.get(Integer.parseInt(id));
			if(YesOrNoStatus.YES.equals(activationCode.getBindStatus())){
				throw new BusinessException("删除失败，激活码"+activationCode.getActivationCode()+"已被绑定");
			}
			
			RfActivationCodeLog log = new RfActivationCodeLog();
			log.setOpearteType(ActivationCodeOperateType.DELETE);
			log.setCodeId(activationCode.getCodeId());
			log.setActivationCode(activationCode.getActivationCode());
			log.setTypeId(activationCode.getTypeId());
			log.setActivationStatus(activationCode.getActivationStatus());
			log.setStartTime(activationCode.getStartTime());
			log.setEndTime(activationCode.getEndTime());
			log.setStatus(activationCode.getStatus());
			log.setPadType(activationCode.getPadType());
			log.setEnableStatus(activationCode.getEnableStatus());
			log.setOperateIp(StringUtils.getRemoteAddr(request));
			log.setCreater(SessionUtils.getCurrentUserId(request));
			log.setCreateTime(new Date());
			log.setRemark("{"+"operateType:"+ActivationCodeOperateType.DELETE+",codeId:"+activationCode.getCodeId()+"}");
			rfActivationCodeLogMapper.insert(log);
		}
		this.remove(request, code);
	}
}
