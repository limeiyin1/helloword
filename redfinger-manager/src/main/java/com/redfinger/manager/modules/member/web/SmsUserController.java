package com.redfinger.manager.modules.member.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfSmsTemplate;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sms.bean.SendResult;
import com.redfinger.manager.modules.sms.service.SmsService;
import com.redfinger.manager.modules.sms.service.SmsTemplateService;

@Controller
@RequestMapping(value = "/member/pad")
public class SmsUserController extends BaseController {

	@Autowired
	PadService padService;
	@Autowired
	SmsTemplateService smsTemplateService;
	@Autowired
	ControlService controlService;
	@Autowired
	UserService userService;
	@Autowired
	SmsService smsService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	IdcService idcService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfIdc> idcList = idcService.initQuery().findStopTrue();
		model.addAttribute("idcList", idcList);
		return this.toPage(request, response, model);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPad> list(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String userId, String bindMobile) throws Exception {
		List<Integer> padIds = null;
		if (StringUtils.isNotBlank(userId) || StringUtils.isNotBlank(bindMobile)) {
			List<RfUserPad> userPadList = userPadService.initQuery().andEqualTo("userId", userId).andEqualTo("bindMobile", bindMobile).findAll();
			if (!CollectionUtils.isEmpty(userPadList)) {
				padIds = (List<Integer>) Collections3.extractToList(userPadList, "padId");
			} else {
				padIds = Lists.newArrayList(-1);
			}
		}
		List<RfPad> list = padService.initQuery(bean).andEqualTo("enableStatus", bean.getEnableStatus()).andEqualTo("userPadStatus", bean.getUserPadStatus())
				.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).andEqualTo("padStatus", bean.getPadStatus()).andEqualTo("faultStatus", bean.getFaultStatus()).andEqualTo("idcId", bean.getIdcId())
				.andLike("padCode", bean.getPadCode()).andLike("padIp", bean.getPadIp()).andIn("padId", padIds).addOrderClause("padId", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		for (RfPad pad : list) {
			RfUserPad userPad = userPadService.initQuery().andEqualTo("padId", pad.getPadId()).singleAll().get(0);
			pad.getMap().put("userId", userPad.getUserId());
			pad.getMap().put("bindMobile", userPad.getBindMobile());
			pad.getMap().put("bindTime", userPad.getBindTime());
			pad.getMap().put("idcId", idcService.get(pad.getIdcId()).getIdcName());
		}
		PageInfo<RfPad> pageInfo = new PageInfo<RfPad>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		List<RfSmsTemplate> templateList = smsTemplateService.initQuery().addOrderClause("reorder", "asc").findStopTrue();
		model.addAttribute("templateList", templateList);
		model.addAttribute("templateListJson", JsonUtils.ObjectToString(templateList));
		if (CollectionUtils.isEmpty(templateList)) {
			model.addAttribute("defaultContent", "");
		} else {
			model.addAttribute("defaultContent", templateList.get(0).getSmsTemplateContent());
		}
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "sendCheckbox")
	public SendResult sendCheckbox(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String smsContent, Integer templateId, String batchName) throws Exception {
		return smsService.sendSms(request, bean.getIds(), smsContent, templateId,batchName);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "sendSearch")
	public SendResult sendSearch(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean, String userId, String bindMobile, String smsContent, Integer templateId,String batchName)
			throws Exception {
		List<Integer> padIds = null;
		if (StringUtils.isNotBlank(userId) || StringUtils.isNotBlank(bindMobile)) {
			List<RfUserPad> userPadList = userPadService.initQuery().andEqualTo("userId", userId).andEqualTo("bindMobile", bindMobile).findAll();
			if (!CollectionUtils.isEmpty(userPadList)) {
				padIds = (List<Integer>) Collections3.extractToList(userPadList, "padId");
			} else {
				padIds = Lists.newArrayList(-1);
			}
		}
		List<RfPad> list = padService.initQuery(bean).andEqualTo("enableStatus", bean.getEnableStatus()).andEqualTo("userPadStatus", bean.getUserPadStatus())
				.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).andEqualTo("padStatus", bean.getPadStatus()).andEqualTo("faultStatus", bean.getFaultStatus()).andEqualTo("idcId", bean.getIdcId())
				.andLike("padCode", bean.getPadCode()).andLike("padIp", bean.getPadIp()).andIn("padId", padIds).findAll();
		String ids = Collections3.extractToString(list, "padId", ",");
		return smsService.sendSms(request, ids, smsContent, templateId,batchName);
	}

}
