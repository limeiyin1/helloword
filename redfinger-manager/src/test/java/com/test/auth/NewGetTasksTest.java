package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

/**
 * 新获取任务列表接口调试
 * 
 * @ClassName: NewGetTasks
 * @author tluo
 * @date 2016年6月3日 上午9:54:26
 */
public class NewGetTasksTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();
	// 任务类型：每日任务
	// private String EVERYDAY= "everyday";
	// 任务类型：推广任务
	// private String GANERALIZE= "ganeralize";
	// 任务类型：完善资料
	// private String USERDATA= "userdata";
	// 任务类型：问卷调查
	// private String WJDC = "wjdc";
	// 任务类型：游戏下载
	private String GAMEDOWNLOAD = "gamedownload";

	@Test
	public void screenTest() {
		// 参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", "2");
		prams.put("category", GAMEDOWNLOAD);

		// 调用
		@SuppressWarnings("unchecked")
		Map<String, Object> map = template.postForObject(host
				+ "newtask/getTasks.html?userId={userId}&sessionId={sessionId}&category={category}", null, Map.class,
				prams);
		// 打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数：" + JsonUtils.ObjectToString(prams));
		System.out.println("返回：" + JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}
}
