package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

public class sendTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	@SuppressWarnings("unchecked")
	public void screenTest() {
		//******************************输入数据与数据库中channel_id和baidu-user_id任意一个不一样
		//参数String userName, String sign, String v
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("userName", "18890386735");
		prams.put("sign", "18890386735");
		prams.put("v", "1.2.0");

		//调用
		Map<String, Object> map = template.postForObject(host + "/user/getUser.html?userName={userName}&sign={sign}&v={v}", null, Map.class,prams);
		//打印返回值
		// 打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数：" + JsonUtils.ObjectToString(prams));
		System.out.println("返回：" + JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}
	}

