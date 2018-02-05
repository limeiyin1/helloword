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
import com.redfinger.manager.common.domain.LogAdmin;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.log.service.LogAdminService;
import com.redfinger.manager.modules.sys.service.AdminService;

@Controller
@RequestMapping(value = "/log/admin")
public class LogAdminController extends BaseController {

	@Autowired
	LogAdminService service;
	@Autowired
	AdminService adminService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<LogAdmin> list(HttpServletRequest request, HttpServletResponse response, Model model, LogAdmin bean, String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}

		List<LogAdmin> list = service.initQuery(bean)
				.andLike("creater", bean.getCreater())
				.andLike("name", bean.getName())
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andEqualTo("resultStatus", bean.getResultStatus())
				.andLessThan("createTime", endTime).addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());
		for(LogAdmin logAdmin : list){
			if(StringUtils.isNotBlank(logAdmin.getCreater())){
				SysAdmin sysAdmin =  adminService.get(logAdmin.getCreater());
				if(sysAdmin!=null){
					logAdmin.getMap().put("creater",sysAdmin.getAdminName());
				}else{
					logAdmin.getMap().put("creater","");
				}
				
			}
		}
		PageInfo<LogAdmin> pageInfo = new PageInfo<LogAdmin>(list);
		return pageInfo;

	}
}
