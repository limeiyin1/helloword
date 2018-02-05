package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfCoupon;
import com.redfinger.manager.common.domain.RfCouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfCouponMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int countByExample(RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int deleteByExample(RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int deleteByPrimaryKey(Integer couponId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int insert(RfCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int insertSelective(RfCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    List<RfCoupon> selectByExample(RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    RfCoupon selectByPrimaryKey(Integer couponId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int updateByExampleSelective(@Param("record") RfCoupon record, @Param("example") RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int updateByExample(@Param("record") RfCoupon record, @Param("example") RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int updateByPrimaryKeySelective(RfCoupon record);
    
    int updateByTypeIdSelective(RfCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int updateByPrimaryKey(RfCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    RfCoupon selectOneByExample(RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    List<RfCoupon> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    RfCoupon selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int insertBatch(List<RfCoupon> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int updateByExampleSelectiveSync(@Param("record") RfCoupon record, @Param("example") RfCouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    int updateByPrimaryKeySelectiveSync(RfCoupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_coupon
     *
     * @mbggenerated Tue Oct 25 15:00:08 CST 2016
     */
    RfCoupon selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("couponId") Integer couponId);
}