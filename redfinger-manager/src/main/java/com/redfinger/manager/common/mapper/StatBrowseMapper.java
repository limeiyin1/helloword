package com.redfinger.manager.common.mapper;

import java.util.Date;
import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatBrowseMapper {
List<StatDomain> statNumber(StatDomain bean);
	
	Integer statBrowse(Date date);
}
