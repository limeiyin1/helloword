package com.redfinger.manager.modules.tools.help;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientHelp {

	//获取html页面源代码
	public static String requestGet(CloseableHttpClient httpclient, String urlWithParams) throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(urlWithParams);
		// 配置请求的超时设置
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000).build();
		httpget.setConfig(requestConfig);
		CloseableHttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String jsonStr = EntityUtils.toString(entity, "utf-8");
		httpget.releaseConnection();
		return jsonStr;
	}

	//获取连接地址文件大小
	public static int getLength(CloseableHttpClient httpclient, String url) throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpget);
		Header h = response.getHeaders("Content-Length")[0];
		httpget.releaseConnection();
		return Integer.parseInt(h.getValue());
	}
	
}
