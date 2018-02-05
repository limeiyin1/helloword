package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfUserInformation;
import com.redfinger.manager.common.domain.RfUserInformationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfUserInformationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int countByExample(RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int deleteByExample(RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int deleteByPrimaryKey(Integer userInformationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int insert(RfUserInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int insertSelective(RfUserInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    List<RfUserInformation> selectByExample(RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    RfUserInformation selectByPrimaryKey(Integer userInformationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int updateByExampleSelective(@Param("record") RfUserInformation record, @Param("example") RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int updateByExample(@Param("record") RfUserInformation record, @Param("example") RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int updateByPrimaryKeySelective(RfUserInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int updateByPrimaryKey(RfUserInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    RfUserInformation selectOneByExample(RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    List<RfUserInformation> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    RfUserInformation selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int insertBatch(List<RfUserInformation> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int updateByExampleSelectiveSync(@Param("record") RfUserInformation record, @Param("example") RfUserInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    int updateByPrimaryKeySelectiveSync(RfUserInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_user_information
     *
     * @mbggenerated Wed Oct 12 14:25:08 CST 2016
     */
    RfUserInformation selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("userInformationId") Integer userInformationId);
}