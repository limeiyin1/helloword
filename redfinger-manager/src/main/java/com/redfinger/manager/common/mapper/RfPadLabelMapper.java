package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfPadLabel;
import com.redfinger.manager.common.domain.RfPadLabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfPadLabelMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int countByExample(RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int deleteByExample(RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int deleteByPrimaryKey(Integer padLabelId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int insert(RfPadLabel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int insertSelective(RfPadLabel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	List<RfPadLabel> selectByExample(RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	RfPadLabel selectByPrimaryKey(Integer padLabelId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int updateByExampleSelective(@Param("record") RfPadLabel record, @Param("example") RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int updateByExample(@Param("record") RfPadLabel record, @Param("example") RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int updateByPrimaryKeySelective(RfPadLabel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int updateByPrimaryKey(RfPadLabel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	RfPadLabel selectOneByExample(RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	List<RfPadLabel> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	RfPadLabel selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int insertBatch(List<RfPadLabel> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int updateByExampleSelectiveSync(@Param("record") RfPadLabel record, @Param("example") RfPadLabelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	int updateByPrimaryKeySelectiveSync(RfPadLabel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad_label
	 * @mbggenerated  Mon Feb 27 19:03:27 CST 2017
	 */
	RfPadLabel selectByPrimaryKeyShowField(@Param("showField") List<String> showField,
			@Param("padLabelId") Integer padLabelId);
}