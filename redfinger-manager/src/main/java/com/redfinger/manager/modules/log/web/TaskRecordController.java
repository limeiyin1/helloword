package com.redfinger.manager.modules.log.web;

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
import com.redfinger.manager.common.domain.TaskSystem;
import com.redfinger.manager.common.domain.ViewTaskRecord;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.log.service.ViewTaskRecordService;
import com.redfinger.manager.modules.tasks.service.TaskSystemService;


@Controller
@RequestMapping(value = "/log/record")
public class TaskRecordController extends BaseController {
    @Autowired 
    TaskSystemService taskSystemService;
	@Autowired
	ViewTaskRecordService service;
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		List<TaskSystem>taskSystemList=taskSystemService.initQuery().findAll();
		model.addAttribute("taskSystemList",taskSystemList);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ViewTaskRecord> list(HttpServletRequest request,HttpServletResponse response, Model model,ViewTaskRecord bean)  throws Exception {
          List<ViewTaskRecord>list=service.initQuery(bean)
        		.andLike("userMobilePhoneT2", bean.getUserMobilePhoneT2())
        		.andEqualTo("taskId", bean.getTaskId())
				.andGreaterThanOrEqualTo("takeTime",DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("takeTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		PageInfo<ViewTaskRecord> pageInfo = new PageInfo<ViewTaskRecord>(list);
		return pageInfo;
	}

	
}
