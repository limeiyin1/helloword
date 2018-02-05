package com.redfinger.manager.common.utils;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.UserAgent;

public class AgentUtils {

	public static boolean isFirefox(UserAgent userAgent){
		return userAgent.getBrowser().toString().contains(Browser.FIREFOX.toString());
	}
	public static boolean isIe(UserAgent userAgent){
		return userAgent.getBrowser().toString().contains(Browser.IE.toString());
	}
	public static boolean isChrome(UserAgent userAgent){
		return userAgent.getBrowser().toString().contains(Browser.CHROME.toString());
	}
	public static boolean isMozilla(UserAgent userAgent){
		return userAgent.getBrowser().toString().contains(Browser.MOZILLA.toString());
	}
}
