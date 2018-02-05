package com.redfinger.manager.modules.facility.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseDomain;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.PadClassify;
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.common.domain.RfFacilityExample;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadExample;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserExample;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.RfFacilityMapper;
import com.redfinger.manager.common.mapper.RfPadMapper;
import com.redfinger.manager.common.mapper.RfUserPadMapper;
import com.redfinger.manager.common.utils.BusinessLockUtil;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.Reflections;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.facility.dto.UserPadDto;
import com.redfinger.manager.modules.goods.dto.PadGoodsDto;
import com.redfinger.manager.modules.label.service.LabelService;
import com.redfinger.manager.modules.log.service.PadHandleLogService;


@Transactional
@Service
@PrimaryKeyAnnotation(field = "padId")
public class PadService extends BaseService<RfPad, RfPadExample, RfPadMapper> {
	@Autowired
	PadHandleLogService padHandleLogService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	RfPadMapper mapper;
	@Autowired
	RfFacilityMapper facilityMapper;
	@Autowired
	RfUserPadMapper userPadMapper;
	@Autowired
	LabelService labelService;


	public void saveNew(HttpServletRequest request, RfPad bean) throws Exception {
		String ipStrm[] = bean.getPadIp().split("\\.");
		int padNumber = Integer.parseInt(ipStrm[ipStrm.length - 1]);// 最后一分号内容
		if (bean.getIds() != null) {
			for (Integer i = padNumber; i <= Integer.parseInt(bean.getIds()); i++) {
				RfPad pad = new RfPad();
				PropertyUtils.copyProperties(pad, bean);
				String postfix = String.format("%03d", i);
				// 新增设备默认状态（无故障，离线，未绑定）
				pad.setActiveStatus(ConstantsDb.rfPadActiveStatusActive());
				pad.setFaultStatus(ConstantsDb.rfPadFaultStatusNomarl());
				pad.setPadStatus(ConstantsDb.rfPadPadStatusOffline());
				pad.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
				pad.setPadName(bean.getPadName() + postfix);// 批量新增默认把用户输入的作为前缀加ip最后一位做默认名
				pad.setPadCode(bean.getPadCode() + postfix);// 批量新增默认把用户输入的作为前缀加ip最后一位做默认
				pad.setPadIp(ipStrm[0] + '.' + ipStrm[1] + '.' + ipStrm[2] + '.' + Integer.toString(i));
				pad.setMaintStatus(ConstantsDb.rfPadMaintStatusOff());//正常
				this.savePad(request, pad);
				padHandleLogService.addPad(request, pad);
			}
		} else {
			// 新增设备默认状态（无故障，离线，未绑定）
			bean.setActiveStatus(ConstantsDb.rfPadActiveStatusActive());
			bean.setFaultStatus(ConstantsDb.rfPadFaultStatusNomarl());
			bean.setPadStatus(ConstantsDb.rfPadPadStatusOffline());
			bean.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
			bean.setMaintStatus(ConstantsDb.rfPadMaintStatusOff());//正常
			this.savePad(request, bean);
			padHandleLogService.addPad(request, bean);
		}
	}

	public void savePad(HttpServletRequest request, RfPad bean) {
		List<RfPad> pad = this.initQuery().andEqualTo("padCode", bean.getPadCode()).findAll();
		if (pad.size() > 0) {
			throw new BusinessException("编码重复，请重新输入！");
		}
		pad = this.initQuery().andEqualTo("padName", bean.getPadName()).findAll();
		if (pad.size() > 0) {
			throw new BusinessException("名称重复，请重新输入！");
		}
		pad = this.initQuery().andEqualTo("padIp", bean.getPadIp()).findAll();
		if (pad.size() > 0) {
			throw new BusinessException("Ip" + bean.getPadIp() + "重复，请重新输入！");
		}
		RfPadMapper mapper = (RfPadMapper) this.getMapper();
		mapper.updateByPrimaryKeySelective(bean);
		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		bean.setStatus(ConstantsDb.globalStatusNomarl());
		bean.setCreateTime(currentDate);
		bean.setCreater(userId);
		mapper.insert(bean);
	}
/*//启动设备的时候判断虚拟设备是否有绑定物理设备
	@ServiceAnnotation(name = ServiceMethod.pre_start)
	public String preStart(HttpServletRequest request, RfPad bean) {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String string : idArray) {
			RfPad pad = this.get(Integer.parseInt(string));
			if(null==pad.getDeviceId()){
				return "虚拟设备"+pad.getPadCode()+"没有绑定物理设备，不可启用";
			}
		}
		return null;
	}*/
	

	// 启用状态控制（启用控制设备是否为客户可以申请）
//	@ServiceAnnotation(name = ServiceMethod.pre_start)
//	public String preStart(HttpServletRequest request, RfPad bean) {
//		String[] idArray = StringUtils.split(bean.getIds(), ",");
//		for (String string : idArray) {
//			RfPad pad = this.get(Integer.parseInt(string));
			/*
			 * if
			 * (ConstantsDb.globalEnableStatusNomarl().equals(pad.getEnableStatus
			 * ())) { return pad.getPadCode() + "设备已启用，操作无效"; } if
			 * ((ConstantsDb.rfPadPadStatusOffline()).equals(pad.getPadStatus())
			 * ||
			 * (ConstantsDb.rfPadPadStatusControl()).equals(pad.getPadStatus()))
			 * { return "设备状态不是在线，不可启用";// 离线和受控设备不能启用 } if
			 * (ConstantsDb.rfPadFaultStatusFault
			 * ().equals(pad.getFaultStatus())) { return "设备故障中，不能启用"; } if
			 * (ConstantsDb.rfPadBindStatusBind().equals(pad.getBindStatus())) {
			 * return "设备已绑定，不可启用"; }
			 */
//		}
//		return null;
//	}

