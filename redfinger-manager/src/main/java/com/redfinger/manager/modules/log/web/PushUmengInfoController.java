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
import com.redfinger.manager.common.domain.PushUmengInfoLog;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.PushStatus;
import com.redfinger.manager.common.utils.PushType;
import com.redfinger.manager.modules.log.service.PushUmengInfoLogService;
@Controller
@RequestMapping(value = "/log/uMeng")
public class PushUmengInfoController extends BaseController  {
	@Autowired
	PushUmengInfoLogService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		model.addAttribute("pushTypes", PushType.DICT_MAP);
		model.addAttribute("pushStatus", PushStatus.DICT_MAP);
		
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<PushUmengInfoLog> list(HttpServletRequest request, HttpServletResponse response, Model model, PushUmengInfoLog bean, String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}

		List<PushUmengInfoLog> list = service.initQuery(bean)
				.andLike("userMobilePhone", bean.getUserMobilePhone())
				.andLike("padCode", bean.getPadCode())
				.andEqualTo("pushType", bean.getPushType())
				.andEqualTo("pushStatus", bean.getPushStatus())
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime).addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());
		for(PushUmengInfoLog pushUmengInfoLog : list){
			if(StringUtils.isNotBlank(pushUmengInfoLog.getPushType())){
				pushUmengInfoLog.getMap().put("pushTypeName", PushType.DICT_MAP.get(pushUmengInfoLog.getPushType()));
			}
			if(StringUtils.isNotBlank(pushUmengInfoLog.getPushStatus())){
				pushUmengInfoLog.getMap().put("pushStatusName", PushStatus.DICT_MAP.get(pushUmengInfoLog.getPushStatus()));
			}
		}
		PageInfo<PushUmengInfoLog> pageInfo = new PageInfo<PushUmengInfoLog>(list);
		return pageInfo;

	}
}
