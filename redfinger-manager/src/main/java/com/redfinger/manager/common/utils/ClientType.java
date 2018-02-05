/**
 *
 * com.toone.web.goldfinger.commons.define  2015-9-23
 *
 */
package com.redfinger.manager.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * @ClassName: PayOsType 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson ylrainbow82@gmail.com
 * @date 2015-9-23 下午7:44:38 
 *  
 */
public class ClientType {
	private static final String DICT_CODE = "CLIENT_TYPE";//对应数据库表SYS_DICT的DICT_CODE值
	/** 电脑端 */
	public static final String PC = "win";
	/** android */
	public static final String ANDROID = "android";
	/** IOS */
	public static final String IOS = "ios";
	/** win_st */
	public static final String WIN_ST = "win_st";
	/** 官网 */
	public static final String WEBSIT = "websit";
	/** 重新发送 */
	public static final String RESEND = "resed";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(PC, "电脑端");
		DICT_MAP.put(ANDROID, "安卓端");
		DICT_MAP.put(IOS, "苹果端");
		DICT_MAP.put(WIN_ST, "超级多开端");
		DICT_MAP.put(WEBSIT, "官网端");
		DICT_MAP.put(RESEND, "重新发送");
   }
}
