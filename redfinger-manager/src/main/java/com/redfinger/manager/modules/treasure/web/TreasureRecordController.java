package com.redfinger.manager.modules.treasure.web;

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
import com.redfinger.manager.common.domain.RfTreasureNumber;
import com.redfinger.manager.common.domain.RfTreasurePeriod;
import com.redfinger.manager.common.domain.RfTreasureRecord;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.treasure.service.TreasureNumberService;
import com.redfinger.manager.modules.treasure.service.TreasurePeriodService;
import com.redfinger.manager.modules.treasure.service.TreasureRecordService;
import com.redfinger.manager.modules.treasure.service.TreasureService;

@Controller
@RequestMapping(value="/treasure/recordManage")
public class TreasureRecordController extends BaseController{
	
	@Autowired
	private TreasureRecordService service;
	@Autowired
	private TreasureNumberService numberService;
	@Autowired
	private TreasurePeriodService periodService;
	@Autowired
	private TreasureService treasureService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		List<RfTreasurePeriod> list = periodService.initQuery().findAll();
		model.addAttribute("list", list);
		
		model.addAttribute("yesOrNos", YesOrNoStatus.DICT_MAP);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfTreasureRecord> list(HttpServletRequest request, HttpServletResponse response, Model model,
			RfTreasureRecord bean) throws Exception {
		List<RfTreasureRecord> list = service.initQuery(bean)
				.andEqualTo("periodId", bean.getPeriodId())
				.andEqualTo("isWinning", bean.getIsWinning())
				.andEqualTo("isAward", bean.getIsAward())
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		
		for (RfTreasureRecord record : list) {
			int recordNumberCount = numberService.initQuery().andEqualTo("recordId", record.getRecordId()).countStopTrue();
			record.setRecordNumberCount(recordNumberCount);
			
			if(null != record.getTreasureId()){
				record.getMap().put("treasureName", treasureService.get(record.getTreasureId()).getTreasureName());
			}
			
			if(null != record.getPeriodId()){
				RfTreasurePeriod period = periodService.get(record.getPeriodId());
				record.getMap().put("currentPeriod", period.getCurrentPeriod());
				record.getMap().put("periodName", period.getTreasureName());
				record.getMap().put("periodStatus", period.getPeriodStatus());
			}
			
			if(null != record.getUserId()){
				record.getMap().put("userMobilePhone", userService.get(record.getUserId()).getUserMobilePhone());
			}
			
			if(StringUtils.isNotBlank(record.getIsWinning())){
				record.getMap().put("isWinning", YesOrNoStatus.DICT_MAP.get(record.getIsWinning()));
			}
		}
		PageInfo<RfTreasureRecord> pageInfo = new PageInfo<RfTreasureRecord>(list);
		return pageInfo;
	}
	
	
	//夺宝交易--子夺宝
	@ResponseBody
	@RequestMapping(value = "subNumberList")
	public List<RfTreasureNumber> subNumberList(HttpServletRequest request, HttpServletResponse response, Model model,RfTreasureNumber bean) throws Exception {
		Integer recordId = bean.getRecordId();
		if(recordId == null) recordId = -9999;
		
		List<RfTreasureNumber> list = numberService.initQuery(bean)
				.andEqualTo("recordId", recordId)
				.addOrderClause("reorder", "asc")
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.findDelTrue();
		
		for (RfTreasureNumber number : list) {
			if(StringUtils.isNotBlank(number.getIsWinning())){
				number.getMap().put("isWinning", YesOrNoStatus.DICT_MAP.get(number.getIsWinning()));
			}
		}
		return list;
	}
}
