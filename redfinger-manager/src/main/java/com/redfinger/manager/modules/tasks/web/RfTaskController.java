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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.HsSdk;
import com.redfinger.manager.common.constants.TaskClassify;
import com.redfinger.manager.common.constants.TaskType;
import com.redfinger.manager.common.domain.AppApk;
import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfSurvey;
import com.redfinger.manager.common.domain.RfTask;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.http.HttpClient;
import com.redfinger.manager.common.http.HttpEncryptUtils;
import com.redfinger.manager.common.utils.AwardType;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.NumberUtils;
import com.redfinger.manager.common.utils.PadTypeUtils;
import com.redfinger.manager.modules.activation.dto.ActivationCodeTypeDto;
import com.redfinger.manager.modules.activation.service.ActivationCodeTypeService;
import com.redfinger.manager.modules.market.service.MarketAppApkService;
import com.redfinger.manager.modules.survey.service.RfSurveyService;
import com.redfinger.manager.modules.tasks.service.RfTaskService;

@Controller
@RequestMapping(value = "/rftask/task")
public class RfTaskController extends BaseController {
	@Autowired
	RfTaskService service;
	@Autowired
	MarketAppApkService appApkService;
	@Autowired
	private RfSurveyService surveyService;
	@Autowired
	private ActivationCodeTypeService codeTypeService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfTask> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfTask bean) throws Exception {
		List<RfTask> list = service
				.initQuery(bean)
				.andLike("taskName", bean.getTaskName())
				.andEqualTo("taskClassify", bean.getTaskClassify())
				.andEqualTo("taskType", bean.getTaskType())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andGreaterThanOrEqualTo("taskStartTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThanOrEqualTo("taskEndTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc").addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		for (RfTask task : list) {
			if(task.getSurveyId() != null){
				RfSurvey survey = surveyService.get(task.getSurveyId());
				if(survey != null){
					task.getMap().put("surveyName", survey.getSurveyName());
				}
			}
			if(StringUtils.isNotBlank(task.getAwardType())){
				if(AwardType.ACTIVATION_CODE.equals(task.getAwardType()) && StringUtils.isNotBlank(task.getTaskAwardJson())){
					JSONObject json = JSONObject.fromObject(task.getTaskAwardJson());
					if(json.containsKey("activationCodeTypeId")){
						Integer typeId = json.getInt("activationCodeTypeId");
						RfActivationCodeType codeType = codeTypeService.get(typeId);
						task.getMap().put("activationCodeTypeName", codeType.getActivationTypeName());
					}
				}
			}
		}
		PageInfo<RfTask> pageInfo = new PageInfo<RfTask>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfTask bean)
			throws Exception {
		// 查询所有的问卷调查
		List<RfSurvey> surveyList = surveyService.initQuery().findStopTrue();
		model.addAttribute("surveyList", surveyList);
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("clientid", HsSdk.HS_SDK_CLIENTID);
		paramMap.put("appid", HsSdk.HS_SDK_APPID);
		paramMap.put("hot", "1");	//排行
		paramMap.put("offset", "999");	//分页，默认999
		String hs_url = HsSdk.HS_SDK_URL + "/api/public/v1/game/list?"+HttpEncryptUtils.createLinkString(paramMap);
		log.info("hs sdk url={}",hs_url);
		
		try {
			//从火速获取游戏列表
			HttpClient httpClient = new HttpClient(hs_url, 6000, 6000);
			int responseCode = httpClient.send(null, "UTF-8");
			List<AppApk> appApkList = new ArrayList<AppApk>();
			if(responseCode == 200){
				AppApk appApk = null;
				JSONObject json = JSONObject.fromObject(httpClient.getResult());
				if(json.getInt("code") == 200){
					JSONObject dataJson = json.getJSONObject("data");
					JSONArray gamesJson = dataJson.getJSONArray("game_list");
					int size = gamesJson.size();
					for (int i = 0; i < size; i++) {
						json = gamesJson.getJSONObject(i);
						appApk = new AppApk();
						appApk.setId(json.getInt("gameid"));
						appApk.setName(json.getString("gamename"));
						appApkList.add(appApk);
					}	
				}
			}
//			List<AppApk> appApkList = appApkService.initQuery().findStopTrue();
			model.addAttribute("appApkList", appApkList);
		} catch (Exception e) {
			log.error("get sdk game list error:"+e.getMessage(), e);
		}
		
		bean = service.get(bean.getTaskId());
		model.addAttribute("task", bean);
		if(bean != null && AwardType.ACTIVATION_CODE.equals(bean.getAwardType())){//如果是激活码
			if(StringUtils.isNotBlank(bean.getTaskAwardJson())){
				JSONObject json = JSONObject.fromObject(bean.getTaskAwardJson());
				if(json.containsKey("activationCodeTypeId")){
					if(StringUtils.isNotBlank(json.get("activationCodeTypeId").toString())){
						model.addAttribute("activationCodeTypeId", Integer.parseInt(json.get("activationCodeTypeId").toString()));
					}
				}
			}
		}
		
		List<ActivationCodeTypeDto> dtos = new ArrayList<ActivationCodeTypeDto>();
		List<RfActivationCodeType> codeTypes = codeTypeService.initQuery().findStopTrue();
		if(null != codeTypes && codeTypes.size()>0){
			for (RfActivationCodeType codeType : codeTypes) {
				ActivationCodeTypeDto dto = new ActivationCodeTypeDto();
				dto.setTypeId(codeType.getTypeId());
				dto.setCodeTypeName(codeType.getActivationTypeName()+"["+PadTypeUtils.DICT_MAP.get(codeType.getPadType())+"]"+"["+NumberUtils.dividedTime(codeType.getPadTime())+"]");
				dtos.add(dto);
			}
		}
		model.addAttribute("codeTypes", dtos);
		
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfTask bean,
			Integer gameId, Integer surveyId,String awardType,Integer typeId,String condition) throws Exception {
		bean.setCondition(condition);
		bean.setTaskStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setTaskEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		if(TaskClassify.RBC.equals(bean.getTaskClassify())) bean.setScoreAward(null);
		else if(TaskClassify.SCORE.equals(bean.getTaskClassify())) bean.setRbcAward(null);
		if(TaskType.GAME_DOWNLOAD.equals(bean.getTaskType())) bean.setSurveyId(null);
		else if(TaskType.WJDC.equals(bean.getTaskType())) bean.setGameId(null);
		
		if(StringUtils.isNotBlank(bean.getTaskCode())){
			int count = service.selectCountByTaskCode(bean, null);
			if(count > 0){
				throw new BusinessException("任务编码不能重复");
			}
		}
		
		if(TaskClassify.RBC.equals(bean.getTaskClassify())){//如果是红豆任务
			if(AwardType.RBC.equals(awardType)){//红豆奖励
				bean.setAwardType(AwardType.RBC);
				bean.setTaskAwardJson(null);
			}else if(AwardType.ACTIVATION_CODE.equals(awardType)){//激活码奖励
				bean.setAwardType(AwardType.ACTIVATION_CODE);
				String str = "{activationCodeTypeId:"+typeId+"}";
				bean.setTaskAwardJson(str);
				bean.setRbcAward(0);
			}
			bean.setScoreAward(0);
		}else {
			bean.setAwardType(AwardType.SCORE);
			bean.setTaskAwardJson(null);
			bean.setRbcAward(0);
		}
		service.save(request, bean);
	}
	
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfTask bean,
			String awardType,Integer typeId,String condition)
			throws Exception {
		bean.setCondition(condition);
		bean.setTaskStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setTaskEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		if(TaskClassify.RBC.equals(bean.getTaskClassify())) bean.setScoreAward(null);
		else if(TaskClassify.SCORE.equals(bean.getTaskClassify())) bean.setRbcAward(null);
		if(TaskType.GAME_DOWNLOAD.equals(bean.getTaskType())) bean.setSurveyId(null);
		else if(TaskType.WJDC.equals(bean.getTaskType())) bean.setGameId(null);
		
		if(StringUtils.isNotBlank(bean.getTaskCode())){
			int count = service.selectCountByTaskCode(bean, bean.getTaskId());
			if(count > 0){
				throw new BusinessException("任务编码不能重复");
			}
		}
		if(TaskClassify.RBC.equals(bean.getTaskClassify())){//如果是红豆任务
			if(AwardType.RBC.equals(awardType)){//红豆奖励
				bean.setAwardType(AwardType.RBC);
				bean.setTaskAwardJson(null);
			}else if(AwardType.ACTIVATION_CODE.equals(awardType)){//激活码奖励
				bean.setAwardType(AwardType.ACTIVATION_CODE);
				String str = "{activationCodeTypeId:"+typeId+"}";
				bean.setTaskAwardJson(str);
				bean.setRbcAward(0);
			}
			bean.setScoreAward(0);
		}else {
			bean.setAwardType(AwardType.SCORE);
			bean.setTaskAwardJson(null);
			bean.setRbcAward(0);
		}
		service.update(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfTask bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfTask bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfTask bean)
			throws Exception {
		service.delete(request, bean);
	}
	
	@ResponseBody
	@RequestMapping(value = "taskCount")
	public Map<String, Object> taskCount(HttpServletRequest request, HttpServletResponse response, RfTask bean)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		bean.setTaskStartTime(DateUtils.parseDate(bean.getBeginTimeStr()));
		bean.setTaskEndTime(DateUtils.parseDate(bean.getEndTimeStr()));
		if(TaskType.GAME_DOWNLOAD.equals(bean.getTaskType())) bean.setSurveyId(null);
		else if(TaskType.WJDC.equals(bean.getTaskType())) bean.setGameId(null);
		
		List<RfTask> taskList = service.initQuery().andNotEqualTo("taskId", bean.getTaskId())
								.andEqualTo("taskClassify", bean.getTaskClassify())
								.andEqualTo("taskType", bean.getTaskType())
								.andEqualTo("taskCode", bean.getTaskCode())
								.andEqualTo("gameId", bean.getGameId())
								.andEqualTo("surveyId", bean.getSurveyId())
								.andEqualTo("condition", bean.getCondition())
								.andEqualTo("conditionValue", bean.getConditionValue())
								.findStopTrue();
		int count = 0;
		long startTime = bean.getTaskStartTime().getTime(), endTime = bean.getTaskEndTime().getTime();
		for (RfTask task : taskList) {
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
