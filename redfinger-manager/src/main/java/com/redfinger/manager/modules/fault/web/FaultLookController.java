package com.redfinger.manager.modules.fault.web;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.ExportConstants;
import com.redfinger.manager.common.domain.RfExport;
import com.redfinger.manager.common.domain.RfFaultFeedback;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.helper.PermissionHelper;
import com.redfinger.manager.common.jsm.ExportProducer;
import com.redfinger.manager.common.jsm.FingerProducer;
import com.redfinger.manager.common.utils.ClientType;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.modules.base.service.ClassService;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.batch.service.ExportService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.fault.service.FaultFeedbackService;
import com.redfinger.manager.modules.fault.service.FaultHandleService;
import com.redfinger.manager.modules.goods.service.GoodsService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sys.service.AdminService;

@Controller
@RequestMapping(value = "/fault/look")
public class FaultLookController extends BaseController {

	@Autowired
	FaultFeedbackService service;
	@Autowired
	ClassService classService;
	@Autowired
	FaultHandleService handleService;
	@Autowired
	AdminService adminService;
	@Autowired
	PadService padService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	UserService userService;
	@Autowired
	private FingerProducer fingerProducer;
	@Autowired
	ConfigService configService;
	@Autowired
	ExportService exportService;
	@Autowired
	FilePathUtils filePathUtils;
	@Autowired
	ExportProducer exportProducer;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// 故障类型
		model.addAttribute("categoryTree", classService.getStopTrueTreeAndTop(ConstantsDb.rfClassClassTypeFault()));
		// 故障修复类型
		model.addAttribute("categoryFixTree",classService.getStopTrueTreeAndTop(ConstantsDb.rfClassClassTypeFaultfix()));
		// 咨询人员
		List<SysAdmin> zixunList=PermissionHelper.findAdminByRoleList(Lists.newArrayList(ConfigConstantsDb.configRoleZixun().split(",")));
		String systemCreater=ConfigConstantsDb.configFaultClientCreater();
		if(StringUtils.isNotBlank(systemCreater)){
			zixunList.add(adminService.get(systemCreater));
		}
		model.addAttribute("zixunList", zixunList);
		// 测试 & 运维 & 客服
		List<String> roles=Lists.newArrayList();
		roles.addAll(Lists.newArrayList(ConfigConstantsDb.configRoleCeshi().split(",")));
		roles.addAll(Lists.newArrayList(ConfigConstantsDb.configRoleKefu().split(",")));
		roles.addAll(Lists.newArrayList(ConfigConstantsDb.configRoleYunwei().split(",")));
		List<SysAdmin> handerList=PermissionHelper.findAdminByRoleList(Lists.newArrayList(roles));
		model.addAttribute("handerList", handerList);
		model.addAttribute("currentUserId", SessionUtils.getCurrentUserId(request));
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfFaultFeedback> list(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean, String padCodeStart, String padCodeEnd,String padStatus, String vmStatus) throws Exception {
		List<String> feedbackStatusList=null;
		if(StringUtils.isNotBlank(bean.getFeedbackStatus())){
			feedbackStatusList=Lists.newArrayList(bean.getFeedbackStatus());
		}
		List<RfFaultFeedback> list = service.query(request, bean,feedbackStatusList,false,padCodeStart,padCodeEnd, padStatus, vmStatus);
		if(null != list && list.size() > 0){
			for (RfFaultFeedback faultFeedback : list) {
				faultFeedback.getMap().put("clientSourceName", ClientType.DICT_MAP.get(faultFeedback.getClientSource()));
			}
		}
		
		PageInfo<RfFaultFeedback> pageInfo = new PageInfo<RfFaultFeedback>(list);
		return pageInfo;
	}

	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model, RfFaultFeedback bean) throws Exception {
		service.detail(request, model, bean);
		return this.toPage(request, response, model,"detail");
	}
	
	 // 导出
	@RequestMapping(value="export")
	public String export(HttpServletRequest request,HttpServletResponse response,Model model,RfFaultFeedback bean,String exportHead, String exportField, String exportName, String padCodeStart, String padCodeEnd,String padStatus, String vmStatus)throws Exception{
		System.out.println(exportHead);
		exportHead=StringUtils.removeEnd(exportHead, ",");
		exportField=StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xls");
		// 创建一个workbook 对应一个excel应用文件
		Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
		// 构建表头
		ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		// 构建表体
		boolean keep=true;
		int page=1;
		while(keep){
			bean.setPage(page);
			bean.setRows(1000);
			PageInfo<RfFaultFeedback> pageInfo=this.list(request, response, model, bean, padCodeStart, padCodeEnd,padStatus,vmStatus);
			List<RfFaultFeedback> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
				for(RfFaultFeedback pad:list){
					if(null != pad.getMap().get("bindStatus")){
						pad.getMap().put("bindStatus",pad.getMap().containsKey("bindStaut")?DictHelper.getLabel("rf_pad.bind_status", pad.getMap().get("bindStatus").toString()):"--");
					}
					if(null !=  pad.getMap().get("padGrade")){
						pad.getMap().put("padGrade", pad.getMap().containsKey("padGrade")?DictHelper.getLabel("rf_user_pad.pad_grade", pad.getMap().get("padGrade").toString()):"--" );
					}
					if(null !=  pad.getMap().get("padStatus")){
						pad.getMap().put("padStatus", pad.getMap().containsKey("padStatus")?DictHelper.getLabel("rf_pad.pad_status", pad.getMap().get("padStatus").toString()):"--" );
					}
					if(null !=  pad.getMap().get("vmStatus")){
						pad.getMap().put("vmStatus", pad.getMap().containsKey("vmStatus")?DictHelper.getLabel("rf_pad.vm_status", pad.getMap().get("vmStatus").toString()):"--" );
					}
					pad.setFeedbackStatus(DictHelper.getLabel("rf_fault_feedback.feedback_status", pad.getFeedbackStatus()));
				}
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list),sheet,bodyStyle,exportField.split(","),pageInfo.getStartRow());
				if(pageInfo.isHasNextPage()){
					page++;
					continue;
				}
			}
			keep=false;
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
    }  
	
	@RequestMapping(value = "asyncExport")
	public void asyncExport(HttpServletRequest request, HttpServletResponse response, 
			Model model, String exportHead, String exportField, String exportName,String exportTaskName, String queryParams) throws Exception{
		String queryJson = URLDecoder.decode(queryParams, "utf-8");
		
		List<RfExport> rfExportts = exportService.initQuery()
				.andEqualTo("parm", queryJson)
				.andEqualTo("type", ExportConstants.TYPE_FAULT_LOOK)
				.addOrderClause("createTime", "desc")
				.findAll();
				Date now = new Date();
				
		// 已有导出任务
		if (null != rfExportts && rfExportts.size() > 0
				&& DateUtils.getDistanceOfTwoDateM(rfExportts.get(0).getCreateTime(), now) < 3) {
				throw new BusinessException("已经有该条件的导出任务, 请在"
					+ (int) (3 - DateUtils.getDistanceOfTwoDateM(rfExportts.get(0).getCreateTime(), now)) + "分钟后再试试");
		}
				
		String baseFilePath = filePathUtils.getFilePath()+"/batchTask/export";
    	String baseFileLinkUrl = filePathUtils.getFileLinkUrl()+"/batchTask/export";
    	String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());				//日作为文件夹
		String dateTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());		//年月日时分秒作为文件名
		int randomNumber = new Random().nextInt(999999);
		createFilePath(baseFilePath + File.separator + dateStr);
		String fileName = dateTimeStr + randomNumber+".xlsx";
		String filePath = baseFilePath + File.separator + dateStr + File.separator + fileName;//无斜杠结尾
		String fileUrl = baseFileLinkUrl + File.separator + dateStr + File.separator + fileName;
    	
		RfExport export = new RfExport();
		export.setPath(filePath);
		export.setUrl(fileUrl);
		export.setParm(queryJson);
		export.setTaskName(exportTaskName);
		export.setType(ExportConstants.TYPE_FAULT_LOOK);
		export.setExportStatus(ExportConstants.EXPORT_STATUS_MAKE);//任务创建未执行
		exportService.save(request, export);
		HashMap<Object, String> exportMap = Maps.newHashMap();
		exportMap.put("exportId", String.valueOf(export.getExportId()));
		exportMap.put("exportHead", exportHead);
		exportMap.put("exportField", exportField);
		System.out.println(JsonUtils.ObjectToString(exportMap));
		exportProducer.sendMessage(JsonUtils.ObjectToString(exportMap));
	}

	/**
	 * 跳转到管理员绑定页面
	 * @param request
	 * @param response
	 * @param model
	 * @param padCodes		设备code，多个用,分隔开
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "adminBindingForm")
	public String adminBindingForm(HttpServletRequest request, HttpServletResponse response, Model model, String padCodes) throws Exception {
		List<RfPad> padList = padService.selectPadByCodes(padCodes.split(","));
		model.addAttribute("padList", padList);
		
		
		/*boolean isMain = false;
		boolean isGame = false;
		for(RfPad rfPad : padList){//判断是否同时存在主营设备和游戏设备//TODOO
			if(PadClassify.PAD_CLASSIFY_MAIN.equals(rfPad.getPadClassify())){
				isMain = true;
			}else if(PadClassify.PAD_CLASSIFY_GAME.equals(rfPad.getPadClassify())){
				isGame = true;
			}
		}
		if(isMain&&isGame){
			throw new BusinessException("不能同时绑定主营设备和游戏设备");
		}
		
		if(isGame){
			model.addAttribute("padClassify", PadClassify.PAD_CLASSIFY_GAME);
		}else{
			model.addAttribute("padClassify", PadClassify.PAD_CLASSIFY_MAIN);
		}*/
		
		SysConfig config = configService.get("config_gamePad_online_time");
		model.addAttribute("config", config);
		
		List<RfGoods> goods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsVip()).findStopTrue();
		model.addAttribute("goods", goods);
		return this.toPage(request, response, model);
	}
	
	/**
	 * 管理员绑定设备 方法
	 * @param request
	 * @param response
	 * @param model
	 * @param mid		绑定的帐号
	 * @param goodsId	VIP套餐
	 * @param padId		绑定的设备id集合
	 * @throws Exception
	 */
	@RequestMapping(value = "adminbinding")
	public void adminbinding(HttpServletRequest request, HttpServletResponse response, Model model, String mid, String goodsId, Integer[] padId,String padClassify,Integer gamePadOnlineTime) throws Exception {
		RfUser user = userService.getUser(request, mid);
		if (null != user) {
			
			for (Integer pid : padId) {
				RfUserPad bean = new RfUserPad();
				bean.setUserId(user.getUserId());
				bean.setPadId(pid);
				padService.adminBinding(request, bean,goodsId, padClassify, gamePadOnlineTime);
			}
			
			//向用户发送jms
			JSONObject sendMessageObj = new JSONObject();
			sendMessageObj.put("opType", "refresh");
			sendMessageObj.put("userId", user.getUserId());
			String sendMessageStr = sendMessageObj.toString();
			log.info("sendMessageStr: "+ sendMessageStr);
			// 发送jms消息
			fingerProducer.sendMessage(sendMessageStr);
		} else {
			throw new BusinessException("用户手机号或EMAIL输入错误！");
		}
	}
	
	private void createFilePath(String foldName){
		if(StringUtils.isNotEmpty(foldName)){
			File file = new File(foldName);
			if(file.exists()){
				if(file.isFile()){
					file.deleteOnExit();
					file.mkdir();
				}
			}else{
				file.mkdirs();
			}
		}
	}
}
