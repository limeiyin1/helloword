package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.AssessProjectOption;
import com.redfinger.manager.common.domain.AssessProjectOptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssessProjectOptionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int countByExample(AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int deleteByExample(AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int insert(AssessProjectOption record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int insertSelective(AssessProjectOption record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    List<AssessProjectOption> selectByExample(AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    AssessProjectOption selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByExampleSelective(@Param("record") AssessProjectOption record, @Param("example") AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByExample(@Param("record") AssessProjectOption record, @Param("example") AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByPrimaryKeySelective(AssessProjectOption record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByPrimaryKey(AssessProjectOption record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    AssessProjectOption selectOneByExample(AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    List<AssessProjectOption> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    AssessProjectOption selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int insertBatch(List<AssessProjectOption> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByExampleSelectiveSync(@Param("record") AssessProjectOption record, @Param("example") AssessProjectOptionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByPrimaryKeySelectiveSync(AssessProjectOption record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_project_option
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    AssessProjectOption selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}