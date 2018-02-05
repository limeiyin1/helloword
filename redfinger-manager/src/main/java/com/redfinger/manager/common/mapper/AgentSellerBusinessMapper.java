package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.AgentSellerBusiness;
import com.redfinger.manager.common.domain.AgentSellerBusinessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentSellerBusinessMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int countByExample(AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int deleteByExample(AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int deleteByPrimaryKey(Integer businessId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int insert(AgentSellerBusiness record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int insertSelective(AgentSellerBusiness record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	List<AgentSellerBusiness> selectByExample(AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	AgentSellerBusiness selectByPrimaryKey(Integer businessId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int updateByExampleSelective(@Param("record") AgentSellerBusiness record,
			@Param("example") AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int updateByExample(@Param("record") AgentSellerBusiness record,
			@Param("example") AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int updateByPrimaryKeySelective(AgentSellerBusiness record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int updateByPrimaryKey(AgentSellerBusiness record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	AgentSellerBusiness selectOneByExample(AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	List<AgentSellerBusiness> selectByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	AgentSellerBusiness selectOneByExampleShowField(@Param("showField") List<String> showField,
			@Param("example") AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int insertBatch(List<AgentSellerBusiness> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int updateByExampleSelectiveSync(@Param("record") AgentSellerBusiness record,
			@Param("example") AgentSellerBusinessExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	int updateByPrimaryKeySelectiveSync(AgentSellerBusiness record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table agent_seller_business
	 * @mbggenerated  Wed Mar 09 15:04:57 CST 2016
	 */
	AgentSellerBusiness selectByPrimaryKeyShowField(@Param("showField") List<String> showField,
			@Param("businessId") Integer businessId);
}