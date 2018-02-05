package com.redfinger.manager.modules.message.web;

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
import com.redfinger.manager.common.domain.RfMessageNotify;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.message.service.MessageNotifyService;

@Controller
@RequestMapping(value = "/message/manage")
public class MessageNotifyController  extends BaseController {

	@Autowired
	private MessageNotifyService service;
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfMessageNotify> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfMessageNotify bean,String userMobilePhone, Integer externalUserId) throws Exception {
		
		Integer userId = null;
		if(StringUtils.isNotBlank(userMobilePhone) || externalUserId != null){
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(userMobilePhone, externalUserId);
			if(null != user){
				userId = user.getUserId();
			}else{
				userId = -1;
			}
		}
		
		List<RfMessageNotify> list = service.initQuery(bean)
				.andEqualTo("notifyType", bean.getNotifyType())
				.andLike("notityMessage", bean.getNotityMessage())
				.andEqualTo("userId", userId)
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		if(null != list && list.size()>0){
			for (RfMessageNotify message : list) {
				if(null != message.getUserId()){
					message.getMap().put("userMobilePhone", userService.get(message.getUserId()).getUserMobilePhone());
				}
				if(org.apache.commons.lang3.StringUtils.isNotBlank(message.getIsLook())){
					message.getMap().put("lookName", YesOrNoStatus.DICT_MAP.get(message.getIsLook()));
				}
				
				/** 查询客户端ID*/
				if(message.getUserId() != null){
					/** 根据用户ID查询用户*/
					RfUser rfUser = userService.load(message.getUserId());
					/** 查询客户端ID*/
					message.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
			}
		}
		
		PageInfo<RfMessageNotify> pageInfo = new PageInfo<RfMessageNotify>(list);
		return pageInfo;
	}
}
