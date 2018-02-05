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
import com.redfinger.manager.common.domain.RfVersionLog;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.log.service.LogVersionService;

@Controller
@RequestMapping(value = "/log/version")
public class LogVersionController  extends BaseController {

	@Autowired
	private LogVersionService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfVersionLog> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfVersionLog bean,
			String begin, String end) throws Exception {
		
		Date beginTime = null;
		Date endTime = null;
		
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		
		List<RfVersionLog> list = service.initQuery(bean)
				.andEqualTo("clientType", bean.getClientType())
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		PageInfo<RfVersionLog> pageInfo = new PageInfo<RfVersionLog>(list);
		return pageInfo;
	}
	
}
