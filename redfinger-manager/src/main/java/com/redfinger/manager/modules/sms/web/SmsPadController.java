package com.redfinger.manager.modules.sms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfSmsTemplate;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.ViewPadUser;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.FacilityService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.facility.service.ViewPadUserService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sms.bean.SendResult;
import com.redfinger.manager.modules.sms.service.SmsService;
import com.redfinger.manager.modules.sms.service.SmsTemplateService;

@Controller
@RequestMapping(value = "/sms/pad")
public class SmsPadController extends BaseController {

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
	@Autowired
	ViewPadUserService viewPadUserService;
	@Autowired
	private FacilityService facilityService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfIdc> idcList=idcService.initQuery().findStopTrue();
		model.addAttribute("idcList", idcList);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list" )
	public PageInfo<ViewPadUser> list(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean,String padCode2,String padIp2) throws Exception {
		String likePadCode=null;
		String regexStr = "^[0-9a-zA-Z]+$";
		if(StringUtils.isNotBlank(bean.getPadCode())){
			if(!bean.getPadCode().matches(regexStr)){
				likePadCode=bean.getPadCode();
				bean.setPadCode(null);
			}
		}
		if(StringUtils.isNotBlank(padCode2)){
			if(!padCode2.matches(regexStr)){
				likePadCode=padCode2;
				padCode2 = null;
			}
		}
		List<ViewPadUser> list = viewPadUserService.initQuery(bean)
				.andLike("padCode", likePadCode)
				.andGreaterThanOrEqualTo("padCode", bean.getPadCode())
				.andLessThanOrEqualTo("padCode", padCode2)
				.andLike("padIp", bean.getPadIp())
				.andEqualTo("idcId", bean.getIdcId())
				.andEqualTo("padStatus", bean.getPadStatus())
				.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind())
				.andEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart())
				.pageDelTrue(bean.getPage(),bean.getRows());
		
		List<RfFacility> facilityList = facilityService.initQuery().findAll();
		Map<String, RfFacility> facilityMap = new HashMap<String, RfFacility>();
		RfFacility f = null;
		for (RfFacility rfFacility : facilityList) {
			facilityMap.put(rfFacility.getFacilityCode(), rfFacility);
		}
		
		for(ViewPadUser pad:list){
			RfIdc rfIdc = idcService.get(pad.getIdcId());
			if(rfIdc!=null){
				pad.getMap().put("idcId", rfIdc.getIdcName());
			}else{
				pad.getMap().put("idcId",null);
			}
			if(pad.getUserIdT2()!=null){
				RfUser user = userService.get(pad.getUserIdT2());
				pad.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			
			f = facilityMap.get(pad.getPadSource());
            if(f != null){
           	 pad.getMap().put("padSourceName", f.getFacilityName());
            }
		}
		PageInfo<ViewPadUser> pageInfo=new PageInfo<ViewPadUser>(list);
		return pageInfo;
	}
	@ResponseBody
	@RequestMapping(value = "sendSearch")
	public SendResult sendSearch(HttpServletRequest request, HttpServletResponse response, Model model,ViewPadUser bean,String padCode2,String padIp2,String smsContent,Integer templateId,String batchName) throws Exception {
		List<ViewPadUser> list = viewPadUserService.initQuery(bean)
				.andGreaterThanOrEqualTo("padCode", bean.getPadCode())
				.andLessThanOrEqualTo("padCode", padCode2)
				.andGreaterThanOrEqualTo("padIp",bean.getPadIp())
				.andLessThanOrEqualTo("padIp", padIp2)
				.andEqualTo("idcId", bean.getIdcId())
				.andEqualTo("padStatus", bean.getPadStatus())
				.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind())
				.andEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart())
				.findDelTrue();
		String ids=Collections3.extractToString(list, "padId", ",");
		return smsService.sendSms(request,ids,smsContent,templateId,batchName);
	}
	
	@ResponseBody
	@RequestMapping(value = "sendCheckbox")
	public SendResult sendCheckbox(HttpServletRequest request, HttpServletResponse response, Model model,RfPad bean,String smsContent,Integer templateId,String batchName) throws Exception {
		return smsService.sendSms(request,bean.getIds(),smsContent,templateId,batchName);
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		List<RfSmsTemplate> templateList = smsTemplateService.initQuery().addOrderClause("reorder", "asc").findStopTrue();
		model.addAttribute("templateList", templateList);
		model.addAttribute("templateListJson", JsonUtils.ObjectToString(templateList));
		if(CollectionUtils.isEmpty(templateList)){
			model.addAttribute("defaultContent", "");
		}else{
			model.addAttribute("defaultContent", templateList.get(0).getSmsTemplateContent());
		}
		return this.toPage(request, response, model);
	}

	
	

}
