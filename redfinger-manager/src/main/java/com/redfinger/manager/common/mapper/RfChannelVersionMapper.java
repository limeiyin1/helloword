package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfChannelVersion;
import com.redfinger.manager.common.domain.RfChannelVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfChannelVersionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int countByExample(RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int deleteByExample(RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int deleteByPrimaryKey(Integer channelVersionId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int insert(RfChannelVersion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int insertSelective(RfChannelVersion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	List<RfChannelVersion> selectByExample(RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	RfChannelVersion selectByPrimaryKey(Integer channelVersionId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int updateByExampleSelective(@Param("record") RfChannelVersion record,
			@Param("example") RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int updateByExample(@Param("record") RfChannelVersion record, @Param("example") RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int updateByPrimaryKeySelective(RfChannelVersion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int updateByPrimaryKey(RfChannelVersion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	RfChannelVersion selectOneByExample(RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	List<RfChannelVersion> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	RfChannelVersion selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int insertBatch(List<RfChannelVersion> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int updateByExampleSelectiveSync(@Param("record") RfChannelVersion record,
			@Param("example") RfChannelVersionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	int updateByPrimaryKeySelectiveSync(RfChannelVersion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_channel_version
	 * @mbggenerated  Mon Nov 14 17:36:05 CST 2016
	 */
	RfChannelVersion selectByPrimaryKeyShowField(@Param("showField") List<String> showField,
			@Param("channelVersionId") Integer channelVersionId);
}