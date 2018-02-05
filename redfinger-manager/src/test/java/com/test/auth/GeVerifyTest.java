package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

public class GeVerifyTest {
	private String host = "http://10.254.0.155:8180/fingerauth/";
	public RestTemplate template = new RestTemplate();
	@Test
	@SuppressWarnings("unchecked")
	public void completionDataTest() {
		//参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId",6);
		prams.put("taskId", 2);
		prams.put("userEmail", "12");
		prams.put("qq", "12");
		prams.put("userGender", "12");
		prams.put("provinceId", 1);
		prams.put("cityId", 1);
		//调用
		Map<String, Object> map = template.postForObject(host + "task/completionData.html?sessionId={sessionId}&userId={userId}&taskId={taskId}&userEmail={userEmail}&qq={qq}&userGender={userGender}&provinceId={provinceId}&cityId={cityId}",  null, Map.class,prams);
		//打印返回值
		System.out.println("完善资料===============");
		System.out.println("参数："+JsonUtils.ObjectToString(prams));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}
}
