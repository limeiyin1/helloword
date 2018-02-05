package com.redfinger.manager.common.redis.impl;

import com.redfinger.manager.common.redis.CacheRedisService;
import com.redfinger.manager.common.redis.RedisService;

public class CacheRedisServiceImpl implements CacheRedisService{
	
	private RedisService redisService;

	/**
	 * 
	 * 获取redis value
	 * @param module 模块名称
	 * @param key 
	 * @return 
	 */
	public String get(String module, String key) {
		return redisService.get(module+"_"+key);
	}

	/**
	 * 
	 * 获取redis value
	 * @param module 模块名称
	 * @param key 
	 * @return 
	 */
	public String get(String module, Integer key) {
		return redisService.get(module+"_"+key);
	}
	
	/**
	 * 
	 * 添加key value 并且设置存活时间
	 * @param module 模块名称
	 * @param key redis中的key
	 * @param value 添加的值
	 * @param liveTime 存活时间
	 */
	public void set(String module, String key,String value,int liveTime) {
		redisService.set(module+"_"+key, value, liveTime);
	}
	
    
	/**
	 * 
	 * 添加key value 并且设置存活时间
	 * @param module 模块名称
	 * @param key redis中的key
	 * @param value 添加的值
	 * @param liveTime 存活时间
	 */
    public void set(String module, Integer key,String value,int liveTime){
    	redisService.set(module+"_"+key, value, liveTime);
    }
    
    /**
     * 
     * 添加key value
     * @param module 模块名称
     * @param key
     * @param value 
     */
    public void set(String module, String key,String value){
    	redisService.set(module+"_"+key, value);
    }

    /**
     * 
     * 获取存活时间
     * @param module 模块名称
     * @param key
     * @return Long
     */
    public Long getExpire(String module, String key){
    	return redisService.getExpire(module+"_"+key);
    }

    /**
     * 
     * 删除key
     * @param module 模块名称
     * @param key 
     */
    public void del(String module, String key){
    	redisService.del(module+"_"+key);
    }
    
    /**
     * 
     * 删除指定模块下的所有数据
     * @param module 
     */
	public void delByModule(String module) {
    	redisService.delByPattern(module+"*");
	}

    
	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}


    
}
