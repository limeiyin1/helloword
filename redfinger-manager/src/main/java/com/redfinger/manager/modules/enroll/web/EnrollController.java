package com.redfinger.manager.modules.enroll.web;

import java.io.IOException;
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
import com.redfinger.manager.common.domain.RfActivityEnroll;
import com.redfinger.manager.common.domain.RfFaultFeedback;
import com.redfinger.manager.common.domain.RfUser;
import com.redfinger.manager.common.helper.DictHelper;
import com.redfinger.manager.common.utils.Collections3;
import com.redfinger.manager.common.utils.DateUtils;
import com.redfinger.manager.common.utils.ExcelUtils;
import com.redfinger.manager.common.utils.JsonUtils;
import com.redfinger.manager.modules.enroll.service.ActivityEnrollService;


@Controller
@RequestMapping(value = "/member/enroll")
public class EnrollController extends BaseController {

	@Autowired
	private ActivityEnrollService service;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.toPage(request, response, model);
	}
	
	@ResponseBody
	@RequestMapping(value = "list")
	public PageInfo<RfActivityEnroll> list(HttpServletRequest request, HttpServletResponse response, Model model, RfActivityEnroll bean,String createTimeBegin,String createTimeEnd) throws Exception {
		List<RfActivityEnroll> list = service.initQuery(bean)
				.andLike("userName", bean.getUserName())
				.andLike("qq", bean.getQq())
				.andLike("mobile", bean.getMobile())
				.andGreaterThanOrEqualTo("createTime",DateUtils.parseDate(createTimeBegin))
				.andLessThanOrEqualTo("createTime",DateUtils.parseDateAddOneDay(createTimeEnd) )
				.addOrderClause("createTime", "desc")
				.addOrderForce(bean.getSort(), bean.getOrder())
				.pageDelTrue(bean.getPage(), bean.getRows());
		PageInfo<RfActivityEnroll> pageInfo = new PageInfo<RfActivityEnroll>(list);
		return pageInfo;
	}
	
	// 导出
	@RequestMapping(value="export")
	public String export(HttpServletRequest request, HttpServletResponse response, Model model, RfActivityEnroll bean,
			String createTimeBegin,String createTimeEnd,
			String exportHead, String exportField, String exportName)throws Exception{
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
			PageInfo<RfActivityEnroll> pageInfo=this.list(request, response, model, bean, createTimeBegin, createTimeEnd);
			List<RfActivityEnroll> list=pageInfo.getList();
			if(!Collections3.isEmpty(list)){
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
