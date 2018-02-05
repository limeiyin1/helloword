package com.redfinger.manager.common.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.SysMenu;

public class SessionUtils {
	
	public static final String currentUser="user";
	public static final String currentPermission="permission";
	public static final String currentSessionId="sessionId";
	
	public static void setCurrentUser(HttpServletRequest request,SysAdmin user){
		request.getSession().setAttribute(currentUser, user);
	}
	public static void removeCurrentUser(HttpServletRequest request){
		request.getSession().removeAttribute(currentUser);
	}
	
	public static SysAdmin getCurrentUser(HttpServletRequest request){
		if(request==null){
			return null;
		}
		HttpSession session =request.getSession();
		if(session.getAttribute(currentUser)==null){
			return null;
		}
		return (SysAdmin)session.getAttribute(currentUser);
	}
	
	public static String getCurrentUserId(HttpServletRequest request){
		SysAdmin user=getCurrentUser(request);
		if(user==null){
			return "";
		}else{
			return user.getAdminCode();
		}
	}
	
	public static boolean isUserLogin(HttpServletRequest request){
		if(getCurrentUser(request)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public static void setCurrentPermission(HttpServletRequest request,Map<String,SysMenu> permissionMap){
		request.getSession().setAttribute(currentPermission, permissionMap);
	}
	@SuppressWarnings("unchecked")
	public static Map<String,SysMenu> getCurrentPersmission(HttpServletRequest request){
		return (Map<String,SysMenu>)request.getSession().getAttribute(currentPermission);
	}
	
	public static void removeCurrentPersmission(HttpServletRequest request){
		request.getSession().removeAttribute(currentPermission);
	}
}
