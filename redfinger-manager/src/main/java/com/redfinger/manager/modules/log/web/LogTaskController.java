package com.redfinger.manager.modules.log.web;

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
import com.redfinger.manager.common.domain.RfRbcLog;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.facility.service.PadService;

import com.redfinger.manager.modules.goods.service.GoodsShopPackageService;

import com.redfinger.manager.modules.log.service.LogTaskService;
import com.redfinger.manager.modules.log.service.TaskService;
import com.redfinger.manager.modules.member.service.UserService;


@Controller
@RequestMapping(value = "/log/task")
public class LogTaskController extends BaseController {

	@Autowired
	LogTaskService service;
	@Autowired
	UserService userService;
	@Autowired
	PadService padService;
	@Autowired
	TaskService taskService;
	@Autowired
	GoodsShopPackageService shopService;

	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfRbcLog> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfRbcLog bean,
			String begin, String end,String userMobilePhone,Integer externalUserId) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		Integer uId = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		if(StringUtils.isNotBlank(userMobilePhone)||externalUserId!=null){
			try {
				List<RfUser> list_u = userService.initQuery()
						.andEqualTo("userMobilePhone", userMobilePhone)
						.andEqualTo("externalUserId", externalUserId)
						.findStopTrue();
				if(list_u.size()!=1){
					uId = 0;	
				}
				uId=list_u.get(0).getUserId();	
			} catch (Exception e) {
				//throw new BusinessException("手机号码不存在或绑定多用户错误！");
			}
		}
				List<RfRbcLog> list = service.initQuery(bean)
				.andEqualTo("userId", uId)
				.andLike("taskCode", bean.getTaskCode())
				.andLike("padCode", bean.getPadCode())
				.andEqualTo("logType", bean.getLogType())
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		for(RfRbcLog log :list){
			log.getMap().put("userName", log.getUserId() == null ? "--" : userService.load(log.getUserId()).getUserName());
			log.getMap().put("userMobilePhone", log.getUserId() == null ? "--" : userService.load(log.getUserId()).getUserMobilePhone());
			log.getMap().put("padName", log.getPadId() == null ? "--" : padService.load(log.getPadId()).getPadName());
			log.getMap().put("taskName", log.getTaskId() == null ? "--" : taskService.load(log.getTaskId()).getTaskName());
			log.getMap().put("commodity", log.getPackageId() == null ? "--" : shopService.load(log.getPackageId()).getName());
//			log.getMap().put(userService.load(log.getUserId()).getUserMobilePhone(),log.getUserId());
			
			/** 查询客户端ID*/
			if(log.getUserId() != null){
				/** 根据用户Id查询用户*/
				RfUser rfUser = userService.load(log.getUserId());
				/** 查询客户端ID*/
				log.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
		}
		PageInfo<RfRbcLog> pageInfo = new PageInfo<RfRbcLog>(list);
		return pageInfo;

	}
}
