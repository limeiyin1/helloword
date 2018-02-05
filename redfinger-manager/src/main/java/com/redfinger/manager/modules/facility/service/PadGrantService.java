package com.redfinger.manager.modules.facility.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.OperateConstants;
import com.redfinger.manager.common.domain.RfPadGrant;
import com.redfinger.manager.common.domain.RfPadGrantCode;
import com.redfinger.manager.common.domain.RfPadGrantExample;
import com.redfinger.manager.common.domain.RfPadGrantLog;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.RfPadGrantCodeMapper;
import com.redfinger.manager.common.mapper.RfPadGrantLogMapper;
import com.redfinger.manager.common.mapper.RfPadGrantMapper;
import com.redfinger.manager.common.utils.OperateTypeUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.grant.service.PadGrantCodeService;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "grantId")
public class PadGrantService  extends BaseService<RfPadGrant, RfPadGrantExample, RfPadGrantMapper> {
	@Autowired
	RfPadGrantLogMapper grantLogMapper;
	@Autowired
	RfPadGrantCodeMapper rfPadGrantCodeMapper;
	@Autowired
	PadGrantCodeService codeService;
	@Autowired
	RfPadGrantMapper padGrantMapper;
	
	public void padGrantOperate(HttpServletRequest request,Integer userPadId,String operateType) throws Exception{
		//RfPadGrant grant = null;
		RfPadGrantCode code = null;
		RfPadGrantLog grantLog = new RfPadGrantLog();
		if(null != userPadId){
			List<RfPadGrant> list = this.initQuery().andEqualTo("userPadId", userPadId).findStopTrue();
			List<RfPadGrantCode> codes = codeService.findByUserPadId(userPadId);
			if(null!=codes && codes.size()>0){
				code = codes.get(0);
				log.info("授权码禁用,code："+code.getGrantCode());
			    /*BeanUtils.copyProperties(code, log);
			    log.setOperateType(OperateTypeUtils.USER_EABLE_GAVE);
				log.setOperateTime(new Date());
				log.setRemark(getRemark(operateType,code.getRemark()));
				grantLogMapper.insert(log);*/
				code.setEnableStatus(YesOrNoStatus.NO);
				if(StringUtils.isNotEmpty(code.getRemark())){
					code.setRemark(getRemark(operateType,code.getRemark()));
				}else{
					code.setRemark(getRemark(operateType,""));
				}
				codeService.update(request, code);
			}
			
			/** 目前增加了管理员授权的功能,所以授权信息可能不止有一条 */
			if(null != list && list.size() > 0){
				for (RfPadGrant grant : list) {
					log.info("设备授权删除,grantId："+grant.getGrantId());
					BeanUtils.copyProperties(grant, grantLog);
					grantLog.setOperateType(OperateTypeUtils.USER_EABLE_GAVE);
					grantLog.setOperateTime(new Date());
					if(StringUtils.isNotEmpty(grant.getRemark())){
						grantLog.setRemark(getRemark(operateType,grant.getRemark()));
					}else{
						grantLog.setRemark(getRemark(operateType,""));
					}
					
					grantLogMapper.insert(grantLog);
					this.remove(request, grant.getGrantId());
				}
			}
		}
	}
	
	public void cancelGrant(HttpServletRequest request, RfPadGrant bean) throws Exception {
		RfPadGrantLog log = new RfPadGrantLog();
		if(null != bean && null != bean.getGrantId()){
			BeanUtils.copyProperties(bean, log);
			/** 只要是后台进行取消授权的都是管理员取消授权  */
			log.setOperateType(OperateTypeUtils.ADMIN_ENABLE_GAVE);
			log.setOperateTime(new Date());
			grantLogMapper.insert(log);
			this.remove(request, bean.getGrantId());
		}
	}
	
