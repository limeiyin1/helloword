package com.redfinger.manager.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redfinger.manager.modules.stat.base.StatDomain;

public interface MyRfUserPadMapper {
	List<StatDomain> selectAll(@Param("common") int common,@Param("vip")int vip,@Param("svip")int svip);
}
