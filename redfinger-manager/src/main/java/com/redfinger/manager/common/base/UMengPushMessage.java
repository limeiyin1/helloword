package com.redfinger.manager.common.base;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UMengPushMessage {
	private static Logger logger = LoggerFactory.getLogger(UMengPushMessage.class);
	
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();
	
	// The host
	protected static final String host = "http://msg.umeng.com/api/send";
	
	protected static final String fileHost = "http://msg.umeng.com/upload";
	
	protected static final String appkey = "5776140967e58e5b350029a9";
	
	protected static final String appMasterSecret = "7tqy74vk0y3limxixiw2hbehmb8c07nw";
	
	// Upload file with device_tokens to Umeng
	public Map<String,Object> uploadContents(String appkey,String appMasterSecret,String contents) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		JSONObject json = new JSONObject();
		json.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		json.put("timestamp", timestamp);
		json.put("type", "unicast");
		json.put("device_tokens", "Aod89m4ytYw5h9Hq-nMGd-Rr4SboyQkLc7iRQipxNPav," +
				"An3_s5LHPwEHHFLecxwM-wdom73VZUPk7KLRgjf2Ne0W," +
				"ArnCNPn-9m9lfGB9yh6F3FW8fiU8qTbhJa7K4-KqDErG," +
				"An3_s5LHPwEHHFLecxwM-wdom73VZUPk7KLRgjf2Ne0W");
		json.put("alias", "alias");
		json.put("alias_type", "alias");
		
		JSONObject payLoadJson = new JSONObject();
		payLoadJson.put("display_type", "notification");
		
		
		JSONObject bodyJson = new JSONObject();
		bodyJson.put("ticker","提示");
		bodyJson.put("title","提示标题");
		bodyJson.put("text",contents);
		bodyJson.put("after_open","go_app");
		bodyJson.put("activity","PadDetailActivity");
		bodyJson.put("play_vibrate","true");
        bodyJson.put("play_lights","true");
        bodyJson.put("play_sound","true");
		
		payLoadJson.put("body", bodyJson);
		json.put("payload", payLoadJson);
		
		JSONObject policyJson = new JSONObject();
		policyJson.put("expire_time", "2016-07-15 12:00:00");
		
		json.put("policy", policyJson);
		json.put("description", "测试alias通知-Android");
		
		
		// Construct the request
		String url = host;
		String postBody = json.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		logger.info("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		logger.info(result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = new JSONObject(result.toString());
		String ret = respJson.getString("ret");
		JSONObject dataJson = new JSONObject(respJson.get("data").toString());
		if (!ret.equals("SUCCESS")) {
			map.put("ret", ret);
			map.put("errorCode", dataJson.get("error_code"));
		}else{
			map.put("ret", ret);
			if(dataJson.has("msg_id")){
				map.put("msgId", dataJson.get("msg_id"));
			}
		}
		return map;
	}
	
	
	public Map<String,Object> messageContents(JSONObject json,String host,String mothed,String userAgent ,
			String appkey,String appMasterSecret) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		String postBody = json.toString();
		String sign = DigestUtils.md5Hex((mothed + host + postBody + appMasterSecret).getBytes("utf8"));
		logger.info("友盟推送信息， : " + json.toString());
		host = host + "?sign=" + sign;
		HttpPost post = new HttpPost(host);
		post.setHeader("User-Agent", userAgent);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		
		HttpResponse response = client.execute(post);
		logger.info("友盟返回编码，Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		logger.info("友盟返回信息，Request Code : " + result.toString());
		JSONObject respJson = new JSONObject(result.toString());
		String ret = respJson.getString("ret");
		JSONObject dataJson = new JSONObject(respJson.get("data").toString());
		if (!ret.equals("SUCCESS")) {
			map.put("ret", ret);
			map.put("errorCode", dataJson.get("error_code"));
		}else{
			map.put("ret", ret);
			if(dataJson.has("msg_id")){
				map.put("msgId", dataJson.get("msg_id"));
			}
			
			if(dataJson.has("task_id")){
				map.put("taskId", dataJson.get("task_id"));
			}
		}
		
		return map;
	}
	
	/**
	 * 友盟文件上传
	 * @param appkey
	 * @param appMasterSecret
	 * @param contents
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> fileContents(String appkey,String appMasterSecret,String contents) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		JSONObject json = new JSONObject();
		json.put("appkey", appkey);
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		json.put("timestamp", timestamp);
		json.put("content", contents);
		
		// Construct the request
		String url = fileHost;
		String postBody = json.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		logger.info("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		logger.info(result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = new JSONObject(result.toString());
		String ret = respJson.getString("ret");
		JSONObject dataJson = new JSONObject(respJson.get("data").toString());
		if (!ret.equals("SUCCESS")) {
			map.put("ret", ret);
			map.put("errorCode", dataJson.get("error_code"));
		}else{
			map.put("ret", ret);
			if(dataJson.has("file_id")){
				map.put("fileId", dataJson.get("file_id"));
			}
		}
		return map;
	}
	
	/**
	 * 读取配置文件
	 * @return
	 */
	public Map<String,Object> readErrorPro(){
		Map<String,Object> map = new HashMap<String,Object>();
		Properties prop = new Properties();   
		 try{
			 //读取属性文件a.properties
			 prop.load(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("filters/error-conf.properties"), "UTF-8")); 
			 Iterator<String> it=prop.stringPropertyNames().iterator();
			 while(it.hasNext()){
			        String key=it.next();
			        map.put(key, prop.getProperty(key));
			 }
		 }catch(Exception e){
		      e.printStackTrace();
		 }
		 return map;
	}
	
	
	public static void main(String args[]){
		UMengPushMessage umpm = new UMengPushMessage();
		try {
			/*umpm.uploadContents(appkey, appMasterSecret, "上传地址");*/
			Map<String,Object> map = umpm.readErrorPro();
			for (String key : map.keySet()) {
				logger.info("key= "+ key + " and value= " + map.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}

	
