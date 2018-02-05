package com.redfinger.manager.common.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.redfinger.manager.common.utils.Md5Utils;

public class HttpEncryptUtils {
	
	public static boolean verifyCode(Map<String, String> params, String key, String signType) {
		String sign = params.get(key);
	    String signVerify = getSignVeryfyCode(params, key, null, signType);

        return signVerify.equals(sign);
    }

	public static String getSignVeryfyCode(Map<String, String> params, String key, String keyValue, String signType) {
		StringBuffer sb = new StringBuffer();
        Set<Entry<String, String>> es = params.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if (StringUtils.isNotBlank(v) && !key.equals(k)) {
                sb.append((sb.length()==0?"":"&")+ k + "=" + v);
            }
        }
        if(keyValue != null){
        	sb.append("&"+key+"=" + keyValue);
        }
        return Md5Utils.MD5Encode(sb.toString(), signType);
    }
	
	public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
}
