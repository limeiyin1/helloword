package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;



public interface StatSysLoginLogMapper {
	/**
	 * 
	* @Title: selectClientType2LoginTime 
	* @Description: 统计登录次数
	* @param @param bean
	* @param @return
	* @return List<StatDomain>
	* @throws
	 */
	List<StatDomain> selectClientType2LoginTime(StatDomain bean);
	List<StatDomain> selectCountAndroid(StatDomain bean);
	List<StatDomain> selectCountPc(StatDomain bean);
	List<StatDomain> selectCount(StatDomain bean);
	List<StatDomain> selectCountNull(StatDomain bean);
}