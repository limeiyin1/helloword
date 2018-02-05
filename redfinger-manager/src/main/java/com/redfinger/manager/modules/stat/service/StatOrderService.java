package com.redfinger.manager.modules.stat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfOrder;
import com.redfinger.manager.common.domain.RfOrderExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfOrderMapper;
import com.redfinger.manager.common.mapper.StatOrderMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.goods.service.GoodsService;
import com.redfinger.manager.modules.stat.base.StatDomain;	
import com.redfinger.manager.modules.stat.utils.StatUtils;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "orderId")
public class StatOrderService extends BaseService<RfOrder, RfOrderExample, RfOrderMapper> {
 
	@Autowired
	GoodsService goodsService;
	@Autowired
	StatOrderMapper mapper;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> stat(StatDomain bean) {
		Integer price=0;
		Integer count=0;
		this.validate(bean);
		Map<String, Object> map = Maps.newHashMap();
		List<Integer> number = Lists.newArrayList();
		List<Integer> number1 = Lists.newArrayList();
		List<String> label = Lists.newArrayList();
		List<String> label1 = Lists.newArrayList();
		map.put("subTitle", bean.getSubTitle());
		map.put("number", number);
		map.put("label", label);
		map.put("number1", number1);
		map.put("label1", label1);
		String legend=(DictHelper.getLabel("rf_order.order_type", bean.getWhere()));
		map.put("legend",legend );
		List<StatDomain> list = mapper.statSucceedOrder(bean);
		List<StatDomain> countlist = mapper.statSucceedOrderCount(bean);
		for (StatDomain domain : list) {
			number.add(domain.getNumber());
			label.add(domain.getLabel());
	        price+=domain.getNumber();	
		}   
		StatUtils.transferDateResultForNumber(bean,map,"label","number");
		for(StatDomain domain : countlist){
			count+=domain.getNumber();
			number1.add(domain.getNumber());
			label1.add(domain.getLabel());
		}
		StatUtils.transferDateResultForNumber(bean, map,"label1","number1");
		((List<Integer>)map.get("number")).addAll((List<Integer>)map.get("number1"));
		//移除MAP里不需要的KEY
		map.remove("number1");
		map.remove("label1");
		map.put("price", price);
		map.put("count", count);
		return map;
		}

	
	@Transactional(readOnly = true)
	public Map<String, Object> statOrder(StatDomain bean) {
		if (bean == null || StringUtils.isEmpty(bean.getType()) || StringUtils.isEmpty(bean.getBegin()) || StringUtils.isEmpty(bean.getEnd())) {
			throw new BusinessException("查询条件不能为空！");
		}
		
		int i=0;
		Map<String, List<StatDomain>> m_date = Maps.newHashMap();
		List<RfGoods>list = Lists.newArrayList();
		list=goodsService.initQuery().andLessThan("createTime",  DateUtils.parseDateAddOneDay(bean.getEnd())).findStopTrue();
		for (RfGoods rfGoods : list) {
			m_date.put(rfGoods.getGoodsId().toString(), null);
		}
		List<?>[] l_arr = new List<?>[list.size()];
		String names=null;
		String goodsIds=null;	
		for(Entry<String, List<StatDomain>> entry : m_date.entrySet()){
			String goodsId = entry.getKey();
			goodsIds+=goodsId+",";
			bean.setWhere("goods_id='"+goodsId+"'");
			List<StatDomain> date_list = mapper.statSucceedOrder1(bean);
			l_arr[i++]=(mapper.statSucceedOrder1(bean));
			entry.setValue(date_list);
		}
		
		Map<String, Object> map = StatUtils.autoStatDate(bean.getType(), bean.getBegin(),bean.getEnd(),l_arr);
		int countAll = 0;//总数统计
		int price= 0;//总数统计
		for (RfGoods rfGoods : list) {
		for(StatDomain ss :m_date.get(rfGoods.getGoodsId().toString())){
			countAll+=ss.getNumber();
			price+=rfGoods.getGoodsPrice()/100;
		}
		}
		map.put("countAll", countAll);
		map.put("price", price);
		goodsIds=goodsIds.replaceAll("null", "");
		for (String string : goodsIds.split(",")) {
			names+=goodsService.get(Integer.parseInt(string)).getGoodsName()+",";
		}
		names=names.replaceAll("null", "");
		names=names.substring(0, names.length()-1);
		map.put("names", names);
		map.put("goodsIds", goodsIds);
	    return map;	
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
	
}
