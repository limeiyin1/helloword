package com.redfinger.manager.modules.facility.service;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.OperateConstants;
import com.redfinger.manager.common.constants.PadClassify;
import com.redfinger.manager.common.constants.RelievePadClassify;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfRefund;
import com.redfinger.manager.common.domain.RfRefundExample;
import com.redfinger.manager.common.domain.RfRefundLog;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.RfUserPadExample;
import com.redfinger.manager.common.domain.RfUserPadLog;
import com.redfinger.manager.common.domain.RfWechartTemplate;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.jsm.FingerProducer;
import com.redfinger.manager.common.jsm.WeixinProducer;
import com.redfinger.manager.common.mapper.RfRefundLogMapper;
import com.redfinger.manager.common.mapper.RfRefundMapper;
import com.redfinger.manager.common.mapper.RfUserMapper;
import com.redfinger.manager.common.mapper.RfUserPadMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.RefundLogType;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.base.service.ResPhonService;
import com.redfinger.manager.modules.goods.service.GoodsService;
import com.redfinger.manager.modules.group.service.UserPadGroupService;
import com.redfinger.manager.modules.log.service.PadHandleLogService;
import com.redfinger.manager.modules.log.service.UserPadLogService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.message.service.MessageNotifyService;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.wechart.service.WechartTemplateService;
import com.redfinger.manager.modules.wechart.service.WechartUserService;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "userPadId")
public class UserPadService extends BaseService<RfUserPad, RfUserPadExample, RfUserPadMapper> {
	@Autowired
	UserService userService;
	@Autowired
	UserPadLogService userPadLogService;
	@Autowired
	PadService padService;
	@Autowired
	ResPhonService resPhoneService;
	@Autowired
	PadHandleLogService padHandleLogService;
	@Autowired
	AdminService adminService;
	@Autowired
	ConfigService configService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	PadGrantService padGrantService;
	@Autowired
	RfRefundMapper refundMapper;
	@Autowired
	RfRefundLogMapper refundLogMapper;
	@Autowired
	RfUserPadMapper userPadMapper;
	@Autowired
	RfUserMapper userMapper;
	@Autowired
	MessageNotifyService notifyService;
	@Autowired
	WechartTemplateService wechartTemplateService;
	@Autowired
	WechartUserService wechartUserService;
	@Autowired
	UserPadGroupService userPadGroupService;
	

	
	// 解绑操作
	public synchronized void relieve(HttpServletRequest request, Integer id) throws Exception {
		// 会员设备信息 一个设备只能给一个用户使用
		// 修改设备绑定状态
		Date relieveTime = new Date();
		RfUserPad userPad = this.get(id);
		RfUser user=userService.get(userPad.getUserId());
		if(null!=user.getUserEmail()||!("".equals(user.getUserEmail())))
		{
		SysAdmin admin=adminService.get(user.getUserEmail());
		if(admin!=null){
			throw new BusinessException("管理员帐号，无法在该功能解绑");
		}}
		RfPad pad = padService.get(userPad.getPadId());
		if (ConstantsDb.rfPadPadStatusControl().equals(pad.getPadStatus())) {
			throw new BusinessException("受控中，无法解绑");
		}
		if(!(ConstantsDb.rfUserPadPadGradeGeneral().equals(userPad.getPadGrade()))){
			throw new BusinessException("VIP绑定设备，无法解绑");
		}
		//解绑时保存剩余普通设备时间到rf_user
		userPad.setModifyTime(relieveTime);
		userService.saveLeftOnlineTime(request,userPad);
		
		//需要将设备授权表中数据删掉
		padGrantService.padGrantOperate(request, userPad.getUserPadId(),OperateConstants.NomalUnbundling);
		
		//解绑时把user_pad中的 信息写入到user_pad_log中保存
		this.relieveLog(request, relieveTime, userPad);
		this.remove(request, id);
		// 设备操作记录
		pad.setModifyTime(relieveTime);
		pad.setModifyTime(relieveTime);
		pad.setModifier(SessionUtils.getCurrentUserId(request));
		pad.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
		pad.setEnableStatus(ConstantsDb.rfPadEnableStatusRelieve());
		padService.modif(request,pad);
		padHandleLogService.relievePad(request, pad);
	}

	// user_pad_log记录解绑
	public void relieveLog(HttpServletRequest request, Date time, RfUserPad userPad) throws Exception {
		// 解绑时user_pad直接拷贝到user_pad_log上 修改时间写解绑时间，备注解绑
		RfUserPadLog padLog = new RfUserPadLog();
		PropertyUtils.copyProperties(padLog, userPad);
		padLog.setCreateTime(time);
		padLog.setModifier(SessionUtils.getCurrentUserId(request));
		padLog.setModifyTime(time);
		padLog.setOldPadId(userPad.getPadId());
		if(null==padLog.getRemark()||"".equals(padLog.getRemark())||"null".equals(padLog.getRemark())){
			padLog.setRemark("解绑");
			}else{
		padLog.setRemark(padLog.getRemark() + ",解绑");
			}
		userPadLogService.userPadLog(request, padLog);
	}

