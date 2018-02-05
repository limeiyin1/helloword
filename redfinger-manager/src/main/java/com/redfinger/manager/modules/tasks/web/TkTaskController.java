package com.redfinger.manager.modules.tasks.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.base.HsSdk;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.LabelCode;
import com.redfinger.manager.common.constants.LabelType;
import com.redfinger.manager.common.constants.TaskType;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfCouponType;
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.domain.RfSurvey;
import com.redfinger.manager.common.domain.SysDict;
import com.redfinger.manager.common.domain.TkTask;
import com.redfinger.manager.common.domain.TkTaskAward;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DataInitProcessor;
import com.redfinger.manager.common.http.HttpClient;
import com.redfinger.manager.common.utils.AwardType;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.NumberUtils;
import com.redfinger.manager.common.utils.PadTypeUtils;
import com.redfinger.manager.modules.activation.dto.ActivationCodeTypeDto;
import com.redfinger.manager.modules.activation.service.ActivationCodeTypeService;
import com.redfinger.manager.modules.coupon.service.CouponTypeService;
import com.redfinger.manager.modules.label.service.LabelService;
import com.redfinger.manager.modules.market.service.MarketAppApkService;
import com.redfinger.manager.modules.survey.service.RfSurveyService;
import com.redfinger.manager.modules.tasks.service.TkTaskAwardService;
import com.redfinger.manager.modules.tasks.service.TkTaskService;
import com.redfinger.manager.modules.tasks.web.dto.ViewTaskAward;

