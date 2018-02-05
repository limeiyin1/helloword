package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.common.domain.RfOrder;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatOrderMapper {
	List<StatDomain> statSucceedOrder(StatDomain bean);
	Integer priceByExample(RfOrder bean);
	List<StatDomain> statSucceedOrderCount(StatDomain bean);
	List<StatDomain> statSucceedOrderGoods(StatDomain bean);
	List<StatDomain> statSucceedOrder1(StatDomain bean);
	
	/**
	* @Title: statOrderNumber2Time 
	* @Description: 统计一个时间段每个用户的订单数和总金额
	* @param @param bean
	* @param @return
	* @return List<StatDomain>
	* @throws
	 */
	List<RfUser> statOrder2Time(StatDomain bean);
	Integer statPayForTheFirstTime(StatDomain bean);
	Integer statAgainPay(StatDomain bean);
}
