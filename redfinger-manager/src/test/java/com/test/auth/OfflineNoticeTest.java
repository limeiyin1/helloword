package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

public class OfflineNoticeTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	@SuppressWarnings("unchecked")
	public void screenTest() {
		
		//******************************开启掉线提醒*********************************
		//参数
		Map<String, Object> pramsOpen = Maps.newHashMap();
		
		pramsOpen.put("sessionId", "1");
		pramsOpen.put("userId", 2);
		pramsOpen.put("padCode", "CSpad2026024");
		//调用
		Map<String, Object> map = template.postForObject(host + "offlineNotice/open.html?sessionId={sessionId}&userId={userId}&padCode={padCode}", null, Map.class,pramsOpen);
		//打印返回值
		System.out.println("截屏测试===============开启掉线提醒");
		System.out.println("参数："+JsonUtils.ObjectToString(pramsOpen));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
		
		
		//******************************设备初始状态已开启,再次开启掉线提醒*********************************
		//参数
		Map<String, Object> pramsOpenAgain = Maps.newHashMap();
		pramsOpenAgain.put("sessionId", "1");
		pramsOpenAgain.put("userId", 2);
		pramsOpenAgain.put("padCode", "CSpad2026024");
		//调用
		map = template.postForObject(host + "offlineNotice/open.html?sessionId={sessionId}&userId={userId}&padCode={padCode}", null, Map.class,pramsOpenAgain);
		//打印返回值
		System.out.println("截屏测试===============再次开启掉线提醒");
		System.out.println("参数："+JsonUtils.ObjectToString(pramsOpenAgain));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
		
		

		
//		//********************************关闭掉线提醒********************************
//		//参数
//		Map<String, Object> pramsClose = Maps.newHashMap();
//		pramsClose.put("sessionId", "1");
//		pramsClose.put("userId", 2);
//		pramsClose.put("padCode", "CSpad2026024");
//		//调用
//		 map = template.postForObject(host + "offlineNotice/close.html?sessionId={sessionId}&userId={userId}&padCode={padCode}", null, Map.class,pramsClose);
//		//打印返回值
//		System.out.println("截屏测试===============关闭掉线提醒功能");
//		System.out.println("参数："+JsonUtils.ObjectToString(pramsClose));
//		System.out.println("返回："+JsonUtils.ObjectToString(map));
//		System.out.println("======================");
//		
//		
//		//********************************设备初始状态关闭,再次关闭掉线提醒********************************
//		//参数
//		Map<String, Object> pramsCloseAgain = Maps.newHashMap();
//		pramsCloseAgain.put("sessionId", "1");
//		pramsCloseAgain.put("userId", 2);
//		pramsCloseAgain.put("padCode", "CSpad2026024");
//		//调用
//		 map = template.postForObject(host + "offlineNotice/close.html?sessionId={sessionId}&userId={userId}&padCode={padCode}", null, Map.class,pramsCloseAgain);
//		//打印返回值
//		System.out.println("截屏测试===============再次关闭掉线提醒");
//		System.out.println("参数："+JsonUtils.ObjectToString(pramsCloseAgain));
//		System.out.println("返回："+JsonUtils.ObjectToString(map));
//		System.out.println("======================");
		
		
		//********************************无此用户********************************
		//参数
		Map<String, Object> pramsNoUser = Maps.newHashMap();
		pramsNoUser.put("sessionId", "1");
		pramsNoUser.put("userId", 200);
		pramsNoUser.put("padCode", "CSpad2026024");
		//调用
		 map = template.postForObject(host + "offlineNotice/close.html?sessionId={sessionId}&userId={userId}&padCode={padCode}", null, Map.class,pramsNoUser);
		//打印返回值
		System.out.println("截屏测试===============数据库中无用户信息");
		System.out.println("参数："+JsonUtils.ObjectToString(pramsNoUser));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
		
		
		//********************************测试无参数********************************
		//参数
		Map<String, Object> pramsNull = Maps.newHashMap();
		pramsNull.put("sessionId", "1");
//		pramsNull.put("userId", null);
//		pramsNull.put("padCode", "CSpad2026024");
		
		pramsNull.put("userId", null);
		pramsNull.put("padCode", null);		
		
//		pramsNull.put("userId", 2);
//		pramsNull.put("padCode", null);
		//调用
		 map = template.postForObject(host + "offlineNotice/close.html?sessionId={sessionId}&userId={userId}&padCode={padCode}", null, Map.class,pramsNull);
		//打印返回值
		System.out.println("截屏测试===============无参数");
		System.out.println("参数："+JsonUtils.ObjectToString(pramsNull));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
		
		
		
		
		
	}
	
	
}