	@ServiceAnnotation(name = ServiceMethod.after_start)
	public String afterStart(HttpServletRequest request, RfPad bean) {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String string : idArray) {
			try {
				RfPad pad=this.get(Integer.parseInt(string));
				if(null==pad){
				throw new BusinessException("设备号错误，操作失败！");	
				}else{
					padHandleLogService.startPad(request,pad );
				}
			} catch (Exception e) {
			/*	return "启用日志写入失败";*/
				e.printStackTrace();
			}
		}
		return null;
	}

//	@ServiceAnnotation(name = ServiceMethod.pre_stop)
//	public String preStop(HttpServletRequest request, RfPad bean) {
//		String[] idArray = StringUtils.split(bean.getIds(), ",");
//		for (String string : idArray) {
//			RfPad pad = this.get(Integer.parseInt(string));
			/*
			 * if
			 * (ConstantsDb.globalEnableStatusStop().equals(pad.getEnableStatus
			 * ())) { return pad.getPadCode() + "设备已禁用，操作无效"; } if
			 * (ConstantsDb.rfPadPadStatusControl().equals(pad.getPadStatus()))
			 * { return "设备受控中，无法禁用"; } if
			 * (ConstantsDb.rfPadBindStatusBind().equals(pad.getBindStatus())) {
			 * return "设备绑定中，无法禁用"; }
			 */
