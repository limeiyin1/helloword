package com.redfinger.manager.common.mapper;

import com.redfinger.manager.modules.stat.base.StatDomain;
import java.util.List;
/**
 * 
* @ClassName: StatRfUserMapper
* @Description: mapper
* @author tluo
* @date 2015年9月23日 上午10:15:03
*
*/
public interface StatRfUserMapper {
	/**
	 * 
	* @Title: statUserLogin2CreateTime 
	* @Description: 注册用户统计查询
	* @param @param bean
	* @param @return
	* @return List<StatDomain>
	* @throws
	 */
	List<StatDomain> statUserLogin2CreateTime(StatDomain bean);

}