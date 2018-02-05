package com.redfinger.manager.modules.log.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.SysAdmin;
import com.redfinger.manager.common.domain.ViewPadTask;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.RefundLogType;
import com.redfinger.manager.modules.log.service.ViewPadTaskService;
import com.redfinger.manager.modules.sys.service.AdminService;

@Controller
@RequestMapping(value = "/log/padTask")
public class LogPadTaskController extends BaseController {

	@Autowired
	ViewPadTaskService service;
	@Autowired
	AdminService adminService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<ViewPadTask> list(HttpServletRequest request,
			HttpServletResponse response, Model model, ViewPadTask bean,
			String begin, String end) throws Exception {
		Date beginTime = null;
		Date endTime = null;
		if (StringUtils.isNotBlank(begin)) {
			beginTime = DateUtils.parseDate(begin);
		}
		if (StringUtils.isNotBlank(end)) {
			endTime = DateUtils.addDays(DateUtils.parseDate(end), 1);
		}
		List<ViewPadTask> list = service.initQuery(bean)
				.andGreaterThanOrEqualTo("createTime", beginTime)
				.andLessThan("createTime", endTime)
				.andLike("nameT3", bean.getNameT3())
				.andLike("padCodeT2", bean.getPadCodeT2())
				.andLike("taskStatus", bean.getTaskStatus())
				.andEqualTo("commandType", bean.getCommandType())
				.andEqualTo("taskResultStatus", bean.getTaskResultStatus())
				.addOrderClause("createTime", "desc nulls last")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageAll(bean.getPage(), bean.getRows());
		for (ViewPadTask rfPadTask : list) {
			if (StringUtils.isNotBlank(rfPadTask.getCreater())) {
				SysAdmin sysAdmin = adminService.get(rfPadTask.getCreater());
				if (null != sysAdmin) {
					rfPadTask.getMap().put("creater", sysAdmin.getAdminName());
				}
				//rfPadTask.getMap().put("creater",adminService.get(rfPadTask.getCreater()).getAdminName());
			}
		}
		PageInfo<ViewPadTask> pageInfo = new PageInfo<ViewPadTask>(list);
		return pageInfo;
	}
	
	// 导出
		@RequestMapping(value="export")
		public String export(HttpServletRequest request,HttpServletResponse response,Model model,ViewPadTask bean,String exportHead, String exportField, String exportName,String begin, String end)throws Exception{
			exportField=exportField.replace("checkboxValue", "taskId");
			exportHead=StringUtils.removeEnd(exportHead, ",");
			exportField=StringUtils.removeEnd(exportField, ",");
			response.setContentType("application/binary;charset=UTF-8");
			ServletOutputStream outputStream = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment; filename=" + ExcelUtils.getFileName(request, exportName)+".xls");
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
				PageInfo<ViewPadTask> pageInfo=this.list(request, response, model, bean,begin, end);
				List<ViewPadTask> list=pageInfo.getList();
				if(!Collections3.isEmpty(list)){
					for(ViewPadTask rtask:list){
//						rtask.setTaskCommand(DictHelper.getLabel("rf_padtask.batch", rtask.getTaskCommand()));
						rtask.setTaskStatus(DictHelper.getLabel("rf_pad_task.task_status", rtask.getTaskStatus()));
						rtask.setEnableStatus(DictHelper.getLabel("global.enable_status", rtask.getEnableStatus()));
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
}
