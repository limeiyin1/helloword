package com.redfinger.manager.common.mapper;

import java.util.Date;
import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatLogPadMapper {

	List<StatDomain> statNumber(StatDomain bean);
	
	Integer getBindNumberByEndDate(Date date);
}