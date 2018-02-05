package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.AppLogDownload;
import com.redfinger.manager.common.domain.AppLogDownloadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppLogDownloadMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(AppLogDownloadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(AppLogDownloadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(AppLogDownload record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(AppLogDownload record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<AppLogDownload> selectByExample(AppLogDownloadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	AppLogDownload selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") AppLogDownload record, @Param("example") AppLogDownloadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") AppLogDownload record, @Param("example") AppLogDownloadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(AppLogDownload record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(AppLogDownload record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	AppLogDownload selectOneByExample(AppLogDownloadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<AppLogDownload> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") AppLogDownloadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	AppLogDownload selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") AppLogDownloadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<AppLogDownload> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(AppLogDownload record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table app_log_download
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	AppLogDownload selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}