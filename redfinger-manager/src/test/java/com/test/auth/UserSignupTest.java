package com.test.auth;

import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.redfinger.manager.common.utils.JsonUtils;
/**
 * 
* @ClassName: UserSignupTest
* @Description: 使用邀请码注册用户
* @author tluo
* @date 2015年11月6日 上午10:37:19
*
 */
public class UserSignupTest {
	private String host = "http://localhost:8080/fingerauth/";
	public RestTemplate template = new RestTemplate();

	@Test
	public void screenTest() {
		//参数
		Map<String, Object> prams = Maps.newHashMap();
		prams.put("mobileCode", "18890386735");
		prams.put("pwd", "12345678");
		prams.put("pwdRepeat", "12345678");
		prams.put("inviteCode", "531783");
		prams.put("macId", "wdw143221");
		prams.put("imei", "we22");
		
		//调用
		Map<String, Object> map = template.postForObject(host + "user/signup.html?macId={macId}&imei={imei}&mobileCode={mobileCode}&pwd={pwd}&pwdRepeat={pwdRepeat}&inviteCode={inviteCode}", null, Map.class,prams);
		//打印返回值
		System.out.println("截屏测试===============");
		System.out.println("参数："+JsonUtils.ObjectToString(prams));
		System.out.println("返回："+JsonUtils.ObjectToString(map));
		System.out.println("======================");
	}
}
