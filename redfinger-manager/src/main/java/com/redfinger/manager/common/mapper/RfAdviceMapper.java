package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfAdvice;
import com.redfinger.manager.common.domain.RfAdviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfAdviceMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(RfAdviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(RfAdviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer adviceId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(RfAdvice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(RfAdvice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfAdvice> selectByExample(RfAdviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfAdvice selectByPrimaryKey(Integer adviceId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") RfAdvice record, @Param("example") RfAdviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") RfAdvice record, @Param("example") RfAdviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(RfAdvice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(RfAdvice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfAdvice selectOneByExample(RfAdviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfAdvice> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfAdviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfAdvice selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfAdviceExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<RfAdvice> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(RfAdvice record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_advice
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfAdvice selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("adviceId") Integer adviceId);
}