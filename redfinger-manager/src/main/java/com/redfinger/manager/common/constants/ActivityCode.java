/**
 *
 * com.toone.web.goldfinger.commons.define  2015-9-5
 *
 */
package com.redfinger.manager.common.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/** 
 * @ClassName: TaskStatus 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson ylrainbow82@gmail.com
 * @date 2015-9-5 上午10:27:55 
 *  
 */
public class ActivityCode {
	/** 红豆活动 */
	public static final String RBC = "rbc";
	/** 微信抽奖 */
	public static final String WECHART_LOTTERY = "wechartLottery";
	/** 套餐活动 */
	public static final String GOODS = "goods";
	/** 优惠券活动 */
	public static final String COUPON = "coupon";
	/** 超级VIP活动 */
	public static final String SVIP = "svip";
	/**赠送设备时长-给购买人赠送*/
	public static final String TIME = "time";
	/**钱包充值*/
	public static final String WALLET_RECHARGE = "wallet_recharge";
	/**邀请购买赠送-给邀请人赠送*/
	public static final String INVITE_TIME = "invite_time";
	
	/** 键值对象 */
	public static Map<String, String> DICT_MAP = new LinkedHashMap<String, String>();
   
	static {
		DICT_MAP.put(RBC, "红豆活动");
		DICT_MAP.put(WECHART_LOTTERY, "微信抽奖");
		DICT_MAP.put(GOODS, "套餐活动");
		DICT_MAP.put(COUPON, "优惠券活动");
		DICT_MAP.put(SVIP, "超级VIP活动");
		DICT_MAP.put(TIME, "赠送设备时长");
		DICT_MAP.put(WALLET_RECHARGE, "钱包充值");
		DICT_MAP.put(INVITE_TIME, "邀请购买设备");
   }
}
