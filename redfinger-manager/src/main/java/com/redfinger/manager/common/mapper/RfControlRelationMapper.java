package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfControlRelation;
import com.redfinger.manager.common.domain.RfControlRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfControlRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int countByExample(RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int deleteByExample(RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int deleteByPrimaryKey(Integer relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int insert(RfControlRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int insertSelective(RfControlRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    List<RfControlRelation> selectByExample(RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    RfControlRelation selectByPrimaryKey(Integer relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int updateByExampleSelective(@Param("record") RfControlRelation record, @Param("example") RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int updateByExample(@Param("record") RfControlRelation record, @Param("example") RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int updateByPrimaryKeySelective(RfControlRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int updateByPrimaryKey(RfControlRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    RfControlRelation selectOneByExample(RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    List<RfControlRelation> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    RfControlRelation selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int insertBatch(List<RfControlRelation> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int updateByExampleSelectiveSync(@Param("record") RfControlRelation record, @Param("example") RfControlRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    int updateByPrimaryKeySelectiveSync(RfControlRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_control_relation
     *
     * @mbggenerated Tue Apr 18 15:17:56 CST 2017
     */
    RfControlRelation selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("relationId") Integer relationId);
}