	// 绑定
	public void binding(HttpServletRequest request, RfUserPad bean) throws Exception {
		//判断用户是否符合设备绑定条件
		RfUser user = userService.initQuery().get(bean.getUserId());
		if (user == null) {
			throw new BusinessException("该帐号无效，不能绑定");
		}
		if (user.getBindPadMax() == null || user.getBindPadMax() == 0) {
			throw new BusinessException("该用户绑定设备数量不足");
		}
		//判断用户是否还能绑定普通设备
		List<RfUserPad> userPad = this.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padGrade", ConstantsDb.rfUserPadPadGradeGeneral()).findStopTrue();
		if(userPad.size()>=Integer.parseInt(ConfigConstantsDb.configMemberFacility())){
			throw new BusinessException("该用户可绑定普通设备数已达到上限");
		}
		if ("".equals(user.getUserMobilePhone()) || user.getUserMobilePhone() == null) {
			throw new BusinessException("用户帐号没有绑定手机号码");
		}
		RfPad pad = padService.getLock(bean.getPadId());
		this.savebinding(request, bean);
		// 修改设备绑定状态记录 user_pad_log
		RfUserPadLog padLog = new RfUserPadLog();
		PropertyUtils.copyProperties(padLog, bean);
		if(null==padLog.getRemark()||"".equals(padLog.getRemark())){
			padLog.setRemark("绑定");
		}else{
			padLog.setRemark("绑定");
		}
		padLog.setCreateTime(new Date());
		padLog.setModifyTime(new Date());
		padLog.setNewPadId(bean.getPadId());
		userPadLogService.userPadLog(request, padLog);
		// 设备绑定日志log_pad
		pad.setModifyTime(bean.getBindTime());
		padHandleLogService.bindingPad(request, pad);
	}
	
	//绑定普通或者体验设备，用于会员中心批量绑定设备
	public void bindNormalOrTastePad(HttpServletRequest request, RfUserPad bean,String padGrade,RfUser user) throws Exception {
		if (user == null) {
			throw new BusinessException("该帐号无效，不能绑定");
		}
		if (user.getBindPadMax() == null || user.getBindPadMax() == 0) {
			throw new BusinessException("该用户绑定设备数量不足");
		}
		if(org.apache.commons.lang.StringUtils.equals(padGrade, ConstantsDb.rfUserPadPadGradeGeneral())){//如果是普通设备
			//判断用户是否还能绑定普通设备
			List<RfUserPad> userPad = this.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padGrade", ConstantsDb.rfUserPadPadGradeGeneral()).findStopTrue();
			if(userPad.size()>=Integer.parseInt(ConfigConstantsDb.configMemberFacility())){
				throw new BusinessException("该用户可绑定普通设备数已达到上限");
			}
		}
		
		if ("".equals(user.getUserMobilePhone()) || user.getUserMobilePhone() == null) {
			throw new BusinessException("用户帐号没有绑定手机号码");
		}
		RfPad pad = padService.getLock(bean.getPadId());
		this.savebindNormalOrTastePad(request, bean, padGrade,user);
		// 修改设备绑定状态记录 user_pad_log
		RfUserPadLog padLog = new RfUserPadLog();
		PropertyUtils.copyProperties(padLog, bean);
		if(null==padLog.getRemark()||"".equals(padLog.getRemark())){
			padLog.setRemark("绑定");
		}else{
			padLog.setRemark("绑定");
		}
		padLog.setCreateTime(new Date());
		padLog.setModifyTime(new Date());
		padLog.setNewPadId(bean.getPadId());
		userPadLogService.userPadLog(request, padLog);
		// 设备绑定日志log_pad
		pad.setModifyTime(bean.getBindTime());
		padHandleLogService.bindingPad(request, pad);
	}
	
