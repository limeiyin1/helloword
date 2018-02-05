package com.redfinger.manager.modules.fault.web;

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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfFaultFeedback;
import com.redfinger.manager.common.domain.RfFaultHandle;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.fault.service.FaultFeedbackService;
import com.redfinger.manager.modules.fault.service.FaultHandleService;
import com.redfinger.manager.modules.sys.service.AdminService;

@Controller
@RequestMapping(value = "/fault/selfHistory")
public class FaultSelfHistoryController extends BaseController {

	@Autowired
	FaultFeedbackService service;
	@Autowired
	ClassService classService;
	@Autowired
	FaultHandleService handleService;
	@Autowired
	FaultFeedbackService feedbackService;
	@Autowired
	PadService padService;
	@Autowired
	AdminService adminService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 故障类型
		model.addAttribute("categoryTree", classService.getStopTrueTreeAndTop(ConstantsDb.rfClassClassTypeFault()));
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfFaultHandle> list(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		List<RfFaultHandle> list = handleService.initQuery(bean)
				.andEqualTo("adminUserId", SessionUtils.getCurrentUserId(request))
				.andIsNotNull("modifyTime")
				.andGreaterThanOrEqualTo("modifyTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("modifyTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("modifyTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		for(RfFaultHandle handle : list){
			RfFaultFeedback feedback= feedbackService.load(handle.getFaultFeedbackId());
			handle.getMap().put("feedbackStatus", feedback.getFeedbackStatus());
			handle.getMap().put("createTime", feedback.getCreateTime());
			handle.getMap().put("feedbackContent", feedback.getFeedbackContent());
			handle.getMap().put("feedbackSource", feedback.getFeedbackSource());
			handle.getMap().put("feedbackContact", feedback.getFeedbackContact());
			handle.getMap().put("feedbackQq", feedback.getFeedbackQq());
			handle.getMap().put("padCode", feedback.getPadCode());
			
			//故障类型
			handle.getMap().put("className", classService.getFullClass(ConstantsDb.rfClassClassTypeFault(), feedback.getClassId(), " - "));
			//获取咨询人
			if(StringUtils.isNotBlank(feedback.getCreater())){
				if(null != adminService.get(feedback.getCreater())){
					handle.getMap().put("creater", adminService.get(feedback.getCreater()).getAdminName());
				}
			}
			// 获取设备
			if(StringUtils.isNotBlank(feedback.getPadCode())){
				List<RfPad> padList = padService.initQuery().andEqualTo("padCode", feedback.getPadCode()).singleAll();
				if (padList.size() > 0) {
					RfPad pad = padList.get(0);
					handle.getMap().put("padIp", pad.getPadIp());
					handle.getMap().put("bindStatus",pad.getBindStatus());
				}
			}
		}
		PageInfo<RfFaultHandle> pageInfo = new PageInfo<RfFaultHandle>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		service.detail(request, model, bean);
		return this.toPage(request, response, model,"detail");
	}
}
