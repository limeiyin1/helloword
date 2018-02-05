package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.AgentConsult;
import com.redfinger.manager.common.domain.AgentConsultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentConsultMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int countByExample(AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int deleteByExample(AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int deleteByPrimaryKey(Integer consultId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int insert(AgentConsult record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int insertSelective(AgentConsult record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	List<AgentConsult> selectByExample(AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	AgentConsult selectByPrimaryKey(Integer consultId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int updateByExampleSelective(@Param("record") AgentConsult record, @Param("example") AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int updateByExample(@Param("record") AgentConsult record, @Param("example") AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int updateByPrimaryKeySelective(AgentConsult record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int updateByPrimaryKey(AgentConsult record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	AgentConsult selectOneByExample(AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	List<AgentConsult> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	AgentConsult selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int insertBatch(List<AgentConsult> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int updateByExampleSelectiveSync(@Param("record") AgentConsult record, @Param("example") AgentConsultExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	int updateByPrimaryKeySelectiveSync(AgentConsult record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_consult
	 * @mbggenerated  Wed Mar 09 15:12:30 CST 2016
	 */
	AgentConsult selectByPrimaryKeyShowField(@Param("showField") List<String> showField,
			@Param("consultId") Integer consultId);
}