package com.redfinger.manager.modules.tasks.web;

import java.util.Date;
import java.util.List;

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
import com.redfinger.manager.common.domain.RfRbcTask;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.tasks.service.RbcTaskService;
/**
 * 
* @ClassName: RbcTaskManegeController
* @Description: 红豆任务管理Controller
* @author tluo
* @date 2015年9月23日 下午4:57:28
*
 */
@Controller
@RequestMapping(value = "/task/rbcTask")
public class RbcTaskManegeController extends BaseController {
	@Autowired
	RbcTaskService service;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfRbcTask> list(HttpServletRequest request,HttpServletResponse response, Model model, RfRbcTask bean,
			String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		List<RfRbcTask> list = service.initQuery(bean)
				.andLike("taskCode", bean.getTaskCode())
				.andLike("taskName", bean.getTaskName())
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfRbcTask> pageInfo = new PageInfo<RfRbcTask>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, RfRbcTask bean)
			throws Exception {
		if (bean.getTaskId() == null) {

		} else {
			bean = service.get(bean.getTaskId());
			model.addAttribute("bean", bean);

		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, RfRbcTask bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, RfRbcTask bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response,
			Model model, RfRbcTask bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response,
			Model model, RfRbcTask bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, RfRbcTask bean)
			throws Exception {
		service.remove(request, bean);
	}

}
