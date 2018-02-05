package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

public class ChannelServiceTest {
	private String host = "http://10.254.0.155:8180/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	@SuppressWarnings("unchecked")
	public void screenTest() {
		//******************************输入数据与数据库中channel_id和baidu-user_id任意一个不一样
		//参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", 2);
		prams.put("channelId", "1");
		prams.put("baiduUserId","1");
		//调用
		Map<String, Object> map = template.postForObject(host + "channel/check.html?sessionId={sessionId}&userId={userId}&channelId={channelId}&baiduUserId={baiduUserId}", null, Map.class,prams);
		//打印返回值
		System.out.println("截屏测试===============输入数据与数据库中channel_id和baidu-user_id任意一个不一样");
		System.out.println("参数："+JsonUtils.ObjectToString(prams));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
		
		
		//******************************输入数据与数据库中channel_id和baidu-user_id字段完全相同******************************
		//参数
		Map<String, Object> pramsSameAsNewdata = Maps.newHashMap();
		pramsSameAsNewdata.put("sessionId", "1");
		pramsSameAsNewdata.put("userId", 2);
		pramsSameAsNewdata.put("channelId", "2");
		pramsSameAsNewdata.put("baiduUserId", "1");
		//调用
		 map = template.postForObject(host + "channel/check.html?sessionId={sessionId}&userId={userId}&channelId={channelId}&baiduUserId={baiduUserId}", null, Map.class,pramsSameAsNewdata);
		//打印返回值
		System.out.println("截屏测试===============输入数据与数据库中channel_id和baidu-user_id字段完全相同");
		System.out.println("参数："+JsonUtils.ObjectToString(pramsSameAsNewdata));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");	
			
		
	}
}
