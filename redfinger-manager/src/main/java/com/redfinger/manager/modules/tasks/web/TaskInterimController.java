package com.redfinger.manager.modules.tasks.web;

import java.util.ArrayList;
import java.util.List;

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
import com.redfinger.manager.common.domain.TaskUserInterim;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.market.service.MarketAppApkService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.tasks.service.TaskInterimService;

@Controller
@RequestMapping(value = "/task/interim")
public class TaskInterimController extends BaseController {
	@Autowired
	TaskInterimService service;
	@Autowired
	MarketAppApkService appApkService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TaskUserInterim> list(HttpServletRequest request, HttpServletResponse response, Model model,
			TaskUserInterim bean, String userMobilePhone) throws Exception {
		if (StringUtils.isNotBlank(userMobilePhone)) {
			if (userMobilePhone.length() != 11)
				throw new BusinessException("请输入正确的手机号码！");
			List<RfUser> list_user = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone)
					.singleStopTrue();
			if (list_user != null && list_user.size() > 0)
				bean.setUserId(list_user.get(0).getUserId());
		}
		List<TaskUserInterim> list = service.initQuery(bean).andEqualTo("userId", bean.getUserId())
				.andLike("awardStatus", bean.getAwardStatus()).andLike("taskName", bean.getTaskName())
				.andLike("category", bean.getCategory()).andEqualTo("taskNow", bean.getTaskNow())
				.andGreaterThanOrEqualTo("beginTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("beginTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (TaskUserInterim tu : list) {
			tu.getMap().put(
					"userMobilePhone",
					userService.load(tu.getUserId()) == null ? "--" : userService.load(tu.getUserId())
							.getUserMobilePhone());
		}
		PageInfo<TaskUserInterim> pageInfo = new PageInfo<TaskUserInterim>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, TaskUserInterim bean)
			throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
			model.addAttribute("userMobilePhone",
					userService.load(bean.getUserId()) == null ? "--" : userService.load(bean.getUserId())
							.getUserMobilePhone());
			model.addAttribute("bean", bean);

		}
		return this.toPage(request, response, model);
	}

	/**
	 * 批量修改中间表
	 * 
	 * @Title: batchUpdate
	 * @return void 返回类型
	 * @param request
	 * @param response
	 * @param model
	 * @param takeTime
	 * @param recordTime
	 * @param beginTime
	 * @param endTime
	 * @throws Exception
	 */
	@RequestMapping(value = "batchUpdate")
	public String batchUpdate(HttpServletRequest request, HttpServletResponse response, Model model, String ids)
			throws Exception {
		model.addAttribute("sId", ids);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "batchList")
	public PageInfo<TaskUserInterim> batchList(HttpServletRequest request, HttpServletResponse response, Model model,
			TaskUserInterim bean, String ids) throws Exception {
		String[] id_arr = ids.split(",");
		List<Integer> pras_list = new ArrayList<Integer>();
		for (String s : id_arr) {
			pras_list.add(Integer.parseInt(s));
		}

		List<TaskUserInterim> list = service.initQuery(bean).andIn("id", pras_list)
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (TaskUserInterim tu : list) {
			tu.getMap().put(
					"userMobilePhone",
					userService.load(tu.getUserId()) == null ? "--" : userService.load(tu.getUserId())
							.getUserMobilePhone());
		}
		PageInfo<TaskUserInterim> pageInfo = new PageInfo<TaskUserInterim>(list);
		return pageInfo;
	}

	@RequestMapping(value = "saveBatch")
	public void saveBatch(HttpServletRequest request, HttpServletResponse response, Model model, TaskUserInterim bean,
			String beginTime1, String endTime1) throws Exception {
		TaskUserInterim upBean = new TaskUserInterim();
		if (StringUtils.isNotBlank(bean.getTaskNow() + "")) {
			upBean.setTaskNow(bean.getTaskNow());
		}
		if (StringUtils.isNotBlank(bean.getAwardStatus())) {
			upBean.setAwardStatus(bean.getAwardStatus());
		}
		if (StringUtils.isNotBlank(bean.getTaskName())) {
			upBean.setTaskName(bean.getTaskName());
		}
		if (StringUtils.isNotBlank(bean.getRemark())) {
			upBean.setRemark(bean.getRemark());
		}
		if (StringUtils.isNotBlank(bean.getAwardAmount() + "")) {
			upBean.setAwardAmount(bean.getAwardAmount());
		}
		if (StringUtils.isNotBlank(bean.getThresholds() + "")) {
			upBean.setThresholds(bean.getThresholds());
		}
		if (StringUtils.isNotBlank(bean.getTaskSer())) {
			upBean.setTaskSer(bean.getTaskSer());
		}
		if (StringUtils.isNotBlank(beginTime1)) {
			upBean.setBeginTime(DateUtils.parseDate(beginTime1));
		}
		if (StringUtils.isNotBlank(endTime1)) {
			upBean.setEndTime(DateUtils.parseDate(endTime1));
		}
		String[] id_arr = bean.getIds().split(",");
		for (String id : id_arr) {
			upBean.setId(Integer.parseInt(id));
			service.update(null, upBean);
		}
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, TaskUserInterim bean)
			throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, TaskUserInterim bean,
			String takeTime1, String recordTime1, String beginTime1, String endTime1) throws Exception {
		if (StringUtils.isNotBlank(takeTime1)) {
			bean.setTakeTime(DateUtils.parseDate(takeTime1));
		}
		if (StringUtils.isNotBlank(recordTime1)) {
			bean.setRecordTime(DateUtils.parseDate(recordTime1));
		}
		if (StringUtils.isNotBlank(beginTime1)) {
			bean.setBeginTime(DateUtils.parseDate(beginTime1));
		}
		if (StringUtils.isNotBlank(endTime1)) {
			bean.setEndTime(DateUtils.parseDate(endTime1));
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, TaskUserInterim bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, TaskUserInterim bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, TaskUserInterim bean)
			throws Exception {
		service.delete(request, bean);
	}

	@RequestMapping(value = "remove")
	public void remove(HttpServletRequest request, HttpServletResponse response, Model model, TaskUserInterim bean)
			throws Exception {
		service.remove(request, bean);
	}
}
