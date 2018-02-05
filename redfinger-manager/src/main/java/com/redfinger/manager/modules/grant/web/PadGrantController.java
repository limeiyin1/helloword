package com.redfinger.manager.modules.grant.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadGrant;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.SysOrg;
import com.redfinger.manager.common.jsm.FingerProducer;
import com.redfinger.manager.common.jsm.MessageProducer;
import com.redfinger.manager.common.mapper.RfPadMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.GrantControlUtils;
import com.redfinger.manager.common.utils.GrantModeUtils;
import com.redfinger.manager.common.utils.GrantWatchUtils;
import com.redfinger.manager.modules.facility.service.PadGrantService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.grant.service.PadGrantCodeService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/grant/grant")
public class PadGrantController extends BaseController{
	@Autowired
	PadGrantService service;
	@Autowired
	private PadGrantCodeService codeService;
	@Autowired
	private PadService padService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageProducer messageProducer;
	@Autowired
	private FingerProducer fingerProducer;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("grantContrls", GrantControlUtils.DICT_MAP);
		model.addAttribute("grantWatchs", GrantWatchUtils.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfPadGrant> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfPadGrant bean, 
			String beginGrant, String endGrant,String padName,String grantorUserName,String userName,
			Integer grantorExternalUserId, Integer externalUserId)throws Exception {
		Integer padId = null;
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(beginGrant)) {
			beginTime = DateUtils.parseDate(beginGrant);
		}
		if (StringUtils.isNotBlank(endGrant)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(endGrant), 1);
		}
		
		if(StringUtils.isNotBlank(padName)){
			List<RfPad> list = padService.initQuery().andEqualTo("padName", padName).findAll();
			if(null != list && list.size()>0){
				padId = list.get(0).getPadId();
			}else{
				padId = 0;
			}
		}
		//过滤授权人
		List<Integer> grantorUserIdList = Lists.newArrayList();
		if(StringUtils.isNotBlank(grantorUserName) || grantorExternalUserId != null){
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", grantorUserName).andEqualTo("externalUserId", grantorExternalUserId).findDelTrue();
	  		if(!Collections3.isEmpty(userList)){
	  			grantorUserIdList.addAll(Collections3.extractToList(userList, "userId"));
	  		}else{
	  			grantorUserIdList.add(-1);
	  		}
	  	}
		//过滤被授权人
		List<Integer> userIdList = Lists.newArrayList();
		if(StringUtils.isNotBlank(userName)|| externalUserId != null){
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userName).andEqualTo("externalUserId",externalUserId).findDelTrue();
	  		if(!Collections3.isEmpty(userList)){
	  			userIdList.addAll(Collections3.extractToList(userList, "userId"));
	  		}else{
	  			userIdList.add(-1);
	  		}
	  	}
		List<RfPadGrant> list = service.initQuery(bean)
				.andIn("grantorUserId", grantorUserIdList)
				.andIn("userId", userIdList)
				.andGreaterThanOrEqualTo("grantTime",beginTime)
				.andLessThan("grantTime", endTime)
				.andEqualTo("grantControl", bean.getGrantControl())
				.andEqualTo("grantWatch", bean.getGrantWatch())
				.andEqualTo("padId", padId)
				.addOrderClause("createTime", "desc nulls last")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		if(null != list && list.size() > 0){
			for (RfPadGrant rfPadGrant : list) {
				if(null != rfPadGrant.getUserId()){
					rfPadGrant.getMap().put("userName", userService.get(rfPadGrant.getUserId()).getUserMobilePhone());
				}
				if(null != rfPadGrant.getGrantorUserId()){
					rfPadGrant.getMap().put("grantorUserName", userService.get(rfPadGrant.getGrantorUserId()).getUserMobilePhone());
				}
				if(null != rfPadGrant.getPadId()){
					rfPadGrant.getMap().put("padName", padService.get(rfPadGrant.getPadId()).getPadName());
				}
				
				if(null != rfPadGrant.getGrantCodeId()){
					rfPadGrant.getMap().put("grantCode", codeService.get(rfPadGrant.getGrantCodeId()).getGrantCode());
				}
				if(StringUtils.isNotBlank(rfPadGrant.getGrantMode())){
					rfPadGrant.getMap().put("grantModeCode", GrantModeUtils.DICT_MAP.get(rfPadGrant.getGrantMode()));
				}
				if(StringUtils.isNotBlank(rfPadGrant.getGrantControl())){
					rfPadGrant.getMap().put("grantControlCode", GrantControlUtils.DICT_MAP.get(rfPadGrant.getGrantControl()));
				}
				if(StringUtils.isNotBlank(rfPadGrant.getGrantWatch())){
					rfPadGrant.getMap().put("grantWatchCode", GrantWatchUtils.DICT_MAP.get(rfPadGrant.getGrantWatch()));
				}
				/** 增加授权类型字段 */
				rfPadGrant.getMap().put("grantType", rfPadGrant.getGrantType()==null?"1":rfPadGrant.getGrantType());
				
				/** 查询授权人客户端ID*/
				if(rfPadGrant.getUserId() != null){
					/** 根据用户Id查询用户*/
					RfUser rfUser = userService.load(rfPadGrant.getUserId());
					/** 查询客户端ID*/
					rfPadGrant.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
				/** 查询被授权人客户端ID*/
				if(rfPadGrant.getGrantorUserId() != null){
					/** 根据用户Id查询用户*/
					RfUser rfUser = userService.load(rfPadGrant.getGrantorUserId());
					/** 根据用户ID查询客户端ID*/
					rfPadGrant.getMap().put("grantorExternalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
				}
			}
		}
		        
		PageInfo<RfPadGrant> pageInfo = new PageInfo<RfPadGrant>(list);

		return pageInfo;
	}
	
	@ResponseBody
	@RequestMapping(value = "cancelGrant")
	public void cancelGrant(HttpServletRequest request, HttpServletResponse response, Model model, RfPadGrant bean){
		try {
			String[] idArray = StringUtils.split(bean.getIds(), ",");
			for (String string : idArray) {
				log.info("授权id："+string);
				bean = service.get(Integer.parseInt(string));
				service.cancelGrant(request, bean);
				
				//向用户发送jms
				/** 向被授权的用户发送jms消息 */
				JSONObject sendMessageObj = new JSONObject();
				sendMessageObj.put("opType", "refresh");
				sendMessageObj.put("userId", bean.getUserId());
				String sendMessageStr = sendMessageObj.toString();
				log.info("sendMessageStr: "+ sendMessageStr);
				// 发送jms消息
				fingerProducer.sendMessage(sendMessageStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
