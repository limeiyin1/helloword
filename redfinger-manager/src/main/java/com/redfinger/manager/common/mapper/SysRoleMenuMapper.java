package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.SysRoleMenu;
import com.redfinger.manager.common.domain.SysRoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(SysRoleMenuExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(SysRoleMenuExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(SysRoleMenu record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(SysRoleMenu record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<SysRoleMenu> selectByExample(SysRoleMenuExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysRoleMenu selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") SysRoleMenu record, @Param("example") SysRoleMenuExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") SysRoleMenu record, @Param("example") SysRoleMenuExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(SysRoleMenu record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(SysRoleMenu record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysRoleMenu selectOneByExample(SysRoleMenuExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<SysRoleMenu> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") SysRoleMenuExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysRoleMenu selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") SysRoleMenuExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<SysRoleMenu> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(SysRoleMenu record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_role_menu
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	SysRoleMenu selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}