package com.redfinger.manager.modules.tools.job;

import java.util.List;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.ToolsMarketUpdate;
import com.redfinger.manager.common.helper.SpringContextHolder;
import com.redfinger.manager.modules.tools.service.ToolsMarketUpdateScanService;

public class AutoScanJob {

	public void doJob() {
		ToolsMarketUpdateScanService service =SpringContextHolder.getBean(ToolsMarketUpdateScanService.class);
		List<ToolsMarketUpdate> list=service.initQuery().findAll();
		List<String> idsList=Lists.newArrayList();
		for(ToolsMarketUpdate bean:list){
			if(bean.getEnableStatus().equals(ConstantsDb.globalEnableStatusNomarl())){
				idsList.add(String.valueOf(bean.getId()));
			}
		}
		service.scan(idsList);
	}
}
