package com.redfinger.manager.modules.survey.web;

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
import com.redfinger.manager.common.domain.RfProblem;
import com.redfinger.manager.common.domain.RfProblemAnswer;
import com.redfinger.manager.common.domain.RfSurvey;
import com.redfinger.manager.common.domain.RfSurveyProbem;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.survey.base.ProblemPO;
import com.redfinger.manager.modules.survey.base.SurveyPO;
import com.redfinger.manager.modules.survey.service.RfProblemAnswerService;
import com.redfinger.manager.modules.survey.service.RfProblemService;
import com.redfinger.manager.modules.survey.service.RfSurveyProbemService;
import com.redfinger.manager.modules.survey.service.RfSurveyService;

@Controller
@RequestMapping(value = "/survey/survey")
public class SurveyController extends BaseController {

	@Autowired
	RfSurveyService service;
	@Autowired
	RfProblemService rfProblemService;
	@Autowired
	RfProblemAnswerService rfProblemAnswerService;
	@Autowired
	RfSurveyProbemService surveyProbemService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfSurvey> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfSurvey bean,String begin, String end)
			throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		List<RfSurvey> list = service.initQuery(bean)
				.andLike("surveyName", bean.getSurveyName())
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfSurvey> pageInfo = new PageInfo<RfSurvey>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, SurveyPO bean)
			throws Exception {
		if (bean.getSurveyId() == null) {

		} else {
			RfSurvey rfSurvey =  service.get(bean.getSurveyId());
			List<RfSurveyProbem> list = surveyProbemService.initQuery().andEqualTo("surveyId", rfSurvey.getSurveyId()).findStopTrue(); //获取所有问卷问题
			List<ProblemPO> list_problemVO = new ArrayList<ProblemPO>();
			bean = new SurveyPO(rfSurvey);
			bean.setList_problemVO(list_problemVO);
			List<Integer> pId_list = new ArrayList<Integer>();
			for(RfSurveyProbem rfSurveyProbem :list){
				pId_list.add(rfSurveyProbem.getProblemId());
				RfProblem rfProblem = rfProblemService.get(rfSurveyProbem.getProblemId());
				ProblemPO problemPO =new ProblemPO(rfProblem);
				List<RfProblemAnswer> rfProblemAnswer_list= rfProblemAnswerService.initQuery().andEqualTo("problemId", rfProblem.getProblemId()).addOrderClause("answerId", "asc").findStopTrue();
				problemPO.setList_rfProblemAnswer(rfProblemAnswer_list);
				list_problemVO.add(problemPO);
			}
			bean.setListSize(list_problemVO.size());
			model.addAttribute("bean", bean);
			//获取所有的问题
			List<RfProblem> radio= rfProblemService.initQuery().andEqualTo("problemType", "1").andNotIn("problemId", pId_list).addOrderClause("problemId", "asc").findStopTrue();
			List<RfProblem> check= rfProblemService.initQuery().andEqualTo("problemType", "2").andNotIn("problemId", pId_list).addOrderClause("problemId", "asc").findStopTrue();
			List<RfProblem> blank= rfProblemService.initQuery().andEqualTo("problemType", "-1").andNotIn("problemId", pId_list).addOrderClause("problemId", "asc").findStopTrue();
			model.addAttribute("radio", radio);
			model.addAttribute("check", check);
			model.addAttribute("blank", blank);
		}
		return this.toPage(request, response, model);
	}
	
	//删除问题
	@RequestMapping(value = "delProblem")
	public void delProblem(HttpServletRequest request, HttpServletResponse response, Model model, SurveyPO bean,Integer problemId,Integer surveyId) throws Exception {
		System.out.println(problemId+":"+surveyId);
		List<RfSurveyProbem> list = surveyProbemService.initQuery().andEqualTo("problemId", problemId).andEqualTo("surveyId",surveyId).findStopTrue();
		if(list.size()==0){
			return;
		}
		for(RfSurveyProbem sp :list){
			surveyProbemService.remove(null, sp.getId());
		}
		
	}
	//添加问题
	@RequestMapping(value = "addProblem")
	public void addProblem(HttpServletRequest request, HttpServletResponse response, Model model, SurveyPO bean,Integer problemId,Integer surveyId) throws Exception {
		List<RfSurveyProbem> list = surveyProbemService.initQuery().andEqualTo("problemId", problemId).andEqualTo("surveyId", surveyId).findStopTrue();
		if(list.size()>0){
			throw new BusinessException("问题已经存在！");
		}
		RfSurveyProbem spBean = new RfSurveyProbem();
		spBean.setSurveyId(surveyId);
		spBean.setProblemId(problemId);
		surveyProbemService.save(null, spBean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, SurveyPO bean)
			throws Exception {
				service.update(request, bean);	
	}
	
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, SurveyPO bean) throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, SurveyPO bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, SurveyPO bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, SurveyPO bean) throws Exception {
		service.delete(request, bean);
		List<RfSurveyProbem> list = surveyProbemService.initQuery().andEqualTo("surveyId", bean.getSurveyId()).findStopTrue();
		for(RfSurveyProbem sp :list){
			surveyProbemService.remove(null, sp.getId());
		}
		
	}
}
