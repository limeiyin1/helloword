package com.redfinger.manager.modules.award.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.RfAwardBatch;
import com.redfinger.manager.common.domain.RfAwardPool;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.award.pogo.POAwardPool;
import com.redfinger.manager.modules.award.service.RfAwardBatchService;
import com.redfinger.manager.modules.award.service.RfAwardPoolService;

@Controller
@RequestMapping(value = "/award/poolManage")
public class RfAwardPoolController extends BaseController {
	@Autowired
	RfAwardPoolService service;
	@Autowired
	RfAwardBatchService awardBatchService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfAwardPool> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfAwardPool bean, String begin, String end, String awardGrade,String awardType) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		
		List<Integer> awardIds = new ArrayList<>();
		List<RfAwardBatch> awards = null;
		if (StringUtils.isNotBlank(awardType) || StringUtils.isNotBlank(awardGrade)) {
			awards = awardBatchService.initQuery().andEqualTo("awardType", awardType).andEqualTo("awardGrade", awardGrade).findStopTrue();
		}
		
		if(!Collections3.isEmpty(awards)){
			for (RfAwardBatch award : awards) {
				if(!awardIds.contains(award.getAwardId())){
					awardIds.add(award.getAwardId());
				}
			}
		}else if (StringUtils.isNotBlank(awardType) || StringUtils.isNotBlank(awardGrade)) {
			awardIds.add(-1);
		}
		
		if(StringUtils.isNotBlank(bean.getAwardCode())){
			bean.setAwardCode(bean.getAwardCode());
		} else {
			bean.setAwardCode(null);
		}
		
		service.initQuery(bean).andEqualTo("giveStatus", bean.getGiveStatus()).andEqualTo("awardCode", bean.getAwardCode())
				.andEqualTo("receiveStatus", bean.getReceiveStatus()).andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime).addOrderClause("createTime", "desc");
		if(!Collections3.isEmpty(awardIds)){
			service.andIn("awardId", awardIds);
		}
		List<RfAwardPool> list = service.addOrderForce(bean.getSort(), bean.getOrder()).pageDelTrue(bean.getPage(), bean.getRows());
		
		for (RfAwardPool pool : list) {
			RfAwardBatch award = awardBatchService.get(pool.getAwardId());
			pool.getMap().put("awardName",award == null ? "" : award.getAwardName());
			pool.getMap().put("awardGrade",award == null ? "" : award.getAwardGrade());
			pool.getMap().put("awardType",award == null ? "" : award.getAwardType());
		}
		PageInfo<RfAwardPool> pageInfo = new PageInfo<RfAwardPool>(list);

		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardPool bean)
			throws Exception {
		if (bean.getId() == null) {

		} else {
			bean = service.get(bean.getId());
			bean.getMap().put(
					"awardName",
					awardBatchService.get(bean.getAwardId()) == null ? "" : awardBatchService.get(bean.getAwardId())
							.getAwardName());
			bean.getMap().put(
					"awardGrade",
					awardBatchService.get(bean.getAwardId()) == null ? "" : awardBatchService.get(bean.getAwardId())
							.getAwardGrade());
			model.addAttribute("bean", bean);

		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "addBatch")
	public String addBatch(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 查询所有余量大于0的奖品(安慰奖除外)
		List<RfAwardBatch> list = awardBatchService.initQuery().andGreaterThanOrEqualTo("awardMargin", 0)
				.andGreaterThan("awardTotal", 0).andLessThan("awardTotal", 100000).andGreaterThan("awardMargin", 0)
				.andNotEqualTo("awardGrade", "0").findStopTrue();
		model.addAttribute("list", list);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "saveBatch")
	public void saveBatch(HttpServletRequest request, HttpServletResponse response, Model model, POAwardPool bean)
			throws Exception {
		if (null == bean.getAwardId()) {
			throw new BusinessException("请选择要注入的奖品");
		}
		
		RfAwardBatch award = awardBatchService.get(bean.getAwardId());
		if (award == null || award.getAwardMargin() < bean.getAwardNumber()) {
			throw new BusinessException("添加的奖品数量不能超过奖品余量！");
		}
		
		service.saveBatchAwardPool(request, bean, award);
	}



	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardPool bean)
			throws Exception {
		service.save(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardPool bean)
			throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardPool bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardPool bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfAwardPool bean)
			throws Exception {
		service.remove(request, bean);
	}

}
