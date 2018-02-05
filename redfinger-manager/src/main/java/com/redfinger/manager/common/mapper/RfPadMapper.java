package com.redfinger.manager.common.mapper;

import com.github.pagehelper.Page;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadExample;
import com.redfinger.manager.modules.facility.dto.UserPadDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RfPadMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int countByExample(RfPadExample example);
	
	/**
	 * 根据条件查询设备个数
	 * @param map
	 * @return
	 */
	int countByMap(Map<String,Object> map);
	
	/**
	 * 根据条件查询可绑定设备
	 * @param map
	 * @return
	 */
	RfPad selectByMap(Map<String,Object> map);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int deleteByExample(RfPadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int deleteByPrimaryKey(Integer padId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int insert(RfPad record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int insertSelective(RfPad record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	List<RfPad> selectByExample(RfPadExample example);
	
	List<UserPadDto> selectPadByUserId(Map<String,Object> map);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	RfPad selectByPrimaryKey(Integer padId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int updateByExampleSelective(@Param("record") RfPad record, @Param("example") RfPadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int updateByExample(@Param("record") RfPad record, @Param("example") RfPadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int updateByPrimaryKeySelective(RfPad record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int updateByPrimaryKey(RfPad record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	RfPad selectOneByExample(RfPadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	List<RfPad> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfPadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	RfPad selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfPadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int insertBatch(List<RfPad> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int updateByExampleSelectiveSync(@Param("record") RfPad record, @Param("example") RfPadExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	int updateByPrimaryKeySelectiveSync(RfPad record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_pad
	 * @mbggenerated  Fri Dec 16 15:43:28 CST 2016
	 */
	RfPad selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("padId") Integer padId);

	int updateByPadCode(RfPad record);

	RfPad selectPadLock(Map<String,Object> map);

	/**
	 * 根据条件查询
	 * @param map
	 * @return
	 */
	Page<RfPad> seleteByParamsMap(Map<String, Object> map);

	/**
	 * 根据条件查询总数
	 * @param params
	 * @return
	 */
	Integer seleteByParamsCount(Map<String, Object> params);
	
	/**
	 * 根据机房ID查出所有的设备ID
	 * @param idcId
	 * @return
	 */
	List<Integer> selectDeviceIdByIdcId(int idcId);
	
	/**
	 * 
	 * 根据Id查询设备, 并且锁定该行数据
	 * @param padId
	 * @return RfPad
	 */
	RfPad selectByPrimaryKeyForUpdate(Integer padId);
}