@Controller
@RequestMapping(value = "/tktask/task")
public class TkTaskController extends BaseController {
	@Autowired
	TkTaskService service;
	@Autowired
	TkTaskAwardService taskAwardService;
	@Autowired
	MarketAppApkService appApkService;
	@Autowired
	private RfSurveyService surveyService;
	@Autowired
	private ActivationCodeTypeService codeTypeService;
	@Autowired
	private CouponTypeService couponTypeService;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	private LabelService labelService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfLabel> labelList = labelService.initQuery().andEqualTo("labelType", LabelType.USER_LABEL).andEqualTo("labelCode", LabelCode.USER_TASK).findDelTrue();
		model.addAttribute("labelList", labelList);
		
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TkTask> list(HttpServletRequest request, HttpServletResponse response, Model model,
			TkTask bean) throws Exception {
		List<TkTask> list = service.initQuery(bean)
				.andEqualTo("parentTaskId", -1)
				.andLike("taskName", bean.getTaskName())
				.andEqualTo("taskType", bean.getTaskType())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andEqualTo("labelId", bean.getLabelId())
				.andGreaterThanOrEqualTo("taskStartTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("taskEndTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		List<Integer> taskIds = new ArrayList<Integer>();
		Map<Integer, List<TkTaskAward>> taskAwardMap = new HashMap<Integer, List<TkTaskAward>>();
		for (TkTask task : list) {
			if(task.getSurveyId() != null){
				RfSurvey survey = surveyService.get(task.getSurveyId());
				if(survey != null){
					task.getMap().put("surveyName", survey.getSurveyName());
				}
			}
			taskIds.add(task.getTaskId());
		}
		if(taskIds.size() > 0){
			List<TkTaskAward> taskAwardList = taskAwardService.initQuery().andIn("taskId", taskIds).findStopTrue();
			List<TkTaskAward> awards = null;
			for (TkTaskAward taskAward : taskAwardList) {
				awards = taskAwardMap.get(taskAward.getTaskId());
				if(awards == null){
					awards = new ArrayList<TkTaskAward>();
				}
				awards.add(taskAward);
				taskAwardMap.put(taskAward.getTaskId(), awards);
			}
		}
		for (TkTask task : list) {
			int subTaskCount = service.initQuery().andEqualTo("parentTaskId", task.getTaskId()).countStopTrue();
			task.setSubTaskCount(subTaskCount);
			
			if(task.getLabelId() != null){
				RfLabel label = labelService.get(task.getLabelId());
				if(label != null){
					task.getMap().put("labelName", label.getLabelName());
				}
			}
			
			List<TkTaskAward> taskAwardList = taskAwardMap.get(task.getTaskId());
			if(taskAwardList == null) continue;
			
			for (TkTaskAward taskAward : taskAwardList) {
				if(AwardType.ACTIVATION_CODE.equals(taskAward.getAwardType())){
					RfActivationCodeType codeType = codeTypeService.get(taskAward.getForeignKey());
					if(codeType != null){
						task.getMap().put("activationCodeTypeName", codeType.getActivationTypeName());
					}
				}else if(AwardType.COUPON.equals(taskAward.getAwardType())){
					RfCouponType couponType = couponTypeService.get(taskAward.getForeignKey());
					if(couponType != null){
						task.getMap().put("couponTypeName", couponType.getTypeName());
					}
				}else if(AwardType.RBC.equals(taskAward.getAwardType())){
					task.getMap().put("rbcAward", taskAward.getAwardNum());
				}else if(AwardType.SCORE.equals(taskAward.getAwardType())){
					task.getMap().put("scoreAward", taskAward.getAwardNum());
				}
			}
		}
		PageInfo<TkTask> pageInfo = new PageInfo<TkTask>(list);
		return pageInfo;
	}
	
	//任务列表-子任务
	@ResponseBody
	@RequestMapping(value = "subTaskList")
	public List<TkTask> subTaskList(HttpServletRequest request, HttpServletResponse response, Model model,TkTask bean) throws Exception {
		Integer parentTaskId = bean.getParentTaskId();
		if(parentTaskId == null) parentTaskId = -9999;
		
		List<TkTask> list = service.initQuery(bean)
				.andEqualTo("parentTaskId", parentTaskId)
				.addOrderClause("reorder", "asc")
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.findDelTrue();
		
		List<Integer> taskIds = new ArrayList<Integer>();
		Map<Integer, List<TkTaskAward>> taskAwardMap = new HashMap<Integer, List<TkTaskAward>>();
		for (TkTask task : list) {
			taskIds.add(task.getTaskId());
		}
		if(taskIds.size() > 0){
			List<TkTaskAward> taskAwardList = taskAwardService.initQuery().andIn("taskId", taskIds).findStopTrue();
			List<TkTaskAward> awards = null;
			for (TkTaskAward taskAward : taskAwardList) {
				awards = taskAwardMap.get(taskAward.getTaskId());
				if(awards == null){
					awards = new ArrayList<TkTaskAward>();
				}
				awards.add(taskAward);
				taskAwardMap.put(taskAward.getTaskId(), awards);
			}
		}
		for (TkTask task : list) {
			List<TkTaskAward> taskAwardList = taskAwardMap.get(task.getTaskId());
			if(taskAwardList == null) continue;
			
			for (TkTaskAward taskAward : taskAwardList) {
				if(AwardType.ACTIVATION_CODE.equals(taskAward.getAwardType())){
					RfActivationCodeType codeType = codeTypeService.get(taskAward.getForeignKey());
					if(codeType != null){
						task.getMap().put("activationCodeTypeName", codeType.getActivationTypeName());
					}
				}else if(AwardType.COUPON.equals(taskAward.getAwardType())){
					RfCouponType couponType = couponTypeService.get(taskAward.getForeignKey());
					if(couponType != null){
						task.getMap().put("couponTypeName", couponType.getTypeName());
					}
				}else if(AwardType.RBC.equals(taskAward.getAwardType())){
					task.getMap().put("rbcAward", taskAward.getAwardNum());
				}else if(AwardType.SCORE.equals(taskAward.getAwardType())){
					task.getMap().put("scoreAward", taskAward.getAwardNum());
				}
			}
		}
		return list;
	}
	
	//编辑任务-子任务列表
	@ResponseBody
	@RequestMapping(value = "subTaskListEdit")
	public PageInfo<TkTask> subTaskListEdit(HttpServletRequest request, HttpServletResponse response, Model model,TkTask bean) throws Exception {
		Integer parentTaskId = bean.getParentTaskId();
		if(parentTaskId == null) parentTaskId = -9999;
		
		List<TkTask> list = service.initQuery(bean)
				.andEqualTo("parentTaskId", parentTaskId)
				.addOrderClause("reorder", "asc")
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		List<Integer> taskIds = new ArrayList<Integer>();
		Map<Integer, List<TkTaskAward>> taskAwardMap = new HashMap<Integer, List<TkTaskAward>>();
		for (TkTask task : list) {
			taskIds.add(task.getTaskId());
		}
		if(taskIds.size() > 0){
			List<TkTaskAward> taskAwardList = taskAwardService.initQuery().andIn("taskId", taskIds).findStopTrue();
			List<TkTaskAward> awards = null;
			for (TkTaskAward taskAward : taskAwardList) {
				awards = taskAwardMap.get(taskAward.getTaskId());
				if(awards == null){
					awards = new ArrayList<TkTaskAward>();
				}
				awards.add(taskAward);
				taskAwardMap.put(taskAward.getTaskId(), awards);
			}
		}
		for (TkTask task : list) {
			task.setSubTaskId(task.getTaskId());
			List<TkTaskAward> taskAwardList = taskAwardMap.get(task.getTaskId());
			if(taskAwardList == null) continue;
			
			for (TkTaskAward taskAward : taskAwardList) {
				if(AwardType.ACTIVATION_CODE.equals(taskAward.getAwardType())){
					RfActivationCodeType codeType = codeTypeService.get(taskAward.getForeignKey());
					if(codeType != null){
						task.getMap().put("activationCodeTypeName", codeType.getActivationTypeName());
					}
				}else if(AwardType.COUPON.equals(taskAward.getAwardType())){
					RfCouponType couponType = couponTypeService.get(taskAward.getForeignKey());
					if(couponType != null){
						task.getMap().put("couponTypeName", couponType.getTypeName());
					}
				}else if(AwardType.RBC.equals(taskAward.getAwardType())){
					task.getMap().put("rbcAward", taskAward.getAwardNum());
				}else if(AwardType.SCORE.equals(taskAward.getAwardType())){
					task.getMap().put("scoreAward", taskAward.getAwardNum());
				}
			}
		}
		PageInfo<TkTask> pageInfo = new PageInfo<TkTask>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, TkTask bean)
			throws Exception {
		// 查询所有的问卷调查
		List<RfSurvey> surveyList = surveyService.initQuery().findStopTrue();
		model.addAttribute("surveyList", surveyList);
		
		String hs_url = HsSdk.HS_SDK_URL + "/api/public/v1/game/list";
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("clientid", HsSdk.HS_SDK_CLIENTID);
		paramMap.put("appid", HsSdk.HS_SDK_APPID);
		paramMap.put("hot", "1");	//排行
		paramMap.put("offset", "999");	//分页，默认999
		
		try {
			//从火速获取游戏列表
			HttpClient httpClient = new HttpClient(hs_url, 6000, 6000);
			int responseCode = httpClient.send(paramMap, "UTF-8");
			List<Map<String, Object>> apkList = new ArrayList<Map<String, Object>>();
			if(responseCode == 200){
				Map<String, Object> apkMap = null, gameMap = new HashMap<String, Object>();
				JSONObject json = JSONObject.fromObject(httpClient.getResult());
				if(json.getInt("code") == 200){
					JSONObject dataJson = json.getJSONObject("data");
					JSONArray gamesJson = dataJson.getJSONArray("game_list");
					int size = gamesJson.size();
					for (int i = 0; i < size; i++) {
						json = gamesJson.getJSONObject(i);
						apkMap = new HashMap<String, Object>();
						apkMap.put("id", json.getInt("gameid"));
						apkMap.put("name", json.getString("gamename"));
						apkMap.put("icon", json.getString("icon"));
						apkMap.put("oneword", json.getString("oneword"));
						apkList.add(apkMap);
						
						gameMap.put("game"+json.getInt("gameid"), apkMap);
					}
				}
				model.addAttribute("gameJson", JSONObject.fromObject(gameMap).toString());
			}
			model.addAttribute("apkList", apkList);
		} catch (Exception e) {
			log.error("get sdk game list error:"+e.getMessage(), e);
		}
		
		TkTask task = service.get(bean.getTaskId());
		model.addAttribute("task", task);
		
		if(task != null){
			List<TkTaskAward> taskAwardList = taskAwardService.initQuery().andEqualTo("taskId", task.getTaskId()).findStopTrue();
			for (TkTaskAward taskAward : taskAwardList) {
				if(ConstantsDb.taskAwardActivationCode().equals(taskAward.getAwardType())){
					model.addAttribute("activationCodeAward", taskAward);
				}else if(ConstantsDb.taskAwardRbc().equals(taskAward.getAwardType())){
					model.addAttribute("rbcAward", taskAward);
				}else if(ConstantsDb.taskAwardScore().equals(taskAward.getAwardType())){
					model.addAttribute("scoreAward", taskAward);
				}else if(ConstantsDb.taskAwardCoupon().equals(taskAward.getAwardType())){
					model.addAttribute("couponAward", taskAward);
				}
			}
		}
		
		List<ActivationCodeTypeDto> activationCodeDtos = new ArrayList<ActivationCodeTypeDto>();
		List<RfActivationCodeType> codeTypes = codeTypeService.initQuery().findStopTrue();
		if(null != codeTypes && codeTypes.size()>0){
			for (RfActivationCodeType codeType : codeTypes) {
				ActivationCodeTypeDto dto = new ActivationCodeTypeDto();
				dto.setTypeId(codeType.getTypeId());
				dto.setCodeTypeName(codeType.getActivationTypeName()+"["+PadTypeUtils.DICT_MAP.get(codeType.getPadType())+"]"+"["+NumberUtils.dividedTime(codeType.getPadTime())+"]");
				activationCodeDtos.add(dto);
			}
		}
		model.addAttribute("activationCodeTypes", activationCodeDtos);
		
		List<RfCouponType> couponTypes = couponTypeService.initQuery().findStopTrue();
		model.addAttribute("couponTypes", couponTypes);
		
		List<SysDict> taskCodeList= DataInitProcessor.dictCategoryMap.get("task.task_code");
		model.addAttribute("taskCodeList", taskCodeList);
		
		List<RfLabel> labelList = labelService.initQuery().andEqualTo("labelType", LabelType.USER_LABEL).andEqualTo("labelCode", LabelCode.USER_TASK).findDelTrue();
		model.addAttribute("labelList", labelList);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "subform")
	public String subform(HttpServletRequest request, HttpServletResponse response, Model model, TkTask bean) throws Exception {
		TkTask parentTask = null;
		if(bean.getParentTaskId() != null){	//新增时parentTaskId不为空
			parentTask = service.get(bean.getParentTaskId());
			model.addAttribute("parentTask", parentTask);
		}
		
		TkTask subTask = service.get(bean.getTaskId());
		model.addAttribute("subTask", subTask);
		
		if(subTask != null){
			if(parentTask == null){
				parentTask = service.get(subTask.getParentTaskId());
				model.addAttribute("parentTask", parentTask);
			}
			
			List<TkTaskAward> taskAwardList = taskAwardService.initQuery().andEqualTo("taskId", subTask.getTaskId()).findStopTrue();
			for (TkTaskAward taskAward : taskAwardList) {
				if(ConstantsDb.taskAwardActivationCode().equals(taskAward.getAwardType())){
					model.addAttribute("activationCodeAward", taskAward);
				}else if(ConstantsDb.taskAwardRbc().equals(taskAward.getAwardType())){
					model.addAttribute("rbcAward", taskAward);
				}else if(ConstantsDb.taskAwardScore().equals(taskAward.getAwardType())){
					model.addAttribute("scoreAward", taskAward);
				}else if(ConstantsDb.taskAwardCoupon().equals(taskAward.getAwardType())){
					model.addAttribute("couponAward", taskAward);
				}
			}
		}
		
		List<ActivationCodeTypeDto> activationCodeDtos = new ArrayList<ActivationCodeTypeDto>();
		List<RfActivationCodeType> codeTypes = codeTypeService.initQuery().findStopTrue();
		if(null != codeTypes && codeTypes.size()>0){
			for (RfActivationCodeType codeType : codeTypes) {
				ActivationCodeTypeDto dto = new ActivationCodeTypeDto();
				dto.setTypeId(codeType.getTypeId());
				dto.setCodeTypeName(codeType.getActivationTypeName()+"["+PadTypeUtils.DICT_MAP.get(codeType.getPadType())+"]"+"["+NumberUtils.dividedTime(codeType.getPadTime())+"]");
				activationCodeDtos.add(dto);
			}
		}
		model.addAttribute("activationCodeTypes", activationCodeDtos);
		
		List<RfCouponType> couponTypes = couponTypeService.initQuery().findStopTrue();
		model.addAttribute("couponTypes", couponTypes);
		
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "save")
	public Map<String, Object> save(HttpServletRequest request, HttpServletResponse response, TkTask bean,@ModelAttribute ViewTaskAward viewTaskAward,MultipartHttpServletRequest fileRequest) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		bean.setTaskStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setTaskEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		if(TaskType.GAME_DOWNLOAD.equals(bean.getTaskType())) bean.setSurveyId(null);
		else if(TaskType.WJDC.equals(bean.getTaskType())) bean.setGameId(null);
		
		if(StringUtils.isNotBlank(bean.getTaskCode())){
			int count = service.selectCountByTaskCode(bean, bean.getTaskId());
			if(count > 0){
				throw new BusinessException("任务编码不能重复");
			}
		}
		
		MultipartFile file = fileRequest.getFileMap().get("gameIconFile");
		if(file != null && !file.isEmpty()){
			Map<String, String> uploadMap = fileUtils.saveImageFile(file, filePathUtils.getImageUrl()+"/activity", filePathUtils.getImageLinkUrl()+"/activity");
			String gameIcon = uploadMap.get(FileUtils.FILE_PATH_KEY);
			bean.setGameIcon(gameIcon);
		}
		
		service.saveTask(request, bean, viewTaskAward);
		model.put("code", 200);
		model.put("taskId", bean.getTaskId());
		return model;
	}
	
