package com.redfinger.manager.modules.stat.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfProblem;
import com.redfinger.manager.common.domain.RfSurvey;
import com.redfinger.manager.common.domain.RfSurveyLog;
import com.redfinger.manager.common.domain.RfSurveyProbem;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.service.StatRfSurveyLogService;
import com.redfinger.manager.modules.survey.service.RfProblemAnswerService;
import com.redfinger.manager.modules.survey.service.RfProblemService;
import com.redfinger.manager.modules.survey.service.RfSurveyProbemService;
import com.redfinger.manager.modules.survey.service.RfSurveyService;
@Controller
@RequestMapping(value = "/stat/survey")
public class StatUserSurveyController extends BaseController {
	@Autowired
	RfSurveyService surveyService;
	@Autowired
	RfSurveyProbemService surveyProbemService;
	@Autowired
	RfProblemService problemService;
	@Autowired
	RfProblemAnswerService problemAnswerService;
	@Autowired
	StatRfSurveyLogService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	//获取问卷填充下拉
	@ResponseBody
	@RequestMapping(value = "getSurvey")
	public List<RfSurvey> getSurvey(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return surveyService.initQuery().findStopTrue();

	}
	//获取问题填充下拉
	@ResponseBody
	@RequestMapping(value = "getProblem")
	public List<RfProblem> getProblem(HttpServletRequest request, HttpServletResponse response, Model model,Integer surveyId) throws Exception {
		//获取所有的非问答题
		List<RfSurveyProbem> sp_list = surveyProbemService.initQuery().andEqualTo("surveyId", surveyId).findStopTrue();
		List<RfProblem>  problemList = new ArrayList<RfProblem>();
		for(RfSurveyProbem sp : sp_list){
			Integer p_id = sp.getProblemId();
			RfProblem p =problemService.initQuery().andEqualTo("problemId", p_id).andNotEqualTo("problemType", "-1").singleStopTrue().get(0);
			problemList.add(p);
		}
		return problemList;
	}

	@ResponseBody
	@RequestMapping(value = "getChart")
	public Map<String,Object> getChart (HttpServletRequest request, HttpServletResponse response, Model model,RfSurveyLog bean) {
		return service.statSurveyLog2ProblemAnswer(bean);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, String exportDatas, String exportHead, String exportField, String exportName) throws Exception {
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName) + ".xls");
		Map<String, Object> map = JsonUtils.stringToObject(exportDatas, Map.class);
		List<String> lableList = (List<String>) map.get("label");
		List<Integer> numberList = (List<Integer>) map.get("number");
		List<StatDomain> list = Lists.newArrayList();
		for (int i = 0; i < lableList.size(); i++) {
			StatDomain domain = new StatDomain();
			domain.setNumber(numberList.get(i));
			domain.setLabel(lableList.get(i));
			list.add(domain);
		}
		String json = JsonUtils.ObjectToString(list);
		ExcelUtils.exportExcel(json, exportHead.split(","), exportField.split(","), outputStream);
		return null;
	}
	
}
