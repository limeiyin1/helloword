package com.redfinger.manager.modules.refund.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.domain.RfNotice;
import com.redfinger.manager.common.domain.RfRefund;
import com.redfinger.manager.common.domain.RfRefundAdmin;
import com.redfinger.manager.common.domain.RfRefundExample;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserData;
import com.redfinger.manager.common.domain.RfUserNotice;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfRefundMapper;
import com.redfinger.manager.common.utils.AdminRoleCode;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.Md5Utils;
import com.redfinger.manager.common.utils.NoticeTypeUtils;
import com.redfinger.manager.common.utils.RefundHandleStatus;
import com.redfinger.manager.common.utils.RefundLogType;
import com.redfinger.manager.common.utils.RefundStatus;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.member.service.UserDataService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.notice.service.NoticeService;
import com.redfinger.manager.modules.notice.service.UserNoticeService;
import com.redfinger.manager.modules.sys.service.AdminRoleService;

@Transactional
@Service
@PrimaryKeyAnnotation(field="refundId")
public class RefundService  extends BaseService<RfRefund, RfRefundExample, RfRefundMapper> {
	
	@Autowired
	private RefundLogService refundLogService;
	@Autowired
	private RefundAdminService refundAdminService;
	@Autowired
	private UserDataService userDataService;
	@Autowired
	private AdminRoleService adminRoleService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserPadService userPadService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private UserNoticeService userNoticeService;
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private RfRefundMapper refundMapper;
	
	public void look(HttpServletRequest request, Model model, RfRefund bean) throws Exception{
		SysAdmin admin = SessionUtils.getCurrentUser(request);
		
		RfRefund refund = this.get(bean.getRefundId());
		model.addAttribute("refund", refund);
		
		RfUser user = userService.get(refund.getUserId());
		model.addAttribute("user", user);

		RfUserPad userPad = userPadService.get(refund.getUserPadId());
		model.addAttribute("userPad", userPad);
		
		List<String> adminRoles = new ArrayList<String>();
		adminRoles.add(AdminRoleCode.SUPER_POWER);
		adminRoles.add(AdminRoleCode.SYSTEM_POWER);
		adminRoles.add(AdminRoleCode.CAIWU);
		int adminRoleCount = adminRoleService.initQuery().andEqualTo("adminCode", admin.getAdminCode()).andIn("roleCode", adminRoles).countStopTrue();
		
		RfUserData userData = null;
		//管理员、财务才能查看完成银行卡信息
		if(adminRoleCount > 0){
			userData = userDataService.getUserData(user.getUserId(), false);
		}else {
			userData = userDataService.getUserData(user.getUserId(), true);
		}
		model.addAttribute("userData", userData);
	}
	
	public boolean checkSecurePwd(RfRefund refund, String securePwd){
		RfUserData userData = userDataService.get(refund.getUserId());
		String securePwdEncode = Md5Utils.MD5(userData.getUserId() + "##" + securePwd);
		return securePwdEncode.equals(userData.getSecurePwd());
	}

