package com.redfinger.manager.modules.fault.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.domain.RfFaultFeedback;
import com.redfinger.manager.common.domain.RfGame;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadTaskBatch;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.FaultFeedOperateType;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.fault.service.FaultFeedbackService;
import com.redfinger.manager.modules.fault.service.FaultHandleService;
import com.redfinger.manager.modules.fault.service.pojo.RfFaultFeedbackCustom;
import com.redfinger.manager.modules.game.service.GameService;
import com.redfinger.manager.modules.log.service.UserPadLogService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.tasks.jms.ProducterMessageSender;
import com.redfinger.manager.modules.tasks.service.TaskBatchService;

@Controller
@RequestMapping(value = "/fault/newly")
public class FaultNewlyController extends BaseController {

	@Autowired
	FaultFeedbackService service;
	@Autowired
	ClassService classService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	UserService userService;
	@Autowired
	PadService padService;
	@Autowired
	IdcService idcService;
	@Autowired
	UserPadLogService userPadLogService;
	@Autowired
	FaultHandleService handleService;
	@Autowired
	AdminService adminService;
	@Autowired
	TaskBatchService taskBatchService;
	@Autowired
	GameService gameService;
	@Autowired
	ProducterMessageSender pro;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 故障类型
		model.addAttribute("categoryTree", classService.getStopTrueTreeAndTop(ConstantsDb.rfClassClassTypeFault()));
		return this.toPage(request, response, model);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfFaultFeedback> list(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		List<Integer> classIdList = (List<Integer>)classService.getChildAndSelfIds(ConstantsDb.rfClassClassTypeFault(), bean.getClassId());;
		String userId = SessionUtils.getCurrentUserId(request);
		List<RfFaultFeedback> list = service.initQuery(bean)
				.andIn("classId", classIdList)
				.andEqualTo("feedbackStatus", bean.getFeedbackStatus())
				.andEqualTo("creater", userId)
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageStopTrue(bean.getPage(), bean.getRows());
		for (RfFaultFeedback feedback : list) {
			feedback.getMap().put("className", classService.getFullClass(ConstantsDb.rfClassClassTypeFault(), feedback.getClassId(), " - "));
		}
		PageInfo<RfFaultFeedback> pageInfo = new PageInfo<RfFaultFeedback>(list);
		return pageInfo;
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		model.addAttribute("bean", bean);
		// 添加故障类型tree到请求域中
		model.addAttribute("categoryTree", classService.getStopTrueTree(ConstantsDb.rfClassClassTypeFault()));
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "getPad")
	public String getPad(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		model.addAttribute("padCode", bean.getPadCode());

		// 添加故障类型tree到请求域中
		model.addAttribute("categoryTree", classService.getStopTrueTree(ConstantsDb.rfClassClassTypeFault()));
		
		
		RfPad pad = padService.getPadByPadCode(bean.getPadCode());
		if(pad!=null){
			pad.getMap().put("idc", idcService.get(pad.getIdcId()));
			
			// 设置设备类型
			pad.getMap().put("classifyName", DictHelper.getLabel("rf_pad.pad_classify", pad.getPadClassify()));
			// 已绑定
			if (pad.getBindStatus().equals(ConstantsDb.rfPadBindStatusBind())) {
				// 获取用户信息
				List<RfUserPad> userPadList = userPadService.initQuery().andEqualTo("padId", pad.getPadId()).singleStopTrue();
				if (userPadList.size() > 0) {
					RfUserPad userPad = userPadList.get(0);
					model.addAttribute("userPad", userPad);
					RfUser user = userService.get(userPad.getUserId());
					model.addAttribute("user", user);
					// 获取绑定总数量
					user.getMap().put("bindAllCount", userPadLogService.initQuery().andEqualTo("userId", user.getUserId()).andIsNotNull("newPadId").countAll());
					// 获取用户已经绑定数量
					user.getMap().put("bindCount", userPadService.initQuery().andEqualTo("userId", user.getUserId()).countStopTrue());
				}
				//获取设备等级
				RfUserPad userPad=userPadService.getUserPadByPadId(pad.getPadId());
				if(userPad!=null){
					pad.getMap().put("padGrade", userPad.getPadGrade());
				}
			}
			// 获取设备故障记录
			List<RfFaultFeedback> feedbackList = service.initQuery()
					.andEqualTo("batchNumber",pad.getBatchNumber()==null?1:pad.getBatchNumber())
					.andEqualTo("padCode", pad.getPadCode())
					.addOrderClause("createTime", "desc").findAll();
			for (RfFaultFeedback feedback : feedbackList) {
				feedback.getMap().put("className", classService.getFullClass(ConstantsDb.rfClassClassTypeFault(), feedback.getClassId(), " - "));
				if(feedback.getSmallClassId()!=null){
					feedback.getMap().put("fixName", classService.getFullClass(ConstantsDb.rfClassClassTypeFaultfix(), feedback.getSmallClassId(), " - "));
					if(StringUtils.isNotBlank(feedback.getCreater())){
						SysAdmin createAdmin=adminService.get(feedback.getPromoter());
						feedback.getMap().put("promoter", createAdmin == null ? feedback.getPromoter():createAdmin.getAdminName());
					}
				}
				if(null != feedback.getMap().get("className")){
					feedback.getMap().put("title",StringUtils.abbr(feedback.getMap().get("className").toString(), 60));
				}else{
					log.info("设备编码为："+pad.getPadCode()+"的故障类型为空");
				}
			}
			model.addAttribute("feedbackList", feedbackList);
			model.addAttribute("pad", pad);
		}
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		bean.setClientSource(ClientType.PC);
		bean.setOperateType(FaultFeedOperateType.NEW_ADD);
		service.save(request, bean);
	}
	
