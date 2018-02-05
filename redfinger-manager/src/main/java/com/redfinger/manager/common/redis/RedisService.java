package com.redfinger.manager.common.redis;

public interface RedisService {
	
    /**
     * 通过key删除
     * @param key
     */
    public void del(String key);
    
    /**
     * 通过模式删除
     * @param key
     */
    public void delByPattern(String pattern);

    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key,String value,int liveTime);
    
    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(Integer key,String value,int liveTime);
    
    /**
     * 添加key value
     * @param key
     * @param value
     */
    public void set(String key,String value);

    /**
     * 获取redis value (String)
     * @param key
     * @return
     */
    public String get(String key);
    
    /**
     * 获取redis value (String)
     * @param key
     * @return
     */
    public String get(Integer key);
    
    /**
     * 
     * 获取redis 到期时间
     * @param key
     * @return Long
     */
    public Long getExpire(String key);
    

    
}
