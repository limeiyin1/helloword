package com.redfinger.manager.modules.main.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.Constants;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.ResultType;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.utils.Md5Utils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.VerificationCodeUtil;
import com.redfinger.manager.modules.sys.service.AdminService;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {

	@Autowired
	AdminService service;

	@ResponseBody
	@RequestMapping(value = "login")
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, Model model, String login, String passwd ,String verificationCode) throws Exception {
		HttpSession session = request.getSession();
		Map<String, Object> result= new HashMap<String, Object>();
		int fail_count = (null!=session.getAttribute(ResultType.FAILT_COUNT))?(int)session.getAttribute(ResultType.FAILT_COUNT):0;//获取登录失败次数
		if (StringUtils.isBlank(login)) {
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "登录账号不能为空！");
			return result;
		}
		if (StringUtils.isBlank(passwd)) {
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "登录密码不能为空！");
			return result;
		}
		if(fail_count>=3){
			String Verification = (String)session.getAttribute(VerificationCodeUtil.Verification);
			if(!Verification.equalsIgnoreCase(verificationCode)){
				result.put(ResultType.CODE, ResultType.FAIL);
				result.put(ResultType.MSG, "验证码不正确！");
				result.put(ResultType.FAILT_COUNT, fail_count);
				return result;
			}
		}
		SysAdmin user = service.get(login);
		if (user == null) {
			fail_count++;
			session.setAttribute(ResultType.FAILT_COUNT, fail_count);
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "账号或密码有误，登录失败！");
			result.put(ResultType.FAILT_COUNT, fail_count);
			return result;
		}
		if(ConstantsDb.globalEnableStatusStop().equals(user.getEnableStatus())){
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "帐号禁用，登录失败！");
			return result;
		}
		if (!Md5Utils.MD5(login + Constants.USER_PASSWORD_SALT+passwd).equals(user.getAdminPwd())) {
			fail_count++;
			session.setAttribute("fail_count", fail_count);
			result.put(ResultType.CODE, ResultType.FAIL);
			result.put(ResultType.MSG, "账号或密码有误，登录失败！");
			result.put(ResultType.FAILT_COUNT, fail_count);
			return result;
		}
		
		if(user.getAdminPwd().equals(Md5Utils.MD5(user.getAdminCode()+ Constants.USER_PASSWORD_SALT + Constants.USER_DEFAULT_PASSWORD))){
			session.setAttribute(ResultType.USER_TEMP, user);
			result.put(ResultType.CODE, ResultType.needResetPwd);
			result.put(ResultType.MSG, "需要修改密码");
			return result;
		}
		if(!passwd.matches("^.{8,}$")){
			session.setAttribute(ResultType.USER_TEMP, user);
			result.put(ResultType.CODE, ResultType.needResetPwd);
			result.put(ResultType.MSG, "需要修改密码");
			return result;
		}
		if(!(passwd.matches("^.*[a-z]+.*$") && passwd.matches("^.*[0-9]+.*$") && (passwd.matches("^.*[A-Z]+.*$"))|| passwd.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$"))){
			session.setAttribute(ResultType.USER_TEMP, user);
			result.put(ResultType.CODE, ResultType.needResetPwd);
			result.put(ResultType.MSG, "需要修改密码");
			return result;
		}
		
		service.login(request,response,login,user);
		// 获取用户拥有的权限
		SessionUtils.setCurrentPermission(request, service.getPermissionByUser(user));
		SessionUtils.setCurrentUser(request, user);
		
		result.put(ResultType.CODE, ResultType.SUCCESS);
		result.put(ResultType.MSG, "登录成功！");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "getVerificationCode")
	public String getVerificationCode(HttpServletRequest request, HttpServletResponse response, Model model, String login, String passwd) throws Exception {
		HttpSession session = request.getSession();
		VerificationCodeUtil.writeRandomImage(response, session, VerificationCodeUtil.Verification, null, 30);//生成验证码
		log.info((String)session.getAttribute(VerificationCodeUtil.Verification));
		return null;
	}

	/*@RequestMapping(value = "login1")
	public void login1(HttpServletRequest request, HttpServletResponse response, Model model, String login, String passwd,String pin) throws Exception {
		SysAdmin user = service.login(request,response,login, passwd ,pin);
		SessionUtils.setCurrentUser(request, user);
		// 获取用户拥有的权限
		SessionUtils.setCurrentPermission(request, service.getPermissionByUser(user));
	}*/
	
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		SessionUtils.removeCurrentUser(request);
		SessionUtils.removeCurrentPersmission(request);
		request.getSession().removeAttribute("fail_count");
		return "redirect:/";
	}

	@RequestMapping(value = "changePasswd")
	public String changePasswd(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "doChangePasswd")
	public void doChangePasswd(HttpServletRequest request, HttpServletResponse response, Model model, String passwd, String passwd2) throws Exception {
		if (StringUtils.isBlank(passwd) || StringUtils.isBlank(passwd2)) {
			throw new BusinessException("密码不能为空");
		}
		if (!passwd.equals(passwd2)) {
			throw new BusinessException("密码验证失败！");
		}
		SysAdmin user = SessionUtils.getCurrentUser(request);
		if (user == null) {
			throw new BusinessException("未登录，不能修改！");
		}

		SysAdmin userCopy = service.get(user.getAdminCode());
		userCopy.setAdminPwd(Md5Utils.MD5(userCopy.getAdminCode() + Constants.USER_PASSWORD_SALT + passwd));
		service.save(request, userCopy);
	}

}