	/**
	 * 退款受理
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public synchronized void accept(HttpServletRequest request, RfRefund bean) throws Exception{
		SysAdmin admin = SessionUtils.getCurrentUser(request);
		String[] refundIds = bean.getIds().split(",");
		
		RfRefund refund = null;
		for (String refundId : refundIds) {
			refund = this.get(Integer.parseInt(refundId));
			if(!YesOrNoStatus.YES.equals(refund.getStatus()) || !YesOrNoStatus.YES.equals(refund.getEnableStatus())){
				throw new BusinessException("退款申请数据状态异常");
			}
			if(StringUtils.isNotBlank(refund.getHandleAdmin())){
				throw new BusinessException("退款申请已被"+refund.getHandleAdmin()+"受理,请刷新页面再试");
			}
			if(!RefundHandleStatus.USER_APPLY.equals(refund.getHandleStatus()) && !RefundHandleStatus.SYSTEM_APPLY.equals(refund.getHandleStatus())
					&& !RefundHandleStatus.VERIFY.equals(refund.getHandleStatus())){
				throw new BusinessException("退款申请非待受理状态,请刷新页面再试");
			}
			
			String remark = null, logType = null;
			if(RefundHandleStatus.USER_APPLY.equals(refund.getHandleStatus()) || RefundHandleStatus.SYSTEM_APPLY.equals(refund.getHandleStatus())){
				refund.setRefundStatus(RefundStatus.ACCEPT);	//外部状态
				refund.setHandleAdmin(admin.getAdminCode());
				refund.setHandleStatus(RefundHandleStatus.APPLE_ACCEPT);	//内部状态
				refund.setModifier(admin.getAdminCode());
				refund.setModifyTime(new Date());
				refundMapper.updateStatusByPrimaryKey(refund);
				
				remark = admin.getAdminCode()+"受理退款申请";
				logType = RefundLogType.APPLE_ACCEPT;
			}else if(RefundHandleStatus.VERIFY.equals(refund.getHandleStatus())){
				refund.setRefundStatus(RefundStatus.VERIFY);	//外部状态，审核中
				refund.setHandleAdmin(admin.getAdminCode());
				refund.setHandleStatus(RefundHandleStatus.VERIFY_ACCEPT);	//内部状态，审核受理
				refund.setModifier(admin.getAdminCode());
				refund.setModifyTime(new Date());
				refundMapper.updateStatusByPrimaryKey(refund);
				
				remark = admin.getAdminCode()+"退款审核受理";
				logType = RefundLogType.VERIFY_ACCEPT;
			}
			refundLogService.saveRefundLog(request, refund, admin.getAdminCode(), logType, remark);
			
			RfRefundAdmin refundAdmin = new RfRefundAdmin();
			refundAdmin.setRefundId(refund.getRefundId());
			refundAdmin.setAdminCode(admin.getAdminCode());
			refundAdmin.setStatus(YesOrNoStatus.YES);
			refundAdmin.setEnableStatus(YesOrNoStatus.YES);
			refundAdmin.setCreater(admin.getAdminCode());
			refundAdmin.setCreateTime(new Date());
			refundAdminService.save(request, refundAdmin);
		}
	}
	
	/**
	 * 退款受理处理
	 * @param request
	 * @param refund
	 * @throws Exception
	 */
	public synchronized void refundHandle(HttpServletRequest request, RfRefund bean, String noticeContent, MultipartHttpServletRequest fileRequest) throws Exception{
		SysAdmin admin = SessionUtils.getCurrentUser(request);
		RfRefund refund = this.get(bean.getRefundId());
		if(!YesOrNoStatus.YES.equals(refund.getStatus()) || !YesOrNoStatus.YES.equals(refund.getEnableStatus())){
			throw new BusinessException("退款申请数据状态异常");
		}
		if(!admin.getAdminCode().equals(refund.getHandleAdmin())){
			throw new BusinessException("当前退款申请只能由"+refund.getHandleAdmin()+"处理,请刷新页面再试");
		}

		String remark = null, logType = null;;
		if(RefundHandleStatus.VERIFY.equals(bean.getHandleStatus())){
			if(!RefundHandleStatus.APPLE_ACCEPT.equals(refund.getHandleStatus())){
				throw new BusinessException("非退款申请受理状态，处理失败");
			}
			refund.setRefundStatus(RefundStatus.VERIFY);	//外部状态，审核中
			refund.setHandleAdmin(null);
			refund.setHandleOpinion(bean.getHandleOpinion());
			refund.setHandleStatus(RefundHandleStatus.VERIFY);	//内部状态,审核待受理
			refund.setModifier(admin.getAdminCode());
			refund.setModifyTime(new Date());
			refundMapper.updateStatusByPrimaryKey(refund);
			
			remark = admin.getAdminCode()+"退款受理后处理";
			logType = RefundLogType.ACCEPT_HANDLE;
		}else if(RefundHandleStatus.REFUND.equals(bean.getHandleStatus())){
			if(!RefundHandleStatus.VERIFY_ACCEPT.equals(refund.getHandleStatus())){
				throw new BusinessException("非退款审核受理状态，处理失败");
			}
			String refundImgName = "refundImgName";
			Map<String, MultipartFile> fileMap = fileRequest.getFileMap();
			MultipartFile file = fileMap.get(refundImgName);
			if(file == null || file.isEmpty()){
				throw new BusinessException("退款截图不能为空");
			}
			Map<String, String> uploadMap = fileUtils.saveImageFile(file, filePathUtils.getImageUrl()+"/refund", filePathUtils.getImageLinkUrl()+"/refund");
			String refundImg = uploadMap.get(FileUtils.FILE_PATH_KEY);
			refund.setRefundImg(refundImg);
			
			refund.setRefundStatus(RefundStatus.REFUND);	//外部状态，已退款
			refund.setRefundTime(new Date());
			refund.setHandleOpinion(bean.getHandleOpinion());
			refund.setHandleStatus(RefundHandleStatus.REFUND);	//内部状态,已退款
			refund.setModifier(admin.getAdminCode());
			refund.setModifyTime(new Date());
			refundMapper.updateStatusByPrimaryKey(refund);
			
			//解绑设备
			userPadService.relieveSvip(request, refund.getUserPadId());
			
			//解绑设备日志
			remark = admin.getAdminCode()+"解绑设备,userPadId="+refund.getUserPadId()+",padCode="+refund.getPadCode();
			refundLogService.saveRefundLog(request, refund, admin.getAdminCode(), RefundLogType.UNBIND_PAD, remark);
			
			//发送用户公告
			pushNotice(request, "SVIP退款申请", noticeContent, refund.getUserId());
			
			remark = admin.getAdminCode()+"完成退款";
			logType = RefundLogType.REFUND;
		}else{
			throw new BusinessException("退款处理状态码非法");
		}
		
		refundLogService.saveRefundLog(request, refund, admin.getAdminCode(), logType, remark);
	}
	
