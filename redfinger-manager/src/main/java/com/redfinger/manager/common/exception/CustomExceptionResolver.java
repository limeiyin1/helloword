package com.redfinger.manager.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class CustomExceptionResolver extends SimpleMappingExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		logger.error(ex.getMessage(), ex);
		StringBuffer sb = new StringBuffer();
		if (ex instanceof BusinessException) {
			StackTraceElement[] s = { ex.getStackTrace()[0] };
			ex.setStackTrace(s);
			if (handlerMethod.getMethodAnnotation(ResponseBody.class) != null || handlerMethod.getMethod().getReturnType().toString().equals("void")) {
				sb.append("{\"code\":\"501\",\"message\":\"" + ex.getMessage() + "\"}");
			} else {
				sb.append("<script type=\"text/javascript\">");
				sb.append("cancel();");
				sb.append("$.messager.alert('提示', '" + ex.getMessage() + "', 'info');");
				sb.append("</script>");
			}
		} else {
			if (handlerMethod.getMethodAnnotation(ResponseBody.class) != null || handlerMethod.getMethod().getReturnType().toString().equals("void")) {
				sb.append("{\"code\":\"500\",\"message\":\"操作失败，请联系管理员！\"}");
			} else {
				sb.append("<script type=\"text/javascript\">");
				sb.append("cancel();");
				sb.append("$.messager.alert('提示', '操作失败，请联系管理员！', 'info');");
				sb.append("</script>");
			}
		}
		request.setAttribute("info", sb.toString());
		return new ModelAndView("exception");
	}
}
