package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.SysLoginLog;
import com.redfinger.manager.common.domain.SysLoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysLoginLogMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(SysLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(SysLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer logId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(SysLoginLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(SysLoginLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<SysLoginLog> selectByExample(SysLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysLoginLog selectByPrimaryKey(Integer logId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") SysLoginLog record, @Param("example") SysLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") SysLoginLog record, @Param("example") SysLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(SysLoginLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(SysLoginLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysLoginLog selectOneByExample(SysLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<SysLoginLog> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") SysLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysLoginLog selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") SysLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<SysLoginLog> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(SysLoginLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_login_log
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysLoginLog selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("logId") Integer logId);
}