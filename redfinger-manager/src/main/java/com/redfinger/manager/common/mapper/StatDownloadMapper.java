package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatDownloadMapper {
	/**
	 * 
	* @Title: selectClientType2LoginTime 
	* @Description: 统计下载次数
	* @param @param bean
	* @param @return
	* @return List<StatDomain>
	* @throws
	 */
	
	List<StatDomain> selectCountApp(StatDomain bean);//获取下载应用市场的数量
	
}
