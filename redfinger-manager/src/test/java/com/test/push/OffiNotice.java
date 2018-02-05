package com.test.push;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

public class OffiNotice {

	private String host = "http://localhost:8080/monitor/";
	public RestTemplate template = new RestTemplate();

	@Test
	public void screenTest() {
		// 参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("ip", "10.1.4.92");
		prams.put("packageName", "com.finger.www");
		// 调用
		String map = template.postForObject(host + "/game/push.html?ip={ip}&packageName={packageName}", null, String.class, prams);
		// 打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数：" + JsonUtils.ObjectToString(prams));
		System.out.println("返回：" + map);
		System.out.println("======================");
	}
}
