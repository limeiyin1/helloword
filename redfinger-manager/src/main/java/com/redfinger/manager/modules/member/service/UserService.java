package com.redfinger.manager.modules.member.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfBetaUser;
import com.redfinger.manager.common.domain.RfBetaUserExample;
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.common.domain.RfFacilityExample;
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadExample;
import com.redfinger.manager.common.domain.RfRbcLog;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserExample;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.RfUserPadExample;
import com.redfinger.manager.common.domain.RfUserPadLog;
import com.redfinger.manager.common.domain.SysDict;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.MyRfUserPadMapper;
import com.redfinger.manager.common.mapper.RfBetaUserMapper;
import com.redfinger.manager.common.mapper.RfFacilityMapper;
import com.redfinger.manager.common.mapper.RfPadMapper;
import com.redfinger.manager.common.mapper.RfRbcLogMapper;
import com.redfinger.manager.common.mapper.RfUserMapper;
import com.redfinger.manager.common.mapper.RfUserPadLogMapper;
import com.redfinger.manager.common.mapper.RfUserPadMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.DetaUserUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.label.service.LabelService;
import com.redfinger.manager.modules.stat.base.StatDomain;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "userId")
public class UserService extends BaseService<RfUser, RfUserExample, RfUserMapper> {
	@Autowired
	RfUserMapper mapper;
	@Autowired
	RfRbcLogMapper rbcLogMapper;
	@Autowired
	MyRfUserPadMapper myRfUserPadMapper;
	@Autowired
	RfUserPadMapper userPadMapper;
	@Autowired
	RfUserPadLogMapper userPadLogMapper;
	@Autowired
	RfFacilityMapper facilityMapper;
	@Autowired
	RfPadMapper padMapper;
	@Autowired
	UserPadService userPadService;
	@Autowired
	LabelService labelService;
	@Autowired
	RfBetaUserMapper betaUserMapper;
	
	
	public RfUser getUser(HttpServletRequest request,String mid)throws Exception{
		if (mid.indexOf("@")!=-1) {
			List<RfUser>list=this.initQuery().andEqualTo("userEmail", mid).singleStopTrue();
			if(Collections3.isEmpty(list)){
				throw new BusinessException("输入的邮箱/手机号不存在，请确认！");
			}
			return list.get(0);
		} else {
			List<RfUser>list= this.initQuery().andEqualTo("userMobilePhone", mid).singleStopTrue();
			if(Collections3.isEmpty(list)){
				throw new BusinessException("输入的邮箱/手机号不存在，请确认！");
			}
			return list.get(0);
		}

	}
//解绑普通设备或者升级普通设备时保存普通设备剩余控制时间
	public void saveLeftOnlineTime(HttpServletRequest request, RfUserPad userPad) {
		if (ConstantsDb.rfUserPadPadGradeGeneral().equals(userPad.getPadGrade())) {
			RfUserMapper mapper = (RfUserMapper) this.getMapper();
			RfUserExample example = new RfUserExample();
			RfUserExample.Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualToIgnoreNull(userPad.getUserId());
			RfUser user = mapper.selectOneByExampleShowField(
			Lists.newArrayList(RfUser.FD_USER_ID,RfUser.FD_LEFT_ONLINE_TIME), example);
			if ("".equals(userPad.getExpireTime())|| null == userPad.getExpireTime()) {
				throw new BusinessException("绑定数据错误，该条绑定记录没有到期时间！");
			}
			user.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDate(new Date(), userPad.getExpireTime())));
			mapper.updateByPrimaryKeySelectiveSync(user);
		}
	}

	// 修改首次绑定普通设备的值
	public void savefirstApplyStatus(HttpServletRequest request,
			RfUserPad userPad) {
		RfUser user = this.get(userPad.getUserId());
		user.setModifier(SessionUtils.getCurrentUserId(request));
		user.setModifyTime(userPad.getModifyTime());
		user.setFirstApplyStatus(ConstantsDb.rfUserFirstApplyStatusNo());
		user.setLeftOnlineTime(0);
		RfUserMapper mapper = (RfUserMapper) this.getMapper();
		mapper.updateByPrimaryKeySelective(user);
	}
	
	// 修改首次绑定体验设备设备的值
	public void savefirstProbationalStatus(HttpServletRequest request,
			RfUserPad userPad) {
		RfUser user = this.get(userPad.getUserId());
		user.setModifier(SessionUtils.getCurrentUserId(request));
		user.setModifyTime(userPad.getModifyTime());
		user.setFirstProbationalStatus(ConstantsDb.rfUserFirstApplyStatusNo());
		user.setLeftOnlineTime(0);
		RfUserMapper mapper = (RfUserMapper) this.getMapper();
		mapper.updateByPrimaryKeySelective(user);
	}

	// 批量修改红豆的方法
	public void updateRbcs(HttpServletRequest request, int rbcGet,
			String userPhone,String idType) throws Exception {
		if (StringUtils.isBlank(userPhone)) {
			throw new BusinessException("请输入会员清单！");
		}
		userPhone = userPhone.replaceAll(" ", "");
		String[] phones = userPhone.split("\n");
		for (String phone : phones) {
			phone = phone.replaceAll("\r", "");
			
			RfUser user = null;
			if("2".equals(idType)){
				if(org.apache.commons.lang3.math.NumberUtils.isNumber(phone)&&phone.length()<=9){
					user = getUserByExternalUserIdOrUserPhone(null,Integer.valueOf(phone));
				}else{
					throw new BusinessException("账号非法:非纯数字或长度过长");
				}
			}else{
				user = this.getUserByUserPhone(phone);
			}
			
			//RfUser user = this.getUserByUserPhone(phone);
			
			if (user == null) {
				throw new BusinessException("没有这个账号的记录" + phone);
			}
			if (user.getRbcAmount() + rbcGet < 0) {
				throw new BusinessException("扣除红豆不能大于现有红豆！");
			}
			user.setRbcAmount(rbcGet);
			this.rbcUpdate(request, user);
		}
	}
	
	/**
	 * 根据用户手机号获取user,因为userPhone有唯一约束，所以只能是返回一条记录
	 * @param userPhone
	 * @return RfUser
	 */
	@Transactional(readOnly = true)
	public RfUser getUserByUserPhone(String phone){
		List<RfUser> list = this.initQuery().andEqualTo("userMobilePhone", phone).singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据ExternalUserIdOrUserPhone获取user,因为ExternalUserIdOrUserPhone有唯一约束，所以只能是返回一条记录
	 * @param externalUserId
	 * @return RfUser
	 * yirongze修改于 17-8-22
	 */
	@Transactional(readOnly = true)
	public RfUser getUserByExternalUserIdOrUserPhone(String phone,Integer externalUserId){
		List<RfUser> list = this.initQuery()
				.andEqualTo("externalUserId", externalUserId)
				.andEqualTo("userMobilePhone", phone)
				.singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	public void rbcUpdate(HttpServletRequest request, RfUser record) {
		String userId = SessionUtils.getCurrentUserId(request);
		Date modifyTime = new Date();
		record.setModifyTime(modifyTime);
		record.setModifier(userId);
		if (mapper.updateByRbc(record) > 0) {
			RfRbcLog rbcLog = new RfRbcLog();
			//操作类型
			rbcLog.setLogType("4");
			rbcLog.setRbcAmount(record.getRbcAmount());
			rbcLog.setUserId(record.getUserId());
			rbcLog.setCreater(userId);
			rbcLog.setCreateTime(modifyTime);
			rbcLog.setStatus(ConstantsDb.globalStatusNomarl());
			rbcLog.setEnableStatus(ConstantsDb.globalEnableStatusNomarl());
			if(rbcLogMapper.insertSelective(rbcLog)<=0){
				throw new BusinessException("红豆记录日志出错");
			}
		}
	}
	
	public void giveRbc(HttpServletRequest request, int common,int vip,int svip){
		List<StatDomain> all=myRfUserPadMapper.selectAll(common, vip, svip);
		for(StatDomain a : all){
			if(a.getNumber() > 0){
				RfUser user=this.get(a.getId());
				user.setRbcAmount(a.getNumber());
				this.rbcUpdate(request, user);
			}
		}
	}
	
	/**
	 * 根据用户和设备修改过期时间
	 * @param request
	 * @param response
	 * @param bean
	 * @param timeType
	 * @param time
	 * @param padIds
	 */
	public void updateExpireTime(HttpServletRequest request, HttpServletResponse response, RfUser bean, String timeType,Integer time,String padIds){
		String admin = SessionUtils.getCurrentUserId(request);
		String ids[] = padIds.split(",");
		for (String padId : ids) {
			if(StringUtils.isNotBlank(padId)){
				RfUserPadExample example = new RfUserPadExample();
				example.createCriteria().andUserIdEqualTo(bean.getUserId()).andPadIdEqualTo(Integer.parseInt(padId));
				RfUserPad userPad = userPadMapper.selectOneByExample(example);
				if(null != userPad){
					String expireTime = null;
					expireTime = this.getAfterExpireTime(timeType, time, userPad.getExpireTime());
					if(StringUtils.isBlank(expireTime)){
						throw new BusinessException("赠送时间报错");
					}
					
					this.updateExpireTime(userPad, DateUtils.parseDate(expireTime), admin, new Date());
				}
			}
		}
	}
	
	/**
	 * 根据设备修改批量时间
	 * @param request
	 * @param response
	 * @param timeType
	 * @param time
	 * @param padCodes
	 */
	public void batchUpdateExpireTime(HttpServletRequest request, HttpServletResponse response,String timeType,Integer time,String padCodes){
		// TODO Auto-generated method stub
		String admin = SessionUtils.getCurrentUserId(request);
		padCodes = padCodes.replaceAll(" ", "");
		padCodes = padCodes.replaceAll("\r", "");
		String [] codes = padCodes.split("\n");
		if(codes.length < 1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		if(codes.length > 1000){
			throw new BusinessException("设备个数不能超过1000个");
		}
		Map<String,Object> params = new HashMap<String,Object>();
		for(String padCode : codes){
			if(StringUtils.isNoneBlank(padCode)){
				params.clear();
				params.put("padCode", padCode);
				RfUserPad userPad = userPadMapper.selectByPadCode(params);
				if(null != userPad){
					String expireTime = null;
					
					expireTime = this.getAfterExpireTime(timeType, time, userPad.getExpireTime());
					if(StringUtils.isBlank(expireTime)){
						throw new BusinessException("批量修改时间报错");
					}
					this.updateExpireTime(userPad, DateUtils.parseDate(expireTime), admin, new Date());
				}else{
					log.info("设备编码为：" + padCode + "没有绑定设备");
					throw new BusinessException("设备编码为：" + padCode + "错误或者没有绑定设备");
				}
			}
		}
	}
	
	public void maintAddTime(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,
			Integer idcId,String timeType,Integer tasteExpireTime,Integer normalExpireTime,Integer vipExpireTime,Integer svipExpireTime) throws Exception{
		String admin = SessionUtils.getCurrentUserId(request);
		if(null != vipExpireTime && vipExpireTime>0){
			this.addExpireTimeDays(idcId, ConstantsDb.vipnormalPadGrade(), timeType, vipExpireTime, admin);
		}
		
		if(null != normalExpireTime && normalExpireTime > 0){
			this.addExpireTimeDays(idcId, ConstantsDb.normalPadGrade(), timeType, normalExpireTime, admin);
		}
		
		if(null != tasteExpireTime && tasteExpireTime > 0){
			this.addExpireTimeDays(idcId, ConstantsDb.tastePadGrade(), timeType, tasteExpireTime, admin);
		}
		
		if(null != svipExpireTime && svipExpireTime > 0){
			this.addExpireTimeDays(idcId, ConstantsDb.svipPadGrade(), timeType, svipExpireTime, admin);
		}
	}
	
	public void addExpireTimeDays(Integer idcId,String padGrade,String timeType,Integer time,String admin){
		int count = 0;
		Map<String,Object> params = new HashMap<String,Object>();
		if(org.apache.commons.lang3.StringUtils.equals(ConstantsDb.expireTimeTypeDay(), timeType)){
			params.clear();
			params.put("idcId", idcId);
			params.put("afterDays", "interval '" + time + " Days'");
			params.put("modifier", admin);
			params.put("padGrade", padGrade);
			count = userPadMapper.updateExpireTimeByDays(params);
			log.info("update count [idcId:{},padGrade:{},timeType:{},time:{},count:{}]",
					new Object[]{idcId,padGrade,timeType,time,count});
		}else if(org.apache.commons.lang3.StringUtils.equals(ConstantsDb.expireTimeTypeHour(), timeType)){
			params.clear();
			params.put("idcId", idcId);
			params.put("afterHours", "interval '" + time + " Hours'");
			params.put("modifier", admin);
			params.put("padGrade", padGrade);
			count = userPadMapper.updateExpireTimeByHours(params);
			log.info("update count [idcId:{},padGrade:{},timeType:{},time:{},count:{}]",
					new Object[]{idcId,padGrade,timeType,time,count});
		}else if(org.apache.commons.lang3.StringUtils.equals(ConstantsDb.expireTimeTypeMinute(), timeType)){
			params.clear();
			params.put("idcId", idcId);
			params.put("afterMinutes", "interval '" + time + " Minutes'");
			params.put("modifier", admin);
			params.put("padGrade", padGrade);
			count = userPadMapper.updateExpireTimeByMinutes(params);
			log.info("update count [idcId:{},padGrade:{},timeType:{},time:{},count:{}]",
					new Object[]{idcId,padGrade,timeType,time,count});
		}
		
	}
	
	
	public void updateExpireTime(RfUserPad userPad,Date expireTime,String modifier,Date modifyTime){
		RfUserPadLog userPadLog = new RfUserPadLog();
		userPadLog.setOldPadId(userPad.getPadId());
		userPadLog.setBindMobile(userPad.getBindMobile());
		userPadLog.setLeftOnlineTime(userPad.getLeftOnlineTime());
		userPadLog.setLeftControlTime(userPad.getLeftControlTime());
		userPadLog.setTotalOnlineTime(userPad.getTotalOnlineTime());
		userPadLog.setTotalControlTime(userPad.getTotalControlTime());
		userPadLog.setPadControlCount(userPad.getPadControlCount());
		userPadLog.setPadControlTime(userPad.getPadControlTime());
		userPadLog.setBindTime(userPad.getBindTime());
		userPadLog.setStatus(userPad.getStatus());
		userPadLog.setCreater(modifier);
		userPadLog.setCreateTime(new Date());
		userPadLog.setReorder(userPad.getReorder());
		userPadLog.setRemark("修改到期时间,修改至："+DateUtils.formatDateTime(expireTime));
		userPadLog.setEnableStatus(userPad.getEnableStatus());
		userPadLog.setExpireTime(userPad.getExpireTime());
		userPadLog.setPadGrade(userPad.getPadGrade());
		userPadLog.setUserId(userPad.getUserId());
		userPadLog.setLeftRecoveryDay(userPad.getLeftRecoveryDay());
		userPadLog.setPadSource(userPad.getPadSource());
		
		userPadLogMapper.insertSelective(userPadLog);
		
		userPad.setExpireTime(expireTime);
		userPad.setModifier(modifier);
		userPad.setModifyTime(modifyTime);
		userPadMapper.updateByPrimaryKey(userPad);
	}
	
	/**
	 * 根据时间类型和具体时间数量获取过期时间
	 * @param timeType
	 * @param time
	 * @param userPadExpireTime
	 * @return
	 */
	public String getAfterExpireTime(String timeType,Integer time,Date userPadExpireTime){
		String expireTime = null;
		if(StringUtils.equals(ConstantsDb.expireTimeTypeDay(), timeType) && time > 0){
			expireTime = DateUtils.getAfterDate(userPadExpireTime, time * 24);
		}else if(StringUtils.equals(ConstantsDb.expireTimeTypeHour(), timeType)  && time > 0){
			expireTime = DateUtils.getAfterDate(userPadExpireTime, time);
		}else if(StringUtils.equals(ConstantsDb.expireTimeTypeMinute(), timeType)  && time > 0){
			expireTime = DateUtils.getAfterMinuteDate(userPadExpireTime, time);
		}
		return expireTime;
	}
	
	public void batchBindPad(HttpServletRequest request, HttpServletResponse response, 
			String padGrade, String timeType,Integer bindTime,String userMobilePhones,
			String idcId,String controlId,String padCode,String padCode2) throws Exception{
		String admin = SessionUtils.getCurrentUserId(request);
		userMobilePhones = userMobilePhones.replaceAll(" ", "");
		userMobilePhones = userMobilePhones.replaceAll("\r", "");
		String [] phones = userMobilePhones.split("\n");
		if(phones.length < 1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		if(phones.length > 1000){
			throw new BusinessException("号码个数不能超过1000个");
		}
		int userPhoneSize = phones.length;
		List<String> padSources = new ArrayList<String>();
		RfFacilityExample facilityExample = new RfFacilityExample();
		facilityExample.createCriteria().andEnableStatusEqualTo(YesOrNoStatus.YES).andStatusEqualTo(YesOrNoStatus.YES);
		List<RfFacility> facilities = facilityMapper.selectByExample(facilityExample);
		if(null != facilities && facilities.size()>0){
			for (RfFacility rfFacility : facilities) {
				padSources.add(rfFacility.getFacilityCode());
			}
		}
		
		RfPadExample padExample = new RfPadExample();
		padExample.createCriteria().andVmStatusEqualTo(YesOrNoStatus.YES).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusNomarl())
		.andEnableStatusEqualTo(YesOrNoStatus.YES).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusNobind())
		.andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOnline()).andPadSourceIn(padSources);
		
		if(!(StringUtils.isBlank(idcId) && StringUtils.isBlank(controlId) && StringUtils.isBlank(padCode) && StringUtils.isBlank(padCode2))){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("padSourceIn", padSources);
			params.put("vmStatus", YesOrNoStatus.YES);
			params.put("faultStatus", ConstantsDb.rfPadFaultStatusNomarl());
			params.put("enableStatus", YesOrNoStatus.YES);
			params.put("bindStatus",ConstantsDb.rfPadBindStatusNobind());
			params.put("padStatus",ConstantsDb.rfPadPadStatusOnline());
			if(StringUtils.isNotBlank(idcId)){
				params.put("idcId", Integer.parseInt(idcId));
			}
			if(StringUtils.isNotBlank(controlId)){
				params.put("controlId", Integer.parseInt(controlId));
			}
			if(StringUtils.isNotBlank(padCode)){
				params.put("padCodeLike", padCode);
			}
			
			if(StringUtils.isNotBlank(padCode2)){
				params.put("padCodeLte", padCode2);
			}
			
			int padSize = padMapper.countByMap(params);
			if(userPhoneSize > padSize){//如果用户个数大于条件查询的设备个数
				throw new BusinessException("操作失败,设备不足");
			}
		}
		
		List<String> phoneList = new ArrayList<String>();
		for (String phone : phones) {
			phoneList.add(phone);
			
		}
		phoneList = this.cleanRepeat(phoneList);//清理相同的数据
		for (String phone : phoneList) {
			log.info("batchBindPad userMobilePhone:{}", phone);
			
			RfUserExample exmaple = new RfUserExample();
			exmaple.createCriteria().andUserMobilePhoneEqualTo(phone).andStatusEqualTo(YesOrNoStatus.YES);
			RfUser user = mapper.selectOneByExample(exmaple);
			if(null == user){
				throw new BusinessException("手机号码为：" + phone + "没有注册,不能绑定设备");
			}else{
				if(StringUtils.equals(user.getEnableStatus(), YesOrNoStatus.NO)){
					throw new BusinessException("手机号码为：" + phone + "已经被禁用");
				}
				
				if(StringUtils.equals(ConstantsDb.rfUserPadPadGradeGeneral(), padGrade)){//如果是普通设备
					this.bindNormalPad(request, user, admin, padSources,timeType,bindTime,
							userPhoneSize,idcId,controlId,padCode,padCode2);
				}else if(StringUtils.equals(ConstantsDb.rfUserPadPadGradeTaste(), padGrade)){//如果是体验设备
					this.bindTastePad(request, user, admin, padSources,timeType,bindTime,
							userPhoneSize,idcId,controlId,padCode,padCode2);
				}else if(StringUtils.equals(ConstantsDb.rfUserPadPadGradeVip(), padGrade)){//如果是vip设备
					this.bindVipPad(request, user, admin, padSources,timeType,bindTime,
							userPhoneSize,idcId,controlId,padCode,padCode2);
				}
			}
		}
	}
	
	public void bindNormalPad(HttpServletRequest request,RfUser user,String admin,List<String> padSources,
			String timeType,Integer bindTime,int userPhoneSize,String idcId,String controlId,String padCode,String padCode2) throws Exception{
		if(Integer.parseInt(ConfigConstantsDb.configLeftBindPadCount())<=0){
			throw new BusinessException("系统当前已经没有可绑定普通设备");
		}
		
		/*if(StringUtils.equals(user.getFirstApplyStatus(), YesOrNoStatus.YES)){
			throw new BusinessException("手机号码是:"+user.getUserMobilePhone()+"已经绑定过普通设备,不能再绑定普通设备");
		}*/
		
		RfUserPadExample userPadExample = new RfUserPadExample();
		userPadExample.createCriteria().andUserIdEqualTo(user.getUserId()).andPadGradeEqualTo(ConstantsDb.rfUserPadPadGradeGeneral());
		int count = userPadMapper.countByExample(userPadExample);//查询用户当前绑定普通设备的台数
		if(count > 0){
			throw new BusinessException("手机号码是:"+user.getUserMobilePhone()+"已经绑定过普通设备,不能再绑定普通设备");
		}
		
		/*RfPadExample padExample = new RfPadExample();
		padExample.createCriteria().andVmStatusEqualTo(YesOrNoStatus.YES).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusNomarl())
		.andEnableStatusEqualTo(YesOrNoStatus.YES).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusNobind())
		.andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOnline()).andPadSourceIn(padSources);*/
		
		RfPad rfPad = this.selectPad(idcId, controlId, padCode, padCode2, padSources);
		if(null == rfPad){
			throw new BusinessException("系统当前已经没有可绑定的设备");
		}
		RfUserPad userPad = new RfUserPad();
		userPad.setUserId(user.getUserId());
		userPad.setPadId(rfPad.getPadId());
		userPad.setExpireTime(DateUtils.parseDate(getAfterExpireTime(timeType, bindTime, new Date())));
		userPad.setBindTime(new Date());
		
		rfPad.setModifier(SessionUtils.getCurrentUserId(request));
		rfPad.setModifyTime(new Date());
		rfPad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		userPadService.bindNormalOrTastePad(request, userPad, ConstantsDb.rfUserPadPadGradeGeneral(), user);
		padMapper.updateByPrimaryKeySelective(rfPad);
	}
	
	public void bindVipPad(HttpServletRequest request,RfUser user,String admin,List<String> padSources,
			String timeType,Integer bindTime,int userPhoneSize,String idcId,String controlId,String padCode,String padCode2) throws Exception{
		/*RfPadExample padExample = new RfPadExample();
		padExample.createCriteria().andVmStatusEqualTo(YesOrNoStatus.YES).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusNomarl())
		.andEnableStatusEqualTo(YesOrNoStatus.YES).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusNobind())
		.andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOnline()).andPadSourceIn(padSources);*/
		RfPad rfPad = this.selectPad(idcId, controlId, padCode, padCode2, padSources);
		if(null == rfPad){
			throw new BusinessException("系统当前已经没有可绑定的设备");
		}
		
		RfUserPad userPad = new RfUserPad();
		userPad.setUserId(user.getUserId());
		userPad.setPadId(rfPad.getPadId());
		userPad.setExpireTime(DateUtils.parseDate(getAfterExpireTime(timeType, bindTime, new Date())));
		userPad.setBindTime(new Date());
		
		rfPad.setModifier(SessionUtils.getCurrentUserId(request));
		rfPad.setModifyTime(new Date());
		rfPad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		userPadService.bindVipPad(request, userPad, rfPad, user, ConstantsDb.rfUserPadPadGradeVip(), 
				DateUtils.parseDate(getAfterExpireTime(timeType, bindTime, new Date())));
		padMapper.updateByPrimaryKeySelective(rfPad);
	}
	
	public void bindTastePad(HttpServletRequest request,RfUser user,String admin,List<String> padSources,
			String timeType,Integer bindTime,int userPhoneSize,String idcId,String controlId,String padCode,String padCode2) throws Exception{
		/*if(StringUtils.equals(user.getFirstProbationalStatus(), YesOrNoStatus.YES)){
			throw new BusinessException("手机号码是:"+user.getUserMobilePhone()+"已经绑定过体验设备,不能再绑定体验设备");
		}*/
		
		RfUserPadExample userPadExample = new RfUserPadExample();
		userPadExample.createCriteria().andUserIdEqualTo(user.getUserId()).andPadGradeEqualTo(ConstantsDb.rfUserPadPadGradeTaste());
		int count = userPadMapper.countByExample(userPadExample);//查询用户当前绑定体验过设备的台数
		if(count > 0){
			throw new BusinessException("手机号码是:"+user.getUserMobilePhone()+"已经绑定过体验设备,不能再绑定体验设备");
		}
		
		
		/*RfPadExample padExample = new RfPadExample();
		padExample.createCriteria().andVmStatusEqualTo(YesOrNoStatus.YES).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusNomarl())
		.andEnableStatusEqualTo(YesOrNoStatus.YES).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusNobind())
		.andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOnline()).andPadSourceIn(padSources);*/
		RfPad rfPad = this.selectPad(idcId, controlId, padCode, padCode2, padSources);
		if(null == rfPad){
			throw new BusinessException("系统当前已经没有可绑定的设备");
		}
		RfUserPad userPad = new RfUserPad();
		userPad.setUserId(user.getUserId());
		userPad.setPadId(rfPad.getPadId());
		userPad.setExpireTime(DateUtils.parseDate(getAfterExpireTime(timeType, bindTime, new Date())));
		userPad.setBindTime(new Date());
		
		rfPad.setModifier(SessionUtils.getCurrentUserId(request));
		rfPad.setModifyTime(new Date());
		rfPad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		userPadService.bindNormalOrTastePad(request, userPad, ConstantsDb.rfUserPadPadGradeTaste(), user);
		padMapper.updateByPrimaryKeySelective(rfPad);
	}
	
	/**
	 * 查询根据条件查询设备信息
	 * @param idcId 机房
	 * @param controlId 控制节点
	 * @param padCode 设备编码段开始
	 * @param padCode2 设备编码段结尾
	 * @param padSources 设备来源
	 * @return
	 */
	public RfPad selectPad(String idcId,String controlId,String padCode,String padCode2,List<String> padSources){
		if(!(StringUtils.isBlank(idcId) && StringUtils.isBlank(controlId) && StringUtils.isBlank(padCode) && StringUtils.isBlank(padCode2))){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("padSourceIn", padSources);
			params.put("vmStatus", YesOrNoStatus.YES);
			params.put("faultStatus", ConstantsDb.rfPadFaultStatusNomarl());
			params.put("enableStatus", YesOrNoStatus.YES);
			params.put("bindStatus",ConstantsDb.rfPadBindStatusNobind());
			params.put("padStatus",ConstantsDb.rfPadPadStatusOnline());
			if(StringUtils.isNotBlank(idcId)){
				params.put("idcId", Integer.parseInt(idcId));
			}
			if(StringUtils.isNotBlank(controlId)){
				params.put("controlId", Integer.parseInt(controlId));
			}
			if(StringUtils.isNotBlank(padCode)){
				params.put("padCodeGte", padCode);
			}
			
			if(StringUtils.isNotBlank(padCode2)){
				params.put("padCodeLte", padCode2);
			}
			
			RfPad pad = padMapper.selectByMap(params);
			return pad;
		}else{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("padSourceIn", padSources);
			params.put("vmStatus", YesOrNoStatus.YES);
			params.put("faultStatus", ConstantsDb.rfPadFaultStatusNomarl());
			params.put("enableStatus", YesOrNoStatus.YES);
			params.put("bindStatus",ConstantsDb.rfPadBindStatusNobind());
			params.put("padStatus",ConstantsDb.rfPadPadStatusOnline());
			
			RfPad pad = padMapper.selectByMap(params);
			return pad;
		}
	}
	
	public void saveUserLabel(HttpServletRequest request, String labelIds,String userPhone,String idType) throws Exception{
		String admin = SessionUtils.getCurrentUserId(request);
		userPhone = userPhone.replaceAll(" ", "");
		String[] phones = userPhone.split("\n");
		String ids[] = labelIds.split("\\|");
		if(phones.length > 100){
			throw new BusinessException("用户个数不能超过100个");
		}
		if(ids.length > 20){
			throw new BusinessException("用户标签个数不能超过20个");
		}
		
		if(checkLabelId(ids)){
			throw new BusinessException("相同的标签编码只能选择一个标签");
		}
		
		deleteByUserId(phones,idType);
		//TODO
		for(String labelId : ids){
			if(StringUtils.isNotBlank(labelId)){
				saveByUserIdOrPhones(Integer.parseInt(labelId.trim()), phones,admin,idType);
			}
		}
	}
	
	
	public void saveDetaUser(HttpServletRequest request,String userPhone){
		String admin = SessionUtils.getCurrentUserId(request);
		userPhone = userPhone.replaceAll(" ", "");
		userPhone = userPhone.replaceAll("\r","");
		String[] phones = userPhone.split("\n");
		if(phones.length > 100){
			throw new BusinessException("用户个数不能超过100个");
		}
		
		List<String> phoneList = new ArrayList<String>();
		for(String phone : phones){
			phoneList.add(phone);
		}
		
		phoneList = cleanRepeat(phoneList);
		
		for (String mobilePhone : phoneList) {
			
			RfUserExample userExample = new RfUserExample();
			userExample.createCriteria().andUserMobilePhoneEqualTo(mobilePhone)
			.andStatusEqualTo(YesOrNoStatus.YES).andEnableStatusEqualTo(YesOrNoStatus.YES);
			RfUser user = mapper.selectOneByExample(userExample);
			
			if(null != user){
				RfBetaUserExample example = new RfBetaUserExample();
				example.createCriteria().andUserIdEqualTo(user.getUserId()).andClientTypeEqualTo(DetaUserUtils.IOS);
				int count = betaUserMapper.countByExample(example);
				if(count < 1){
					RfBetaUser detaUser = new RfBetaUser(); 
					detaUser.setUserId(user.getUserId());
					detaUser.setUserMobilePhone(mobilePhone);
					detaUser.setClientType(DetaUserUtils.IOS);
					detaUser.setStatus(YesOrNoStatus.YES);
					detaUser.setEnableStatus(YesOrNoStatus.YES);
					detaUser.setCreater(admin);
					detaUser.setCreateTime(new Date());
					betaUserMapper.insertSelective(detaUser);
				}
			}
		}
	}
	
	/**
	 * 限制普通设备申请
	 * @param request
	 * @param userPhone
	 */
	public void saveLimitUse (HttpServletRequest request, String userPhone,String idType) throws Exception{
		userPhone = userPhone.replaceAll(" ", "");
		userPhone = userPhone.replaceAll("\r", "");
		String[] phones = userPhone.split("\n");
		if (phones.length > 1000) {
			throw new BusinessException("用户个数不能超过1000个");
		}
		if("2".equals(idType)){
			List<Integer> externalIds = new ArrayList<Integer>();
			for (String phone : phones) {
				if(NumberUtils.isNumber(phone)&&phone.length()<=9){
					externalIds.add(Integer.valueOf(phone));
				}else{
					throw new BusinessException("账号非法:非纯数字或长度过长");
				}
			}
			List<RfUser> users = selectUserByExternalUserIds(phones);
			if(null==users||users.size()<=0||users.size()<phones.length){
				throw new BusinessException("账号非法:不存在用户");
			}
			mapper.batchUpdateByLimitUseAndExternalId(externalIds, YesOrNoStatus.YES);
		}else{
			List<String> phoneList = new ArrayList<String>();
			for (String phone : phones) {
				phoneList.add(phone);
			}
			List<RfUser> users = selectUserByPhones(phones);
			if(null==users||users.size()<=0||users.size()<phones.length){
				throw new BusinessException("账号非法:不存在用户");
			}
			mapper.batchUpdateByLimitUse(phoneList, YesOrNoStatus.YES);
		}
	}
	
	public void saveByUserIdOrPhones(Integer labelId,String[] phones,String creater,String idType) throws Exception{
		
		List<RfUser> list = null;
		if("2".equals(idType)){
			list = selectUserByExternalUserIds(phones);
			if(null==list||list.size()<=0||list.size()<phones.length){
				throw new BusinessException("账号非法:不存在用户");
			}
		}else{
			list = selectUserByPhones(phones);
			if(null==list||list.size()<=0||list.size()<phones.length){
				throw new BusinessException("账号非法:不存在用户");
			}
		}
		List<Integer> userIds = selectUserIdByList(list);
		labelService.saveUserLabel(labelId, userIds, creater);//然后保存
	}
	
	public void deleteByUserId(String[] phones,String idType) throws Exception{
		//List<RfUser> list = selectUserByPhones(phones);
		
		List<RfUser> list = null;
		if("2".equals(idType)){
			list = selectUserByExternalUserIds(phones);
			if(null==list||list.size()<=0||list.size()<phones.length){
				throw new BusinessException("账号非法:不存在用户");
			}
		}else{
			list = selectUserByPhones(phones);
			if(null==list||list.size()<=0||list.size()<phones.length){
				throw new BusinessException("账号非法:不存在用户");
			}
		}
		
		List<Integer> userIds = selectUserIdByList(list);
		labelService.deleteByUserId(userIds);//先删除
	}
	
	public List<RfUser> selectUserByPhones(String[] phones) throws Exception{
		List<String> list = new ArrayList<String>();
		for (String string : phones) {
			if(StringUtils.isNotBlank(string)){
				list.add(string.trim());
			}
		}
		RfUserExample example = new RfUserExample();
		example.createCriteria().andUserMobilePhoneIn(list);
		return mapper.selectByExample(example);
	}
	
	public List<RfUser> selectUserByExternalUserIds(String[] externalUserIds) throws Exception{
		List<Integer> list = new ArrayList<Integer>();
		for (String externalUserId : externalUserIds) {
			if(StringUtils.isNotBlank(externalUserId)&&NumberUtils.isNumber(externalUserId)&&externalUserId.length()<=9){
				list.add(Integer.valueOf(externalUserId));
			}
		}
		RfUserExample example = new RfUserExample();
		example.createCriteria().andExternalUserIdIn(list);
		return mapper.selectByExample(example);
	}
	
	public List<Integer> selectUserIdByList(List<RfUser> list) throws Exception{
		return Collections3.extractToList(list, "userId");
	}
	
	
	public int selectCount() throws Exception{
		return this.countAll();
	}
	
	/**
	 * 比较多个标签中的标签编码是否相同
	 * @param labelIds
	 * @return
	 */
	private boolean checkLabelId(String[] labelIds){
		for (int i = 0; i < labelIds.length; i++) {
			String labelId = labelIds[i];
			RfLabel label = null;
			if(StringUtils.isNotBlank(labelId)){
				label = labelService.get(Integer.parseInt(labelId.trim()));
			}
			
			for (int j = i + 1; j < labelIds.length; j++) {
				String labelIdTemp = labelIds[j];
				RfLabel labelTemp = null;
				if(StringUtils.isNotBlank(labelIdTemp)){
					labelTemp = labelService.get(Integer.parseInt(labelIdTemp.trim()));
				}
				if(null != label && null != labelTemp){
					if(StringUtils.equals(label.getLabelCode(), labelTemp.getLabelCode())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public List<String> selectUserSource(){
		return mapper.selectUserSource();
	}
	
	public List<String> selectClientSource(){
		return mapper.selectClientSource();
	}
	
	/**
	 * 
	 * 根据用户id更新会员类型
	 * @param request
	 * @param userId 用户id
	 * @param userClassify 用户类型
	 */
	public void updateUserClassify(HttpServletRequest request, Integer userId, String userClassify){
		// 查询用户
		RfUser user = this.get(userId);
		// 判断用户是否存在
		if(user == null){
			throw new BusinessException("更改会员类型失败, 用户不存在!");
		}
		// 判断会员类型是否为空
		if(StringUtils.isBlank(userClassify)){
			throw new BusinessException("更改会员类型失败, 用户类型参数不能为空!");
		}
		// 查询所有会员类型 
		List<SysDict> classfiyList = DictHelper.getDictListByCategory("rf_user.user_classify");
		boolean classfiyIsExists = false;
		// 判断会员类型是否存在
		if(classfiyList != null && classfiyList.size() > 0 ){
			for (SysDict sysDict : classfiyList) {
				if(userClassify.equals(sysDict.getDictValue())){
					classfiyIsExists = true;
					break;
				}
			}
		}
		// 会员类型不存在, 提示用户
		if(!classfiyIsExists){
			throw new BusinessException("更改会员类型失败, 用户类型不存在!");
		}
		// 设置会员类型
		user.setUserClassify(userClassify);
		// 设置修改人
		user.setModifier(SessionUtils.getCurrentUserId(request));
		// 设置修改时间
		user.setModifyTime(new Date());
		
		RfUserMapper mapper = (RfUserMapper) this.getMapper();
		// 更新用户信息
		mapper.updateByPrimaryKeySelective(user);
		
	}
}
