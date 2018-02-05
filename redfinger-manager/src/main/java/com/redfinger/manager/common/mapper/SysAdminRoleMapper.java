package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.SysAdminRole;
import com.redfinger.manager.common.domain.SysAdminRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysAdminRoleMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(SysAdminRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(SysAdminRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(SysAdminRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(SysAdminRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<SysAdminRole> selectByExample(SysAdminRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysAdminRole selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") SysAdminRole record, @Param("example") SysAdminRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") SysAdminRole record, @Param("example") SysAdminRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(SysAdminRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(SysAdminRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysAdminRole selectOneByExample(SysAdminRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<SysAdminRole> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") SysAdminRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysAdminRole selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") SysAdminRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<SysAdminRole> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(SysAdminRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_admin_role
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysAdminRole selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}