package com.redfinger.manager.common.mapper;

import java.util.List;

import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.base.StatRealTimeDomain;

public interface StatRealtimeDataMapper {
	List<StatDomain> statRealtimeData(StatDomain bean);
	List<StatDomain> statRealtimeDataOnline(StatDomain bean);
	List<StatDomain> statRealtimeDataOffline(StatDomain bean);
	List<StatRealTimeDomain> statRealTimeDataDomain(StatDomain bean);
}
