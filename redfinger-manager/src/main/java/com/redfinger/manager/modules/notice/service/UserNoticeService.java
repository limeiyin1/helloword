package com.redfinger.manager.modules.notice.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfUserNotice;
import com.redfinger.manager.common.domain.RfUserNoticeExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfUserNoticeMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "userNoticeId")
public class UserNoticeService extends BaseService<RfUserNotice, RfUserNoticeExample, RfUserNoticeMapper> {
	@Autowired
	NoticeService noticeService;

	public void insert(HttpServletRequest request, String uidS, RfNotice notice) throws Exception {
		String userS[] = uidS.split(",");
		for (String uid : userS) {
			RfUserNotice userNotice = new RfUserNotice();
			userNotice.setNoticeId(notice.getNoticeId());
			userNotice.setUserId(Integer.parseInt(uid));
			userNotice.setUserNoticeStatus("0");
			this.save(request, userNotice);
		}
	}

}
