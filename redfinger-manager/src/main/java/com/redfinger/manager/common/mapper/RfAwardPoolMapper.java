package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfAwardPool;
import com.redfinger.manager.common.domain.RfAwardPoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfAwardPoolMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int countByExample(RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int deleteByExample(RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int insert(RfAwardPool record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int insertSelective(RfAwardPool record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	List<RfAwardPool> selectByExample(RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	RfAwardPool selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int updateByExampleSelective(@Param("record") RfAwardPool record, @Param("example") RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int updateByExample(@Param("record") RfAwardPool record, @Param("example") RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int updateByPrimaryKeySelective(RfAwardPool record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int updateByPrimaryKey(RfAwardPool record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	RfAwardPool selectOneByExample(RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	List<RfAwardPool> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	RfAwardPool selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int insertBatch(List<RfAwardPool> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int updateByExampleSelectiveSync(@Param("record") RfAwardPool record, @Param("example") RfAwardPoolExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	int updateByPrimaryKeySelectiveSync(RfAwardPool record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_pool
	 * @mbggenerated  Fri Oct 20 16:38:58 CST 2017
	 */
	RfAwardPool selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}