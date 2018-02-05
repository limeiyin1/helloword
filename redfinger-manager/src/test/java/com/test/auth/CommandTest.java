package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

public class CommandTest {
	private String host = "http://10.254.0.155:8180/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	@SuppressWarnings("unchecked")
	public void screenTest() {
		// 参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", 3166);
		prams.put("padCode", "CSpad2004092");
		// 调用
		Map<String, Object> map = template.postForObject(host + "command/screen.html?userId={userId}&sessionId={sessionId}&padCode={padCode}", null, Map.class, prams);
		// 打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数：" + JsonUtils.ObjectToString(prams));
		System.out.println("返回：" + JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}


	@Test
	@SuppressWarnings("unchecked")
	public void rebootTest() {
		// 参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", 3166);
		prams.put("padCode", "CSpad2004092");
		// 调用
		Map<String, Object> map = template.postForObject(host + "command/reboot.html?userId={userId}&sessionId={sessionId}&padCode={padCode}", null, Map.class, prams);
		// 打印返回值
		System.out.println("重启测试===============");
		System.out.println("参数：" + JsonUtils.ObjectToString(prams));
		System.out.println("返回：" + JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}


	

}
