package com.redfinger.manager.common.gather.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.redfinger.manager.common.gather.domain.RfGatherFieldDefine;
import com.redfinger.manager.common.gather.domain.RfGatherFieldDefineExample;

public interface RfGatherFieldDefineMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int countByExample(RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int deleteByExample(RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int deleteByPrimaryKey(String fieldCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int insert(RfGatherFieldDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int insertSelective(RfGatherFieldDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    List<RfGatherFieldDefine> selectByExample(RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    RfGatherFieldDefine selectByPrimaryKey(String fieldCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int updateByExampleSelective(@Param("record") RfGatherFieldDefine record, @Param("example") RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int updateByExample(@Param("record") RfGatherFieldDefine record, @Param("example") RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int updateByPrimaryKeySelective(RfGatherFieldDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int updateByPrimaryKey(RfGatherFieldDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    RfGatherFieldDefine selectOneByExample(RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    List<RfGatherFieldDefine> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    RfGatherFieldDefine selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int insertBatch(List<RfGatherFieldDefine> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int updateByExampleSelectiveSync(@Param("record") RfGatherFieldDefine record, @Param("example") RfGatherFieldDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    int updateByPrimaryKeySelectiveSync(RfGatherFieldDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_gather_field_define
     *
     * @mbggenerated Thu Jul 27 10:17:55 CST 2017
     */
    RfGatherFieldDefine selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("fieldCode") String fieldCode);
}