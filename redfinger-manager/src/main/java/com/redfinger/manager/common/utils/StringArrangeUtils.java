package com.redfinger.manager.common.utils;

import java.util.regex.Pattern;

/**
 * 
* @ClassName: StringArrangeUtils
* @Description: 字符串整理工具类
* @author tluo
* @date 2015年10月13日 上午10:02:10
*
 */
public class StringArrangeUtils {
	/**
	 * 
	* @Title: removeEdgeNotNumber 
	* @Description: 移除字符串两端非数字字符
	* @param @param str
	* @param @return
	* @return String 
	* @throws
	 */
	public static String removeEdgeNotNumber(String str){
		StringBuffer sbuff = new StringBuffer(); 
		sbuff.append(str);
		for(int i = sbuff.length()-1 ; i>0 ; i--){		//移除后面非数字字符
			if(Character.isDigit(sbuff.charAt(i))){
				break;
			}
			sbuff.setLength(sbuff.length()-1);
		}
		int len = 0;
		while(len<sbuff.length()){		//移除前面非数组字符
			if(Character.isDigit(sbuff.charAt(0))){
				break;
			}
			sbuff.deleteCharAt(0);
			len++;
		}
	return sbuff.toString();
	}
	/**
	* @Title: censorCronIsCorrect 
	* @Description: 判断cron表达式是否正确
	* @param @param cron
	* @param @return
	* @return boolean
	* @throws
	 */
	public static boolean censorCronIsCorrect(String cron){
		
		// 秒、分
		String regex = "(([*]{1}|([0-5]{0,1}[0-9]{1})|([0-5]{0,1}[0-9]{1}([,-][0-5]{0,1}[0-9])*)|([0-5]{0,1}[0-9]{1}/[0-5]{0,1}[0-9]{1}))\\s+){2}";
		// 小时
		regex += "(([*]{1}|(([0,1]{0,1}[0-9]{1})|2[0-3])|((([0,1]{0,1}[0-9]{1})|2[0-3])([,-](([0,1]{0,1}[0-9]{1})|2[0-3]))*)|((([0,1]{0,1}[0-9]{1})|2[0-3])/(([0,1]{0,1}[0-9]{1})|2[0-3])))\\s+){1}";

		// 日
		regex += "(([*?L]{1}|[1-7]#[1-5]|(([0-2]{0,1}[0-9]{1}W{0,1})|3[0-1])|((([0-2]{0,1}[0-9]{1})|3[0-1])([,-](([0-2]{0,1}[0-9]{1})|3[0-1]))*)|((([0-2]{0,1}[0-9]{1})|3[0-1])/(([0-2]{0,1}[0-9]{1})|3[0-1])))\\s+){1}";

		// 月
		regex += "(([*]{1}|(([0]{0,1}[0-9]{1})|1[0-1])|((([0]{0,1}[0-9]{1})|1[0-1])([,-](([0]{0,1}[0-9]{1})|1[0-1]))*)|((([0]{0,1}[0-9]{1})|1[0-1])/(([0]{0,1}[0-9]{1})|1[0-1])))\\s+){1}";

		// 星期中的天1=sun
		regex += "(([*?]{1}|(([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT){1}L{0,1})|((([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT){1})([,-](([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT){1}))*)|((([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT){1})/(([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT){1})))\\s*){1}";
		// 年
		regex += "(([*]{0,1})|20[0-9][0-9]{1}([-]20[0-9][0-9])*){1}";
				
		return Pattern.matches(regex, cron);	
	}
}
