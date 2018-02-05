package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfSmsTemplate;
import com.redfinger.manager.common.domain.RfSmsTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfSmsTemplateMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(RfSmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(RfSmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer smsTemplateId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(RfSmsTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(RfSmsTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfSmsTemplate> selectByExample(RfSmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfSmsTemplate selectByPrimaryKey(Integer smsTemplateId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") RfSmsTemplate record, @Param("example") RfSmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") RfSmsTemplate record, @Param("example") RfSmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(RfSmsTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(RfSmsTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfSmsTemplate selectOneByExample(RfSmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfSmsTemplate> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfSmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfSmsTemplate selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfSmsTemplateExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<RfSmsTemplate> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(RfSmsTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_sms_template
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfSmsTemplate selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("smsTemplateId") Integer smsTemplateId);
}