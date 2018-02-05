package com.redfinger.manager.modules.log.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.PushUmengInfoLog;
import com.redfinger.manager.common.domain.PushUmengInfoLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.PushUmengInfoLogMapper;
import com.redfinger.manager.common.utils.PushStatus;
import com.redfinger.manager.common.utils.SessionUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class PushUmengInfoLogService extends BaseService<PushUmengInfoLog, PushUmengInfoLogExample, PushUmengInfoLogMapper> {

	@Autowired
	PushUmengInfoLogMapper pushUmengInfoLogMapper;
	
	
	public void updateSendToUMeng(HttpServletRequest request,Integer id,String msgId) throws Exception{
		PushUmengInfoLog pushInfo = new PushUmengInfoLog();
		pushInfo.setId(id);
		pushInfo.setPushStatus(PushStatus.PUSH_SUCCESS);
		pushInfo.setReturnInfo(msgId);
		pushInfo.setModifier(SessionUtils.getCurrentUserId(request));
		pushInfo.setModifyTime(new Date());
		pushUmengInfoLogMapper.updateByPrimaryKeySelective(pushInfo);
	}
	
	public void updateToFinishError(HttpServletRequest request,int id, String error) throws Exception {
		PushUmengInfoLog pushInfo = new PushUmengInfoLog();
		pushInfo.setId(id);
		pushInfo.setPushStatus(PushStatus.PUSH_FAIL);
		pushInfo.setReturnInfo(error);
		pushInfo.setModifier(SessionUtils.getCurrentUserId(request));
		pushInfo.setModifyTime(new Date());
		pushUmengInfoLogMapper.updateByPrimaryKeySelective(pushInfo);
	}
}
