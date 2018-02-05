package com.redfinger.manager.modules.tasks.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.TkTask;
import com.redfinger.manager.common.domain.TkUserTask;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.tasks.service.TkTaskService;
import com.redfinger.manager.modules.tasks.service.TkUserTaskService;

@Controller
@RequestMapping(value = "/tktask/userTask")
public class TkUserTaskController extends BaseController {
	@Autowired
	private TkUserTaskService service;
	@Autowired
	private UserService userService;
	@Autowired
	private TkTaskService taskService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TkUserTask> list(HttpServletRequest request, HttpServletResponse response, Model model,
			TkUserTask bean, String userMobilePhone, String taskName,String taskType, Integer externalUserId) throws Exception {
		List<TkUserTask> userTaskList = new ArrayList<TkUserTask>();
		if (StringUtils.isNotBlank(userMobilePhone) || externalUserId != null) {
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).andEqualTo("externalUserId", externalUserId).singleStopTrue();
			if (userList != null && userList.size() > 0)
				bean.setUserId(userList.get(0).getUserId());
			else{
				bean.setUserId(-1);
			}
		}
		List<Integer> taskIds = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(taskName) || StringUtils.isNotBlank(taskType)){
			List<TkTask> taskList = taskService.initQuery().andLike("taskName", taskName).andEqualTo("taskType", taskType).andEqualTo("parentTaskId", -1).findAll();
			for (TkTask tkTask : taskList) {
				taskIds.add(tkTask.getTaskId());
			}
			if(taskIds.size()==0) taskIds.add(-1);
		}
		
		if(taskIds.isEmpty()){
			List<TkTask> taskList = taskService.initQuery().andEqualTo("parentTaskId", -1).findDelTrue();
			for (TkTask t : taskList) {
				taskIds.add(t.getTaskId());
			}
		}
		
		userTaskList = service.initQuery(bean).andEqualTo("userId", bean.getUserId())
				.andIn("taskId", taskIds)
				.andEqualTo("taskStatus", bean.getTaskStatus())
				.andEqualTo("inviteCode", bean.getInviteCode())
				.andLike("enableStatus", bean.getEnableStatus())
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		Map<Integer, TkTask> taskMap = new HashMap<Integer, TkTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		Map<Integer, Integer> subTaskCountMap = new HashMap<Integer, Integer>();
		taskIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (TkUserTask tu : userTaskList) {
			taskIds.add(tu.getTaskId());
			userIds.add(tu.getUserId());
		}
		if(taskIds.size() > 0){
			List<TkTask> taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
			for (TkTask t : taskList) {
				taskMap.put(t.getTaskId(), t);
				
				int subTaskCount = taskService.initQuery().andEqualTo("parentTaskId", t.getTaskId()).countDelTrue();
				subTaskCountMap.put(t.getTaskId(), subTaskCount);
			}
		}
		
		if(userIds.size() > 0){
			List<RfUser> userList = userService.initQuery().andIn("userId", userIds).findAll();
			for (RfUser u : userList) {
				userMap.put(u.getUserId(), u);
			}
		}
		
		RfUser user = null;
		TkTask task = null;
		for (TkUserTask tu : userTaskList) {
			task = taskMap.get(tu.getTaskId());
			user = userMap.get(tu.getUserId());
			if(user != null){
				tu.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			if(task != null){
				tu.getMap().put("taskType", task.getTaskType());
				tu.getMap().put("taskName", task.getTaskName());
				tu.getMap().put("taskRemark", task.getRemark());
				tu.getMap().put("taskThreshold", task.getTaskThreshold());
			}
			Integer subUserTaskCount = subTaskCountMap.get(tu.getTaskId());
			tu.setSubUserTaskCount(subUserTaskCount);
			
			/** 查询客户端ID*/
			if(tu.getUserId() != null){
				/** 根据用户ID查询用户*/
				RfUser rfUser = userService.load(tu.getUserId());
				/** 根据用户ID查询客户端ID*/
				tu.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
		}
		PageInfo<TkUserTask> pageInfo = new PageInfo<TkUserTask>(userTaskList);
		return pageInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "subUserTaskList")
	public List<TkUserTask> subUserTaskList(HttpServletRequest request, HttpServletResponse response, Model model, TkUserTask bean) throws Exception {
		List<Integer> taskIds = new ArrayList<Integer>();
		
		if(bean.getUserId() == null || bean.getTaskId() == null){
			bean.setUserId(-1);
			bean.setTaskId(-0);
		}
		
		List<TkTask> taskList = taskService.initQuery().andEqualTo("parentTaskId", bean.getTaskId()).findDelTrue();
		for (TkTask t : taskList) {
			taskIds.add(t.getTaskId());
		}
		
		List<TkUserTask> userTaskList = service.initQuery(bean)
				.andEqualTo("userId", bean.getUserId())
				.andIn("taskId", taskIds)
				.findDelTrue();
		
		Map<Integer, TkTask> taskMap = new HashMap<Integer, TkTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		taskIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (TkUserTask tu : userTaskList) {
			taskIds.add(tu.getTaskId());
			userIds.add(tu.getUserId());
		}
		if(taskIds.size() > 0){
			taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
			for (TkTask t : taskList) {
				taskMap.put(t.getTaskId(), t);
			}
		}
		
		if(userIds.size() > 0){
			List<RfUser> userList = userService.initQuery().andIn("userId", userIds).findAll();
			for (RfUser u : userList) {
				userMap.put(u.getUserId(), u);
			}
		}
		
		RfUser user = null;
		TkTask task = null;
		for (TkUserTask tu : userTaskList) {
			task = taskMap.get(tu.getTaskId());
			user = userMap.get(tu.getUserId());
			if(user != null){
				tu.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			if(task != null){
				tu.getMap().put("taskType", task.getTaskType());
				tu.getMap().put("taskName", task.getTaskName());
				tu.getMap().put("taskRemark", task.getRemark());
				tu.getMap().put("taskThreshold", task.getTaskThreshold());
			}
		}
		return userTaskList;
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, TkUserTask bean)
			throws Exception {
		service.stopUserTask(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, TkUserTask bean)
			throws Exception {
		service.startUserTask(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, TkUserTask bean)
			throws Exception {
		service.deleteUserTask(request, bean);
	}
}
