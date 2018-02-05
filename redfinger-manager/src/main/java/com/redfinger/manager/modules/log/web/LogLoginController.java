package com.redfinger.manager.modules.log.web;

import java.util.ArrayList;
import java.util.Date;
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
import com.redfinger.manager.common.domain.LogAdmin;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.SysLoginLog;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.log.service.SysLoginLogService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/log/login")
public class LogLoginController extends BaseController {

	@Autowired
	SysLoginLogService service;
	@Autowired
	UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<SysLoginLog> list(HttpServletRequest request, HttpServletResponse response, Model model, LogAdmin bean,String phone) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(bean.getBeginTimeStr())) {
			beginTime = DateUtils.parseDate(bean.getBeginTimeStr());
		}
		if (StringUtils.isNotBlank(bean.getEndTimeStr())) {
			endTime = DateUtils.addDays(DateUtils.parseDate(bean.getEndTimeStr()), 1);
		}
		
		Integer userId=null;
        if(StringUtils.isNotBlank(phone)){
         	RfUser user=userService.getUserByUserPhone(phone);
        	 if(user==null){
        		userId=-1;
        	 }else{
        		 userId=user.getUserId();
        	 }
        }
        
		List<SysLoginLog> list = service.initQuery(bean)
				.andEqualTo("userId",userId)
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime).addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());
		if(list!=null&&list.size()>0){
			for(SysLoginLog sysLoginLog : list){
				if(sysLoginLog!=null&&sysLoginLog.getUserId()!=null){
					RfUser rfUser =  userService.get(sysLoginLog.getUserId());
					if(rfUser!=null){
						sysLoginLog.getMap().put("userEmail",rfUser.getUserEmail());
						sysLoginLog.getMap().put("userMobilePhone",rfUser.getUserMobilePhone());
						sysLoginLog.getMap().put("userName",rfUser.getUserName());
					}
					
				}
			}
		}
		PageInfo<SysLoginLog> pageInfo = new PageInfo<SysLoginLog>(list);
		return pageInfo;

	}
}
