package com.redfinger.manager.modules.survey.web;

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
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.survey.base.ProblemPO;
import com.redfinger.manager.modules.survey.service.RfProblemAnswerService;
import com.redfinger.manager.modules.survey.service.RfProblemService;

@Controller
@RequestMapping(value = "/survey/problem")
public class SurveyProblemController extends BaseController {
	@Autowired
	RfProblemService service;
	@Autowired
	RfProblemAnswerService rfProblemAnswerService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfProblem> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfProblem bean,String begin, String end)
			throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		List<RfProblem> list =service.initQuery(bean)
				.andEqualTo("isMust", bean.getIsMust())
				.andLike("problemContent", bean.getProblemContent())
				.andLike("problemType", bean.getProblemType())
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.addOrderClause("reorder", "asc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());	
		for(RfProblem problem :list){
			problem.getMap().put("answer", problem.getProblemId() == null ? "--" : rfProblemAnswerService.initQuery()
					.andEqualTo("problemId", problem.getProblemId()).findStopTrue());
		}
		PageInfo<RfProblem> pageInfo = new PageInfo<RfProblem>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request,
			HttpServletResponse response, Model model, ProblemPO bean)
			throws Exception {

		if (bean.getProblemId() == null) {

		} else {
			RfProblem proBean =  service.get(bean.getProblemId());
			ProblemPO poBean = new ProblemPO(proBean);
			poBean.setRfProblem(proBean);
			poBean.setList_rfProblemAnswer(rfProblemAnswerService.initQuery().andEqualTo("problemId", poBean.getProblemId()).addOrderClause("answerId", "asc").findStopTrue());
			model.addAttribute("bean", poBean);

		}
		return this.toPage(request, response, model);
	}
	//增加答案
	@RequestMapping(value = "addAnswer")
	public void addAnswer(HttpServletRequest request, HttpServletResponse response,
			Model model, ProblemPO bean) throws Exception {
		 List<RfProblemAnswer>  list_answer =bean.getList_rfProblemAnswer();
		 for(RfProblemAnswer rf : list_answer){
			 if(rf.getAnswerContent()==null||rf.getAnswerContent()==""||rf.getAnswerContent()==" "){
				 continue;
			 }
			 rf.setProblemId(bean.getProblemId());
				rfProblemAnswerService.save(null, rf);
		 }
	}
	//删除答案
	@RequestMapping(value = "delAnswer")
	public void delAnswer(HttpServletRequest request, HttpServletResponse response,
			Model model, ProblemPO bean,Integer answerId) throws Exception {
		if(answerId!=null){
			rfProblemAnswerService.remove(null, answerId);
		}
		
	}

	//更新
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response, Model model, ProblemPO bean)
			throws Exception {
		service.update(request, bean);
		List<RfProblemAnswer> list_answer = bean.getList_rfProblemAnswer();
		if(list_answer==null){
			return;
		}
		for(RfProblemAnswer answer : list_answer){
			if(answer.getAnswerContent()==null||answer.getAnswerContent()==""||answer.getAnswerContent()==" "){
				continue;
			}
			if(answer.getAnswerId()==null){
				answer.setProblemId(bean.getProblemId());
				rfProblemAnswerService.save(null, answer);
			}
			rfProblemAnswerService.update(null, answer);
		}	
	}
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Model model, ProblemPO bean) throws Exception {
		service.save(request, bean);
	}
	//删除问题未物理删除他所有答案
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request,
			HttpServletResponse response, Model model, ProblemPO bean)
			throws Exception {
		service.delete(request, bean);
	}
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, ProblemPO bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, ProblemPO bean) throws Exception {
		service.stop(request, bean);
	}

}
