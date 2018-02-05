package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;
/**
 * 
* @ClassName: TaskInviteTest
* @Description: 领取邀请任务奖励
* @author tluo
* @date 2015年11月6日 上午10:37:06
*
 */
public class TaskInviteTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	public void screenTest() {
		//参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", 4);
		prams.put("taskId", "3");

		//调用
		Map<String, Object> map = template.postForObject(host + "receive/invite.html?userId={userId}&sessionId={sessionId}&taskId={taskId}", null, Map.class,prams);
		//打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数："+JsonUtils.ObjectToString(prams));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}
}
