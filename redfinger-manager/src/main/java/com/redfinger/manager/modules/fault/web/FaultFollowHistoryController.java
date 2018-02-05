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
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.helper.PermissionHelper;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.fault.service.FaultFeedbackService;
import com.redfinger.manager.modules.fault.service.FaultHandleService;

@Controller
@RequestMapping(value = "/fault/followHistory")
public class FaultFollowHistoryController extends BaseController {

	@Autowired
	FaultFeedbackService service;
	@Autowired
	ClassService classService;
	@Autowired
	FaultHandleService handleService;

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
		bean.setPromoter(SessionUtils.getCurrentUserId(request));
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
		List<RfFaultFeedback> list = service.query(request, bean,feedbackStatusList,false, padCodeStart, padCodeEnd,null,null);
		if(null != list && list.size() > 0){
			for (RfFaultFeedback faultFeedback : list) {
				faultFeedback.getMap().put("clientSourceName", ClientType.DICT_MAP.get(faultFeedback.getClientSource()));
			}
		}
		
		PageInfo<RfFaultFeedback> pageInfo = new PageInfo<RfFaultFeedback>(list);
		return pageInfo;
	}

	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		service.detail(request, model, bean);
		return this.toPage(request, response, model,"detail");
	}

}
