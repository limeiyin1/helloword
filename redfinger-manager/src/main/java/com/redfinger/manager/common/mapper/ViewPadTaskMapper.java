package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.ViewPadTask;
import com.redfinger.manager.common.domain.ViewPadTaskExample;
import java.util.List;

public interface ViewPadTaskMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table view_pad_task
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	int countByExample(ViewPadTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table view_pad_task
	 * @mbggenerated  Thu Mar 31 19:54:20 CST 2016
	 */
	List<ViewPadTask> selectByExample(ViewPadTaskExample example);
}