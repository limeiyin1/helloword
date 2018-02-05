/*
 * com.toone.finger.controller.redis  2015-4-30
 *
 * Copyright 2010 Shenzhen TYDIC Information Technology Co.,Ltd.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.redfinger.manager.common.redis.impl;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.redfinger.manager.common.redis.RedisService;

/** 
 * @ClassName: RedisService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Carson yuchanghong@tydic.com 
 * @date 2015-4-30 下午4:11:36 
 *  
 */
public class RedisServiceImpl implements RedisService{
	
    private StringRedisTemplate redisTemplate;
    
    /**
     * 通过key删除
     * @param key
     */
    public void del(String key){
    	redisTemplate.delete(key);
    }
    
    /**
     * 通过模式删除
     * @param pattern
     */
	public void delByPattern(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		redisTemplate.delete(keys);
	}

    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key,String value,int liveTime){
    	redisTemplate.opsForValue().set(key, value, liveTime, TimeUnit.SECONDS);
    }
    
    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(Integer key,String value,int liveTime){
    	redisTemplate.opsForValue().set(key + "", value, liveTime, TimeUnit.SECONDS);
    }
    
    /**
     * 添加key value
     * @param key
     * @param value
     */
    public void set(String key,String value){
    	redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取redis value (String)
     * @param key
     * @return
     */
    public String get(String key){
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }
    
    /**
     * 获取redis value (String)
     * @param key
     * @return
     */
    public String get(Integer key){
        String value = redisTemplate.opsForValue().get(key + "");
        return value;
    }
    
    public Long getExpire(String key){
    	return redisTemplate.getExpire(key);
    }
    
    private RedisServiceImpl (){

    }

	/** 
	 * @return redisTemplate 
	 */
	public StringRedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	/** 
	 * @param redisTemplate 要设置的 redisTemplate 
	 */
	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

    
}
