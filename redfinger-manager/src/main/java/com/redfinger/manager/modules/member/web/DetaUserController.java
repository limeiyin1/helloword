package com.redfinger.manager.modules.member.web;

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
import com.redfinger.manager.common.domain.RfBetaUser;
import com.redfinger.manager.common.domain.RfSms;
import com.redfinger.manager.common.domain.RfSmsResultInfo;
import com.redfinger.manager.common.sms.SmsType;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.member.service.BetaUserService;

@Controller
@RequestMapping(value = "/member/beta")
public class DetaUserController  extends BaseController {

	@Autowired
	BetaUserService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfBetaUser> list(HttpServletRequest request, HttpServletResponse response, Model model, RfBetaUser bean) throws Exception {
		List<RfBetaUser> list = service.initQuery(bean)
				.andLike("userMobilePhone", bean.getUserMobilePhone())
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		
		PageInfo<RfBetaUser> pageInfo = new PageInfo<RfBetaUser>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfBetaUser bean) throws Exception {
		service.remove(request, bean);
	}
}