	public String getRemark(String operateType,String remark){
		String str = "";
		if(OperateConstants.AdminUnbundling.equals(operateType)){
			str = "管理员取消绑定."+remark;
		}
		if(OperateConstants.NomalUnbundling.equals(operateType)){
			str = "取消绑定."+remark;
		}
		if(OperateConstants.VipUnbundling.equals(operateType)){
			str = "VIP取消绑定."+remark;
		}
		if(OperateConstants.ReplacePad.equals(operateType)){
			str = "更换设备."+remark;
		}
		if(OperateConstants.GvipUnbundling.equals(operateType)){
			str = "GVIP取消绑定."+remark;
		}
		if(OperateConstants.UnKnowUnbundling.equals(operateType)){
			str = "未知取消绑定."+remark;
		}
		if(OperateConstants.SvipUnbundling.equals(operateType)){
			str = "SVIP取消绑定."+remark;
		}
		return str;
	}

	public int getPadGrantCountByUserPadAndGrantType(Integer userPadId,
			String grantType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userPadId", userPadId);
		params.put("status", "1");
		params.put("enableStatus", "1");
		/** 授权类型必须是管理员授权才能看到,0表示管理员授权,1表示用户授权 */
		params.put("grantType", "0");
		
		return padGrantMapper.countByUserPadAndGrantType(params);
	}

	public Integer savePadGrantByAccount(HttpServletRequest request,Integer granteeUserId, RfUserPad userPad,
			String grantOperate, Date grantExpireTime) {
		
		/** 首先获取到当前的管理员 */
		String adminName = SessionUtils.getCurrentUserId(request);
		
		RfPadGrant padGrant = new RfPadGrant();
		padGrant.setUserPadId(userPad.getUserPadId());
		padGrant.setUserId(granteeUserId);
		padGrant.setPadId(userPad.getPadId());
		/** 管理员代替用户授权,所以授权人应该是用户,如果此处授权人不是用户,客户端无法查询到该设备*/
		padGrant.setGrantorUserId(userPad.getUserId());
		padGrant.setGrantCodeId(null);
		/** 采用指定账号的方式进行授权 */
		padGrant.setGrantMode("1");
		/** 设置控制权限 */
		padGrant.setGrantControl("1".equals(grantOperate)?"1":"0"); 
		padGrant.setGrantWatch("1");	//默认观看权限
		Date date = new Date();
		padGrant.setGrantStartTime(date);
		if(grantExpireTime == null){
			padGrant.setGrantEndTime(userPad.getExpireTime());	//授权失效时间默认为设备失效时间
		}else {
			padGrant.setGrantEndTime(grantExpireTime);
		}
		padGrant.setGrantTime(date);
		/** 设置授权类型,0表示管理员授权,1表示用户授权 */
		padGrant.setGrantType("0");
		padGrant.setStatus("1");
		padGrant.setEnableStatus("1");
		/** 设置创建人为当前的管理员 */
		padGrant.setCreater(String.valueOf(adminName));
		padGrant.setCreateTime(date);
		/** 插入的时候如果userPadId存在且grant_type == 0 说明管理员已经授权过一次了,就无法插入 */
		Integer grantCount = padGrantMapper.insertByNotExists(padGrant);
		if(grantCount > 0){
			//设备授权操作日志
			RfPadGrantLog padGrantLog = new RfPadGrantLog();
			BeanUtils.copyProperties(padGrant, padGrantLog);
			/**  4表示用户是管理员进行授权  */
			padGrantLog.setOperateType(OperateTypeUtils.ADMIN_GAVE);
			padGrantLog.setOperateTime(date);
			grantLogMapper.insert(padGrantLog);
		}
		return grantCount;
	}

	/**
	 * 根据用户id和设备id查询设备授权对象
	 * @param userId 用户id
	 * @param padId 设备id
	 * @return 设备授权对象
	 */
	public int selectCountRfPadGrantByUserIdAndPadId(Integer userId,
			Integer padId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("padId", padId);
		
		return padGrantMapper.selectCountRfPadGrantByUserIdAndPadId(params);
	}
}
