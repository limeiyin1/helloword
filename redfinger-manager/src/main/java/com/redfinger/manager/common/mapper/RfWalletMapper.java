package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfWallet;
import com.redfinger.manager.common.domain.RfWalletExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfWalletMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int countByExample(RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int deleteByExample(RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int deleteByPrimaryKey(Integer walletId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int insert(RfWallet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int insertSelective(RfWallet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    List<RfWallet> selectByExample(RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    RfWallet selectByPrimaryKey(Integer walletId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int updateByExampleSelective(@Param("record") RfWallet record, @Param("example") RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int updateByExample(@Param("record") RfWallet record, @Param("example") RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int updateByPrimaryKeySelective(RfWallet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int updateByPrimaryKey(RfWallet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    RfWallet selectOneByExample(RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    List<RfWallet> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    RfWallet selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int insertBatch(List<RfWallet> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int updateByExampleSelectiveSync(@Param("record") RfWallet record, @Param("example") RfWalletExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    int updateByPrimaryKeySelectiveSync(RfWallet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_wallet
     *
     * @mbggenerated Wed Mar 22 11:02:30 CST 2017
     */
    RfWallet selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("walletId") Integer walletId);
}