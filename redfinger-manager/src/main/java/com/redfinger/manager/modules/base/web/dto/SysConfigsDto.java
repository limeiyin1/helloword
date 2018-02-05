package com.redfinger.manager.modules.base.web.dto;

import java.util.List;

import com.redfinger.manager.common.domain.SysConfig;

public class SysConfigsDto {
	List<SysConfig> sysConfigs;

	public List<SysConfig> getSysConfigs() {
		return sysConfigs;
	}

	public void setSysConfigs(List<SysConfig> sysConfigs) {
		this.sysConfigs = sysConfigs;
	}
	
}
