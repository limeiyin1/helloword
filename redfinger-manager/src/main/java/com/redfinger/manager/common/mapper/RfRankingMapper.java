package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfRanking;
import com.redfinger.manager.common.domain.RfRankingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfRankingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int countByExample(RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int deleteByExample(RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int deleteByPrimaryKey(Integer rankingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int insert(RfRanking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int insertSelective(RfRanking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    List<RfRanking> selectByExample(RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    RfRanking selectByPrimaryKey(Integer rankingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int updateByExampleSelective(@Param("record") RfRanking record, @Param("example") RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int updateByExample(@Param("record") RfRanking record, @Param("example") RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int updateByPrimaryKeySelective(RfRanking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int updateByPrimaryKey(RfRanking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    RfRanking selectOneByExample(RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    List<RfRanking> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    RfRanking selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int insertBatch(List<RfRanking> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int updateByExampleSelectiveSync(@Param("record") RfRanking record, @Param("example") RfRankingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    int updateByPrimaryKeySelectiveSync(RfRanking record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_ranking
     *
     * @mbggenerated Wed Mar 01 10:10:38 CST 2017
     */
    RfRanking selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("rankingId") Integer rankingId);
}