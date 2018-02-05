package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfAwardBatch;
import com.redfinger.manager.common.domain.RfAwardBatchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfAwardBatchMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int countByExample(RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int deleteByExample(RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int deleteByPrimaryKey(Integer awardId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int insert(RfAwardBatch record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int insertSelective(RfAwardBatch record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	List<RfAwardBatch> selectByExample(RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	RfAwardBatch selectByPrimaryKey(Integer awardId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int updateByExampleSelective(@Param("record") RfAwardBatch record, @Param("example") RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int updateByExample(@Param("record") RfAwardBatch record, @Param("example") RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int updateByPrimaryKeySelective(RfAwardBatch record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int updateByPrimaryKey(RfAwardBatch record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	RfAwardBatch selectOneByExample(RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	List<RfAwardBatch> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	RfAwardBatch selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int insertBatch(List<RfAwardBatch> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int updateByExampleSelectiveSync(@Param("record") RfAwardBatch record,
			@Param("example") RfAwardBatchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	int updateByPrimaryKeySelectiveSync(RfAwardBatch record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_award_batch
	 * @mbggenerated  Mon Feb 06 18:28:54 CST 2017
	 */
	RfAwardBatch selectByPrimaryKeyShowField(@Param("showField") List<String> showField,
			@Param("awardId") Integer awardId);
}