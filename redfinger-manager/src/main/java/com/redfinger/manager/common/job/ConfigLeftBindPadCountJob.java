package com.redfinger.manager.common.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redfinger.manager.modules.base.service.ConfigService;

@Component
public class ConfigLeftBindPadCountJob {

	@Autowired
	ConfigService configService;
	public void update(){
		try {
			configService.resetConfigLeftBindPadCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
