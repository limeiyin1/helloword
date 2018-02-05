package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;

/**
 * 用户客户端操作统计
 * 
 * @ClassName: StatClientOperateMapper
 * @author tluo
 * @date 2016年2月19日 下午3:21:59
 */
public interface StatClientOperateMapper {
	/**
	 * 统计指定时间段类每点钟用户登录数量（24小时）
	 * 
	 * @Title: statUserLoginTo24
	 * @return List<StatDomain> 返回类型
	 * @param bean
	 * @return
	 */
	List<StatDomain> statUserLoginTo24(StatDomain bean);

	/**
	 * 统计用户按钮操作
	 * 
	 * @Title: statUIButton
	 * @return List<StatDomain> 返回类型
	 * @param bean
	 * @return
	 */
	List<StatDomain> statUIButton(StatDomain bean);

	/**
	 * 统计新用户前20步操作
	 * 
	 * @Title: statNewUserUIButton
	 * @return List<StatDomain> 返回类型
	 * @param bean
	 * @return
	 */
	List<StatDomain> statNewUserUIButton(StatDomain bean);

	/**
	 * 饼图统计用户按钮操作
	 * 
	 * @Title: statBtButton
	 * @return List<StatDomain> 返回类型
	 * @param bean
	 * @return
	 */
	List<StatDomain> statBtButton(StatDomain bean);

}