package com.redfinger.manager.modules.tasks.web;

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
import com.redfinger.manager.common.domain.AppApk;
import com.redfinger.manager.common.domain.TaskSystem;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.market.service.MarketAppApkService;
import com.redfinger.manager.modules.tasks.service.TaskSystemService;

@Controller
@RequestMapping(value = "/task/system")
public class TaskSystemController extends BaseController {
	@Autowired
	TaskSystemService service;
	@Autowired
	MarketAppApkService appApkService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TaskSystem> list(HttpServletRequest request, HttpServletResponse response, Model model,
			TaskSystem bean) throws Exception {
		List<TaskSystem> list = service
				.initQuery(bean)
				.andLike("remark", bean.getRemark())
				.andLike("name", bean.getName())
				.andLike("creater", bean.getCreater())
				.andLike("category", bean.getCategory())
				// 类型
				.andLike("enableStatus", bean.getEnableStatus())
				.andGreaterThanOrEqualTo("beginTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("beginTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<TaskSystem> pageInfo = new PageInfo<TaskSystem>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, TaskSystem bean)
			throws Exception {
		// 查询所有的游戏
		List<AppApk> appApkList = appApkService.initQuery().findStopTrue();

		if (null != bean.getId()) {
			bean = service.get(bean.getId());
		}
		model.addAttribute("appApkList", appApkList);
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "gameApk")
	public List<AppApk> getGameApk(HttpServletRequest request, HttpServletResponse response, Model model, String zm)
			throws Exception {
		// 查询所有的游戏
		List<AppApk> appApkList = appApkService.initQuery().andLikeSuffix("pinyin", zm).findStopTrue();
		return appApkList;

	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, TaskSystem bean,
			Integer game) throws Exception {
		if (null != game && !"".equals(game) && !" ".equals(game)) {
			bean.setTaskSer(bean.getTaskSer() + game);
		}
		bean.setBeginTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setEndTime(DateUtils.parseDateAddOneDay(bean.getEndTimeStr()));
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, TaskSystem bean)
			throws Exception {
		bean.setBeginTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setEndTime(DateUtils.parseDateAddOneDay(bean.getEndTimeStr()));
		service.update(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, TaskSystem bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, TaskSystem bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, TaskSystem bean)
			throws Exception {
		service.delete(request, bean);
	}
	
	
/*	pad_uninstallXM
	pad_restart
	pad_ping
	pad_reset
	pad_set_time
	pad_screencap
	pad_reboot
	pad_uninstall
	pad_check_app_install
	pad_check_app_version
	pad_check_app_run
	pad_install*/
}
