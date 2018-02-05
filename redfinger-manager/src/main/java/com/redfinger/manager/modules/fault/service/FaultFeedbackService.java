package com.redfinger.manager.modules.fault.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.redfinger.manager.common.base.BaseService;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfFaultFeedback;
import com.redfinger.manager.common.domain.RfFaultFeedbackExample;
import com.redfinger.manager.common.domain.RfFaultHandle;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadTask;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.helper.PrimaryKeyAnnotation;
import com.redfinger.manager.common.helper.ServiceAnnotation;
import com.redfinger.manager.common.helper.ServiceMethod;
import com.redfinger.manager.common.mapper.RfDeviceMapper;
import com.redfinger.manager.common.mapper.RfFaultFeedbackMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.FaultFeedbackLastHandlerType;
import com.redfinger.manager.common.utils.Md5Utils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.log.service.PadTaskService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.tasks.jms.ProducterMessageSender;

@Transactional
@Service
@PrimaryKeyAnnotation(field = "faultFeedbackId")
public class FaultFeedbackService extends BaseService<RfFaultFeedback, RfFaultFeedbackExample, RfFaultFeedbackMapper> {
	public static Logger logger = LoggerFactory.getLogger(FaultFeedbackService.class);
	
	@Autowired
	PadService padService;
	@Autowired
	ClassService classService;
	@Autowired
	FaultHandleService faultHandleService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	AdminService adminService;
	@Autowired
	UserService userService;
	@Autowired
	IdcService idcService;
	@Autowired
	PadTaskService padTaskService;
	@Autowired
	ProducterMessageSender pro;
	@Autowired
	RfDeviceMapper rfDeviceMapper;
	@Autowired
	RfFaultFeedbackMapper mapper;

	@ServiceAnnotation(name = ServiceMethod.pre_save)
	public String preSave(HttpServletRequest request, RfFaultFeedback bean) throws Exception {
		// 根据padCode获取pad
		RfPad pad = padService.getPadByPadCode(bean.getPadCode());
		if (pad == null) {
			return "设备编号[" + bean.getPadCode() + "]不存在！";
		}
		// 如果已经绑定
		RfUserPad userPad = userPadService.getUserPadByPadId(pad.getPadId());
		// 若用户已绑定设备, 则设置用户id
		if (userPad != null) {
			bean.setUserId(userPad.getUserId());
		}
		// 设置设备故障状态为:1(故障)
		pad.setFaultStatus(ConstantsDb.rfPadFaultStatusFault());
		// 更新设备数据
		padService.update(request, pad);
		// 设置来源ip
		bean.setFeedbackIp(StringUtils.getRemoteAddr(request));
		// 设置故障工单状态为 '新工单'
		bean.setFeedbackStatus(ConstantsDb.rfFaultFeedbackFeedbackStatusNew());
		// 设置咨询人为当前用户
		bean.setPromoter(SessionUtils.getCurrentUserId(request));
		// 设置设备id
		bean.setPadId(pad.getPadId());
		// 设置批次号, 默认为1
		bean.setBatchNumber(pad.getBatchNumber() == null ? 1 : pad.getBatchNumber());
		return null;
	}

