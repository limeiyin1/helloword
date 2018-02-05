package com.redfinger.manager.common.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.redfinger.manager.modules.base.service.ConfigService;

@Service
public class ConfigChannelBindPadCountJob {
	
		@Autowired
		ConfigService configService;
		@Scheduled(cron = "0 0 0/1 * * ?")  
		public void update(){
			try {
				configService.resetConfigChannelBindPadCount();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
