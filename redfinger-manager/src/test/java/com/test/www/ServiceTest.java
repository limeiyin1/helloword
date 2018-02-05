package com.test.www;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.redfinger.manager.common.domain.SysOrg;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.sys.service.OrgService;


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:spring-context.xml"}) 
public class ServiceTest {

	@Autowired
	OrgService orgService;
	
	@Test
	public void startTest() throws Exception{
		SysOrg org = new SysOrg();
		org.setOrgCode("1");
		orgService.start(null, org);
	}
	@Test
	public void stopTest() throws Exception{
		SysOrg org = new SysOrg();
		org.setOrgCode("1");
		orgService.stop(null, org);
	}
	@Test
	public void deleteTest() throws Exception{
		SysOrg org = new SysOrg();
		org.setOrgCode("1");
		orgService.delete(null, org);
	}

		
		
		
		

	
	@SuppressWarnings("unchecked")
	@Test
	public void treeTest()throws Exception{
	/*	List<SysOrg> list=orgService.initQuery().findStopTrue();
		TreeDomain treeDomain = ListSortUtils.sortListToTree(list, "orgCode", "parentOrgCode", "orgName");
		String json=JsonUtils.ObjectToString(treeDomain);
		System.out.println(json);*/
/*
		String httpUrl = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";
		String httpArg = "phone=" + mobileNumber;
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "5d12f28405e46d7677fccba95b7ddf5f");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
			}
			reader.close();
			result = sbf.toString();
			LinkedHashMap<String, String> map = JsonUtils.stringToObject(result, LinkedHashMap.class);
			return map;
		} catch (Exception e) {

		}*/
		
	/*	 JSON返回示例 :

		 {
		     "errNum": 0,
		     "retMsg": "success",
		     "retData": {
		         "phone": "15210011578",
		         "prefix": "1521001",
		         "supplier": "移动 ",
		         "province": "北京 ",
		         "city": "北京 ",
		         "suit": "152卡"
		     }
		 }
		 备注 :
		 "phone": 手机号码,
		         "prefix": 手机号码前7位,
		         "supplier": "移动 ",
		         "province": 省份,
		         "city": 城市,
		         "suit": "152卡"
*/
		String httpUrl = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";
		String httpArg = "phone=" + "15116258912";
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "5d12f28405e46d7677fccba95b7ddf5f");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
			}
			reader.close();
			result = sbf.toString();
			result = result.replaceAll("phone", "\"phone\"");
			result = result.replaceAll("prefix", "\"prefix\"");
			result = result.replaceAll("supplier", "\"supplier\"");
			result = result.replaceAll("areaVid", "\"areaVid\"");
			result = result.replaceAll("ispVid", "\"ispVid\"");
			result = result.replaceAll("carrier", "\"carrier\"");
			LinkedHashMap<String, String> map = JsonUtils.stringToObject(result, LinkedHashMap.class);
			System.out.println(map.get("phone").toString());
			System.out.println(map.get("prefix").toString());
			System.out.println(map.get("province").toString());
			System.out.println(map.get("city").toString());
			System.out.println(map.get("suit").toString());
			System.out.println(map.get("supplier").toString());
			}catch (Exception e) {

			}
		
	}
}
