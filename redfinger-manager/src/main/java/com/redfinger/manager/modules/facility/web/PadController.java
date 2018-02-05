package com.redfinger.manager.modules.facility.web;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.bean.view.ViewPadGoods;
import com.redfinger.manager.common.constants.CommonPadConfigCode;
import com.redfinger.manager.common.constants.ConfigConstantsDb;
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.ExportConstants;
import com.redfinger.manager.common.constants.MsgTemplateType;
import com.redfinger.manager.common.constants.PadClassify;
import com.redfinger.manager.common.domain.RfControl;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfExport;
import com.redfinger.manager.common.domain.RfFacility;
import com.redfinger.manager.common.domain.RfGoods;
import com.redfinger.manager.common.domain.RfIdc;
import com.redfinger.manager.common.domain.RfLabel;
import com.redfinger.manager.common.domain.RfMsgTemplate;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfPadExample;
import com.redfinger.manager.common.domain.RfPadExample.Criteria;
import com.redfinger.manager.common.domain.RfPadGrantCode;
import com.redfinger.manager.common.domain.RfPadLabel;
import com.redfinger.manager.common.domain.RfPadTask;
import com.redfinger.manager.common.domain.RfPadTaskBatch;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.RfVideo;
import com.redfinger.manager.common.domain.SysConfig;
import com.redfinger.manager.common.domain.ViewPadUser;
import com.redfinger.manager.common.domain.ViewPadUserExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.jsm.ExportProducer;
import com.redfinger.manager.common.jsm.FingerProducer;
import com.redfinger.manager.common.jsm.WeixinProducer;
import com.redfinger.manager.common.mapper.RfDeviceMapper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.GrantCodeStatusUtils;
import com.redfinger.manager.common.utils.GrantControlUtils;
import com.redfinger.manager.common.utils.GrantTypeUtils;
import com.redfinger.manager.common.utils.GrantWatchUtils;
import com.redfinger.manager.common.utils.IP2LongUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.NumberUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.common.utils.YesOrNoStatus;
import com.redfinger.manager.modules.base.service.ConfigService;
import com.redfinger.manager.modules.batch.service.ExportService;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.DeviceService;
import com.redfinger.manager.modules.facility.service.FacilityService;
import com.redfinger.manager.modules.facility.service.IdcService;
import com.redfinger.manager.modules.facility.service.PadGrantService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.facility.service.UserPadService;
import com.redfinger.manager.modules.facility.service.VideoService;
import com.redfinger.manager.modules.facility.service.ViewPadUserService;
import com.redfinger.manager.modules.fault.service.FaultMsgTemplateService;
import com.redfinger.manager.modules.goods.service.GoodsService;
import com.redfinger.manager.modules.grant.service.PadGrantCodeService;
import com.redfinger.manager.modules.label.service.LabelService;
import com.redfinger.manager.modules.log.service.PadHandleLogService;
import com.redfinger.manager.modules.log.service.PadTaskService;
import com.redfinger.manager.modules.log.service.UserPadLogService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.sys.service.AdminService;
import com.redfinger.manager.modules.tasks.jms.ProducterMessageSender;
import com.redfinger.manager.modules.tasks.service.TaskBatchService;

@Controller
@RequestMapping(value = "/facility/pad")
public class PadController extends BaseController {
	@Autowired
	PadService service;
	@Autowired
	TaskBatchService taskBatchService;
	@Autowired
	PadTaskService padTaskService;
	@Autowired
	UserService userService;
	@Autowired
	IdcService idcService;
	@Autowired
	ControlService controlService;
	@Autowired
	UserPadService userPadService;
	@Autowired
	VideoService videoService;
	@Autowired
	UserPadLogService userPadLogService;
	@Autowired
	PadHandleLogService padHandleLogService;
	@Autowired
	ViewPadUserService viewPadUserService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	PadService padService;
	@Autowired
	RfDeviceMapper deviceMapper;
	@Autowired
	private FingerProducer fingerProducer;
	@Autowired
	private PadGrantCodeService padGrantCodeService;
	@Autowired
	ProducterMessageSender pro;
	@Autowired
	private FacilityService facilityService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private WeixinProducer weixinProducer;
	@Autowired
	FaultMsgTemplateService msgTemplateService;
	@Autowired
	ConfigService configService;
	@Autowired
	PadGrantService padGrantService;
	@Autowired
	AdminService adminService;
	@Autowired
	private ExportService exportService;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private ExportProducer exportProducer;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfIdc> idcList = idcService.initQuery().findStopTrue();
		model.addAttribute("idcList", idcList);
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		model.addAttribute("controlList", controlList);
		
		List<RfFacility> facilityList = facilityService.initQuery().findDelTrue();
		model.addAttribute("facilityList", facilityList);
		model.addAttribute("currentUserId", SessionUtils.getCurrentUserId(request));
		return this.toPage(request, response, model);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ViewPadUser> list(HttpServletRequest request, HttpServletResponse response, 
			Model model, ViewPadUser bean,String padCode2,String padControlTime,
			String expireTime,String expireTimeBegin,String expireTimeEnd,String padControlTimeBegin,
			String padControlTimeEnd,String type,String padCodes,String deviceCodes,String userMobilePhone, 
			String renewalBeginTimeStr, String renewalEndTimeStr,String deviceStartIp, String deviceEndIp,
			Integer externalUserId) throws Exception {
			//会员ID查询 yirongze修改于 17-8-22
			Integer userId = null;
			if (externalUserId!=null) {
				RfUser user = userService.getUserByExternalUserIdOrUserPhone(null,externalUserId);
				if (null != user) {
					userId = user.getUserId();
				} else {
					userId = -1;
				}
			}
			String regexChinese = "^[0-9a-zA-Z]+$";
			List<String> codeS = Lists.newArrayList();
		  	if(StringUtils.isNotBlank(padCodes)){
		  		codeS=Arrays.asList(padCodes.split(","));
		  	}
		  	List<Integer> deviceIds = Lists.newArrayList();
		  	if(StringUtils.isNotBlank(deviceCodes)){
		  		List<RfDevice> devicelist = deviceService.deviceCodes(deviceCodes);
		  		if(!Collections3.isEmpty(devicelist)){
		  			deviceIds.addAll(Collections3.extractToList(devicelist, "deviceId"));
		  		}else{
		  			deviceIds.add(-1);
		  		}
		  	}
		  	List<Integer> padIds = Lists.newArrayList();
		  	if(StringUtils.isNotBlank(userMobilePhone)){
		  		List<RfUser> userList = userService.initQuery().andEqualTo("userMobilePhone", userMobilePhone).findAll();
		  		if(!Collections3.isEmpty(userList)){
		  			List<RfUserPad> userPadList = userPadService.initQuery().
		  					andEqualTo("userId", userList.get(0).getUserId()).findAll();
		  			if(!Collections3.isEmpty(userPadList)){
		  				padIds=Collections3.extractToList(userPadList, "padId");
		  			}else {
		  				padIds.add(-1);
					}
		  		}else {
		  			padIds.add(-1);
				}
		  	}
		  	
			if(!StringUtils.isEmpty(bean.getPadCode())){
				if (!bean.getPadCode().matches(regexChinese)) {
					bean.setPadCode("-1");
				}
			}
			if(!StringUtils.isEmpty(padCode2)){
				if (!padCode2.matches(regexChinese)) {
					padCode2 = "-1";
				}
			}
			
			String startPadCode=null;
			if(!StringUtils.isEmpty(padCode2)){
				startPadCode=bean.getPadCode();
				bean.setPadCode(null);
			}
			
			//设备到期时间大于当前时间为正常设备
			Date leftOnlineTime=null;
			if(bean.getLeftOnlineTimeT2()!=null){
				if(bean.getLeftOnlineTimeT2()==0){
					//过期时间大于当前时间正常使用期
			        leftOnlineTime=new Date();
				}else
				if(bean.getLeftOnlineTimeT2()==-3){
					//过期时间小于当前时间大于当前时间减去赎回期
					//leftOnlineTime=new Date();
					bean.setExpireTimeT2(new Date());
				}else{
					if(bean.getLeftOnlineTimeT2()==-4){
						//过期时间小于当前时间加赎回期
						bean.setExpireTimeT2(DateUtils.addDays(new Date(),Integer.parseInt(ConfigConstantsDb.configFacilityRansom())));
					}
				}
			}
	
			if ("1".equals(type)) {
				type = "0";
			}
			// 查询条件
			viewPadUserService.initQuery(bean);
			ViewPadUserExample example =(ViewPadUserExample) viewPadUserService.getExample();
			//iPv4的ip地址都是（1~255）.（0~255）.（0~255）.（0~255）的格式
			String regexStr = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			if(StringUtils.isNotBlank(deviceStartIp)){
				if(deviceStartIp.matches(regexStr)){
					example.getMap().put("deviceStartIp", deviceStartIp);
				}else{
					deviceIds.add(-1);
				}
			}
			if(StringUtils.isNotBlank(deviceEndIp)){
				if(deviceEndIp.matches(regexStr)){
					example.getMap().put("deviceEndIp", deviceEndIp);
				}else{
					deviceIds.add(-1);
				}
			}
			if(example.getMap().size()==2){
				if(IP2LongUtils.ipToNumber(deviceStartIp)>IP2LongUtils.ipToNumber(deviceEndIp)){
					deviceIds.add(-1);
					example.getMap().clear();
				}
			}
			if("null".equals(bean.getControlProtocol())) {
				viewPadUserService.andIsNull("controlProtocol");
			} else {
				viewPadUserService.andEqualTo("controlProtocol", bean.getControlProtocol());
			}
			if (StringUtils.isNotBlank(bean.getRemark())) {
				if (bean.getRemark().contains("%")) {
					bean.setRemark(bean.getRemark().replaceAll("%","\\\\%" ));
				}
				if (bean.getRemark().contains("_")) {
					bean.setRemark(bean.getRemark().replaceAll("_","\\\\_" ));
				}
			}
			/*Date lastControlTime = null;
			if (StringUtils.isNotBlank(padControlTime)) {
				lastControlTime = DateUtils.addDays(DateUtils.parseDate(padControlTime),1);
			}*/
			
			List<ViewPadUser> list = viewPadUserService
					//.initQuery(bean)
		           // .andGreaterThanOrEqualTo("vmStatus", type)
					.andEqualTo("userIdT2", userId)
				    .andIn("deviceId", deviceIds)
				    .andIn("padId", padIds)
				    .andIn("padCode", codeS)
				    .andEqualTo("romVersion", bean.getRomVersion())
					.andEqualTo("padSn", bean.getPadSn())
					.andEqualTo("vmStatus", bean.getVmStatus())
					.andEqualTo("maintStatus", bean.getMaintStatus())
					.andEqualTo("grantOpenStatus",bean.getGrantOpenStatus() )
					.andEqualTo("padSource", bean.getPadSource())
				    .andLike("padName", bean.getPadName())
				    .andLike("userPadNameT2", bean.getUserPadNameT2())
				    .andLike("remark", bean.getRemark())
				    .andEqualTo("grantOpenStatus", bean.getGrantOpenStatus())
					.andGreaterThanOrEqualTo("expireTimeT2", DateUtils.parseDate(expireTimeBegin))
					.andLessThan("expireTimeT2", DateUtils.parseDate(expireTimeEnd))
				    .andEqualTo("padGradeT2", bean.getPadGradeT2())
					.andGreaterThanOrEqualTo("bindTimeT2", DateUtils.parseDate(bean.getBeginTimeStr()))
					.andLessThan("bindTimeT2", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
					.andGreaterThanOrEqualTo("renewalTimeT2", DateUtils.parseDate(renewalBeginTimeStr))
					.andLessThan("renewalTimeT2", DateUtils.parseDateAddOneDay(renewalEndTimeStr))
					.andLessThan("padControlTimeT2", DateUtils.parseDateAddOneDay(padControlTime))
					.andGreaterThanOrEqualTo("padControlTimeT2", DateUtils.parseDate(padControlTime))
					.andEqualTo("padControlId", bean.getPadControlId())
					.andEqualTo("padStatus", bean.getPadStatus())
					.andEqualTo("bindStatus", bean.getBindStatus())
					.andEqualTo("enableStatus", bean.getEnableStatus())
					.andEqualTo("faultStatus", bean.getFaultStatus())
					.andEqualTo("idcId", bean.getIdcId())
					.andEqualTo("padClassify", bean.getPadClassify())
					//.andEqualTo("controlProtocol", bean.getControlProtocol())
					.andLike("padCode", bean.getPadCode())
					.andGreaterThanOrEqualTo("padCode",startPadCode)
					.andLessThanOrEqualTo("padCode", padCode2)
					.andLike("padIp", bean.getPadIp())
					.andGreaterThanOrEqualTo("padControlTimeT2", DateUtils.parseDate(padControlTimeBegin))//2017-9-15 10:05:48解注释，不知之前出于什么目的注释掉的，现解注释
					.andLessThan("padControlTimeT2", DateUtils.parseDateAddOneDay(padControlTimeEnd))//2017-9-15 10:05:48解注释，不知之前出于什么目的注释掉的，现解注释
					.andLessThan("expireTimeT2", bean.getExpireTimeT2())
				    .andGreaterThanOrEqualTo("expireTimeT2", leftOnlineTime)
					.addOrderClause("padId", "asc")
					.addOrderForce(bean.getSort(), bean.getOrder())
					.pageDelTrue(bean.getPage(), bean.getRows());
			
			List<RfFacility> facilityList = facilityService.initQuery().addOrderClause("reorder", "asc").findAll();
			Map<String, RfFacility> facilityMap = new HashMap<String, RfFacility>();
			RfFacility f = null;
			for (RfFacility rfFacility : facilityList) {
				facilityMap.put(rfFacility.getFacilityCode(), rfFacility);
			}
			
			long currentTime = System.currentTimeMillis();
			for (ViewPadUser viewPadUser : list) {
				RfDevice deivce = deviceService.get(viewPadUser.getDeviceId());
				viewPadUser.getMap().put("deviceName", deivce == null ? "--" : deivce.getDeviceCode());
				viewPadUser.getMap().put("userMobilePhone", viewPadUser.getUserIdT2()==null?"--":userService.load(viewPadUser.getUserIdT2()).getUserMobilePhone());
				viewPadUser.getMap().put("idcName", viewPadUser.getIdcId() == null ? "--" : idcService.load(viewPadUser.getIdcId()).getIdcName());
				viewPadUser.getMap().put("controlName", viewPadUser.getUserControlId() == null ? "--" : controlService.load(viewPadUser.getUserControlId()).getControlName());
				viewPadUser.getMap().put("padControlName", viewPadUser.getPadControlId() == null ? "--" : controlService.load(viewPadUser.getPadControlId()).getControlName());
				viewPadUser.getMap().put("manageControlName", viewPadUser.getPadManageControlId() == null ? "--" : controlService.load(viewPadUser.getPadManageControlId()).getControlName());
				viewPadUser.getMap().put("showPadId",viewPadUser.getPadId());
				if(null!= viewPadUser.getExpireTimeT2()&&!("".equals(viewPadUser.getExpireTimeT2()))){
					viewPadUser.getMap().put("controltime", DateUtils.formatDateTime2(viewPadUser.getExpireTimeT2().getTime()-currentTime));
				}
				if(null!= viewPadUser.getLeftControlTimeT2()&&!("".equals(viewPadUser.getLeftControlTimeT2()))){
					Integer time=viewPadUser.getLeftControlTimeT2();
					Integer hour=time/(60*60);
		 			Integer  min =(time/60-hour*60);
		 			String onlinetime=hour.toString()+"小时"+min.toString()+"分钟";
					viewPadUser.getMap().put("onlinetime", onlinetime);
				}
				
				 f = facilityMap.get(viewPadUser.getPadSource());
	             if(f != null){
	            	 viewPadUser.getMap().put("padSourceName", f.getFacilityName());
	             }
	             if(null != viewPadUser.getDeviceId()){
	            	 viewPadUser.getMap().put("deviceIp", null != deivce ? deivce.getDeviceIp() : "");
	             }
	             
	             if(viewPadUser.getUserIdT2() != null){
	            	 viewPadUser.getMap().put("externalUserId",userService.load(viewPadUser.getUserIdT2()).getExternalUserId());
	             }
	            
			}
			PageInfo<ViewPadUser> pageInfo = new PageInfo<ViewPadUser>(list);	
			return pageInfo;
		}
	
	// 明细
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
		
		bean = viewPadUserService.initQuery().andEqualTo("padId", bean.getPadId()).singleAll().get(0);
		model.addAttribute("bean", bean);

		RfDevice deivce = deviceService.get(bean.getDeviceId());
		bean.getMap().put("deviceName", deivce == null ? "--" : deivce.getDeviceCode());
		bean.getMap().put("userMobilePhone", bean.getUserIdT2() == null ? "--" : userService.load(bean.getUserIdT2()).getUserMobilePhone());
		bean.getMap().put("idcName", bean.getIdcId() == null ? "--" : idcService.load(bean.getIdcId()).getIdcName());
		bean.getMap().put("controlName", bean.getUserControlId() == null ? "--" : controlService.load(bean.getUserControlId()).getControlName());
		bean.getMap().put("padControlName", bean.getPadControlId() == null ? "--" : controlService.load(bean.getPadControlId()).getControlName());
		bean.getMap().put("manageControlName", bean.getPadManageControlId() == null ? "--" : controlService.load(bean.getPadManageControlId()).getControlName());
		bean.getMap().put("showPadId",bean.getPadId());
		if (null != bean.getExpireTimeT2() && !("".equals(bean.getExpireTimeT2()))) {
			bean.getMap().put("controltime", DateUtils.formatDateTime2(bean.getExpireTimeT2().getTime() - System.currentTimeMillis()));
		}
		if (null != bean.getLeftControlTimeT2() && !("".equals(bean.getLeftControlTimeT2()))) {
			Integer time = bean.getLeftControlTimeT2();
			Integer hour = time / (60 * 60);
			Integer min = (time / 60 - hour * 60);
			String onlinetime = hour.toString() + "小时" + min.toString() + "分钟";
			bean.getMap().put("onlinetime", onlinetime);
		}

		List<RfFacility> facilityList = facilityService.initQuery().addOrderClause("reorder", "asc").findAll();
		Map<String, RfFacility> facilityMap = new HashMap<String, RfFacility>();
		RfFacility f = null;
		for (RfFacility rfFacility : facilityList) {
			facilityMap.put(rfFacility.getFacilityCode(), rfFacility);
		}

		f = facilityMap.get(bean.getPadSource());
		if (f != null) {
			bean.getMap().put("padSourceName", f.getFacilityName());
		}
		if (null != bean.getDeviceId()) {
			bean.getMap().put("deviceIp", null != deivce ? deivce.getDeviceIp() : "");
		}
		return this.toPage(request, response, model);
	}
	
