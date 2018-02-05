package com.redfinger.manager.modules.tasks.service;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.TkTask;
import com.redfinger.manager.common.domain.TkUserTask;
import com.redfinger.manager.common.domain.TkUserTaskExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.TkUserTaskMapper;
import com.redfinger.manager.common.utils.YesOrNoStatus;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "userTaskId")
public class TkUserTaskService extends BaseService<TkUserTask, TkUserTaskExample, TkUserTaskMapper> {
	
	@Autowired
	private TkTaskService taskService;
	@Autowired
	private TkUserTaskMapper userTaskMapper;
	
	public void startUserTask(HttpServletRequest request, TkUserTask userTask) throws Exception{
		String[] ids = userTask.getIds().split(",");
		for (String userTaskId : ids) {
			List<TkUserTask> userTaskList = this.initQuery().andEqualTo("userTaskId", Integer.parseInt(userTaskId)).singleAll();
			TkUserTask ut = userTaskList.size() > 0 ? userTaskList.get(0) : null;
			
			if(ut != null){
				List<TkTask> taskList = taskService.initQuery().andEqualTo("parentTaskId", ut.getTaskId()).findAll();
				List<Integer> taskIds = new ArrayList<Integer>();
				for (TkTask tkTask : taskList) {
					taskIds.add(tkTask.getTaskId());
				}
				
				if(taskIds.size() > 0){
					userTaskMapper.updateUserTaskStatus(ut.getUserId(), taskIds, YesOrNoStatus.YES, null);
				}
			}
		}
		this.start(request, userTask);
	}
	
	public void stopUserTask(HttpServletRequest request, TkUserTask userTask) throws Exception{
		String[] ids = userTask.getIds().split(",");
		for (String userTaskId : ids) {
			List<TkUserTask> userTaskList = this.initQuery().andEqualTo("userTaskId", Integer.parseInt(userTaskId)).singleAll();
			TkUserTask ut = userTaskList.size() > 0 ? userTaskList.get(0) : null;
			
			if(ut != null){
				List<TkTask> taskList = taskService.initQuery().andEqualTo("parentTaskId", ut.getTaskId()).findAll();
				List<Integer> taskIds = new ArrayList<Integer>();
				for (TkTask tkTask : taskList) {
					taskIds.add(tkTask.getTaskId());
				}
				
				if(taskIds.size() > 0){
					userTaskMapper.updateUserTaskStatus(ut.getUserId(), taskIds, YesOrNoStatus.NO, null);
				}
			}
		}
		this.stop(request, userTask);
	}
	
	public void deleteUserTask(HttpServletRequest request, TkUserTask userTask) throws Exception{
		String[] ids = userTask.getIds().split(",");
		for (String userTaskId : ids) {
			List<TkUserTask> userTaskList = this.initQuery().andEqualTo("userTaskId", Integer.parseInt(userTaskId)).singleAll();
			TkUserTask ut = userTaskList.size() > 0 ? userTaskList.get(0) : null;
			
			if(ut != null){
				List<TkTask> taskList = taskService.initQuery().andEqualTo("parentTaskId", ut.getTaskId()).findAll();
				List<Integer> taskIds = new ArrayList<Integer>();
				for (TkTask tkTask : taskList) {
					taskIds.add(tkTask.getTaskId());
				}
				
				if(taskIds.size() > 0){
					userTaskMapper.updateUserTaskStatus(ut.getUserId(), taskIds, null, YesOrNoStatus.NO);
				}
			}
		}
		this.delete(request, userTask);
	}
}
