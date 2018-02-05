package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatFaultMapper {

	List<StatDomain> statFaultCategory(StatDomain bean);
	
	List<StatDomain> statFaultCategoryFix(StatDomain bean);
	
	List<StatDomain> statFaultAcceptByRole(StatDomain bean);
	
	List<StatDomain> statFualtSolveByRole(StatDomain bean);
	
	List<StatDomain> statFualtFixTimeByRole(StatDomain bean);
	
}