package com.redfinger.manager.modules.log.web;

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
import com.google.common.collect.Maps;
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.base.FilePathUtils;
import com.redfinger.manager.common.constants.ExportConstants;
import com.redfinger.manager.common.domain.RfDevice;
import com.redfinger.manager.common.domain.RfExport;
import com.redfinger.manager.common.domain.RfPad;
import com.redfinger.manager.common.domain.RfVmTaskHis;
import com.redfinger.manager.common.exception.BusinessException;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.jsm.ExportProducer;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.SessionUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.batch.service.ExportService;
import com.redfinger.manager.modules.facility.service.ControlService;
import com.redfinger.manager.modules.facility.service.DeviceService;
import com.redfinger.manager.modules.facility.service.PadService;
import com.redfinger.manager.modules.log.service.VmTaskHisService;

@Controller
@RequestMapping(value="/log/vmTaskHis")
public class VmTaskHisController extends BaseController {
	@Autowired 
	VmTaskHisService service;
	@Autowired
	DeviceService deviceService;
	@Autowired
	PadService padService;
	@Autowired
	ControlService controlService;
	@Autowired
	ExportService exportService;
	@Autowired
	FilePathUtils filePathUtils;
	@Autowired
	ExportProducer exportProducer;
	
	@RequestMapping(value="")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		model.addAttribute("currentUserId", SessionUtils.getCurrentUserId(request));
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfVmTaskHis> list(HttpServletRequest request, HttpServletResponse response, Model model, RfVmTaskHis bean,String deviceCode,String padCode) throws Exception {
	Integer deviceId=null;
	Integer padId=null;
		if (null != deviceCode && !"".equals(deviceCode)) {
			RfDevice device = deviceService.getDeviceByPadCode(deviceCode);
			if(null!=device){
				deviceId=device.getDeviceId();
			}else{
				deviceId = -1;
			}
		}
		
		if (null != padCode && !"".equals(padCode)) {
			RfPad pad = padService.getPadByPadCodeContainDel(padCode);
			if(null!=pad){
				padId = pad.getPadId();
			}else{
				padId = -1;
			}
		}

		List<RfVmTaskHis> list = service.initQuery(bean)
				.andEqualTo("taskSource", bean.getTaskSource())
				.andEqualTo("deviceId", deviceId)
		        .andEqualTo("padId", padId)
		        .andLike("creater", bean.getCreater())
				.andEqualTo("taskResultStatus", bean.getTaskResultStatus())
				.andEqualTo("taskType", bean.getTaskType())
				.andLike("taskResultInfo", bean.getTaskResultInfo())
				.andEqualTo("taskStatus", bean.getTaskStatus())
				.andGreaterThanOrEqualTo("createTime",DateUtils.parseDate(bean.getBeginTimeStr()) )
		    	.andLessThanOrEqualTo("createTime", DateUtils.parseDate(bean.getEndTimeStr()))
				.addOrderClause("createTime", "desc nulls last")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		for (RfVmTaskHis rfVmTaskHis : list) {
			rfVmTaskHis.getMap().put("manageControlName", rfVmTaskHis.getDeviceId() == null ? "--" :deviceService.load(rfVmTaskHis.getDeviceId())==null?"--": controlService.load(deviceService.load(rfVmTaskHis.getDeviceId()).getDeviceManageControlId()).getControlName());
			rfVmTaskHis.getMap().put("deviceCode",(null==rfVmTaskHis.getDeviceId()||"".equals(rfVmTaskHis.getDeviceId()))?"--":deviceService.load(rfVmTaskHis.getDeviceId()).getDeviceCode());
			rfVmTaskHis.getMap().put("romVersion",(null==rfVmTaskHis.getDeviceId()||"".equals(rfVmTaskHis.getDeviceId()))?"--":deviceService.load(rfVmTaskHis.getDeviceId()).getRomVersion());
			rfVmTaskHis.getMap().put("deviceIp",(null==rfVmTaskHis.getDeviceId()||"".equals(rfVmTaskHis.getDeviceId()))?"--":deviceService.load(rfVmTaskHis.getDeviceId()).getDeviceIp());
			rfVmTaskHis.getMap().put("padCode",(null==rfVmTaskHis.getPadId()||"".equals(rfVmTaskHis.getPadId()))?"--":padService.load(rfVmTaskHis.getPadId()).getPadCode());
			rfVmTaskHis.getMap().put("padIp",(null==rfVmTaskHis.getPadId()||"".equals(rfVmTaskHis.getPadId()))?"--":padService.load(rfVmTaskHis.getPadId()).getPadIp());
		}
		PageInfo<RfVmTaskHis> pageInfo = new PageInfo<RfVmTaskHis>(list);
		return pageInfo;
	}
	
	//导出EXcel数据
			@RequestMapping(value="export")
			public String export(HttpServletRequest request, HttpServletResponse response, Model model, RfVmTaskHis bean,String deviceCode,String padCode,String exportHead, String exportField, String exportName) throws Exception{
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
					PageInfo<RfVmTaskHis> pageInfo=this.list(request, response, model, bean,deviceCode,padCode);
					List<RfVmTaskHis> list=pageInfo.getList();
					if(!Collections3.isEmpty(list)){
						for(RfVmTaskHis his:list){
							his.setTaskType(DictHelper.getLabel("rf_vm_task.task_type", his.getTaskType()));
					        his.setTaskStatus(DictHelper.getLabel("rf_vm_task.task_result_status", his.getTaskStatus()));
					        his.setTaskResultStatus(DictHelper.getLabel("rf_vm_task.task_result_status", his.getTaskResultStatus()));
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
					Model model, String exportHead, String exportField, String exportName,String exportTaskName, String queryParams,Integer totalCount) throws Exception{
				String queryJson = URLDecoder.decode(queryParams, "utf-8");
				
				List<RfExport> rfExportts = exportService.initQuery()
						.andEqualTo("parm", queryJson)
						.andEqualTo("type", ExportConstants.TYPE_VM_TASK_HIS)
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
				export.setFinishCount(totalCount);
				export.setPath(filePath);
				export.setUrl(fileUrl);
				export.setParm(queryJson);
				export.setTaskName(exportTaskName);
				export.setType(ExportConstants.TYPE_VM_TASK_HIS);
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
