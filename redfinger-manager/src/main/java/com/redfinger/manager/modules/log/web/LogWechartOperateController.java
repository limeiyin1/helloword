package com.redfinger.manager.modules.log.web;

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
import com.redfinger.manager.common.domain.LogWechartOperate;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.log.service.LogWechartOperateService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/log/wechart")
public class LogWechartOperateController extends BaseController {

	@Autowired
	LogWechartOperateService service;
	@Autowired
	UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<LogWechartOperate> list(HttpServletRequest request, HttpServletResponse response, Model model,
			LogWechartOperate bean, String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}

		List<LogWechartOperate> list = service.initQuery(bean).andEqualTo("operateType", bean.getOperateType())
				.andGreaterThanOrEqualTo("createTime", beginTime).andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		for (LogWechartOperate LogWechartOperate : list) {
			if (StringUtils.isNotBlank(LogWechartOperate.getCreater())) {
				RfUser user = userService.get(LogWechartOperate.getUserId());
				if (user != null) {
					LogWechartOperate.getMap().put("userEmail", user.getUserEmail());
					LogWechartOperate.getMap().put("userMobilePhone", user.getUserMobilePhone());
				}
			}
		}
		PageInfo<LogWechartOperate> pageInfo = new PageInfo<LogWechartOperate>(list);
		return pageInfo;
	}
}
