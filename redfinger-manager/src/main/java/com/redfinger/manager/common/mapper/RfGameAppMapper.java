package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfGameApp;
import com.redfinger.manager.common.domain.RfGameAppExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RfGameAppMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int countByExample(RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int deleteByExample(RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int deleteByPrimaryKey(Integer gameAppId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int insert(RfGameApp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int insertSelective(RfGameApp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    List<RfGameApp> selectByExample(RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    RfGameApp selectByPrimaryKey(Integer gameAppId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int updateByExampleSelective(@Param("record") RfGameApp record, @Param("example") RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int updateByExample(@Param("record") RfGameApp record, @Param("example") RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int updateByPrimaryKeySelective(RfGameApp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int updateByPrimaryKey(RfGameApp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    RfGameApp selectOneByExample(RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    List<RfGameApp> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    RfGameApp selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int insertBatch(List<RfGameApp> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int updateByExampleSelectiveSync(@Param("record") RfGameApp record, @Param("example") RfGameAppExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    int updateByPrimaryKeySelectiveSync(RfGameApp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_game_app
     *
     * @mbggenerated Wed Feb 22 14:26:17 CST 2017
     */
    RfGameApp selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("gameAppId") Integer gameAppId);

	List<RfGameApp> selectByMap(Map<String, Object> params);
}