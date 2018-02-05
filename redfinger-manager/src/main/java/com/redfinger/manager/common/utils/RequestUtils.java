package com.redfinger.manager.common.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	public static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getLocalRequest() {
		return localRequest.get();
	}

	public static boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	}
}
