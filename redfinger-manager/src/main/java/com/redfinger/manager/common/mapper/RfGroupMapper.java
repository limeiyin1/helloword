package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfGroup;
import com.redfinger.manager.common.domain.RfGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfGroupMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int countByExample(RfGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int deleteByExample(RfGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int deleteByPrimaryKey(Integer groupId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int insert(RfGroup record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int insertSelective(RfGroup record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	List<RfGroup> selectByExample(RfGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	RfGroup selectByPrimaryKey(Integer groupId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int updateByExampleSelective(@Param("record") RfGroup record, @Param("example") RfGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int updateByExample(@Param("record") RfGroup record, @Param("example") RfGroupExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int updateByPrimaryKeySelective(RfGroup record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	int updateByPrimaryKey(RfGroup record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_group
	 * @mbggenerated  Sun Dec 10 15:34:23 CST 2017
	 */
	RfGroup selectOneByExample(RfGroupExample example);
}