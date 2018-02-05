package com.redfinger.manager.modules.game.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppCategory;
import com.redfinger.manager.common.domain.AppCategoryApk;
import com.redfinger.manager.common.domain.AppCategoryExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.AppCategoryMapper;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class GameAppcategoryService extends BaseService<AppCategory, AppCategoryExample, AppCategoryMapper>{
	
	@ServiceAnnotation(name = ServiceMethod.pre_save)
	
	public String preSave(HttpServletRequest request, AppCategoryApk bean) {
		AppCategory categoryId=this.initQuery().get(bean.getApkId());
		if(categoryId!=null){
			return "该分类已存在!";
		}
		return null;
		
	}
}
