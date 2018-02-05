package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.TaskGameCheck;
import com.redfinger.manager.common.domain.TaskGameCheckExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskGameCheckMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int countByExample(TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int deleteByExample(TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int deleteByPrimaryKey(Integer checkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int insert(TaskGameCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int insertSelective(TaskGameCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    List<TaskGameCheck> selectByExample(TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    TaskGameCheck selectByPrimaryKey(Integer checkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int updateByExampleSelective(@Param("record") TaskGameCheck record, @Param("example") TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int updateByExample(@Param("record") TaskGameCheck record, @Param("example") TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int updateByPrimaryKeySelective(TaskGameCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int updateByPrimaryKey(TaskGameCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    TaskGameCheck selectOneByExample(TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    List<TaskGameCheck> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    TaskGameCheck selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int insertBatch(List<TaskGameCheck> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int updateByExampleSelectiveSync(@Param("record") TaskGameCheck record, @Param("example") TaskGameCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    int updateByPrimaryKeySelectiveSync(TaskGameCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_game_check
     *
     * @mbggenerated Tue May 31 14:37:57 CST 2016
     */
    TaskGameCheck selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("checkId") Integer checkId);
}