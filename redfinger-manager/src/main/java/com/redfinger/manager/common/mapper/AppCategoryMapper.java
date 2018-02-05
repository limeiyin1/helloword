package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.AppCategory;
import com.redfinger.manager.common.domain.AppCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppCategoryMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int countByExample(AppCategoryExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int deleteByExample(AppCategoryExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int insert(AppCategory record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int insertSelective(AppCategory record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	List<AppCategory> selectByExample(AppCategoryExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	AppCategory selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int updateByExampleSelective(@Param("record") AppCategory record, @Param("example") AppCategoryExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int updateByExample(@Param("record") AppCategory record, @Param("example") AppCategoryExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int updateByPrimaryKeySelective(AppCategory record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int updateByPrimaryKey(AppCategory record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	AppCategory selectOneByExample(AppCategoryExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	List<AppCategory> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") AppCategoryExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	AppCategory selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") AppCategoryExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int insertBatch(List<AppCategory> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(AppCategory record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_category
	 * @mbggenerated  Sun Dec 27 10:51:21 CST 2015
	 */
	AppCategory selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}