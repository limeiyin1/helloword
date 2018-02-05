package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfPadGrantCode;
import com.redfinger.manager.common.domain.RfPadGrantCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfPadGrantCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int countByExample(RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int deleteByExample(RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int deleteByPrimaryKey(Integer grantCodeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int insert(RfPadGrantCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int insertSelective(RfPadGrantCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    List<RfPadGrantCode> selectByExample(RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    RfPadGrantCode selectByPrimaryKey(Integer grantCodeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int updateByExampleSelective(@Param("record") RfPadGrantCode record, @Param("example") RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int updateByExample(@Param("record") RfPadGrantCode record, @Param("example") RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int updateByPrimaryKeySelective(RfPadGrantCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int updateByPrimaryKey(RfPadGrantCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    RfPadGrantCode selectOneByExample(RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    List<RfPadGrantCode> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    RfPadGrantCode selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int insertBatch(List<RfPadGrantCode> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int updateByExampleSelectiveSync(@Param("record") RfPadGrantCode record, @Param("example") RfPadGrantCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    int updateByPrimaryKeySelectiveSync(RfPadGrantCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_grant_code
     *
     * @mbggenerated Tue Jun 21 15:34:38 CST 2016
     */
    RfPadGrantCode selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("grantCodeId") Integer grantCodeId);
}