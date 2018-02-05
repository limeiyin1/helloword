package com.redfinger.manager.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class SearchType {
	/** 查询类型 */
	public static final String IP_SEARCH_TYPE = "ipSearchType";
	/** 模糊查询 */
	public static final String FUZZY = "1";
	/** 精准查询 */
	public static final String ACCURATE = "2";
	
	
	/** 键值对象 */
	public static Map<String, String> IP_SEARCH_TYPE_MAP = new LinkedHashMap<String, String>();
   
	static {
		IP_SEARCH_TYPE_MAP.put(FUZZY, "模糊查询");
		IP_SEARCH_TYPE_MAP.put(ACCURATE, "精准查询");
    }
}
