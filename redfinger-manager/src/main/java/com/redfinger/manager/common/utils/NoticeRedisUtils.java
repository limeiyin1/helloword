package com.redfinger.manager.common.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.constants.CacheRedisConstant;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfUserNotice;
import com.redfinger.manager.common.redis.CacheRedisService;

public class NoticeRedisUtils {
	
	/**
	 * 
	 * 添加公告
	 * @param cacheRedisService
	 * @param userNotices
	 * @param notice void
	 */
	public static void addNoticeRedis(CacheRedisService cacheRedisService, List<RfUserNotice> userNotices, RfNotice notice){
		if(notice == null){
			return;
		}
		if(!Collections3.isEmpty(userNotices)){
			
			Map<String, Object> noticeMap = Maps.newLinkedHashMap();
			noticeMap.put("noticeId",notice.getNoticeId());
			noticeMap.put("noticeTitle",notice.getNoticeTitle());
			noticeMap.put("noticeContent",notice.getNoticeContent());
			noticeMap.put("createTime",DateUtils.formatDateTime(notice.getCreateTime()));
			noticeMap.put("popStatus",notice.getPopStatus());
			noticeMap.put("userNoticeStatus","0");
			
			for (RfUserNotice userNotice : userNotices) {
			// 
			noticeMap.put("userNoticeId",userNotice.getUserNoticeId());
			// 在redis中查询该用户的公告信息
			String redisJsonData = cacheRedisService.get(CacheRedisConstant.USERNOTICEPAGE_MODULE, userNotice.getUserId());
				// redis中是否有该用户的公告信息
				if(StringUtils.isNotBlank(redisJsonData)){
					// 将json数据轮换为map
					@SuppressWarnings("unchecked")
					Map<String, Object> redisData = JsonUtils.stringToObject(redisJsonData, Map.class);
					
					if(redisData != null && redisData.size() > 0){
						// 取出map中所有的公告信息
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> noticeDtos = (List<Map<String, Object>>) redisData.get("noticeDtos");
						if(noticeDtos != null && noticeDtos.size() > 0){
							
							// 公告每页显示的记录数
							Integer pageSize = (Integer) redisData.get("pageSize");
							
							// 公告中未读消息数量
							if(redisData.containsKey("unread") && "0".equals(userNotice.getUserNoticeStatus())){
								Integer unread = (Integer) redisData.get("unread");
								/*Map<String, Object> lastNoticeDto = Collections3.getLast(noticeDtos);
								// 最后一条数据是否为未读
								if(!"0".equals(lastNoticeDto.get("userNoticeStatus")) || noticeDtos.size() < pageSize){
									redisData.put("unread", unread+1) ;
								}*/
								redisData.put("unread", unread+1);
							}
							// 插入最新的公告信息
							@SuppressWarnings("unchecked")
							List<Map<String, Object>> list = Lists.newArrayList(noticeMap);
							noticeDtos = Collections3.union(list,noticeDtos);
							// 如果公告数多于每页记录数, 则移除最后一条数据
							if(noticeDtos.size() >=pageSize){
								noticeDtos.remove(noticeDtos.size() - 1);
							}
							// 更新公告内容
							redisData.put("noticeDtos", noticeDtos);
						}
						
						// 公告总数
						if(redisData.containsKey("total")){
							Integer total = (Integer) redisData.get("total");
							redisData.put("total", total+1) ;
						}
						// 将修改后的公告信息更新到redis中
						cacheRedisService.set(CacheRedisConstant.USERNOTICEPAGE_MODULE, userNotice.getUserId(), JsonUtils.ObjectToString(redisData),CacheRedisConstant.USERNOTICEPAGE_REDIS_EXPIRE);
					}
					
				}
			}
		}
		
		
	}
	
	public static void addNoticeRedis(CacheRedisService cacheRedisService,RfUserNotice userNotice, RfNotice notice){
		
		List<RfUserNotice> userNotices = Lists.newArrayList(userNotice);
		
		NoticeRedisUtils.addNoticeRedis(cacheRedisService, userNotices, notice);
	}
	
	
	/**
	 * 
	 * 修改公告
	 * @param cacheRedisService
	 * @param userNotices
	 * @param notice void
	 */
	public static void updateNoticeRedis(CacheRedisService cacheRedisService, List<RfUserNotice> userNotices, RfNotice notice){
		
		if(notice == null){
			return;
		}
		
		Map<String, Object> noticeMap = Maps.newLinkedHashMap();
		/**公告id*/
		noticeMap.put("noticeId",notice.getNoticeId());
		/**公告标题*/
		noticeMap.put("noticeTitle",notice.getNoticeTitle());
		/**公告内容*/
		noticeMap.put("noticeContent",notice.getNoticeContent());
		/**创建时间*/
		noticeMap.put("createTime",DateUtils.formatDateTime(notice.getCreateTime()));
		/**是否强制弹出*/
		noticeMap.put("popStatus",notice.getPopStatus());
		
		if(userNotices != null && userNotices.size() > 0){
			
			for (RfUserNotice userNotice : userNotices) {
				
				/**公告id*/
				noticeMap.put("userNoticeId",userNotice.getUserNoticeId());
				/**公告状态 */
				noticeMap.put("userNoticeStatus",userNotice.getUserNoticeStatus());
			
				// 在redis中查询该用户的公告信息
				String redisJsonData = cacheRedisService.get(CacheRedisConstant.USERNOTICEPAGE_MODULE, userNotice.getUserId());
					/** redis中是否有该用户的公告信息*/
					if(StringUtils.isNotBlank(redisJsonData)){
						// 将json数据轮换为map
						@SuppressWarnings("unchecked")
						Map<String, Object> redisData = JsonUtils.stringToObject(redisJsonData, Map.class);
						
						if(redisData != null && redisData.size() > 0){
							// 取出map中所有的公告信息
							@SuppressWarnings("unchecked")
							List<Map<String, Object>> noticeDtos = (List<Map<String, Object>>) redisData.get("noticeDtos");
							if(noticeDtos != null && noticeDtos.size() > 0){
								for (Map<String, Object> dto : noticeDtos) {
									if(dto != null && dto.get("noticeId")!= null && noticeMap.get("noticeId") != null){
										if(dto.get("noticeId") == noticeMap.get("noticeId")){
											BeanUtils.copyProperties(noticeMap, dto);
										}
									}
								}
							}
							// 将修改后的公告信息更新到redis中
							cacheRedisService.set(CacheRedisConstant.USERNOTICEPAGE_MODULE, userNotice.getUserId(), JsonUtils.ObjectToString(redisData),CacheRedisConstant.USERNOTICEPAGE_REDIS_EXPIRE);
						}
						
					}
			}
			
		}
	}
	
}
