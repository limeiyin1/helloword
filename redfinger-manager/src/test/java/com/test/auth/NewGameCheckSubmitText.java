package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

public class NewGameCheckSubmitText {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	public void screenTest() {
		// 参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", "2");
		prams.put("taskId", "16");
		prams.put("checkGameAccount", "zhanghao1231");

		// 调用
		@SuppressWarnings("unchecked")
		Map<String, Object> map = template
				.postForObject(
						host
								+ "newtask/gameSubmitCheck.html?userId={userId}&taskId={taskId}&sessionId={sessionId}&checkGameAccount={checkGameAccount}",
						null, Map.class, prams);
		// 打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数：" + JsonUtils.ObjectToString(prams));
		System.out.println("返回：" + JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}

}
