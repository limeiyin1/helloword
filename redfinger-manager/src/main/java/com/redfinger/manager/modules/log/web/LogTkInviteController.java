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
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.TkTask;
import com.redfinger.manager.common.domain.TkTaskRecord;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.tasks.service.TkTaskRecordService;
import com.redfinger.manager.modules.tasks.service.TkTaskService;

@Controller
@RequestMapping(value = "/log/tkinvite")
public class LogTkInviteController extends BaseController {

	@Autowired
	private TkTaskRecordService service;
	@Autowired
	private TkTaskService taskService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TkTaskRecord> list(HttpServletRequest request, HttpServletResponse response, Model model, TkTaskRecord bean, 
			String userMobilePhone, String inviteeMobilePhone, Integer externalUserId, Integer inviteeExternalUserId) throws Exception {
		RfUser user = new RfUser(), inviteeUser = new RfUser();
		if (StringUtils.isNotBlank(userMobilePhone) || externalUserId != null) {
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).andEqualTo("externalUserId", externalUserId).findAll();
			
			if (userList.size() > 0) {
				user = userList.get(0);
			} else {
				user.setUserId(-1);
			}
		}
		if (StringUtils.isNotBlank(inviteeMobilePhone) || inviteeExternalUserId != null) {
			List<RfUser> inviteeUserList = userService.initQuery().andEqualTo("userMobilePhone", inviteeMobilePhone).andEqualTo("externalUserId", inviteeExternalUserId).findAll();
			
			if (inviteeUserList.size() > 0) {
				inviteeUser = inviteeUserList.get(0);
			} else {
				inviteeUser.setUserId(-1);
			}
		}
		
		List<TkTask> taskList = taskService.initQuery().andEqualTo("taskCode", "InviteBuy").andEqualTo("status", YesOrNoStatus.YES).findDelTrue();
		TkTask task = taskList.size() > 0 ? taskList.get(0) : null;
		if(task == null) {
			task = new TkTask();
			task.setTaskId(-1);
		}
		
		List<TkTaskRecord> recordList = service.initQuery(bean)
				.andEqualTo("taskId", task.getTaskId())
				.andEqualTo("userId", user.getUserId())
				.andEqualTo("recordStatus", bean.getRecordStatus())
				.andEqualTo("inviteeUserId", inviteeUser.getUserId())
				.andGreaterThanOrEqualTo("createTime", bean.getBeginTimeStr()!=null?DateUtils.parseDate(bean.getBeginTimeStr()):null)
				.andLessThanOrEqualTo("createTime", bean.getEndTimeStr()!=null?DateUtils.parseDate(bean.getEndTimeStr()):null)
				.andEqualTo("inviteCode", bean.getInviteCode())
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		
		Map<Integer, TkTask> taskMap = new HashMap<Integer, TkTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		Map<Integer, RfUser> inviteeUserMap = new HashMap<Integer, RfUser>();
		List<Integer> taskIds = new ArrayList<Integer>();
		List<Integer> inviteeUserIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (TkTaskRecord record : recordList) {
			taskIds.add(record.getTaskId());
			
			inviteeUserIds.add(record.getInviteeUserId());
			userIds.add(record.getUserId());
		}
		if(taskIds.size() > 0){
			taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
			for (TkTask t : taskList) {
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
		
		for (TkTaskRecord record : recordList) {
			task = taskMap.get(record.getTaskId());
			user = userMap.get(record.getUserId());
			inviteeUser = inviteeUserMap.get(record.getInviteeUserId());
			if(inviteeUser != null){
				record.getMap().put("inviteeMobilePhone", inviteeUser != null ? inviteeUser.getUserMobilePhone() : "");
				record.getMap().put("inviteeExternalUserId", inviteeUser != null ? inviteeUser.getExternalUserId() : "");
			}
			if(user != null){
				record.getMap().put("userMobilePhone", user != null ?user.getUserMobilePhone() : "");
				record.getMap().put("externalUserId", user != null ? user.getExternalUserId() : "");
			}
			if(task != null){
				record.getMap().put("taskName", task.getTaskName());
				record.getMap().put("taskEndTime", task.getTaskEndTime());
			}
		}
		PageInfo<TkTaskRecord> pageInfo = new PageInfo<TkTaskRecord>(recordList);
		return pageInfo;
	}
	
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request,
			HttpServletResponse response, Model model, String exportDatas,
			String exportHead, String exportField, String exportName,
			TkTaskRecord bean,String userMobilePhone,String inviteeMobilePhone, Integer externalUserId,Integer inviteeExternalUserId) throws Exception {
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
		int page = 1;
		while (keep) {
			bean.setPage(page);
			bean.setRows(1000);
			PageInfo<TkTaskRecord> pageInfo = this.list(request, response, model, bean, userMobilePhone, inviteeMobilePhone, externalUserId, inviteeExternalUserId);
			List<TkTaskRecord> list=pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list),sheet,bodyStyle,exportField.split(","),pageInfo.getStartRow());
			
				if (pageInfo.isHasNextPage()) {
					page++;
					continue;
				}
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
