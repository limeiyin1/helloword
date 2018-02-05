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
import com.redfinger.manager.common.constants.TaskClassify;
import com.redfinger.manager.common.constants.TaskType;
import com.redfinger.manager.common.domain.RfSurvey;
import com.redfinger.manager.common.domain.RfTask;
import com.redfinger.manager.common.domain.RfTaskRecord;
import com.redfinger.manager.common.domain.RfTaskRecordExample;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.survey.service.RfSurveyService;
import com.redfinger.manager.modules.tasks.service.RfTaskRecordService;
import com.redfinger.manager.modules.tasks.service.RfTaskService;

@Controller
@RequestMapping(value = "/rftask/taskrecord")
public class RfTaskRecordController extends BaseController {
	@Autowired
	private RfTaskRecordService service;
	@Autowired 
	private UserService userService;
	@Autowired
	private RfTaskService taskService;
	@Autowired
	private RfSurveyService surveyService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfTaskRecord> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfTaskRecord bean, String userMobilePhone, String taskClassify, String taskType) throws Exception {
		RfUser user = null;
		if(StringUtils.isNotBlank(userMobilePhone)){
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).findAll();
			user = Collections3.isEmpty(userList) ? null : userList.get(0);
			if(user == null) {
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		List<Integer> taskIds = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(taskClassify) || StringUtils.isNotBlank(taskType)){
			List<RfTask> taskList = taskService.initQuery().andEqualTo("taskClassify", taskClassify).andEqualTo("taskType", taskType).findAll();
			for (RfTask rfTask : taskList) {
				taskIds.add(rfTask.getTaskId());
			}
			if(taskIds.size() == 0) taskIds.add(-1);
		}
		
		List<RfTaskRecord> recordList = service.initQuery(bean)
				.andEqualTo("userId", user!=null?user.getUserId():null)
				.andIn("taskId", taskIds)
				.andEqualTo("inviteCode", bean.getInviteCode())
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("createTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		Map<Integer, RfTask> taskMap = new HashMap<Integer, RfTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		taskIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (RfTaskRecord record : recordList) {
			taskIds.add(record.getTaskId());
			userIds.add(record.getUserId());
		}
		if(taskIds.size() > 0){
			List<RfTask> taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
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
		
		RfTask task = null;
		for (RfTaskRecord record : recordList) {
			task = taskMap.get(record.getTaskId());
			user = userMap.get(record.getUserId());
			
			if(record.getSurveyId() != null){
				RfSurvey survey = surveyService.get(task.getSurveyId());
				if(survey != null){
					task.getMap().put("surveyName", survey.getSurveyName());
				}
			}
			
			
			if(user != null){
				record.getMap().put("userMobilePhone", user.getUserMobilePhone());
			}
			if(task != null){
				record.getMap().put("taskClassify", task.getTaskClassify());
				record.getMap().put("taskType", task.getTaskType());
				record.getMap().put("taskName", task.getTaskName());
				record.getMap().put("taskCode", task.getTaskCode());
			}
		}
		PageInfo<RfTaskRecord> pageInfo = new PageInfo<RfTaskRecord>(recordList);
		return pageInfo;
	}
	
	/**
	 * 查询导出数据的个数
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
	public int listSize(HttpServletRequest request, HttpServletResponse response, Model model,
			RfTaskRecord bean, String userMobilePhone, String taskClassify, String taskType) throws Exception {
		RfUser user = null;
		if(StringUtils.isNotBlank(userMobilePhone)){
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).findAll();
			user = Collections3.isEmpty(userList) ? null : userList.get(0);
			if(user == null) {
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		List<Integer> taskIds = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(taskClassify) || StringUtils.isNotBlank(taskType)){
			List<RfTask> taskList = taskService.initQuery().andEqualTo("taskClassify", taskClassify).andEqualTo("taskType", taskType).findAll();
			for (RfTask rfTask : taskList) {
				taskIds.add(rfTask.getTaskId());
			}
			if(taskIds.size() == 0) taskIds.add(-1);
		}
		
		return service.selectCountByParams(bean, user, taskIds);
		
	}
	
	//会员导出
	@RequestMapping(value="export")
    public String export(HttpServletRequest request,HttpServletResponse response,Model model,
    		RfTaskRecord bean, String userMobilePhone, String taskClassify, String taskType,
    		String exportHead, String exportField, String exportName)throws Exception{
		RfUser user = null;
		if(StringUtils.isNotBlank(userMobilePhone)){
			List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).findAll();
			user = Collections3.isEmpty(userList) ? null : userList.get(0);
			if(user == null) {
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		List<Integer> taskIds = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(taskClassify) || StringUtils.isNotBlank(taskType)){
			List<RfTask> taskList = taskService.initQuery().andEqualTo("taskClassify", taskClassify).andEqualTo("taskType", taskType).findAll();
			for (RfTask rfTask : taskList) {
				taskIds.add(rfTask.getTaskId());
			}
			if(taskIds.size() == 0) taskIds.add(-1);
		}
		
		int size = this.listSize(request, response, model, bean, userMobilePhone, taskClassify, taskType);
		
		if(size > 50000){//导出数据不能超过50000
			throw new BusinessException("导出数据不能超过50000条");
		}
		
		List<RfTaskRecord> recordList = service.initQuery(bean)
				.andEqualTo("userId", user!=null?user.getUserId():null)
				.andIn("taskId", taskIds)
				.andEqualTo("inviteCode", bean.getInviteCode())
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("createTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		Map<Integer, RfTask> taskMap = new HashMap<Integer, RfTask>();
		Map<Integer, RfUser> userMap = new HashMap<Integer, RfUser>();
		taskIds = new ArrayList<Integer>();
		List<Integer> userIds = new ArrayList<Integer>();
		for (RfTaskRecord record : recordList) {
			taskIds.add(record.getTaskId());
			userIds.add(record.getUserId());
		}
		if(taskIds.size() > 0){
			List<RfTask> taskList = taskService.initQuery().andIn("taskId", taskIds).findAll();
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
		RfTask task = null;
		
		exportHead=StringUtils.removeEnd(exportHead, ",");
	    exportField=StringUtils.removeEnd(exportField, ",");
	    response.setContentType("application/binary;charset=UTF-8");
	    ServletOutputStream outputStream=response.getOutputStream();
	    response.setHeader("Content-disposition", "attachment;filename="+ExcelUtils.getFileName(request, exportName)+".xls");
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
			PageInfo<RfTaskRecord> pageInfo = this.list(request, response, model, bean, userMobilePhone, taskClassify, taskType);
			List<RfTaskRecord> list = pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				
				
				for (RfTaskRecord record : list) {
					
					if(record.getSurveyId() != null){
						RfSurvey survey = surveyService.get(task.getSurveyId());
						if(survey != null){
							task.getMap().put("surveyName", survey.getSurveyName());
						}
					}
					
					task = taskMap.get(record.getTaskId());
					user = userMap.get(record.getUserId());
					
					if(user != null){
						record.getMap().put("userMobilePhone", user.getUserMobilePhone());
					}
					if(task != null){
						record.getMap().put("taskClassify", TaskClassify.DICT_MAP.get(task.getTaskClassify()));
						record.getMap().put("taskType", TaskType.DICT_MAP.get(task.getTaskType()));
						record.getMap().put("taskName", task.getTaskName());
						record.getMap().put("taskCode", task.getTaskCode());
					}
				}
				
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list), sheet, bodyStyle, exportField.split(","), pageInfo.getStartRow());
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
