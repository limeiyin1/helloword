package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfSurveyLog;
import com.redfinger.manager.modules.stat.base.StatDomain;


/**
 * 
* @ClassName: StatRfSurveyLogMapper
* @Description: 问卷调查统计
* @author tluo
* @date 2015年11月10日 上午9:23:04
*
 */
public interface StatRfSurveyLogMapper {
	/**
	 * 
	* @Title: StatSurveyLog 
	* @Description: 根据问卷ID，问题Id，答案Id获取该答案被选择的次数
	* @param @param rfSurveyLog
	* @param @return
	* @return StatDomain
	* @throws
	 */
	public StatDomain StatSurveyLog(RfSurveyLog rfSurveyLog);
}