package com.redfinger.manager.common.mapper;

import com.redfinger.manager.common.domain.RfPadVersion;
import com.redfinger.manager.common.domain.RfPadVersionExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RfPadVersionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int countByExample(RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int deleteByExample(RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int insert(RfPadVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int insertSelective(RfPadVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    List<RfPadVersion> selectByExample(RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    RfPadVersion selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int updateByExampleSelective(@Param("record") RfPadVersion record, @Param("example") RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int updateByExample(@Param("record") RfPadVersion record, @Param("example") RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int updateByPrimaryKeySelective(RfPadVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int updateByPrimaryKey(RfPadVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    RfPadVersion selectOneByExample(RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    List<RfPadVersion> selectByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    RfPadVersion selectOneByExampleShowField(@Param("showField") List<String> showField, @Param("example") RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int insertBatch(List<RfPadVersion> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int updateByExampleSelectiveSync(@Param("record") RfPadVersion record, @Param("example") RfPadVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    int updateByPrimaryKeySelectiveSync(RfPadVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rf_pad_version
     *
     * @mbggenerated Thu Aug 18 11:48:17 CST 2016
     */
    RfPadVersion selectByPrimaryKeyShowField(@Param("showField") List<String> showField, @Param("id") Integer id);
    
    /**
     * 根据设备邦定启动器
     * @param map
     * @return
     */
    void insertByPad(Map<String,Object> map);
    
    /**
     * 根据启动器id删除
     * @param versionId
     * @return
     */
    int deleteByVersionId(Integer versionId);
}