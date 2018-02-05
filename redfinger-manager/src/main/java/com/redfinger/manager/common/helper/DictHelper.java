package com.redfinger.manager.common.helper;

import java.util.List;

import com.redfinger.manager.common.domain.SysDict;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.modules.base.service.DictService;

public class DictHelper {

	public static synchronized void initDict() {
		DataInitProcessor.dictCategoryMap.clear();
		DataInitProcessor.dictKeyMap.clear();
		DictService dictService = SpringContextHolder.getBean(DictService.class);
		List<String> categoryList = dictService.getDictCategory();
		for (String category : categoryList) {
			List<SysDict> list = dictService.initQuery().andEqualTo("dictCategory", category).addOrderClause("reorder", "asc").findAll();
			DataInitProcessor.dictCategoryMap.put(category, list);
			for (SysDict dict : list) {
				DataInitProcessor.dictKeyMap.put(dict.getDictCode(), dict);
			}
		}
	}

	public static SysDict getDictByKey(String key) {
		return DataInitProcessor.dictKeyMap.get(key);
	}

	public static String getDictValueByKey(String key) {
		return getDictByKey(key).getDictValue();
	}

	public static List<SysDict> getDictListByCategory(String category) {
		return DataInitProcessor.dictCategoryMap.get(category);
	}

	public static String getLabelStyle(String category, String value) {
		List<SysDict> list = getDictListByCategory(category);
		if(!Collections3.isEmpty(list)){
			for (SysDict dict : list) {
				if (dict.getDictValue().equals(value)) {
					return "<span class=\"label label-" + dict.getThemes() + "\">" + dict.getDictName() + "</span>";
				}
			}
		}
		return "";
	}

	public static String getLabel(String category, String value) {
		List<SysDict> list = getDictListByCategory(category);
		if(!Collections3.isEmpty(list)){
			for (SysDict dict : list) {
				if (dict.getDictValue().equals(value)) {
					return dict.getDictName();
				}
			}
		}
		return null;
	}

}
