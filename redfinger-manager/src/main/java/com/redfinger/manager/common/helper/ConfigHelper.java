package com.redfinger.manager.common.helper;

import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.modules.base.service.ConfigService;


public class ConfigHelper {

	public static SysConfig getConfigByCode(String code){
		ConfigService service = SpringContextHolder.getBean(ConfigService.class);
		return service.get(code);
	}
	
	public static String getValueByCode(String code){
		return getConfigByCode(code).getConfigValue();
	}
	
	
}