	@ServiceAnnotation(name = ServiceMethod.pre_update)
	public String preUpdate(HttpServletRequest request, RfFaultFeedback bean) throws Exception {
		RfFaultFeedback feedback = this.get(bean.getFaultFeedbackId());
		String status = feedback.getFeedbackStatus();
		String currentUserId = SessionUtils.getCurrentUserId(request);
		if (status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusHandle()) || status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusEnd())) {
			return "当前故障状态为:"+DictHelper.getLabel("rf_fault_feedback.feedback_status", status)+",不允许修改！";
		}
		// 客户端工单
		if (ConfigConstantsDb.configFaultClientCreater().indexOf(feedback.getCreater())!=-1) {
			return "客户端或系统提交的工单不允许修改！";
		}
		// 新故障
		if (status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusNew())) {
			if (!currentUserId.equals(feedback.getPromoter())) {
				return "新工单，只允许本人修改！";
			}
		}
		// 受理中
		if (status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusProcessing())) {
			if (!currentUserId.equals(feedback.getLastHandler())) {
				return "受理工单后，只允许受理人修改！";
			}
		}
		// 放弃
		if (status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveceshi()) || status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMovekefu())
				|| status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveyunwei())) {
			if (!currentUserId.equals(feedback.getPromoter())) {
				return "移交工单，只允许咨询本人修改！";
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<RfFaultFeedback> query(HttpServletRequest request, RfFaultFeedback bean, List<String> feedbackStatusList, boolean addClient, String padCodeStart, String padCodeEnd,String padStatus, String vmStatus) {
		String regexStr = "^[0-9a-zA-Z]+$";
		// 获取类型的所有子节点
		List<Integer> classIdList = (List<Integer>) classService.getChildAndSelfIds(ConstantsDb.rfClassClassTypeFault(), bean.getClassId());
		// 修复类型
		List<Integer> smallClassIdList = (List<Integer>) classService.getChildAndSelfIds(ConstantsDb.rfClassClassTypeFaultfix(), bean.getSmallClassId());
		this.initQuery(bean)
				.andIn("classId", classIdList) // 故障类型
				.andIn("smallClassId", smallClassIdList) //故障细节
				.andIn("feedbackStatus", feedbackStatusList) // 故障状态集
				.andEqualTo("promoter", bean.getPromoter()) // 咨询人
				.andEqualTo("lastHandler", bean.getLastHandler()) // 最后处理人
				.andEqualTo("feedbackSource", bean.getFeedbackSource()) // 故障来源
				.andEqualTo("operateType", bean.getOperateType()) // 操作类型
				.andEqualTo("padStatus", padStatus) // 设备状态
				.andEqualTo("vmStatus", vmStatus) // 虚拟机状态
				.andLike("padCode", bean.getPadCode()) // 设备编码
				.andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()));
				if(StringUtils.isNotEmpty(padCodeStart)){
					if(!padCodeStart.matches(regexStr)){
						this
						.andGreaterThan("padCode", "-1")// 设备编码开始
						.andLessThan("padCode", "-1"); // 设备编码结束
					}else{
						this.andGreaterThanOrEqualTo("padCode", padCodeStart); // 设备编码开始
					}
				}
				if(StringUtils.isNotEmpty(padCodeEnd)){
					if(!padCodeEnd.matches(regexStr)){
						this
						.andGreaterThan("padCode", "-1")// 设备编码开始
						.andLessThan("padCode", "-1"); // 设备编码结束
					}else{
						this.andLessThanOrEqualTo("padCode", padCodeEnd); // 设备编码结束
					}
				}
				this
				.addOrderClause("createTime", "desc") 
				.addOrderForce(bean.getSort(), bean.getOrder());
		if (addClient) {
			RfFaultFeedbackExample example = (RfFaultFeedbackExample) this.getExample();
			RfFaultFeedbackExample.Criteria criteria = example.or();
			
			RfFaultFeedbackExample.Criteria criteria2 = example.or();
			// 处理类型查询
			criteria2.andFeedbackReplyType(FaultFeedbackLastHandlerType.ALL_USER);
			// 状态为已处理
			criteria2.andFeedbackStatusEqualTo(bean.getFeedbackStatus() != null ? bean.getFeedbackStatus() : ConstantsDb.rfFaultFeedbackFeedbackStatusHandle());
			
			
			if (feedbackStatusList.size() == 6) {
				criteria.andFeedbackStatusEqualTo(ConstantsDb.rfFaultFeedbackFeedbackStatusHandle());
				} else {
				criteria.andFeedbackStatusIn(feedbackStatusList);
			}
			// 故障咨询包括的创建人(super, system)
			List<String> creatorList=Lists.newArrayList(ConfigConstantsDb.configFaultClientCreater().split(","));
			criteria.andCreaterIn(creatorList);
			// 故障类型集
			if (!Collections3.isEmpty(classIdList)) {
				criteria.andClassIdIn(classIdList);
				criteria2.andClassIdIn(classIdList);
			}
			// 故障细节集
			if (!Collections3.isEmpty(smallClassIdList)) {
				criteria.andSmallClassIdIn(classIdList);
				criteria2.andSmallClassIdIn(classIdList);
			}
			// 最后处理人
			if (StringUtils.isNotEmpty(bean.getLastHandler())) {
				criteria.andLastHandlerEqualTo(bean.getLastHandler());
				criteria2.andLastHandlerEqualTo(bean.getLastHandler());
			}
			// 故障来源
			if (StringUtils.isNotEmpty(bean.getFeedbackSource())) {
				criteria.andFeedbackSourceEqualTo(bean.getFeedbackSource());
				criteria2.andFeedbackSourceEqualTo(bean.getFeedbackSource());
			}
			// 设备编码
			if (StringUtils.isNotEmpty(bean.getPadCode())) {
				criteria.andPadCodeLike("%" + bean.getPadCode() + "%");
				criteria2.andPadCodeLike("%" + bean.getPadCode() + "%");
			}
			// 故障开始时间
			if (StringUtils.isNotEmpty(bean.getBeginTimeStr())) {
				criteria.andCreateTimeGreaterThanOrEqualTo(DateUtils.parseDate(bean.getBeginTimeStr()));
				criteria2.andCreateTimeGreaterThanOrEqualTo(DateUtils.parseDate(bean.getBeginTimeStr()));
			}
			// 故障结束时间
			if (StringUtils.isNotEmpty(bean.getEndTimeStr())) {
				criteria.andCreateTimeLessThan(DateUtils.parseDateAddOneDay(bean.getEndTimeStr()));
				criteria2.andCreateTimeLessThan(DateUtils.parseDateAddOneDay(bean.getEndTimeStr()));
			}
			// 设备编码段
			if(StringUtils.isNotEmpty(padCodeStart)){
				if(!padCodeStart.matches(regexStr)){
					criteria.andPadCodeGreaterThan("-1");
					criteria.andPadCodeLessThan("-1");
					criteria2.andPadCodeLessThan("-1");
					criteria2.andPadCodeGreaterThan("-1");
				}else{
					criteria.andPadCodeGreaterThanOrEqualTo(padCodeStart);
					criteria2.andPadCodeGreaterThanOrEqualTo(padCodeStart);
				}
			}
			if(StringUtils.isNotEmpty(padCodeEnd)){
				if(!padCodeEnd.matches(regexStr)){
					criteria.andPadCodeGreaterThan("-1");
					criteria.andPadCodeLessThan("-1");
					criteria2.andPadCodeLessThan("-1");
					criteria2.andPadCodeGreaterThan("-1");
				}else{
					criteria.andPadCodeLessThanOrEqualTo(padCodeEnd);
					criteria2.andPadCodeLessThanOrEqualTo(padCodeEnd);
				}
			}
		}
		// 分页查询所有正常数据
		List<RfFaultFeedback> list = this.pageStopTrue(bean.getPage(), bean.getRows());
		for (RfFaultFeedback feedback : list) {
			// 设置设备故障类型名称, 格式为:兼容故障 - 停止运行
			feedback.getMap().put("className", classService.getFullClass(ConstantsDb.rfClassClassTypeFault(), feedback.getClassId(), " - "));
			//　设置故障修复类型名称
			if (feedback.getSmallClassId() != null) {
				feedback.getMap().put("fixName", classService.getFullClass(ConstantsDb.rfClassClassTypeFaultfix(), feedback.getSmallClassId(), " - "));
			}
			//　设置询问人
			if (StringUtils.isNotEmpty(feedback.getPromoter())) {
				feedback.getMap().put("promoter", adminService.get(feedback.getPromoter()).getAdminName());
			}
			//　设置最后处理人
			if (StringUtils.isNotEmpty(feedback.getLastHandler())) {
				feedback.getMap().put("lastHandler", adminService.get(feedback.getLastHandler()).getAdminName());
			}
			// 获取设备
			if (StringUtils.isNotEmpty(feedback.getPadCode())) {
				RfPad pad = padService.getPadByPadCode(feedback.getPadCode());
				if (pad != null) {
					feedback.getMap().put("padIp", pad.getPadIp());
					feedback.getMap().put("bindStatus", pad.getBindStatus());
					//feedback.getMap().put("padStatus", pad.getPadStatus());
					//feedback.getMap().put("vmStatus", pad.getVmStatus());
					// 获取设备等级
					if (pad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
						RfUserPad userPad = userPadService.getUserPadByPadId(pad.getPadId());
						if (userPad != null) {
							feedback.getMap().put("padGrade", userPad.getPadGrade());
						}
					}
					
					if(null != pad.getDeviceId()){
						RfDevice device = rfDeviceMapper.selectByPrimaryKey(pad.getDeviceId());
						feedback.getMap().put("deviceIp", null != device ? device.getDeviceIp() : "");
					}
					
				}
			}
		}
		return list;
	}

	// 受理故障单
	public synchronized void accept(HttpServletRequest request, RfFaultFeedback bean) throws Exception {
		String[] ids = bean.getIds().split(",");
		// 获取当前登录用户名称
		String userId = SessionUtils.getCurrentUserId(request);
		Date date = new Date();
		for (String id : ids) {
			// 获取当前故障对象
			bean = this.get(Integer.parseInt(id));
			// 获取当前的故障处理状态
			String status = bean.getFeedbackStatus();
			// 当前故障状态非新工单,移交测试,移交运维,移交客服时, 抛出异常
			if (!status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusNew()) && !status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveceshi())
					&& !status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveyunwei()) && !status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMovekefu())) {
				throw new BusinessException("设备[" + bean.getPadCode() + "]当前故障状态为:"+DictHelper.getLabel("rf_fault_feedback.feedback_status", status)+",不允许受理！");
			}
			// 创建故障反馈对象
			RfFaultFeedback feedback = new RfFaultFeedback();
			// 设置故障反馈id
			feedback.setFaultFeedbackId(bean.getFaultFeedbackId());
			// 设置故障反馈状态为 '处理中'
			feedback.setFeedbackStatus(ConstantsDb.rfFaultFeedbackFeedbackStatusProcessing());
			// 设置修改人
			feedback.setModifier(userId);
			// 设置最后处理人
			feedback.setLastHandler(userId);
			// 设置修改时间
			feedback.setModifyTime(date);
			// 故障咨询中创建人是否包含创建人
			// super,system
			if(ConfigConstantsDb.configFaultClientCreater().indexOf(bean.getCreater())!=-1){
				// 添加咨询人
				feedback.setPromoter(userId);
			}
			
			RfFaultFeedbackMapper feedbackMapper = (RfFaultFeedbackMapper) this.getMapper();
			// 更新
			feedbackMapper.updateByPrimaryKeySelective(feedback);
			// 创建故障反馈处理类
			RfFaultHandle handle = new RfFaultHandle();
			// 设置管理员id
			handle.setAdminUserId(userId);
			//设置故障反馈id
			handle.setFaultFeedbackId(bean.getFaultFeedbackId());
			// 设置为是否解决:未解决
			handle.setIsSolve(ConstantsDb.rfFaultFeedbackIsSolveNosolve());
			// 保存
			faultHandleService.save(request, handle);
			// 更新设备为故障状态
			if (StringUtils.isNotEmpty(bean.getPadCode())) {
				RfPad pad = padService.getPadByPadCode(bean.getPadCode());
				if (pad != null) {
					// 设置设备状态为: 故障
					pad.setFaultStatus(ConstantsDb.rfPadFaultStatusFault());
					padService.update(request, pad);
				}
			}
		}
	}
	
	// 批量受理工单 
	public synchronized String batchAccept(HttpServletRequest request, String padCodes, Map<String, String> errorMsg) throws Exception {
		padCodes = padCodes.replaceAll(" ", "");
		padCodes = padCodes.replaceAll("\r", "");
		String[] padCode = padCodes.split("\n");
		
		if(padCode.length > 2000){
			throw new BusinessException("受理的设备个数不能大于2000！");
		}
		
		String userId = SessionUtils.getCurrentUserId(request);
		Date date = new Date();
		int i = 0;
		StringBuffer buffer = new StringBuffer();
		List<String> statusList =new ArrayList<String>();
		// 0:新工单
		statusList.add(ConstantsDb.rfFaultFeedbackFeedbackStatusNew().trim());
		// 3:移交测试
		statusList.add(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveceshi().trim());
		// 4:移交运维
		statusList.add(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveyunwei().trim());
		// 2:移交客服
		statusList.add(ConstantsDb.rfFaultFeedbackFeedbackStatusMovekefu().trim());
		
		List<String> padCodeList = Lists.newArrayList();
		
		for (String code : padCode) {
			if(StringUtils.isNotBlank(code)){
				if(padCodeList.contains(code)){
					continue;
				}
				padCodeList.add(code);
				i = 0;
				RfFaultFeedbackExample example = new RfFaultFeedbackExample();
				// 创建根据padCode和故障反馈工单状态查询的条件
				example.createCriteria().andPadCodeEqualTo(code)
				.andFeedbackStatusIn(statusList);//状态为新工单，移交测试，移交运维，移交客户
				List<RfFaultFeedback> list = mapper.selectByExample(example);
				if(null == list || list.size()<=0){
					//throw new BusinessException(code+"该设备没有故障可以受理！");
					if(errorMsg != null){
						errorMsg.put(code, "该设备没有故障可以受理！");
					}
				}
				for (RfFaultFeedback rfFaultFeedback : list) {
					String status = rfFaultFeedback.getFeedbackStatus();
					if (!status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusNew()) && !status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveceshi())
							&& !status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveyunwei()) && !status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMovekefu())) {
						i++;
						/*throw new BusinessException("设备[" + rfFaultFeedback.getPadCode() + "]当前故障状态不允许受理！");*/
						if(i <= 1){
							buffer.append("|"+rfFaultFeedback.getPadCode());
						}
						logger.info("faultFeedbackId为["+rfFaultFeedback.getFaultFeedbackId()+"],设备[" + rfFaultFeedback.getPadCode() + "]的当前状态为"+status+",是不允许受理！");
						if(errorMsg != null){
							errorMsg.put(code, "该设备当前状态为"+status+",不允许受理！");
						}
						continue;
					}
					RfFaultFeedback feedback = new RfFaultFeedback();
					feedback.setFaultFeedbackId(rfFaultFeedback.getFaultFeedbackId());
					feedback.setFeedbackStatus(ConstantsDb.rfFaultFeedbackFeedbackStatusProcessing());
					feedback.setModifier(userId);
					feedback.setLastHandler(userId);
					feedback.setModifyTime(date);
					if(ConfigConstantsDb.configFaultClientCreater().indexOf(rfFaultFeedback.getCreater())!=-1){
						feedback.setPromoter(userId);
					}
					RfFaultFeedbackMapper feedbackMapper = (RfFaultFeedbackMapper) this.getMapper();
					feedbackMapper.updateByPrimaryKeySelective(feedback);

					RfFaultHandle handle = new RfFaultHandle();
					handle.setAdminUserId(userId);
					handle.setFaultFeedbackId(rfFaultFeedback.getFaultFeedbackId());
					handle.setIsSolve(ConstantsDb.rfFaultFeedbackIsSolveNosolve());
					faultHandleService.save(request, handle);
					// 更新设备为故障状态
					if (StringUtils.isNotEmpty(rfFaultFeedback.getPadCode())) {
						RfPad pad = padService.getPadByPadCode(rfFaultFeedback.getPadCode());
						if (pad != null) {
							pad.setFaultStatus(ConstantsDb.rfPadFaultStatusFault());
							padService.update(request, pad);
						}
					}
				}
				
			}
		}
		return buffer.toString();
	}

	/**
	 * 处理故障
	 * @param request
	 * @param bean  故障反馈工单对象
	 * @throws Exception void
	 */
	public synchronized void fix(HttpServletRequest request, RfFaultFeedback bean) throws Exception {
		
		// 获取当前登录用户的名称
		String userId = SessionUtils.getCurrentUserId(request);
		Date date = new Date();
		// 获取所有的故障反馈id
		String[] idArr = bean.getIds().split(",");
		for (String id : idArr) {
			// 设置故障反馈id
			bean.setFaultFeedbackId(Integer.parseInt(id));
			// 根据故障id获取故障反馈对象
			RfFaultFeedback feedback = this.get(Integer.parseInt(id));
			// 获取故障处理状态
			String status = feedback.getFeedbackStatus();
			// 判断故障处理状态是否为:处理中
			if (!status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusProcessing())) {
				throw new BusinessException("设备[" + feedback.getPadCode() + "]当前故障状态为:　"+DictHelper.getLabel("rf_fault_feedback.feedback_status", status)+",不允许处理！");
			}
			// 判断最后处理人是否为当前用户
			if (!feedback.getLastHandler().equals(userId)) {
				throw new BusinessException("设备[" + feedback.getPadCode() + "]必须本人处理！");
			}
			feedback = new RfFaultFeedback();
			
			feedback.setReplyType(bean.getReplyType());

			RfFaultFeedbackMapper feedbackMapper = (RfFaultFeedbackMapper) this.getMapper();
			// 放弃处理(状态为: 移交测试,移交客服)
			if (bean.getFeedbackStatus().equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveceshi()) || bean.getFeedbackStatus().equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMovekefu())) {
				// 设置当前故障反馈id
				feedback.setFaultFeedbackId(bean.getFaultFeedbackId());
				// 设置故障反馈状态
				feedback.setFeedbackStatus(bean.getFeedbackStatus());
				// 设置修改人为当前用户
				feedback.setModifier(userId);
				// 设置修改时间
				feedback.setModifyTime(date);
				// 更新
				feedbackMapper.updateByPrimaryKeySelective(feedback);
				// 获取rf_fault_handle
				// 根据管理员id和故障反馈id降序查询故障反馈处理对象的集合
				List<RfFaultHandle> faultHandleList = faultHandleService.initQuery().andEqualTo("adminUserId", userId).andEqualTo("faultFeedbackId", bean.getFaultFeedbackId())
						.addOrderClause("createTime", "desc").singleStopTrue();
				if (faultHandleList.size() > 0) {
					RfFaultHandle faultHandle = faultHandleList.get(0);
					// 设置备注
					// 完结, 已处理, 移交测试, 移交客服, 移交运维, 新工单, 处理中
					faultHandle.setRemark(DictHelper.getLabel("rf_fault_feedback.feedback_status", bean.getFeedbackStatus()));
					faultHandleService.update(request, faultHandle);
				}
			}
			// 修复状态为:　已修复,移交运维
			if (bean.getFeedbackStatus().equals(ConstantsDb.rfFaultFeedbackFeedbackStatusHandle())||bean.getFeedbackStatus().equals(ConstantsDb.rfFaultFeedbackFeedbackStatusMoveyunwei())) {
				// 设置故障反馈id
				feedback.setFaultFeedbackId(bean.getFaultFeedbackId());
				// 设置故障状态
				feedback.setFeedbackStatus(bean.getFeedbackStatus());
				// 设置修改人为当前用户
				feedback.setModifier(userId);
				// 设置修改时间
				feedback.setModifyTime(date);
				// 设置故障处理状态
				feedback.setFeedbackHandle(bean.getFeedbackHandle());
				// 设置故障细节
				feedback.setSmallClassId(bean.getSmallClassId());
				feedbackMapper.updateByPrimaryKeySelective(feedback);
				// 获取rf_fault_handle
				List<RfFaultHandle> faultHandleList = faultHandleService.initQuery().andEqualTo("adminUserId", userId).andEqualTo("faultFeedbackId", bean.getFaultFeedbackId())
						.addOrderClause("createTime", "desc").singleStopTrue();
				if (faultHandleList.size() > 0) {
					RfFaultHandle faultHandle = faultHandleList.get(0);
					// 故障反馈状态为: 已处理
					if(bean.getFeedbackStatus().equals(ConstantsDb.rfFaultFeedbackFeedbackStatusHandle())){
						// 是否解决: 设置为解决
						faultHandle.setIsSolve(ConstantsDb.rfFaultFeedbackIsSolveSolve());
					}
					// 设置备注
					faultHandle.setRemark(bean.getFeedbackHandle());
					faultHandleService.update(request, faultHandle);
				}
			}
		}

	}

	/**
	 * 
	 * 处理完毕确认
	 * @param request
	 * @param bean 故障反馈工单对象
	 * @throws Exception
	 */
	public void reply(HttpServletRequest request, RfFaultFeedback bean) throws Exception {
		String[] ids = bean.getIds().split(",");
		// 获取当前用户
		String userId = SessionUtils.getCurrentUserId(request);
		Date date = new Date();
		RfFaultFeedback feed = null;
		for (String id : ids) {
			logger.info("reply service id={}",new Object[]{id});
			feed = this.selectByFaultFeedback(Integer.parseInt(id));
			// 根据id查询故障工单
			String status = feed.getFeedbackStatus();
			// 判断故障状态是否为'已处理'
			if (!status.equals(ConstantsDb.rfFaultFeedbackFeedbackStatusHandle())) {
				throw new BusinessException("设备[" + feed.getPadCode() + "]故障状态，不能进行此项操作！");
			}
//			if (!bean.getCreater().equals(userId) && ConfigConstantsDb.configFaultClientCreater().indexOf(bean.getCreater())==-1) {
//				throw new BusinessException("设备[" + bean.getPadCode() + "]非本人创建，不能进行此项操作！");
//			}
//			if (ConfigConstantsDb.configFaultClientCreater().indexOf(bean.getCreater())!=-1 && !bean.getPromoter().equals(userId)) {
//				throw new BusinessException("设备[" + bean.getPadCode() + "]故障非本人受理，不能进行此项操作！");
//			}
			RfFaultFeedback feedback = new RfFaultFeedback();
			feedback.setFaultFeedbackId(feed.getFaultFeedbackId());
			// 设置故障工单反馈状态为:　完结
			feedback.setFeedbackStatus(ConstantsDb.rfFaultFeedbackFeedbackStatusEnd());
			// 设置修改人
			feedback.setModifier(userId);
			// 设置咨询人
			feedback.setPromoter(userId);
			// 设置修改时间
			feedback.setModifyTime(date);
			// 设置完成时间
			feedback.setFinishTime(date);
			RfFaultFeedbackMapper feedbackMapper = (RfFaultFeedbackMapper) this.getMapper();
			feedbackMapper.updateByPrimaryKeySelective(feedback);

			/*String padCode = bean.getPadCode();
			int faultCount = this.initQuery().andEqualTo("padCode", padCode).andNotEqualTo("feedbackStatus", ConstantsDb.rfFaultFeedbackFeedbackStatusEnd()).countDelTrue();
			logger.info("reply faultCount={} padCode={}",new Object[]{faultCount,bean.getPadCode()});
			if (faultCount == 0) {
				// 获取设备&置故障状态为正常
				RfPad pad = padService.getPadByPadCode(bean.getPadCode());
				if (pad != null) {
					logger.info("reply padCode={}",new Object[]{pad.getPadCode()});
					pad.setFaultStatus(ConstantsDb.rfPadFaultStatusNomarl());
					padService.update(request, pad);
				}
			}*/
		}
	}

	@Transactional(readOnly = true)
	public void detail(HttpServletRequest request, Model model, RfFaultFeedback bean){
		// 故障处理日志
		List<RfFaultHandle> handleList=faultHandleService.queryByFaultFeedbackId(bean.getFaultFeedbackId());
		for(RfFaultHandle handle :handleList){
			handle.getMap().put("creater", adminService.get(handle.getCreater()).getAdminName());
			if(handle.getModifyTime()!=null){
				handle.getMap().put("modifier", adminService.get(handle.getModifier()).getAdminName());
			}
		}
		model.addAttribute("handleList", handleList);
		// 故障详细
		bean=this.get(bean.getFaultFeedbackId());
		bean.getMap().put("className", classService.getFullClass(ConstantsDb.rfClassClassTypeFault(), bean.getClassId(), " - "));
		bean.getMap().put("creater", adminService.get(bean.getCreater()).getAdminName());
		if(bean.getFinishTime()!=null){
			bean.getMap().put("modifier", adminService.get(bean.getModifier()).getAdminName());
		}
		if (bean.getSmallClassId() != null) {
			bean.getMap().put("fixName", classService.getFullClass(ConstantsDb.rfClassClassTypeFaultfix(), bean.getSmallClassId(), " - "));
		}
		model.addAttribute("bean", bean);
		//用户数据
		if(bean.getUserId()!=null){
			RfUser user = userService.get(bean.getUserId());
			model.addAttribute("member",user);
		}
		// 设备信息
		if(StringUtils.isNotEmpty(bean.getPadCode())){
			RfPad pad=padService.getPadByPadCode(bean.getPadCode());
			// 获取设备等级
			if (pad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
				RfUserPad userPad = userPadService.getUserPadByPadId(pad.getPadId());
				model.addAttribute("userPad", userPad);
			}
			model.addAttribute("pad", pad);
			// 历史故障
			if(bean.getBatchNumber()==null){
				bean.setBatchNumber(1);
			}
			List<RfFaultFeedback> feedbackList= this.initQuery().andEqualTo("padCode", bean.getPadCode()).andEqualTo("batchNumber", bean.getBatchNumber()).addOrderClause("createTime", "desc").findAll();
			for(RfFaultFeedback feedback:feedbackList){
				feedback.getMap().put("className", classService.getFullClass(ConstantsDb.rfClassClassTypeFault(), feedback.getClassId(), " - "));
			}
			model.addAttribute("feedbackList", feedbackList);
		}
	}

	public List<RfPadTask> screencap(HttpServletRequest request,HttpServletResponse response, String type, Integer padId) throws Exception {
		RfPadTask padTask=new RfPadTask();
		String commandType="";
		String url="";
		String task="./";
		String time=DateUtils.getDate("yyyyMMddHHmmss");
		RfPad pad=padService.get(padId);
		if(null==pad){
			throw new BusinessException("设备信息错误padId:"+padId);
		}
		String k=Md5Utils.MD5(pad.getPadCode()+time+"screen123!@#$%^");
		if(null!=pad.getDeviceId()&&null!=pad.getPadSn()){
			commandType="vm_screencap";
			RfDevice device= rfDeviceMapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfDevice.FD_DEVICE_IP), pad.getDeviceId());
			String deviceIp=device.getDeviceIp();
			task=task+commandType+" "+pad.getPadCode()+" "+deviceIp+" "+22;
			task=task+" "+time;
			task=task+"@"+pad.getPadSn();	
		} else {
			commandType="pad_screencap";	
			task=task+commandType+" "+pad.getPadCode()+" "+pad.getPadIp()+" "+22;
			task=task+" "+time;
		}
		RfIdc idc= idcService.get(pad.getIdcId());
		if(StringUtils.isNotEmpty(idc.getUserScreencapServer())){
			url=idc.getUserScreencapServer()+"?padCode="+pad.getPadCode()+"&d="+time+"&k="+k;
			padTask.setRemark(url);
			padTask.setPadId(pad.getPadId());
			padTask.setPadCode(pad.getPadCode());
			padTask.setTaskCommand(task);
			padTask.setCommandType(commandType);
			padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
			padTaskService.save(request, padTask);		
		}else{
			throw new BusinessException("截图获取地址参数异常");
		}
		
		List<RfPadTask>list= padTaskService.initQuery().andEqualTo("taskId", padTask.getTaskId()).singleAll();
			if (Collections3.isEmpty(list)) {
				return null;
			}
		return list;
	}
	
	/*public RfPadTask screencap(HttpServletRequest request,String type, Integer padId) throws Exception{
		String commandType="";
		String url="";
		String task="./";
		String time=DateUtils.getDate("yyyyMMddHHmmss");
	
		RfPad pad=padService.get(padId);
		if(null==pad){
			throw new BusinessException("设备信息错误padId:"+padId);
		}
		String k=Md5Utils.MD5(pad.getPadCode()+time+"screen123!@#$%^");
		if(null!=pad.getDeviceId()&&null!=pad.getPadSn()){
			commandType="vm_screencap";
			RfDevice device= rfDeviceMapper.selectByPrimaryKeyShowField(Lists.newArrayList(RfDevice.FD_DEVICE_IP), pad.getDeviceId());
			String deviceIp=device.getDeviceIp();
			task=task+commandType+" "+pad.getPadCode()+" "+deviceIp+" "+22;
			task=task+" "+time;
			task=task+"@"+pad.getPadSn();
			
		} else {
			commandType="pad_screencap";	
			task=task+commandType+" "+pad.getPadCode()+" "+pad.getPadIp()+" "+22;
			task=task+" "+time;
			
		}

		
		RfIdc idc= idcService.get(pad.getIdcId());
		if(StringUtils.isNotEmpty(idc.getUserScreencapServer())){
			url=idc.getUserScreencapServer()+"?padCode="+pad.getPadCode()+"&d="+time+"&k="+k;
			RfPadTask padTask=new RfPadTask();
			padTask.setRemark(url);
			padTask.setPadId(pad.getPadId());
			padTask.setPadCode(pad.getPadCode());
			padTask.setTaskCommand(task);
			padTask.setCommandType(commandType);
			padTask.setTaskStatus(ConstantsDb.rfPadTaskTaskStatusStart());
			padTaskService.save(request, padTask);		
	        return padTask;
		}else{
			throw new BusinessException("截图获取地址参数异常");
		}
		
	}*/
	
	public RfFaultFeedback selectByFaultFeedback(Integer faultFeedbackId){
		return mapper.selectByPrimaryKey(faultFeedbackId);
	}
}