	//批量进行设备操作
		@RequestMapping(value = "exportForm")
		public String exportForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
			return this.toPage(request, response, model);
		}
	
	@RequestMapping(value="asyncExport")
	public void asyncExport(HttpServletRequest request,HttpServletResponse response,Model model,ViewPadUser bean,String padControlTime,String expireTime,String padCode2, String exportName,String expireTimeBegin,String expireTimeEnd,String padControlTimeBegin,String padControlTimeEnd,String type,String padCodes,String deviceCodes ,String userMobilePhone, String renewalBeginTimeStr, String renewalEndTimeStr,String deviceStartIp, String deviceEndIp, Integer externalUserId,String taskName)throws Exception{
		taskName = URLDecoder.decode(taskName,"UTF-8");
		if(StringUtils.isNotBlank(bean.getRemark())){
			bean.setRemark(URLDecoder.decode(bean.getRemark(),"UTF-8"));
		}
		if(StringUtils.isNotBlank(bean.getUserPadNameT2())){
			bean.setUserPadNameT2(URLDecoder.decode(bean.getUserPadNameT2(),"UTF-8"));
		}
		if(StringUtils.isNotBlank(bean.getPadName())){
			bean.setPadName(URLDecoder.decode(bean.getPadName(),"UTF-8"));
		}
		
		RfExport export = new RfExport();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bean", bean);
		params.put("padCode2", padCode2);
		params.put("padControlTime", padControlTime);
		params.put("expireTime", expireTime);
		params.put("expireTimeBegin", expireTimeBegin);
		params.put("expireTimeEnd", expireTimeEnd);
		params.put("padControlTimeBegin", padControlTimeBegin);
		params.put("padControlTimeEnd", padControlTimeEnd);
		params.put("type", type);
		params.put("padCodes", padCodes);
		params.put("deviceCodes", deviceCodes);
		params.put("userMobilePhone", userMobilePhone);
		params.put("renewalBeginTimeStr", renewalBeginTimeStr);
		params.put("renewalEndTimeStr", renewalEndTimeStr);
		params.put("deviceStartIp", deviceStartIp);
		params.put("deviceEndIp", deviceEndIp);
		params.put("externalUserId", externalUserId);
		String json = JsonUtils.ObjectToString(params);
		log.info(json);
		
		List<RfExport> rfExportts = exportService.initQuery()
		.andEqualTo("parm", json)
		.andEqualTo("type", ExportConstants.TYPE_PAD)
		.addOrderClause("createTime", "desc")
		.findAll();
		Date now = new Date();
		if(null!=rfExportts&&rfExportts.size()>0&&DateUtils.getDistanceOfTwoDateM(rfExportts.get(0).getCreateTime(), now)<3){
			throw new BusinessException("同搜索条件的导出操作过于频繁");
		}
		
		String baseFilePath = filePathUtils.getFilePath()+"/batchTask/export";
    	String baseFileLinkUrl = filePathUtils.getFileLinkUrl()+"/batchTask/export";
    	String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());				//日作为文件夹
		String dateTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());		//年月日时分秒作为文件名
		int randomNumber = new Random().nextInt(999999);
		createFilePath(baseFilePath + "/" + dateStr);
		String fileName = dateTimeStr + randomNumber+".xls";
		String filePath = baseFilePath + "/" + dateStr + "/" + fileName;//无斜杠结尾
		String fileUrl = baseFileLinkUrl + "/" + dateStr + "/" + fileName;
    	
		export.setPath(filePath);
		export.setUrl(fileUrl);
    	
		export.setParm(json);
		export.setTaskName(taskName);
		export.setType(ExportConstants.TYPE_PAD);//pad类型
		export.setExportStatus(ExportConstants.EXPORT_STATUS_MAKE);//任务创建未执行
		exportService.save(request, export);
		Integer exportId = export.getExportId();
		//exportProducer.setDestination(new ActiveMQQueue("export-task"));
		exportProducer.sendMessage(exportId.toString());
		
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
	
	// 导出
	@RequestMapping(value="export")
	public String export(HttpServletRequest request,HttpServletResponse response,Model model,ViewPadUser bean,String padControlTime,String expireTime,String padCode2, String exportName,String expireTimeBegin,String expireTimeEnd,String padControlTimeBegin,String padControlTimeEnd,String type,String padCodes,String deviceCodes ,String userMobilePhone, String renewalBeginTimeStr, String renewalEndTimeStr,String deviceStartIp, String deviceEndIp, Integer externalUserId)throws Exception{
//		exportHead=StringUtils.removeEnd(exportHead, ",");
//		exportField=StringUtils.removeEnd(exportField, ",");
		
		// 优化了列表，隐藏了部分字段，所以把表头写死。 -- 2017年6月2日17:33:25
		String exportHead = "会员ID,申请来源,设备名称,用户设备名称,设备编码,设备等级,会员手机号,绑定时间,更换时间,最近受控时间,物理设备,控制时间,设备过期时间,MAC,设备IP,物理设备IP,机房,用户控制节点,设备控制节点,设备SN,设备来源,设备imei,备注,维护状态,授权状态,虚拟状态,绑定状态,启用状态,受控状态,故障状态";
		String exportField = "map.externalUserId,padSourceT2,padName,userPadNameT2,padCode,padGradeT2,map.userMobilePhone,bindTimeT2,renewalTimeT2,padControlTimeT2,map.deviceName,map.onlinetime,expireTimeT2,vmMac,padIp,map.deviceIp,map.idcName,map.controlName,map.padControlName,padSn,map.padSourceName,imei,remark,maintStatus,grantOpenStatus,vmStatus,bindStatus,enableStatus,padStatus,faultStatus";
		
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
		String padCode="";
		if(!StringUtils.isEmpty(bean.getPadCode())){
			padCode=bean.getPadCode();
		}
		if(!StringUtils.isEmpty(bean.getRemark())){
			bean.setRemark(java.net.URLDecoder.decode(bean.getRemark(),"UTF-8"));
		}
		if(!StringUtils.isEmpty(bean.getPadName())){
			bean.setPadName(java.net.URLDecoder.decode(bean.getPadName(),"UTF-8"));
		}
		if(!StringUtils.isEmpty(bean.getUserPadNameT2())){
			bean.setUserPadNameT2(java.net.URLDecoder.decode(bean.getUserPadNameT2(),"UTF-8"));
		}
		while(keep){
			bean.setPage(page);
			bean.setRows(1000);
			if(!StringUtils.isEmpty(padCode)){
				bean.setPadCode(padCode);
			}
			PageInfo<ViewPadUser> pageInfo=this.list(request, response, model, bean, padCode2,padControlTime,expireTime, expireTimeBegin,expireTimeEnd, padControlTimeBegin, padControlTimeEnd,type,padCodes,deviceCodes,userMobilePhone, renewalBeginTimeStr, renewalEndTimeStr ,deviceStartIp, deviceEndIp,externalUserId);
			List<ViewPadUser> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
				for(ViewPadUser pad:list){
					pad.setMaintStatus(DictHelper.getLabel("rf_pad.maint_status", pad.getMaintStatus()));
					pad.setBindStatus(DictHelper.getLabel("rf_pad.bind_status", pad.getBindStatus()));
					pad.setEnableStatus(DictHelper.getLabel("rf_pad.enable_status", pad.getEnableStatus()));
					pad.setPadStatus(DictHelper.getLabel("rf_pad.pad_status", pad.getPadStatus()));
					pad.setFaultStatus(DictHelper.getLabel("rf_pad.fault_status", pad.getFaultStatus()));
					pad.setVmStatus(DictHelper.getLabel("rf_pad.vm_status", pad.getVmStatus()));
			        pad.setPadGradeT2(DictHelper.getLabel("rf_user_pad.pad_grade", pad.getPadGradeT2()));
			        if(StringUtils.isNoneEmpty(pad.getVmMac())){//将xx:xx:xx:xx:xx:xx转换为xxxx-xxxx-xxxx
			        	String[] string = pad.getVmMac().split(":");
			        	pad.setVmMac(string[0]+string[1]+"-"+string[2]+string[3]+"-"+string[4]+string[5]);
			        }
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
    
	  // 导出
		@RequestMapping(value="exportTxt")
		public String exportTxt(HttpServletRequest request,HttpServletResponse response,Model model,ViewPadUser bean,String padControlTime,String expireTime,String padCode2, String exportName,String expireTimeBegin,String expireTimeEnd,String padControlTimeBegin,String padControlTimeEnd,String type,String padCodes,String deviceCodes ,String userMobilePhone, String renewalBeginTimeStr, String renewalEndTimeStr,String deviceStartIp, String deviceEndIp, Integer externalUserId)throws Exception{
			
			response.setContentType("text/plain");
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".txt");
		
			// 构建表体
			boolean keep=true;
			int page=1;
			String padCode="";
			if(!StringUtils.isEmpty(bean.getPadCode())){
				padCode=bean.getPadCode();
			}
			 
			if(!StringUtils.isEmpty(bean.getRemark())){
				bean.setRemark(java.net.URLDecoder.decode(bean.getRemark(),"UTF-8"));
			}
			if(!StringUtils.isEmpty(bean.getPadName())){
				bean.setPadName(java.net.URLDecoder.decode(bean.getPadName(),"UTF-8"));
			}
			if(!StringUtils.isEmpty(bean.getUserPadNameT2())){
				bean.setUserPadNameT2(java.net.URLDecoder.decode(bean.getUserPadNameT2(),"UTF-8"));
			}
			StringBuffer write = new StringBuffer();
			BufferedOutputStream buff = new BufferedOutputStream(outputStream); 
			while(keep){
				bean.setPage(page);
				bean.setRows(1000);
				if(!StringUtils.isEmpty(padCode)){
					bean.setPadCode(padCode);
				}
				PageInfo<ViewPadUser> pageInfo=this.list(request, response, model, bean, padCode2,padControlTime,expireTime, expireTimeBegin,expireTimeEnd, padControlTimeBegin, padControlTimeEnd,type,padCodes,deviceCodes,userMobilePhone, renewalBeginTimeStr, renewalEndTimeStr ,deviceStartIp, deviceEndIp,externalUserId);
				List<ViewPadUser> list=pageInfo.getList();
				if(!Collections3.isEmpty(list)){
					
				    String tab = " "; 
					String enter = "\r\n"; 
				    for (int i = 0; i < list.size(); i++) { 
					     ViewPadUser viewPadUser = list.get(i); 
					     write.append("static-bind ip-address "+viewPadUser.getPadIp()); //序号 
					     write.append(tab); 
					     /**格式化Mac */
					     if(StringUtils.isNotEmpty(viewPadUser.getVmMac())){//将xx:xx:xx:xx:xx:xx转换为xxxx-xxxx-xxxx
					        	String[] string = viewPadUser.getVmMac().split(":");
					        	viewPadUser.setVmMac(string[0]+string[1]+"-"+string[2]+string[3]+"-"+string[4]+string[5]);
					     }
					     write.append("mac-address "+ viewPadUser.getVmMac());   
				         write.append(enter); 
				    } 
					if(pageInfo.isHasNextPage()){
						page++;
						continue;
					}
				}
				keep=false;
			 }
			 buff.write(write.toString().getBytes("UTF-8")); 
		     buff.flush(); 
		     buff.close(); 
			
			return null;
	    }  
	
	@RequestMapping(value = "formOnly")
	public String formOnly(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		List<String> padNameList = new ArrayList<String>();
		List<RfPad> padList = service.initQuery().findStopTrue();
		for (RfPad rfPad : padList) {
			padNameList.add(rfPad.getPadName());
		}
		String str = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder builder = new StringBuilder();
		while (true) {
			for (int i = 0; i < 8; i++) {
				builder.append(str.charAt((int) (Math.random() * 26)));
			}
			if (!(padNameList.contains(builder.toString().toUpperCase())))
				break;
		}
		bean.setPadName(builder.toString().toUpperCase());
		List<RfVideo> userVideoList = videoService.initQuery().andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeUser()).findStopTrue();
		List<RfVideo> padVideoList = videoService.initQuery().andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeDevice()).findStopTrue();
		model.addAttribute("userVideoList", userVideoList);
		model.addAttribute("padVideoList", padVideoList);
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeUser()).findStopTrue();
		List<RfControl> padControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		List<RfControl> manageControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeManage()).findStopTrue();
		List<RfIdc> idcList = idcService.initQuery().findDelTrue();
		model.addAttribute("idcList", idcList);
		model.addAttribute("controlList", controlList);
		model.addAttribute("padControlList", padControlList);
		model.addAttribute("manageControlList", manageControlList);
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "stopOnlyForm")
	public String stopOnlyForm(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		if (bean.getPadId() == null) {
			List<String> padNameList = new ArrayList<String>();
			List<RfPad> padList = service.initQuery().findStopTrue();
			for (RfPad rfPad : padList) {
				padNameList.add(rfPad.getPadName());
			}
			String str = "abcdefghijklmnopqrstuvwxyz";
			StringBuilder builder = new StringBuilder();
			while (true) {
				for (int i = 0; i < 8; i++) {
					builder.append(str.charAt((int) (Math.random() * 26)));
				}
				if (!(padNameList.contains(builder.toString().toUpperCase())))
					break;
			}
			bean.setPadName(builder.toString().toUpperCase());
		} else {

			bean = service.get(bean.getPadId());
		}
		List<RfVideo> userVideoList = videoService.initQuery().andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeUser()).findStopTrue();
		List<RfVideo> padVideoList = videoService.initQuery().andEqualTo("videoType", ConstantsDb.rfVideoVideoTypeDevice()).findStopTrue();
		model.addAttribute("userVideoList", userVideoList);
		model.addAttribute("padVideoList", padVideoList);
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType",ConstantsDb.rfControlControlTypeUser()).findStopTrue();
		List<RfControl> padControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		List<RfControl> manageControlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeManage()).findStopTrue();
		List<RfIdc> idcList = idcService.initQuery().findDelTrue();
		model.addAttribute("idcList", idcList);
		model.addAttribute("controlList", controlList);
		model.addAttribute("padControlList", padControlList);
		model.addAttribute("manageControlList", manageControlList);
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	// 新增
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		service.saveNew(request, bean);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		service.update(request, bean);
	}

	@RequestMapping(value = "start")
	public void start(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {

		service.start(request, bean);

	}

	@RequestMapping(value = "stop")
	public void stop(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {

		service.stopPad(request, bean);

	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		service.remove(request, bean);
	}

	@RequestMapping(value = "look")
	public String look(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		bean = service.get(bean.getPadId());
		// 视频
		bean.getMap().put("userVideo", bean.getUserVideoId() == null ? "--" : videoService.get(bean.getUserVideoId()).getVideoName());
		bean.getMap().put("padVideo", bean.getPadVideoId() == null ? "--" : videoService.get(bean.getPadVideoId()).getVideoName());
		// 节点
		bean.getMap().put("userControlName", bean.getUserControlId() == null ? "--" : controlService.get(bean.getUserControlId()).getControlName());
		bean.getMap().put("controlName", bean.getPadControlId() == null ? "--" : controlService.get(bean.getPadControlId()).getControlName());
		bean.getMap().put("idcName", idcService.get(bean.getIdcId()).getIdcName());
		bean.getMap().put("manageControlName", bean.getPadManageControlId() == null ? "--" : controlService.get(bean.getPadManageControlId()).getControlName());
		model.addAttribute("bean", bean);
		return this.toPage(request, response, model);
	}

	// 解绑
	@RequestMapping(value = "relieve")
	public void relieve(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String idStr : idArray) {
			//判断USER_PAD表中的PADID下的数据是否正常
			List<RfUserPad> userPad = userPadService.initQuery().andEqualTo("padId", Integer.parseInt(idStr)).findStopTrue();
			if (userPad.isEmpty())
			{
				userPadService.padUserStatus(request, Integer.parseInt(idStr));
				continue;
			}
			userPadService.relieve(request, userPad.get(0).getUserPadId());
			try{
				//向用户发送jms
				JSONObject sendMessageObj = new JSONObject();
				sendMessageObj.put("opType", "refresh");
				sendMessageObj.put("userId", userPad.get(0).getUserId());
				String sendMessageStr = sendMessageObj.toString();
				log.info("sendMessageStr: "+ sendMessageStr);
				// 发送jms消息
				fingerProducer.sendMessage(sendMessageStr);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "userForm")
	public String userForm(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		RfPad pad = service.get(bean.getPadId());
		/**2017/11/07 增加逻辑判断, 非主营设备不允许绑定 */
		if(!PadClassify.PAD_CLASSIFY_MAIN.equals(pad.getPadClassify())){
			throw new BusinessException("非主营设备不允许绑定");
		}
		model.addAttribute("pad", pad);
		return this.toPage(request, response, model);
	}

	// 绑定
	@RequestMapping(value = "binding")
	public void binding(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean, String mid,String idType) throws Exception {
		if(StringUtils.isBlank(mid)){
			throw new BusinessException("账号不能为空");
		}
		
		RfUser user = null;
		
		if("2".equals(idType)){
			if(org.apache.commons.lang3.math.NumberUtils.isNumber(mid)&&mid.length()<=9){
				user = userService.getUserByExternalUserIdOrUserPhone(null,Integer.valueOf(mid));
			}else{
				throw new BusinessException("账号非法:非纯数字或长度过长");
			}
		}else{
			user = userService.getUser(request, mid);
		}
		
		if (null != user) {
			bean.setUserId(user.getUserId());
			service.bind(request, bean);
			
			try {
				//向用户发送jms
				JSONObject sendMessageObj = new JSONObject();
				sendMessageObj.put("opType", "refresh");
				sendMessageObj.put("userId", user.getUserId());
				String sendMessageStr = sendMessageObj.toString();
				log.info("sendMessageStr: "+ sendMessageStr);
				// 发送jms消息
				fingerProducer.sendMessage(sendMessageStr);
			} catch (Exception e) {
				log.error("binding pad error:"+e.getMessage(), e);
			}
		} else {
			throw new BusinessException("账号输入错误！");
		}
	}

	// 绑定VIP
	@RequestMapping(value = "bindAdmin")
	public void bindAdmin(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,String mid,String goodsId,String idType) throws Exception {
		bindAdminMethod(request, response, model, bean, mid, goodsId,idType);
		
	}
	
	// 绑定GVIP
	@RequestMapping(value = "bindGvipAdmin")
	public void bindGvipAdmin(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,String mid,String goodsId,String idType) throws Exception {
		bindAdminMethod(request, response, model, bean, mid, goodsId,idType);
		
	}
	
	/**
	 * 
	 * 绑定方法
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @param mid 手机/邮箱
	 * @param goodsId 商品套餐id
	 * @param padClassify 设备类型
	 * @throws Exception 
	 */
		public void bindAdminMethod(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,String mid,String goodsId,String idType) throws Exception {
			if(StringUtils.isBlank(mid)){
				throw new BusinessException("账号不能为空");
			}
			
			RfUser user = null;
			if("2".equals(idType)){
				if(org.apache.commons.lang3.math.NumberUtils.isNumber(mid)&&mid.length()<=9){
					user = userService.getUserByExternalUserIdOrUserPhone(null,Integer.valueOf(mid));
				}else{
					throw new BusinessException("账号非法:非纯数字或长度过长");
				}
			}else{
				user = userService.getUser(request, mid);
			}
			
			
			
			if (null != user) {
				bean.setUserId(user.getUserId());
				service.bindAdmin(request, bean,goodsId);
				try{
					//向用户发送jms
					JSONObject sendMessageObj = new JSONObject();
					sendMessageObj.put("opType", "refresh");
					sendMessageObj.put("userId", user.getUserId());
					String sendMessageStr = sendMessageObj.toString();
					log.info("sendMessageStr: "+ sendMessageStr);
					// 发送jms消息
					fingerProducer.sendMessage(sendMessageStr);
				}catch(Exception e){
					e.printStackTrace();
				}
			} else {
				throw new BusinessException("账号输入错误！");
			}
			
		}

	@RequestMapping(value = "adminForm")
	public String adminForm(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		/**根据设备id查询设备类型 */
		RfPad pad = service.get(bean.getPadId());
		//如果设备类别为游戏设备，不允许绑定vip和升级vip
		if(!PadClassify.PAD_CLASSIFY_MAIN.equals(pad.getPadClassify())){
			throw new BusinessException("VIP绑定只允许绑定主营设备");
		}
		return bindGoodsForm(request, response, model, bean);
	}
	
	/***
	 * 
	 * 绑定GVIP设备
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return String
	 */
	@RequestMapping(value = "adminGvipForm")
	public String adminGvipForm(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		/**根据设备id查询设备类型 */
		RfPad pad = service.get(bean.getPadId());
		//如果设备类别为游戏设备，不允许绑定vip和升级vip
		if(!PadClassify.PAD_CLASSIFY_GVIP.equals(pad.getPadClassify())){
			throw new BusinessException("GVIP绑定只允许绑定GVIP设备");
		}
		return bindGoodsForm(request, response, model, bean);
	}
	
	/**
	 * 
	 * 根据设备类型绑定设备套餐
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return String
	 */
	private String bindGoodsForm(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean){
		/**根据设备id查询设备类型 */
		RfPad pad = service.get(bean.getPadId());
		//如果设备类别为游戏设备，不允许绑定vip和升级vip
		if(PadClassify.PAD_CLASSIFY_GAME.equals(pad.getPadClassify())){
			throw new BusinessException("游戏设备不允许绑定");
		// svip设备不允许进行绑定
		}else if(PadClassify.PAD_CLASSIFY_SVIP.equals(pad.getPadClassify())){
			throw new BusinessException("SVIP设备不允许绑定");
		// 非vip和gvip设备
		}else if(!(PadClassify.PAD_CLASSIFY_MAIN.equals(pad.getPadClassify()) || PadClassify.PAD_CLASSIFY_GVIP.equals(pad.getPadClassify()))){
			throw new BusinessException("未知设备类型不允许绑定");
		}
		model.addAttribute("pad", pad);
		// 根据设备类型查询设备套餐
		List<RfGoods> goods=goodsService.initQuery().andEqualTo("goodsType", PadClassify.PAD_CLASSIFY_GVIP.equals(pad.getPadClassify()) ? ConstantsDb.goodsGvip() : ConstantsDb.goodsVip()).findStopTrue();
		if(goods == null || goods.size() == 0){
			throw new BusinessException("该设备类型没有套餐可绑定");
		}
		model.addAttribute("goods", goods);
		return this.toPage(request, response, model);
	}
	
	/** 点击保存,绑定游戏设备 */
	@RequestMapping(value = "bindGame")
	public void bindGame(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,String mid,String gamePadTime) throws Exception {
		if(StringUtils.isBlank(mid)){
			throw new BusinessException("输入的邮箱/手机号不能为空");
		}
		RfUser user = userService.getUser(request, mid);
		if (null != user) {
			bean.setUserId(user.getUserId());
			service.bindGame(request, bean,gamePadTime);
			try{
				//向用户发送jms
				JSONObject sendMessageObj = new JSONObject();
				sendMessageObj.put("opType", "refresh");
				sendMessageObj.put("userId", user.getUserId());
				String sendMessageStr = sendMessageObj.toString();
				log.info("sendMessageStr: "+ sendMessageStr);
				// 发送jms消息
				fingerProducer.sendMessage(sendMessageStr);
			}catch(Exception e){
				e.printStackTrace();
			}
		} else {
			throw new BusinessException("用户手机号或EMAIL输入错误！");
		}
		
	}
	
	/** 绑定游戏设备 */
	@RequestMapping(value = "adminGame")
	public String adminGame(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		RfPad pad = service.get(bean.getPadId());
		/**2017/11/07 更改判断逻辑, 不是游戏设备不允许绑定 */
		if(!PadClassify.PAD_CLASSIFY_GAME.equals(pad.getPadClassify())){//如果设备类别为游戏设备，方可绑定游戏设备
			throw new BusinessException("设备类别不是游戏设备不允许绑定");
		}
		model.addAttribute("pad", pad);
		try {
			SysConfig gamePadTime = configService.selectByConfingCode(CommonPadConfigCode.CONFIG_GAMEPAD_ONLINE_TIME);//游戏设备申请后在线时长
			model.addAttribute("gamePadTime", gamePadTime == null ? null : gamePadTime.getConfigValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.toPage(request, response, model);
	}
	
	/**批量绑定设备页面*/
	@RequestMapping(value = "batchBindVipForm")
	public String batchBindVipForm(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		List<RfGoods> goods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsVip()).findStopTrue();
		model.addAttribute("goods", goods);

		List<RfIdc> idcs = idcService.initQuery().findStopTrue();
		model.addAttribute("idcs", idcs);
		return this.toPage(request, response, model);
	}
	
	/**批量绑定设备*/
	@RequestMapping(value = "batchBindVip")
	public void batchBindVip(HttpServletRequest request, HttpServletResponse response, Model model, 
			RfUserPad bean,String mid,Integer idcId,@ModelAttribute ViewPadGoods viewPadGoods,String idType) throws Exception {
		if(StringUtils.isBlank(mid)){
			throw new BusinessException("账号不能为空");
		}
		
		RfUser user = null;
		if("2".equals(idType)){
			if(org.apache.commons.lang3.math.NumberUtils.isNumber(mid)&&mid.length()<=9){
				user = userService.getUserByExternalUserIdOrUserPhone(null,Integer.valueOf(mid));
			}else{
				throw new BusinessException("账号非法:非纯数字或长度过长");
			}
		}else{
			user = userService.getUser(request, mid);
		}
		
		
		if(null == idcId){
			throw new BusinessException("请选择机房！");
		}
		if (null != user) {
			bean.setUserId(user.getUserId());
			service.batchBindVip(request, bean, idcId, viewPadGoods.getPadGoods());
			try{
				//向用户发送jms
				JSONObject sendMessageObj = new JSONObject();
				sendMessageObj.put("opType", "refresh");
				sendMessageObj.put("userId", user.getUserId());
				String sendMessageStr = sendMessageObj.toString();
				log.info("sendMessageStr: "+ sendMessageStr);
				// 发送jms消息
				fingerProducer.sendMessage(sendMessageStr);
			}catch(Exception e){
				e.printStackTrace();
			}
		} else {
			throw new BusinessException("账号输入错误！");
		}
	}

	// active 激活设备
	@RequestMapping(value = "active")
	public void active(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		service.active(request, bean);
	}

	// active冻结设备
	@RequestMapping(value = "freeze")
	public void freeze(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		service.freeze(request, bean);
	}

	@RequestMapping(value="change")
	public String change(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<RfIdc> idcList = idcService.initQuery().findStopTrue();
		model.addAttribute("idcList", idcList);
		List<RfControl> controlList = controlService.initQuery().andEqualTo("controlType", ConstantsDb.rfControlControlTypeDevice()).findStopTrue();
		model.addAttribute("controlList", controlList);
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value="changeList")
	public PageInfo<ViewPadUser>  changeList(HttpServletRequest request,HttpServletResponse response,Model model, RfPad bean, String padControlTime,String padCode2){
			String likePadCode=null;
			if(!StringUtils.isEmpty(padCode2)){
				likePadCode=bean.getPadCode();
				bean.setPadCode(null);
			}
			// 查询条件
			List<ViewPadUser> list = viewPadUserService.initQuery(bean)
					.andGreaterThanOrEqualTo("bindTimeT2", DateUtils.parseDate(bean.getBeginTimeStr()))
					.andLessThan("bindTimeT2", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
					.andEqualTo("padControlId", bean.getPadControlId())
					.andEqualTo("padStatus", bean.getPadStatus())
					.andEqualTo("bindStatus", bean.getBindStatus())
					.andEqualTo("enableStatus", bean.getEnableStatus())
					.andEqualTo("faultStatus", bean.getFaultStatus())
					.andEqualTo("idcId", bean.getIdcId())
					.andLike("padCode", bean.getPadCode())
					.andGreaterThanOrEqualTo("padCode",likePadCode)
					.andLessThanOrEqualTo("padCode", padCode2)
					.andLike("padIp", bean.getPadIp())
					.andLessThanOrEqualTo("padControlTimeT2", DateUtils.parseDate(padControlTime))
					.addOrderClause("padId", "asc")
					.addOrderForce(bean.getSort(), bean.getOrder())
					.pageDelTrue(bean.getPage(), bean.getRows());
			for (ViewPadUser viewPadUser : list) {
				viewPadUser.getMap().put("userMobilePhone", viewPadUser.getUserIdT2()==null?"--":userService.load(viewPadUser.getUserIdT2()).getUserMobilePhone());
				viewPadUser.getMap().put("idcName", viewPadUser.getIdcId() == null ? "--" : idcService.load(viewPadUser.getIdcId()).getIdcName());
				viewPadUser.getMap().put("controlName", viewPadUser.getUserControlId() == null ? "--" : controlService.load(viewPadUser.getUserControlId()).getControlName());
				viewPadUser.getMap().put("padControlName", viewPadUser.getPadControlId() == null ? "--" : controlService.load(viewPadUser.getPadControlId()).getControlName());
				viewPadUser.getMap().put("manageControlName", viewPadUser.getPadManageControlId() == null ? "--" : controlService.load(viewPadUser.getPadManageControlId()).getControlName());
			}
			PageInfo<ViewPadUser> pageInfo = new PageInfo<ViewPadUser>(list);
			return pageInfo;
		}
		
	
	@RequestMapping(value="changeNew")
	public void changeNew(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		service.changeNew(request, bean);
	}
	@RequestMapping(value="upVIP")
	public void upVIP(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean,String goodsId) throws Exception {
		RfPad pad = service.get(bean.getPadId());
		if(!PadClassify.PAD_CLASSIFY_MAIN.equals(pad.getPadClassify())){
			throw new BusinessException("非主营设备不允许升级VIP");
		}
		List<RfUserPad> userPad = userPadService.initQuery().andEqualTo("padId", bean.getPadId()).findStopTrue();
		if (Collections3.isEmpty(userPad)){
			userPadService.padUserStatus(request, bean.getPadId());
			throw new BusinessException("该设备没有绑定记录");
		}
		userPadService.upVIP(request, userPad.get(0).getUserPadId(),goodsId);
		
		//向用户发送jms
		JSONObject sendMessageObj = new JSONObject();
		sendMessageObj.put("opType", "refresh");
		sendMessageObj.put("userId", userPad.get(0).getUserId());
		String sendMessageStr = sendMessageObj.toString();
		log.info("sendMessageStr: "+ sendMessageStr);
		// 发送jms消息
		fingerProducer.sendMessage(sendMessageStr);
	}
	@RequestMapping(value = "upVIPForm")
	public String upVIPForm(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		RfPad pad = service.get(bean.getPadId());
		/**2017/11/07 更改为非主营设备不允许升级为VIP */
		if(!PadClassify.PAD_CLASSIFY_MAIN.equals(pad.getPadClassify())){//如果设备类别为游戏设备，不允许绑定vip和升级vip
			throw new BusinessException("非主营设备不允许升级vip");
		}
		model.addAttribute("pad", pad);
		List<RfGoods> goods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsVip()).findStopTrue();
		model.addAttribute("goods", goods);
		return this.toPage(request, response, model);
	}
	
	//管理员绑定页面
	@RequestMapping(value = "adminBindingForm")
	public String adminBindingForm(HttpServletRequest request, HttpServletResponse response, Model model, String padIds) throws Exception {
		List<Integer> padIdList = new ArrayList<Integer>();
		for (String padIdStr : padIds.split(",")) {
			padIdList.add(Integer.parseInt(padIdStr));
		}
		List<RfPad> padList = service.initQuery().andIn("padId",padIdList).findAll();
		/*boolean isMain = false;
		boolean isGame = false;
		for(RfPad rfPad : padList){//判断是否同时存在主营设备和游戏设备//TODO
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
		model.addAttribute("padList", padList);
		List<RfGoods> goods=goodsService.initQuery().andEqualTo("goodsType", ConstantsDb.goodsVip()).findStopTrue();
		model.addAttribute("goods", goods);
		return this.toPage(request, response, model);
	}
	// 管理员绑定
	@RequestMapping(value = "adminbinding")
	public void adminbinding(HttpServletRequest request, HttpServletResponse response, Model model, String mid, String goodsId, Integer[] padId,String padClassify,Integer gamePadOnlineTime) throws Exception {
		RfUser user = userService.getUser(request, mid);
		if (null != user) {
			
			for (Integer pid : padId) {
				RfUserPad bean = new RfUserPad();
				bean.setUserId(user.getUserId());
				bean.setPadId(pid);
				service.adminBinding(request, bean,goodsId, padClassify, gamePadOnlineTime);
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
	
	
	// 管理员解绑
	@RequestMapping(value = "adminrelieve")
	public void adminrelieve(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String idStr : idArray) {
			//判断USER_PAD表中的PADID下的数据是否正常
			List<RfUserPad> userPad = userPadService.initQuery().andEqualTo("padId", Integer.parseInt(idStr)).findStopTrue();
			if (userPad.isEmpty())
			{
				userPadService.padUserStatus(request, Integer.parseInt(idStr));
				continue;
			}
			userPadService.adminrelieve(request, userPad.get(0).getUserPadId());
			
			try{
				//向用户发送jms
				JSONObject sendMessageObj = new JSONObject();
				sendMessageObj.put("opType", "refresh");
				sendMessageObj.put("userId", userPad.get(0).getUserId());
				String sendMessageStr = sendMessageObj.toString();
				log.info("sendMessageStr: "+ sendMessageStr);
				// 发送jms消息
				fingerProducer.sendMessage(sendMessageStr);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	// VIP解绑
	@RequestMapping(value = "relievevip")
	public void relievevip(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		String[] idArray = StringUtils.split(bean.getIds(), ",");
		for (String idStr : idArray) {
			//判断USER_PAD表中的PADID下的数据是否正常
			List<RfUserPad> userPad = userPadService.initQuery().andEqualTo("padId", Integer.parseInt(idStr)).findStopTrue();
			if (userPad.isEmpty())
			{
				userPadService.padUserStatus(request, Integer.parseInt(idStr));
				continue;
			}
			userPadService.relievevip(request, userPad.get(0).getUserPadId());
			
			try{
				//向用户发送jms
				JSONObject sendMessageObj = new JSONObject();
				sendMessageObj.put("opType", "refresh");
				sendMessageObj.put("userId", userPad.get(0).getUserId());
				String sendMessageStr = sendMessageObj.toString();
				log.info("sendMessageStr: "+ sendMessageStr);
				// 发送jms消息
				fingerProducer.sendMessage(sendMessageStr);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	// VIP解绑
		@RequestMapping(value = "relieveGvip")
		public void relieveGvip(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
			String[] idArray = StringUtils.split(bean.getIds(), ",");
			for (String idStr : idArray) {
				//判断USER_PAD表中的PADID下的数据是否正常
				List<RfUserPad> userPad = userPadService.initQuery().andEqualTo("padId", Integer.parseInt(idStr)).findStopTrue();
				if (userPad.isEmpty())
				{
					userPadService.padUserStatus(request, Integer.parseInt(idStr));
					continue;
				}
				userPadService.relieveGvip(request, userPad.get(0).getUserPadId());
				
				try{
					//向用户发送jms
					JSONObject sendMessageObj = new JSONObject();
					sendMessageObj.put("opType", "refresh");
					sendMessageObj.put("userId", userPad.get(0).getUserId());
					String sendMessageStr = sendMessageObj.toString();
					log.info("sendMessageStr: "+ sendMessageStr);
					// 发送jms消息
					fingerProducer.sendMessage(sendMessageStr);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	
	//修改在线时间
	@RequestMapping(value="onlinetime")
	public void onlinetime(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,Integer onlinetime) throws Exception {
		if(bean==null){
			throw new BusinessException("输入了错误的参数！");
		}
		bean=userPadService.get(bean.getUserPadId());
		if(null!=bean.getLeftControlTime()&&!("".equals(bean.getLeftControlTime()))){
		if(bean.getLeftControlTime()+onlinetime<0){
			throw new BusinessException("扣除时间不能大于现有时间！");
		}	bean.setLeftControlTime(bean.getLeftControlTime()+onlinetime);
		}else{
			bean.setLeftControlTime(onlinetime);
		}
		userPadService.update(request, bean);
	}
	
	//修改控制天
	@RequestMapping(value="controltime")
	public void controltime(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean,String timeType,Integer controltime) throws Exception {
		Date time=new Date();
		if(bean==null){
			throw new BusinessException("输入了错误的参数！");
		}
		if(StringUtils.isBlank(timeType)){
			throw new BusinessException("选择时间类型！");
		}
		bean=userPadService.get(bean.getUserPadId());
		
		if(StringUtils.equals(ConstantsDb.timeTypeDay(), timeType)){//如果是天
			if(null!=bean.getExpireTime()&&!("".equals(bean.getExpireTime()))){
				if(DateUtils.getDistanceOfTwoDate(time, bean.getExpireTime())+controltime<0){
					throw new BusinessException("扣除时间不能大于现有天数！");
				}
				bean.setExpireTime(DateUtils.addDays(bean.getExpireTime(),controltime));
				bean.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDate(time, bean.getExpireTime())));
			}else{
				bean.setExpireTime(DateUtils.addDays(new Date(),controltime));
				bean.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDate(time, bean.getExpireTime())));
			}
		}else if(StringUtils.equals(ConstantsDb.timeTypeHour(), timeType)){//如果是小时
			if(null!=bean.getExpireTime()&&!("".equals(bean.getExpireTime()))){
				if(DateUtils.getDistanceOfTwoDateH(time, bean.getExpireTime())+controltime<0){
					throw new BusinessException("扣除时间不能大于现有小时数！");
				}
				bean.setExpireTime(DateUtils.addHours(bean.getExpireTime(),controltime));
				bean.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDateH(time, bean.getExpireTime())));
			}else{
				bean.setExpireTime(DateUtils.addHours(new Date(),controltime));
				bean.setLeftOnlineTime(StringUtils.toInteger(DateUtils.getDistanceOfTwoDateH(time, bean.getExpireTime())));
			}
		}
		
		userPadService.update(request, bean);
	}
	
	@RequestMapping(value = "controltimeForm")
	public String controltimeForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
		long currentTime = System.currentTimeMillis();
		List<ViewPadUser> userPad = viewPadUserService.initQuery().andEqualTo("padId",bean.getPadId()).findStopTrue();
		if (userPad.isEmpty()){
			userPadService.padUserStatus(request,bean.getPadId());
		} else {
			userPadService.padUserStatus(request,bean.getPadId());
			Date expireTime=userPad.get(0).getExpireTimeT2();
			if(null!= expireTime&&!("".equals(expireTime))){
				model.addAttribute("controltime", DateUtils.formatDateTime2(expireTime.getTime()-currentTime));
			
			}
			RfUser user=userService.get(userPad.get(0).getUserIdT2());
			if(user==null){
				throw new BusinessException("没有查到该会员帐号记录！");
			}
				if (null != user.getUserMobilePhone()) {
					userPad.get(0).getMap().put("userPhone", user.getUserMobilePhone());
				}
				if (null != user.getUserEmail()) {
					userPad.get(0).getMap().put("userEmail", user.getUserEmail());
				}
			model.addAttribute("bean",userPad.get(0));
		}

		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "onlinetimeForm")
	public String onlinetimeForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
		long currentTime = System.currentTimeMillis();
		List<ViewPadUser> userPad = viewPadUserService.initQuery().andEqualTo("padId",bean.getPadId()).findStopTrue();
		if (userPad.isEmpty()){
			userPadService.padUserStatus(request,bean.getPadId());
		} else {
			userPadService.padUserStatus(request,bean.getPadId());
			Date expireTime=userPad.get(0).getExpireTimeT2();
			if(null!= expireTime&&!("".equals(expireTime))){
				model.addAttribute("controltime", DateUtils.formatDateTime2(expireTime.getTime()-currentTime));
				
			}
		RfUser user=userService.get(userPad.get(0).getUserIdT2());
		if(user==null){
			throw new BusinessException("没有查到该会员帐号记录！");
		}
			if (null != user.getUserMobilePhone()) {
				userPad.get(0).getMap().put("userPhone", user.getUserMobilePhone());
			}
			if (null != user.getUserEmail()) {
				userPad.get(0).getMap().put("userEmail", user.getUserEmail());
			}

			model.addAttribute("bean",userPad.get(0));
		}

		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "floatData")
	public Map<String,Integer> floatDate(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception{
		List<RfFacility> facilityList = facilityService.initQuery().findStopTrue();
		List<String> facilityCodes = new ArrayList<String>();
		for (RfFacility rfFacility : facilityList) {
			facilityCodes.add(rfFacility.getFacilityCode());
		}
		// 设备池禁用的来源
		List<String> disableFacilityCodes = Lists.newArrayList();
		facilityList = facilityService.initQuery().andNotEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart()).findDelTrue();
		for (RfFacility rfFacility : facilityList) {
			disableFacilityCodes.add(rfFacility.getFacilityCode());
		}
		
		Map<String,Integer> map=Maps.newHashMap();
		Date time=new Date();
		//当日到期设备
		Integer todayExpire=viewPadUserService.initQuery().andGreaterThanOrEqualTo("expireTimeT2", DateUtils.parseDate(DateUtils.getDate(),"yyyy-MM-dd")).andLessThanOrEqualTo("expireTimeT2", DateUtils.parseDateAddOneDay(DateUtils.formatDate(time))).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("todayExpire", todayExpire);
		//当日到期普通
		Integer todayExpire1=viewPadUserService.initQuery().andGreaterThanOrEqualTo("expireTimeT2", DateUtils.parseDate(DateUtils.getDate(),"yyyy-MM-dd")).andLessThanOrEqualTo("expireTimeT2", DateUtils.parseDateAddOneDay(DateUtils.formatDate(time))).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("todayExpire1", todayExpire1);
		//当日到期VIP	
		Integer todayExpire2=viewPadUserService.initQuery().andGreaterThanOrEqualTo("expireTimeT2", DateUtils.parseDate(DateUtils.getDate(),"yyyy-MM-dd")).andLessThanOrEqualTo("expireTimeT2", DateUtils.parseDateAddOneDay(DateUtils.formatDate(time))).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeVip()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("todayExpire2", todayExpire2);
		//当日到期sVIP	
		Integer todayExpire3=viewPadUserService.initQuery().andGreaterThanOrEqualTo("expireTimeT2", DateUtils.parseDate(DateUtils.getDate(),"yyyy-MM-dd")).andLessThanOrEqualTo("expireTimeT2", DateUtils.parseDateAddOneDay(DateUtils.formatDate(time))).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradesVip()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("todayExpire3", todayExpire3);
		//当日到期gVIP	
		Integer todayExpire4=viewPadUserService.initQuery().andGreaterThanOrEqualTo("expireTimeT2", DateUtils.parseDate(DateUtils.getDate(),"yyyy-MM-dd")).andLessThanOrEqualTo("expireTimeT2", DateUtils.parseDateAddOneDay(DateUtils.formatDate(time))).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGvip()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("todayExpire4", todayExpire4);
		//当日到期体验
		Integer todayExpire5=viewPadUserService.initQuery().andGreaterThanOrEqualTo("expireTimeT2", DateUtils.parseDate(DateUtils.getDate(),"yyyy-MM-dd")).andLessThanOrEqualTo("expireTimeT2", DateUtils.parseDateAddOneDay(DateUtils.formatDate(time))).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeTaste()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("todayExpire5", todayExpire5);
		
		// a、当前已到期设备数量
		Integer floatExpire=viewPadUserService.initQuery().andLessThan("expireTimeT2", time).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("floatExpire", floatExpire);
		// 当前已到期普通设备在线
	    Integer floatExpire3=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andLessThan("expireTimeT2",time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("floatExpire3", floatExpire3);
		// 当前已到期普通设备离线
	    //Integer floatExpire4=viewPadUserService.initQuery().andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andLessThan("expireTimeT2",time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).countDelTrue();
		ViewPadUserExample floatExpire4Example = new ViewPadUserExample();
		floatExpire4Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andExpireTimeT2LessThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGeneral()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		floatExpire4Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andExpireTimeT2LessThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGeneral()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
	   
		Integer floatExpire4 = viewPadUserService.countByExample(floatExpire4Example);
		map.put("floatExpire4", floatExpire4);
		
		// 当前已到期svip设备在线
	    Integer floatExpire6=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andLessThan("expireTimeT2",time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradesVip()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("floatExpire6", floatExpire6);
		// 当前已到期svip设备离线
		ViewPadUserExample floatExpire7Example = new ViewPadUserExample();
		floatExpire7Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andExpireTimeT2LessThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradesVip()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		floatExpire7Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andExpireTimeT2LessThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradesVip()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
	   
		Integer floatExpire7 = viewPadUserService.countByExample(floatExpire7Example);
		map.put("floatExpire7", floatExpire7);
		
		// 当前已到期gvip设备在线
		Integer floatExpire8=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andLessThan("expireTimeT2",time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGvip()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("floatExpire8", floatExpire8);
		// 当前已到期gvip设备离线
		ViewPadUserExample floatExpire9Example = new ViewPadUserExample();
		floatExpire9Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andExpireTimeT2LessThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGvip()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		floatExpire9Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andExpireTimeT2LessThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGvip()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		
		Integer floatExpire9 = viewPadUserService.countByExample(floatExpire9Example);
		map.put("floatExpire9", floatExpire9);
		// 当前已到期体验设备在线
		Integer floatExpire10=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andLessThan("expireTimeT2",time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeTaste()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("floatExpire10", floatExpire10);
		// 当前已到期体验设备离线
		ViewPadUserExample floatExpire11Example = new ViewPadUserExample();
		floatExpire11Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andExpireTimeT2LessThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeTaste()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		floatExpire11Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andExpireTimeT2LessThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeTaste()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		
		Integer floatExpire11 = viewPadUserService.countByExample(floatExpire11Example);
		map.put("floatExpire11", floatExpire11);
		
		
	    //当前已到期VIP设备 到期的所有设备减去到期的普通设备（在线）
		Integer floatExpire2=viewPadUserService.initQuery().andLessThan("expireTimeT2",  time).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeVip()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("floatExpire2", floatExpire2);
	    //当前已到期VIP设备 到期的所有设备减去到期的普通设备（离线）
		//Integer floatExpire5=viewPadUserService.initQuery().andLessThan("expireTimeT2",  time).andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countDelTrue()-floatExpire4;
		ViewPadUserExample floatExpire5Example = new ViewPadUserExample();
		floatExpire5Example.or().andExpireTimeT2LessThan(time).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeVip()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
		floatExpire5Example.or().andExpireTimeT2LessThan(time).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeVip()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
		
		
		Integer floatExpire5 = viewPadUserService.countByExample(floatExpire5Example);
		map.put("floatExpire5", floatExpire5);
		//可绑定的离线设备  （启用，未绑定，无故障，离线）
		//Integer  floatEnable3=service.initQuery().andIn("padSource", facilityCodes).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusNomarl()).andEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusNobind()).andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countDelTrue();
//		 
	     RfPadExample floatEnable3example = new RfPadExample();
	     floatEnable3example.or().andPadSourceIn(facilityCodes).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusNomarl()).andEnableStatusEqualTo(ConstantsDb.rfPadEnableStatusStart()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusNobind()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andPadStatusIsNotNull().andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	     floatEnable3example.or().andPadSourceIn(facilityCodes).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusNomarl()).andEnableStatusEqualTo(ConstantsDb.rfPadEnableStatusStart()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusNobind()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andPadStatusIsNotNull().andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	    
	     Integer floatEnable3 = service.countByExample(floatEnable3example);
	     
		map.put("floatEnable3", floatEnable3);
		//可绑定的设备（启用，未绑定，无故障，在线）
		Integer  floatEnable2=service.initQuery().andIn("padSource", facilityCodes).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusNomarl()).andEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusNobind()).andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOnline()).andEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOnline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("floatEnable2", floatEnable2);
		//禁用在线设备
		 Integer floatUnable2=service.initQuery().andNotEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOnline()).andNotIn("padSource", disableFacilityCodes).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		 Integer padPoolEnable2 = 0;
		 if(disableFacilityCodes != null && disableFacilityCodes.size() > 0){
			 padPoolEnable2 = service.initQuery().andIn("padSource", disableFacilityCodes).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOnline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		 }
		 map.put("floatUnable2", floatUnable2+padPoolEnable2);
		 //禁用离线设备
		 //Integer floatUnable3=service.initQuery().andNotEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart()).andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countDelTrue();
		 RfPadExample example = new RfPadExample();
		 Criteria or1 = example.or();
		 Criteria or2 =example.or();
		 
		 Integer padPoolEnable3 = 0;
		 if(disableFacilityCodes != null && disableFacilityCodes.size() > 0){
			 or1.andPadSourceNotIn(disableFacilityCodes);
			 or2.andPadSourceNotIn(disableFacilityCodes);
			 RfPadExample example2 = new RfPadExample();
			 example2.or().andPadSourceIn(disableFacilityCodes).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
			 example2.or().andPadSourceIn(disableFacilityCodes).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
			 padPoolEnable3 = service.countByExample(example2);
		 }
		 or1.andEnableStatusNotEqualTo(ConstantsDb.rfPadEnableStatusStart()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull( bean.getIdcId()).andPadClassifyEqualToIgnoreNull( bean.getPadClassify());
		 or2.andEnableStatusNotEqualTo(ConstantsDb.rfPadEnableStatusStart()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull( bean.getIdcId()).andPadClassifyEqualToIgnoreNull( bean.getPadClassify());
		 Integer floatUnable3 = service.countByExample(example);
		 map.put("floatUnable3", floatUnable3+padPoolEnable3);
	     //离线的故障设备普通
		 //Integer floatFault4=viewPadUserService.initQuery().andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusFault()).andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countDelTrue();
		 ViewPadUserExample floatFault4Example = new ViewPadUserExample();
		 floatFault4Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGeneral()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 floatFault4Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGeneral()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 
		 Integer floatFault4=viewPadUserService.countByExample(floatFault4Example);
		 map.put("floatFault4", floatFault4);
		 //在线的故障设备普通
		 Integer floatFault5=viewPadUserService.initQuery().andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusFault()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		 map.put("floatFault5", floatFault5);
		//离线的故障设备VIP
		//Integer floatFault3=viewPadUserService.initQuery().andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeVip()).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusFault()).andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countDelTrue();
		 ViewPadUserExample floatFault3Example = new ViewPadUserExample();
		 floatFault3Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeVip()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 floatFault3Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeVip()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 
		 
		 Integer floatFault3 = viewPadUserService.countByExample(floatFault3Example);
		 map.put("floatFault3", floatFault3);
		//在线的故障设备VIP
		Integer floatFault2=viewPadUserService.initQuery().andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeVip()).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusFault()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		 map.put("floatFault2", floatFault2);
		 
		 //在线的故障设备svIP
		 Integer floatFault6=viewPadUserService.initQuery().andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradesVip()).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusFault()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		 map.put("floatFault6", floatFault6);
		//离线的故障设备sVIP
		 ViewPadUserExample floatFault7Example = new ViewPadUserExample();
		 floatFault7Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradesVip()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 floatFault7Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradesVip()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 
		 Integer floatFault7 = viewPadUserService.countByExample(floatFault7Example);
		 map.put("floatFault7", floatFault7);
		 //在线的故障设备gvIP
		 Integer floatFault8=viewPadUserService.initQuery().andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGvip()).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusFault()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		 map.put("floatFault8", floatFault8);
		 //离线的故障设备gVIP
		 ViewPadUserExample floatFault9Example = new ViewPadUserExample();
		 floatFault9Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGvip()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 floatFault9Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGvip()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 
		 Integer floatFault9 = viewPadUserService.countByExample(floatFault9Example);
		 map.put("floatFault9", floatFault9);
		 //在线的故障设备体验设备
		 Integer floatFault10=viewPadUserService.initQuery().andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeTaste()).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusFault()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		 map.put("floatFault10", floatFault10);
		 //离线的故障设备体验设备
		 ViewPadUserExample floatFault11Example = new ViewPadUserExample();
		 floatFault11Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeTaste()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 floatFault11Example.or().andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeTaste()).andFaultStatusEqualTo(ConstantsDb.rfPadFaultStatusFault()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		 
		 Integer floatFault11 = viewPadUserService.countByExample(floatFault11Example);
		 map.put("floatFault11", floatFault11);
		 
		//故障设备总数
		 Integer floatFault=service.initQuery().andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusFault()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		 map.put("floatFault", floatFault);
		 //totality 设备总数
		Integer floatTotal=service.initQuery().andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue(); 
		 map.put("floatTotal", floatTotal);
		//离线设备数量
		//Integer floatTotal3=service.initQuery().andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countDelTrue();
		RfPadExample floatTotal3Example = new RfPadExample();
		floatTotal3Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
		floatTotal3Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
		
		Integer floatTotal3 = service.countByExample(floatTotal3Example);
		map.put("floatTotal3", floatTotal3);
		//在线设备数量
		Integer floatTotal2=floatTotal-floatTotal3;
		map.put("floatTotal2", floatTotal2);
		//可用设备数量
		Integer floatEnable=service.initQuery().andIn("padSource", facilityCodes).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusNobind()).andEqualTo("faultStatus", ConstantsDb.rfPadFaultStatusNomarl()).andEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart()).andIsNotNull("padStatus").andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("floatEnable", floatEnable);
		//禁用设备数量
		Integer floatUnable=service.initQuery().andNotEqualTo("enableStatus", ConstantsDb.rfPadEnableStatusStart()).andNotIn("padSource", disableFacilityCodes).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		// 设备池禁用的数量
		Integer padPoolEnable = 0;
		if(disableFacilityCodes != null && disableFacilityCodes.size() > 0){
			padPoolEnable = service.initQuery().andIn("padSource", disableFacilityCodes).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		}
		map.put("floatUnable", (floatUnable+padPoolEnable));
		//未到期设备总数
		Integer noExpire=viewPadUserService.initQuery().andGreaterThan("expireTimeT2", time).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("noExpire", noExpire);
		//未到期设备VIP在线
		Integer noExpire1=viewPadUserService.initQuery().andGreaterThan("expireTimeT2", time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeVip()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("noExpire1", noExpire1);
		//未到期设备VIP离线
		//Integer noExpire2=viewPadUserService.initQuery().andGreaterThan("expireTimeT2", time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeVip()).andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countDelTrue();
		ViewPadUserExample noExpire2Example = new ViewPadUserExample();
		noExpire2Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeVip()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		noExpire2Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeVip()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		
		Integer noExpire2 = viewPadUserService.countByExample(noExpire2Example);
		map.put("noExpire2", noExpire2);
		//未到期设备普通在线
		Integer noExpire3=viewPadUserService.initQuery().andGreaterThan("expireTimeT2", time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("noExpire3", noExpire3);
		//未到期设备普通离线
		//Integer noExpire4=viewPadUserService.initQuery().andGreaterThan("expireTimeT2", time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countDelTrue();
		ViewPadUserExample noExpire4Example= new ViewPadUserExample();
		noExpire4Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGeneral()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		noExpire4Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGeneral()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		
		Integer noExpire4 = viewPadUserService.countByExample(noExpire4Example);
		map.put("noExpire4", noExpire4);
		
		//未到期设备svip在线
		Integer noExpire5=viewPadUserService.initQuery().andGreaterThan("expireTimeT2", time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradesVip()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("noExpire5", noExpire5);
		//未到期设备svip离线
		ViewPadUserExample noExpire6Example= new ViewPadUserExample();
		noExpire6Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradesVip()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		noExpire6Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradesVip()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		
		Integer noExpire6 = viewPadUserService.countByExample(noExpire6Example);
		map.put("noExpire6", noExpire6);
		//未到期设备gvip在线
		Integer noExpire7=viewPadUserService.initQuery().andGreaterThan("expireTimeT2", time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGvip()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("noExpire7", noExpire7);
		//未到期设备svip离线
		ViewPadUserExample noExpire8Example= new ViewPadUserExample();
		noExpire8Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGvip()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		noExpire8Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGvip()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		
		Integer noExpire8 = viewPadUserService.countByExample(noExpire8Example);
		map.put("noExpire8", noExpire8);
		
		//未到期设备体验设备在线
		Integer noExpire9=viewPadUserService.initQuery().andGreaterThan("expireTimeT2", time).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeTaste()).andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
		map.put("noExpire9", noExpire9);
		//未到期设备体验设备离线
		ViewPadUserExample noExpire10Example= new ViewPadUserExample();
		noExpire10Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeTaste()).andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		noExpire10Example.or().andExpireTimeT2GreaterThan(time).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeTaste()).andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
		
		Integer noExpire10 = viewPadUserService.countByExample(noExpire10Example);
		map.put("noExpire10", noExpire10);
		
		
		
		//绑定设备总数
		Integer	bindingPad4=viewPadUserService.initQuery().andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
	    map.put("bindingPad4", bindingPad4);
		//绑定设备在线VIP
		Integer	bindingPad=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeVip()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
	    map.put("bindingPad", bindingPad);
	    //绑定设备离线VIP
	    //Integer	bindingPad1=viewPadUserService.initQuery().andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeVip()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).countDelTrue();
	    ViewPadUserExample bindingPad1Example = new ViewPadUserExample();
	    bindingPad1Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeVip()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
	    bindingPad1Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeVip()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());
	    
	    Integer	bindingPad1 = viewPadUserService.countByExample(bindingPad1Example);
	    map.put("bindingPad1", bindingPad1);
		//绑定设备在线普通
	    Integer	bindingPad2=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
	    map.put("bindingPad2", bindingPad2);
		//绑定设备离线普通
	    //Integer	bindingPad3=viewPadUserService.initQuery().andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGeneral()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).countDelTrue();
	    ViewPadUserExample bindingPad3Example = new ViewPadUserExample();
	    bindingPad3Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGeneral()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	    bindingPad3Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGeneral()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	  
	    Integer	bindingPad3 = viewPadUserService.countByExample(bindingPad3Example);
	    map.put("bindingPad3", bindingPad3);
	    
	    //绑定设备在线SVIP
	    Integer	bindingPad5=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradesVip()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
	    map.put("bindingPad5", bindingPad5);
	    //绑定设备离线SVIP
	    ViewPadUserExample bindingPad6Example = new ViewPadUserExample();
	    bindingPad6Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradesVip()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	    bindingPad6Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradesVip()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	  
	    Integer	bindingPad6 = viewPadUserService.countByExample(bindingPad6Example);
	    map.put("bindingPad6", bindingPad6);
	    
	    //绑定设备在线GVIP
	    Integer	bindingPad7=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeGvip()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
	    map.put("bindingPad7", bindingPad7);
	    //绑定设备离线GVIP
	    ViewPadUserExample bindingPad8Example = new ViewPadUserExample();
	    bindingPad8Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGvip()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	    bindingPad8Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeGvip()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	    
	    Integer	bindingPad8 = viewPadUserService.countByExample(bindingPad8Example);
	    map.put("bindingPad8", bindingPad8);
	    //绑定设备在线体验
	    Integer	bindingPad9=viewPadUserService.initQuery().andNotEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).andNotEqualTo("vmStatus", ConstantsDb.rfPadVmStatusOffline()).andEqualTo("padGradeT2", ConstantsDb.rfUserPadPadGradeTaste()).andEqualTo("bindStatus", ConstantsDb.rfPadBindStatusBind()).andEqualTo("idcId", bean.getIdcId()).andEqualTo("padClassify", bean.getPadClassify()).countDelTrue();
	    map.put("bindingPad9", bindingPad9);
	    //绑定设备离线体验
	    ViewPadUserExample bindingPad10Example = new ViewPadUserExample();
	    bindingPad10Example.or().andPadStatusEqualTo(ConstantsDb.rfPadPadStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeTaste()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	    bindingPad10Example.or().andVmStatusEqualTo(ConstantsDb.rfPadVmStatusOffline()).andPadGradeT2EqualTo(ConstantsDb.rfUserPadPadGradeTaste()).andBindStatusEqualTo(ConstantsDb.rfPadBindStatusBind()).andStatusEqualTo(ConstantsDb.globalStatusNomarl()).andIdcIdEqualToIgnoreNull(bean.getIdcId()).andPadClassifyEqualToIgnoreNull(bean.getPadClassify());;
	    
	    Integer	bindingPad10 = viewPadUserService.countByExample(bindingPad10Example);
	    map.put("bindingPad10", bindingPad10);
		
		return map;
	}
	
	@RequestMapping(value = "controlsForm")
	public String controlsForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "onlinesForm")
	public String onlinesForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	//批量修改在线时间
	@RequestMapping(value="onlines")
	public void onlinetime(HttpServletRequest request, HttpServletResponse response, Model model, String padCodes,Integer onlinetime) throws Exception {
       userPadService.updateBatchTime(request, padCodes, onlinetime);
	}
	
	//批量修改控制天
	@RequestMapping(value="controls")
	public void controltime(HttpServletRequest request, HttpServletResponse response, Model model, String padCodes,String timeType,Integer controltime) throws Exception {
		userPadService.updateBatchDay(request, padCodes,timeType,controltime);
	}
	
	//批量进行设备操作
	@RequestMapping(value = "batchForm")
	public String batchForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
		return this.toPage(request, response, model);
	}
	
	//批量执行设备操作
	@RequestMapping(value="batchPad")
	public void batchPad(HttpServletRequest request, HttpServletResponse response, Model model, String padIp,String remark,String actionType) throws Exception {
		service.batchPad(request, padIp,remark,actionType);
	}
	
	// 授权开放虚拟设备
	@RequestMapping(value = "openOn")
	public void openOn(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		service.openOn(request, bean);
	}

	// 取消虚拟设备开放授权
	@RequestMapping(value = "openOff")
	public void openOff(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		service.openOff(request, bean);
	}
	
	
	//批量更换设备Form
	@RequestMapping(value = "renewalForm")
	public String renewalForm(HttpServletRequest request, HttpServletResponse response, Model model, ViewPadUser bean) throws Exception {
		List<RfMsgTemplate> msgTemplates_msg = msgTemplateService.initQuery()
				.andEqualTo("templateType", MsgTemplateType.TEMPLATE_MSG)
				.addOrderClause("reorder", "asc")
				.findStopTrue();
		List<RfMsgTemplate> msgTemplates_weixin = msgTemplateService.initQuery()
				.andEqualTo("templateType", MsgTemplateType.TEMPLATE_WEIXIN)
				.addOrderClause("reorder", "asc")
				.findStopTrue();
		List<RfMsgTemplate> msgTemplates_msg_last = msgTemplateService.initQuery()
				.andEqualTo("templateType", MsgTemplateType.TEMPLATE_MSG)
				.addOrderClause("createTime", "desc")
				.findStopTrue();
		List<RfMsgTemplate> msgTemplates_weixin_last = msgTemplateService.initQuery()
				.andEqualTo("templateType", MsgTemplateType.TEMPLATE_WEIXIN)
				.addOrderClause("createTime", "desc")
				.findStopTrue();
		
		model.addAttribute("msgTemplates_msg", msgTemplates_msg);
		model.addAttribute("msgTemplates_weixin", msgTemplates_weixin);
		model.addAttribute("msgTemplates_msg_last", msgTemplates_msg_last.size()>0?msgTemplates_msg_last.get(0).getTemplateText():"");
		model.addAttribute("msgTemplates_weixin_last", msgTemplates_weixin_last.size()>0?msgTemplates_weixin_last.get(0).getTemplateText():"");
		return this.toPage(request, response, model);
	}
	
	//批量更换设备
	@RequestMapping(value = "renewal")
	public void renewal(HttpServletRequest request, HttpServletResponse response, Model model, 
			String  code,String suffix,String isSendMessage,String isSendWechart,String sendMessageTemplate,String sendWechartTemplate) throws Exception{
		//对是否发送消息先做判断
		if(StringUtils.isNotBlank(sendMessageTemplate)){
			isSendMessage=YesOrNoStatus.YES;
		}else{
			isSendMessage=YesOrNoStatus.NO;
		}
		if(StringUtils.isNotBlank(sendWechartTemplate)){
			isSendWechart=YesOrNoStatus.YES;
		}else{
			isSendWechart=YesOrNoStatus.NO;
		}
		
		
		code=code.replaceAll(" ", "");
		code=code.replaceAll("\r", "");
		String [] padCodes = code.split("\n");
		if(padCodes.length<1){
			throw new BusinessException("错误：001 输入了无效参数");
		}else if(padCodes.length>1000){
			throw new BusinessException("批量更换的设备不能超过1000个");
		}
		for (String str : padCodes) {
			if(StringUtils.isNotBlank(str)){
				Integer userId = null;
				
				Map<String,Object> params = service.renewalOnly(request, response, model, str, suffix,isSendMessage,isSendWechart,sendMessageTemplate,sendWechartTemplate);
				
				if(params.containsKey("userId")){
					userId = (Integer)params.get("userId");
				}
				if(null == userId){
					break;
				}
				if(null != userId){
					//向用户发送jms
					try{
						JSONObject sendMessageObj = new JSONObject();
						sendMessageObj.put("opType", "refresh");
						sendMessageObj.put("userId", userId);
						String sendMessageStr = sendMessageObj.toString();
						log.info("sendMessageStr: "+ sendMessageStr);
						// 发送jms消息
						fingerProducer.sendMessage(sendMessageStr);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				if(params.containsKey("wechartTemplateId")){
					JSONObject sendMessageObj = new JSONObject();
					//向用户发送jms
					sendMessageObj.clear();
					sendMessageObj.put("templateId", (Integer)params.get("wechartTemplateId"));
					String sendMessageStr = sendMessageObj.toString();
					sendMessageStr = sendMessageObj.toString();
					log.info("renewal sendMessageStr: "+ sendMessageStr);
					// 发送jms消息
					weixinProducer.sendMessage(sendMessageStr);
				}
			}
		}
		
		
		/*List<Integer> userIds = service.renewal(request, response, model, code,suffix);
		
		if(null !=userIds && userIds.size()>0){
			for (Integer userId : userIds) {
				//向用户发送jms
				try{
					JSONObject sendMessageObj = new JSONObject();
					sendMessageObj.put("opType", "refresh");
					sendMessageObj.put("userId", userId);
					String sendMessageStr = sendMessageObj.toString();
					log.info("sendMessageStr: "+ sendMessageStr);
					// 发送jms消息
					fingerProducer.sendMessage(sendMessageStr);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}*/
	}
	
	@RequestMapping(value = "maintainOn")
	public void maintainOn(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		service.maintain(request, bean);
	}

	@RequestMapping(value = "maintainOff")
	public void maintainOff(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		service.maintain(request, bean);
	}
	
	//批量维护操作
	@RequestMapping(value = "batchMaintainForm")
	public String batchMaintainForm(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	//批量执行设备操作
	@RequestMapping(value="batchMaintain")
	public void batchMaintain(HttpServletRequest request, HttpServletResponse response, Model model, String padCode,String maintStatus) throws Exception {
		service.batchMaintain(request, maintStatus, padCode);
	}
	
	@RequestMapping(value = "lookGrantCode")
	public String lookGrantCode(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) {
		
		model.addAttribute("padId", bean.getPadId());
		model.addAttribute("enableStatus", YesOrNoStatus.YES);
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "getGrantCode")
	public PageInfo<RfPadGrantCode> getGrantCode(HttpServletRequest request, HttpServletResponse response, Model model, String padId,String grantCode, RfPadGrantCode bean) throws Exception {
		List<RfPadGrantCode> list = padGrantCodeService.initQuery(bean)
				.andEqualTo("padId", bean.getPadId())
				.andEqualTo("enableStatus", bean.getEnableStatus())
				.andLike("grantCode", bean.getGrantCode())
				.addOrderClause("grantCodeStatus", "asc nulls last")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		if(null != list && list.size()>0){
			for (RfPadGrantCode rfPadGrantCode : list) {
				rfPadGrantCode.getMap().put("padName", service.get(rfPadGrantCode.getPadId()).getPadName());
				rfPadGrantCode.getMap().put("grantControlName", GrantControlUtils.DICT_MAP.get(rfPadGrantCode.getGrantControl()));
				rfPadGrantCode.getMap().put("grantWatchName", GrantWatchUtils.DICT_MAP.get(rfPadGrantCode.getGrantWatch()));
				rfPadGrantCode.getMap().put("grantCodeStatusName", GrantCodeStatusUtils.DICT_MAP.get(rfPadGrantCode.getGrantCodeStatus()));
			}
		}
		PageInfo<RfPadGrantCode> pageInfo = new PageInfo<RfPadGrantCode>(list);
		return pageInfo;
	}
	
	// 修改设备状态
	@RequestMapping(value = "updatePadStatus")
	public void updatePadStatus(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean) throws Exception {
		if (null != bean.getPadId()) {
			bean = service.get(bean.getPadId());
			//向用户发送jms
			JSONObject sendMessageObj = new JSONObject();
			sendMessageObj.put("opType", "checkPadStatus");
			sendMessageObj.put("padCode", bean.getPadCode());
			String sendMessageStr = sendMessageObj.toString();
			log.info("sendMessageStr: "+ sendMessageStr);
			// 发送jms消息
			fingerProducer.sendMessage(sendMessageStr);
		} else {
			throw new BusinessException("请选择设备！");
		}
	}
	
	@RequestMapping(value="formPad")
	public String formPad(HttpServletRequest request,HttpServletResponse response,Model model,RfPad bean){
	    bean= service.get(bean.getPadId());	
	    if(null==bean.getPadId()){
		    throw new BusinessException("设备编号"+bean.getPadCode()+"没有记录设备信息，无法进行解绑操作");
	    }
	    RfUserPad userPad=userPadService.getUserPadByPadId(bean.getPadId());
	    RfUser user = new RfUser();
	    if(null!=userPad){
		    //throw new BusinessException("用户设备关系表"+bean.getPadCode()+"没有记录设备绑定信息,设备Id:"+bean.getPadId()+"，无法进行解绑操作");
	    	user = userService.get(userPad.getUserId());
	    }
	    model.addAttribute("user", user);
		model.addAttribute("pad", bean);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value="padTask")
	@ResponseBody
	public  Map<String, Object> padTask(HttpServletRequest request,HttpServletResponse response,Model model,String type,Integer padId) throws Exception{
		
		RfPad pad = service.get(padId);
		if(null == pad){
			throw new BusinessException("设备id【"+padId+"】的设备不存在");
		}
		RfPadTaskBatch task = new RfPadTaskBatch();
		task.setRemark(type);
		task.setName(pad.getPadCode());
		RfPadTask padTask = null;
		Map<String, Object> map = new HashMap<>();
		if("vm_screencap".equals(type)){
			padTask = taskBatchService.saveVmScreencap(request, task, pad);
		}else if("device_get_info".equals(type)){
			padTask = taskBatchService.saveDeviceGetInfo(request, task, pad, map);
		}else if("pad_package".equals(type)){
			padTask = taskBatchService.saveGameInfo(request, task, pad);
		}
		
		if(null != padTask){
			pro.sendPadCode(String.valueOf(padTask.getTaskId()));
			
			RfPadTaskBatch padTaskBatch = new RfPadTaskBatch();
			
			if(null != padTask){
				List<RfPadTask> list = padTaskService.initQuery(padTaskBatch)
						.andEqualTo("batchId", padTask.getBatchId())
						.addOrderClause("createTime", "desc")
						.addOrderForce(padTask.getSort(), padTask.getOrder())
						.pageDelTrue(padTask.getPage(), padTask.getRows());
				for (RfPadTask rfPadTask : list) {
					if ("device_ping".equals(rfPadTask.getCommandType())) {
						rfPadTask.getMap().put("IP", deviceMapper.selectByPrimaryKey((rfPadTask.getPadId())).getDeviceIp());
					} else {
						rfPadTask.getMap().put("IP", padService.load(rfPadTask.getPadId()).getPadIp());
					}
				}
				PageInfo<RfPadTask> rows = new PageInfo<RfPadTask>(list);
				
				HashMap<String, Object> result = new HashMap<>();
				result.put("rows", rows);
				result.put("taskId", padTask.getTaskId());
				map.put("taskId", padTask.getTaskId());
				if("device_get_info".equals(type)){//10秒内每隔一秒去获取一次设备属性
					for(int i=0;i<10;i++){
						if(null==map.get("MEM_TOTAL")){
							taskBatchService.getDeviceInfo(padTask.getTaskId(), map);
						}else{
							break;
						}
						Thread.sleep(1000);
					}
				}else if("pad_package".equals(type)){//10秒内每隔一秒去获取一次设备游戏情况
					for(int i=0;i<10;i++){
					    if(null==map.get("gameList")){
					    	taskBatchService.getGameInfo(padTask.getTaskId(), map);
					    }else{
					        break;
					    }
					    Thread.sleep(1500);
					}
						
					
				}
				if(map.size()>0){
					result.put("info", map);
				}
				return result;
			}
		}
		return null;
	}
	
	@RequestMapping(value="getDeviceInfo")
	@ResponseBody
	public  Map<String, Object> getDeviceInfo(HttpServletRequest request,HttpServletResponse response,Model model,Integer taskId,String IP) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("IP", IP);
		map.put("taskId", taskId);
		taskBatchService.getDeviceInfo(taskId, map);
		return map;
	}
	
	@RequestMapping(value="getGameInfo")
	@ResponseBody
	public  Map<String, Object> getGameInfo(HttpServletRequest request,HttpServletResponse response,Model model,Integer taskId) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("taskId", taskId);
		taskBatchService.getGameInfo(taskId, map);
		return map;
	}
	
	@RequestMapping(value="padTaskList")
	@ResponseBody
	public  PageInfo<RfPadTask> padTaskList(HttpServletRequest request,HttpServletResponse response,Model model,Integer taskId) throws Exception{
		RfPadTask padTask = taskBatchService.getPadTaskByTaskId(taskId);
		if(null != padTask){
			RfPadTaskBatch padTaskBatch = new RfPadTaskBatch();
			if(null != padTask){
				List<RfPadTask> list = padTaskService.initQuery(padTaskBatch)
						.andEqualTo("batchId", padTask.getBatchId())
						.addOrderClause("createTime", "desc")
						.addOrderForce(padTask.getSort(), padTask.getOrder())
						.pageDelTrue(padTask.getPage(), padTask.getRows());
				PageInfo<RfPadTask> rows = new PageInfo<RfPadTask>(list);
				Map<String, Object> result = new HashMap<>();
				result.put("rows", rows);
				return rows;
			}
		}
		return null;
	}
	
	
	@RequestMapping(value="bindLabelForm")
	public String bindLabelForm(HttpServletRequest request,HttpServletResponse response,Model model,RfPad bean){
		List<RfLabel> list = labelService.initQuery().
				andEqualTo("labelType", ConstantsDb.labelTypePad()).findStopTrue();
		bean = service.initQuery(bean).get(bean.getPadId());
		
		List<RfPadLabel> padLabelList = labelService.selectByPadId(bean.getPadId());
		Map<String,RfPadLabel> padLabelMap = new HashMap<String,RfPadLabel>();
		for (RfPadLabel rfPadLabel : padLabelList) {
			padLabelMap.put(rfPadLabel.getLabelId() + "_" + bean.getPadId(), rfPadLabel);
		}
		
		model.addAttribute("padLabelMap", padLabelMap);
		model.addAttribute("bean", bean);
		model.addAttribute("list", list);
		model.addAttribute("padLabelList", padLabelList);
		return this.toPage(request, response, model);
	}
	
	/**
	 * 设备批量绑定标签form
	 * @param request
	 * @param response
	 * @param model
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "batchBindLabelForm")
	public String batchBindLabelForm(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean) throws Exception {
		List<RfLabel> list = labelService.initQuery().
				andEqualTo("labelType", ConstantsDb.labelTypePad()).findStopTrue();
		model.addAttribute("list", list);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value = "bindLabel")
	public void bindLabel(HttpServletRequest request, HttpServletResponse response, Model model, RfPad bean,
			String labelIds) throws Exception {
		if(StringUtils.isBlank(labelIds)){
			throw new BusinessException("请选择标签");
		}
		
		if(null == bean.getPadId()){
			throw new BusinessException("请选择设备");
		}
		service.savePadLabel(request, labelIds, bean.getPadCode());
	}
	
	@RequestMapping(value = "batchBindLabel")
	public void batchBindLabel(HttpServletRequest request, HttpServletResponse response, Model model, RfUser bean,
			String labelIds,String padCode) throws Exception {
		if(StringUtils.isBlank(labelIds)){
			throw new BusinessException("请选择标签");
		}
		
		if(StringUtils.isBlank(padCode)){
			throw new BusinessException("请填写设备编码");
		}
		service.savePadLabel(request, labelIds, padCode);
	}
	
	
	/**
	 * 查询出设备的信息,跳转到管理员授权设备页面,一次只可以授权一台
	 * @param request 请求
	 * @param response 响应
	 * @param model 模型对象
 	 * @param padIds 请求绑定的设备id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "adminGrantForm")
	public String adminGrantForm(HttpServletRequest request, HttpServletResponse response, Model model, String padIds) throws Exception {
		/**  */
		if (!StringUtils.isNotBlank(padIds)) {
			throw new BusinessException("请选择一台需要授权的设备!");
		}
		
		if (padIds.contains(",")) {
			throw new BusinessException("一次只能授权一台设备!");
		}
		
		/** 根据用户选中的设备id查询出设备的的信息,显示到前端页面 */
		List<Integer> padIdList = new ArrayList<Integer>();
		for (String padIdStr : padIds.split(",")) {
			padIdList.add(Integer.parseInt(padIdStr));
		}
		List<RfPad> padList = service.initQuery().andIn("padId",padIdList).findAll();
		
		/** 获取到第一条数据 */
		RfPad rfPad = padList.get(0);
		
		/** 根据设备的id获取到绑定的用户 */
		RfUserPad rfUserPad = userPadService.getUserPadByPadId(rfPad.getPadId());
		/** 如果用户设备是空,表示这个设备没有被用户绑定 */
		if (rfUserPad == null) {
			throw new BusinessException("用户没有绑定这个设备");
		}
		
		/** 剩余时间如果不能被整除,就加上1 */
		Date expireTime = rfUserPad.getExpireTime();
		long currentTime = System.currentTimeMillis();
		String leftControlTime = DateUtils.formatDateTime2(expireTime.getTime()-currentTime);
		model.addAttribute("leftControlTime", rfUserPad == null?null:leftControlTime);
		model.addAttribute("expireTime", DateUtils.formatDate(expireTime, "yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("pad", rfPad);
		/** 跳转到授权页面 */
		return this.toPage(request, response, model);
	}
	
	/** 授权的时候增加一个字段grantOperator,表示的是授权的类型 */
	/**
	 * 授权给管理人员
	 * @param request 请求
	 * @param response 响应
	 * @param model 模型
	 * @param padCode 设备编号
	 * @param granteeAccount 被授权人账号
	 * @param grantOperate 授权的类型
	 * @param grantDays 授权的天数
	 * @throws Exception 
	 */
	@RequestMapping(value = "adminGrant")
	public void adminGrant(HttpServletRequest request, HttpServletResponse response, Model model, 
			Integer padId,String padCode, String granteeAccount, String grantOperate,String grantDays) throws Exception {

		/** 被授权人进行处理 */
		granteeAccount = granteeAccount != null ? granteeAccount.trim():null;
		
		if(StringUtils.isBlank(padCode)){
			throw new BusinessException("设备码不能为空,授权失败!");
		}
		
		if(StringUtils.isBlank(granteeAccount)){
			throw new BusinessException("被授权人不能为空,授权失败!");
		}
		
		if(StringUtils.isBlank(grantDays)){
			throw new BusinessException("授权到期日期为空,授权失败!");
		}

		/** 根据用户手机号码获取到用户 */
		RfUser granteeUser = userService.getUserByUserPhone(granteeAccount);
		if (granteeUser == null) {
			throw new BusinessException("授权失败,请输入正确的手机号码!");
		}
		
		/** 授权人只能是管理员,用户的email如果是管理员的主键,那么该用户就是管理员 */
		String userEmail = granteeUser.getUserEmail();
		if (userEmail == null || StringUtils.isBlank(userEmail) || adminService.get(userEmail) == null) {
			throw new BusinessException("该操作只能将设备授权给管理员，请确认会员帐号邮箱和管理员帐号一致!");
		}
		
		
		/** 根据设备码获取到设备对象 */
		RfPad rfPad = padService.getPadByPadCode(padCode);
		if(rfPad == null){
			throw new BusinessException("设备不存在,授权失败!");
		}
	
		if(!rfPad.getStatus().equals(YesOrNoStatus.YES) || !rfPad.getEnableStatus().equals(YesOrNoStatus.YES)){
			throw new BusinessException("设备状态异常,授权失败!");
		}
		
		/** 根据设备id获取到用户设备id  */
		RfUserPad userPad = userPadService.getUserPadByPadId(padId);
		if (userPad == null) {
			throw new BusinessException("用户没有绑定该设备,授权失败!");
		}
		
		/** 查询出用户的手机号码.判断是否是自己授权给自己 */
		RfUser rfUser = userService.get(userPad.getUserId());
		if (granteeAccount.equals(rfUser.getUserMobilePhone())) {
			throw new BusinessException("管理员不能自己授权给自己,授权失败!");
		}
		
		/** 根据用户id和设备id查询授权设备,如果已经存在,不可以重复授权了 */
		int rfPadGrantCount = padGrantService.selectCountRfPadGrantByUserIdAndPadId(granteeUser.getUserId(),padId);
		if (rfPadGrantCount > 0) {
			throw new BusinessException("该设备已经被授权给该用户,无法重复授权!");
		}
		
		
		if(userPad.getExpireTime()==null||userPad.getExpireTime().getTime() <= new Date().getTime()){
			throw new BusinessException("设备已过期,不能进行授权!");
		}
		
		/** 授权终止时间
		 * 如果管理员输入的授权时间大于设备的到期时间
		 * 那么后台修改授权设备到期时间为设备绑定到期时间.
		 * */
		Date grantExpireTime = null;
		if(grantDays != null){
			grantExpireTime = DateUtils.strExchangeDate(grantDays);
			
			if(grantExpireTime.getTime() > userPad.getExpireTime().getTime()){
				/** 设备的输入授权时间大于设备的到期时间 */
				throw new BusinessException("授权到期时间大于设备到期时间,授权失败!");
				/** 当授权时间小于当前的时间的时候,表示授权时间无效 */
			}else if (grantExpireTime.getTime() < new Date().getTime()) {
				throw new BusinessException("授权时间无效,授权失败");
			}
		}
		
		/** 设备不能重复授权,授权类型"0"表示管理员授权,1表示用户授权,默认是1 */
		int grantCount = padGrantService.getPadGrantCountByUserPadAndGrantType(userPad.getUserPadId(),GrantTypeUtils.ADMIN);
		
		if (grantCount > 0) {
			throw new BusinessException("该设备已经授权给管理员,不能重复授权!");
		}
		
		if (!StringUtils.equals(granteeUser.getEnableStatus(), YesOrNoStatus.YES) || !StringUtils.equals(granteeUser.getStatus(), YesOrNoStatus.YES)) {
			throw new BusinessException("用户已经被冻结,授权失败!");
		}
		
		Integer saveGrantCount = padGrantService.savePadGrantByAccount(request,granteeUser.getUserId(), userPad, grantOperate, grantExpireTime);
		
		if (saveGrantCount <= 0) {
			/** 表示授权失败 */
			throw new BusinessException("该设备已经授权给管理员,不能重复授权!");
		}
	}
	
}
