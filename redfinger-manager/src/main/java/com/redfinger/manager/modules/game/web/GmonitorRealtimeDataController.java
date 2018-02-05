package com.redfinger.manager.modules.game.web;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.redfinger.manager.common.constants.ConstantsDb;
import com.redfinger.manager.common.constants.ExportConstants;
import com.redfinger.manager.common.domain.RfExport;
import com.redfinger.manager.common.domain.RfGameMonitorCfg;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.domain.RfUserPad;
import com.redfinger.manager.common.domain.ViewGmonitorRealtimeData;
import com.redfinger.manager.common.domain.ViewGmonitorRealtimeDataExample;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.jsm.ExportProducer;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.IP2LongUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.batch.service.ExportService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.game.service.GameMonitorService;
import com.redfinger.manager.modules.game.service.GmonitorRealtimeDataService;
import com.redfinger.manager.modules.game.service.ViewGmonitorRealtimeDataService;
import com.redfinger.manager.modules.member.service.UserService;
import com.redfinger.manager.modules.stat.base.StatDomain;
import com.redfinger.manager.modules.stat.base.StatRealTimeDomain;

@Controller
@RequestMapping(value = "/game/gmonitor")
public class GmonitorRealtimeDataController extends BaseController {
	@Autowired
	ViewGmonitorRealtimeDataService service;
	@Autowired
	GmonitorRealtimeDataService gmonitorReatimeDataService;
    @Autowired
    PadService padService;
	@Autowired
	GameMonitorService gameMonitorService;
	@Autowired
	private UserService userService;
	@Autowired
	private ExportService exportService;
	@Autowired
	private FilePathUtils filePathUtils;
	@Autowired
	private ExportProducer exportProducer;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
	List<RfGameMonitorCfg>list=gameMonitorService.initQuery().findStopTrue();
	model.addAttribute("monitorlist", list);
	return this.toPage(request, response, model);  
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ViewGmonitorRealtimeData> list(HttpServletRequest request, HttpServletResponse response, Model model, ViewGmonitorRealtimeData bean, 
			Integer externalUserId,String padStartIp, String padEndIp, String startPadCode, String endPadCode) throws Exception {
		
		/** 数据表用户ID*/
		Integer userId = null;
		/** 根据客户端ID查询用户ID*/
		if (externalUserId!=null) {
			RfUser user = userService.getUserByExternalUserIdOrUserPhone(null,externalUserId);
			if (user != null) {
				userId = user.getUserId();
			} else {
				userId = -1;
			}
		}
		
		
		//=======================================================================================================
		service.initQuery(bean);
		ViewGmonitorRealtimeDataExample example =(ViewGmonitorRealtimeDataExample) service.getExample();
		//iPv4的ip地址都是（1~255）.（0~255）.（0~255）.（0~255）的格式
		String regexStr = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		if(StringUtils.isNotBlank(padStartIp)){
			if(padStartIp.matches(regexStr)){
				example.getMap().put("padStartIp", padStartIp);
			}else{
				//deviceIds.add(-1);
				service.andEqualTo("padId", -1);
			}
		}
		if(StringUtils.isNotBlank(padEndIp)){
			if(padEndIp.matches(regexStr)){
				example.getMap().put("padEndIp", padEndIp);
			}else{
				//deviceIds.add(-1);
				service.andEqualTo("padId", -1);
			}
		}
		if(example.getMap().size()==2){
			if(IP2LongUtils.ipToNumber(padStartIp)>IP2LongUtils.ipToNumber(padEndIp)){
				//deviceIds.add(-1);
				service.andEqualTo("padId", -1);
				example.getMap().clear();
			}
		}
		//=======================================================================================================
		String regexChinese = "^[0-9a-zA-Z]+$";
		String padCode = null;
		StringBuilder samePadCode = new StringBuilder();
		if(StringUtils.isNotBlank(startPadCode)){//VM010109036056
			for (int i = 0; i < startPadCode.length(); i++) {
				char ch = startPadCode.charAt(i);
				if(!((ch >= 'A' && ch <='Z') || (ch >= 'a' && ch <= 'z'))){
					samePadCode = StringUtils.isBlank(samePadCode) ? samePadCode.append("-1") : samePadCode;
					break;
				}
				samePadCode.append(ch);
			}
			if (!startPadCode.matches(regexChinese)) {
				if(StringUtils.isNotBlank(endPadCode)){
					startPadCode = endPadCode + "0";
				}else{
					startPadCode = endPadCode = "-1";
				}
			}
		}
		if(StringUtils.isNotBlank(endPadCode)){
			samePadCode.delete(0,samePadCode.length());
			for (int i = 0; i < endPadCode.length(); i++) {
				char ch = endPadCode.charAt(i);
				if(!((ch >= 'A' && ch <='Z') || (ch >= 'a' && ch <= 'z'))){
					samePadCode = StringUtils.isBlank(samePadCode) ? samePadCode.append("-1") : samePadCode;
					break;
				}
				samePadCode.append(ch);
			}
			if (!endPadCode.matches(regexChinese)) {
				endPadCode = "-1";
			}
		}
		
		
		if(StringUtils.isNoneBlank(startPadCode, endPadCode) && startPadCode.length() > 0 && endPadCode.length() > 0){
			samePadCode.delete(0,samePadCode.length());
			char[] startArray = startPadCode.toCharArray();
			char[] endArray = endPadCode.toCharArray();
			for (int i = 0; i < startArray.length && i < endArray.length; i++) {
				if(startArray[i] != endArray[i]){
					break;
				}
				samePadCode.append(startArray[i]);
			}
		}
		
		padCode = samePadCode.length() <= 0 ? null : samePadCode.toString();
		
		
		List<ViewGmonitorRealtimeData> list = service
			.andLike("padCode", bean.getPadCode())
			.andLike("gameNameT2", bean.getGameNameT2())
			.andEqualTo("userMobilePhoneT4", bean.getUserMobilePhoneT4())
			.andEqualTo("gameStatusT2", bean.getGameStatusT2())
			.andGreaterThanOrEqualTo("createTimeT4",DateUtils.parseDate(bean.getBeginTimeStr()))
			.andLessThan("createTimeT4",  DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
			.andEqualTo("registerIpT4", bean.getRegisterIpT4())
			.andEqualTo("userIdT3", userId)
			.andLikeSuffix("padCode", padCode)
			.andGreaterThanOrEqualTo("padCode", StringUtils.isNotBlank(startPadCode) ? startPadCode : null)
			.andLessThanOrEqualTo("padCode", StringUtils.isNotBlank(endPadCode) ? endPadCode : null)
			.addOrderClause("modifyTimeT2", "desc Nulls Last ")
			.addOrderForce(bean.getSort(), bean.getOrder())	
			.pageDelTrue(bean.getPage(), bean.getRows());
		
		    if(list != null && list.size() > 0){
		    	for (ViewGmonitorRealtimeData viewGmonitorRealtimeData : list) {
		    		/** 查询客户端ID*/
					if(viewGmonitorRealtimeData.getUserIdT3() != null){
						/** 根据用户Id查询用户*/
						RfUser rfUser = userService.load(viewGmonitorRealtimeData.getUserIdT3());
						/** 查询客户端ID*/
						viewGmonitorRealtimeData.getMap().put("externalUserId", rfUser != null ? rfUser.getExternalUserId() : "");
					}
				}
		    }
	PageInfo<ViewGmonitorRealtimeData> pageInfo = new PageInfo<ViewGmonitorRealtimeData>(list);
	return pageInfo;
}

	@ResponseBody
	@RequestMapping(value = "getChart")
	public Map<String, Object> getChart(HttpServletRequest request, HttpServletResponse response, Model model, StatDomain bean) {
		return service.stat(bean);
	}


	@ResponseBody
	@RequestMapping(value = "monitorList")
	public List<StatRealTimeDomain> monitorList(HttpServletRequest request, HttpServletResponse response, Model model, StatDomain bean,String beginTimeStr) {
		if(null!=beginTimeStr&&!"".equals(beginTimeStr)){
			bean.setWhere(beginTimeStr);
		}
		List<StatRealTimeDomain> list=service.statRealTime(bean);
         
		return list;
	}
	@ResponseBody
	@RequestMapping(value = "floatData")
	public Map<String, Object> floatDate(HttpServletRequest request, HttpServletResponse response, Model model, RfUserPad bean) throws Exception {
		Map<String,Object> map=Maps.newHashMap();
		//设备总数padTotal
		Integer padTotal=padService.initQuery().countStopTrue();
		map.put("padTotal", padTotal);
		//在线设备onlinePad
		Integer zx=padService.initQuery().andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOnline()).countStopTrue();
		Integer sk=padService.initQuery().andEqualTo("padStatus", ConstantsDb.rfPadPadStatusControl()).countStopTrue();
		Integer onlinePad=zx+sk;
		map.put("onlinePad", onlinePad);
		//离线设备offlinePad
		Integer offlinePad=padService.initQuery().andEqualTo("padStatus", ConstantsDb.rfPadPadStatusOffline()).countStopTrue();
		map.put("offlinePad", offlinePad);
		//监控游戏总数gameTotal                                
		Integer gameTotal=gmonitorReatimeDataService.initQuery().andIsNotNull("gameStatus").countStopTrue();
		map.put("gameTotal", gameTotal);
		//在线游戏数onlineGmae
		Integer onlineGmae=gmonitorReatimeDataService.initQuery().andEqualTo("gameStatus", ConstantsDb.rfGmonitorGameGameStatusOnline()).countStopTrue();
		map.put("onlineGmae", onlineGmae);
		//离线游戏数offlineGame
		Integer offlineGame=gmonitorReatimeDataService.initQuery().andEqualTo("gameStatus", ConstantsDb.rfGmonitorGameGameStatusOffline()).countStopTrue();
		map.put("offlineGame", offlineGame);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, StatDomain bean) throws Exception {
		Map<String,Object> map=this.getChart(request, response, model, bean);
		List<Map<String,Object>> list=Lists.newArrayList();
	/*	model.addAttribute("gameList", map.get("label") );
		model.addAttribute("online", map.get("number"));
		model.addAttribute("offline", map.get("number1"));*/
		List<String>str=(List<String>)  map.get("label");
		List<Object>str1=(List<Object>) map.get("number");
		List<Object>str2=(List<Object>) map.get("number1");
		int i=0;
		for (String string : str) {
			Map<String,Object> map1=Maps.newLinkedHashMap();
			map1.put("game", string);
			map1.put("online", str1.get(i));
			map1.put("offline", str2.get(i++));
			list.add(map1);
		}
          model.addAttribute("list",list);
          List<RfGameMonitorCfg>listMonitor=gameMonitorService.initQuery().findStopTrue();
          model.addAttribute("monitorlist", listMonitor);
		return this.toPage(request, response, model);
	}
	
