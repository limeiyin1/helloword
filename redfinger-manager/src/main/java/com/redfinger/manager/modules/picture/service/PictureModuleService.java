package com.redfinger.manager.modules.picture.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.LogAdmin;
import com.redfinger.manager.common.domain.RfPictureModule;
import com.redfinger.manager.common.domain.RfPictureModuleExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.LogAdminMapper;
import com.redfinger.manager.common.mapper.RfPictureModuleMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "moduleId")
public class PictureModuleService extends BaseService<RfPictureModule, RfPictureModuleExample, RfPictureModuleMapper> {

	@Autowired
	LogAdminMapper logAdminMapper;
	
	public void saveModule(HttpServletRequest request,RfPictureModule bean) throws Exception{
		String path = request.getServletPath();
		this.save(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{moduleCode:[" + bean.getModuleCode() + "],modulename:["+ bean.getModuleName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("新增图片模块"+bean.getModuleName());
		admin.setName("新增图片模块"+bean.getModuleName());
		logAdminMapper.insert(admin);*/
	}
	
	public void updateModule(HttpServletRequest request,RfPictureModule bean) throws Exception{
		String path = request.getServletPath();
		this.update(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{moduleCode:[" + bean.getModuleCode() + "],modulename:["+ bean.getModuleName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("修改图片模块"+bean.getModuleName());
		admin.setName("修改图片模块"+bean.getModuleName());
		logAdminMapper.insert(admin);*/
	}
	
	public void startModule(HttpServletRequest request,RfPictureModule bean) throws Exception{
		String path = request.getServletPath();
		this.start(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{moduleCode:[" + bean.getModuleCode() + "],modulename:["+ bean.getModuleName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("启用图片模块"+bean.getModuleName());
		admin.setName("启用图片模块"+bean.getModuleName());
		logAdminMapper.insert(admin);*/
	}
	
	public void stopModule(HttpServletRequest request,RfPictureModule bean) throws Exception{
		String path = request.getServletPath();
		this.stop(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{moduleCode:[" + bean.getModuleCode() + "],modulename:["+ bean.getModuleName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("禁用图片模块"+bean.getModuleName());
		admin.setName("禁用图片模块"+bean.getModuleName());
		logAdminMapper.insert(admin);*/
	}
	
	public void removeModule(HttpServletRequest request,RfPictureModule bean) throws Exception{
		String path = request.getServletPath();
		this.remove(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{moduleCode:[" + bean.getModuleCode() + "],modulename:["+ bean.getModuleName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("删除图片模块"+bean.getModuleName());
		admin.setName("删除图片模块"+bean.getModuleName());
		logAdminMapper.insert(admin);*/
	}
}
