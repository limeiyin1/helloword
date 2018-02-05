package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.ShopPackageCode;
import com.redfinger.manager.common.domain.ShopPackageCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopPackageCodeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int countByExample(ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int deleteByExample(ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int insert(ShopPackageCode record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int insertSelective(ShopPackageCode record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	List<ShopPackageCode> selectByExample(ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	ShopPackageCode selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int updateByExampleSelective(@Param("record") ShopPackageCode record, @Param("example") ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int updateByExample(@Param("record") ShopPackageCode record, @Param("example") ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int updateByPrimaryKeySelective(ShopPackageCode record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int updateByPrimaryKey(ShopPackageCode record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	ShopPackageCode selectOneByExample(ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	List<ShopPackageCode> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	ShopPackageCode selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int insertBatch(List<ShopPackageCode> list);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int updateByExampleSelectiveSync(@Param("record") ShopPackageCode record, @Param("example") ShopPackageCodeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	int updateByPrimaryKeySelectiveSync(ShopPackageCode record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table shop_package_code
	 * @mbggenerated  Thu Jan 14 11:51:59 CST 2016
	 */
	ShopPackageCode selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
}