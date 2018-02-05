package com.redfinger.manager.modules.upload.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserUpload;
import com.redfinger.manager.common.jsm.ScriptProducer;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.upload.service.UserUploadService;

@Controller
@RequestMapping(value="/upload/upload")
public class UserUploadController extends BaseController {
	@Autowired
	UserUploadService service;
	@Autowired 
	UserService userService;
	@Autowired
	PadService padService;
	@Autowired
	ScriptProducer scriptProducer;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value="list")
	public PageInfo<RfUserUpload> list(HttpServletRequest request,HttpServletResponse response,Model model,RfUserUpload bean,Integer externalUserId)throws Exception{
		//前端查询条件
		RfUser user = null;
		RfPad pad = null;
		if(StringUtils.isNotBlank(bean.getUserMobilePhone()) || StringUtils.isNotBlank(bean.getUserEmail())||externalUserId!=null){
			List<RfUser> userList = userService.initQuery()
					.andEqualTo("userMobilePhone", bean.getUserMobilePhone())
					.andEqualTo("externalUserId", externalUserId)
					.andEqualTo("userEmail", bean.getUserEmail())
					.findAll();
			user = Collections3.isEmpty(userList) ? null : userList.get(0);
			if(user == null) {
				user = new RfUser();
				user.setUserId(-1);
			}
		}
		if(StringUtils.isNotBlank(bean.getPadCode())){
			pad = padService.getPadByPadCode(bean.getPadCode());
			if(pad == null) {
				pad = new RfPad();
				pad.setPadId(-1);
			}
		}
		
		//上传记录列表
		List<RfUserUpload> uploadList = service.initQuery(bean).andGreaterThanOrEqualTo("createTime",DateUtils.parseDate(bean.getBeginTimeStr()+" 00:00:00"))
				.andLessThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()+" 23:59:59"))
				.andEqualTo("uploadStatus", bean.getUploadStatus())
				.andEqualTo("status", ConstantsDb.globalStatusNomarl())
				.andEqualTo("userId", user!=null?user.getUserId():null)
				.andEqualTo("padId", pad!=null?pad.getPadId():null)
				.addOrderForce(bean.getSort(),bean.getOrder())
				.addOrderClause("createTime", "desc")
				.pageAll(bean.getPage(), bean.getRows());
		
		List<Integer> userIds = new ArrayList<Integer>(), padIds = new ArrayList<Integer>();
		for (RfUserUpload userUpload : uploadList) {
			userIds.add(userUpload.getUserId());
			padIds.add(userUpload.getPadId());
		}
		//上传记录关联的用户、设备
		Map<Integer, Object> userMap = new HashMap<Integer, Object>(), padMap = new HashMap<Integer, Object>();
		if(userIds.size() > 0){
			List<RfUser> userList = userService.initQuery().andIn("userId", userIds).findAll();
			for (RfUser rfUser : userList) {
				userMap.put(rfUser.getUserId(), rfUser);
			}
		}
		if(padIds.size() > 0){
			List<RfPad> padList = padService.initQuery().andIn("padId", padIds).findAll();
			for (RfPad rfPad : padList) {
				padMap.put(rfPad.getPadId(), rfPad);
			}
		}
		
		for (RfUserUpload userUpload : uploadList) {
			user = (RfUser)userMap.get(userUpload.getUserId());
			pad = (RfPad)padMap.get(userUpload.getPadId());
			if(user != null){
				userUpload.setUserMobilePhone(user.getUserMobilePhone());
				userUpload.setUserEmail(user.getUserEmail());
			}
			if(pad != null){
				userUpload.setPadCode(pad.getPadCode());
			}
			
			/** 查询客户端ID*/
			if(userUpload.getUserId() != null){
				/** 根据用户Id查询用户*/
				RfUser rfUser = userService.load(userUpload.getUserId());
				/** 查询客户端ID*/
				userUpload.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
			}
		}
		PageInfo<RfUserUpload> pageInfo = new PageInfo<RfUserUpload>(uploadList);
		return pageInfo;
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfUserUpload bean) throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfUserUpload bean) throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "transmission")
	public void transmission(HttpServletRequest request, HttpServletResponse response, String ids) throws Exception{
		if(StringUtils.isBlank(ids)){
			throw new Exception("请选择需要上传文件的记录");
		}
		
		String[] uploadIds = ids.split(",");
		for (String id : uploadIds) {
			scriptProducer.sendMessage(id);
		}
	}
}