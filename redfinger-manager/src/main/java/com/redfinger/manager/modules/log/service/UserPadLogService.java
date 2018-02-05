package com.redfinger.manager.modules.log.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfUserPadLog;
import com.redfinger.manager.common.domain.RfUserPadLogExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfUserPadLogMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "userPadLogId")
public class UserPadLogService extends BaseService<RfUserPadLog, RfUserPadLogExample, RfUserPadLogMapper> {
	public void userPadLog(HttpServletRequest request,RfUserPadLog bean) {
	RfUserPadLogMapper mapper=(RfUserPadLogMapper)this.getMapper();
	mapper.insertSelective(bean);
	}



}
