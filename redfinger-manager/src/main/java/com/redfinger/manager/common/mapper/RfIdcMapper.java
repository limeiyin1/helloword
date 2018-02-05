package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfIdcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfIdcMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int countByExample(RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int deleteByExample(RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int deleteByPrimaryKey(Integer idcId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int insert(RfIdc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int insertSelective(RfIdc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	List<RfIdc> selectByExample(RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	RfIdc selectByPrimaryKey(Integer idcId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int updateByExampleSelective(@Param("record") RfIdc record,
			@Param("example") RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int updateByExample(@Param("record") RfIdc record,
			@Param("example") RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int updateByPrimaryKeySelective(RfIdc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int updateByPrimaryKey(RfIdc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	RfIdc selectOneByExample(RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	List<RfIdc> selectByExampleShowField(
			@Param("showField") List<String> showField,
			@Param("example") RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	RfIdc selectOneByExampleShowField(
			@Param("showField") List<String> showField,
			@Param("example") RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int insertBatch(List<RfIdc> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int updateByExampleSelectiveSync(@Param("record") RfIdc record,
			@Param("example") RfIdcExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	int updateByPrimaryKeySelectiveSync(RfIdc record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_idc
	 * @mbggenerated  Thu Jun 02 17:56:52 CST 2016
	 */
	RfIdc selectByPrimaryKeyShowField(
			@Param("showField") List<String> showField,
			@Param("idcId") Integer idcId);
}