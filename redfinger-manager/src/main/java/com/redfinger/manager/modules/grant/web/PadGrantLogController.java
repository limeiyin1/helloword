package com.redfinger.manager.modules.grant.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.redfinger.manager.common.domain.RfPadGrantLog;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.GrantControlUtils;
import com.redfinger.manager.common.utils.GrantModeUtils;
import com.redfinger.manager.common.utils.GrantWatchUtils;
import com.redfinger.manager.common.utils.OperateTypeUtils;
import com.redfinger.manager.modules.grant.service.PadGrantCodeService;
import com.redfinger.manager.modules.grant.service.PadGrantLogService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/grant/grantLog")
public class PadGrantLogController extends BaseController {
	@Autowired
	private PadGrantLogService service;
	@Autowired
	private PadGrantCodeService codeService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("maps", OperateTypeUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPadGrantLog> list(HttpServletRequest request,	HttpServletResponse response, Model model, RfPadGrantLog bean, 
			String grantorUserMobilePhone,String userMobilePhone,String begin, String end,Integer externalUserId, Integer grantorExternalUserId)
			throws Exception {
		RfUser grantorUser = null;	//授权人
		RfUser user = null;	//被授权人
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.parseDate(end);
		}
		if(StringUtils.isNotBlank(grantorUserMobilePhone) || grantorExternalUserId != null){
			grantorUser = userService.getUserByExternalUserIdOrUserPhone(grantorUserMobilePhone, grantorExternalUserId);
			if(grantorUser == null){
				grantorUser = new RfUser();
				grantorUser.setUserId(-1);
			}
		}
		if (StringUtils.isNotBlank(userMobilePhone)||externalUserId!=null) {
			user = userService.getUserByExternalUserIdOrUserPhone(userMobilePhone,externalUserId);
			if(user == null){
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		List<RfPadGrantLog> list = service.initQuery(bean)
				.andEqualTo("userId", user!=null?user.getUserId():null)
				.andEqualTo("grantorUserId", grantorUser!=null?grantorUser.getUserId():null)
				.andEqualTo("operateType", bean.getOperateType())
				.andGreaterThanOrEqualTo("operateTime", beginTime)
				.andLessThan("operateTime", endTime)
				.addOrderClause("operateTime", "desc nulls last")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		List<Integer> userIdList = new ArrayList<Integer>();
		for (RfPadGrantLog log : list) {
			userIdList.add(log.getGrantorUserId());
			userIdList.add(log.getUserId());
		}
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		if(userIdList.size() > 0){
			List<RfUser> userList = userService.initQuery().andIn("userId", userIdList).findAll();
			for (RfUser u : userList) {
				userMap.put(u.getUserId(), u);
			}
		}
		
		if(null != list && list.size() > 0){
			for (RfPadGrantLog rfPadGrantLog : list) {
				if(null != rfPadGrantLog.getGrantCodeId()){
					rfPadGrantLog.getMap().put("grantCode", codeService.get(rfPadGrantLog.getGrantCodeId()).getGrantCode());
				}
				if(StringUtils.isNotBlank(rfPadGrantLog.getGrantMode())){
					rfPadGrantLog.getMap().put("grantModeCode", GrantModeUtils.DICT_MAP.get(rfPadGrantLog.getGrantMode()));
				}
				if(StringUtils.isNotBlank(rfPadGrantLog.getOperateType())){
					rfPadGrantLog.getMap().put("operateTypeCode", OperateTypeUtils.DICT_MAP.get(rfPadGrantLog.getOperateType()));
				}
				if(StringUtils.isNotBlank(rfPadGrantLog.getGrantControl())){
					rfPadGrantLog.getMap().put("grantControlCode", GrantControlUtils.DICT_MAP.get(rfPadGrantLog.getGrantControl()));
				}
				if(StringUtils.isNotBlank(rfPadGrantLog.getGrantWatch())){
					rfPadGrantLog.getMap().put("grantWatchCode", GrantWatchUtils.DICT_MAP.get(rfPadGrantLog.getGrantWatch()));
				}
				
				user = userMap.get(rfPadGrantLog.getUserId());
				grantorUser = userMap.get(rfPadGrantLog.getGrantorUserId());
				if(user != null){
					rfPadGrantLog.getMap().put("userMobilePhone", user.getUserMobilePhone());
				}
				if(grantorUser != null){
					rfPadGrantLog.getMap().put("grantorUserMobilePhone", grantorUser.getUserMobilePhone());
				}
				
				/** 查询授权人客户端ID*/
				if(rfPadGrantLog.getUserId() != null){
					/** 根据用户Id查询用户*/
					RfUser rfUser = userService.load(rfPadGrantLog.getUserId());
					/** 查询客户端ID*/
					rfPadGrantLog.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
				
				/** 查询被授权人客户端ID*/
				if(rfPadGrantLog.getGrantorUserId() != null){
					/** 根据用户Id查询用户*/
					RfUser rfUser = userService.load(rfPadGrantLog.getGrantorUserId());
					/** 查询客户端ID*/
					rfPadGrantLog.getMap().put("grantorExternalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
			}
		}
		        
		PageInfo<RfPadGrantLog> pageInfo = new PageInfo<RfPadGrantLog>(list);
		return pageInfo;
	}
}
