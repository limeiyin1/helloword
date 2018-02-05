package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfUserTask;
import com.redfinger.manager.common.domain.RfUserTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfUserTaskMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(RfUserTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(RfUserTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer userTaskId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(RfUserTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(RfUserTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfUserTask> selectByExample(RfUserTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfUserTask selectByPrimaryKey(Integer userTaskId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") RfUserTask record, @Param("example") RfUserTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") RfUserTask record, @Param("example") RfUserTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(RfUserTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(RfUserTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfUserTask selectOneByExample(RfUserTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfUserTask> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfUserTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfUserTask selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfUserTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<RfUserTask> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(RfUserTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_user_task
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfUserTask selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("userTaskId") Integer userTaskId);
}