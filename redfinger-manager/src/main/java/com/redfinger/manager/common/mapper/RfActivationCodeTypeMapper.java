package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfActivationCodeType;
import com.redfinger.manager.common.domain.RfActivationCodeTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfActivationCodeTypeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int countByExample(RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int deleteByExample(RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int deleteByPrimaryKey(Integer typeId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int insert(RfActivationCodeType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int insertSelective(RfActivationCodeType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	List<RfActivationCodeType> selectByExample(RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	RfActivationCodeType selectByPrimaryKey(Integer typeId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int updateByExampleSelective(@Param("record") RfActivationCodeType record,
			@Param("example") RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int updateByExample(@Param("record") RfActivationCodeType record,
			@Param("example") RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int updateByPrimaryKeySelective(RfActivationCodeType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int updateByPrimaryKey(RfActivationCodeType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	RfActivationCodeType selectOneByExample(RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	List<RfActivationCodeType> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	RfActivationCodeType selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int insertBatch(List<RfActivationCodeType> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int updateByExampleSelectiveSync(@Param("record") RfActivationCodeType record,
			@Param("example") RfActivationCodeTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	int updateByPrimaryKeySelectiveSync(RfActivationCodeType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_activation_code_type
	 * @mbggenerated  Wed Jun 07 09:03:01 CST 2017
	 */
	RfActivationCodeType selectByPrimaryKeyShowField(@Param("showField") List<String> showField,
			@Param("typeId") Integer typeId);
}