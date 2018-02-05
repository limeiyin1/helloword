package com.redfinger.manager.modules.refund.web;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfRefund;
import com.redfinger.manager.common.domain.RfRefundLog;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.RefundHandleStatus;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.refund.service.RefundLogService;
import com.redfinger.manager.modules.refund.service.RefundService;

@Controller
@RequestMapping(value = "/refund/self")
public class RefundSelfController  extends BaseController {

	@Autowired
	private RefundService service;
	@Autowired
	private UserService userService;
	@Autowired
	private UserPadService userPadService;
	@Autowired
	private RefundLogService refundLogService;
	
	@RequestMapping(value = "")
	public String toApple(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model, "refund_self");
	}
	
	//我的退款处理列表
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfRefund> list(HttpServletRequest request, HttpServletResponse response, Model model, RfRefund bean, String userMobilePhone, Integer externalUserId) throws Exception {
		SysAdmin admin = SessionUtils.getCurrentUser(request);
		Integer userId = null;
		if(StringUtils.isNotBlank(userMobilePhone) || externalUserId != null){
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(userMobilePhone, externalUserId);
			if(user != null){
				userId = user.getUserId();
			}else {
				userId = -1;
			}
		}
		
		service.initQuery().andEqualTo("userId", userId).andEqualTo("handleAdmin", admin.getAdminCode())
		.andEqualTo("handleStatus", bean.getHandleStatus()).andEqualTo("refundStatus", bean.getRefundStatus());
		if(StringUtils.isNotBlank(bean.getBeginTimeStr())){
			service.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()+" 00:00:00"));
		}
		if(StringUtils.isNotBlank(bean.getEndTimeStr())){
			service.andLessThanOrEqualTo("createTime", DateUtils.parseDate(bean.getEndTimeStr()+" 23:59:59"));
		}
		List<RfRefund> refundList = service.addOrderClause("createTime", "desc").pageDelTrue(bean.getPage(), bean.getRows());
		RfUser user = null;
		RfUserPad userPad = null;
		for (RfRefund refund : refundList) {
			user = userService.get(refund.getUserId());
			if(user != null){
				refund.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			userPad = userPadService.get(refund.getUserPadId());
			if(userPad != null){
				refund.getMap().put("bindTime", userPad.getBindTime());
			}
			
			/** 查询客户端ID*/
			if(refund.getUserId() != null){
				/** 根据用户ID查询用户*/
				RfUser rfUser = userService.load(refund.getUserId());
				/** 根据用户ID查询客户端ID*/
				refund.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
		}
		PageInfo<RfRefund> pageInfo = new PageInfo<RfRefund>(refundList);
		return pageInfo;
	}
	
	@RequestMapping(value = "look")
	public String look(HttpServletRequest request, HttpServletResponse response, Model model, RfRefund bean) throws Exception {
		service.look(request, model, bean);
		
		return this.toPage(request, response, model, "refund_look");
	}
	
	@ResponseBody
	@RequestMapping(value = "refundLogList")
	public PageInfo<RfRefundLog> refundLogList(HttpServletRequest request, HttpServletResponse response, Model model, RfRefund bean) throws Exception {
		List<RfRefundLog> refundLogList = null;
		if(bean.getRefundId() != null){
			refundLogList = refundLogService.initQuery().andEqualTo("refundId", bean.getRefundId()).addOrderClause("createTime", "desc").addOrderClause("refundLogId", "desc")
					.pageStopTrue(bean.getPage(), bean.getRows());
			
			Map<Integer, RfRefundLog> refundLogMap = new HashMap<Integer, RfRefundLog>();
			for (RfRefundLog log : refundLogList) {
				refundLogMap.put(log.getRefundLogId(), log);
			}
			
			for (RfRefundLog log : refundLogList) {
				RfRefundLog parentLog = refundLogMap.get(log.getpRefundLogId());
				if(parentLog != null){
					log.getMap().put("parentHandleStatus", parentLog.getHandleStatus());
				}
			}
		}
		PageInfo<RfRefundLog> page = new PageInfo<RfRefundLog>(refundLogList);
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkSecurePwd")
	public Map<String, Object> checkSecurePwd(HttpServletRequest request, HttpServletResponse response, Model model, RfRefund bean, String securePwd) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("code", 0);
		if(StringUtils.isBlank(securePwd)){
			map.put("message", "安全口令不能为空");
			return map;
		}
		if(service.checkSecurePwd(bean, securePwd)){
			map.put("code", 1);
			map.put("message", "安全口令校验成功");
			return map;
		}
		map.put("code", 0);
		map.put("message", "安全口令不正确");
		return map;
	}
	
	@RequestMapping(value = "refundForm")
	public String refundForm(HttpServletRequest request, HttpServletResponse response, Model model, RfRefund bean) throws Exception {
		RfRefund refund = service.get(bean.getRefundId());
		model.addAttribute("refund", refund);
		
		RfUser user = userService.get(refund.getUserId());
		model.addAttribute("user", user);
		return this.toPage(request, response, model, "refund_form");
	}
	
	@ResponseBody
	@RequestMapping(value = "refundHandle")
	public void refundHandle(HttpServletRequest request, HttpServletResponse response, Model model, RfRefund bean, String noticeContent, MultipartHttpServletRequest fileRequest) throws Exception {
		if(StringUtils.isBlank(noticeContent) && (RefundHandleStatus.CANCEL.equals(bean.getHandleStatus()) || RefundHandleStatus.BACK.equals(bean.getHandleStatus())
				|| RefundHandleStatus.REFUND.equals(bean.getHandleStatus()))){
			throw new BusinessException("用户公告内容不能为空");
		}
		if(RefundHandleStatus.CANCEL.equals(bean.getHandleStatus())){
			service.cancelRefund(fileRequest, bean, noticeContent);
		}else if(RefundHandleStatus.BACK.equals(bean.getHandleStatus())){
			service.backRefund(fileRequest, bean, noticeContent);
		}else {
			service.refundHandle(request, bean, noticeContent, fileRequest);
		}
	}
	
	//归档
	@ResponseBody
	@RequestMapping(value = "finishRefund")
	public void finishRefund(HttpServletRequest request, HttpServletResponse response, Model model, RfRefund bean) throws Exception {
		String[] ids = bean.getIds().split(",");
		List<Integer> refundIds = new ArrayList<Integer>();
		for (String refundid : ids) {
			refundIds.add(Integer.valueOf(refundid));
		}
		if(refundIds.size()==0){
			throw new BusinessException("请先选择记录");
		}
		int count = service.initQuery().andIn("refundId", refundIds).andNotIn("handleStatus", new String[]{RefundHandleStatus.BACK,RefundHandleStatus.CANCEL,RefundHandleStatus.REFUND}).countDelTrue();
		if(count > 0){
			throw new BusinessException("只有退回、取消、已退款的记录才能归档");
		}
		bean.setHandleStatus(RefundHandleStatus.FINISH);
		service.finishRefund(request, bean);
	}
}