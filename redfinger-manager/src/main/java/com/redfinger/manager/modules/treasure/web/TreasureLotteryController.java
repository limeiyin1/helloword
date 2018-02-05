package com.redfinger.manager.modules.treasure.web;

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
import com.redfinger.manager.common.domain.RfTreasure;
import com.redfinger.manager.common.domain.RfTreasureLottery;
import com.redfinger.manager.common.domain.RfTreasurePeriod;
import com.redfinger.manager.modules.treasure.service.TreasureLotteryService;
import com.redfinger.manager.modules.treasure.service.TreasurePeriodService;
import com.redfinger.manager.modules.treasure.service.TreasureService;

@Controller
@RequestMapping(value="/treasure/lotteryManage")
public class TreasureLotteryController extends BaseController{
	@Autowired
	private TreasurePeriodService periodService;
	@Autowired
	private TreasureService treasureService;
	@Autowired
	private TreasureLotteryService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		List<RfTreasurePeriod> list = periodService.initQuery().findAll();
		model.addAttribute("list", list);
		
		List<RfTreasure> sureList = treasureService.initQuery().findAll();
		model.addAttribute("sureList", sureList);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfTreasureLottery> list(HttpServletRequest request,
			HttpServletResponse response, Model model, RfTreasureLottery bean)
			throws Exception {
		List<RfTreasureLottery> list = service.initQuery(bean)
				.andEqualTo("periodId", bean.getPeriodId())
				.andEqualTo("treasureId", bean.getTreasureId())
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null != list && list.size() > 0){
			for (RfTreasureLottery lottery : list) {
				if(null != lottery.getPeriodId()){
					lottery.getMap().put("periodName", periodService.get(lottery.getPeriodId()).getTreasureName());
				}
				if(null != lottery.getLotteryId()){
					lottery.getMap().put("treasureName", treasureService.get(lottery.getTreasureId()).getTreasureName());
				}
			}
		}
		PageInfo<RfTreasureLottery> pageInfo = new PageInfo<RfTreasureLottery>(list);
		return pageInfo;
	}
}
