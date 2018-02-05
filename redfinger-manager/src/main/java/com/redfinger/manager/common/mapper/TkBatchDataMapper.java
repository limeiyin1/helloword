package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.TkBatchData;
import com.redfinger.manager.common.domain.TkBatchDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TkBatchDataMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int countByExample(TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int deleteByExample(TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int deleteByPrimaryKey(Integer dataId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int insert(TkBatchData record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int insertSelective(TkBatchData record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	List<TkBatchData> selectByExample(TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	TkBatchData selectByPrimaryKey(Integer dataId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int updateByExampleSelective(@Param("record") TkBatchData record, @Param("example") TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int updateByExample(@Param("record") TkBatchData record, @Param("example") TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int updateByPrimaryKeySelective(TkBatchData record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int updateByPrimaryKey(TkBatchData record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	TkBatchData selectOneByExample(TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	List<TkBatchData> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	TkBatchData selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int insertBatch(List<TkBatchData> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int updateByExampleSelectiveSync(@Param("record") TkBatchData record, @Param("example") TkBatchDataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	int updateByPrimaryKeySelectiveSync(TkBatchData record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table tk_batch_data
	 * @mbggenerated  Sat Apr 01 14:24:58 CST 2017
	 */
	TkBatchData selectByPrimaryKeyShowField(@Param("showField") List<String> showField,
			@Param("dataId") Integer dataId);
}