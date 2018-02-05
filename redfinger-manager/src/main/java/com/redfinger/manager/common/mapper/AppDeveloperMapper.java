package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.AppDeveloper;
import com.redfinger.manager.common.domain.AppDeveloperExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppDeveloperMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int countByExample(AppDeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int deleteByExample(AppDeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int insert(AppDeveloper record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int insertSelective(AppDeveloper record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    List<AppDeveloper> selectByExample(AppDeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    AppDeveloper selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int updateByExampleSelective(@Param("record") AppDeveloper record, @Param("example") AppDeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int updateByExample(@Param("record") AppDeveloper record, @Param("example") AppDeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int updateByPrimaryKeySelective(AppDeveloper record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int updateByPrimaryKey(AppDeveloper record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    AppDeveloper selectOneByExample(AppDeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    List<AppDeveloper> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") AppDeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    AppDeveloper selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") AppDeveloperExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int insertBatch(List<AppDeveloper> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    int updateByPrimaryKeySelectiveSync(AppDeveloper record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_developer
     *
     * @mbggenerated Mon Dec 21 11:12:38 CST 2015
     */
    AppDeveloper selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}