	/**
	 * 取消退款
	 * @param request
	 * @param bean
	 * @param handleOpinion
	 * @throws Exception
	 */
	public synchronized void cancelRefund(HttpServletRequest request, RfRefund bean, String noticeContent) throws Exception{
		if(StringUtils.isBlank(bean.getHandleOpinion())){
			throw new BusinessException("处理意见不能为空");
		}
		SysAdmin admin = SessionUtils.getCurrentUser(request);
		RfRefund refund = this.get(bean.getRefundId());
		if(!YesOrNoStatus.YES.equals(refund.getStatus()) || !YesOrNoStatus.YES.equals(refund.getEnableStatus())){
			throw new BusinessException("退款申请数据状态异常");
		}
		if(!admin.getAdminCode().equals(refund.getHandleAdmin())){
			throw new BusinessException("当前退款申请只能由"+refund.getHandleAdmin()+"处理,请刷新页面再试");
		}
		if(RefundHandleStatus.REFUND.equals(refund.getHandleStatus())){
			throw new BusinessException("退款申请已完成退款，不能取消");
		}
		if(RefundHandleStatus.FINISH.equals(refund.getHandleStatus())){
			throw new BusinessException("退款申请已归档，不能取消");
		}
		refund.setRefundStatus(RefundStatus.CANCEL);	//外部状态，取消退款
		refund.setHandleOpinion(bean.getHandleOpinion());
		refund.setHandleStatus(RefundHandleStatus.CANCEL);	//内部状态,取消退款
		refund.setModifier(admin.getAdminCode());
		refund.setModifyTime(new Date());
		refundMapper.updateStatusByPrimaryKey(refund);

		refundLogService.saveRefundLog(request, refund, admin.getAdminCode(), RefundLogType.CANCEL, bean.getHandleOpinion());
		
		//发送用户公告
		pushNotice(request, "SVIP退款申请", noticeContent, refund.getUserId());
	}
	
