package com.redfinger.manager.common.interceptors;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.LogAdmin;
import com.redfinger.manager.common.helper.DataInitProcessor;
import com.redfinger.manager.common.mapper.LogAdminMapper;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.RequestUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;

@Aspect
@Component
public class LogAdminInterceptor {
	@Autowired
	LogAdminMapper mapper;

	@SuppressWarnings("unchecked")
	@Around("execution(public * com.redfinger.manager.modules.*.web.*.*(..))")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		boolean record = false;
		HttpServletRequest request = RequestUtils.getLocalRequest();
		String path = request.getServletPath();
		String name = DataInitProcessor.propertiesLoader.getProperty(path);
		if (StringUtils.isNotEmpty(name) && !name.equals("no")) {
			record = true;
		}
		String pramIn = null;
		String pramOut = null;
		String exceptionMgs = null;
		String adminName = SessionUtils.getCurrentUserId(request);
		Date date = new Date();
		try {
			Object result = proceedingJoinPoint.proceed();
			if (record && result != null) {
				pramOut = JsonUtils.ObjectToString(result);
			}
			return result;
		} catch (Exception e) {
			exceptionMgs = e.getMessage()==null?e.toString():e.getMessage();
			throw e;
		} finally {
			if (record) {
				Map<String, Object> map = (Map<String, Object>) request.getParameterMap();
				pramIn = JsonUtils.ObjectToString(map);
				if (path.equals("/login/login")) {
					// 不保存密码
					pramIn = pramIn.replaceAll("\"passwd\":\\[\"(\\S*)\"]", "\"passwd\":\\[\"***\"]");
				} else if (path.equals("/sys/admin/alterPwd")) {
					pramIn = pramIn.replaceAll("\"pwd\":\\[\"(\\S*)\"]", "\"pwd\":\\[\"***\"]");
					pramIn = pramIn.replaceAll("\"reNewPassword\":\\[\"(\\S*)\"]", "\"reNewPassword\":\\[\"***\"]");
				}
				LogAdmin log = new LogAdmin();
				log.setCategory(path);
				log.setParamIn(pramIn!=null && pramIn.length() > 1000 ? pramIn.substring(0, 1000) : pramIn);
				log.setParamOut(pramOut!=null && pramOut.length() > 1000 ? pramOut.substring(0, 1000) : pramOut);
				log.setExceptionMsg(exceptionMgs!=null && exceptionMgs.length() > 400 ? exceptionMgs.substring(0, 400) : exceptionMgs);
				log.setOperIp(StringUtils.getRemoteAddr(request));
				log.setName(name);
				log.setResultStatus(exceptionMgs == null ? ConstantsDb.logAdminResultStatusSuccess() : ConstantsDb.logAdminResultStatusDefeated());
				log.setCreateTime(date);
				log.setCreater(StringUtils.isNotEmpty(adminName) ? adminName : SessionUtils.getCurrentUserId(request));
				mapper.insertSelective(log);
			}
		}
	}

}
