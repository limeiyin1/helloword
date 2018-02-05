package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface StatGameDownloadMapper {

	/**
	 * 
	* @Title: selectClientType2LoginTime 
	* @Description: 统计游戏下载次数
	* @param @param bean
	* @param @return
	* @return List<StatDomain>
	* @throws
	 */
	
	List<StatDomain> selectGameCount(StatDomain bean);//获取游戏下载的数量
}