	public void savebindNormalOrTastePad(HttpServletRequest request, RfUserPad userPad,String padGrade,RfUser user) throws Exception {
		
		if (!("".equals(user.getLeftOnlineTime()) || null == user.getLeftOnlineTime()||user.getLeftOnlineTime()==0)) {
			//用户账号中有剩余在线时间 就取用
			userPad.setLeftOnlineTime(userService.get(userPad.getUserId()).getLeftOnlineTime());
		}else if(ConstantsDb.rfUserFirstApplyStatusNo().equals(user.getFirstApplyStatus())){//再次申请送的天数
			userPad.setLeftOnlineTime(Integer.parseInt(ConfigConstantsDb.configFacilityReapply()));
		}else{//首次申请送的天数
			userPad.setLeftOnlineTime(Integer.parseInt(ConfigConstantsDb.configFacilityCommon()));
		}
		userPad.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityCommoncontrol()));
		userPad.setPadGrade(padGrade);
		int padCount = userMapper.addBoundPadCount(user.getUserId(), 1);
		String modelName = DictHelper.getLabel("global.pad_model", userPad.getPadGrade());
		if(StringUtils.isNotBlank(modelName)){
			userPad.setUserPadName(modelName+"-"+padCount);
		}
		configService.cutOneConfigLeftBindPadCount();
		if(org.apache.commons.lang.StringUtils.equals(padGrade, ConstantsDb.rfUserPadPadGradeGeneral())){//如果是普通设备
			userService.savefirstApplyStatus(request, userPad);
		}else if(org.apache.commons.lang.StringUtils.equals(padGrade, ConstantsDb.rfUserPadPadGradeTaste())){//如果是体验设备
			userService.savefirstProbationalStatus(request, userPad);
		}
		
		this.save(request, userPad);
	}
		
	public void savebinding(HttpServletRequest request, RfUserPad userPad) throws Exception {
		RfUser user = userService.get(userPad.getUserId());
		
		if (!("".equals(user.getLeftOnlineTime()) || null == user.getLeftOnlineTime()||user.getLeftOnlineTime()==0)) {
			//用户账号中有剩余在线时间 就取用
			userPad.setExpireTime(DateUtils.addDays(new Date(),userService.get(userPad.getUserId()).getLeftOnlineTime()));
			userPad.setLeftOnlineTime(userService.get(userPad.getUserId()).getLeftOnlineTime());
		}else if(ConstantsDb.rfUserFirstApplyStatusNo().equals(user.getFirstApplyStatus())){//再次申请送的天数
			userPad.setExpireTime(DateUtils.addDays(new Date(),Integer.parseInt(ConfigConstantsDb.configFacilityReapply())));
			userPad.setLeftOnlineTime(Integer.parseInt(ConfigConstantsDb.configFacilityReapply()));
		}else{//首次申请送的天数
			userPad.setExpireTime(DateUtils.addDays(new Date(),Integer.parseInt(ConfigConstantsDb.configFacilityCommon())));
			userPad.setLeftOnlineTime(Integer.parseInt(ConfigConstantsDb.configFacilityCommon()));
		}
		userPad.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityCommoncontrol()));
		userPad.setPadGrade(ConstantsDb.rfUserPadPadGradeGeneral());
		int padCount = userMapper.addBoundPadCount(user.getUserId(), 1);
		String modelName = DictHelper.getLabel("global.pad_model", userPad.getPadGrade());
		if(StringUtils.isNotBlank(modelName)){
			userPad.setUserPadName(modelName+"-"+padCount);
		}
		configService.cutOneConfigLeftBindPadCount();
		userService.savefirstApplyStatus(request, userPad);
		this.save(request, userPad);
	}

	public Integer renewal(HttpServletRequest request, RfUserPad bean, RfPad newPad,String isSendMessage,String isSendWechart,
			String sendMessageTemplate,String sendWechartTemplate) throws Exception {
		/** 锁定更换的设备*/
		newPad = padService.getLock(newPad.getPadId());
		Integer wechartTemplateId = null;
		Date time=new Date();
		
		//更新新设备状态
		newPad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		newPad.setModifier(SessionUtils.getCurrentUserId(request));
		newPad.setModifyTime(time);
		padService.modif(request, newPad);
		//更换时解绑日志
		RfUserPadLog rpadLog = new RfUserPadLog();
		PropertyUtils.copyProperties(rpadLog, bean);
		rpadLog.setCreateTime(new Date());
		rpadLog.setModifier(SessionUtils.getCurrentUserId(request));
		rpadLog.setModifyTime(time);
		rpadLog.setOldPadId(bean.getPadId());
		rpadLog.setRemark(rpadLog.getRemark() + ",解绑");
		rpadLog.setOrderId(bean.getOrderId());
		rpadLog.setRenewalTime(time);
		
		userPadLogService.userPadLog(request, rpadLog);
		//更换时绑定日志
		RfUserPadLog bpadLog = new RfUserPadLog();
		BeanUtils.copyProperties(bean, bpadLog, new String[]{"creater","createTime","modifier","modifierTime"});
		bpadLog.setCreater(SessionUtils.getCurrentUserId(request));
		bpadLog.setBindTime(time);
		bpadLog.setCreateTime(time);
		bpadLog.setModifyTime(time);
		bpadLog.setRemark(bpadLog.getRemark() + "绑定");
		bpadLog.setNewPadId(newPad.getPadId());
		bpadLog.setOrderId(bean.getOrderId());
		bpadLog.setRenewalTime(time);
		userPadLogService.userPadLog(request, bpadLog);
		//更换日志
		bpadLog.setOldPadId(bean.getPadId());
		bpadLog.setNewPadId(newPad.getPadId());
		bpadLog.setRemark(bean.getPadId() + "更换为设备" + newPad.getPadId());
		userPadLogService.userPadLog(request, bpadLog);
		
		//需要将设备授权表中数据删掉
		padGrantService.padGrantOperate(request, bean.getUserPadId(),OperateConstants.ReplacePad);
		
		
		//更新旧设备状态
		RfPad oldPad=padService.get(bean.getPadId());
		oldPad.setModifyTime(time);
		oldPad.setModifier(SessionUtils.getCurrentUserId(request));
		oldPad.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
		oldPad.setEnableStatus(ConstantsDb.rfPadEnableStatusRenewal());
		padService.modif(request, oldPad);
		padHandleLogService.renewalPad(request, oldPad);
		padHandleLogService.renewalPadNew(request, newPad);
		
		//是否发送消息
		if(StringUtils.equals(YesOrNoStatus.YES, isSendMessage)){
			if(StringUtils.isNotBlank(sendMessageTemplate)){
				String message = "";
				if(StringUtils.isNotBlank(bean.getUserPadName())){
					message = sendMessageTemplate.replaceAll("XXX", bean.getUserPadName());
				}else{
					message = sendMessageTemplate.replaceAll("XXX", oldPad.getPadName());
				}
				
				String title = "云手机更换通知";
				notifyService.saveMessage(bean.getUserId(), ConstantsDb.padExchange(), 
						message, oldPad.getPadCode(),title);
			}
		}
		
		//是否发送微信消息
		if(StringUtils.equals(YesOrNoStatus.YES, isSendWechart)){
			if(StringUtils.isNotBlank(sendWechartTemplate)){
				int count = wechartUserService.getCountByUserId(bean.getUserId());//用户是否绑定微信
				if(count > 0 && (org.apache.commons.lang3.StringUtils.equals(bean.getPadGrade(), ConstantsDb.rfUserPadPadGradeVip()) || 
						org.apache.commons.lang3.StringUtils.equals(bean.getPadGrade(), ConstantsDb.rfUserPadPadGradesVip()))){//只有vip和超级vip才发送
					String padName = "";
					if(StringUtils.isNotBlank(bean.getUserPadName())){
						padName = bean.getUserPadName();
					}else{
						padName = oldPad.getPadName();
					}
					RfWechartTemplate template = wechartTemplateService.saveWechartTemplate(bean.getUserId(), oldPad.getPadCode(), padName,
							SessionUtils.getCurrentUserId(request),sendWechartTemplate);
					if(null != template && null != template.getTemplateId()){
						wechartTemplateId = template.getTemplateId();
					}else{
						throw new BusinessException("保存微信更换设备消息失败");
					}
				}
			}
		}
		
	    //更新user_pad表 
		RfUserPadMapper mapper=(RfUserPadMapper)this.getMapper();
		bean.setPadId(newPad.getPadId());
		bean.setModifier(SessionUtils.getCurrentUserId(request));
		bean.setModifyTime(time);
		bean.setRenewalTime(time);
		mapper.updateByPrimaryKeySelective(bean);
		
		//如果超级vip设备
		if(ConstantsDb.rfUserPadPadGradesVip().equals(bean.getPadGrade())){
			RfRefundExample example = new RfRefundExample();
			example.createCriteria().andUserIdEqualTo(bean.getUserId()).andUserPadIdEqualTo(bean.getUserPadId());
			List<RfRefund> rfRefunds = refundMapper.selectByExample(example);
			if(null != rfRefunds && rfRefunds.size()>0){
				for (RfRefund rfRefund : rfRefunds) {
					rfRefund.setPadId(newPad.getPadId());
					rfRefund.setPadCode(newPad.getPadCode());
					rfRefund.setModifier(SessionUtils.getCurrentUserId(request));
					rfRefund.setModifyTime(time);
					rfRefund.setRemark(rfRefund.getRemark()+" | 更换设备,旧设备的设备编码："+oldPad.getPadCode()+",新设备的设备编码："+newPad.getPadCode());
					refundMapper.updateByPrimaryKeySelective(rfRefund);
					
					RfRefundLog refundLog = new RfRefundLog();
					refundLog.setRefundId(rfRefund.getRefundId());
					refundLog.setLogType(RefundLogType.CHANGE_PAD);
					refundLog.setHandleAdmin(SessionUtils.getCurrentUserId(request));
					refundLog.setUserId(bean.getUserId());
					refundLog.setUserPadId(bean.getUserPadId());
					refundLog.setPadId(oldPad.getPadId());
					refundLog.setPadCode(oldPad.getPadCode());
					refundLog.setRefundStatus(rfRefund.getRefundStatus());
					refundLog.setRefundSource(rfRefund.getRefundSource());
					refundLog.setRefundIp(rfRefund.getRefundIp());
					refundLog.setRefundTime(rfRefund.getRefundTime());
					refundLog.setImei(rfRefund.getImei());
					refundLog.setMacId(rfRefund.getMacId());
					refundLog.setVersion(rfRefund.getVersion());
					refundLog.setStatus(YesOrNoStatus.YES);
					refundLog.setEnableStatus(YesOrNoStatus.YES);
					refundLog.setCreater(SessionUtils.getCurrentUserId(request));
					refundLog.setCreateTime(new Date());
					refundLog.setRemark("更换设备,旧设备的设备编码："+oldPad.getPadCode()+",新设备的设备编码："+newPad.getPadCode());
					refundLogMapper.insertSelective(refundLog);
				}
			}
		}
		return wechartTemplateId;
	}

	// 管理员绑定设备(VIP/GVIP)
	public void bindAdmin(HttpServletRequest request, RfUserPad bean,String goodsId, String padClassify) throws Exception {
		if(padClassify == null || !(PadClassify.PAD_CLASSIFY_GVIP.equals(padClassify) || PadClassify.PAD_CLASSIFY_MAIN.equals(padClassify))){
			throw new BusinessException("只能绑定主营设备/GVIP设备");
		}
		//判断用户是否符合设备绑定条件
		RfUser user = userService.initQuery().get(bean.getUserId());
		if (user == null) {
			throw new BusinessException("该帐号无效，不能绑定");
		}
		if (user.getBindPadMax() == null || user.getBindPadMax() == 0) {
			throw new BusinessException("该用户绑定设备数量不足");
		}
		//判断用户是否还能绑定	VIP设备
		//List<RfUserPad> userPad = this.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padGrade", ConstantsDb.rfUserPadPadGradeVip()).findStopTrue();
		/** 更改逻辑, 查询用户绑定的所有设备数量 2017/11/03*/
		int userBindPadCount = this.initQuery().andEqualTo("userId", bean.getUserId()).countDelTrue();
		/** 判断用户绑定的设备数量是否达到最大绑定数*/
		if(userBindPadCount >=Integer.parseInt(ConfigConstantsDb.configMemberVipfacility())){
			throw new BusinessException("该用户可绑定设备数已达到上限");
		}
		if (StringUtils.isBlank(user.getUserMobilePhone())) {
			throw new BusinessException("用户帐号没有绑定手机号码");
		}
		/** 根据ID查询设备, 并锁定这设备*/
		RfPad pad = padService.getLock(bean.getPadId());
		
		int padCount = userMapper.addBoundPadCount(user.getUserId(), 1);
		/** 根据设备类型获取不同的设备名称前缀*/
		String modelName = DictHelper.getLabel("global.pad_model", PadClassify.PAD_CLASSIFY_GVIP.equals(padClassify) ? ConstantsDb.rfUserPadPadGradeGvip():ConstantsDb.rfUserPadPadGradeVip());
		if(StringUtils.isNotBlank(modelName)){
			bean.setUserPadName(modelName+"-"+padCount);
		}
		// 设备设备等级
		bean.setPadGrade(PadClassify.PAD_CLASSIFY_GVIP.equals(padClassify) ? ConstantsDb.rfUserPadPadGradeGvip() : ConstantsDb.rfUserPadPadGradeVip());
		this.savebindAdmin(request, bean,goodsId);
		// 修改设备绑定状态记录 user_pad_log
		RfUserPadLog padLog = new RfUserPadLog();
		PropertyUtils.copyProperties(padLog, bean);
		padLog.setCreateTime(new Date());
		padLog.setModifyTime(new Date());
		padLog.setRemark(PadClassify.PAD_CLASSIFY_GVIP.equals(padClassify) ? "绑定GVIP" : "绑定VIP");
		padLog.setNewPadId(bean.getPadId());
		userPadLogService.userPadLog(request, padLog);
		// 设备绑定日志log_pad
		pad.setModifyTime(bean.getBindTime());
		if(PadClassify.PAD_CLASSIFY_GVIP.equals(padClassify)){
			padHandleLogService.bindingPadGVIP(request, pad);
		}else{
			padHandleLogService.bindingPadVIP(request, pad);
		}
	}
	
	
	public void savebindAdmin(HttpServletRequest request, RfUserPad userPad,String goodsId) throws Exception {
		userPad.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityVipcontrol()));
		try {
			userPad.setExpireTime(DateUtils.addDays(new Date(),goodsService.get(Integer.parseInt(goodsId)).getOnlineTime() ));
			userPad.setLeftOnlineTime(goodsService.get(Integer.parseInt(goodsId)).getOnlineTime());
		} catch (Exception e) {
			throw new BusinessException("请选择正确的套餐！");
		}
		
		//userPad.setPadGrade(ConstantsDb.rfUserPadPadGradeVip());
		this.save(request, userPad);
	}
	
	/**
	 * 绑定vip设备
	 */
	public void bindVipPad(HttpServletRequest request, RfUserPad bean,RfPad pad,RfUser user,String padGrade,Date expiretTime) throws Exception {
		//判断用户是否符合设备绑定条件
		if (user == null) {
			throw new BusinessException("该帐号无效，不能绑定");
		}
		if (user.getBindPadMax() == null || user.getBindPadMax() == 0) {
			throw new BusinessException("该用户绑定设备数量不足");
		}
		//判断用户是否还能绑定	VIP设备
		List<RfUserPad> userPad = this.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padGrade", ConstantsDb.rfUserPadPadGradeVip()).findStopTrue();
		if(userPad.size()>=Integer.parseInt(ConfigConstantsDb.configMemberVipfacility())){
			throw new BusinessException("该用户可绑定VIP设备数已达到上限");
		}
		if ("".equals(user.getUserMobilePhone()) || user.getUserMobilePhone() == null) {
			throw new BusinessException("用户帐号没有绑定手机号码");
		}
		
		int padCount = userMapper.addBoundPadCount(user.getUserId(), 1);
		String modelName = DictHelper.getLabel("global.pad_model", ConstantsDb.rfUserPadPadGradeVip());
		if(StringUtils.isNotBlank(modelName)){
			bean.setUserPadName(modelName+"-"+padCount);
		}
		this.savebindVipPad(request, bean,user,expiretTime);
		// 修改设备绑定状态记录 user_pad_log
		RfUserPadLog padLog = new RfUserPadLog();
		PropertyUtils.copyProperties(padLog, bean);
		padLog.setCreateTime(new Date());
		padLog.setModifyTime(new Date());
		padLog.setRemark("绑定VIP");
		padLog.setNewPadId(bean.getPadId());
		userPadLogService.userPadLog(request, padLog);
		// 设备绑定日志log_pad
		pad.setModifyTime(bean.getBindTime());
		padHandleLogService.bindingPadVIP(request, pad);
	}
	
	public void savebindVipPad(HttpServletRequest request, RfUserPad userPad,RfUser user,Date expireTime) throws Exception {
		userPad.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityVipcontrol()));
		try {
			userPad.setExpireTime(expireTime);
			userPad.setLeftOnlineTime(user.getLeftOnlineTime());
		} catch (Exception e) {
			throw new BusinessException("请选择正确的套餐！");
		}
		
		userPad.setPadGrade(ConstantsDb.rfUserPadPadGradeVip());
		this.save(request, userPad);
	}
	
	// 绑定游戏设备
	public void bindGame(HttpServletRequest request, RfUserPad bean,String gamePadTime) throws Exception {
		//判断用户是否符合设备绑定条件
		List<RfUserPad> userPadList = this.initQuery().andEqualTo("userId", bean.getUserId()).singleDelTrue();
		for (RfUserPad userPad : userPadList) {
			if (padService.get(userPad.getPadId()).getPadClassify().equals(PadClassify.PAD_CLASSIFY_GAME)) {
				throw new BusinessException("该用户已经绑定游戏设备");
			}
		}
		RfUser user = userService.initQuery().get(bean.getUserId());
		if (user == null) {
			throw new BusinessException("该帐号无效，不能绑定");
		}
		if ("".equals(user.getUserMobilePhone()) || user.getUserMobilePhone() == null) {
			throw new BusinessException("用户帐号没有绑定手机号码");
		}
		RfPad pad = padService.get(bean.getPadId());
		int padCount = userMapper.addBoundPadCount(user.getUserId(), 1);
		String modelName = DictHelper.getLabel("global.pad_model", ConstantsDb.rfClassClassTypeGame());
		if(StringUtils.isNotBlank(modelName)){
			bean.setUserPadName(modelName+"-"+padCount);
		}
		
		// 修改绑定状态
		bean.setPadGrade("1");
		bean.setExpireTime(DateUtils.strExchangeDate(DateUtils.getAfterDate(new Date(), Integer.parseInt(gamePadTime))));
		bean.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityVipcontrol()));
		
		this.save(request, bean);
		
		// 修改设备绑定状态记录 user_pad_log
		RfUserPadLog padLog = new RfUserPadLog();
		PropertyUtils.copyProperties(padLog, bean);
		padLog.setCreateTime(new Date());
		padLog.setModifyTime(new Date());
		padLog.setRemark("绑定游戏设备");
		padLog.setNewPadId(bean.getPadId());
		userPadLogService.userPadLog(request, padLog);
		// 设备绑定日志log_pad
		pad.setModifyTime(bean.getBindTime());
		padHandleLogService.bindingPadGame(request, pad);
	}
	
	/**
	 * 根据设备编号，获取绑定的用户
	 * @param padId
	 * @return
	 */
	@Transactional(readOnly = true)
	public RfUserPad getUserPadByPadId(Integer padId){
		List<RfUserPad> list = this.initQuery().andEqualTo("padId", padId).singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	public void upVIP(HttpServletRequest request, Integer userPadId,String goodsId) throws Exception {
		Date time = new Date();
		if(null==goodsId||"".equals(goodsId)){
			throw new BusinessException("请选择升级的VIP套餐");
		}
		RfUserPad userPad = this.get(userPadId);
		userPad.setModifier(SessionUtils.getCurrentUserId(request));
		userPad.setModifyTime(time);
			if(ConstantsDb.rfUserPadPadGradeGeneral().equals(userPad.getPadGrade())){
				  userService.saveLeftOnlineTime(request, userPad);
				  userPad.setExpireTime(DateUtils.addDays(time,goodsService.get(Integer.parseInt(goodsId)).getOnlineTime()));
			 	  userPad.setLeftOnlineTime(goodsService.get(Integer.parseInt(goodsId)).getOnlineTime());
				}
			else{
				if(null==userPad.getExpireTime()||"".equals(userPad.getExpireTime())){
			      userPad.setExpireTime(DateUtils.addDays(time,goodsService.get(Integer.parseInt(goodsId)).getOnlineTime()));
				}else{
		          userPad.setExpireTime(DateUtils.addDays(userPad.getExpireTime(),goodsService.get(Integer.parseInt(goodsId)).getOnlineTime()));
				}
				//VIP升级后LeftOnlineTime存放的是到期时间到当前时间之间的天数
	              userPad.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDate(time, userPad.getExpireTime())));
		}
	    userPad.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityVipcontrol()));
		userPad.setPadGrade(ConstantsDb.rfUserPadPadGradeVip());
		RfUserPadMapper mapper = (RfUserPadMapper) this.getMapper();
		mapper.updateByPrimaryKeySelective(userPad);
		padHandleLogService.upVIP(request, userPad);
	}
	
	public void  padUserStatus(HttpServletRequest request,Integer padId){
		List<RfUserPad> userPad = this.initQuery().andEqualTo("padId", padId).findAll();
		if(Collections3.isEmpty(userPad)){
			RfPad pad=padService.load(padId);
			throw new BusinessException("设备数据异常，请确认设备【"+pad.getPadCode()+"】是否有绑定记录!");
		}
		for (RfUserPad rfUserPad : userPad) {
			if(null==rfUserPad.getEnableStatus()||"".equals(rfUserPad.getEnableStatus())){
				RfPad pad=padService.load(padId);
				throw new BusinessException("设备数据异常，编号【"+pad.getPadCode()+"】启用状态为空");
			}
			if(null==rfUserPad.getStatus()||"".equals(rfUserPad.getStatus())){
				RfPad pad=padService.load(padId);
				throw new BusinessException("设备数据异常，编号【"+pad.getPadCode()+"】状态为空");
			}
		}
	}
	
	public void  userPadStatus(HttpServletRequest request,Integer userId){
		List<RfUserPad> userPad = this.initQuery().andEqualTo("userId", userId).findAll();
		if(Collections3.isEmpty(userPad)){
			RfUser user=userService.load(userId);
			throw new BusinessException("设备数据异常，请确认用户【"+user.getUserEmail()+"是否有绑定记录】");
		}
		for (RfUserPad rfUserPad : userPad) {
			if(null==rfUserPad.getEnableStatus()||"".equals(rfUserPad.getEnableStatus())){
				RfPad pad=padService.load(userPad.get(0).getPadId());	
				throw new BusinessException("设备数据异常，编号【"+pad.getPadCode()+"】启用状态为空");
			}
			if(null==rfUserPad.getStatus()||"".equals(rfUserPad.getStatus())){
				RfPad pad=padService.load(userPad.get(0).getPadId());	
				throw new BusinessException("设备数据异常，编号【"+pad.getPadCode()+"】状态为空");
			}
		}
	}
	
	//管理员绑定
	public void adminBinding(HttpServletRequest request, RfUserPad bean, String goodsId,String padClassify,Integer gamePadOnlineTime) throws Exception {
		//判断用户是否符合设备绑定条件
		RfUser user = userService.initQuery().get(bean.getUserId());
		if (user == null) {
			throw new BusinessException("该会员帐号不存在，不能绑定");
		}
		SysAdmin admin=adminService.get(user.getUserEmail());
		if(admin==null){
			throw new BusinessException("管理员帐号不存在，不能绑定，请确认会员帐号邮箱和管理员帐号一致");
		}
		//判断用户是否还能绑定	VIP设备
	/*	List<RfUserPad> userPad = this.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padGrade", ConstantsDb.rfUserPadPadGradeVip()).findStopTrue();
		if(userPad.size()>=Integer.parseInt(ConfigConstantsDb.configMemberVipfacility())){
			throw new BusinessException("该用户可绑定VIP设备数已达到上限");
		}*/
		if ("".equals(user.getUserMobilePhone()) || user.getUserMobilePhone() == null) {
			throw new BusinessException("用户帐号没有绑定手机号码");
		}
		RfPad pad = padService.get(bean.getPadId());
		
		int padCount = userMapper.addBoundPadCount(user.getUserId(), 1);
		String modelName = DictHelper.getLabel("global.pad_model", bean.getPadGrade());
		if(StringUtils.isNotBlank(modelName)){
			bean.setUserPadName(modelName+"-"+padCount);
		}
		this.saveAdminBinding(request, bean,goodsId, padClassify, gamePadOnlineTime);
		// 修改设备绑定状态记录 user_pad_log
		RfUserPadLog padLog = new RfUserPadLog();
		PropertyUtils.copyProperties(padLog, bean);
		padLog.setCreateTime(new Date());
		padLog.setModifyTime(new Date());
		padLog.setRemark("管理员绑定设备");
		padLog.setNewPadId(bean.getPadId());
		userPadLogService.userPadLog(request, padLog);
		// 设备绑定日志log_pad
		pad.setModifyTime(bean.getBindTime());
		padHandleLogService.bindingPadAdmin(request, pad);
	}
	
	//管理员绑定
	public void saveAdminBinding(HttpServletRequest request, RfUserPad userPad,String goodsId,String padClassify,Integer gamePadOnlineTime) throws Exception {
		/*if(PadClassify.PAD_CLASSIFY_GAME.equals(padClassify)){//注释代码原因：管理员绑定改回原来的，不根据设备类别区分情况
			userPad.setLeftOnlineTime(gamePadOnlineTime);
			userPad.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityCommoncontrol()));
			userPad.setExpireTime(DateUtils.addDays(new Date(),Integer.parseInt(ConfigConstantsDb.configFacilityReapply())));
			userPad.setPadGrade(ConstantsDb.rfUserPadPadGradeGame());
		}else{*/
			if(StringUtils.isEmpty(goodsId)){
				userPad.setLeftOnlineTime(Integer.parseInt(ConfigConstantsDb.configFacilityCommon()));
				userPad.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityCommoncontrol()));
				userPad.setExpireTime(DateUtils.addDays(new Date(),Integer.parseInt(ConfigConstantsDb.configFacilityReapply())));
				userPad.setPadGrade(ConstantsDb.rfUserPadPadGradeGeneral());
			}else{
				userPad.setLeftOnlineTime(goodsService.get(Integer.parseInt(goodsId)).getOnlineTime());
				userPad.setLeftControlTime(Integer.parseInt(ConfigConstantsDb.configFacilityVipcontrol()));
				userPad.setExpireTime(DateUtils.addDays(new Date(),goodsService.get(Integer.parseInt(goodsId)).getOnlineTime() ));	
				userPad.setPadGrade(ConstantsDb.rfUserPadPadGradeVip());
			}
		//}
		
		
		this.save(request, userPad);
	}
	//管理员解绑（强制解绑）
	public void adminrelieve(HttpServletRequest request, Integer id) throws Exception {
		// 会员设备信息 一个设备只能给一个用户使用
		// 修改设备绑定状态
		Date relieveTime = new Date();
		RfUserPad userPad = this.get(id);
		RfPad pad = padService.get(userPad.getPadId());
		if (ConstantsDb.rfPadPadStatusControl().equals(pad.getPadStatus())) {
			throw new BusinessException("受控中，无法解绑");
		}
		RfUser user=userService.get(userPad.getUserId());
		SysAdmin admin= adminService.get(user.getUserEmail());
		if(admin==null){
			throw new BusinessException("该操作只能解绑管理员帐号绑定的设备，请确认会员帐号邮箱和管理员帐号一致");
		}
		//解绑时保存剩余普通设备时间到rf_user
		userPad.setModifyTime(relieveTime);
		userService.saveLeftOnlineTime(request,userPad);
		
		//需要将设备授权表中数据删掉
		padGrantService.padGrantOperate(request, userPad.getUserPadId(),OperateConstants.AdminUnbundling);
		
		//解绑时把user_pad中的 信息写入到user_pad_log中保存
		userPad.setRemark("管理员解绑");
		this.relieveLog(request, relieveTime, userPad);
		this.remove(request, id);
		// 设备操作记录
		pad.setModifyTime(relieveTime);
		pad.setModifyTime(relieveTime);
		pad.setModifier(SessionUtils.getCurrentUserId(request));
		pad.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
		pad.setEnableStatus(ConstantsDb.rfPadEnableStatusRelieve());
		padService.modif(request,pad);
		padHandleLogService.relievePadAdmin(request, pad);
	}
	
	//VIP解绑（强制解绑）
	public void relievevip(HttpServletRequest request, Integer id) throws Exception {
		relieveMethod(request, id, RelievePadClassify.RELIEVE_VIP);
	}
	
	//GVIP解绑（强制解绑）
	public void relieveGvip(HttpServletRequest request, Integer id) throws Exception {
		relieveMethod(request, id, RelievePadClassify.RELIEVE_GVIP);
	}
	
	//SVIP解绑（强制解绑）
	public void relieveSvip(HttpServletRequest request, Integer id) throws Exception {
		relieveMethod(request, id, RelievePadClassify.RELIEVE_SVIP);
	}
	
	private void relieveMethod(HttpServletRequest request, Integer id ,String RelieveType)throws Exception{
		// 会员设备信息 一个设备只能给一个用户使用
		// 修改设备绑定状态
		Date relieveTime = new Date();
		RfUserPad userPad = this.get(id);
		if(userPad == null){
			throw new BusinessException("用户设备不存在!");
		}
		RfPad pad = padService.get(userPad.getPadId());
		if(pad == null ){
			throw new BusinessException("设备不存在!");
		}
		if (ConstantsDb.rfPadPadStatusControl().equals(pad.getPadStatus())) {
			throw new BusinessException("受控中，无法解绑");
		}
		
		String operate = OperateConstants.UnKnowUnbundling;
		if(StringUtils.isBlank(RelieveType)){
			throw new BusinessException("解绑失败, 解绑类型未知!");
		}else if(RelievePadClassify.RELIEVE_VIP.equals(RelieveType)){
			// 解绑时把user_pad中的 信息写入到user_pad_log中保存
			userPad.setRemark("VIP解绑");
			// 操作类型 vip解绑
			operate = OperateConstants.VipUnbundling;
			if(!ConstantsDb.rfUserPadPadGradeVip().equals(userPad.getPadGrade())){
				throw new BusinessException("该设备不是VIP设备，无法进行VIP解绑");
			}
		}else if(RelievePadClassify.RELIEVE_GVIP.equals(RelieveType)){
			// 解绑时把user_pad中的 信息写入到user_pad_log中保存
			userPad.setRemark("GVIP解绑");
			// 操作类型 vip解绑
			operate = OperateConstants.GvipUnbundling;
			if(!ConstantsDb.rfUserPadPadGradeGvip().equals(userPad.getPadGrade())){
				throw new BusinessException("该设备不是GVIP设备，无法进行GVIP解绑");
			}
		}else if(RelievePadClassify.RELIEVE_SVIP.equals(RelieveType)){
			// 解绑时把user_pad中的 信息写入到user_pad_log中保存
			userPad.setRemark("SVIP解绑");
			//  操作类型 svip解绑
			operate = OperateConstants.SvipUnbundling;
			if(!ConstantsDb.rfUserPadPadGradesVip().equals(userPad.getPadGrade())){
				throw new BusinessException("该设备不是SVIP设备，无法进行SVIP解绑");
			}
		}else{
			throw new BusinessException("解绑失败，解绑类型未知!");
		}
		
		userPad.setModifyTime(relieveTime);
		//解绑时把user_pad中的 信息写入到user_pad_log中保存
		//userPad.setRemark(RelievePadClassify.RELIEVE_VIP.equals(RelieveType) ? "VIP解绑" : (RelievePadClassify.RELIEVE_GVIP.equals(RelieveType) ? "GVIP解绑" : "未知解绑"));
		this.relieveLog(request, relieveTime, userPad);
		// 解绑类型
		//String operate = RelievePadClassify.RELIEVE_VIP.equals(RelieveType) ? OperateConstants.VipUnbundling : (RelievePadClassify.RELIEVE_GVIP.equals(RelieveType) ? OperateConstants.GvipUnbundling : OperateConstants.UnKnowUnbundling);
		//需要将设备授权表中数据删掉
		padGrantService.padGrantOperate(request, userPad.getUserPadId(),operate);
		
		
		userPadGroupService.deleteByUserPadId(id);//2017-12-10 16:11:48 删除分组
		
		this.remove(request, id);
		// 设备操作记录
		pad.setModifyTime(relieveTime);
		//pad.setModifyTime(relieveTime);
		pad.setModifier(SessionUtils.getCurrentUserId(request));
		pad.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
		pad.setEnableStatus(ConstantsDb.rfPadEnableStatusRelieve());
		padService.modif(request,pad);
		if(RelievePadClassify.RELIEVE_GVIP.equals(RelieveType)){
			padHandleLogService.relieveGvip(request, pad);
		}else if(RelievePadClassify.RELIEVE_SVIP.equals(RelieveType)){
			padHandleLogService.relieveSvip(request, pad);
		}else{
			padHandleLogService.relievevip(request, pad);
			
		}
	}



	public void updateBatchDay(HttpServletRequest request, String padCodes, String timeType, Integer controltime) throws Exception {
	    Date time=new Date();
		if(padCodes==null){
			throw new BusinessException("请输入正确的参数！");
		}
		
		if(StringUtils.isBlank(timeType)){
			throw new BusinessException("选择时间类型！");
		}
		padCodes=padCodes.replaceAll(" ", "");
		padCodes=padCodes.replaceAll("\r", "");
		String [] padCode=padCodes.split("\n");
		if(padCode.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		for (String code : padCode) {
			RfPad	pad=padService.getPadByPadCode(code);
			if(pad==null){
				throw new BusinessException("没有这个设备编号"+code);
			}
			List<RfUserPad> list=this.initQuery().andEqualTo("padId",pad.getPadId()).singleStopTrue();
			if(Collections3.isEmpty(list)){
				throw new BusinessException("没有这个设备编号"+code+"的绑定记录，请重新核对设备编号清单");
			}	
			RfUserPad bean=list.get(0);
			
			if(StringUtils.equals(ConstantsDb.timeTypeDay(), timeType)){//如果是天
				if(null!=bean.getExpireTime()&&!("".equals(bean.getExpireTime()))){
					if(DateUtils.getDistanceOfTwoDate(time,bean.getExpireTime())+controltime<0){
						throw new BusinessException("扣除时间不能大于现有天数！");
					}
					
					bean.setExpireTime(DateUtils.addDays(bean.getExpireTime(),controltime));
					bean.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDate(time, bean.getExpireTime())));
				}else{
			
					bean.setExpireTime(DateUtils.addDays(time,controltime));
					bean.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDate(time, bean.getExpireTime())));
				}
			}else if(StringUtils.equals(ConstantsDb.timeTypeHour(), timeType)){//如果是小时
				if(null!=bean.getExpireTime()&&!("".equals(bean.getExpireTime()))){
					if(DateUtils.getDistanceOfTwoDateH(time,bean.getExpireTime())+controltime<0){
						throw new BusinessException("扣除时间不能大于现有小时数！");
					}
					
					bean.setExpireTime(DateUtils.addHours(bean.getExpireTime(),controltime));
					bean.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDateH(time, bean.getExpireTime())));
				}else{
			
					bean.setExpireTime(DateUtils.addHours(time,controltime));
					bean.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDateH(time, bean.getExpireTime())));
				}
			}
			
			
			this.update(request, bean);
		}
		
	}

	public void updateBatchTime(HttpServletRequest request, String padCodes, Integer onlinetime) throws Exception {
		if(padCodes==null){
			throw new BusinessException("请输入正确的参数！");
		}
		padCodes=padCodes.replaceAll(" ", "");
		padCodes=padCodes.replaceAll("\r", "");
		String [] padCode=padCodes.split("\n");
		if(padCode.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		for (String code : padCode) {
		RfPad	pad=padService.getPadByPadCode(code);
		if(pad==null){
			throw new BusinessException("没有这个设备编号"+code);
		}
		List<RfUserPad> list=this.initQuery().andEqualTo("padId",pad.getPadId()).singleStopTrue();
		if(Collections3.isEmpty(list)){
			throw new BusinessException("没有这个设备编号"+code+"的绑定记录，请重新核对设备编号清单");
		}	 
		RfUserPad bean=list.get(0);
		bean=this.get(bean.getUserPadId());
			if(null!=bean.getLeftControlTime()&&!("".equals(bean.getLeftControlTime()))){
			if(bean.getLeftControlTime()+onlinetime<0){
				throw new BusinessException("扣除时间不能大于现有时间！");
			}	bean.setLeftControlTime(bean.getLeftControlTime()+onlinetime);
			}else{
				bean.setLeftControlTime(onlinetime);
			}
			this.update(request, bean);
		}
	}
	
	/**
	 * 根据用户
	 * @param userId
	 * @param padGrade
	 * @return
	 */
	public int getPadCount(Integer userId,String padGrade){
		RfUserPadExample example = new RfUserPadExample();
		example.createCriteria().andUserIdEqualTo(userId).andPadGradeEqualTo(padGrade);
		return userPadMapper.countByExample(example);
	}
}
