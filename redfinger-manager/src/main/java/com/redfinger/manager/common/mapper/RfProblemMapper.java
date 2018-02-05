package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfProblem;
import com.redfinger.manager.common.domain.RfProblemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfProblemMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int countByExample(RfProblemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByExample(RfProblemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int deleteByPrimaryKey(Integer problemId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insert(RfProblem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertSelective(RfProblem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfProblem> selectByExample(RfProblemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfProblem selectByPrimaryKey(Integer problemId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExampleSelective(@Param("record") RfProblem record, @Param("example") RfProblemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByExample(@Param("record") RfProblem record, @Param("example") RfProblemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelective(RfProblem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKey(RfProblem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfProblem selectOneByExample(RfProblemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	List<RfProblem> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfProblemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfProblem selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfProblemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int insertBatch(List<RfProblem> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	int updateByPrimaryKeySelectiveSync(RfProblem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rf_problem
	 * @mbggenerated  Mon Dec 21 11:12:38 CST 2015
	 */
	RfProblem selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("problemId") Integer problemId);
}