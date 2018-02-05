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
import com.redfinger.manager.common.domain.LogAwardRecord;
import com.redfinger.manager.common.domain.RfAwardBatch;
import com.redfinger.manager.common.domain.RfAwardPool;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.award.service.RfAwardBatchService;
import com.redfinger.manager.modules.award.service.RfAwardPoolService;
import com.redfinger.manager.modules.log.service.LogAwardRecordService;
import com.redfinger.manager.modules.member.service.UserService;

@Controller
@RequestMapping(value = "/log/award")
public class LogAwardRecordController extends BaseController {
	@Autowired
	LogAwardRecordService service;
	@Autowired
	UserService userService;
	@Autowired
	RfAwardBatchService awardBatchService;
	@Autowired
	RfAwardPoolService awardPoolService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<LogAwardRecord> list(HttpServletRequest request, HttpServletResponse response, Model model,
			LogAwardRecord bean, String begin, String end,String userPhone,String awardGrade,String awardType,
			String awardName,String awardCode, Integer externalUserId) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		
		List<Integer> awardIds = null;
		if(StringUtils.isNotBlank(awardGrade) || StringUtils.isNotBlank(awardType) || StringUtils.isNotBlank(awardName)){
			String grade = null;
			if(StringUtils.isNotBlank(awardGrade)){
				grade = awardGrade;
			}
			String type = null;
			if(StringUtils.isNotBlank(awardType)){
				type = awardType;
			}
			String name = null;
			if(StringUtils.isNotBlank(awardName)){
				name = awardName;
			}
			awardIds = new ArrayList<>();
			List<RfAwardBatch> awards = awardBatchService.initQuery().andEqualTo("awardGrade", grade)
					.andEqualTo("awardType", type)
					.andEqualTo("awardName", name).findAll();
			
			if(Collections3.isEmpty(awards)){
				awardIds.add(-1);
			} else {
				for (RfAwardBatch awardBatch : awards) {
					awardIds.add(awardBatch.getAwardId());
				}
			}
		}
		
		List<Integer> poolIds = null;
		if(StringUtils.isNotBlank(awardCode)){
			poolIds = new ArrayList<>();
			List<RfAwardPool> awardPools = awardPoolService.initQuery().andEqualTo("awardCode", awardCode).findAll();
			if(Collections3.isEmpty(awardPools)){
				poolIds.add(-1);
			} else {
				for (RfAwardPool awardPool : awardPools) {
					poolIds.add(awardPool.getId());
				}
			}
		}
		
		List<Integer> userIds = null;
 		if(StringUtils.isNotBlank(userPhone) || externalUserId != null){
 			userIds = new ArrayList<>();
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userPhone).andEqualTo("externalUserId", externalUserId).findAll();
			if(Collections3.isEmpty(userList)){
				userIds.add(-2);
			} else {
				for (RfUser rfUser : userList) {
					userIds.add(rfUser.getUserId());
				}
			}
		}
		
		List<LogAwardRecord> list = service.initQuery(bean)
				.andEqualTo("counpCode", bean.getCounpCode())
				.andEqualTo("activationCode", bean.getActivationCode())
				.andIn("userId", userIds)
				.andIn("awardId", awardIds)
				.andIn("poolId", poolIds)
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.addOrderClause("createTime", "desc")
				.pageAll(bean.getPage(), bean.getRows());
		PageInfo<LogAwardRecord> pageInfo = new PageInfo<LogAwardRecord>(list);
		for (LogAwardRecord record : list) {
			RfUser user = userService.get(record.getUserId());
			if(null != user){
				record.getMap().put("userPhone", user.getUserMobilePhone());
				/** 查询客户端ID*/
				record.getMap().put("externalUserId", user.getExternalUserId());
			}
			
			RfAwardBatch award = awardBatchService.get(record.getAwardId());
			if(null != award){
				record.getMap().put("awardName", award.getAwardName());
				record.getMap().put("awardGrade", award.getAwardGrade());
				record.getMap().put("awardCategory", award.getAwardCategory());
				record.getMap().put("awardType", award.getAwardType());
			}
			
			if(null != record.getPoolId()){
				RfAwardPool awardPool = awardPoolService.get(record.getPoolId());
				if(null != awardPool){
					record.getMap().put("awardCode", awardPool.getAwardCode());
				}
			}
			
		}
		
		
		
		return pageInfo;

	}
}








