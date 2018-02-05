package com.redfinger.manager.modules.market.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppApk;
import com.redfinger.manager.common.domain.AppApkExample;
import com.redfinger.manager.common.domain.AppRecommendApk;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.AppApkMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class MarketAppApkService extends BaseService<AppApk, AppApkExample, AppApkMapper>{
	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, AppRecommendApk bean) {
		AppApk apkId=this.initQuery().get(bean.getApkId());
		if(apkId!=null){
			return "该游戏已存在";
		}
		return null;
		
	}
}
