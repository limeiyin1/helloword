package com.redfinger.manager.common.utils;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

//拼音工具类
public class PinYinUtils {

	//转化为汉语拼音全拼（小写，多音字用逗号风格）
	public static String FullToString(String cn) throws Exception{
		StringBuffer hz=new StringBuffer();
		char[] arr=cn.toCharArray();
		HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
		//转化为小写
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		//无声调的拼音格式
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
		for(int x=0;x<arr.length;x++){
			//通过正则判断是否是汉子
			if (Character.toString(arr[x]).matches("[\\u4E00-\\u9FA5]+")){
				//将汉字多有的读音存进数组中
				 String[] strs = PinyinHelper.toHanyuPinyinStringArray(arr[x], format);
				 if(strs!=null){
					 for(int y=0;y<strs.length;y++){
						 hz.append(strs[y]);
						   if (y != strs.length - 1) { 
                               hz.append(","); 
                           }
					 }
				 }
				 hz.append(" ");
			}else{
				 hz.append(arr[x]);
				 hz.append(" ");
			}
			
		}
		//将传入的汉子转化为空格分隔的形式，多音字用，将多种读音连接
		return PinYinUtils.formattingTheChinese(hz.toString());
	}
	//转化为首字母
	public static String LogogramToString(String cn) throws Exception{
		StringBuffer hz=new StringBuffer();
		char[] arr=cn.toCharArray();
		HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
		//转化为小写
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		//无声调的拼音格式
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
		for(int x=0;x<arr.length;x++){
			//通过正则判断是否是汉子
			if (Character.toString(arr[x]).matches("[\\u4E00-\\u9FA5]+")){
				//将汉字多有的读音存进数组中
				 String[] strs = PinyinHelper.toHanyuPinyinStringArray(arr[x], format);
				 if(strs!=null){
					 for(int y=0;y<strs.length;y++){
						 hz.append(strs[y].charAt(0));
						   if (y != strs.length - 1) { 
                               hz.append(","); 
                           }
					 }
				 }
				 hz.append(" ");
			}else{
				 hz.append(arr[x]);
				 hz.append(" ");
			}
			
		}
		//将传入的汉子转化为空格分隔的形式，多音字用，将多种读音连接
		return PinYinUtils.formattingTheChinese(hz.toString());
	}
	  //将得到的拼音字符串进行处理转划  xyz,asd,qwe 格式的字符串
	  private static String formattingTheChinese(String str) { 
	        List<Map<String, Integer>>list = Lists.newArrayList(); 
	        Map<String, Integer> map = null; 
	        String[] strList = str.split(" "); 
	        for (String string : strList) {
				map=new Hashtable<String, Integer>();
				String[] pinyin=string.split(",");
				for (String string2 : pinyin) {
					Integer count=map.get(string2);
					if(count==null){
						map.put(string2, new Integer(1));
					}else{
						map.remove(string2);
						count++;
						map.put(string2, count);
					}
				}
				list.add(map);
			}
	        Map<String,Integer>twomap=null;
	        for(int i=0;i<list.size();i++){
	        	Map<String,Integer>trmap=new Hashtable<String, Integer>();
	        	if(null==twomap){
	        		for(String string:list.get(i).keySet()){
	        		   trmap.put(string, 1);
	        		}
	        	}else{
	        		for(String string:twomap.keySet()){
	        			for (String s : list.get(i).keySet()) {
							String s1=string+s;
							trmap.put(s1, 1);
						}
	        		}
	        		if(trmap!=null&&trmap.size()>0){
	        			twomap.clear();
	        		}
	        	}
	        	if (trmap != null && trmap.size() > 0) { 
		                twomap = trmap; 
		        } 
	        }
	        String pinyin = ""; 
	        if (twomap != null) { 
	            // 遍历取出组合字符串 
	            for (String s : twomap.keySet()) { 
	            	pinyin += (s + ","); 
	            } 
	        } 
	        pinyin = pinyin.substring(0, pinyin.length() - 1); 
	        
	        return pinyin;
	    } 
	  

		//查询时使用不区分多音字只查第一个转化为汉语拼音全拼（小写，多音字用逗号风格）
		public static String selectFullToString(String cn) throws Exception{
			StringBuffer hz=new StringBuffer();
			char[] arr=cn.toCharArray();
			HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
			//转化为小写
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			//无声调的拼音格式
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
			for(int x=0;x<arr.length;x++){
				//通过正则判断是否是汉子
				if (Character.toString(arr[x]).matches("[\\u4E00-\\u9FA5]+")){
					//将汉字多有的读音存进数组中
					 String[] strs = PinyinHelper.toHanyuPinyinStringArray(arr[x], format);
					 if(strs!=null){
							 hz.append(strs[0]);
					 }
				}else{
					 hz.append(arr[x]);
				}
			}
			//将传入的汉子转化为空格分隔的形式，多音字用，将多种读音连接
			return hz.toString();
		}
		//查询时使用不区分多音字只查多音字转化为首字母
		public static String selectLogogramToString(String cn) throws Exception{
			StringBuffer hz=new StringBuffer();
			char[] arr=cn.toCharArray();
			HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
			//转化为小写
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			//无声调的拼音格式
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
			for(int x=0;x<arr.length;x++){
				//通过正则判断是否是汉子
				if (Character.toString(arr[x]).matches("[\\u4E00-\\u9FA5]+")){
					//将汉字多有的读音存进数组中
					 String[] strs = PinyinHelper.toHanyuPinyinStringArray(arr[x], format);
					 if(strs!=null){
							 hz.append(strs[0].charAt(0));
					 }
			
				}else{
					 hz.append(arr[x]);

				}
			}
			//将传入的汉子转化为空格分隔的形式，多音字用，将多种读音连接
			return hz.toString();
		}
	   
}



