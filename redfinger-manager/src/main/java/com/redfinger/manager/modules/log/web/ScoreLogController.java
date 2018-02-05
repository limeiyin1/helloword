package com.redfinger.manager.modules.log.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfScoreLog;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.TkTask;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.log.service.ScoreLogService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.tasks.service.TkTaskService;

@Controller
@RequestMapping(value = "/log/scorelog")
public class ScoreLogController extends BaseController {
	@Autowired
	private ScoreLogService service;
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
	public PageInfo<RfScoreLog> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfScoreLog bean, String userMobilePhone,Integer externalUserId) throws Exception {
		RfUser user = null;
		if(StringUtils.isNotBlank(userMobilePhone)||externalUserId!=null){
			List<RfUser> userList = userService.initQuery()
					.andEqualTo("userMobilePhone", userMobilePhone)
					.andEqualTo("externalUserId", externalUserId)
					.findAll();
			user = Collections3.isEmpty(userList) ? null : userList.get(0);
			if(user == null) {
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		
		List<RfScoreLog> scoreList = service.initQuery(bean)
				.andEqualTo("userId", user!=null?user.getUserId():null)
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("createTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		Map<Integer, TkTask> taskMap = new HashMap<Integer, TkTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		List<Integer> taskIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (RfScoreLog score : scoreList) {
			taskIds.add(score.getTaskId());
			userIds.add(score.getUserId());
		}
		if(taskIds.size() > 0){
			List<TkTask> taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
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
		
		TkTask task = null;
		for (RfScoreLog score : scoreList) {
			task = taskMap.get(score.getTaskId());
			user = userMap.get(score.getUserId());
			if(user != null){
				score.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			if(task != null){
				score.getMap().put("taskName", task.getTaskName());
				score.getMap().put("taskCode", task.getTaskCode());
			}
			
			/** 查询客户端ID*/
			if(score.getUserId() != null){
				/** 根据用户Id查询用户*/
				RfUser rfUser = userService.load(score.getUserId());
				/** 查询客户端ID*/
				score.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
		}
		PageInfo<RfScoreLog> pageInfo = new PageInfo<RfScoreLog>(scoreList);
		return pageInfo;
	}

}