	@RequestMapping(value = "saveSub")
	public void saveSub(HttpServletRequest request, HttpServletResponse response, TkTask bean,@ModelAttribute ViewTaskAward viewTaskAward) throws Exception {
		//bean.setTaskEndTime(null!=bean.getEndTimeStr()?DateUtils.parseDate(bean.getEndTimeStr()):DateUtils.parseDate(""));
		if(StringUtils.isEmpty(bean.getEndTimeStr())){
			bean.setTaskEndTimeSetNull(1);
		}
		bean.setTaskEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		service.saveTask(request, bean, viewTaskAward);
	}
	
	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, TkTask bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, TkTask bean)
			throws Exception {
		String[] ids = bean.getIds().split(",");
		for (String taskId : ids) {
			TkTask task = service.get(Integer.valueOf(taskId));
			if(StringUtils.isNotBlank(task.getTaskCode())){
				int count = service.selectCountByTaskCode(task, task.getTaskId());
				if(count > 0){
					throw new BusinessException("已启用相同编码的任务，不能重复");
				}
			}
		}
		service.start(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, TkTask bean)
			throws Exception {
		service.delete(request, bean);
	}
	
	@ResponseBody
	@RequestMapping(value = "taskCount")
	public Map<String, Object> taskCount(HttpServletRequest request, HttpServletResponse response, TkTask bean)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		bean.setTaskStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setTaskEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		if(TaskType.GAME_DOWNLOAD.equals(bean.getTaskType())) bean.setSurveyId(null);
		else if(TaskType.WJDC.equals(bean.getTaskType())) bean.setGameId(null);
		
		List<TkTask> taskList = service.initQuery()
								.andEqualTo("taskType", bean.getTaskType())
								.andEqualTo("taskCode", bean.getTaskCode())
								.andEqualTo("gameId", bean.getGameId())
								.andEqualTo("surveyId", bean.getSurveyId())
								.findStopTrue();
		int count = 0;
		long startTime = bean.getTaskStartTime().getTime(), endTime = bean.getTaskEndTime().getTime();
		for (TkTask task : taskList) {
			if((startTime >= task.getTaskStartTime().getTime() && startTime <= task.getTaskEndTime().getTime())
				|| (endTime >= task.getTaskStartTime().getTime() && endTime <= task.getTaskEndTime().getTime())){
				count++;
			}
		}
		model.put("code", 200);
		model.put("taskCount", count);
		return model;
	}
}
