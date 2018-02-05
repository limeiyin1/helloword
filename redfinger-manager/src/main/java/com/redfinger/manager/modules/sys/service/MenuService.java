package com.redfinger.manager.modules.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.SysMenu;
import com.redfinger.manager.common.domain.SysMenuExample;
import com.redfinger.manager.common.helper.PermissionHelper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.SysMenuMapper;
import com.redfinger.manager.common.utils.StringUtils;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "menuCode")
public class MenuService extends BaseService<SysMenu, SysMenuExample, SysMenuMapper> {
	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, SysMenu bean) {
		SysMenu sysMenu = this.initQuery().get(bean.getMenuCode());
		if (sysMenu != null) {
			return "该用户编号已被使用！";
		}
		if (bean.getParentMenuCode() != null && !bean.getParentMenuCode().equals("0")) {
			SysMenu parent = this.get(bean.getParentMenuCode());
			if (parent.getMenuLayer()==1) {
				bean.setMenuLayer(2);
			} else if (parent.getMenuLayer() == 2) {
				bean.setMenuLayer(3);
			}
		} else {
			bean.setMenuLayer(1);
			bean.setParentMenuCode("0");
		}
		// 纠正URL错误
		if (bean.getMenuLayer() == 2 || bean.getMenuLayer() == 3) {
			String url = bean.getMenuUri();
			if (!url.startsWith("http") && !url.startsWith("/")) {
				url = "/" + url;
			}
			if (url.endsWith("/")) {
				url = url.substring(0, url.length() - 1);
			}
			bean.setMenuUri(url);
		}
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.pre_update)
	public String preUpdate(HttpServletRequest request, SysMenu bean) {
		SysMenu sysMenu = this.initQuery().get(bean.getMenuCode());
		if (sysMenu.getMenuLayer() == 2 || sysMenu.getMenuLayer() == 3) {
			String url = bean.getMenuUri();
			if (!url.startsWith("http") && !url.startsWith("/")) {
				url = "/" + url;
			}
			if (url.endsWith("/")) {
				url = url.substring(0, url.length() - 1);
			}
			bean.setMenuUri(url);
		}
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.pre_delete)
	public String preDelete(HttpServletRequest request, SysMenu bean) {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String id : idArray) {
			List<SysMenu> list = this.initQuery().andEqualTo("parentMenuCode", id).singleStopTrue();
			if (!CollectionUtils.isEmpty(list)) {
				return "此记录有下级数据，不能禁用！";
			}
		}
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.pre_stop)
	public String preStop(HttpServletRequest request, SysMenu bean) {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String id : idArray) {
			List<SysMenu> list = this.initQuery().andEqualTo("parentMenuCode", id).singleStopTrue();
			if (!CollectionUtils.isEmpty(list)) {
				return "此记录有下级数据，不能禁用！";
			}
		}
		return null;
	}
	
	
	@ServiceAnnotation(name = ServiceMethod.after_save)
	public String afterSave(HttpServletRequest request, SysMenu bean) {
		PermissionHelper.initPermission();
		return null;
	}
	@ServiceAnnotation(name = ServiceMethod.after_update)
	public String afterUpdate(HttpServletRequest request, SysMenu bean) {
		PermissionHelper.initPermission();
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.after_start)
	public String afterStart(HttpServletRequest request, SysMenu bean) {
		PermissionHelper.initPermission();
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.after_stop)
	public String afterStop(HttpServletRequest request, SysMenu bean) {
		PermissionHelper.initPermission();
		return null;
	}
	@ServiceAnnotation(name = ServiceMethod.after_delete)
	public String afterDelete(HttpServletRequest request, SysMenu bean) {
		PermissionHelper.initPermission();
		return null;
	}
}
