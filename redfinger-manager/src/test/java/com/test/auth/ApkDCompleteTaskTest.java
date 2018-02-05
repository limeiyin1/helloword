package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;
/**
 * 
* @ClassName: TaskGetTasksTest
* @Description: 游戏下载任务完成
* @author tluo
* @date 2015年11月6日 上午10:36:57
*
 */
public class ApkDCompleteTaskTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	public void screenTest() {
		//参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", "3");
		prams.put("apkId", "4274");

		//调用
		Map<String, Object> map = template.postForObject(host + "task/apkDComplete.html?userId={userId}&apkId={apkId}&sessionId={sessionId}", null, Map.class,prams);
		//打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数："+JsonUtils.ObjectToString(prams));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}
}
