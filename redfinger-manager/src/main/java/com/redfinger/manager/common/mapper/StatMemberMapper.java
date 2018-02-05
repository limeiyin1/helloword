package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatMemberMapper {
	List<StatDomain> statMenberCarriers(StatDomain bean);
	List<StatDomain> statIp(StatDomain bean);
	
	/**
	 * 统计会员年龄
	 * @param bean
	 * @return
	 */
	List<StatDomain> statMemberAge();
	List<StatDomain> statMemberSex();
	List<StatDomain> statMemberMobileCarriers();
}
