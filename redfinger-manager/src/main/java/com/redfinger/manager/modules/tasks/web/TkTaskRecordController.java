package com.redfinger.manager.modules.tasks.web;

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
import com.redfinger.manager.common.constants.TaskType;
import com.redfinger.manager.common.domain.RfSurvey;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.TkTask;
import com.redfinger.manager.common.domain.TkTaskRecord;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.survey.service.RfSurveyService;
import com.redfinger.manager.modules.tasks.service.TkTaskRecordService;
import com.redfinger.manager.modules.tasks.service.TkTaskService;

@Controller
@RequestMapping(value = "/tktask/taskrecord")
public class TkTaskRecordController extends BaseController {
	@Autowired
	private TkTaskRecordService service;
	@Autowired
	private UserService userService;
	@Autowired
	private TkTaskService taskService;
	@Autowired
	private RfSurveyService surveyService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TkTaskRecord> list(HttpServletRequest request, HttpServletResponse response, Model model,
			TkTaskRecord bean, String userMobilePhone, String taskName, String taskType, Integer externalUserId) throws Exception {
		RfUser user = null;
		if (StringUtils.isNotBlank(userMobilePhone) || externalUserId != null) {
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).andEqualTo("externalUserId", externalUserId).findAll();
			user = Collections3.isEmpty(userList) ? null : userList.get(0);
			if (user == null) {
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		List<Integer> taskIds = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(taskType) || StringUtils.isNotBlank(taskName)) {
			List<TkTask> taskList = taskService.initQuery().andEqualTo("taskType", taskType)
					.andLike("taskName", taskName).findAll();
			if (!Collections3.isEmpty(taskList)) {
				for (TkTask TkTask : taskList) {
					taskIds.add(TkTask.getTaskId());
				}
			}else{
				taskIds.add(-1);
			}
		}

		List<TkTaskRecord> recordList = service.initQuery(bean)
				.andEqualTo("userId", user != null ? user.getUserId() : null).andIn("taskId", taskIds)
				.andEqualTo("inviteCode", bean.getInviteCode())
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("createTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());

		Map<Integer, TkTask> taskMap = new HashMap<Integer, TkTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		taskIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (TkTaskRecord record : recordList) {
			taskIds.add(record.getTaskId());
			userIds.add(record.getUserId());
		}
		if (taskIds.size() > 0) {
			List<TkTask> taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
			for (TkTask t : taskList) {
				taskMap.put(t.getTaskId(), t);
			}
		}

		if (userIds.size() > 0) {
			List<RfUser> userList = userService.initQuery().andIn("userId", userIds).findAll();
			for (RfUser u : userList) {
				userMap.put(u.getUserId(), u);
			}
		}

		TkTask task = null;
		for (TkTaskRecord record : recordList) {
			task = taskMap.get(record.getTaskId());
			user = userMap.get(record.getUserId());

			if (record.getSurveyId() != null) {
				RfSurvey survey = surveyService.get(task.getSurveyId());
				if (survey != null) {
					task.getMap().put("surveyName", survey.getSurveyName());
				}
			}

			if (user != null) {
				record.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			if (task != null) {
				record.getMap().put("taskType", task.getTaskType());
				record.getMap().put("taskName", task.getTaskName());
				record.getMap().put("taskCode", task.getTaskCode());
				record.getMap().put("gameName", task.getGameName());
			}
			
			/** 查询客户端ID*/
			if(record.getUserId() != null){
				/** 根据用户Id查询用户*/
				RfUser rfUser = userService.load(record.getUserId());
				/** 查询客户端ID*/
				record.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
		}
		PageInfo<TkTaskRecord> pageInfo = new PageInfo<TkTaskRecord>(recordList);
		return pageInfo;
	}

	/**
	 * 查询导出数据的个数
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param userMobilePhone
	 * @param taskClassify
	 * @param taskType
	 * @return
	 * @throws Exception
	 */
	public int listSize(HttpServletRequest request, HttpServletResponse response, Model model, TkTaskRecord bean,
			String userMobilePhone, String taskType) throws Exception {
		RfUser user = null;
		if (StringUtils.isNotBlank(userMobilePhone)) {
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).findAll();
			user = Collections3.isEmpty(userList) ? null : userList.get(0);
			if (user == null) {
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		List<Integer> taskIds = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(taskType)) {
			List<TkTask> taskList = taskService.initQuery().andEqualTo("taskType", taskType).findAll();
			for (TkTask TkTask : taskList) {
				taskIds.add(TkTask.getTaskId());
			}
			if (taskIds.size() == 0)
				taskIds.add(-1);
		}

		return service.selectCountByParams(bean, user, taskIds);

	}

	// 会员导出
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, TkTaskRecord bean,
			String userMobilePhone, String taskName, String taskType, String exportHead, String exportField,
			String exportName,Integer externalUserId) throws Exception {
		RfUser user = null;
		if (StringUtils.isNotBlank(userMobilePhone)) {
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).findAll();
			user = Collections3.isEmpty(userList) ? null : userList.get(0);
			if (user == null) {
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		List<Integer> taskIds = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(taskType)) {
			List<TkTask> taskList = taskService.initQuery().andEqualTo("taskType", taskType).findAll();
			for (TkTask TkTask : taskList) {
				taskIds.add(TkTask.getTaskId());
			}
			if (taskIds.size() == 0)
				taskIds.add(-1);
		}

		int size = this.listSize(request, response, model, bean, userMobilePhone, taskType);

		if (size > 50000) {// 导出数据不能超过50000
			throw new BusinessException("导出数据不能超过50000条");
		}

		List<TkTaskRecord> recordList = service.initQuery(bean)
				.andEqualTo("userId", user != null ? user.getUserId() : null).andIn("taskId", taskIds)
				.andEqualTo("inviteCode", bean.getInviteCode())
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("createTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());

		Map<Integer, TkTask> taskMap = new HashMap<Integer, TkTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		taskIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (TkTaskRecord record : recordList) {
			taskIds.add(record.getTaskId());
			userIds.add(record.getUserId());
		}
		if (taskIds.size() > 0) {
			List<TkTask> taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
			for (TkTask t : taskList) {
				taskMap.put(t.getTaskId(), t);
			}
		}

		if (userIds.size() > 0) {
			List<RfUser> userList = userService.initQuery().andIn("userId", userIds).findAll();
			for (RfUser u : userList) {
				userMap.put(u.getUserId(), u);
			}
		}
		TkTask task = null;

		exportHead = StringUtils.removeEnd(exportHead, ",");
		exportField = StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition",
				"attachment;filename=" + ExcelUtils.getFileName(request, exportName) + ".xls");
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
			PageInfo<TkTaskRecord> pageInfo = this.list(request, response, model, bean, userMobilePhone, taskName,
					taskType, externalUserId);
			List<TkTaskRecord> list = pageInfo.getList();
			if (!Collections3.isEmpty(list)) {

				for (TkTaskRecord record : list) {

					if (record.getSurveyId() != null) {
						RfSurvey survey = surveyService.get(task.getSurveyId());
						if (survey != null) {
							task.getMap().put("surveyName", survey.getSurveyName());
						}
					}

					task = taskMap.get(record.getTaskId());
					user = userMap.get(record.getUserId());

					if (user != null) {
						record.getMap().put("userMobilePhone", user.getUserMobilePhone());
					}
					if (task != null) {
						record.getMap().put("taskType", TaskType.DICT_MAP.get(task.getTaskType()));
						record.getMap().put("taskName", task.getTaskName());
						record.getMap().put("taskCode", task.getTaskCode());
					}
				}

				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list), sheet, bodyStyle, exportField.split(","),
						pageInfo.getStartRow());
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
