package com.redfinger.manager.modules.stat.service;


import java.util.Date;
import java.util.List;



import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;






import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfOrder;
import com.redfinger.manager.common.domain.RfOrderExample;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfOrderMapper;
import com.redfinger.manager.common.mapper.StatOrderMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.stat.base.StatDomain;	



@Transactional
@Service
@PrimaryKeyAnnotation(field = "orderId")
public class StatOrderUserService extends BaseService<RfOrder, RfOrderExample, RfOrderMapper> {

	@Autowired
	StatOrderMapper mapper;
	
	@Autowired
	UserService userService;
	
	@Transactional(readOnly = true)
	public List<RfUser> statUserOrder(StatDomain bean) {
		
		List<RfUser> order_list = mapper.statOrder2Time(bean);
		for(RfUser rf : order_list){
			rf.getMap().put("ordernumber", rf.getCreater());
			rf.getMap().put("ordermoney",Double.parseDouble(rf.getModifier())/100.0);
			rf.getMap().put("orderaverage", Double.parseDouble(rf.getModifier())/100.0/Integer.parseInt(rf.getCreater()));
		}
		return order_list;
		}

	public void validate(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
		if (bean.getType().equals("month")) {
			if (DateUtils.addYears(beginDate, 1).before(endDate)) {
				throw new BusinessException("月统计，时段不能超过1年！");
			}
		} else if (bean.getType().equals("week")) {
			if (DateUtils.addMonths(beginDate, 6).before(endDate)) {
				throw new BusinessException("周统计，时段不能超过半年！");
			}
		} else if (bean.getType().equals("day")) {
			if (DateUtils.addMonths(beginDate, 1).before(endDate)) {
				throw new BusinessException("天统计，时段不能超过1个月！");
			}
		}
	}
	
	@Transactional(readOnly=true)
	public Map<String, Object> statPayForTheFirstTime(StatDomain bean){
		Integer date=0;
		Map<String, Object> map = Maps.newHashMap();
		List<String>label=Lists.newArrayList();
		List<Integer>number=Lists.newArrayList();
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
		if (bean.getType().equals("month")) {
			if (DateUtils.addYears(beginDate, 1).before(endDate)) {
				throw new BusinessException("月统计，时段不能超过1年！");
			}
		do {
			StatDomain stat=new StatDomain();
			stat.setBegin(DateUtils.formatDateTime(DateUtils.addMonths(DateUtils.parseDate(bean.getBegin()), date)));	
			date+=1;
			if(DateUtils.addDays(DateUtils.parseDate(bean.getEnd()),1).getTime()>=DateUtils.addMonths(DateUtils.parseDate(bean.getBegin()), date).getTime()){
				stat.setEnd(DateUtils.formatDateTime(DateUtils.addMonths(DateUtils.parseDate(bean.getBegin()), date)));
				label.add(DateUtils.formatDate(DateUtils.addMonths(DateUtils.parseDate(bean.getBegin()), date-1),"yyyy-MM-dd"));
			}else{
				break;
			}
			number.add(mapper.statPayForTheFirstTime(stat));
	
		} while (true);
		map.put("label", label);
		map.put("number", number);
		return map;
		} else if (bean.getType().equals("week")) {
			if (DateUtils.addMonths(beginDate, 6).before(endDate)) {
				throw new BusinessException("周统计，时段不能超过半年！");
			}
			do {
				StatDomain stat=new StatDomain();
				stat.setBegin(DateUtils.formatDateTime(DateUtils.addWeeks(DateUtils.parseDate(bean.getBegin()), date)));	
				date+=1;
				if(DateUtils.addDays(DateUtils.parseDate(bean.getEnd()),1).getTime()>=DateUtils.addWeeks(DateUtils.parseDate(bean.getBegin()), date).getTime()){
					stat.setEnd(DateUtils.formatDateTime(DateUtils.addWeeks(DateUtils.parseDate(bean.getBegin()), date)));
					label.add(DateUtils.formatDate(DateUtils.addWeeks(DateUtils.parseDate(bean.getBegin()), date-1),"yyyy-MM-dd"));
				}else{
					break;
				}
				number.add(mapper.statPayForTheFirstTime(stat));
		
			} while (true);
			map.put("label", label);
			map.put("number", number);
			return map;
		} else if (bean.getType().equals("day")) {
			if (DateUtils.addMonths(beginDate, 1).before(endDate)) {
				throw new BusinessException("天统计，时段不能超过1个月！");
			}
			do {
				StatDomain stat=new StatDomain();
				stat.setBegin(DateUtils.formatDateTime(DateUtils.addDays(DateUtils.parseDate(bean.getBegin()), date)));
				date+=1;
				if(DateUtils.addDays(DateUtils.parseDate(bean.getEnd()),1).getTime()>=DateUtils.addDays(DateUtils.parseDate(bean.getBegin()), date).getTime()){
					stat.setEnd(DateUtils.formatDateTime(DateUtils.addDays(DateUtils.parseDate(bean.getBegin()), date)));
					label.add(DateUtils.formatDate(DateUtils.addDays(DateUtils.parseDate(bean.getBegin()), date-1),"yyyy-MM-dd"));
				}else{
					break;
				}
				number.add(mapper.statPayForTheFirstTime(stat));
		
			} while (true);
			map.put("label", label);
			map.put("number", number);
			return map;
		}
		return map;
	}
	
