package com.redfinger.manager.modules.wechart.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfWechartTemplate;
import com.redfinger.manager.common.domain.RfWechartTemplateExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfWechartTemplateMapper;
import com.redfinger.manager.common.utils.WechartCodeUtils;
import com.redfinger.manager.common.utils.WechartTemplateStatus;
import com.redfinger.manager.common.utils.WechartTemplateType;
import com.redfinger.manager.common.utils.WechartUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field="templateId")
public class WechartTemplateService extends BaseService<RfWechartTemplate, RfWechartTemplateExample, RfWechartTemplateMapper> {

	@Autowired
	private RfWechartTemplateMapper mapper;
	
	public RfWechartTemplate saveWechartTemplate(Integer userId,String padCode,String messageName,String creater,String event){
		RfWechartTemplate record = new RfWechartTemplate();
		record.setTemplateFirst(WechartUtils.FIRST);
		record.setTemplateRemark(WechartUtils.REMARK);
		record.setTemplateType(WechartTemplateType.REPLACE_PAD);
		record.setFinishTime(new Date());
		record.setEvent(event.replaceAll("XXX", messageName));
		record.setMessageName(messageName);
		record.setTemplateCode(WechartCodeUtils.TM00203);
		record.setTemplateStatus(WechartTemplateStatus.NO_SEND);
		record.setUserId(userId);
		record.setPadCode(padCode);
		record.setStatus(YesOrNoStatus.YES);
		record.setEnableStatus(YesOrNoStatus.YES);
		record.setCreater(creater);
		record.setCreateTime(new Date());
		
		mapper.insertSelective(record);
		return record;
	}
}