	@RequestMapping(value = "saveAndAccept")
	@ResponseBody
	public Object saveAndAccept(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedbackCustom bean) throws Exception {
		bean.setClientSource(ClientType.PC);
		bean.setOperateType(FaultFeedOperateType.NEW_ADD);
		
		if(bean.getClassId() == null ){
			throw new BusinessException("故障类型不能为空!");
		}
		
		service.save(request, bean);
		// 获得自动受理的标记(1为自动受理)
		String isAccept = bean.getIsAccept();
		
		if(StringUtils.isNotBlank(isAccept) && isAccept.equals("1")){
			// 设置当前新建的工单id
			bean.setIds(bean.getFaultFeedbackId() + "");
			// 受理故障工单
			service.accept(request, bean);
		}
		
		Map<String, Object> resultInfo = Maps.newHashMap();
		// 设置响应编码为200
		resultInfo.put("code", 200);
		// 添加message信息
		resultInfo.put("message", "添加成功");
		// 返回故障反馈工单id
		resultInfo.put("sysDate", bean.getFaultFeedbackId());

		return resultInfo;
		
	}
	
	@RequestMapping(value = "pad_screencap")
	public void pad_screencap(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCode) throws Exception {
		this.save(request, response, model, bean, gameId, padCode);
	}

	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfPadTaskBatch bean,String gameId,String padCode) throws Exception {
		if(gameId!=null){
		if(!gameId.equals("com.redfinger.gamemanage")){
			RfGame game=gameService.get(Integer.parseInt(gameId));
			if(game==null){
				throw new BusinessException("错误的游戏名");
			}

			bean.setGameName(game.getGameName());
			bean.setPackageName(game.getGamePackageName());
				} else {
					bean.setGameName("启动器");
					bean.setPackageName("com.redfinger.gamemanage");
				}		
		}
		if(padCode==null){
			throw new BusinessException("请输入设备CODE");
		}
		bean.setName(padCode);
		bean.setRemark("vm_screencap");
		taskBatchService.saveBatch(request, bean,padCode);
		pro.send(bean.getId().toString());
	}
}
