package com.redfinger.manager.modules.game.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppRecommend;
import com.redfinger.manager.common.domain.AppRecommendApk;
import com.redfinger.manager.common.domain.AppRecommendExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;

import com.redfinger.manager.common.mapper.AppRecommendMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GameApprecommendService extends BaseService<AppRecommend, AppRecommendExample, AppRecommendMapper>{
	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, AppRecommendApk bean) {
		AppRecommend recommendId=this.initQuery().get(bean.getId());
		if(recommendId!=null){
			return "该推荐已存在";
		}
		return null;
		
	}
}
