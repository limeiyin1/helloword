package com.redfinger.manager.modules.base.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysDict;
import com.redfinger.manager.common.domain.SysDictExample;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.SysDictMapper;
@Transactional
@Service
@PrimaryKeyAnnotation(field = "dictCode")
public class DictService extends BaseService<SysDict, SysDictExample, SysDictMapper> {
	
	public List<String> getDictCategory(){
		List<SysDict> list = this.initQuery().addOrderClause("reorder", "asc").findAll();
		List<String> categoryList= Lists.newArrayList();
		for(SysDict dict: list){
			if(!categoryList.contains(dict.getDictCategory())){
				categoryList.add(dict.getDictCategory());
			}
		}
		return categoryList;
	}
	
	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, SysDict bean) {
		if(this.get(bean.getDictCode())!=null){
			return "字典key已存在，不能保存！";
		}
		return null;
	}
	
	@ServiceAnnotation(name = ServiceMethod.after_save)
	public String afterSave(HttpServletRequest request, SysDict bean) {
		DictHelper.initDict();
		return null;
	}
	@ServiceAnnotation(name = ServiceMethod.after_update)
	public String afterUpdate(HttpServletRequest request, SysDict bean) {
		DictHelper.initDict();
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.after_start)
	public String afterStart(HttpServletRequest request, SysDict bean) {
		DictHelper.initDict();
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.after_stop)
	public String afterStop(HttpServletRequest request, SysDict bean) {
		DictHelper.initDict();
		return null;
	}
	@ServiceAnnotation(name = ServiceMethod.after_delete)
	public String afterDelete(HttpServletRequest request, SysDict bean) {
		DictHelper.initDict();
		return null;
	}
}