	@RequestMapping(value="export")
	public String export(HttpServletRequest request, HttpServletResponse response, 
			Model model, ViewGmonitorRealtimeData bean,Integer externalUserId, String padStartIp, String padEndIp,String startPadCode, String endPadCode,
			String exportHead, String exportField, String exportName)throws Exception{
		exportHead=StringUtils.removeEnd(exportHead, ",");
		exportField=StringUtils.removeEnd(exportField, ",");
		response.setContentType("application/binary;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xlsx");
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
			PageInfo<ViewGmonitorRealtimeData> pageInfo = this.list(request, response, model, bean, externalUserId, padStartIp, padEndIp,startPadCode , endPadCode);
			List<ViewGmonitorRealtimeData> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
				for (ViewGmonitorRealtimeData viewGmonitorRealtimeData : list) {
					if(viewGmonitorRealtimeData != null){
						viewGmonitorRealtimeData.setPadGradeT3(DictHelper.getLabel("rf_user_pad.pad_grade", viewGmonitorRealtimeData.getPadGradeT3()));
						viewGmonitorRealtimeData.setPadStatus(DictHelper.getLabel("rf_pad.pad_status", viewGmonitorRealtimeData.getPadStatus()));
						viewGmonitorRealtimeData.setGameStatusT2(DictHelper.getLabel("rf_gmonitor_game.game_status", viewGmonitorRealtimeData.getGameStatusT2()));
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
	
	
	@RequestMapping(value = "asyncExport")
	public void asyncExport(HttpServletRequest request, HttpServletResponse response, 
			Model model, String exportHead, String exportField, String exportName,String exportTaskName, String queryParams) throws Exception{
		String queryJson = URLDecoder.decode(queryParams, "utf-8");
		
		List<RfExport> rfExportts = exportService.initQuery()
				.andEqualTo("parm", queryJson)
				.andEqualTo("type", ExportConstants.TYPE_GMONITOR)
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
		export.setType(ExportConstants.TYPE_GMONITOR);
		export.setExportStatus(ExportConstants.EXPORT_STATUS_MAKE);//任务创建未执行
		exportService.save(request, export);
		HashMap<Object, String> exportMap = Maps.newHashMap();
		exportMap.put("exportId", String.valueOf(export.getExportId()));
		exportMap.put("exportHead", exportHead);
		exportMap.put("exportField", exportField);
		System.out.println(JsonUtils.ObjectToString(exportMap));
		exportProducer.sendMessage(JsonUtils.ObjectToString(exportMap));
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
