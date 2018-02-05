package com.redfinger.manager.modules.sms.web;

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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfSms;
import com.redfinger.manager.common.domain.RfSmsResultInfo;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.sms.SmsType;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sms.service.SmsResultInfoService;
import com.redfinger.manager.modules.sms.service.SmsService;

@Controller
@RequestMapping(value = "/sms/sent")
public class SmsController extends BaseController {

	@Autowired
	SmsService service;
	@Autowired
	SmsResultInfoService infoService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("smsType", SmsType.DICT_MAP);
		model.addAttribute("clientType", ClientType.DICT_MAP);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfSms> list(HttpServletRequest request, HttpServletResponse response, Model model, RfSms bean, Integer externalUserId) throws Exception {
		
		/** 数据表用户ID*/
		Integer userId = null;
		/** 根据客户端ID查询用户ID*/
		if (externalUserId!=null) {
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(null,externalUserId);
			if (user != null) {
				userId = user.getUserId();
			} else {
				userId = -1;
			}
		}
		
		List<RfSms> list = service.initQuery(bean)
				.andLike("smsMobile", bean.getSmsMobile())
				.andLike("userName", bean.getUserName())
				.andLike("padCode", bean.getPadCode())
				.andEqualTo("smsType", bean.getSmsType())
				.andEqualTo("clientSource", bean.getClientSource())
				.andEqualTo("smsSource", bean.getSmsSource())
				.andEqualTo("smsSendType", bean.getSmsSendType())
				.andEqualTo("userId", userId)
				.andGreaterThanOrEqualTo("sendTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("sendTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.addOrderClause("sendTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null!=list && list.size()>0){
			for (RfSms rfSms : list) {
				if(StringUtils.isEmpty(rfSms.getSmsType())){
					rfSms.getMap().put("smsTypeName", SmsType.DICT_MAP.get(SmsType.SMS));
				}else{
					rfSms.getMap().put("smsTypeName", SmsType.DICT_MAP.get(rfSms.getSmsType()));
				}
				if(StringUtils.isEmpty(rfSms.getClientSource())){
					rfSms.getMap().put("clientSourceName", "");
				}else{
					rfSms.getMap().put("clientSourceName", ClientType.DICT_MAP.get(rfSms.getClientSource()));
				}
				
				if(StringUtils.isNotBlank(rfSms.getResultStatusCode()) 
						&& StringUtils.isNotBlank(rfSms.getSmsSendType())){
					RfSmsResultInfo info = infoService.selectByCodeOrType(rfSms.getResultStatusCode().trim(), rfSms.getSmsSendType());
					if(null != info){
						rfSms.getMap().put("resultStatusName", info.getResultCodeDetail());
					}else{
						rfSms.getMap().put("resultStatusName", rfSms.getResultStatusCode());
					}
				}else{
					rfSms.getMap().put("resultStatusName", rfSms.getResultStatusCode());
				}
				
				/** 查询客户端ID*/
				if(rfSms.getUserId() != null){
					/** 根据用户Id查询用户*/
					RfUser rfUser = userService.load(rfSms.getUserId());
					/** 查询客户端ID*/
					rfSms.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
				
				
			}
		}
		
		PageInfo<RfSms> pageInfo = new PageInfo<RfSms>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "addmobileForm")
	public String addmobileForm(HttpServletRequest request, HttpServletResponse response, Model model, RfSms bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "addmobile")
	public void addmobile(HttpServletRequest request, HttpServletResponse response, Model model, RfSms bean,String batchName) throws Exception {
		service.addmobile(request, bean,batchName);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfSms bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfSms bean) throws Exception {
		service.stop(request, bean);
	}
	
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfSms bean) throws Exception {
		service.delete(request, bean);
	}
}
