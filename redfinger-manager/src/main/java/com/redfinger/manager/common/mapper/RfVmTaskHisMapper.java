package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfVmTaskHis;
import com.redfinger.manager.common.domain.RfVmTaskHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfVmTaskHisMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int countByExample(RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int deleteByExample(RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int deleteByPrimaryKey(Integer vmTaskId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int insert(RfVmTaskHis record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int insertSelective(RfVmTaskHis record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	List<RfVmTaskHis> selectByExample(RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	RfVmTaskHis selectByPrimaryKey(Integer vmTaskId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByExampleSelective(@Param("record") RfVmTaskHis record, @Param("example") RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByExample(@Param("record") RfVmTaskHis record, @Param("example") RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByPrimaryKeySelective(RfVmTaskHis record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByPrimaryKey(RfVmTaskHis record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	RfVmTaskHis selectOneByExample(RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	List<RfVmTaskHis> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	RfVmTaskHis selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int insertBatch(List<RfVmTaskHis> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByExampleSelectiveSync(@Param("record") RfVmTaskHis record, @Param("example") RfVmTaskHisExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	int updateByPrimaryKeySelectiveSync(RfVmTaskHis record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_vm_task_his
	 * @mbggenerated  Thu Mar 31 19:45:02 CST 2016
	 */
	RfVmTaskHis selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("vmTaskId") Integer vmTaskId);
}