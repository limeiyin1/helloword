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
import com.redfinger.manager.common.domain.RfPadTask;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.log.service.PadTaskService;
import com.redfinger.manager.modules.member.service.UserService;
@Controller
@RequestMapping(value = "/task/taskNoBatch")
public class TaskNoBatchController  extends BaseController  {
	@Autowired
	PadTaskService service;
    @Autowired
    UserService userService;
	@RequestMapping(value = "")
public String index(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
	return this.toPage(request, response, model);
}


@ResponseBody
@RequestMapping(value = "list")
public PageInfo<RfPadTask> list(HttpServletRequest request,HttpServletResponse response, Model model, RfPadTask bean,String name) throws Exception {
	String superCreater=null;
	if("vip".equals(name)){
		name=null;
		superCreater="creater";
	}
	List<RfPadTask> list = service.initQuery(bean)
			.andNotEqualTo("creater", superCreater)
			.andEqualTo("batchId",bean.getBatchId())
			.andEqualTo("creater", name)
			.andEqualTo("commandType", bean.getCommandType())
			.andEqualTo("remark", bean.getRemark())
			.andLike("taskStatus", bean.getTaskStatus())
			.andLike("creater", bean.getCreater())
			.andGreaterThanOrEqualTo("createTime",DateUtils.parseDate(bean.getBeginTimeStr()))
			.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
			.addOrderClause("createTime", "desc")
			.addOrderForce(bean.getSort(), bean.getOrder())
			.pageDelTrue(bean.getPage(), bean.getRows());
	PageInfo<RfPadTask> pageInfo = new PageInfo<RfPadTask>(list);
	return pageInfo;
}

}
