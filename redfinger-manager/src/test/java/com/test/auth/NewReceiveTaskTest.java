package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

/**
 * 新任务接取接口调试
 * 
 * @ClassName: NewReceiveTaskTest
 * @author tluo
 * @date 2016年6月3日 上午9:56:43
 */
public class NewReceiveTaskTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	public void screenTest() {
		// 参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", "2");
		prams.put("taskId", "12");

		// 调用
		@SuppressWarnings("unchecked")
		Map<String, Object> map = template.postForObject(host
				+ "newtask/acessTasks.html?userId={userId}&sessionId={sessionId}&taskId={taskId}", null, Map.class,
				prams);
		// 打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数：" + JsonUtils.ObjectToString(prams));
		System.out.println("返回：" + JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}
}
