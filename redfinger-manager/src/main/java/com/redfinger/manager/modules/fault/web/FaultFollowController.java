package com.redfinger.manager.modules.fault.web;

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
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfFaultFeedback;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.helper.PermissionHelper;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.fault.service.FaultFeedbackService;
import com.redfinger.manager.modules.fault.service.FaultHandleService;

@Controller
@RequestMapping(value = "/fault/follow")
public class FaultFollowController extends BaseController {

	@Autowired
	FaultFeedbackService service;
	@Autowired
	ClassService classService;
	@Autowired
	FaultHandleService handleService;
	@Autowired
	PadService padService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 故障类型
		model.addAttribute("categoryTree", classService.getStopTrueTreeAndTop(ConstantsDb.rfClassClassTypeFault()));
		// 测试 & 运维 & 客服
		List<String> roles=Lists.newArrayList();
		roles.addAll(Lists.newArrayList(ConfigConstantsDb.configRoleCeshi().split(",")));
		roles.addAll(Lists.newArrayList(ConfigConstantsDb.configRoleKefu().split(",")));
		roles.addAll(Lists.newArrayList(ConfigConstantsDb.configRoleYunwei().split(",")));
		List<SysAdmin> handerList=PermissionHelper.findAdminByRoleList(Lists.newArrayList(roles));
		model.addAttribute("handerList", handerList);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfFaultFeedback> list(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean, String padCodeStart, String padCodeEnd) throws Exception {
		// 用户状态集合
		List<String> feedbackStatusList=null;
		if(StringUtils.isNotBlank(bean.getFeedbackStatus())){
			feedbackStatusList=Lists.newArrayList(bean.getFeedbackStatus());
		}else{
			feedbackStatusList=Lists.newArrayList(
					// 状态为: 新工单
					ConstantsDb.rfFaultFeedbackFeedbackStatusNew(),
					// 状态为: 已处理
					ConstantsDb.rfFaultFeedbackFeedbackStatusHandle(),
					// 状态为: 移交客服
					ConstantsDb.rfFaultFeedbackFeedbackStatusMovekefu(),
					// 状态为: 移交客服
					ConstantsDb.rfFaultFeedbackFeedbackStatusMoveceshi(),
					// 状态为: 移交运维
					ConstantsDb.rfFaultFeedbackFeedbackStatusMoveyunwei(),
					// 状态为: 处理中
					ConstantsDb.rfFaultFeedbackFeedbackStatusProcessing()
			);
		}
		// 设置咨询人为当前用户
		bean.setPromoter(SessionUtils.getCurrentUserId(request));
		// 根据条件查询所有故障工单
		List<RfFaultFeedback> list = service.query(request, bean,feedbackStatusList,true, padCodeStart, padCodeEnd,null, null);
		if(null != list && list.size() > 0){
			for (RfFaultFeedback faultFeedback : list) {
				// 设备故障来源值所对应的名称
				faultFeedback.getMap().put("clientSourceName", ClientType.DICT_MAP.get(faultFeedback.getClientSource()));
			}
		}
		PageInfo<RfFaultFeedback> pageInfo = new PageInfo<RfFaultFeedback>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		bean=service.get(bean.getFaultFeedbackId());
		model.addAttribute("bean", bean);
		// 故障类型
		model.addAttribute("categoryTree", classService.getStopTrueTree(ConstantsDb.rfClassClassTypeFault()));
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		service.update(request, bean);
	}
	
	@RequestMapping(value = "reply")
	public void reply(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		service.reply(request, bean);
		log.info("reply ids={}",new Object[]{bean.getIds()});
		if(StringUtils.isNotBlank(bean.getIds())){
			String[] ids = bean.getIds().split(",");
			log.info("reply ids={}",new Object[]{ids});
			for (String id : ids) {
				if(StringUtils.isNotBlank(id)){
					log.info("reply id={}",new Object[]{id});
					RfFaultFeedback feedback = service.selectByFaultFeedback(Integer.parseInt(id));
					if(null != feedback){
						String padCode = feedback.getPadCode();
						int faultCount = service.initQuery().andEqualTo("padCode", padCode).andNotEqualTo("feedbackStatus", ConstantsDb.rfFaultFeedbackFeedbackStatusEnd()).countDelTrue();
						log.info("reply faultCount={} padCode={}",new Object[]{faultCount,feedback.getPadCode()});
						if (faultCount == 0) {
							// 获取设备&置故障状态为正常
							RfPad pad = padService.getPadByPadCode(feedback.getPadCode());
							if (pad != null) {
								log.info("reply padCode={}",new Object[]{pad.getPadCode()});
								pad.setFaultStatus(ConstantsDb.rfPadFaultStatusNomarl());
								padService.update(request, pad);
							}
						}
					}
				}
			}
		}
	}

	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		service.detail(request, model, bean);
		return this.toPage(request, response, model,"detail");
	}
	
	@RequestMapping(value = "fixAndReply")
	public void fixAndReply(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		
		// 处理工单
		service.fix(request, bean);
		
		// 工单状态为已处理, 完结工单
		if(bean.getFeedbackStatus().equals(ConstantsDb.rfFaultFeedbackFeedbackStatusHandle())){
			this.reply(request, response, model, bean);
		}
		
	}

}
