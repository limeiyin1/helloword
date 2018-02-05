package com.redfinger.manager.common.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.helper.DataInitProcessor;
import com.redfinger.manager.common.utils.JsonUtils;


public class BaseController {
	public static Logger log = LoggerFactory.getLogger(BaseController.class);

	public String toPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		String servletPath = request.getServletPath();
		List<String> list = Lists.newArrayList(Splitter.on("/").trimResults().omitEmptyStrings().limit(3).split(servletPath));
		// url:sys/user  => sys/user_index.jsp
		// url:sys/user/index  => sys/user_index.jsp
		if(list.size()<=2){
			list.add("index");
		}
		String page=list.get(0)+"/"+list.get(1)+"_"+list.get(2);
		String modelName=list.get(0)+"/"+list.get(1);
		model.addAttribute("model", modelName);
		model.addAttribute("dictCategoryMap", JsonUtils.ObjectToString(DataInitProcessor.dictCategoryMap));
		return page;
	}
	
	public String toPage(HttpServletRequest request, HttpServletResponse response, Model model,String page){
		String servletPath = request.getServletPath();
		List<String> list = Lists.newArrayList(Splitter.on("/").trimResults().omitEmptyStrings().limit(3).split(servletPath));
		if(list.size()<=2){
			list.add("index");
		}
		page=list.get(0)+"/"+page;
		String modelName=list.get(0)+"/"+list.get(1);
		model.addAttribute("model", modelName);
		model.addAttribute("dictCategoryMap", JsonUtils.ObjectToString(DataInitProcessor.dictCategoryMap));
		return page;
	}
	
	/**
     * 
     * @Title: asyncReturnResult 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param response
     * @param @param json
     * @param @param charsets    设定文件 
     * @return void    返回类型 
     * @throws
     */
    protected JSONObject asyncReturnResult(HttpServletResponse response, JSONObject json, String...charsets) {
		try {
			String charset = "UTF-8";
			if (ArrayUtils.isNotEmpty(charsets))
				charset = charsets[0];
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html;charset="+charset);//注意返回的内容格式
			PrintWriter pw = response.getWriter();
			pw.print(json);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

    /**
     * 随机生成4个字符
     * @return
     */
    protected String generateStr() {  
        String[] beforeShuffle = new String[] {"0", "1", "2", "3", "4", "5", "6", "7",  
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J",  
                "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",  
                "W", "X", "Y", "Z" , "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",  
                "k", "m", "n", "p", "q", "r", "s", "t", "u", "v",  
                "w", "x", "y", "z" };  
        List list = Arrays.asList(beforeShuffle);  
        Collections.shuffle(list);  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < list.size(); i++) {  
            sb.append(list.get(i));  
        }  
        String afterShuffle = sb.toString();  
        String result = afterShuffle.substring(5, 13);  
        return result;  
    }
    
    /**
     * 随机生成4个是数字字符
     * @return
     */
    protected String generateNum(Integer num) {  
        if(num < 10){
        	return "000"+num;
        }
        if(num > 9 && num < 100){
        	return "00"+num;
        }
        if(num > 99 && num < 999){
        	return "0"+num;
        }
        if(num > 999){
        	return ""+num;
        }
        return null;  
    }
    
    /** 
     * 将json格式的字符串解析成Map对象 <li> 
     * json格式：{"name":"admin","retries":"3fff","testname" 
     * :"ddd","testretries":"fffffffff"} 
     */  
    protected static Map<String, Object> toHashMap(Object object) {  
        Map<String, Object> data = new HashMap<String, Object>();  
        // 将json字符串转换成jsonObject  
        JSONObject jsonObject = JSONObject.fromObject(object);  
        Iterator it = jsonObject.keys();  
        // 遍历jsonObject数据，添加到Map对象  
        while (it.hasNext())  
        {  
            String key = String.valueOf(it.next());  
            String value = (String) jsonObject.get(key);  
            data.put(key, value);  
        }  
        return data;  
    }  
    
    /***
     * 根据渠道获取地址
     * @param channelCode
     * @param url
     * @return
     */
	public static String getUrl(String channelCode,String url,String endingCode){
		String str = "" ;
		
		String codes[] = channelCode.split("\\.");
		String code = codes[codes.length-1];
		String updateUrl = url;
		String updateUrls[] = updateUrl.split("\\/");
		StringBuffer buffer = new StringBuffer();
		for (int i=0; i<updateUrls.length;i++) {
			if(StringUtils.isNotBlank(updateUrls[i])){
				if(i==0){
					buffer.append(updateUrls[i]+"//");
				}else{
					if(i <= updateUrls.length-2){
						buffer.append(updateUrls[i]+"/");
					}
				}
			}
		}
		log.info(buffer.toString());
		log.info(updateUrls[updateUrls.length-1]);
		String urls[] = updateUrls[updateUrls.length-1].split("\\.");
		String urlss = urls[0] + "_" + code +"." + endingCode;
		
		str = buffer.toString() + urlss;
		log.info("url:"+str);
		return str;
	}
	
	/**
	 * 判断是否有相同的值
	 * @param list
	 * @return
	 */
	public boolean checkSam(List<Integer> list){
		Integer temp;
		for (int i = 0; i < list.size() - 1; i++){
            temp = list.get(i);
            for (int j = i + 1; j < list.size(); j++){
                if (temp.equals(list.get(j))){
                    return true;
                }
            }
        }
		return false;
	}
}
