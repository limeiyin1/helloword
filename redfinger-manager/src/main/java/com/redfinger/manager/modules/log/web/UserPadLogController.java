package com.redfinger.manager.modules.log.web;

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
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPadLog;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.log.service.UserPadLogService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value="/log/userPadLog")
public class UserPadLogController extends BaseController {
	
	@Autowired
	UserPadLogService service;
	@Autowired
	PadService padService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfUserPadLog> list(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPadLog bean,String phone,String field,String userPhone,Integer externalUserId) throws Exception {
		List<Integer> userIds=null;
		if(StringUtils.isNotBlank(userPhone)||externalUserId!=null){
			List<RfUser> userList=userService.initQuery()
					.andEqualTo("userMobilePhone",userPhone)
					.andEqualTo("externalUserId",externalUserId)
					.singleAll();
			if(!CollectionUtils.isEmpty(userList)){
				userIds=(List<Integer>)Collections3.extractToList(userList, "userId");
			}else{
				userIds=Lists.newArrayList(-1);
			}
		}
		String reorder="";
		if(null==field||"".equals(field)){
			field="userPadLogId";
			reorder="reorder";
		}
		if("newPadId".equals(field)){
			reorder="oldPadId";
		}
		if("oldPadId".equals(field)){
			reorder="newPadId";
		}
		List<RfUserPadLog> list = service.initQuery(bean).andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.andIn("userId", userIds)
				.andIsNotNull(field)
				.andIsNull(reorder)
				.andEqualTo("bindMobile", phone)
				.andLike("remark", bean.getRemark())
				.addOrderClause("userPadLogId", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());
		for (RfUserPadLog rfUserPadLog : list) {
			rfUserPadLog.getMap().put("newPadId", null==rfUserPadLog.getNewPadId()?"--":padService.get(rfUserPadLog.getNewPadId()).getPadCode());
			rfUserPadLog.getMap().put("oldPadId", null==rfUserPadLog.getOldPadId()?"--":padService.get(rfUserPadLog.getOldPadId()).getPadCode());
			rfUserPadLog.getMap().put("phone", null==rfUserPadLog.getUserId()?"--":userService.get(rfUserPadLog.getUserId()).getUserMobilePhone());
			
			/** 查询客户端ID*/
			if(rfUserPadLog.getUserId() != null){
				/** 根据用户Id查询用户*/
				RfUser rfUser = userService.load(rfUserPadLog.getUserId());
				/** 查询客户端ID*/
				rfUserPadLog.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
		}
		PageInfo<RfUserPadLog> pageInfo = new PageInfo<RfUserPadLog>(list);
		return pageInfo;

	}

}
