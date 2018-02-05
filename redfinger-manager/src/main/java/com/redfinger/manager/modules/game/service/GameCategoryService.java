package com.redfinger.manager.modules.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.AppCategory;
import com.redfinger.manager.common.domain.AppCategoryExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.AppCategoryMapper;

@Transactional
@Service
@PrimaryKeyAnnotation(field="id")
public class GameCategoryService extends BaseService<AppCategory,AppCategoryExample,AppCategoryMapper>{
	
}
 