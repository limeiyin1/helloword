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
import com.redfinger.manager.common.domain.RfRefundLog;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.RefundLogType;
import com.redfinger.manager.common.utils.RefundStatus;
import com.redfinger.manager.modules.log.service.LogRefundService;


@Controller
@RequestMapping(value = "/log/refund")
public class LogRefundController extends BaseController {
	@Autowired
	LogRefundService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("logTypes", RefundLogType.DICT_MAP);
		model.addAttribute("status", RefundStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfRefundLog> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfRefundLog bean,
			String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		
		
		List<RfRefundLog> list = service.initQuery(bean)
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.andEqualTo("logType", bean.getLogType())
				.andEqualTo("refundStatus", bean.getRefundStatus())
				.andLike("padCode", bean.getPadCode())
				.addOrderClause("createTime", "desc nulls last")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfRefundLog rfRefundLog : list) {
				rfRefundLog.getMap().put("logTypeName", RefundLogType.DICT_MAP.get(rfRefundLog.getLogType()));
				rfRefundLog.getMap().put("refundStatusName", RefundStatus.DICT_MAP.get(rfRefundLog.getRefundStatus()));
			}
		}
		
		PageInfo<RfRefundLog> pageInfo = new PageInfo<RfRefundLog>(list);
		return pageInfo;
	}
}