	/**
	 * 退款申请退回
	 * @param request
	 * @param bean
	 * @param handleOpinion
	 * @throws Exception
	 */
	public synchronized void backRefund(HttpServletRequest request, RfRefund bean, String noticeContent) throws Exception{
		if(StringUtils.isBlank(bean.getHandleOpinion())){
			throw new BusinessException("处理意见不能为空");
		}
		SysAdmin admin = SessionUtils.getCurrentUser(request);
		RfRefund refund = this.get(bean.getRefundId());
		if(!YesOrNoStatus.YES.equals(refund.getStatus()) || !YesOrNoStatus.YES.equals(refund.getEnableStatus())){
			throw new BusinessException("退款申请数据状态异常");
		}
		if(!admin.getAdminCode().equals(refund.getHandleAdmin())){
			throw new BusinessException("当前退款申请只能由"+refund.getHandleAdmin()+"处理,请刷新页面再试");
		}
		if(RefundHandleStatus.REFUND.equals(refund.getHandleStatus())){
			throw new BusinessException("退款申请已完成退款，不能退回");
		}
		if(RefundHandleStatus.FINISH.equals(refund.getHandleStatus())){
			throw new BusinessException("退款申请已归档，不能退回");
		}
		refund.setRefundStatus(RefundStatus.BACK);	//外部状态，退款申请退回给用户
		refund.setHandleStatus(RefundHandleStatus.BACK);	//内部状态,退款申请退回给用户
		refund.setHandleOpinion(bean.getHandleOpinion());
		refund.setModifier(admin.getAdminCode());
		refund.setModifyTime(new Date());
		refundMapper.updateStatusByPrimaryKey(refund);

		refundLogService.saveRefundLog(request, refund, admin.getAdminCode(), RefundLogType.BACK, bean.getHandleOpinion());
		
		//发送用户公告
		pushNotice(request, "SVIP退款申请", noticeContent, refund.getUserId());
	}
	
	/**
	 * 归档
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public synchronized void finishRefund(HttpServletRequest request, RfRefund bean) throws Exception{
		SysAdmin admin = SessionUtils.getCurrentUser(request);
		String[] refundIds = bean.getIds().split(",");
		for (String refundId : refundIds) {
			RfRefund rf = this.get(Integer.valueOf(refundId));
			
			RfRefund refund = new RfRefund();
			refund.setRefundId(Integer.valueOf(refundId));
			refund.setHandleStatus(RefundHandleStatus.FINISH);
			refund.setHandleAdmin(null);
			refund.setModifier(admin.getAdminCode());
			refund.setModifyTime(new Date());
			refundMapper.updateStatusByPrimaryKey(refund);
			
			refundLogService.saveRefundLog(request, rf, admin.getAdminCode(), RefundLogType.FINISH, "退款申请归档");
		}
	}
	
	/**
	 * 发送公告
	 * @param request
	 * @param title
	 * @param content
	 * @param userId
	 * @throws Exception
	 */
	private void pushNotice(HttpServletRequest request,String title,String content,Integer userId) throws Exception{
		RfNotice notice = new RfNotice();
		notice.setNoticeTitle(title);
		notice.setNoticeContent(content);
		notice.setPopStatus("1");	//弹出
		notice.setStatus(YesOrNoStatus.YES);
		notice.setEnableStatus(YesOrNoStatus.YES);
		notice.setNoticeType(NoticeTypeUtils.PART);	//指定用户发送
		noticeService.save(request, notice);
		
		RfUserNotice userNotice = new RfUserNotice();
		userNotice.setNoticeId(notice.getNoticeId());
		userNotice.setUserId(userId);
		userNotice.setUserNoticeStatus("0");	//未读
		userNoticeService.save(request, userNotice);
	}
}
