package com.redfinger.manager.modules.tasks.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.TkTaskRecord;
import com.redfinger.manager.common.domain.TkTaskRecordExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TkTaskRecordMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "recordId")
public class TkTaskRecordService extends BaseService<TkTaskRecord, TkTaskRecordExample, TkTaskRecordMapper> {
	
	@Autowired
	TkTaskRecordMapper mapper;
	
	public int selectCountByParams(TkTaskRecord bean, RfUser user, List<Integer> taskIds){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user!=null?user.getUserId():null);
		if(null != taskIds && taskIds.size()>0){
			map.put("taskIdIn", taskIds);
		}
		map.put("inviteCode", bean.getInviteCode());
		map.put("status", YesOrNoStatus.YES);
		map.put("createTimeGte", DateUtils.parseDate(bean.getBeginTimeStr()));
		map.put("createTimeLte", DateUtils.parseDate(bean.getEndTimeStr()));
		return mapper.selectCountByExample(map);
	}
}
