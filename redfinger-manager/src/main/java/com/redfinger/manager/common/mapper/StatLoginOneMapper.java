package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatLoginOneMapper {

	List<StatDomain> statLoginOne(StatDomain bean);

	List<StatDomain> countAll(StatDomain bean);

}
