package com.redfinger.manager.common.mapper;

import com.redfinger.manager.modules.stat.base.StatDomain;
import java.util.List;

/**
 * 
* @ClassName: StatRfUserTaskMapper
* @Description: 统计用户完成任务的mapper
* @author tluo
* @date 2015年9月23日 下午6:09:01
*
 */
public interface StatRfUserTaskMapper {
	/**
	 * 
	* @Title: statTaskOk2CreateTime 
	* @Description: 某个时间段内完成的任务
	* @param @param bean
	* @param @return
	* @return List<StatDomain>
	* @throws
	 */
	List<StatDomain> statTaskOk2CreateTime(StatDomain bean);
	/**
	 * 
	* @Title: statTaskRbcSend 
	* @Description: 任务赠送红豆查询
	* @param @param bean
	* @param @return
	* @return List<StatDomain>（number记录任务ID，label 记录完成时间）
	* @throws
	 */
	List<StatDomain> statTaskRbcSend(StatDomain bean);

}