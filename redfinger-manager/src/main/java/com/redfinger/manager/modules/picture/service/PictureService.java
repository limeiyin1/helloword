package com.redfinger.manager.modules.picture.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.domain.LogAdmin;
import com.redfinger.manager.common.domain.RfPicture;
import com.redfinger.manager.common.domain.RfPictureExample;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.LogAdminMapper;
import com.redfinger.manager.common.mapper.RfPictureMapper;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "pictureId")
public class PictureService extends BaseService<RfPicture, RfPictureExample, RfPictureMapper> {
	@Autowired
	LogAdminMapper logAdminMapper;
	
	public void savePicture(HttpServletRequest request,RfPicture bean) throws Exception{
		String path = request.getServletPath();
		this.save(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{pictureName:["+ bean.getPictureName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("新增图片"+bean.getPictureName());
		admin.setName("新增图片"+bean.getPictureName());
		logAdminMapper.insert(admin);*/
	}
	
	public void updatePicture(HttpServletRequest request,RfPicture bean) throws Exception{
		String path = request.getServletPath();
		this.update(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{pictureName:["+ bean.getPictureName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("修改图片"+bean.getPictureName());
		admin.setName("修改图片"+bean.getPictureName());
		logAdminMapper.insert(admin);*/
	}
	
	public void startPicture(HttpServletRequest request,RfPicture bean) throws Exception{
		String path = request.getServletPath();
		this.start(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{pictureName:["+ bean.getPictureName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("启用图片"+bean.getPictureName());
		admin.setName("启用图片"+bean.getPictureName());
		logAdminMapper.insert(admin);*/
	}
	
	public void stopPicture(HttpServletRequest request,RfPicture bean) throws Exception{
		String path = request.getServletPath();
		this.stop(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{pictureName:["+ bean.getPictureName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("禁用图片"+bean.getPictureName());
		admin.setName("禁用图片"+bean.getPictureName());
		logAdminMapper.insert(admin);*/
	}
	
	public void removePicture(HttpServletRequest request,RfPicture bean) throws Exception{
		String path = request.getServletPath();
		this.remove(request, bean);
		/*LogAdmin admin=new LogAdmin();
		admin.setCreater(SessionUtils.getCurrentUserId(request));
		admin.setCreateTime(new Date());
		admin.setCategory(path);
		admin.setOperIp(StringUtils.getRemoteAddr(request));
		admin.setParamIn("{pictureName:["+ bean.getPictureName() +"]}");
		admin.setResultStatus(YesOrNoStatus.YES);
		admin.setExceptionMsg("");
		admin.setRemark("删除图片"+bean.getPictureName());
		admin.setName("删除图片"+bean.getPictureName());
		logAdminMapper.insert(admin);*/
	}
}
