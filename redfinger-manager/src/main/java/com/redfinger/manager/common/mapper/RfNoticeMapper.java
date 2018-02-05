package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfNoticeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(RfNoticeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(RfNoticeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer noticeId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(RfNotice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(RfNotice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfNotice> selectByExample(RfNoticeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfNotice selectByPrimaryKey(Integer noticeId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") RfNotice record, @Param("example") RfNoticeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") RfNotice record, @Param("example") RfNoticeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(RfNotice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(RfNotice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfNotice selectOneByExample(RfNoticeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfNotice> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfNoticeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfNotice selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfNoticeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<RfNotice> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(RfNotice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_notice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfNotice selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("noticeId") Integer noticeId);
}