	@Transactional(readOnly=true)
	public Map<String, Object> statAgainPay(StatDomain bean ){
		Integer date=0;
		Map<String, Object> map = Maps.newHashMap();
		List<String>label=Lists.newArrayList();
		List<Integer>number=Lists.newArrayList();
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		Date beginDate = DateUtils.parseDate(bean.getBegin());
		Date endDate = DateUtils.parseDate(bean.getEnd());
		if (beginDate.after(endDate)) {
			throw new BusinessException("开始日期不能大于结束日期！");
		}
		if (bean.getType().equals("month")) {
			if (DateUtils.addYears(beginDate, 1).before(endDate)) {
				throw new BusinessException("月统计，时段不能超过1年！");
			}
		do {
			StatDomain stat=new StatDomain();
			stat.setBegin(DateUtils.formatDateTime(DateUtils.addMonths(DateUtils.parseDate(bean.getBegin()), date)));	
			date+=1;
			if(DateUtils.addDays(DateUtils.parseDate(bean.getEnd()),1).getTime()>=DateUtils.addMonths(DateUtils.parseDate(bean.getBegin()), date).getTime()){
				stat.setEnd(DateUtils.formatDateTime(DateUtils.addMonths(DateUtils.parseDate(bean.getBegin()), date)));
				int radix=0-Integer.parseInt(bean.getWhere());
				stat.setFormart(DateUtils.formatDateTime(DateUtils.addDays(DateUtils.parseDate(stat.getBegin()), radix)));
				label.add(DateUtils.formatDate(DateUtils.addMonths(DateUtils.parseDate(bean.getBegin()), date-1),"yyyy-MM-dd"));
			}else{
				break;
			}
			number.add(mapper.statAgainPay(stat));
	
		} while (true);
		map.put("label", label);
		map.put("number", number);
		return map;
		} else if (bean.getType().equals("week")) {
			if (DateUtils.addMonths(beginDate, 6).before(endDate)) {
				throw new BusinessException("周统计，时段不能超过半年！");
			}
			do {
				StatDomain stat=new StatDomain();
				stat.setBegin(DateUtils.formatDateTime(DateUtils.addWeeks(DateUtils.parseDate(bean.getBegin()), date)));	
				date+=1;
				if(DateUtils.addDays(DateUtils.parseDate(bean.getEnd()),1).getTime()>=DateUtils.addWeeks(DateUtils.parseDate(bean.getBegin()), date).getTime()){
					stat.setEnd(DateUtils.formatDateTime(DateUtils.addWeeks(DateUtils.parseDate(bean.getBegin()), date)));
					int radix=0-Integer.parseInt(bean.getWhere());
					stat.setFormart(DateUtils.formatDateTime(DateUtils.addDays(DateUtils.parseDate(stat.getBegin()),radix)));
					label.add(DateUtils.formatDate(DateUtils.addWeeks(DateUtils.parseDate(bean.getBegin()), date-1),"yyyy-MM-dd"));
				}else{
					break;
				}
				number.add(mapper.statAgainPay(stat));
		
			} while (true);
			map.put("label", label);
			map.put("number", number);
			return map;
		} else if (bean.getType().equals("day")) {
			if (DateUtils.addMonths(beginDate, 1).before(endDate)) {
				throw new BusinessException("天统计，时段不能超过1个月！");
			}
			do {
				StatDomain stat=new StatDomain();
				stat.setBegin(DateUtils.formatDateTime(DateUtils.addDays(DateUtils.parseDate(bean.getBegin()), date)));
				date+=1;
				if(DateUtils.addDays(DateUtils.parseDate(bean.getEnd()),1).getTime()>=DateUtils.addDays(DateUtils.parseDate(bean.getBegin()), date).getTime()){
					stat.setEnd(DateUtils.formatDateTime(DateUtils.addDays(DateUtils.parseDate(bean.getBegin()), date)));
					int radix=0-Integer.parseInt(bean.getWhere());
					stat.setFormart(DateUtils.formatDateTime(DateUtils.addDays(DateUtils.parseDate(stat.getBegin()),radix)));
					label.add(DateUtils.formatDate(DateUtils.addDays(DateUtils.parseDate(bean.getBegin()), date-1),"yyyy-MM-dd"));
				}else{
					break;
				}
				number.add(mapper.statAgainPay(stat));
		
			} while (true);
			map.put("label", label);
			map.put("number", number);
			return map;
		}
		return map;
	}
	
}
