package com.redfinger.manager.modules.log.web;

import java.io.IOException;
import java.util.List;

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
import com.redfinger.manager.common.base.BaseController;
import com.redfinger.manager.common.domain.LogPad;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.common.utils.StringUtils;
import com.redfinger.manager.modules.log.service.LogPadService;
import com.redfinger.manager.modules.sys.service.AdminService;

@Controller
@RequestMapping(value = "/log/padOper")
public class LogPadOperController extends BaseController {

	@Autowired
	LogPadService service;
	@Autowired
	AdminService adminService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}

	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<LogPad> list(HttpServletRequest request, HttpServletResponse response, Model model, LogPad bean) throws Exception {
		List<LogPad> list = service.initQuery(bean).andGreaterThanOrEqualTo("createTime", DateUtils.parseDate(bean.getBeginTimeStr()))
				.andLessThan("createTime", DateUtils.parseDateAddOneDay(bean.getEndTimeStr()))
				.andLike("creater", bean.getCreater())
				.andLike("padCode", bean.getPadCode())
				.andEqualTo("category", bean.getCategory()).addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder()).pageAll(bean.getPage(), bean.getRows());
		PageInfo<LogPad> pageInfo = new PageInfo<LogPad>(list);
		return pageInfo;

	}
	
	//会员导出
	@RequestMapping(value="export")
    public String export(HttpServletRequest request,HttpServletResponse response,Model model,LogPad bean,String exportHead, String exportField, String exportName)throws Exception{
		exportHead=StringUtils.removeEnd(exportHead, ",");
	    exportField=StringUtils.removeEnd(exportField, ",");
	    response.setContentType("application/binary;charset=UTF-8");
	    ServletOutputStream outputStream=response.getOutputStream();
	    response.setHeader("Content-disposition", "attachment;filename="+ExcelUtils.getFileName(request, exportName)+".xls");
	    Workbook workBook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet("Sheet1");
		CellStyle headStyle = ExcelUtils.getHeadStyle(workBook);
		CellStyle bodyStyle = ExcelUtils.getBodyStyle(workBook);
	    ExcelUtils.insertHead(exportHead.split(","), sheet, headStyle);
		boolean keep = true;
		int page = 1;
		while (keep) {
			bean.setPage(page);
			bean.setRows(1000);
			PageInfo<LogPad> pageInfo = this.list(request, response, model, bean);
			List<LogPad> list = pageInfo.getList();
			if (!Collections3.isEmpty(list)) {
				for (LogPad logPad : list) {
					logPad.setCategory(DictHelper.getLabel("log_pad.category", logPad.getCategory()));
				}
				
				ExcelUtils.insertPageBody(JsonUtils.ObjectToString(list), sheet, bodyStyle, exportField.split(","), pageInfo.getStartRow());
				if (pageInfo.isHasNextPage()) {
					page++;
					continue;
				}
			}
			keep = false;
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
