package com.redfinger.manager.common.mapper;

import java.util.List;
import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatRfPadStatusLogMapper {
	/**
	 * 
	* @Title: statChippyPad 
	* @Description: 统计活跃设备
	* @param @param bean
	* @param @return
	* @return List<StatDomain>
	* @throws
	 */
	List<StatDomain> statChippyPad(StatDomain bean);
	
}