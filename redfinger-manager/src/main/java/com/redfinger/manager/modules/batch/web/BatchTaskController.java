package com.redfinger.manager.modules.batch.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.constants.BatchDataCode;
import com.redfinger.manager.common.constants.BatchOperateStatus;
import com.redfinger.manager.common.constants.BatchOperateType;
import com.redfinger.manager.common.constants.CommonPadConfigCode;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.EnablePadType;
import com.redfinger.manager.common.constants.GiveRbcType;
import com.redfinger.manager.common.constants.PadClassify;
import com.redfinger.manager.common.constants.SendPadMsgType;
import com.redfinger.manager.common.constants.SendPadNoticeType;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfMsgTemplate;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.domain.TkBatchData;
import com.redfinger.manager.common.domain.TkBatchTask;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.jsm.TkBatchTaskProducer;
import com.redfinger.manager.common.utils.DownloadUtil;
import com.redfinger.manager.common.utils.FileUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.batch.service.BatchDataService;
import com.redfinger.manager.modules.batch.service.BatchTaskService;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.fault.service.FaultMsgTemplateService;
import com.redfinger.manager.modules.goods.service.GoodsService;

@Controller
@RequestMapping(value="/tkbatch/batch")
public class BatchTaskController extends BaseController {

	@Autowired
	private BatchTaskService service;
	@Autowired
	private FileUtils fileUtil;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private TkBatchTaskProducer tkBatchTaskProducer;
	@Autowired
	private IdcService idcService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private FaultMsgTemplateService faultMsgTemplateService;
	@Autowired
	private BatchDataService dataService;
	@Autowired
	private ControlService controlService;
	@Autowired
	private ConfigService configService;
	/**
	 * SpringMvc参数日期绑定
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<TkBatchTask> list(HttpServletRequest request, HttpServletResponse response, Model model, TkBatchTask bean) throws Exception {
		List<TkBatchTask> list = service.initQuery(bean)
				.andLike("batchTitle", bean.getBatchTitle())
				.andEqualTo("operateType", bean.getOperateType())
				.andEqualTo("operateStatus", bean.getOperateStatus())
				.andEqualTo("enableStatus", bean.getEnableStatus())  // 根据启用状态查询
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		
		PageInfo<TkBatchTask> pageInfo = new PageInfo<TkBatchTask>(list);
		return pageInfo;
	}
	
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, TkBatchTask bean) throws Exception {

		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		model.addAttribute("controlList", controlList);//控制节点
		model.addAttribute("controlListJson",JsonUtils.ObjectToString(controlList));//控制节点
		
		//修改
		if(bean.getBatchId() != null){
			bean = service.get(bean.getBatchId());
			if(!StringUtils.equals(bean.getOperateStatus(), BatchOperateStatus.WAIT_START)){
				throw new BusinessException("此批处理任务已经被启用不能再修改！");
			}
			
			model.addAttribute("bean", bean);
			Integer batchId = bean.getBatchId();
			String operateType = bean.getOperateType();
			if(operateType.equals(BatchOperateType.START_MAINT) || operateType.equals(BatchOperateType.STOP_MAINT)){
				//批处理——设备维护、取消维护
				getFileValue(model, bean, batchId,BatchDataCode.PAD_MAINT_FILE_PATH);
				
			}else if(operateType.equals(BatchOperateType.RENEWAL_PAD)){
				// 批处理——更换设备
				getFileValue(model, bean, batchId,BatchDataCode.RENEWAL_PAD_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_RENEWPADIDCID,"reNewPadIdcId");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_PADCONTROLID,"padControlId");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_PADCODEGT,"padCodeGt");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_PADCODELT,"padCodeLt");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_DEVICEIPGT,"deviceIpGt");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_DEVICEIPLT,"deviceIpLt");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_IS_MESSAGE,"isSendMessage");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_MESSAGE_TEMPLATE,"sendMessageTemplate");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_IS_WECHART,"isSendWechart");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_WECHART_TEMPLATE,"sendWechartTemplate");
				
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId,BatchDataCode.RENEWAL_PAD_RENEWPADIDCID);
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					String idcId = batchData.getDataValue();
					List<RfControl> padControls = new ArrayList<>();
					for (RfControl control : controlList) {
						if(idcId.equals(control.getIdcId().toString())){
							padControls.add(control);
						}
					}
					model.addAttribute("padControls", padControls);
				}
				
			}else if (operateType.equals(BatchOperateType.USER_NOTICE)) {
				//发送用户公告
				getFileValue(model, bean, batchId,BatchDataCode.USER_NOTICE_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.USER_NOTICE_TITLE,"userNoticeTitle");
				getShowValue(model, batchId,BatchDataCode.USER_NOTICE_CONTENT,"userNoticeContent");
				getShowValue(model, batchId,BatchDataCode.USER_NOTICE_POPSTATUS,"noticePopstatus");
				getShowValue(model, batchId,BatchDataCode.USER_NOTICE_POPEXPIRED,"noticePopexpired");
				
			}else if (operateType.equals(BatchOperateType.PAD_NOTICE)) {
				//发送设备公告
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, BatchDataCode.PAD_NOTICE_SEND_TYPE);//赠送方式
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					String sendPasNoticeType = batchData.getDataValue();
					model.addAttribute("sendPasNoticeType", sendPasNoticeType);
					if(sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_IDC)){
						getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_IDC_ID,"padNoticeIdcId");//按机房赠送
						
					}else if (sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE)){
						getFileValue(model, bean, batchId,BatchDataCode.PAD_NOTICE_FILE_PATH);//按设备编码赠送，有excel文件
						
					}else if(sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE_BETWEEN)){
						getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_PADCODEGT,"padNoticePadCodeGt");
						getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_PADCODELT,"padNoticePadCodeLt");
					}
				}
				getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_TITLE,"padNoticeTitle");
				getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_CONTENT,"padNoticeContent");
				getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_POPSTATUS,"noticePopstatus");
				getShowValue(model, batchId,BatchDataCode.ONE_NOTICE_STATUS,"oneNoticeStatus");
				
			}else if (operateType.equals(BatchOperateType.USER_RBC)) {
				//按用户赠送红豆
				getFileValue(model, bean, batchId,BatchDataCode.RBGET_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.RBGET_AMOUNT,"rbcGet");
				getShowValue(model, batchId,BatchDataCode.RBGET_REMARK,"rbcGetRemark");
				getShowValue(model, batchId,BatchDataCode.RBGET_TYPE,"userRbcType");
				
			}else if (operateType.equals(BatchOperateType.PAD_RBC)){
				//按设备赠送红豆
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, BatchDataCode.RBC_BY_PAD_GIVETYPE);//赠送方式
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					String giveRbcType = batchData.getDataValue();
					model.addAttribute("giveRbcType", giveRbcType);
					if(GiveRbcType.GIVE_RBC_BY_IDC.equals(giveRbcType)){
						getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_IDCID,"idcId");//按机房赠送
						
					}else if (GiveRbcType.GIVE_RBC_BY_PADECODE.equals(giveRbcType)){
						getFileValue(model, bean, batchId,BatchDataCode.RBC_BY_PAD_FILE_PATH);//按设备编码赠送，有excel文件
						
					}else if(GiveRbcType.GIVE_RBC_BY_PADECODE_BETWEEN.equals(giveRbcType)){
						getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_PADECODEGT,"padRbcPadCodeGt");
						getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_PADECODELT,"padRbcPadCodeLt");
					}
					getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_COMMOMRBC,"commonRbc");
					getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_VIPRBC,"vipRbc");
					getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_SVIPRBC,"svipRbc");
					getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_GVIPRBC,"gvipRbc");
				}
				
			}else if (operateType.equals(BatchOperateType.PAD_TIME)){
				//赠送设备时间
				getFileValue(model, bean, batchId,BatchDataCode.PAD_TIME_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.PAD_TIME_TYPE,"timeType");
				getShowValue(model, batchId,BatchDataCode.PAD_TIME_AMOUNT,"controltime");
				
			}else if (operateType.equals(BatchOperateType.PAD_BIND)){
				//绑定设备
				getFileValue(model, bean, batchId,BatchDataCode.BIND_PAD_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.BIND_PAD_GOODS,"goodsId");
				getShowValue(model, batchId,BatchDataCode.BIND_PAD_PADCLASSIFY,"padClassify");
				getShowValue(model, batchId,BatchDataCode.BIND_GAMEPAD_TIME,"gamePadTimeValue");
				getShowValue(model, batchId,BatchDataCode.BIND_CLOUDPAD_TIME,"cloudPadTimeValue");
				getShowValue(model, batchId,BatchDataCode.BIND_PAD_GVIPGOODS,"gvipGoodsId");
				
			}else if (operateType.equals(BatchOperateType.VM_REBOOT)){
				//虚拟设备重启
				getFileValue(model, bean, batchId,BatchDataCode.VM_PAD_RESTART_FILE_PATH);
				
			}else if (operateType.equals(BatchOperateType.DEVICE_REBOOT)){
				//物理设备重启
				getFileValue(model, bean, batchId,BatchDataCode.DEVICE_REBOOT_FILE_PATH);
				
			}else if (operateType.equals(BatchOperateType.VM_GRANT_OPEN) || operateType.equals(BatchOperateType.VM_GRANT_CANCEL)){
				//批处理——虚拟设备授权开放与取消
				getFileValue(model, bean, batchId,BatchDataCode.VM_GRANT_FILE_PATH);
				
			}else if (operateType.equals(BatchOperateType.ENABLE_PAD_YES) || operateType.equals(BatchOperateType.ENABLE_PAD_NO)){
				//设备启用或禁用
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, BatchDataCode.ENABLE_PAD_TYPE);//启用或禁用方式
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					String enablePadType = batchData.getDataValue();
					model.addAttribute("enablePadType", enablePadType);
					if(EnablePadType.ENABLE_PAD_BY_FILE.equals(enablePadType)){
						getFileValue(model, bean, batchId,BatchDataCode.ENABLE_PAD_FILE_PATH);//按设备编码，有excel文件
					}else if (EnablePadType.ENABLE_PAD_BY_SELECTION.equals(enablePadType)){
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_IDC_ID,"enablePadIdcId");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_CONTROLL_ID,"enablePadControlId");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_STATUS,"enablePadStatus");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_BIND_STATUS,"enablePadBindStatus");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_FAULT_STATUS,"enablePadFaultStatus");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_PADCODEGT,"enablePadCodeGt");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_PADCODELT,"enablePadCodeLt");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_DEVICEIPGT,"enablePadDeviceIpGt");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_DEVICEIPLT,"enablePadDeviceIpLt");
						
						TkBatchData data = dataService.selectByBatchIdAndDataCode(batchId,BatchDataCode.ENABLE_PAD_IDC_ID);
						if(null != data && !StringUtils.isBlank(data.getDataValue())){
							String idcId = data.getDataValue();
							List<RfControl> padControls = new ArrayList<>();
							for (RfControl control : controlList) {
								if(idcId.equals(control.getIdcId().toString())){
									padControls.add(control);
								}
							}
							model.addAttribute("enablePadControls", padControls);
						}
					}
				}
				
			}else if (operateType.equals(BatchOperateType.PAD_MSG)) {
				//发送设备消息
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, BatchDataCode.PAD_MSG_SEND_TYPE);//发送方式
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					String sendPadMsgType = batchData.getDataValue();
					model.addAttribute("sendPadMsgType", sendPadMsgType);
					if(sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_IDC)){
						getShowValue(model, batchId,BatchDataCode.PAD_MSG_IDC_ID,"padMsgIdcId");//按机房赠送
						
					}else if (sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE)){
						getFileValue(model, bean, batchId,BatchDataCode.PAD_MSG_FILE_PATH);//按设备编码赠送，有excel文件
						
					}else if(sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE_BETWEEN)){
						getShowValue(model, batchId,BatchDataCode.PAD_MSG_PADCODEGT,"padMsgPadCodeGt");
						getShowValue(model, batchId,BatchDataCode.PAD_MSG_PADCODELT,"padMsgPadCodeLt");
					}
				}
				getShowValue(model, batchId,BatchDataCode.PAD_MSG_TITLE,"padMsgTitle");
				getShowValue(model, batchId,BatchDataCode.PAD_MSG_CONTENT,"padMsgContent");
				getShowValue(model, batchId,BatchDataCode.ONE_MSG_STATUS,"oneMsgStatus");
				getShowValue(model, batchId,BatchDataCode.PAD_WEIXIN_CONTENT,"padWeixinContent");
				
			}
		}
		
		List<RfIdc> idcList = idcService.initQuery(new RfIdc()).findDelTrue();//获取机房列表,按机房赠送红豆
		model.addAttribute("idcList", idcList);
		
		List<RfGoods> goods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsVip()).addOrderClause("goodsPrice", "asc").findStopTrue();//获取商品列表，批量绑定设备
		model.addAttribute("goods", goods);
		List<RfGoods> gvipGoods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsGvip()).addOrderClause("goodsPrice", "asc").findStopTrue();//获取商品列表，批量绑定设备
		model.addAttribute("gvipGoods", gvipGoods);
		
		List<RfMsgTemplate> msgTemplates = faultMsgTemplateService.initQuery().andEqualTo("templateType", "1").addOrderClause("createTime", "desc").findStopTrue();//消息模板
		List<RfMsgTemplate> weixinTemplates = faultMsgTemplateService.initQuery().andEqualTo("templateType", "2").addOrderClause("createTime", "desc").findStopTrue();//微信消息模板
		List<RfMsgTemplate> padNoticeTemplates = faultMsgTemplateService.initQuery().andEqualTo("templateType", "3").findStopTrue();//设备公告模板
		List<RfMsgTemplate> padMsgTemplates = faultMsgTemplateService.initQuery().andEqualTo("templateType", "4").findStopTrue();//设备公告模板
		List<RfMsgTemplate> padWeixinTemplates = faultMsgTemplateService.initQuery().andEqualTo("templateType", "5").findStopTrue();//设备公告模板
		model.addAttribute("msgTemplates", msgTemplates);
		model.addAttribute("weixinTemplates", weixinTemplates);
		model.addAttribute("padNoticeTemplates", padNoticeTemplates);
		model.addAttribute("padMsgTemplates", padMsgTemplates);
		model.addAttribute("padWeixinTemplates", padWeixinTemplates);
		
		SysConfig gamePadTime = configService.selectByConfingCode(CommonPadConfigCode.CONFIG_GAMEPAD_ONLINE_TIME);//游戏设备申请后在线时长
		model.addAttribute("gamePadTime", gamePadTime == null ? null : gamePadTime.getConfigValue());
		return this.toPage(request, response, model);
	}

	private void getFileValue(Model model, TkBatchTask bean, Integer batchId,String batchDataCode) {
		TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, batchDataCode);
		if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
			String filePath = filePathUtils.getFileLinkUrl() + batchData.getDataValue();
			model.addAttribute("filePath", filePath);
			model.addAttribute("excelPath", batchData.getDataValue());
			model.addAttribute("fileName", bean.getBatchTitle());
		}
	}

	private void getShowValue(Model model, Integer batchId,String batchDataCode,String showName) {
		TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId,batchDataCode);
		if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
			model.addAttribute(showName, batchData.getDataValue());
		}
	}
	
	/**
	 * 新增或修改批处理任务
	 * @throws Exception
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public void saveOrUpdate(@RequestParam(value = "fileUpdate", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response,
			Model model, TkBatchTask bean, String excelPath,
			String userNoticeTitle,String userNoticeContent,String popStatus,Date popExpiredTime,
			String padNoticeTitle,String padNoticeContent,String sendPasNoticeType,String padNoticeIdcId,String padNoticePadCodeGt,String padNoticePadCodeLt,String oneNoticeStatus,
			String sendPadMsgType,String padMsgIdcId,String padMsgPadCodeGt,String padMsgPadCodeLt,String padMsgTitle,String padMsgContent,String oneMsgStatus,String padWeixinContent,
			String reNewPadIdcId,String padControlId,String padCodeGt,String padCodeLt,String deviceIpGt,String deviceIpLt,String isSendMessage,String isSendWechart, String sendMessageTemplate, String sendWechartTemplate, 
			String timeType,Integer controltime,
			String userRbcType,Integer rbcGet,
			String giveRbcType,String idcId,String padRbcPadCodeGt,String padRbcPadCodeLt,Integer commonRbc,Integer vipRbc,Integer svipRbc,Integer gvipRbc,
			Integer goodsId,String padClassify,String gamePadTime,Integer gvipGoodsId,String cloudPadTime,
			String enablePadType,String enablePadIdcId,String enablePadCodeGt,String enablePadCodeLt,String enablePadDeviceIpGt,String enablePadDeviceIpLt,
			String enablePadControlId,String enablePadStatus,String enablePadBindStatus,String enablePadFaultStatus) throws Exception {
		
		if(null == bean || StringUtils.isBlank(bean.getOperateType())){
			throw new BusinessException("操作类型不能为空！");
		}
		
		TkBatchTask batchTask = new TkBatchTask();
		String operateType = bean.getOperateType();
		if(null != bean.getBatchId()){
			batchTask = service.get(bean.getBatchId());
			if(null == batchTask){
				throw new BusinessException("修改的 记录不存在！");
			}
			if(!operateType.equals(batchTask.getOperateType())){
				throw new BusinessException("操作类型不能修改！");
			}
		}
		
		bean.setOperateStatus(BatchOperateStatus.WAIT_START);//待启用
		String dataFilePath = null;
		if(operateType.equals(BatchOperateType.PAD_RBC)){
			//批处理——按设备赠送红豆
			if(StringUtils.isBlank(giveRbcType) || (!giveRbcType.equals(GiveRbcType.GIVE_RBC_BY_IDC) && 
							!giveRbcType.equals(GiveRbcType.GIVE_RBC_BY_PADECODE_BETWEEN) && 
							!giveRbcType.equals(GiveRbcType.GIVE_RBC_BY_PADECODE))){
				throw new BusinessException("赠送方式必选或赠送方式有误");
			}
			
			//按机房赠送红豆时,不用上传文件
			if(GiveRbcType.GIVE_RBC_BY_IDC.equals(giveRbcType)){
				if(StringUtils.isBlank(idcId)){
					throw new BusinessException("请选择机房");
				}
			}
			
			//按机房设备编码段赠送，不用上传文件
			if(GiveRbcType.GIVE_RBC_BY_PADECODE_BETWEEN.equals(giveRbcType)){
				if(StringUtils.isBlank(padRbcPadCodeGt) && StringUtils.isBlank(padRbcPadCodeLt)){
					throw new BusinessException("编码段请至少填写一项");
				}
				
				padRbcPadCodeGt = StringUtils.isBlank(padRbcPadCodeGt) ? null : padRbcPadCodeGt.trim();
				padRbcPadCodeLt = StringUtils.isBlank(padRbcPadCodeLt) ? null : padRbcPadCodeLt.trim();
				
				if(null != padRbcPadCodeGt){
					if(padRbcPadCodeGt.length() < 2 || !"VM".equals(padRbcPadCodeGt.substring(0, 2))){
						throw new BusinessException("设备编码段输入有误，必须以VM开头");
					}
				}
				
				if(null != padRbcPadCodeLt){
					if(padRbcPadCodeLt.length() < 2 || !"VM".equals(padRbcPadCodeLt.substring(0, 2))){
						throw new BusinessException("设备编码段输入有误，必须以VM开头");
					}
				}
			}
			
			if(null == commonRbc && null == vipRbc && null ==svipRbc){
				throw new BusinessException("请输入要赠送的红豆数量");
			}
			commonRbc = commonRbc != null ? commonRbc : 0;
			vipRbc = vipRbc != null ? vipRbc : 0;
			svipRbc = svipRbc != null ? svipRbc : 0;
			gvipRbc = gvipRbc != null ? gvipRbc : 0;
			
			if(0 == commonRbc.intValue() && 0 == vipRbc.intValue() && 0 == svipRbc.intValue() && 0 == gvipRbc.intValue()){
				throw new BusinessException("请至少输入一项红豆赠送数量不为0");
			}
			
			if(GiveRbcType.GIVE_RBC_BY_PADECODE.equals(giveRbcType)){
				if(file.isEmpty() && StringUtils.isBlank(excelPath)){
					throw new BusinessException("上传文件不能为空");
				}
				
				dataFilePath = excelPath;
				if(!file.isEmpty()){
					dataFilePath = uploadFileRequest(file);//新增上传excel文件或修改时重新上传excel文件
					File oldFile = new File(excelPath);//旧文件要删除
					if(oldFile.exists()){
						oldFile.delete();
					}
				}
			}
			
			service.saveRbcByPadBatchTask(request, bean, dataFilePath, giveRbcType, idcId, padRbcPadCodeGt,padRbcPadCodeLt,commonRbc, vipRbc, svipRbc, gvipRbc);
			return;
			
		}else if(operateType.equals(BatchOperateType.PAD_NOTICE)){
			//批处理——发送设备公告
			if(StringUtils.isBlank(sendPasNoticeType) || (!sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_IDC) && 
							!sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE) && 
							!sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE_BETWEEN))){
				throw new BusinessException("发送设备公告方式有误！");
			}
			
			//按机房发送时,不用上传文件
			if(sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_IDC)){
				if(StringUtils.isBlank(padNoticeIdcId)){
					throw new BusinessException("请选择机房");
				}
			}
			
			//按机房设备编码段发送，不用上传文件
			if(sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE_BETWEEN)){
				if(StringUtils.isBlank(padNoticePadCodeGt) && StringUtils.isBlank(padNoticePadCodeLt)){
					throw new BusinessException("编码段请至少填写一项");
				}
				
				padNoticePadCodeGt = StringUtils.isBlank(padNoticePadCodeGt) ? null : padNoticePadCodeGt.trim();
				padNoticePadCodeLt = StringUtils.isBlank(padNoticePadCodeLt) ? null : padNoticePadCodeLt.trim();
				
				if(null != padNoticePadCodeGt){
					if(padNoticePadCodeGt.length() < 2 || !"VM".equals(padNoticePadCodeGt.substring(0, 2))){
						throw new BusinessException("设备编码段输入有误，必须以VM开头");
					}
				}
				
				if(null != padNoticePadCodeLt){
					if(padNoticePadCodeLt.length() < 2 || !"VM".equals(padNoticePadCodeLt.substring(0, 2))){
						throw new BusinessException("设备编码段输入有误，必须以VM开头");
					}
				}
				
			}
			
			if(StringUtils.isBlank(padNoticeTitle) || StringUtils.isBlank(padNoticeContent)){
				throw new BusinessException("公告内容和公告标题不能为空！");
			}
			
			if (padNoticeContent.length() > 680) {
				throw new BusinessException("公告内容字数不能超过680 ！");
			}
			
			if(StringUtils.isBlank(oneNoticeStatus) || (!YesOrNoStatus.YES.equals(oneNoticeStatus) && !YesOrNoStatus.NO.equals(oneNoticeStatus))){
				throw new BusinessException("请选择一个用户是否只发送一条！");
			}
			
			//excel编码需上传文件
			if(sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE)){
				if(file.isEmpty() && StringUtils.isBlank(excelPath)){
					throw new BusinessException("上传文件不能为空");
				}
				
				dataFilePath = excelPath;
				if(!file.isEmpty()){
					dataFilePath = uploadFileRequest(file);//新增上传excel文件或修改时重新上传excel文件
					File oldFile = new File(excelPath);//旧文件要删除
					if(oldFile.exists()){
						oldFile.delete();
					}
				}
			}
			
			service.savePadNoticeBatchTask(request, bean, dataFilePath,padNoticeTitle,padNoticeContent,popStatus,sendPasNoticeType,padNoticeIdcId,padNoticePadCodeGt,padNoticePadCodeLt,oneNoticeStatus);
			return;
			
		}else if (operateType.equals(BatchOperateType.ENABLE_PAD_YES) || operateType.equals(BatchOperateType.ENABLE_PAD_NO)) {
			//启用或禁用设备
			//判断启用或禁用方式
			if(StringUtils.isBlank(enablePadType) || (!enablePadType.equals(EnablePadType.ENABLE_PAD_BY_FILE) && 
					!enablePadType.equals(EnablePadType.ENABLE_PAD_BY_SELECTION) && !enablePadType.equals(EnablePadType.ENABLE_PAD_BY_ALL))){
				throw new BusinessException("启用或禁用方式有误方式有误！");
			}
			
			//excel编码需上传文件
			if(enablePadType.equals(EnablePadType.ENABLE_PAD_BY_FILE)){
				if(file.isEmpty() && StringUtils.isBlank(excelPath)){
					throw new BusinessException("上传文件不能为空");
				}
				
				dataFilePath = excelPath;
				if(!file.isEmpty()){
					dataFilePath = uploadFileRequest(file);//新增上传excel文件或修改时重新上传excel文件
					File oldFile = new File(excelPath);//旧文件要删除
					if(oldFile.exists()){
						oldFile.delete();
					}
				}
			} else if (enablePadType.equals(EnablePadType.ENABLE_PAD_BY_SELECTION)){
				//按条件
				if(StringUtils.isBlank(enablePadIdcId)&&StringUtils.isBlank(enablePadControlId)
						&&StringUtils.isBlank(enablePadStatus)&&StringUtils.isBlank(enablePadBindStatus)&&StringUtils.isBlank(enablePadFaultStatus)
						&&StringUtils.isBlank(enablePadCodeGt)&&StringUtils.isBlank(enablePadCodeLt)&&StringUtils.isBlank(enablePadDeviceIpGt)
						&&StringUtils.isBlank(enablePadDeviceIpLt)){
					throw new BusinessException("请至少选择一个条件");
				}
				
				if(StringUtils.isNotBlank(enablePadCodeGt)){
					if(enablePadCodeGt.length() < 2 || !"VM".equals(enablePadCodeGt.substring(0, 2))){
						throw new BusinessException("设备编码段输入有误，必须以VM开头");
					}
				}
				
				if(StringUtils.isNotBlank(enablePadCodeLt)){
					if(enablePadCodeLt.length() < 2 || !"VM".equals(enablePadCodeLt.substring(0, 2))){
						throw new BusinessException("设备编码段输入有误，必须以VM开头");
					}
				}
				
				String regexStr = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
				if(StringUtils.isNotBlank(enablePadDeviceIpGt)){
					if(!enablePadDeviceIpGt.matches(regexStr)){
						throw new BusinessException("ip地址输入有误");
					}
				}
				if(StringUtils.isNotBlank(enablePadDeviceIpLt)){
					if(!enablePadDeviceIpLt.matches(regexStr)){
						throw new BusinessException("ip地址输入有误");
					}
				}
			}
			
			service.saveEnablePadTask(request, bean,dataFilePath,enablePadType,enablePadIdcId,enablePadControlId,enablePadStatus,enablePadBindStatus,
					enablePadFaultStatus,enablePadCodeGt,enablePadCodeLt,enablePadDeviceIpGt,
					enablePadDeviceIpLt);
			
		}else if(operateType.equals(BatchOperateType.PAD_MSG)){
			//批处理——发送设备消息
			if(StringUtils.isBlank(sendPadMsgType) || (!sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_IDC) && 
							!sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE) && 
							!sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE_BETWEEN))){
				throw new BusinessException("发送设备消息方式有误！");
			}
			
			//按机房发送时,不用上传文件
			if(sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_IDC)){
				if(StringUtils.isBlank(padMsgIdcId)){
					throw new BusinessException("请选择机房");
				}
			}
			
			//按机房设备编码段发送，不用上传文件
			if(sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE_BETWEEN)){
				if(StringUtils.isBlank(padMsgPadCodeGt) && StringUtils.isBlank(padMsgPadCodeLt)){
					throw new BusinessException("编码段请至少填写一项");
				}
				
				padMsgPadCodeGt = StringUtils.isBlank(padMsgPadCodeGt) ? null : padMsgPadCodeGt.trim();
				padMsgPadCodeLt = StringUtils.isBlank(padMsgPadCodeLt) ? null : padMsgPadCodeLt.trim();
				
				if(null != padMsgPadCodeGt){
					if(padMsgPadCodeGt.length() < 2 || !"VM".equals(padMsgPadCodeGt.substring(0, 2))){
						throw new BusinessException("设备编码段输入有误，必须以VM开头");
					}
				}
				
				if(null != padMsgPadCodeLt){
					if(padMsgPadCodeLt.length() < 2 || !"VM".equals(padMsgPadCodeLt.substring(0, 2))){
						throw new BusinessException("设备编码段输入有误，必须以VM开头");
					}
				}
				
			}
			
			if(StringUtils.isBlank(padMsgTitle) || StringUtils.isBlank(padMsgContent)){
				throw new BusinessException("通知内容和通知标题不能为空！");
			}
			if(StringUtils.isBlank(padWeixinContent)){
				throw new BusinessException("微信内容不能为空！");
			}
			
			if (padMsgContent.length() > 680) {
				throw new BusinessException("通知内容字数不能超过680 ！");
			}
			if (padWeixinContent.length() > 680) {
				throw new BusinessException("微信内容字数不能超过680 ！");
			}
			
			if(StringUtils.isBlank(oneMsgStatus) || (!YesOrNoStatus.YES.equals(oneMsgStatus) && !YesOrNoStatus.NO.equals(oneMsgStatus))){
				throw new BusinessException("请选择是否合并发送！");
			}
			
			//excel编码需上传文件
			if(sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE)){
				if(file.isEmpty() && StringUtils.isBlank(excelPath)){
					throw new BusinessException("上传文件不能为空");
				}
				
				dataFilePath = excelPath;
				if(!file.isEmpty()){
					dataFilePath = uploadFileRequest(file);//新增上传excel文件或修改时重新上传excel文件
					File oldFile = new File(excelPath);//旧文件要删除
					if(oldFile.exists()){
						oldFile.delete();
					}
				}
			}
			
			service.savePadMsgBatchTask(request, bean, dataFilePath,padMsgTitle,padMsgContent,oneMsgStatus,sendPadMsgType,padMsgIdcId,padMsgPadCodeGt,padMsgPadCodeLt,padWeixinContent);
			return;
		}else{
			//其它批处理必须上传文件
			if(file.isEmpty() && (null == excelPath || "".equals(excelPath.trim()))){
				throw new BusinessException("上传文件不能为空");
			}
			
			dataFilePath = excelPath;
			if(!file.isEmpty()){
				dataFilePath = uploadFileRequest(file);//上传excel文件
				File oldFile = new File(excelPath);//旧文件要删除
				if(oldFile.exists()){
					oldFile.delete();
				}
			}
		}
		
		//批处理——虚拟设备授权开放与取消
		if(operateType.equals(BatchOperateType.VM_GRANT_OPEN) || operateType.equals(BatchOperateType.VM_GRANT_CANCEL)){
			service.saveVmGrantBatchTask(request, bean, dataFilePath);
			return;
		}
		
		//批处理——物理设备重启
		if(operateType.equals(BatchOperateType.DEVICE_REBOOT)){
			service.saveDeviceRebootBatchTask(request, bean, dataFilePath);
			return;
			
		}
		
		//批处理——虚拟设备重启
		if(operateType.equals(BatchOperateType.VM_REBOOT)){
			service.saveVmPadRestartBatchTask(request, bean, dataFilePath);
			return;
			
		}
		
		//批处理——批量绑定设备
		if(operateType.equals(BatchOperateType.PAD_BIND)){
			if(StringUtils.isBlank(padClassify)){
				throw new BusinessException("请选择绑定的设备类型");
			}
			
			if(PadClassify.PAD_CLASSIFY_MAIN.equals(padClassify) && null == goodsId){
				throw new BusinessException("请选择vip套餐类型");
			} else if(PadClassify.PAD_CLASSIFY_GAME.equals(padClassify)){
				if(StringUtils.isBlank(gamePadTime) || (Integer.parseInt(gamePadTime) <= 0)){
					throw new BusinessException("请输入绑定游戏设备时长,且必须大于0");
				}
			} else if(PadClassify.PAD_CLASSIFY_GVIP.equals(padClassify) && null == gvipGoodsId){
				throw new BusinessException("请选择gvip套餐类型");
			} else if(PadClassify.PAD_CLASSIFY_CLOUD.equals(padClassify)){
				if(StringUtils.isBlank(cloudPadTime) || (Integer.parseInt(cloudPadTime) <= 0)){
					throw new BusinessException("请输入绑定云控设备时长,且必须大于0");
				}
			} else {
				/**其它设备走vip设备逻辑**/
				if(null == goodsId){
					throw new BusinessException("请选择vip套餐类型");
				}
			}
			
			service.saveBindPadTask(request, bean, dataFilePath,padClassify,gamePadTime,goodsId,gvipGoodsId,cloudPadTime);
			return;
			
		}
		
		//批处理——设备维护、取消维护
		if(operateType.equals(BatchOperateType.START_MAINT) || operateType.equals(BatchOperateType.STOP_MAINT)){
			service.saveMiantBatchTask(request, bean, dataFilePath);
			return;
			
		}
		
		// 批处理——更换设备
		if(operateType.equals(BatchOperateType.RENEWAL_PAD)){
			
			padCodeGt = StringUtils.isBlank(padCodeGt) ? null : padCodeGt.trim();
			padCodeLt = StringUtils.isBlank(padCodeLt) ? null : padCodeLt.trim();
			if(null != padCodeGt){
				if(padCodeGt.length() < 2 || !"VM".equals(padCodeGt.substring(0, 2))){
					throw new BusinessException("设备编码段输入有误，必须以VM开头");
				}
			}
			
			if(null != padCodeLt){
				if(padCodeLt.length() < 2 || !"VM".equals(padCodeLt.substring(0, 2))){
					
					throw new BusinessException("设备编码段输入有误，必须以VM开头");
				}
			}
			
			if(YesOrNoStatus.YES.equals(isSendMessage) && StringUtils.isBlank(sendMessageTemplate)){
				throw new BusinessException("消息模板不能为空");
			}
			
			if(YesOrNoStatus.YES.equals(isSendWechart) && StringUtils.isBlank(sendWechartTemplate)){
				throw new BusinessException("微信模板不能为空");
			}
			
			String regexStr = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			if(StringUtils.isNotBlank(deviceIpGt)){
				if(!deviceIpGt.matches(regexStr)){
					throw new BusinessException("ip地址输入有误");
				}
			}
			if(StringUtils.isNotBlank(deviceIpLt)){
				if(!deviceIpLt.matches(regexStr)){
					throw new BusinessException("ip地址输入有误");
				}
			}
			
			reNewPadIdcId = StringUtils.isBlank(reNewPadIdcId) ? null : reNewPadIdcId.trim();
			padControlId = StringUtils.isBlank(padControlId) ? null : padControlId.trim();
			deviceIpGt = StringUtils.isBlank(deviceIpGt) ? null : deviceIpGt.trim();
			deviceIpLt = StringUtils.isBlank(deviceIpLt) ? null : deviceIpLt.trim();
			
			service.saveReNewalPadBatchTask(request, bean, dataFilePath, reNewPadIdcId, padControlId, padCodeGt, padCodeLt, 
					deviceIpGt,deviceIpLt,isSendMessage, isSendWechart, sendMessageTemplate, sendWechartTemplate);
			return;
			
		}
		
		//批处理——发送用户公告
		if(operateType.equals(BatchOperateType.USER_NOTICE)){
			//发送用户通告类型，通告内容不能为空
			if(StringUtils.isBlank(userNoticeTitle) || StringUtils.isBlank(userNoticeContent)){
				throw new BusinessException("公告内容和公告标题不能为空！");
			}
			
			if(YesOrNoStatus.YES.equals(popStatus)){
				if(null == popExpiredTime){
					throw new BusinessException("弹出有效时间不能为空！");
				}
			}
			
			service.saveNoticeBatchTask(request, bean, dataFilePath, userNoticeTitle,userNoticeContent,popStatus,popExpiredTime);
			return;
		}
		
		//批处理——增加设备时间
		if(operateType.equals(BatchOperateType.PAD_TIME)){
			if(null == timeType || "".equals(timeType.trim()) || null == controltime){
				throw new BusinessException("时间单位和控制设备时间不能为空！");
			}
			
			if(0 == controltime){
				 new BusinessException("时间数量不能为0");
			}
			service.savePadTimeBatchTask(request, bean, dataFilePath,timeType,controltime);
			return;
		}
		
		//批处理——按用户修改红豆数量
		if(operateType.equals(BatchOperateType.USER_RBC)){
			if (StringUtils.isBlank(userRbcType)) {
				throw new BusinessException("请选择赠送方式");
			}
			if(null == rbcGet || 0 == rbcGet.intValue()){
				throw new BusinessException("修改红豆数量不能为空或0！");
			}
			
			service.saveRbcGetBatchTask(request, bean, dataFilePath,userRbcType,rbcGet);
			return;
		}
	}
	
	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, TkBatchTask bean)
			throws Exception {
		service.start(request, bean);
	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, TkBatchTask bean)
			throws Exception {
		service.stop(request, bean);
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, TkBatchTask bean)
			throws Exception {
		service.delete(request, bean);
	}
	
	/**
	 * 启用任务批次
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "startBatchTask")
	public void startBatchExcute(HttpServletRequest request, HttpServletResponse response, Model model, TkBatchTask bean)
			throws Exception {
		String[] ids = bean.getIds().split(",");
		for (String id : ids) {
			TkBatchTask batchTask = service.get(Integer.valueOf(id));
			if(!StringUtils.equals(batchTask.getOperateStatus(), BatchOperateStatus.WAIT_START)){
				throw new BusinessException("存在已经被启用批处理任务不能重复启用，请刷新页面再试");
			}
			if(StringUtils.equals(YesOrNoStatus.NO,batchTask.getEnableStatus())){
				throw new BusinessException("存在已经被禁用的批处理任务");
			}
			batchTask.setOperateStatus(BatchOperateStatus.START);
			batchTask.setModifier(SessionUtils.getCurrentUserId(request));
			batchTask.setModifyTime(new Date());
			service.update(request, batchTask);
			//修改状态后再发送
			try {
				tkBatchTaskProducer.sendMessage(batchTask.getBatchId().toString());
				log.info(batchTask.getBatchId() + " 已经发送jms，正在启用");
			} catch (Exception e) {
				log.error("send jms failer" + e.getMessage(), e);
				batchTask.setOperateStatus(BatchOperateStatus.WAIT_START);
				batchTask.setModifier(SessionUtils.getCurrentUserId(request));
				batchTask.setModifyTime(new Date());
				service.update(request, batchTask);
				throw new BusinessException("发送启动任务批次jms失败，请联系管理员");
			}
			
		}
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 * @throws Exception
	 */
    private String uploadFileRequest(MultipartFile file) throws Exception {
    	String batchTaskFilePath = filePathUtils.getFilePath()+"/batchTask/data";
    	String batchTaskFileLinkUrl = filePathUtils.getFileLinkUrl()+"/batchTask/data";
    	
        Map<String, String> fileMap = null;
        if (!file.isEmpty()) {
            fileMap = fileUtil.saveUploadFile(file, batchTaskFilePath, batchTaskFileLinkUrl);
            return "/batchTask/data"+fileMap.get("fileName");
        }
        return null;
    }
    
    /**
     * 下载excel文件(已废弃,改成直接点击url下载)
     * @param request
     * @param response
     * @param dataFileId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "downloadBatchFile")
    private String downloadBatchFile(HttpServletRequest request, HttpServletResponse response,String filePath,String fileName) throws Exception {
    	DownloadUtil downloadUtil = new DownloadUtil();
    	downloadUtil.download(filePath.trim(),new String(fileName.trim().getBytes("iso8859-1"), "utf-8")+".xlsx", response, false);
    	return null;
    }
    
    /**
     * 查看单个批次任务详情
     * @param request
     * @param response
     * @param model
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "look")
	public String look(HttpServletRequest request, HttpServletResponse response, Model model, TkBatchTask bean) throws Exception {
    	if(bean.getBatchId() != null){
			bean = service.get(bean.getBatchId());

			model.addAttribute("bean", bean);
			Integer batchId = bean.getBatchId();
			String operateType = bean.getOperateType();
			if(operateType.equals(BatchOperateType.START_MAINT) || operateType.equals(BatchOperateType.STOP_MAINT)){
				//批处理——设备维护、取消维护
				getFileValue(model, bean, batchId,BatchDataCode.PAD_MAINT_FILE_PATH);
				
			}else if(operateType.equals(BatchOperateType.RENEWAL_PAD)){
				// 批处理——更换设备
				getFileValue(model, bean, batchId,BatchDataCode.RENEWAL_PAD_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_RENEWPADIDCID,"reNewPadIdcId");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_PADCONTROLID,"padControlId");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_PADCODEGT,"padCodeGt");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_PADCODELT,"padCodeLt");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_DEVICEIPGT,"deviceIpGT");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_DEVICEIPLT,"deviceIpLT");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_IS_MESSAGE,"isSendMessage");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_MESSAGE_TEMPLATE,"sendMessageTemplate");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_IS_WECHART,"isSendWechart");
				getShowValue(model, batchId,BatchDataCode.RENEWAL_PAD_WECHART_TEMPLATE,"sendWechartTemplate");
				
			}else if (operateType.equals(BatchOperateType.USER_NOTICE)) {
				//发送用户公告
				getFileValue(model, bean, batchId,BatchDataCode.USER_NOTICE_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.USER_NOTICE_TITLE,"userNoticeTitle");
				getShowValue(model, batchId,BatchDataCode.USER_NOTICE_CONTENT,"userNoticeContent");
				getShowValue(model, batchId,BatchDataCode.USER_NOTICE_POPSTATUS,"noticePopstatus");
				getShowValue(model, batchId,BatchDataCode.USER_NOTICE_POPEXPIRED,"noticePopexpired");
				
			}else if (operateType.equals(BatchOperateType.PAD_NOTICE)) {
				//发送设备公告
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, BatchDataCode.PAD_NOTICE_SEND_TYPE);//赠送方式
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					String sendPasNoticeType = batchData.getDataValue();
					model.addAttribute("sendPasNoticeType", sendPasNoticeType);
					if(sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_IDC)){
						getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_IDC_ID,"padNoticeIdcId");//按机房赠送
						
					}else if (sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE)){
						getFileValue(model, bean, batchId,BatchDataCode.PAD_NOTICE_FILE_PATH);//按设备编码赠送，有excel文件
						
					}else if(sendPasNoticeType.equals(SendPadNoticeType.SEND_PAD_NOTICE_BY_PADECODE_BETWEEN)){
						getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_PADCODEGT,"padNoticePadCodeGt");
						getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_PADCODELT,"padNoticePadCodeLt");
					}
				}
				getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_TITLE,"padNoticeTitle");
				getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_CONTENT,"padNoticeContent");
				getShowValue(model, batchId,BatchDataCode.PAD_NOTICE_POPSTATUS,"noticePopstatus");
				getShowValue(model, batchId,BatchDataCode.ONE_NOTICE_STATUS,"oneNoticeStatus");
				
			}else if (operateType.equals(BatchOperateType.USER_RBC)) {
				//按用户赠送红豆
				getFileValue(model, bean, batchId,BatchDataCode.RBGET_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.RBGET_AMOUNT,"rbcGet");
				getShowValue(model, batchId,BatchDataCode.RBGET_REMARK,"rbcGetRemark");
				getShowValue(model, batchId,BatchDataCode.RBGET_TYPE,"userRbcType");
				getShowValue(model, batchId,BatchDataCode.RBGET_TYPE,"userRbcType");
				
			}else if (operateType.equals(BatchOperateType.PAD_RBC)){
				//按设备赠送红豆
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, BatchDataCode.RBC_BY_PAD_GIVETYPE);//赠送方式
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					model.addAttribute("giveRbcType", batchData.getDataValue());
					if(GiveRbcType.GIVE_RBC_BY_IDC.equals(batchData.getDataValue())){
						getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_IDCID,"idcId");//按机房赠送
						
					}else if (GiveRbcType.GIVE_RBC_BY_PADECODE.equals(batchData.getDataValue())){
						getFileValue(model, bean, batchId,BatchDataCode.RBC_BY_PAD_FILE_PATH);//按设备编码赠送，有excel文件
						
					}else if(GiveRbcType.GIVE_RBC_BY_PADECODE_BETWEEN.equals(batchData.getDataValue())){
						getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_PADECODEGT,"padRbcPadCodeGt");
						getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_PADECODELT,"padRbcPadCodeLt");
					}
					getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_COMMOMRBC,"commonRbc");
					getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_VIPRBC,"vipRbc");
					getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_SVIPRBC,"svipRbc");
					getShowValue(model, batchId,BatchDataCode.RBC_BY_PAD_GVIPRBC,"gvipRbc");
				}
				
			}else if (operateType.equals(BatchOperateType.PAD_TIME)){
				//赠送设备时间
				getFileValue(model, bean, batchId,BatchDataCode.PAD_TIME_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.PAD_TIME_TYPE,"timeType");
				getShowValue(model, batchId,BatchDataCode.PAD_TIME_AMOUNT,"controltime");
				
			}else if (operateType.equals(BatchOperateType.PAD_BIND)){
				//绑定设备
				getFileValue(model, bean, batchId,BatchDataCode.BIND_PAD_FILE_PATH);
				getShowValue(model, batchId,BatchDataCode.BIND_PAD_GOODS,"goodsId");
				getShowValue(model, batchId,BatchDataCode.BIND_PAD_PADCLASSIFY,"padClassify");
				getShowValue(model, batchId,BatchDataCode.BIND_GAMEPAD_TIME,"gamePadTime");
				getShowValue(model, batchId,BatchDataCode.BIND_CLOUDPAD_TIME,"cloudPadTime");
				getShowValue(model, batchId,BatchDataCode.BIND_PAD_GVIPGOODS,"gvipGoodsId");
				
			}else if (operateType.equals(BatchOperateType.VM_REBOOT)){
				//虚拟设备重启
				getFileValue(model, bean, batchId,BatchDataCode.VM_PAD_RESTART_FILE_PATH);
				
			}else if (operateType.equals(BatchOperateType.DEVICE_REBOOT)){
				//物理设备重启
				getFileValue(model, bean, batchId,BatchDataCode.DEVICE_REBOOT_FILE_PATH);
				
			}else if (operateType.equals(BatchOperateType.VM_GRANT_OPEN) || operateType.equals(BatchOperateType.VM_GRANT_CANCEL)){
				//批处理——虚拟设备授权开放与取消
				getFileValue(model, bean, batchId,BatchDataCode.VM_GRANT_FILE_PATH);
			}else if (operateType.equals(BatchOperateType.ENABLE_PAD_YES) || operateType.equals(BatchOperateType.ENABLE_PAD_NO)){
				//设备启用或禁用
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, BatchDataCode.ENABLE_PAD_TYPE);//启用或禁用方式
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					String enablePadType = batchData.getDataValue();
					model.addAttribute("enablePadType", enablePadType);
					if(EnablePadType.ENABLE_PAD_BY_FILE.equals(enablePadType)){
						getFileValue(model, bean, batchId,BatchDataCode.ENABLE_PAD_FILE_PATH);//按设备编码，有excel文件
					}else if (EnablePadType.ENABLE_PAD_BY_SELECTION.equals(enablePadType)){
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_IDC_ID,"enablePadIdcId");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_CONTROLL_ID,"enablePadControlId");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_STATUS,"enablePadStatus");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_BIND_STATUS,"enablePadBindStatus");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_FAULT_STATUS,"enablePadFaultStatus");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_PADCODEGT,"enablePadCodeGt");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_PADCODELT,"enablePadCodeLt");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_DEVICEIPGT,"enablePadDeviceIpGt");
						getShowValue(model, batchId,BatchDataCode.ENABLE_PAD_DEVICEIPLT,"enablePadDeviceIpLt");
					}
				}
				
			}else if (operateType.equals(BatchOperateType.PAD_MSG)) {
				//发送设备消息
				TkBatchData batchData = dataService.selectByBatchIdAndDataCode(batchId, BatchDataCode.PAD_MSG_SEND_TYPE);//发送方式
				if(null != batchData && !StringUtils.isBlank(batchData.getDataValue())){
					String sendPadMsgType = batchData.getDataValue();
					model.addAttribute("sendPadMsgType", sendPadMsgType);
					if(sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_IDC)){
						getShowValue(model, batchId,BatchDataCode.PAD_MSG_IDC_ID,"padMsgIdcId");//按机房赠送
						
					}else if (sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE)){
						getFileValue(model, bean, batchId,BatchDataCode.PAD_MSG_FILE_PATH);//按设备编码赠送，有excel文件
						
					}else if(sendPadMsgType.equals(SendPadMsgType.SEND_PAD_MSG_BY_PADECODE_BETWEEN)){
						getShowValue(model, batchId,BatchDataCode.PAD_MSG_PADCODEGT,"padMsgPadCodeGt");
						getShowValue(model, batchId,BatchDataCode.PAD_MSG_PADCODELT,"padMsgPadCodeLt");
					}
				}
				getShowValue(model, batchId,BatchDataCode.PAD_MSG_TITLE,"padMsgTitle");
				getShowValue(model, batchId,BatchDataCode.PAD_MSG_CONTENT,"padMsgContent");
				getShowValue(model, batchId,BatchDataCode.ONE_MSG_STATUS,"oneMsgStatus");
				getShowValue(model, batchId,BatchDataCode.PAD_WEIXIN_CONTENT,"padWeixinContent");
			}
		}
		
		List<RfIdc> idcList = idcService.initQuery(new RfIdc()).findDelTrue();//获取机房列表,按机房赠送红豆
		model.addAttribute("idcList", idcList);
		
		List<RfGoods> goods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsVip()).addOrderClause("goodsPrice", "asc").findStopTrue();//获取商品列表，批量绑定设备
		model.addAttribute("goods", goods);
		List<RfGoods> gvipGoods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsGvip()).addOrderClause("goodsPrice", "asc").findStopTrue();//获取商品列表，批量绑定设备
		model.addAttribute("gvipGoods", gvipGoods);
		
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		model.addAttribute("controlList", controlList);//控制节点
		
		return this.toPage(request, response, model);
	}
}
