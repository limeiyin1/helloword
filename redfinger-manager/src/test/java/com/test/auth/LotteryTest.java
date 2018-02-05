package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;
/**
 * 
* @ClassName: LotteryTest
* @Description: TODO(这里用一句话描述这个类的作用)
* @author tluo
* @date 2016年1月22日 上午9:47:06
 */
public class LotteryTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	public void lotteryTest() {
		//参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "1");
		prams.put("userId", "3");

		//调用
		for(int i = 0 ;i<1000 ;i++){
			Map<String, Object> map = template.postForObject(host + "/award/lottery.html?userId={userId}&sessionId={sessionId}", null, Map.class,prams);
			//打印返回值
			System.out.println("抽奖测试===============");
			System.out.println("参数："+JsonUtils.ObjectToString(prams));
			System.out.println("返回："+JsonUtils.ObjectToString(map));
			System.out.println("======================");
		}

	}
}
