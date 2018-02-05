package com.redfinger.manager.common.utils;

public class NumberUtils {
	
	/**
	 * 除以60
	 * @param time
	 * @return
	 */
	public static Integer dividedTime(Integer time){
		if(null != time){
			return time/60;
		}
		return 0;
	}
	
	/**
	 * 乘以60
	 * @param time
	 * @return
	 */
	public static Integer multiplyTime(Integer time){
		if(null != time){
			return time*60;
		}
		return 0;
	}

}
