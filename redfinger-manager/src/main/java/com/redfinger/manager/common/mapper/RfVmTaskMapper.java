package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfVmTask;
import com.redfinger.manager.common.domain.RfVmTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfVmTaskMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int countByExample(RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int deleteByExample(RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int deleteByPrimaryKey(Integer vmTaskId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int insert(RfVmTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int insertSelective(RfVmTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	List<RfVmTask> selectByExample(RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	RfVmTask selectByPrimaryKey(Integer vmTaskId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByExampleSelective(@Param("record") RfVmTask record, @Param("example") RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByExample(@Param("record") RfVmTask record, @Param("example") RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByPrimaryKeySelective(RfVmTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByPrimaryKey(RfVmTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	RfVmTask selectOneByExample(RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	List<RfVmTask> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	RfVmTask selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int insertBatch(List<RfVmTask> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByExampleSelectiveSync(@Param("record") RfVmTask record, @Param("example") RfVmTaskExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByPrimaryKeySelectiveSync(RfVmTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	RfVmTask selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("vmTaskId") Integer vmTaskId);
}