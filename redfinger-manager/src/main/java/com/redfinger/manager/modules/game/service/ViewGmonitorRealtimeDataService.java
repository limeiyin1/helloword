package com.redfinger.manager.modules.game.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.RfGameMonitorCfg;
import com.redfinger.manager.common.domain.ViewGmonitorRealtimeData;
import com.redfinger.manager.common.domain.ViewGmonitorRealtimeDataExample;
import com.redfinger.manager.common.mapper.StatRealtimeDataMapper;
import com.redfinger.manager.common.mapper.ViewGmonitorRealtimeDataMapper;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.base.StatRealTimeDomain;

@Transactional
@Service
public class ViewGmonitorRealtimeDataService extends BaseService<ViewGmonitorRealtimeData, ViewGmonitorRealtimeDataExample, ViewGmonitorRealtimeDataMapper> {

@Autowired
StatRealtimeDataMapper mapper;
@Autowired
GameMonitorService gameMonitorService;

	public Map<String, Object> stat(StatDomain bean) {
		 Map<String, Object> map = Maps.newLinkedHashMap();
		 List<StatDomain> list=mapper.statRealtimeData(bean);
		 List<Object>offline=Lists.newArrayList();
		 List<Object>online=Lists.newArrayList();
		 List<String>gameList=Lists.newArrayList();
		 for (StatDomain statDomain : list) {
			RfGameMonitorCfg game=   gameMonitorService.get(statDomain.getId());
			if(null!=game&&null!=game.getGameName()){
			bean.setWhere("x.game_id="+statDomain.getId());
			List<StatDomain>offlineList=mapper.statRealtimeDataOffline(bean);
			List<StatDomain>onlineList=mapper.statRealtimeDataOnline(bean);
			if(offlineList.size()>0){
				offline.add(offlineList.get(0).getNumber());
			}else{
				offline.add(0);
			}
			if(onlineList.size()>0){
				online.add(onlineList.get(0).getNumber());
			}else{
				online.add(0);
			}
	      gameList.add(game.getGameName());
		}}
		    map.put("label", gameList);
            map.put("number", online);
            map.put("number1", offline);
		return map;
	}

	public List<StatRealTimeDomain> statRealTime(StatDomain bean) {
		 List<StatRealTimeDomain> list=mapper.statRealTimeDataDomain(bean);
		return list;
	}
}
