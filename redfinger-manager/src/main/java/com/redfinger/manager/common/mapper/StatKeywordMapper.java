package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatKeywordMapper {

	List<StatDomain> selectKeyword(StatDomain bean);
	
}