//		}
//		return null;
//	}

	@ServiceAnnotation(name = ServiceMethod.after_stop)
	public String afterStop(HttpServletRequest request, RfPad bean) {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String string : idArray) {
			try {
				RfPad pad=this.get(Integer.parseInt(string));
				if(null==pad){
				throw new BusinessException("设备号错误，操作失败！");	
				}else{
				padHandleLogService.stopPad(request,pad );
				}
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			//	return "停用日志写入失败";
			}
		}
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.pre_remove)
	public String afterDelete(HttpServletRequest request, RfPad bean) {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String string : idArray) {
			RfPad pad = this.get(Integer.parseInt(string));
			if(null==pad)
			{
				return"设备号错误，操作失败！";
			}
			if(StringUtils.isEmpty(pad.getBindStatus())){
				return"设备绑定状态为空";
			}
			if (ConstantsDb.rfPadBindStatusBind().equals(pad.getBindStatus())) {
				return "设备绑定中，无法移除";
			}
			if(StringUtils.isEmpty(pad.getPadStatus())){
				return"设备受控状态为空";
			}
			if (ConstantsDb.rfPadPadStatusControl().equals(pad.getPadStatus())) {
				return "设备受控中，无法移除";
			}

			try {
				padHandleLogService.deletePad(request, this.get(Integer.parseInt(string)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return "删除日志写入失败";
			}
		}
		return null;
	}

	public synchronized void bind(HttpServletRequest request, RfUserPad userPad) throws Exception {
		Map<String, Object> params = new HashMap<String,Object>();
		/*params.put("lockId", BusinessLockUtil.PAD_BIND_LOCK_ID);
		mapper.selectPadLock(params);*/
		
		
		RfPad rfPad = this.getLock(userPad.getPadId());
		//系统当前剩余可绑定普通设备数量
	    if(Integer.parseInt(ConfigConstantsDb.configLeftBindPadCount())<=0){
		   throw new BusinessException("系统当前已经没有可绑定普通设备");
	    }
		//判断当前选中设备是否可以绑定
		if (rfPad == null) {
			throw new BusinessException("选择绑定的设备号错误，请重新选择设备");
		}
		/**2017/11/07 增加逻辑判断, 非主营设备不允许绑定 */
		if(!PadClassify.PAD_CLASSIFY_MAIN.equals(rfPad.getPadClassify())){
			throw new BusinessException("非主营设备不允许绑定，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getFaultStatus())){
			throw new BusinessException("设备故障状态为空");
		}
		if (rfPad.getFaultStatus().equals(ConstantsDb.rfPadFaultStatusFault())) {
			throw new BusinessException("设备故障中，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getBindStatus())){
			throw new BusinessException("设备绑定状态为空");
		}
		if (rfPad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
			throw new BusinessException("设备绑定中，请重新选择设备");
		}
		/*if(StringUtils.isEmpty(rfPad.getEnableStatus())){
			throw new BusinessException("设备禁用状态为空");
		}
		if (!(rfPad.getEnableStatus().equals(ConstantsDb.rfPadEnableStatusStart()))) {
			throw new BusinessException("设备禁用中，无法给用户绑定");
		}*/
		if(StringUtils.isEmpty(rfPad.getPadStatus())){
			
		}
		if (!(ConstantsDb.rfPadPadStatusOnline().equals(rfPad.getPadStatus()))) {
			throw new BusinessException("设备不在线，绑定失败");
		}
	
		Date bindTime = new Date();
		userPad.setBindTime(bindTime);
		RfPad pad = this.getLock(userPad.getPadId());
		pad.setModifier(SessionUtils.getCurrentUserId(request));
		pad.setModifyTime(bindTime);
		pad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		RfPadMapper mapper = (RfPadMapper) this.getMapper();
		userPadService.binding(request, userPad);
		mapper.updateByPrimaryKeySelective(pad);
	}

	public void relieve(HttpServletRequest request, RfPad bean) throws Exception {
		bean.setModifier(SessionUtils.getCurrentUserId(request));
		bean.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
		bean.setEnableStatus(ConstantsDb.rfPadEnableStatusRelieve());
		RfPadMapper mapper = (RfPadMapper) this.getMapper();
		mapper.updateByPrimaryKeySelective(bean);
	}

	// 独立更新pad信息
	/**
	 * 自定义更新
	 * 
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public void modif(HttpServletRequest request, RfPad bean) throws Exception {
		RfPadMapper mapper = (RfPadMapper) this.getMapper();
		mapper.updateByPrimaryKeySelective(bean);
	}

	public synchronized void bindAdmin(HttpServletRequest request, RfUserPad bean,String goodsId) throws Exception {
		/*Map<String, Object> params = new HashMap<String,Object>();
		params.put("lockId", BusinessLockUtil.PAD_BIND_LOCK_ID);
		mapper.selectPadLock(params);*/
		
		RfPad rfPad = this.getLock(bean.getPadId());
		//判断当前选中设备是否可以绑定
		if (rfPad == null) {
			throw new BusinessException("选择绑定的设备号错误，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getFaultStatus())){
			throw new BusinessException("设备故障状态为空");
		}
		if (rfPad.getFaultStatus().equals(ConstantsDb.rfPadFaultStatusFault())) {
			throw new BusinessException("设备故障中，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getBindStatus())){
			throw new BusinessException("设备绑定状态为空");
		}
		if (rfPad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
			throw new BusinessException("设备绑定中，请重新选择设备");
		}
		/*if(StringUtils.isEmpty(rfPad.getEnableStatus())){
			throw new BusinessException("设备禁用状态为空");
		}
		if (!(rfPad.getEnableStatus().equals(ConstantsDb.rfPadEnableStatusStart()))) {
			throw new BusinessException("设备禁用中，无法给用户绑定");
		}*/
		if(StringUtils.isEmpty(rfPad.getPadStatus())){
			
		}
		if (!(ConstantsDb.rfPadPadStatusOnline().equals(rfPad.getPadStatus()))) {
			throw new BusinessException("设备不在线，绑定失败");
		}
	
		Date bindTime = new Date();
		bean.setBindTime(bindTime);
		RfPad pad = this.getLock(bean.getPadId());
		pad.setModifier(SessionUtils.getCurrentUserId(request));
		pad.setModifyTime(bindTime);
		pad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		RfPadMapper mapper = (RfPadMapper) this.getMapper();
		userPadService.bindAdmin(request, bean, goodsId, pad.getPadClassify());
		mapper.updateByPrimaryKeySelective(pad);
	}

	public synchronized void batchBindVip(HttpServletRequest request, RfUserPad bean,Integer idcId,List<PadGoodsDto> padGoods) throws Exception {
		/*Map<String, Object> params = new HashMap<String,Object>();
		params.put("lockId", BusinessLockUtil.PAD_BIND_LOCK_ID);
		mapper.selectPadLock(params);*/
		
		String adminCode = SessionUtils.getCurrentUserId(request);
		
		int padCount = 0;//需要添加的设备数量
		if(null != padGoods && padGoods.size()>0){//产品不为空
			for (PadGoodsDto dto : padGoods) {
				if(null != dto){
					if(null != dto.getPadCount()){
						if(dto.getPadCount() > 0){
							padCount = padCount + dto.getPadCount();
						}
					}
				}
			}
		}
		
		if(0 == padCount){
			throw new BusinessException("请填写需要添加的设备数量");
		}
		
		
		List<RfUserPad> userPads = userPadService.initQuery().andEqualTo("userId", bean.getUserId()).andEqualTo("padGrade", ConstantsDb.rfUserPadPadGradeVip()).findStopTrue();
		if(null != userPads && userPads.size()>0){
			int userPadCount = userPads.size();
			int count = userPadCount + padCount;
			if(count > Integer.parseInt(ConfigConstantsDb.configMemberVipfacility()) ){
				throw new BusinessException("该用户可绑定VIP设备数最多"+ConfigConstantsDb.configMemberVipfacility()+"台,已经绑定"+userPadCount+"台");
			}
		}else{
			if(padCount > Integer.parseInt(ConfigConstantsDb.configMemberVipfacility())){
				throw new BusinessException("用户最多绑定VIP设备"+ConfigConstantsDb.configMemberVipfacility()+"台");
			}
		}
		
		List<String> padSources = new ArrayList<String>();
		RfFacilityExample facilityExample = new RfFacilityExample();
		facilityExample.createCriteria().andEnableStatusEqualTo(YesOrNoStatus.YES).andStatusEqualTo(YesOrNoStatus.YES);
		List<RfFacility> facilities = facilityMapper.selectByExample(facilityExample);
		if(null != facilities && facilities.size()>0){
			for (RfFacility rfFacility : facilities) {
				padSources.add(rfFacility.getFacilityCode());
			}
		}
		
		RfPadExample example = new RfPadExample();
		example.createCriteria().andVmStatusEqualTo(YesOrNoStatus.YES).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusNomarl())
		.andEnableStatusEqualTo(YesOrNoStatus.YES).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusNobind()).andPadClassifyEqualTo(PadClassify.PAD_CLASSIFY_MAIN)
		.andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOnline()).andIdcIdEqualTo(idcId).andPadSourceIn(padSources);
		
		int size = mapper.countByExample(example);
		
		if(size > 0){
			if(padCount > size){
				throw new BusinessException("您选择机房的设备数量没有您需要绑定多，请重新填写数量，机房设备数量：" + size + ",设备数量：" + padCount);
			}
			
			for (PadGoodsDto dto : padGoods) {
				if(null != dto && null != dto.getPadCount() && dto.getPadCount() > 0){
					
					RfPadExample padExample = new RfPadExample();
					padExample.createCriteria().andVmStatusEqualTo(YesOrNoStatus.YES).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusNomarl())
					.andEnableStatusEqualTo(YesOrNoStatus.YES).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusNobind()).andPadClassifyEqualTo(PadClassify.PAD_CLASSIFY_MAIN)
					.andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOnline()).andIdcIdEqualTo(idcId).andPadSourceIn(padSources);
					List<RfPad> padLists = mapper.selectByExample(padExample);
					
					for(int i = 0;i < dto.getPadCount();i++){
						bean.setPadId(padLists.get(i).getPadId());
						this.bindPad(request, padLists.get(i), adminCode, dto.getGoodsId(), bean);
					}
				}
			}
		}else{
			throw new BusinessException("您选择的机房没有设备可绑定");
		}
	}
	
	/** 绑定游戏设备 */
	public synchronized void bindGame(HttpServletRequest request, RfUserPad bean,String gamePadTime) throws Exception {
		/*Map<String, Object> params = new HashMap<String,Object>();
		params.put("lockId", BusinessLockUtil.PAD_BIND_LOCK_ID);
		mapper.selectPadLock(params);*/
		
		RfPad rfPad = this.getLock(bean.getPadId());
		//判断当前选中设备是否可以绑定
		if (rfPad == null) {
			throw new BusinessException("选择绑定的设备号错误，请重新选择设备");
		}
		/**判断设备类型是否为游戏设备 */
		if(!PadClassify.PAD_CLASSIFY_GAME.equals(rfPad.getPadClassify())){
			throw new BusinessException("游戏设备绑定只能绑定游戏设备，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getFaultStatus())){
			throw new BusinessException("设备故障状态为空");
		}
		if (rfPad.getFaultStatus().equals(ConstantsDb.rfPadFaultStatusFault())) {
			throw new BusinessException("设备故障中，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getBindStatus())){
			throw new BusinessException("设备绑定状态为空");
		}
		if (rfPad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
			throw new BusinessException("设备绑定中，请重新选择设备");
		}
		String padStatus = rfPad.getPadStatus();
		if(StringUtils.isEmpty(padStatus)){
			
		}
		if (!(ConstantsDb.rfPadPadStatusOnline().equals(padStatus))) {
			throw new BusinessException("设备不在线，绑定失败");
		}
	
		Date bindTime = new Date();
		bean.setBindTime(bindTime);
		RfPad pad = this.getLock(bean.getPadId());
		pad.setModifier(SessionUtils.getCurrentUserId(request));
		pad.setModifyTime(bindTime);
		pad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		RfPadMapper mapper = (RfPadMapper) this.getMapper();
		userPadService.bindGame(request, bean, gamePadTime);
		mapper.updateByPrimaryKeySelective(pad);
	}
	
	private void bindPad(HttpServletRequest request,RfPad rfPad,String adminCode,Integer goodsId,RfUserPad bean) throws Exception{
		// 锁定该设备
		rfPad = this.getLock(rfPad.getPadId());
		//判断当前选中设备是否可以绑定
		if (rfPad == null) {
			throw new BusinessException("的设备号错误，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getFaultStatus())){
			throw new BusinessException("设备故障状态为空");
		}
		if (rfPad.getFaultStatus().equals(ConstantsDb.rfPadFaultStatusFault())) {
			throw new BusinessException("设备故障中，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getBindStatus())){
			throw new BusinessException("设备绑定状态为空");
		}
		if (rfPad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
			throw new BusinessException("设备绑定中，请重新选择设备");
		}
		/*if(StringUtils.isEmpty(rfPad.getEnableStatus())){
			throw new BusinessException("设备禁用状态为空");
		}
		if (!(rfPad.getEnableStatus().equals(ConstantsDb.rfPadEnableStatusStart()))) {
			throw new BusinessException("设备禁用中，无法给用户绑定");
		}*/
		if (!(ConstantsDb.rfPadPadStatusOnline().equals(rfPad.getPadStatus()))) {
			throw new BusinessException("设备不在线，绑定失败");
		}
	
		Date bindTime = new Date();
		bean.setBindTime(bindTime);
		bean.setPadId(rfPad.getPadId());
		rfPad.setModifier(adminCode);
		rfPad.setModifyTime(bindTime);
		rfPad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		
		userPadService.bindAdmin(request, bean, goodsId.toString(),rfPad.getPadClassify());
		
		mapper.updateByPrimaryKeySelective(rfPad);
	}
	
	// 激活设备
	public void active(HttpServletRequest request, RfPad bean) {

		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String idStr : idArray) {
			RfPad pad = this.getLock(Integer.parseInt(idStr));
			if (!(pad.getActiveStatus().equals(ConstantsDb.rfPadActiveStatusActive()))) {
				continue;
			}
			pad.setActiveStatus(ConstantsDb.rfPadActiveStatusActive());
			pad.setModifyTime(currentDate);
			pad.setModifier(userId);
			try {
				this.modif(request, pad);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException("激活设备失败！");
			}
		}

	}

	// 冻结设备
	public void freeze(HttpServletRequest request, RfPad bean) {

		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String idStr : idArray) {
			RfPad pad = this.get(Integer.parseInt(idStr));
			if (!(pad.getActiveStatus().equals(ConstantsDb.rfPadActiveStatusFreeze()))) {
				continue;
			}
			pad.setActiveStatus(ConstantsDb.rfPadActiveStatusFreeze());
			pad.setModifyTime(currentDate);
			pad.setModifier(userId);
			try {
				this.modif(request, pad);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException("冻结设备失败！");
			}
		}

	}
	
	/**
	 * 根据padCode获取pad,因为padCode有唯一约束，所以只能是返回一条记录
	 * @param padCode
	 * @return RfPad
	 */
	@Transactional(readOnly = true)
	public RfPad getPadByPadCode(String padCode){
		List<RfPad> list = this.initQuery().andEqualTo("padCode", padCode).singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public RfPad getPadByPadCodeContainDel(String padCode){
		List<RfPad> list = this.initQuery().andEqualTo("padCode", padCode).singleAll();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	

	public void changeNew(HttpServletRequest request, RfPad bean) throws Exception {
		RfPadMapper mapper=(RfPadMapper)this.getMapper();
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String idStr : idArray) {
			// 获取一个设备, 并锁定
			RfPad pad = this.getLock(Integer.parseInt(idStr));
			if(ConstantsDb.rfPadPadStatusControl().equals(pad.getPadStatus())){
				throw new BusinessException(pad.getPadCode()+"设备受控中");
			}
			if(ConstantsDb.rfPadBindStatusBind().equals(pad.getBindStatus())){
				throw new BusinessException(pad.getPadCode()+"设备绑定中");
			}
			String userId = SessionUtils.getCurrentUserId(request);
			Date currentDate = new Date();
			if("".equals(pad.getBatchNumber())||null==pad.getBatchNumber()){
			pad.setBatchNumber(2);
			}else{
				pad.setBatchNumber(pad.getBatchNumber()+1);
			}
			pad.setEnableStatus(ConstantsDb.rfPadEnableStatusForbidden());
			pad.setPadStatus(ConstantsDb.rfPadPadStatusOffline());
			pad.setBindStatus(ConstantsDb.rfPadBindStatusNobind());
			pad.setFaultStatus(ConstantsDb.rfPadFaultStatusNomarl());
	      	pad.setStatus(ConstantsDb.globalStatusNomarl());
	      	pad.setModifier(userId);
	      	pad.setModifyTime(currentDate);
		  	pad.setCreateTime(currentDate);
		    pad.setCreater(userId);
			mapper.updateByPrimaryKeySelective(pad);
			padHandleLogService.changePad(request, pad);
		}
		
	}

	public void adminBinding(HttpServletRequest request, RfUserPad bean, String goodsId,String padClassify,Integer gamePadOnlineTime) throws Exception {
		/*Map<String, Object> params = new HashMap<String,Object>();
		params.put("lockId", BusinessLockUtil.PAD_BIND_LOCK_ID);
		mapper.selectPadLock(params);*/
		// 获取一个设备, 并锁定
		RfPad rfPad = this.getLock(bean.getPadId());
		//判断当前选中设备是否可以绑定
		if (rfPad == null) {
			throw new BusinessException("选择绑定的设备号错误，请重新选择设备");
		}
		if(StringUtils.isEmpty(rfPad.getFaultStatus())){
			throw new BusinessException("设备故障状态为空");
		}
		/*if (rfPad.getFaultStatus().equals(ConstantsDb.rfPadFaultStatusFault())) {
			throw new BusinessException("设备故障中，请重新选择设备");
		}*/
		if(StringUtils.isEmpty(rfPad.getBindStatus())){
			throw new BusinessException("设备绑定状态为空");
		}
		if (rfPad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
			throw new BusinessException("设备绑定中，请重新选择设备");
		}
		/*if(StringUtils.isEmpty(rfPad.getEnableStatus())){
			throw new BusinessException("设备禁用状态为空");
		}*/
	/*	if (!(rfPad.getEnableStatus().equals(ConstantsDb.rfPadEnableStatusStart()))) {
			throw new BusinessException("设备禁用中，无法给用户绑定");
		}*/
		if(StringUtils.isEmpty(rfPad.getPadStatus())){
			
		}
/*	  if (!(ConstantsDb.rfPadPadStatusOnline().equals(rfPad.getPadStatus()))) {
			throw new BusinessException("设备不在线，绑定失败");
		}*/
		 if (ConstantsDb.rfPadPadStatusControl().equals(rfPad.getPadStatus())) {
				throw new BusinessException("设备受控中，绑定失败");
			}
		Date bindTime = new Date();
		bean.setBindTime(bindTime);
		RfPad pad = this.get(bean.getPadId());
		pad.setModifier(SessionUtils.getCurrentUserId(request));
		pad.setModifyTime(bindTime);
		pad.setBindStatus(ConstantsDb.rfPadBindStatusBind());
		RfPadMapper mapper = (RfPadMapper) this.getMapper();
		userPadService.adminBinding(request, bean, goodsId, padClassify, gamePadOnlineTime);
		mapper.updateByPrimaryKeySelective(pad);
		
	}
	
	@Transactional(readOnly = true)
	public RfPad getPadByPadIp(String padIp){
		List<RfPad> list = this.initQuery().andEqualTo("padIp", padIp).singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public List<RfPad> getPadByDeviceId(Integer deviceId){
		List<RfPad> list = this.initQuery().andEqualTo("deviceId", deviceId).findAll();
		if(!Collections3.isEmpty(list)){
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * like查询padcode
	 * @param padCode
	 * @return List<RfPad>
	 */
	@Transactional(readOnly = true)
	public List<RfPad> getPadByPadCodes(String padCode){
		List<RfPad> list = this.initQuery().andLike("padCode", padCode).singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list;
		}else{
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public RfPad getDevicePadByPadCodes(String padCode){
		List<RfPad> list = this.initQuery().andEqualTo("padCode", padCode).andIsNotNull("vmStatus").singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public void batchPad(HttpServletRequest request, String padIp,String remark, String actionType) throws Exception {
		// TODO Auto-generated method stub
		padIp=padIp.replaceAll(" ", "");
		padIp=padIp.replaceAll("\r", "");
		String [] ips=padIp.split("\n");
		if(ips.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}
		String ids = "";
		for (String string : ips) {
			if (null != string &&!"".equals(string)) {
				RfPad pad = this.getPadByPadIp(string);
				if (pad == null) {
					throw new BusinessException("没有这个设备IP:" + string);
				}
				ids += pad.getPadId() + ",";
			}
		}
		if (null == ids || "".equals(ids)) {
			throw new BusinessException("错误：001 输入了无效参数");
		}
		if(remark.length()>500){
			throw new BusinessException("错误：描述的字节长度不得超过500");
		}
		RfPad bean = new RfPad();
		bean.setIds(ids);
		if(actionType.equals(ConstantsDb.rfPadEnableStatusStart())){
		// this.start(request, bean);
			if (StringUtils.isNoneBlank(remark)) {
				bean.setRemark(remark);
			}
			this.startBatch(request, bean);
		}
		if(actionType.equals(ConstantsDb.rfPadEnableStatusForbidden())){
			// this.stop(request, bean);
			if (StringUtils.isNoneBlank(remark)) {
				bean.setRemark(remark);
			}else{
				bean.setRemark("");
			}
			this.stopBacth(request, bean);
			}
		
		
	}

	public void openOn(HttpServletRequest request, RfPad bean) throws Exception {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String string : idArray) {
			RfPad pad=mapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfPad.FD_PAD_ID,RfPad.FD_VM_STATUS,RfPad.FD_DEVICE_ID), Integer.parseInt(string));
			if(null==pad.getVmStatus()&&null==pad.getDeviceId()){
				throw new BusinessException("该设备不是虚拟设备，或者没有关联物理设备，不能执行此操作");
			}
			pad.setDeviceId(null);
			pad.setVmStatus(null);
			pad.setGrantOpenStatus(ConstantsDb.rfPadGrantOpenStatusOn());
			pad.setPadId(Integer.parseInt(string));
			this.update(request, pad);
		}
		
	}
	
	public void openOff(HttpServletRequest request, RfPad bean) throws Exception {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String string : idArray) {
			RfPad pad=mapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfPad.FD_PAD_ID,RfPad.FD_VM_STATUS,RfPad.FD_DEVICE_ID), Integer.parseInt(string));
			if(null==pad.getVmStatus()&&null==pad.getDeviceId()){
				throw new BusinessException("该设备不是虚拟设备，或者没有关联物理设备，不能执行此操作");
			}
			pad.setDeviceId(null);
			pad.setVmStatus(null);
			pad.setGrantOpenStatus(ConstantsDb.rfPadGrantOpenStatusOff());
			pad.setPadId(Integer.parseInt(string));
			this.update(request, pad);
		}	
	}
	//启用设备时判断物理设备和虚拟设备
	@ServiceAnnotation(name = ServiceMethod.pre_start)
	public String preStart(HttpServletRequest request, RfPad bean) {
		
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String string : idArray) {
			RfPad pad=this.get(Integer.parseInt(string));
			if(null!=pad.getVmStatus()){
				if(!ConstantsDb.rfPadGrantOpenStatusOn().equals(pad.getGrantOpenStatus())){
					return ("选择了未授权的虚拟设备，"+pad.getPadCode()+"该设备不能启用");
				}
			      
			}
		}
		
		return null;
		}
	//根据MAC查找
	@Transactional(readOnly = true)
	public RfPad getPadByVmMac(String vmMac){
		List<RfPad> list = this.initQuery().andEqualTo("vmMac", vmMac).singleDelTrue();
		if(!Collections3.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	

	//批量更换设备
	public List<Integer> renewal(HttpServletRequest request, HttpServletResponse response, Model model, 
			String  code,String suffix,String isSendMessage,String isSendWechart,String sendMessageTemplate,String sendWechartTemplate) throws Exception {
		List<Integer> userIds = new ArrayList<Integer>();
		// TODO Auto-generated method stub
		code=code.replaceAll(" ", "");
		code=code.replaceAll("\r", "");
		String [] padCodes = code.split("\n");
		if(padCodes.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}else if(padCodes.length>500){
			throw new BusinessException("批量更换的设备不能超过500个");
		}
		for (String string : padCodes) {
			if (null != string &&!"".equals(string)) {
				RfPad pad = this.getPadByPadCode(string);
				if (pad == null) {
					throw new BusinessException("没有这个设备编号:" + string);
				}
			
				List<RfPad> list = this.initQuery()
				.andLikeSuffix("padName", suffix)
				.andEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart())
				.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusNobind())
				.andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusNomarl())
				.andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOnline())
				.andIsNotNull("deviceId")
				.andEqualTo("vmStatus",ConstantsDb.rfPadVmStatusOnline())
				.addOrderClause("modifyTime", "asc").singleStopTrue();
				if(list.size()<1){
					throw new BusinessException("没有可更换设备");
				}
				RfUserPad	bean = new RfUserPad();
				bean.setIds(String.valueOf(pad.getPadId()));
				bean.setRemark(String.valueOf(list.get(0).getPadCode()));
				String[] padIds = bean.getIds().split(",");
				String[] codes = bean.getRemark().split(",");
				for (int i = 0; i < padIds.length; i++) {
					RfPad oldpad = this.get(Integer.parseInt(padIds[i]));
					List<RfUserPad> userPadlist = userPadService.initQuery().andEqualTo("padId", oldpad.getPadId()).findStopTrue();
					if(Collections3.isEmpty(userPadlist)){
						throw new BusinessException("该设备没有绑定记录："+string);
					}
					if(userPadlist.size()>1){
						throw new BusinessException("该设备ID绑定信息异常："+oldpad.getPadId());
					}
					if (!(oldpad.getPadCode().equals(codes[i]))) {
						RfUserPad rfUserPad =userPadlist.get(0); 
						userIds.add(rfUserPad.getUserId());
						// 旧设备解绑日志
						// 删除原有绑定记录 user_pad
						/** 获取一个设备, 并且锁定该设备*/
						RfPad newPad = this.getLock( this.initQuery().andEqualTo("padCode", codes[i]).singleDelTrue().get(0).getPadId());
						if (newPad == null) {
							throw new BusinessException("设备号错误，请重新选择设备");
						}
						if (newPad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
							throw new BusinessException(newPad.getPadCode()+"设备绑定中，请重新选择设备");
						}
						if (!(ConstantsDb.rfPadPadStatusOnline().equals(newPad.getPadStatus()))) {
							throw new BusinessException(newPad.getPadCode()+"设备当前状态不能绑定");
						}
						if (newPad.getFaultStatus().equals(ConstantsDb.rfPadFaultStatusFault())) {
							throw new BusinessException(newPad.getPadCode()+"设备故障中，请重新选择设备");
						}
						userPadService.renewal(request,rfUserPad,newPad,isSendMessage,isSendWechart,sendMessageTemplate,sendWechartTemplate);
					}
				}
			}
		}
		return userIds;
	}
	
	//批量更换设备
	public Map<String,Object> renewalOnly(HttpServletRequest request, HttpServletResponse response, Model model, String  code,String suffix ,
			String isSendMessage,String isSendWechart,String sendMessageTemplate,String sendWechartTemplate) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		Integer userId = null;
		
		if (null != code &&!"".equals(code)) {
			RfPad pad = this.getPadByPadCode(code);
			if (pad == null) {
				throw new BusinessException("没有这个设备编号:" + code);
			}
			
			List<RfPad> list = this.initQuery()
			.andLikeSuffix("padName", suffix)
			.andEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart())
			.andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusNobind())
			.andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusNomarl())
			.andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOnline())
			.andEqualTo("padClassify",StringUtils.isNotEmpty(pad.getPadClassify())?pad.getPadClassify():PadClassify.PAD_CLASSIFY_MAIN)//更换设备只能更换同设备类别的(1主营设备、2游戏设备)//TODO
			.andIsNotNull("deviceId")
			.andEqualTo("vmStatus",ConstantsDb.rfPadVmStatusOnline())
			.addOrderClause("modifyTime", "asc").singleStopTrue();
			if(list.size()<1){
				throw new BusinessException(code+"没有可更换设备");
			}
			RfUserPad	bean = new RfUserPad();
			bean.setIds(String.valueOf(pad.getPadId()));
			bean.setRemark(String.valueOf(list.get(0).getPadCode()));
			String[] padIds = bean.getIds().split(",");
			String[] codes = bean.getRemark().split(",");
			for (int i = 0; i < padIds.length; i++) {
				RfPad oldpad = this.get(Integer.parseInt(padIds[i]));
				List<RfUserPad> userPadlist = userPadService.initQuery().andEqualTo("padId", oldpad.getPadId()).findStopTrue();
				if(Collections3.isEmpty(userPadlist)){
					throw new BusinessException("该设备没有绑定记录："+code);
				}
				if(userPadlist.size()>1){
					throw new BusinessException("该设备ID绑定信息异常："+oldpad.getPadId());
				}
				if (!(oldpad.getPadCode().equals(codes[i]))) {
					RfUserPad rfUserPad =userPadlist.get(0); 
					// 旧设备解绑日志
					// 删除原有绑定记录 user_pad
					/** 获取一个更换的设备, 并锁定*/
					RfPad newPad = this.getLock( this.initQuery().andEqualTo("padCode", codes[i]).singleDelTrue().get(0).getPadId());
					if (newPad == null) {
						throw new BusinessException("设备号错误，请重新选择设备"+code);
					}
					if (newPad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
						throw new BusinessException(newPad.getPadCode()+"设备绑定中，请重新选择设备");
					}
					if (!(ConstantsDb.rfPadPadStatusOnline().equals(newPad.getPadStatus()))) {
						throw new BusinessException(newPad.getPadCode()+"设备当前状态不能绑定");
					}
					if (newPad.getFaultStatus().equals(ConstantsDb.rfPadFaultStatusFault())) {
						throw new BusinessException(newPad.getPadCode()+"设备故障中，请重新选择设备");
					}
					userId = rfUserPad.getUserId();
					Integer wechartTemplateId = userPadService.renewal(request,rfUserPad,newPad,isSendMessage,isSendWechart,sendMessageTemplate,sendWechartTemplate);
					if(null != wechartTemplateId){
						params.put("wechartTemplateId", wechartTemplateId);
					}
				}
			}
		}
		params.put("userId", userId);
		return params;
	}


	public void maintain(HttpServletRequest request, RfPad bean) {
		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			RfPad pad=new RfPad();
			pad.setPadId(Integer.parseInt(idStr));
			pad.setMaintStatus(bean.getMaintStatus());
			pad.setModifyTime(currentDate);
			pad.setModifier(userId);
			mapper.updateByPrimaryKeySelective(pad);
		}
	}
	
	public void batchMaintain(HttpServletRequest request, String maintStatus,String code) {
		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		code=code.replaceAll(" ", "");
		code=code.replaceAll("\r", "");
		String [] padCodes = code.split("\n");
		if(padCodes.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}else if(padCodes.length>2000){
			throw new BusinessException("批量维护操作不能超过2000个");
		}
		
		List<String> codeList = new ArrayList<String>();
		for (String str : padCodes) {
			codeList.add(str);
		}
		
		List<List<String>> codeLists = this.createList(codeList, 999);
		for (List<String> list : codeLists) {
			log.info("修改设备的个数："+list.size());
			RfPad pad=new RfPad();
			pad.setMaintStatus(maintStatus);
			pad.setModifyTime(currentDate);
			pad.setModifier(userId);
			
			RfPadExample example = new RfPadExample();
			example.createCriteria().andPadCodeIn(list);
			mapper.updateByExampleSelective(pad, example);
		}
		
		/*List<String> codeList = new ArrayList<String>();
		for (String padCode : padCodes) {
			if(StringUtils.isNoneBlank(padCode)){
				codeList.add(padCode);
				RfPad pad=new RfPad();
				pad.setPadCode(padCode);
				pad.setMaintStatus(maintStatus);
				pad.setModifyTime(currentDate);
				pad.setModifier(userId);
				mapper.updateByPadCode(pad);
			}
		}*/
	}
	
	public void stopPad(HttpServletRequest request, RfPad bean) throws Exception {
		RfPad pad = new RfPad();
		pad.setPadId(bean.getPadId());
		pad.setRemark(bean.getRemark());
		pad.setEnableStatus(ConstantsDb.globalEnableStatusStop());
		pad.setModifier(SessionUtils.getCurrentUserId(request));
		pad.setModifyTime(new Date());
		mapper.updateByPrimaryKeySelective(pad);
		padHandleLogService.stopPad(request, bean);
	}
	
	public List<UserPadDto> selectPadByUserId(Integer userId) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		return mapper.selectPadByUserId(params);
	}
	
	
	public void savePadLabel(HttpServletRequest request, String labelIds,String padCodes) throws Exception{
		String admin = SessionUtils.getCurrentUserId(request);
		padCodes = padCodes.replaceAll(" ", "");
		String[] codes = padCodes.split("\n");
		String ids[] = labelIds.split("\\|");
		if(codes.length > 1000){
			throw new BusinessException("设备个数不能超过1000个");
		}
		if(ids.length > 5){
			throw new BusinessException("设备标签个数不能超过5个");
		}
		
		deleteByPadId(codes);
		
		for(String labelId : ids){
			if(StringUtils.isNotBlank(labelId)){
				saveByLabelIdOrPhones(Integer.parseInt(labelId.trim()), codes,admin);
			}
		}
	}
	
	public void saveByLabelIdOrPhones(Integer labelId,String[] codes,String creater) throws Exception{
		List<RfPad> list = selectPadByCodes(codes);
		labelService.savePadLabel(labelId, list, creater);//然后保存
	}
	
	public void deleteByPadId(String[] padCodes) throws Exception{
		List<RfPad> list = selectPadByCodes(padCodes);
		List<Integer> padIds = selectPadIdByList(list);
		labelService.deleteByPadId(padIds);//先删除
	}
	
	public List<RfPad> selectPadByCodes(String[] codes) throws Exception{
		List<String> list = new ArrayList<String>();
		for (String string : codes) {
			if(StringUtils.isNotBlank(string)){
				list.add(string.trim());
			}
		}
		RfPadExample example = new RfPadExample();
		example.createCriteria().andPadCodeIn(list);
		return mapper.selectByExample(example);
	}
	
	public List<Integer> selectPadIdByList(List<RfPad> list) throws Exception{
		return Collections3.extractToList(list, "padId");
	}

	/**
	 * 根据条件查询
	 * @param map
	 * @return
	 */
	public Page<RfPad> seleteByParamsMap(Map<String, Object> map) {
		return mapper.seleteByParamsMap(map);
	}

	/**
	 * 根据条件查询总数
	 * @param params
	 * @return
	 */
	public Integer seleteByParamsCount(Map<String, Object> params) {
		return mapper.seleteByParamsCount(params);
	}
	
	/**
	 * 启用数据
	 * 
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public void startBatch(HttpServletRequest request, RfPad bean) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_start, this, request, bean);
		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String pk=Reflections.getPrimaryKey(this);
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			Object objectBean=bean.getClass().newInstance();
			this.setDefaultValueToField(objectBean, pk, Reflections.fieldIsIntType(bean, pk)?Integer.parseInt(idStr):idStr);
			this.setDefaultValueToField(objectBean, "enableStatus", ConstantsDb.globalEnableStatusNomarl());
			this.setDefaultValueToField(objectBean, "modifyTime", currentDate);
			this.setDefaultValueToField(objectBean, "modifier", userId);
			this.setDefaultValueToField(objectBean, "remark", bean.getRemark());
			this.updatePad(this.getMapper(), new Object[] { objectBean });
		}
		this.executeAopMethod(ServiceMethod.after_start, this, request, bean);
	}

	/**
	 * 禁用数据
	 * 
	 * @param request
	 * @param bean
	 * @throws Exception
	 */
	public void stopBacth(HttpServletRequest request, RfPad bean) throws Exception {
		this.executeAopMethod(ServiceMethod.pre_stop, this, request, bean);

		String userId = SessionUtils.getCurrentUserId(request);
		Date currentDate = new Date();
		String pk=Reflections.getPrimaryKey(this);
		String[] idArray=StringUtils.split(bean.getIds(),",");
		for(String idStr:idArray){
			Object objectBean=bean.getClass().newInstance();
			this.setDefaultValueToField(objectBean, pk, Reflections.fieldIsIntType(bean, pk)?Integer.parseInt(idStr):idStr);
			this.setDefaultValueToField(objectBean, "enableStatus", ConstantsDb.globalEnableStatusStop());
			this.setDefaultValueToField(objectBean, "modifyTime", currentDate);
			this.setDefaultValueToField(objectBean, "modifier", userId);
			this.setDefaultValueToField(objectBean, "remark", bean.getRemark());
			this.updatePad(this.getMapper(), new Object[] { objectBean });
		}

		this.executeAopMethod(ServiceMethod.after_stop, this, request, bean);
	}
	
	/**
	 * 执行AOP方法
	 * 
	 * @param serviceMethod
	 * @param obj
	 * @param request
	 * @param bean
	 */
	private void executeAopMethod(ServiceMethod serviceMethod, Object obj, HttpServletRequest request, Object bean) {
		Method method = Reflections.getAnnotationMethod(this, serviceMethod);
		if (method != null) {
			Object result = null;
			try {
				result = method.invoke(this, request, bean);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			if (result != null) {
				throw new BusinessException(result.toString());
			}
		}
	}
	
	public List<Integer> selectDeviceIdByIdcId(int idcId) {
		return mapper.selectDeviceIdByIdcId(idcId);
	}
	
	/**
	 * 强制填充value到domain的name字段
	 * 
	 * @param bean
	 * @param name
	 * @param value
	 */
	private void setDefaultValueToField(Object bean, String name, Object value) {
		if (value != null && Reflections.getAccessibleField(bean, name) != null) {
			Reflections.setFieldValue(bean, name, value);
		}
	}
	
	/**
	 * 修改
	 * 
	 * @param mapper
	 * @param args
	 */
	private void updatePad(final Object mapper, final Object[] args) {
		Reflections.invokeMethodByName(mapper, "updateByPrimaryKeySelective", args);
	}
	
	public int countByExample(RfPadExample example){
		return mapper.countByExample(example);
	}
	
	/**
	 * 
	 * 根据id查询设备, 并且锁定该行数据
	 * @param padId 设备Id
	 * @return RfPad
	 */
	public RfPad getLock(int padId){
		return mapper.selectByPrimaryKeyForUpdate(padId);
	}
}
