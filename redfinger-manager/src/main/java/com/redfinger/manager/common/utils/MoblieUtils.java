package com.redfinger.manager.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
public class MoblieUtils {
	/**
	 * 测试手机号码是来自哪个城市的，利用淘宝的API
	 * 
	 * @param mobileNumber
	 *            手机号码
	 * @return
	 * @throws MalformedURLException
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> calcMobileCity(String mobileNumber) throws MalformedURLException {
		String jsonString = null;
		String urlString = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + mobileNumber;
		StringBuffer sb = new StringBuffer();
		BufferedReader buffer;
		URL url = new URL(urlString);
		try {
			InputStream in = url.openStream();
			// 解决乱码问题
			buffer = new BufferedReader(new InputStreamReader(in, "gb2312"));
			String line = null;
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			buffer.close();
			// System.out.println(sb.toString());
			jsonString = sb.toString();
			// 替换掉“__GetZoneResult_ = ”，让它能转换为JSONArray对象
			jsonString = jsonString.replaceAll("^[__]\\w{14}+[_ = ]+", "");
			jsonString = jsonString.replaceAll(" ", "");
			jsonString = jsonString.replaceAll("'", "\"");
			jsonString = jsonString.replaceAll("mts", "\"mts\"");
			jsonString = jsonString.replaceAll("province", "\"province\"");
			jsonString = jsonString.replaceAll("catName", "\"catName\"");
			jsonString = jsonString.replaceAll("telString", "\"telString\"");
			jsonString = jsonString.replaceAll("areaVid", "\"areaVid\"");
			jsonString = jsonString.replaceAll("ispVid", "\"ispVid\"");
			jsonString = jsonString.replaceAll("carrier", "\"carrier\"");
			LinkedHashMap<String, String> map = JsonUtils.stringToObject(jsonString, LinkedHashMap.class);
			return map;
		} catch (Exception e) {
			return null;
		}

	}
}
