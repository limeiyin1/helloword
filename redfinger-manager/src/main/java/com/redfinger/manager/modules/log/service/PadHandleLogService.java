package com.redfinger.manager.modules.log.service;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.LogPad;
import com.redfinger.manager.common.domain.LogPadExample;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadGrant;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.mapper.LogPadMapper;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.base.service.ResIpService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sys.service.AdminService;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "id")
public class PadHandleLogService extends BaseService<LogPad, LogPadExample, LogPadMapper> {
	@Autowired
	PadService padService;
	@Autowired
	AdminService adminService;
	@Autowired
	UserService userService;
	@Autowired
	ResIpService resIpService;

	public LogPad readIn(HttpServletRequest request, RfPad bean) throws Exception {

		LogPad logPad = new LogPad();
		bean = padService.get(bean.getPadId());
		SysAdmin admin = adminService.get(SessionUtils.getCurrentUserId(request));
		logPad.setCreater(admin.getAdminCode());
		logPad.setPadId(bean.getPadId());// 设备Id
		logPad.setPadCode(bean.getPadCode());// 设备code
		if(admin.getLoginIp().isEmpty()){
		logPad.setOperIp("000.000.000.000");
		}else{
		logPad.setOperIp(admin.getLoginIp());// 当前操作员的登录ip
		}
		return logPad;

	}

	public LogPad readIn(HttpServletRequest request, RfPad bean, RfUser user) throws Exception {

		LogPad logPad = new LogPad();
		bean = padService.get(bean.getPadId());
		user = userService.get(user.getUserId());
		logPad.setCreater(user.getCreater());
		logPad.setPadId(bean.getPadId());// 设备Id
		logPad.setPadCode(bean.getPadCode());// 设备code
		logPad.setOperIp(user.getLoginIp());// 当前操作员的ip
		return logPad;

	}

	// 启动
	public void startPad(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryStart()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "启动操作" + "登录IP是" + logPad.getOperIp());
		this.save(request, logPad);
	}

	// 停用
	public void stopPad(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryStop()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "停用操作" + "登录IP是" + logPad.getOperIp());
		this.save(request, logPad);
	}

	// 删除
	public void deletePad(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryRemove()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "删除操作" + "登录IP是" + logPad.getOperIp());
		this.save(request, logPad);
	}

	// 解绑
	public void relievePad(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setCategory(ConstantsDb.logPadCategoryRelieve()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "解绑操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}

	// 绑定
	public void bindingPad(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryBind());
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "绑定操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}
	// 管理员绑定
	public void bindingPadAdmin(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryAdminbinding());
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "管理员绑定操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}
	//管理员解绑
	public void relievePadAdmin(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setCategory(ConstantsDb.logPadCategoryAdminrelieve()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "管理员解绑操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}

	// 绑定VIP
	public void bindingPadVIP(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryVipbind());
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "VIP绑定操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}
	//VIP解绑
	public void relievevip(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setCategory(ConstantsDb.logPadCategoryViprelieve()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "VIP解绑操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}
	
	//SVIP解绑
	public void relieveSvip(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setCategory(ConstantsDb.logPadCategorySviprelieve()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "SVIP解绑操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}
	
	// 绑定GVIP
	public void bindingPadGVIP(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryGVipbind());
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "GVIP绑定操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}
	//GVIP解绑
	public void relieveGvip(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setCategory(ConstantsDb.logPadCategoryGViprelieve()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "GVIP解绑操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}
	
	// 绑定游戏设备
	public void bindingPadGame(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryVipbind());
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "绑定操作游戏设备操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}
	
	// 更换
	public void renewalPad(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setCategory(ConstantsDb.logPadCategoryRelievechange()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" +DateUtils.formatDateTime(bean.getModifyTime()) + "执行设备旧设备" + bean.getPadCode() + "更换操作" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}

	public void renewalPadNew(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCreateTime(bean.getModifyTime());
		logPad.setCategory(ConstantsDb.logPadCategoryBindchange()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.formatDateTime(bean.getModifyTime()) + "执行设备更换操作" + bean.getPadCode() + "绑定新设备" + "登录IP是" + logPad.getOperIp());
		this.insert(request, logPad);
	}

	// 新增
	public void addPad(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory(ConstantsDb.logPadCategoryAdd()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "新增操作" + "登录IP是" + logPad.getOperIp());
		this.save(request, logPad);
	}

	// 登录设备 (待测试)
	public void loginPad(HttpServletRequest request, RfPad bean, RfUser user) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCategory("loginpad"); // 操作类型
		logPad.setRemark("会员" + user.getUserId() + "在" + DateUtils.getDateTime() + "执行设备" + bean.getPadCode() + "登录操作" + "登录IP是" + logPad.getOperIp());
		this.save(request, logPad);
	}
	
	public void upVIP(HttpServletRequest request, RfUserPad bean) throws Exception {
	RfPad pad=	padService.get(bean.getPadId());
	RfUser user=userService.get(bean.getUserId());
		LogPad logPad = readIn(request,pad );
		logPad.setCategory(ConstantsDb.logPadCategoryUpvip()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.getDateTime() + "执行设备升级VIP" + pad.getPadCode() + "操作" + "登录IP是" + logPad.getOperIp()+"会员帐号信息id"+user.getUserId()+"手机"+user.getUserMobilePhone()+"邮箱"+user.getUserEmail());
		this.save(request, logPad);
	}
	/*	
	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, LogPad bean) {
	    	try {
		ResIp resIp = new ResIp();
		if (resIpService.get(bean.getOperIp()) == null) {
			Map<String, String> addres = AddressUtils.getAddresses("ip=" + bean.getOperIp(), "utf-8");
			if ( !"0:0:0:0:0:0:0:1".equals(bean.getOperIp())&& !( addres.isEmpty())) {
				resIp.setIp(bean.getOperIp());
				resIp.setCountry(addres.get("country").toString());
				resIp.setRegion(addres.get("region").toString());
				resIp.setProvince(addres.get("province").toString());
				resIp.setCity(addres.get("city").toString());
				resIp.setArea(addres.get("area").toString());
				resIp.setIsp(addres.get("isp").toString());
				resIpService.save(request, resIp);
			}
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}	*/
	public void insert(HttpServletRequest request,LogPad bean){
		LogPadMapper mapper=(LogPadMapper)this.getMapper();
	try {
		mapper.insertSelective(bean);
	} catch (Exception e) {
		/*throw new BusinessException("LOG_PAD日志写入失败");*/
		e.printStackTrace();
	}
		
	}

	public void changePad(HttpServletRequest request, RfPad bean) throws Exception {
		LogPad logPad = readIn(request, bean);
		logPad.setCreateTime(bean.getCreateTime());
		logPad.setCategory(ConstantsDb.logPadCategoryChange()); // 操作类型
		logPad.setRemark(logPad.getCreater() + "在" + DateUtils.formatDateTime(bean.getCreateTime()) + "执行设备换新操作"+ "登录IP是" + logPad.getOperIp()+"批次号更新为"+bean.getBatchNumber());
		this.insert(request, logPad);
		
	}

}
