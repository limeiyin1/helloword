package com.redfinger.manager.modules.tasks.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfTask;
import com.redfinger.manager.common.domain.RfTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfTaskMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "taskId")
public class RfTaskService extends BaseService<RfTask, RfTaskExample, RfTaskMapper> {
	
	@Autowired
	private RfTaskMapper taskMapper;
	
	public int selectCountByTaskCode(RfTask task, Integer notEqualTaskId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskCode", task.getTaskCode());
		params.put("taskStartTime", task.getTaskStartTime());
		params.put("taskEndTime", task.getTaskEndTime());
		params.put("status", ConstantsDb.globalStatusNomarl());
		params.put("enableStatus", ConstantsDb.globalEnableStatusNomarl());
		if(notEqualTaskId != null){
			params.put("notEqualTaskId", notEqualTaskId);
		}
		return taskMapper.selectCountByTaskCode(params);
	}
}
