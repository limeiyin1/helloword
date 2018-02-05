package com.redfinger.manager.common.interceptors;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PermissionHelper;
import com.redfinger.manager.common.utils.RequestUtils;
import com.redfinger.manager.common.utils.SessionUtils;

public class AuthInterceptor implements HandlerInterceptor {

	//不需要登录的URL
	public static List<String> unLoginUrlList=Lists.newArrayList();
	//需要登录，但是不需要验证的URL
	public static List<String> unPermissionUrlList=Lists.newArrayList();
	
	static{
		unLoginUrlList.add("/login/login");
		unLoginUrlList.add("/login/logout");
		unLoginUrlList.add("/login/login1");
		unLoginUrlList.add("/login/getVerificationCode");
		unLoginUrlList.add("/sys/admin/compulsionAlterPwd");
	}
	static{
		unPermissionUrlList.add("/");
		unPermissionUrlList.add("/menu");
		unPermissionUrlList.add("/keepSession");
		unPermissionUrlList.add("/sys/admin/adminCentre");
		unPermissionUrlList.add("/sys/admin/formPwd");
		unPermissionUrlList.add("/sys/admin/alterPwd");
		unPermissionUrlList.add("/sys/admin/compulsionAlterPwd");//密码为默认时，强制要求修改
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		RequestUtils.localRequest.set(request);
		String path = request.getServletPath();
		//不需要登录
		if (unLoginUrlList.contains(path)) {
			return true;
		}
		response.setContentType("text/plain;charset=utf-8");
		String uriPrefix = request.getContextPath();
		SysAdmin user = SessionUtils.getCurrentUser(request);
		//判断是否登录
		if (user == null) {
			uriPrefix = uriPrefix.length() <= 1 ? "" : uriPrefix;
			if (request.getHeader("X-Requested-With") != null) {
				PrintWriter writer = response.getWriter();
				if (path.equals("/menu")) {
					writer.write("<script>window.location.href='" + uriPrefix + "/login.jsp';</script>");
				} else {
					writer.write("{\"code\":\"304\",\"message\":\"未登陆或登陆超时！\"}");
				}
				writer.flush();
			} else {
				response.sendRedirect(uriPrefix + "/login.jsp");
			}
			return false;
		}
		//不需要验证
		if(unPermissionUrlList.contains(path)){
			return true;
		}
		//判断是否有权限
		if(!PermissionHelper.hasMenuAndButtonPermission (request, path)){
			throw new BusinessException("无此操作权限");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 对于无返回值的操作，默认给一个操作成功的json串
		if (handlerMethod.getMethod().getReturnType().toString().equals("void")) {
			response.setContentType("text/plain;charset=utf-8");
			PrintWriter writer;
			writer = response.getWriter();
			writer.write("{\"code\":\"200\",\"message\":\"操作成功！\"}");
			writer.flush();
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//清除缓存
		BaseService.cache.remove();
	}

}
