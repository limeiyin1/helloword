package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfActivationCodeLog;
import com.redfinger.manager.common.domain.RfActivationCodeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfActivationCodeLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int countByExample(RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int deleteByExample(RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int deleteByPrimaryKey(Integer logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int insert(RfActivationCodeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int insertSelective(RfActivationCodeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    List<RfActivationCodeLog> selectByExample(RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    RfActivationCodeLog selectByPrimaryKey(Integer logId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int updateByExampleSelective(@Param("record") RfActivationCodeLog record, @Param("example") RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int updateByExample(@Param("record") RfActivationCodeLog record, @Param("example") RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int updateByPrimaryKeySelective(RfActivationCodeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int updateByPrimaryKey(RfActivationCodeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    RfActivationCodeLog selectOneByExample(RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    List<RfActivationCodeLog> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    RfActivationCodeLog selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int insertBatch(List<RfActivationCodeLog> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int updateByExampleSelectiveSync(@Param("record") RfActivationCodeLog record, @Param("example") RfActivationCodeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    int updateByPrimaryKeySelectiveSync(RfActivationCodeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_activation_code_log
     *
     * @mbggenerated Mon Jul 04 10:58:14 CST 2016
     */
    RfActivationCodeLog selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("logId") Integer logId);
}