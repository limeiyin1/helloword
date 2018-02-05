package com.redfinger.manager.common.gather.mapper;

import com.redfinger.manager.common.gather.domain.RfClientNetinfo;
import com.redfinger.manager.common.gather.domain.RfClientNetinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RfClientNetinfoMapper {
    long countByExample(RfClientNetinfoExample example);

    int deleteByExample(RfClientNetinfoExample example);

    int deleteByPrimaryKey(Integer netinfoId);

    int insert(RfClientNetinfo record);

    int insertSelective(RfClientNetinfo record);

    List<RfClientNetinfo> selectByExample(RfClientNetinfoExample example);

    RfClientNetinfo selectByPrimaryKey(Integer netinfoId);

    int updateByExampleSelective(@Param("record") RfClientNetinfo record, @Param("example") RfClientNetinfoExample example);

    int updateByExample(@Param("record") RfClientNetinfo record, @Param("example") RfClientNetinfoExample example);

    int updateByPrimaryKeySelective(RfClientNetinfo record);

    int updateByPrimaryKey(RfClientNetinfo record);
}