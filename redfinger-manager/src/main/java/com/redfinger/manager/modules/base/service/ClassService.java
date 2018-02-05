package com.redfinger.manager.modules.base.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.base.TreeDomain;
import com.redfinger.manager.common.domain.RfClass;
import com.redfinger.manager.common.domain.RfClassExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.RfClassMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.ListSortUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "classId")
public class ClassService extends BaseService<RfClass, RfClassExample, RfClassMapper> {

	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, RfClass bean) {
		if(bean.getClassPid()!=null && !bean.getClassPid().equals(0)){
			RfClass parent=this.get(bean.getClassPid());
			bean.setClassType(parent.getClassType());
		}
		return null;
	}
	
	public String getFullClass(String type,Integer classId,String sepereate){
		if(StringUtils.isEmpty(type) || classId==null){
			return null;
		}
		List<RfClass> categoryList = this.queryAllByType(type);
		RfClass category = this.get(classId);
		List<RfClass> tempList = Lists.newArrayList();
		ListSortUtils.getParentList(tempList, categoryList, category, "classId", "classPid");
		return Collections3.extractToString(tempList, "className", " - ");
	}
	
	public List<RfClass> queryAllByType(String type){
		return this.initQuery().andEqualTo("classType", type).addOrderClause("reorder", "asc").findAll();
	}
	
	public String getStopTrueTreeAndTop(String type){
		List<RfClass> categoryList = this.initQuery().andEqualTo("classType", type).addOrderClause("reorder", "asc").findStopTrue();
		TreeDomain treeDomain = ListSortUtils.sortListToTree(categoryList, "classId", "classPid", "className");
		return JsonUtils.ObjectToString(Lists.newArrayList(treeDomain));
	}
	
	public String getStopTrueTree(String type){
		List<RfClass> categoryList = this.initQuery().andEqualTo("classType", type).addOrderClause("reorder", "asc").findStopTrue();
		TreeDomain treeDomain = ListSortUtils.sortListToTree(categoryList, "classId", "classPid", "className");
		return JsonUtils.ObjectToString(Lists.newArrayList(treeDomain.getChildren()));
	}
	
	public List<?> getChildAndSelfIds(String type,Integer classId){
		if(StringUtils.isEmpty(type) || classId==null || classId.equals(0)){
			return null;
		}
		List<RfClass> categoryList = this.queryAllByType(type);
		if(!Collections3.isEmpty(categoryList)){
			RfClass category = this.get(classId);
			List<RfClass> childList = Lists.newArrayList();
			ListSortUtils.getChildList(childList, categoryList, category, "classId", "classPid");
			return Collections3.extractToList(childList, "classId");
		}else{
			return null;
		}
	}
	
	public String getGameClassTree(String type,String tools){
		List<String> list=Lists.newArrayList();
		list.add(type);
		list.add(tools);
		List<RfClass> categoryList = this.initQuery().andIn("classType", list).addOrderClause("reorder", "asc").findStopTrue();
		TreeDomain treeDomain = ListSortUtils.sortListToTree(categoryList, "classId", "classPid", "className");
		return JsonUtils.ObjectToString(Lists.newArrayList(treeDomain.getChildren()));
	}
	
	public List<RfClass> getGameClassList(String type,String tools){
		List<String> list=Lists.newArrayList();
		list.add(type);
		list.add(tools);
		List<RfClass> categoryList = this.initQuery().andIn("classType", list).addOrderClause("reorder", "asc").findStopTrue();
		return categoryList;
	}
}
