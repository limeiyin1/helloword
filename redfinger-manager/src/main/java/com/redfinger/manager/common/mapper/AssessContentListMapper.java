package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.AssessContentList;
import com.redfinger.manager.common.domain.AssessContentListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssessContentListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int countByExample(AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int deleteByExample(AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int insert(AssessContentList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int insertSelective(AssessContentList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    List<AssessContentList> selectByExample(AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    AssessContentList selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByExampleSelective(@Param("record") AssessContentList record, @Param("example") AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByExample(@Param("record") AssessContentList record, @Param("example") AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByPrimaryKeySelective(AssessContentList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByPrimaryKey(AssessContentList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    AssessContentList selectOneByExample(AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    List<AssessContentList> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    AssessContentList selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int insertBatch(List<AssessContentList> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByExampleSelectiveSync(@Param("record") AssessContentList record, @Param("example") AssessContentListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    int updateByPrimaryKeySelectiveSync(AssessContentList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assess_content_list
     *
     * @mbggenerated Wed Mar 23 14:42:45 CST 2016
     */
    AssessContentList selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}