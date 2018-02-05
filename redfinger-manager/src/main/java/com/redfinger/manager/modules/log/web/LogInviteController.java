package com.redfinger.manager.modules.log.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfTask;
import com.redfinger.manager.common.domain.RfTaskRecord;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.tasks.service.RfTaskRecordService;
import com.redfinger.manager.modules.tasks.service.RfTaskService;

@Controller
@RequestMapping(value = "/log/invite")
public class LogInviteController extends BaseController {

	@Autowired
	RfTaskRecordService service;
	@Autowired
	RfTaskService taskService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfTaskRecord> list(HttpServletRequest request, HttpServletResponse response, Model model, RfTaskRecord bean, 
			String userMobilePhone, String inviteeMobilePhone) throws Exception {
		RfUser user = new RfUser(), inviteeUser = new RfUser();
		if (StringUtils.isNotBlank(userMobilePhone)) {
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).findAll();
			
			if (userList.size() > 0) {
				user = userList.get(0);
			} else {
				user.setUserId(-1);
			}
		}
		if (StringUtils.isNotBlank(inviteeMobilePhone)) {
			List<RfUser> inviteeUserList = userService.initQuery().andEqualTo("userMobilePhone", inviteeMobilePhone).findAll();
			
			if (inviteeUserList.size() > 0) {
				inviteeUser = inviteeUserList.get(0);
			} else {
				inviteeUser.setUserId(-1);
			}
		}
		
		List<RfTask> taskList = taskService.initQuery().andEqualTo("taskCode", "InviteBuy").andEqualTo("status", YesOrNoStatus.YES).singleAll();
		RfTask task = taskList.size() > 0 ? taskList.get(0) : null;
		if(task == null) {
			task = new RfTask();
			task.setTaskId(-1);
		}
		
		List<RfTaskRecord> recordList = service.initQuery(bean)
				.andEqualTo("taskId", task.getTaskId())
				.andEqualTo("userId", user.getUserId())
				.andEqualTo("inviteeUserId", inviteeUser.getUserId())
				.andGreaterThanOrEqualTo("createTime", bean.getBeginTimeStr()!=null?DateUtils.parseDate(bean.getBeginTimeStr()):null)
				.andLessThanOrEqualTo("createTime", bean.getEndTimeStr()!=null?DateUtils.parseDate(bean.getEndTimeStr()):null)
				.andEqualTo("inviteCode", bean.getInviteCode())
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		Map<Integer, RfTask> taskMap = new HashMap<Integer, RfTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		Map<Integer, RfUser> inviteeUserMap = new HashMap<Integer, RfUser>();
		List<Integer> taskIds = new ArrayList<Integer>();
		List<Integer> inviteeUserIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (RfTaskRecord record : recordList) {
			taskIds.add(record.getTaskId());
			inviteeUserIds.add(record.getInviteeUserId());
			userIds.add(record.getUserId());
		}
		if(taskIds.size() > 0){
			taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
			for (RfTask t : taskList) {
				taskMap.put(t.getTaskId(), t);
			}
		}
		
		if(userIds.size() > 0){
			List<RfUser> userList = userService.initQuery().andIn("userId", userIds).findAll();
			for (RfUser u : userList) {
				userMap.put(u.getUserId(), u);
			}
		}
		if(inviteeUserIds.size() > 0){
			List<RfUser> userList = userService.initQuery().andIn("userId", inviteeUserIds).findAll();
			for (RfUser u : userList) {
				inviteeUserMap.put(u.getUserId(), u);
			}
		}
		
		for (RfTaskRecord record : recordList) {
			task = taskMap.get(record.getTaskId());
			user = userMap.get(record.getUserId());
			inviteeUser = inviteeUserMap.get(record.getInviteeUserId());
			if(inviteeUser != null){
				record.getMap().put("inviteeMobilePhone", inviteeUser.getUserMobilePhone());
			}
			if(user != null){
				record.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			if(task != null){
				record.getMap().put("taskName", task.getTaskName());
				record.getMap().put("taskEndTime", task.getTaskEndTime());
			}
		}
		PageInfo<RfTaskRecord> pageInfo = new PageInfo<RfTaskRecord>(recordList);
		return pageInfo;
	}
	
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request,
			HttpServletResponse response, Model model, String exportDatas,
			String exportHead, String exportField, String exportName,
			RfTaskRecord bean,String userMobilePhone,String inviteeMobilePhone) throws Exception {
		exportHead = StringUtils.removeEnd(exportHead, ",");
		exportField = StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment;filename="
				+ ExcelUtils.getFileName(request, exportName) + ".xls");
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		boolean keep = true;
		while (keep) {
			PageInfo<RfTaskRecord> pageInfo = this.list(request, response, model, bean, userMobilePhone, inviteeMobilePhone);
			List<RfTaskRecord> list=pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list),sheet,bodyStyle,exportField.split(","),pageInfo.getStartRow());
			}
			keep = false;
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
