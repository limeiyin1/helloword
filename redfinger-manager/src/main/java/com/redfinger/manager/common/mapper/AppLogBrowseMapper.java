package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.AppLogBrowse;
import com.redfinger.manager.common.domain.AppLogBrowseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppLogBrowseMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(AppLogBrowseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(AppLogBrowseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(AppLogBrowse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(AppLogBrowse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<AppLogBrowse> selectByExample(AppLogBrowseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	AppLogBrowse selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") AppLogBrowse record, @Param("example") AppLogBrowseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") AppLogBrowse record, @Param("example") AppLogBrowseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(AppLogBrowse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(AppLogBrowse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	AppLogBrowse selectOneByExample(AppLogBrowseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<AppLogBrowse> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") AppLogBrowseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	AppLogBrowse selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") AppLogBrowseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<AppLogBrowse> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(AppLogBrowse record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_browse
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	AppLogBrowse selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}