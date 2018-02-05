package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;

/**
 * 
 * @ClassName: SurveySubmitTest
 * @Description: 问卷调查提交
 * @author tluo
 * @date 2015年11月6日 上午10:31:03
 *
 */
public class SurveySubmitTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	@SuppressWarnings("unchecked")
	public void screenTest() {
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("sessionId", "12");
		prams.put("surveyId", 1);
		prams.put("userId", 5);
		prams.put("taskId", 2);
//		prams.put("map[1]", "2");
//		prams.put("map[2]", "4");
//		prams.put("map[3]", "9");
//		prams.put("map[4]", "14");
//		prams.put("map[5]", "19");
//		prams.put("map[6]", "25");
//		prams.put("map[7]", "30");
//		prams.put("map[8]", "35");
//		prams.put("map[9]", "42");
//		prams.put("map[10]", "50");
//		prams.put("map[11]", "55");
//		prams.put("map[12]", "62");
//		prams.put("map[13]", "63");
		
		prams.put("map[1]", "1");
		prams.put("map[2]", "4");
		prams.put("map[3]", "8");
		prams.put("map[4]", "16");
		prams.put("map[5]", "21");
		prams.put("map[6]", "24");
		prams.put("map[7]", "32");
		prams.put("map[8]", "37");
		prams.put("map[9]", "44");
		prams.put("map[10]", "53");
		prams.put("map[11]", "56");
		prams.put("map[12]", "60");
		prams.put("map[13]", "64");

		StringBuffer sb = new StringBuffer(host);
		sb.append("survey/submit.html?a=a");
		for (String key : prams.keySet()) {
			sb.append("&" + key + "={" + key + "}");
		}
		System.out.println(sb.toString());
		Map<String, Object> map = template.postForObject(sb.toString(), null, Map.class, prams);
		// 打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数：" + JsonUtils.ObjectToString(prams));
		System.out.println("返回：" + JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}

}
