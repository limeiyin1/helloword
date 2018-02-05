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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfTask;
import com.redfinger.manager.common.domain.RfTaskUser;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.tasks.service.RfTaskService;
import com.redfinger.manager.modules.tasks.service.RfTaskUserService;

@Controller
@RequestMapping(value = "/rftask/taskUser")
public class RfTaskUserController extends BaseController {
	@Autowired
	private RfTaskUserService service;
	@Autowired
	private UserService userService;
	@Autowired
	private RfTaskService taskService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfTaskUser> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfTaskUser bean, String userMobilePhone, String taskName,String taskType) throws Exception {
		List<RfTaskUser> taskUserList = new ArrayList<RfTaskUser>();
		if (StringUtils.isNotBlank(userMobilePhone)) {
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).singleStopTrue();
			if (userList != null && userList.size() > 0)
				bean.setUserId(userList.get(0).getUserId());
			else
				bean.setUserId(-1);
		}
		List<Integer> taskIds = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(taskName)){
			List<RfTask> taskList = taskService.initQuery().andLike("taskName", taskName).findAll();
			if(taskList.size() > 0) taskIds.add(taskList.get(0).getTaskId());
			else  taskIds.add(-1);
		}
		if(StringUtils.isNotBlank(taskType)){
			List<RfTask> taskList = taskService.initQuery().andEqualTo("taskType", taskType)
					.andEqualTo("status", ConstantsDb.globalStatusNomarl())
					.andEqualTo("enableStatus", ConstantsDb.globalEnableStatusNomarl())
					.findAll();
			if(taskList.size() > 0)
				for (RfTask t : taskList) taskIds.add(t.getTaskId());
			else taskIds.add(-1);
		}
		
		taskUserList = service.initQuery(bean).andEqualTo("userId", bean.getUserId())
				.andIn("taskId", taskIds)
				.andEqualTo("taskStatus", bean.getTaskStatus())
				.andEqualTo("inviteCode", bean.getInviteCode())
				.andLike("enableStatus", bean.getEnableStatus())
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		Map<Integer, RfTask> taskMap = new HashMap<Integer, RfTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		taskIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (RfTaskUser tu : taskUserList) {
			taskIds.add(tu.getTaskId());
			userIds.add(tu.getUserId());
		}
		if(taskIds.size() > 0){
			List<RfTask> taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
			for (RfTask t : taskList) {
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
		RfTask task = null;
		for (RfTaskUser tu : taskUserList) {
			task = taskMap.get(tu.getTaskId());
			user = userMap.get(tu.getUserId());
			if(user != null){
				tu.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			if(task != null){
				tu.getMap().put("taskClassify", task.getTaskClassify());
				tu.getMap().put("taskType", task.getTaskType());
				tu.getMap().put("taskName", task.getTaskName());
				tu.getMap().put("taskRemark", task.getRemark());
				tu.getMap().put("rbcAward", task.getRbcAward());
				tu.getMap().put("scoreAward", task.getScoreAward());
				tu.getMap().put("taskThreshold", task.getTaskThreshold());
			}
		}
		PageInfo<RfTaskUser> pageInfo = new PageInfo<RfTaskUser>(taskUserList);
		return pageInfo;
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfTaskUser bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfTaskUser bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfTaskUser bean)
			throws Exception {
		service.remove(request, bean);
	}
}
